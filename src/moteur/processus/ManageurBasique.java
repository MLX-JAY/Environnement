package moteur.processus;

import config.ConfigurationCreationEvenement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import moteur.donne.biome.Biome;
import moteur.donne.carte.Bloc;
import moteur.donne.carte.Carte;
import moteur.donne.evenement.Evenement;
import moteur.donne.evenement.statique.Meteore;
import moteur.processus.regle.RegleTransformation;
import moteur.processus.usine.EvenementFactory;
import moteur.processus.visitor.GenerateurEvenementVisitor;
import moteur.processus.visitor.GestionDeplacementVisitor;
import org.apache.log4j.Logger;
import util.LoggerUtility;

public class ManageurBasique implements Manageur 
{
	
	private static final Logger logger = LoggerUtility.getLogger(ManageurBasique.class);
	
	private Carte carte;
	private Map<Bloc, Biome> biomeMap = new HashMap<>();
	private ArrayList<Evenement> evenements = new ArrayList<>();
	private ArrayList<RegleTransformation> reglesTransformation = new ArrayList<>();
	private int nbTransformationsDansRound = 0;
	private EvenementFactory factory;
	private GenerateurEvenementVisitor generateurEvenementVisitor;
	
	private ArrayList<StatRound> historique = new ArrayList<>();
	private int roundActuel = 0;
	
	public static class StatRound {
		public int round;
		public HashMap<String, Integer> compteBiomes;
		public HashMap<String, Integer> compteEvenements;
		public double[] moyennes;
		public int nbTransformations;
		public HashMap<String, Integer> transformationParType;
		
		public StatRound(int round, HashMap<String, Integer> compteBiomes, 
				HashMap<String, Integer> compteEvenements, double[] moyennes,
				int nbTransformations, HashMap<String, Integer> transformationParType) {
			this.round = round;
			this.compteBiomes = compteBiomes;
			this.compteEvenements = compteEvenements;
			this.moyennes = moyennes;
			this.nbTransformations = nbTransformations;
			this.transformationParType = transformationParType;
		}
	}
	
	public ManageurBasique(Carte carte) 
	{
		this.carte = carte;
		this.factory = new EvenementFactory(carte);
		this.generateurEvenementVisitor = new GenerateurEvenementVisitor(factory);
		
		this.reglesTransformation.add(new moteur.processus.regle.RegleInondation());
		this.reglesTransformation.add(new moteur.processus.regle.RegleDesertification());
		this.reglesTransformation.add(new moteur.processus.regle.RegleGlaciation());
		this.reglesTransformation.add(new moteur.processus.regle.ReglePollutionExtreme());
		this.reglesTransformation.add(new moteur.processus.regle.RegleForestation());
		this.reglesTransformation.add(new moteur.processus.regle.RegleCivilisation());
		this.reglesTransformation.add(new moteur.processus.regle.RegleDensification());
	}
	
	@Override
	public void CarteHasard() 
    {
        biomeMap.clear();
		biomeMap.putAll(moteur.processus.usine.BiomeFactory.creerBiomesCoherents(carte));
    }
    
    public Biome getBiomeByPosition(Bloc position) {
        return biomeMap.get(position);
    }
    
	@Override
	public void bougerEvementMobile() 
	{
		GestionDeplacementVisitor visitor = new GestionDeplacementVisitor(carte, biomeMap);
		
		for (Evenement evenement : evenements) {
			evenement.accept(visitor);
		}
		
		ArrayList<Evenement> expirants = visitor.getEvenementsExpirés();
		
		for (Evenement e : expirants) {
			if (e instanceof Meteore) {
				appliquerImpactZoneMeteore(e.getPosition());
			}
		}
		
		evenements.removeAll(expirants);
	}
	
	private void appliquerImpactZoneMeteore(Bloc positionMeteore) {
		int rayon = ConfigurationCreationEvenement.METEORE_RAYON_TRANSFORMATION;
		int posX = positionMeteore.getX();
		int posY = positionMeteore.getY();
		
		for (int i = posX - rayon; i <= posX + rayon; i++) {
			for (int j = posY - rayon; j <= posY + rayon; j++) {
				if (carte.estCoordonneeValide(i, j)) {
					Bloc bloc = carte.getBloc(i, j);
					Biome biome = biomeMap.get(bloc);
					if (biome != null) {
						double distance = Math.sqrt(Math.pow(i - posX, 2) + Math.pow(j - posY, 2));
						if (distance <= rayon) {
							biome.setTemperature(100);
						}
					}
				}
			}
		}
	}
	
	private boolean peutAjouterEvenement() {
		return evenements.size() < ConfigurationCreationEvenement.MAX_EVENEMENTS_SIMULTANES;
	}
	
	private void ajouterEvenementAvecContraintes(Evenement evenement) {
		if (evenement == null || !peutAjouterEvenement()) {
			return;
		}

		evenements.add(evenement);
	}
	
	//la fonction est la mais sah elle sert a rien imo 
	@Override
	public void ajouterEvenement()
	{
		for (int i = 0; i < 3 && peutAjouterEvenement(); i++) {
			Evenement evenement = factory.creerEvenementAleatoire();
			ajouterEvenementAvecContraintes(evenement);
		}
	}
	
	public void genererEvenementsDepuisBiomes() {
		if (!peutAjouterEvenement()) {
			return;
		}
		
		for (Biome biome : biomeMap.values()) {
			if (!peutAjouterEvenement()) {
				break;
			}
			
			ArrayList<Evenement> nouveauxEvenements = generateurEvenementVisitor.genererTousEvenements(biome);
			
			if (nouveauxEvenements != null) {
				for (Evenement e : nouveauxEvenements) {
					if (e != null && peutAjouterEvenement()) {
						evenements.add(e);
					}
				}
			}
		}
	}
	
	@Override
	public ArrayList<Biome> getBiomes()
	{
		return new ArrayList<>(biomeMap.values());
	}
	
	@Override
	public ArrayList<Evenement> getEvenements()
	{
		return evenements;
	}
	
	public void clearEvenements() {
		evenements.clear();
	}
	
	@Override
	public ArrayList<Evenement> getDangers() {
		return new ArrayList<>();
	}
	
	public void remplacerBiome(Bloc position, Biome nouveauBiome) {
		if (position == null || nouveauBiome == null) return;
		biomeMap.put(position, nouveauBiome);
	}
	
	@Override
	public void nextRound()
	{
		roundActuel++;
		nbTransformationsDansRound = 0;
		logger.info("=== Debut du round " + roundActuel + " ===");
		
		ajouterEvenement();
		genererEvenementsDepuisBiomes();
		
		bougerEvementMobile();
		transformation();
		
		collecterStats();
		
		logger.info("=== Fin du round " + roundActuel + ", transformations: " + nbTransformationsDansRound + ", evenements: " + evenements.size() + " ===");
	}
	
	private void collecterStats() {
		HashMap<String, Integer> compteBiomes = new HashMap<>();
		for (Biome biome : biomeMap.values()) {
			String type = biome.getClass().getSimpleName();
			Integer count = compteBiomes.get(type);
			if (count == null) count = 0;
			compteBiomes.put(type, count + 1);
		}
		
		HashMap<String, Integer> compteEvenements = new HashMap<>();
		for (Evenement evt : evenements) {
			String type = evt.getClass().getSimpleName();
			Integer count = compteEvenements.get(type);
			if (count == null) count = 0;
			compteEvenements.put(type, count + 1);
		}
		
		double sumTemp = 0, sumHumid = 0, sumPoll = 0, sumPurif = 0;
		for (Biome biome : biomeMap.values()) {
			sumTemp += biome.getTemperature();
			sumHumid += biome.getHumidite();
			sumPoll += biome.getPollution();
			sumPurif += biome.getPurification();
		}
		int nbBiomes = biomeMap.size();
		double[] moyennes = nbBiomes > 0 ? new double[] {
			sumTemp / nbBiomes,
			sumHumid / nbBiomes,
			sumPoll / nbBiomes,
			sumPurif / nbBiomes
		} : new double[] {0, 0, 0, 0};
		
		StatRound stat = new StatRound(roundActuel, compteBiomes, compteEvenements, moyennes, nbTransformationsDansRound, new HashMap<>());
		historique.add(stat);
	}
	
	public ArrayList<StatRound> getHistorique() {
		return historique;
	}
	
	public int getRoundActuel() {
		return roundActuel;
	}
	
	public HashMap<String, Integer> getCompteBiomesActuel() {
		HashMap<String, Integer> compteBiomes = new HashMap<>();
		for (Biome biome : biomeMap.values()) {
			String type = biome.getClass().getSimpleName();
			compteBiomes.put(type, compteBiomes.getOrDefault(type, 0) + 1);
		}
		return compteBiomes;
	}
	
	public HashMap<String, Integer> getCompteEvenementsActuel() {
		HashMap<String, Integer> compteEvenements = new HashMap<>();
		for (Evenement evt : evenements) {
			String type = evt.getClass().getSimpleName();
			compteEvenements.put(type, compteEvenements.getOrDefault(type, 0) + 1);
		}
		return compteEvenements;
	}
	
	public double[] getMoyennesActuelles() {
		if (biomeMap.isEmpty()) return new double[] {0, 0, 0, 0};
		
		double sumTemp = 0, sumHumid = 0, sumPoll = 0, sumPurif = 0;
		for (Biome biome : biomeMap.values()) {
			sumTemp += biome.getTemperature();
			sumHumid += biome.getHumidite();
			sumPoll += biome.getPollution();
			sumPurif += biome.getPurification();
		}
		int nb = biomeMap.size();
		return new double[] {sumTemp / nb, sumHumid / nb, sumPoll / nb, sumPurif / nb};
	}
	
	@Override
	public void transformation()
	{
		for (int i = 0; i < evenements.size(); i++)
		{
			Evenement evenement = evenements.get(i);
			Biome biome = getBiomeByPosition(evenement.getPosition());
			if (biome != null)
			{
				biome.setHumidite(biome.getHumidite() + evenement.getImpactHumidite());
				biome.setPurification(biome.getPurification() + evenement.getImpactPurification());
				biome.setPollution(biome.getPollution() + evenement.getImpactPollution());
				biome.setTemperature(biome.getTemperature() + evenement.getImpactTemperature());

				for (RegleTransformation regle : reglesTransformation)
				{
					Biome nouveauBiome = regle.evaluer(biome);
					if (nouveauBiome != null)
					{
						nbTransformationsDansRound++;
						biomeMap.put(biome.getPosition(), nouveauBiome);
						break;
					}
				}
			}
		}
	}
}

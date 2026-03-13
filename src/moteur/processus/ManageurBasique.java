package moteur.processus;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import config.ConfigurationCreationEvenement;
import moteur.donne.biome.Biome;
import moteur.donne.carte.Bloc;
import moteur.donne.carte.Carte;
import moteur.donne.evenement.Evenement;
import moteur.donne.evenement.statique.Meteore;
import moteur.processus.regle.RegleTransformation;
import moteur.processus.regle.RegleTransformationEvenement;
import moteur.processus.usine.EvenementFactory;
import moteur.processus.visitor.GenerateurEvenementVisitor;
import moteur.processus.visitor.GestionDeplacementVisitor;

public class ManageurBasique implements Manageur 
{
	
	private Carte carte;
	private Map<Bloc, Biome> biomeMap = new HashMap<>();
	private ArrayList<Evenement> evenements = new ArrayList<>();
	private List<RegleTransformation> reglesTransformation = new ArrayList<>();
	private List<RegleTransformationEvenement> reglesTransformationEvenement = new ArrayList<>();
	private EvenementFactory factory;
	private GenerateurEvenementVisitor generateurEvenementVisitor;
	
	public ManageurBasique(Carte carte) 
	{
		this.carte = carte;
		this.factory = new EvenementFactory(carte);
		this.generateurEvenementVisitor = new GenerateurEvenementVisitor();
		
		this.reglesTransformation.add(new moteur.processus.regle.RegleInondation());
		this.reglesTransformation.add(new moteur.processus.regle.RegleDesertification());
		this.reglesTransformation.add(new moteur.processus.regle.RegleGlaciation());
		this.reglesTransformation.add(new moteur.processus.regle.ReglePollutionExtreme());
		this.reglesTransformation.add(new moteur.processus.regle.RegleForestation());
		this.reglesTransformation.add(new moteur.processus.regle.RegleCivilisation());
		this.reglesTransformation.add(new moteur.processus.regle.RegleErosion());
		this.reglesTransformation.add(new moteur.processus.regle.RegleDensification());
		
		this.reglesTransformationEvenement.add(new moteur.processus.regle.ReglePluieEnAcide());
	}
	
	@Override
	public void CarteHasard() 
    {
        biomeMap.clear();

        for (int i = 0; i < carte.getGrandeurX(); i++) 
        {
            for (int j = 0; j < carte.getGrandeurY(); j++) 
            {
                Biome biome = moteur.processus.usine.BiomeFactory.creerBiomeAleatoire(carte.getBloc(i, j));
                biomeMap.put(biome.getPosition(), biome);
            }
        }
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
		
		List<Evenement> expirants = visitor.getEvenementsExpirés();
		
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
	
	//la fonction est la mais sah elle sert a rien imo 
	@Override
	public void ajouterEvenement()
	{
		for (int i = 0; i < ConfigurationCreationEvenement.NB_EVENEMENTS_ALEATOIRES_PAR_ROUND; i++) {
			Evenement evenement = factory.creerEvenementAleatoire();
			if (evenement != null) {
				evenements.add(evenement);
			}
		}
	}
	
	public void genererEvenementsDepuisBiomes() {
		for (Biome biome : biomeMap.values()) {
			Evenement e = biome.accept(generateurEvenementVisitor);
			if (e != null) {
				evenements.add(e);
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
	
	@Override
	public ArrayList<Evenement> getDangers() {
		return new ArrayList<>();
	}
	
	@Override
	public void nextRound()
	{
		ajouterEvenement();
		genererEvenementsDepuisBiomes();
		
		bougerEvementMobile();
		transformation();
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
				// Appliquer les impacts de l'événement sur le biome
				biome.setHumidite(biome.getHumidite() + evenement.getImpactHumidite());
				biome.setPurification(biome.getPurification() + evenement.getImpactPurification());
				biome.setPollution(biome.getPollution() + evenement.getImpactPollution());
				biome.setTemperature(biome.getTemperature() + evenement.getImpactTemperature());

				// Vérifier les règles de transformation de biome
				for (RegleTransformation regle : reglesTransformation)
				{
					Biome nouveauBiome = regle.evaluer(biome);
					if (nouveauBiome != null)
					{
						biomeMap.put(biome.getPosition(), nouveauBiome);
						break;
					}
				}
				
				// Vérifier les règles de transformation d'événement
				for (RegleTransformationEvenement regle : reglesTransformationEvenement)
				{
					Evenement nouvelEvenement = regle.evaluer(evenement, biome);
					if (nouvelEvenement != null)
					{
						evenements.set(i, nouvelEvenement);
						break;
					}
				}
			}
		}
	}
}

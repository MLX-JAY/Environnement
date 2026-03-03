package moteur.processus;

import java.util.ArrayList;
import java.util.List;


import config.ConfigurationEvenement;
import config.GameConfiguration;
import moteur.donne.biome.Biome;
import moteur.donne.biome.Foret;
import moteur.donne.carte.Bloc;
import moteur.donne.carte.Carte;
import moteur.donne.evenement.Evenement;
import moteur.donne.evenement.mobile.Pluie;
import moteur.donne.evenement.mobile.VentFroid;
import moteur.donne.evenement.mobile.VentChaud;
import moteur.donne.evenement.mobile.PluieAcide;
import moteur.donne.evenement.statique.Meteore;
import moteur.donne.evenement.statique.Purification;

public class ManageurBasique implements Manageur 
{
	
	private Carte carte;
	private ArrayList<Biome> biomes=new ArrayList<Biome> ();
	private ArrayList<Evenement> evenements= new ArrayList<Evenement>();
	private List<moteur.processus.regle.RegleTransformation> reglesTransformation = new ArrayList<>();
	private ArrayList<Evenement> dangers = new ArrayList<Evenement>();
	
	private boolean dangerCondition = false; // provisoire
	
	public ManageurBasique(Carte carte) 
	{
		this.carte = carte;
		this.reglesTransformation.add(new moteur.processus.regle.RegleInondation());
		this.reglesTransformation.add(new moteur.processus.regle.RegleDesertification());
		this.reglesTransformation.add(new moteur.processus.regle.RegleGlaciation());
		this.reglesTransformation.add(new moteur.processus.regle.ReglePollutionExtreme());
		this.reglesTransformation.add(new moteur.processus.regle.RegleForestation());
		this.reglesTransformation.add(new moteur.processus.regle.RegleCivilisation());
		this.reglesTransformation.add(new moteur.processus.regle.RegleErosion());
		this.reglesTransformation.add(new moteur.processus.regle.RegleDensification());
	}
	
	public void CarteHasard() 
    {
        biomes.clear();

        for (int i = 0; i < carte.getGrandeurX(); i++) 
        {
            for (int j = 0; j < carte.getGrandeurY(); j++) 
            {
                Biome biome = moteur.processus.usine.BiomeFactory.creerBiomeAleatoire(carte.getBloc(i, j));
                biomes.add(biome);
            }
        }
    }
	
	public void bougerEvementMobile ()
	{
		
		int[][] directions = {
			{0, -1},   // Haut
			{0, 1},    // Bas
			{-1, 0},   // Gauche
			{1, 0},    // Droite
			{-1, -1},  // Haut-gauche
			{1, -1},   // Haut-droite
			{-1, 1},   // Bas-gauche
			{1, 1}     // Bas-droite
		};
		
		List<Evenement> FinEvenement = new ArrayList<Evenement>();

		for (Evenement evenement : evenements) {
			// Vérifier que c'est un événement mobile
			boolean isMobile = (evenement instanceof Pluie || 
							   evenement instanceof VentFroid ||
							   evenement instanceof VentChaud ||
							   evenement instanceof PluieAcide);
			
			if (isMobile) {
				// Si l'animation est terminée, calculer la prochaine position
				if (evenement.isAnimationComplete()) {
					Bloc position = evenement.getPosition();
					if (!carte.estSurBordure(position) && evenement.getDuree()!=0) {
						// Choisir une direction aléatoire
						int directionAleatoire = nombreAuxHasard(0, directions.length - 1);
						int dx = directions[directionAleatoire][0];
						int dy = directions[directionAleatoire][1];
						
						Bloc positionFuture = carte.getBloc(position.getX() + dx, position.getY() + dy);
					
						if (!carte.estSurBordure(positionFuture) && evenement.getDuree()!=0) 
						{
							Bloc newPosition = carte.getBloc(position.getX() + dx, position.getY() + dy);
							evenement.setTargetPosition(newPosition);
							evenement.setDureeRestante(evenement.getDuree()-1);
						}
					}
					else 
					{
						FinEvenement.add(evenement);
					}
				}
			}
			else {
				// Pour les événements statiques, juste mettre à jour la durée
				if (evenement.getDuree() != 0) {
					evenement.setDureeRestante(evenement.getDuree()-1);
				} else {
					FinEvenement.add(evenement);
				}
			}
		}

		for (Evenement evenement : FinEvenement) {
			evenements.remove(evenement);
		}
	}
	
	public void fonctionnementDanger () {
		ArrayList<Evenement> finEvenement = new ArrayList<Evenement>();
		for (Evenement evenement : dangers) {
			if (evenement.getDuree()!=0) {
				evenement.setDureeRestante(evenement.getDuree()-1);
				if (evenement.getDuree() < 10) {
					
				}
			}
			else {
				finEvenement.add(evenement);
			}
		}
		for (Evenement evenement : finEvenement) {
			dangers.remove(evenement);
		}
	}
	
	public void appliquerOndeDeChoc(Meteore m) {
	    int posX = m.getPosition().getX();
	    int posY = m.getPosition().getY();
	    int rayon = 2; // Rayon de l'impact (2 cases autour)

	    // On parcourt un carré autour du centre
	    for (int i = posX - rayon; i <= posX + rayon; i++) {
	        for (int j = posY - rayon; j <= posY + rayon; j++) {
	            Bloc b = carte.getBloc(i, j);
	            // On vérifie si on est bien dans la carte pour éviter les erreurs
	            if (!carte.estSurBordure(b)) {
	                
	                // Calcul de la distance euclidienne pour faire un vrai cercle
	                double distance = Math.sqrt(Math.pow(i - posX, 2) + Math.pow(j - posY, 2));
	                
	                if (distance <= rayon) {
	                    // ON APPLIQUE LES DEGATS
	                    
	                    // Exemple : Transformation en désert/cratère
	                    b.setBiome(new Desert()); 
	                    // On impacte aussi les stats du manager
	                    this.pollution += 5; 
	                }
	            }
	        }
	    }
	}
	
	public void ajouterDanger() {
		Bloc position = carte.getBloc(1, 1);
		Evenement meteore = new Meteore(position, ConfigurationEvenement.METEORE_IMPACT_DUREE, ConfigurationEvenement.METEORE_IMPACT_TEMPERATURE, 
				ConfigurationEvenement.METEORE_IMPACT_HUMIDITE, ConfigurationEvenement.METEORE_IMPACT_POLLUTION, 
				ConfigurationEvenement.METEORE_IMPACT_PURIFICATION);
		dangers.add(meteore);
	}
	
	public void ajouterEvenement ()
	{
		int colonneHasard = nombreAuxHasard(0, GameConfiguration.NOMBRE_COLONNES - 1);
		int ligneHasard= nombreAuxHasard(0, GameConfiguration.NOMBRE_LIGNES-1);
		Bloc position = carte.getBloc(ligneHasard, colonneHasard);
		Evenement evenement = new Pluie(position,ConfigurationEvenement.PLUIE_IMPACT_DUREE, ConfigurationEvenement.PLUIE_IMPACT_TEMPERATURE,
				ConfigurationEvenement.PLUIE_IMPACT_HUMIDITE, ConfigurationEvenement.PLUIE_IMPACT_POLLUTION, ConfigurationEvenement.PLUIE_IMPACT_PURIFICATION);  // PROVISOIRE POUR TEST PLUIE 
		evenements.add(evenement);
		
		colonneHasard = nombreAuxHasard(0, GameConfiguration.NOMBRE_COLONNES - 1);
		ligneHasard= nombreAuxHasard(0, GameConfiguration.NOMBRE_LIGNES-1);
		position = carte.getBloc(ligneHasard, colonneHasard);
		evenement = new VentFroid(position,ConfigurationEvenement.VENTFROID_IMPACT_DUREE, ConfigurationEvenement.VENTFROID_IMPACT_TEMPERATURE,
				ConfigurationEvenement.VENTFROID_IMPACT_HUMIDITE, ConfigurationEvenement.VENTFROID_IMPACT_POLLUTION, ConfigurationEvenement.VENTFROID_IMPACT_PURIFICATION);  // PROVISOIRE POUR TEST
		evenements.add(evenement);
		
		//je fait une Arraylist pour repertorier les foret, comme sa purification part des fôrets
		ArrayList<Foret> forets = new ArrayList<Foret>();
		for (Biome biome : biomes) {
			if (biome instanceof Foret) forets.add((Foret)biome);
		}
		
		int foretChoisi = nombreAuxHasard(0, forets.size()-1);
		
		position = forets.get(foretChoisi).getPosition();
		evenement = new Purification(position,ConfigurationEvenement.PURIFICATION_IMPACT_DUREE, ConfigurationEvenement.PURIFICATION_IMPACT_TEMPERATURE,
				ConfigurationEvenement.PURIFICATION_IMPACT_HUMIDITE, ConfigurationEvenement.PURIFICATION_IMPACT_POLLUTION, ConfigurationEvenement.PURIFICATION_IMPACT_PURIFICATION);  // PROVISOIRE POUR TEST
		evenements.add(evenement);
	}
	public ArrayList<Biome> getBiomes ()
	{
		return biomes;
	}
	public ArrayList<Evenement> getEvenements ()
	{
		return evenements;
	}
	
	public ArrayList<Evenement> getDangers() {
		return dangers;
	}

	private static int nombreAuxHasard(int min, int max) 
	{
		return (int) (Math.random() * (max + 1 - min)) + min;
	}
	
	public void nextRound()
	{
		ajouterEvenement();
		ajouterEvenement();
		ajouterEvenement();
		bougerEvementMobile();
		transformation();
		//proivisoire pour tester le meteore
		if (dangerCondition == false) {
			ajouterDanger();
			dangerCondition = true;
		}
		fonctionnementDanger();
	}
	public void transformation()
	{
		for (int i = 0; i < biomes.size(); i++)
		{
			Biome biome = biomes.get(i);
			for (Evenement evenement : evenements)
			{
				if (biome.getPosition().equals(evenement.getPosition()))
				{
					// Appliquer l'impact de l'evenement
					biome.setHumidite(biome.getHumidite() + evenement.getImpactHumidite());
					biome.setPurification(biome.getPurification() + evenement.getImpactPurification());
					biome.setPollution(biome.getPollution() + evenement.getImpactPollution());
					biome.setTemperature(biome.getTemperature() + evenement.getImpactTemperature());

					// Evaluer toutes les regles de transformation
					for (moteur.processus.regle.RegleTransformation regle : reglesTransformation)
					{
						Biome nouveauBiome = regle.evaluer(biome);
						if (nouveauBiome != null)
						{
							biomes.set(i, nouveauBiome);
							break; // Une seule transformation par tour pour un meme biome
						}
					}
				}
			}
		}
	}
}

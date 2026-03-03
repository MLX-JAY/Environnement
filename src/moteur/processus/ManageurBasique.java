package moteur.processus;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


import config.ConfigurationEvenement;
import config.GameConfiguration;
import moteur.donne.biome.Biome;
import moteur.donne.biome.Foret;
import moteur.donne.carte.Bloc;
import moteur.donne.carte.Carte;
import moteur.donne.evenement.Evenement;
import moteur.donne.evenement.mobile.Pluie;
import moteur.donne.evenement.mobile.VentFroid;
import moteur.donne.evenement.statique.Purification;

public class ManageurBasique implements Manageur 
{
	
	private Carte carte;
	private ArrayList<Biome> biomes=new ArrayList<Biome> ();
	private Map<Bloc, Biome> biomeMap = new HashMap<>();
	private ArrayList<Evenement> evenements= new ArrayList<Evenement>();
	private List<moteur.processus.regle.RegleTransformation> reglesTransformation = new ArrayList<>();
	
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
        biomeMap.clear();

        for (int i = 0; i < carte.getGrandeurX(); i++) 
        {
            for (int j = 0; j < carte.getGrandeurY(); j++) 
            {
                Biome biome = moteur.processus.usine.BiomeFactory.creerBiomeAleatoire(carte.getBloc(i, j));
                biomes.add(biome);
                biomeMap.put(biome.getPosition(), biome);
            }
        }
    }
    
    public Biome getBiomeByPosition(Bloc position) {
        return biomeMap.get(position);
    }
	public void bougerEvementMobile ()
	{
		List<Evenement> FinEvenement = new ArrayList<Evenement>();

		for (Evenement evenement : evenements) {
			Bloc position = evenement.getPosition();
			Bloc positionFuture = carte.getBloc(position.getX()+1, position.getY()+1);
			if (!carte.estSurBordure(positionFuture) && evenement.getDuree()!=0) 
			{
				Bloc newPosition = carte.getBloc(position.getX()+1, position.getY()+1);
				evenement.setPosition(newPosition);
				evenement.setDureeRestante(evenement.getDuree()-1);
			} 
			else 
			{
				FinEvenement.add(evenement);
			}

		}

		for (Evenement evenement : FinEvenement) {
			evenements.remove(evenement);
		}
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
	}
	public void transformation()
	{
		for (Evenement evenement : evenements)
		{
			Biome biome = getBiomeByPosition(evenement.getPosition());
			if (biome != null)
			{
				biome.setHumidite(biome.getHumidite() + evenement.getImpactHumidite());
				biome.setPurification(biome.getPurification() + evenement.getImpactPurification());
				biome.setPollution(biome.getPollution() + evenement.getImpactPollution());
				biome.setTemperature(biome.getTemperature() + evenement.getImpactTemperature());

				for (moteur.processus.regle.RegleTransformation regle : reglesTransformation)
				{
					Biome nouveauBiome = regle.evaluer(biome);
					if (nouveauBiome != null)
					{
						biomeMap.put(biome.getPosition(), nouveauBiome);
						int index = biomes.indexOf(biome);
						if (index >= 0) {
							biomes.set(index, nouveauBiome);
						}
						break;
					}
				}
			}
		}
	}
}

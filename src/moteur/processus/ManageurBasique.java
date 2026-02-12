package moteur.processus;

import java.util.ArrayList;
import java.util.List;

import config.ConfigurationBiome;
import config.ConfigurationEvenement;
import config.GameConfiguration;
import moteur.donne.biome.Biome;
import moteur.donne.biome.Desert;
import moteur.donne.biome.Foret;
import moteur.donne.biome.Mer;
import moteur.donne.biome.Village;
import moteur.donne.carte.Bloc;
import moteur.donne.carte.Carte;
import moteur.donne.evenement.Evenement;
import moteur.donne.evenement.mobile.Pluie;

public class ManageurBasique implements Manageur 
{
	
	private Carte carte;
	private ArrayList<Biome> biomes=new ArrayList<Biome> ();
	private ArrayList<Evenement> evenements= new ArrayList<Evenement>();
	
	public ManageurBasique(Carte carte) 
	{
		this.carte = carte;
	}
	public void CarteHasard() 
    {
        biomes.clear();

        for (int i = 0; i < carte.getGrandeurX(); i++) 
        {
            for (int j = 0; j < carte.getGrandeurY(); j++) 
            {
                int choixHasard = (int) (Math.random() * 4);
                Biome biome;

                switch (choixHasard) 
                {
                    case 0:
                        biome = new Foret(ConfigurationBiome.FORET_TEMP, ConfigurationBiome.FORET_POLLUTION, ConfigurationBiome.FORET_PURIFICATION, ConfigurationBiome.FORET_HUMIDITE, 0, carte.getBloc(i, j));
                        break;
                    case 1:
                        biome = new Desert(ConfigurationBiome.DESERT_TEMP, ConfigurationBiome.DESERT_POLLUTION, ConfigurationBiome.DESERT_PURIFICATION, ConfigurationBiome.DESERT_HUMIDITE, 0, carte.getBloc(i, j));
                        break;
                    case 2:
                        biome = new Mer(ConfigurationBiome.MER_TEMP, ConfigurationBiome.MER_POLLUTION, ConfigurationBiome.MER_PURIFICATION, ConfigurationBiome.MER_HUMIDITE, 0, carte.getBloc(i, j));
                        break;
                    case 3:
                        biome = new Village(ConfigurationBiome.VILLAGE_TEMP, ConfigurationBiome.VILLAGE_POLLUTION, ConfigurationBiome.VILLAGE_PURIFICATION, ConfigurationBiome.VILLAGE_HUMIDITE, 0, carte.getBloc(i, j));
                        break;
                    default:
                        biome = new Foret(ConfigurationBiome.FORET_TEMP, ConfigurationBiome.FORET_POLLUTION, ConfigurationBiome.FORET_PURIFICATION, ConfigurationBiome.FORET_HUMIDITE, 0, carte.getBloc(i, j));
                        break;
                }
                biomes.add(biome);
            }
        }
    }
	public void bougerEvementMobile ()
	{
		List<Evenement> FinEvenement = new ArrayList<Evenement>();

		for (Evenement evenement : evenements) {
			Bloc position = evenement.getPosition();

			if (!carte.estSurBordure(position) && evenement.getDuree()!=0) 
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
		Evenement evenement = new Pluie(position,ConfigurationEvenement.PLUIE_DUREE, ConfigurationEvenement.PLUIE_IMPACT_TEMPERATURE,
				ConfigurationEvenement.PLUIE_IMPACT_HUMIDITE, ConfigurationEvenement.PLUIE_IMPACT_POLLUTION, ConfigurationEvenement.PLUIE_IMPACT_PURIFICATION);  // PROVISOIRE POUR TEST PLUIE 
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
		bougerEvementMobile();
	}
	public void transformation()
	{
		
	}
}

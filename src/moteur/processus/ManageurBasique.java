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
	private List<moteur.processus.regle.RegleTransformation> reglesTransformation = new ArrayList<>();
	
	public ManageurBasique(Carte carte) 
	{
		this.carte = carte;
		this.reglesTransformation.add(new moteur.processus.regle.RegleInondation());
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
		ajouterEvenement();
		ajouterEvenement();
		bougerEvementMobile();
		transformation();
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

					// Evaluer toutes les regles de transformation
					for (moteur.processus.regle.RegleTransformation regle : reglesTransformation)
					{
						Biome nouveauBiome = regle.evaluer(biome);
						if (nouveauBiome != null)
						{
							biomes.set(i, nouveauBiome);
							biome = nouveauBiome; // Mise a jour de la variable locale
							break; // Une seule transformation par tour pour un meme biome
						}
					}
				}
			}
		}
	}
}

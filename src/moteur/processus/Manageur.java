package moteur.processus;

import java.util.ArrayList;

import moteur.donne.biome.Biome;
import moteur.donne.evenement.Evenement;

public interface Manageur 
{
	
	void CarteHasard ();
	ArrayList<Biome> getBiomes();
	void bougerEvementMobile ();
	void ajouterEvenement ();
	void nextRound();
	void transformation();
	ArrayList<Evenement> getEvenements ();
	
}

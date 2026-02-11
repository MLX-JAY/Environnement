package moteur.processus;

import java.util.ArrayList;

import moteur.donne.biome.Biome;

public interface Manageur 
{
	
	void CarteHasard ();
	ArrayList<Biome> getBiomes();
	void bougerEvementMobile ();
	void ajouterEvenement ();
	void nextRound();
	void transformation();
	
}

package moteur.processus;

import java.util.ArrayList;

import moteur.donne.biome.Biome;

public interface Manageur 
{
	
	public void CarteHasard ();
	public ArrayList<Biome> getBiomes();
	public void bougerEvementMobile ();
	public void ajouterEvenement ();
}

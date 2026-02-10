package moteur.processus;

import java.util.ArrayList;

import moteur.donne.biome.Biome;
import moteur.donne.carte.Carte;
import moteur.donne.evenement.mobile.EvenementMobile;
import moteur.donne.evenement.statique.EvenementStatique;

public class ManageurBasique implements Manageur 
{
	
	private Carte carte;
	private ArrayList<Biome> biomes=new ArrayList<Biome> ();
	private EvenementMobile eventMobile = new EvenementMobile();
	private EvenementStatique eventStatique= new EvenementStatique();
	
	public ManageurBasique(Carte carte) {
		this.carte = carte;
	}
	public void CarteHasard ()
	{
		
	}

}

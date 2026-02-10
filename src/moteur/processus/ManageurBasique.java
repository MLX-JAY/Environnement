package moteur.processus;

import java.util.ArrayList;

import moteur.donne.biome.Biome;
import moteur.donne.carte.Carte;
import moteur.donne.evenement.mobile.EvenementMobile;
import moteur.donne.evenement.statique.EvenementStatique;

public class ManageurBasique implements Manageur 
{
	
	private Carte carte;
	private ArrayList<Biome> biomes;
	private EvenementMobile eventMobile;
	private EvenementStatique eventStatique;
	
	

	public ManageurBasique(Carte carte, ArrayList<Biome> biomes, EvenementMobile eventMobile,
			EvenementStatique eventStatique) 
	{
		this.carte = carte;
		this.biomes = biomes;
		this.eventMobile = eventMobile;
		this.eventStatique = eventStatique;
	}
	
	

	public Carte getCarte() 
	{
		return carte;
	}



	public ArrayList<Biome> getBiomes() 
	{
		return biomes;
	}



	public EvenementMobile getEventMobile() 
	{
		return eventMobile;
	}



	public EvenementStatique getEventStatique() 
	{
		return eventStatique;
	}



	@Override
	public void addEvenement() 
	{
		// TODO Auto-generated method stub

	}

	@Override
	public void nextRound() 
	{
		// TODO Auto-generated method stub

	}

	@Override
	public void transformation() 
	{
		// TODO Auto-generated method stub

	}

}

package moteur.donne.evenement.statique;

import moteur.donne.carte.Bloc;

public class Pollution 
{
	
	private boolean evenementActif;
	private Bloc position;
	private int duree;
	
	
	public Pollution(boolean evenementActif, Bloc depart, int duree) 
	{
		this.evenementActif = evenementActif;
		this.position = depart;
		this.duree = duree;
	}


	public boolean isEvenementActif() 
	{
		return evenementActif;
	}


	public Bloc getDepart() 
	{
		return position;
	}


	public int getDuree() 
	{
		return duree;
	}
	
	
	
}

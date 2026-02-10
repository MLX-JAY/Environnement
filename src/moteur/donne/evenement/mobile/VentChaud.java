package moteur.donne.evenement.mobile;

import moteur.donne.carte.Bloc;

public class VentChaud 
{
	
	private boolean evenementActif;
	private Bloc depart;
	private int duree;
	
	
	public VentChaud(boolean evenementActif, Bloc depart, int duree) 
	{
		this.evenementActif = evenementActif;
		this.depart = depart;
		this.duree = duree;
	}


	public boolean isEvenementActif() 
	{
		return evenementActif;
	}


	public Bloc getDepart() 
	{
		return depart;
	}


	public int getDuree() 
	{
		return duree;
	}
	
	
	
}

package moteur.donne.evenement.mobile;

import moteur.donne.carte.Bloc;
import moteur.donne.evenement.Evenement;

public class Pluie extends Evenement
{

	private boolean evenementActif;
	private Bloc depart;
	private double duree;
	
	
	public Pluie(Bloc position, double dureeRestante) {
		super(position, dureeRestante);
	}


	public boolean isEvenementActif() 
	{
		return evenementActif;
	}


	public Bloc getDepart() 
	{
		return depart;
	}


	public double getDuree() 
	{
		return duree;
	}
	
	
	
}

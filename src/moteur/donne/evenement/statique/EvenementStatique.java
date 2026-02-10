package moteur.donne.evenement.statique;

import java.util.ArrayList;

public class EvenementStatique 
{
	
	private ArrayList<Pollution> listPollution = new ArrayList<Pollution>();
	private ArrayList<Purification> listePurification = new ArrayList<Purification>();
	private ArrayList<Seisme> listeSeisme = new ArrayList<Seisme>();
	private ArrayList<Meteore> listeMeteore = new ArrayList<Meteore>();
	
	public ArrayList<Pollution> getListeVentChaud() 
	{
		return listPollution;
	}
	
	public ArrayList<Purification> getListeVentFroid() 
	{
		return listePurification;
	}
	
	public ArrayList<Seisme> getListePluie() 
	{
		return listeSeisme;
	}
	
	public ArrayList<Meteore> getListePluieAcide() 
	{
		return listeMeteore;
	}
	
	
}

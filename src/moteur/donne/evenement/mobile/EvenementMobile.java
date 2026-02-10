package moteur.donne.evenement.mobile;

import java.util.ArrayList;

public class EvenementMobile 
{
	
	private ArrayList<VentChaud> listeVentChaud = new ArrayList<VentChaud>();
	private ArrayList<VentFroid> listeVentFroid = new ArrayList<VentFroid>();
	private ArrayList<Pluie> listePluie = new ArrayList<Pluie>();
	private ArrayList<PluieAcide> listePluieAcide = new ArrayList<PluieAcide>();
	
	public ArrayList<VentChaud> getListeVentChaud() 
	{
		return listeVentChaud;
	}
	
	public ArrayList<VentFroid> getListeVentFroid() 
	{
		return listeVentFroid;
	}
	
	public ArrayList<Pluie> getListePluie() 
	{
		return listePluie;
	}
	
	public ArrayList<PluieAcide> getListePluieAcide() 
	{
		return listePluieAcide;
	}
	
	
}

package moteur.donne.carte;

import java.util.Arrays;

public class Carte 
{
	
	
	private Bloc[][] blocs;
	private int lignes;
	private int collones;
	
	
	public Carte(Bloc[][] blocs, int grandeurX, int grandeurY) 
	{
		this.blocs = blocs;
		this.lignes = grandeurX;
		this.collones = grandeurY;
	}

	public Bloc[][] getBlocs() 
	{
		return blocs;
	}
	
	public int getGrandeurX() 
	{
		return lignes;
	}
	
	public int getGrandeurY() 
	{
		return collones;
	}

	@Override
	public String toString() {
		return "Carte [blocs=" + Arrays.toString(blocs) + ", lignes=" + lignes + ", collones=" + collones + "]";
	}
	
	
	
}

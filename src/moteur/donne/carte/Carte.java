package moteur.donne.carte;

import java.util.Arrays;

public class Carte 
{
	
	
	private Bloc[][] blocs;
	private int grandeurX;
	private int grandeurY;
	
	
	public Carte(Bloc[][] blocs, int grandeurX, int grandeurY) 
	{
		this.blocs = blocs;
		this.grandeurX = grandeurX;
		this.grandeurY = grandeurY;
	}

	public Bloc[][] getBlocs() 
	{
		return blocs;
	}
	
	public int getGrandeurX() 
	{
		return grandeurX;
	}
	
	public int getGrandeurY() 
	{
		return grandeurY;
	}

	@Override
	public String toString() {
		return "Carte [blocs=" + Arrays.toString(blocs) + ", grandeurX=" + grandeurX + ", grandeurY=" + grandeurY + "]";
	}
	
	
	
}

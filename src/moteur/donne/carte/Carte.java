package moteur.donne.carte;

import java.util.Arrays;

public class Carte 
{
	
	
	private Bloc[][] blocs;
	private int lignes;
	private int colonnes;
	
	
	public Carte(int lignes, int colonnes) {
		init(lignes, colonnes);

		for (int i = 0; i < lignes; i++) {
			for (int j = 0; j < colonnes; j++) {
				blocs[i][j] = new Bloc(i, j);
			}
		}
	}
	
	private void init(int lignes, int colonnes) {
		this.lignes = lignes;	
		this.colonnes = colonnes;

		blocs = new Bloc[lignes][colonnes];
		
	
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
		return colonnes;
	}

	@Override
	public String toString() {
		return "Carte [blocs=" + Arrays.toString(blocs) + ", lignes=" + lignes + ", colonnes=" + colonnes + "]";
	}
	
	
	
}

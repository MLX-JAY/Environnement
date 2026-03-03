package moteur.donne.carte;

import java.util.Arrays;

public class Carte 
{
	
	
	private Bloc[][] blocs;
	private int nombreLignes;
	private int nombreColonnes;
	
	
	public Carte(int nombreLignes, int nombreColonnes) {
		init(nombreLignes, nombreColonnes);

		for (int i = 0; i < nombreLignes; i++) {
			for (int j = 0; j < nombreColonnes; j++) {
				blocs[i][j] = new Bloc(i, j);
			}
		}
	}
	public Bloc getBloc(int line, int column) {
		return blocs[line][column];
	}
	private void init(int nombreLignes, int nombreColonnes) {
		this.nombreLignes = nombreLignes;	
		this.nombreColonnes = nombreColonnes;

		blocs = new Bloc[nombreLignes][nombreColonnes];
		
	
	}

	public Bloc[][] getBlocs() 
	{
		return blocs;
	}
	
	public int getGrandeurX() 
	{
		return nombreLignes;
	}
	
	public int getGrandeurY() 
	{
		return nombreColonnes;
	}
	public boolean estEnHaut(Bloc bloc) {
        int ligne = bloc.getX();
        return ligne == 0;
    }

    public boolean estEnBas(Bloc bloc) {
        int ligne = bloc.getX();
        return ligne == nombreLignes - 1; 
    }

    public boolean estAGauche(Bloc bloc) {
        int colonne = bloc.getY();
        return colonne == 0;
    }

    public boolean estADroite(Bloc bloc) {
        int colonne = bloc.getY();
        return colonne == nombreColonnes - 1; 
    }

    public boolean estSurBordure(Bloc bloc) {
        return estEnHaut(bloc) || estEnBas(bloc) || estAGauche(bloc) || estADroite(bloc);
    }
	public boolean estCoordonneeValide(int x, int y) {
        return x >= 0 && x < nombreLignes && y >= 0 && y < nombreColonnes;
    }
	@Override
	public String toString() {
		return "Carte [blocs=" + Arrays.toString(blocs) + ", lignes=" + nombreLignes + ", colonnes=" + nombreColonnes + "]";
	}
	
	
	
}

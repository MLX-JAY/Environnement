

package moteur.processus;

import moteur.donne.carte.Carte;

public class Builder 
{
	
	public static Carte construireCarte() 
	{
		return new Carte(config.GameConfiguration.NOMBRE_LIGNES, config.GameConfiguration.NOMBRE_COLONNES);
	}
	
	public static Manageur initCarte (Carte carte)
	{
		Manageur manageur = new ManageurBasique(carte);
		manageur.CarteHasard();
		return manageur;
	}
	
}

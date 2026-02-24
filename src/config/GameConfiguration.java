package config;


public class GameConfiguration {
	public static final int FENETRE_LARGEUR = 1080;
	public static final int FENETRE_LONGEUR = 1920;
	
	public static final int CARTE_LARGEUR = FENETRE_LARGEUR - 280;  //800
	public static final int CARTE_LONGEUR = FENETRE_LONGEUR - 120;	//1800
	
	public static final int TAILLE_BLOC = 100;
	
	public static final int NOMBRE_LIGNES = CARTE_LONGEUR/ TAILLE_BLOC;
	public static final int NOMBRE_COLONNES = CARTE_LARGEUR / TAILLE_BLOC;
	
	public static final int VITESSE_JEU = 1000;
	
	

}

package config;


public class GameConfiguration {
	public static final int FENETRE_LARGEUR = 1080;
	public static final int FENETRE_LONGEUR = 1920;
	
	public static final int CARTE_LONGEUR = FENETRE_LONGEUR - 320; // = 1600
	public static final int CARTE_LARGEUR = FENETRE_LARGEUR - 180; // = 900
	
	public static final int TAILLE_BLOC = 40; 
	
	public static final int NOMBRE_LIGNES = CARTE_LONGEUR/ TAILLE_BLOC;
	public static final int NOMBRE_COLONNES = CARTE_LARGEUR / TAILLE_BLOC;
	
	public static int VITESSE_JEU = 1000;
	
	

}

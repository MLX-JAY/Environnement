package config;

public class ConfigurationCreationEvenement {

    public static int METEORE_RAYON_TRANSFORMATION = 2;
    
    public static int CHANCE_TERMINAISON_APRES_DUREE_MINIMALE = 3;
    
    public static int MAX_EVENEMENTS_SIMULTANES = 70;
    public static int DUREE_MINIMALE_EVENEMENT = 10;
    public static int NB_EVENEMENTS_ALEATOIRES_PAR_ROUND = 3;
    
    // Probabilités par biome - modifiables
    public static double PROBABILITE_PURIFICATION_PAR_FORET = 0.025;
    public static double PROBABILITE_VENT_CHAUD_PAR_DESERT = 0.025;
    public static double PROBABILITE_VENT_FROID_PAR_BANQUISE = 0.025;
    public static double PROBABILITE_POLLUTION_PAR_VILLE = 0.02;
    public static double PROBABILITE_PLUVIE_PAR_MER = 0.02;
    
    // Nouveaux événements - probabilités
    public static double PROBABILITE_ORAGE_PAR_MER = 0.0125;
    public static double PROBABILITE_GRELE_PAR_BANQUISE = 0.025;
    public static double PROBABILITE_TORNADE_PAR_DESERT = 0.015;
    public static double PROBABILITE_PLUIEBENITE_PAR_FORET = 0.025;
    public static double PROBABILITE_ZEPHYR_PAR_DESERT = 0.025;
    public static double PROBABILITE_TONNERRE_PAR_MER = 0.0075;
    public static double PROBABILITE_SMOG_PAR_VILLE = 0.015;
    public static double PROBABILITE_NUAGETOXIQUE_PAR_VILLE = 0.015;
    public static double PROBABILITE_POLLUTION_PAR_VILLAGE = 0.02;
    
    // Formations pour chaque événement
    public static Formation FORMATION_PLUIE = Formation.CARRE_2X2;
    public static Formation FORMATION_PLUIE_ACIDE = Formation.CARRE_2X2;
    public static Formation FORMATION_VENT_FROID = Formation.LIGNE_HORIZONTALE;
    public static Formation FORMATION_VENT_CHAUD = Formation.LIGNE_HORIZONTALE;
    public static Formation FORMATION_POLLUTION = Formation.UNIQUE;
    public static Formation FORMATION_PURIFICATION = Formation.UNIQUE;
    public static Formation FORMATION_METEO = Formation.UNIQUE;
    public static Formation FORMATION_ORAGE = Formation.CARRE_2X2;
    public static Formation FORMATION_GRELE = Formation.LIGNE_VERTICALE;
    public static Formation FORMATION_TORNADE = Formation.CROIX;
    public static Formation FORMATION_PLUIE_BENITE = Formation.CARRE_2X2;
    public static Formation FORMATION_ZEPHYR = Formation.DIAGONALE;
    public static Formation FORMATION_TONNERRE = Formation.T;
    public static Formation FORMATION_SMOG = Formation.CERCLE;
    public static Formation FORMATION_NUAGE_TOXIQUE = Formation.CERCLE;
    
    public static void reinitialiser() {
        PROBABILITE_PURIFICATION_PAR_FORET = 0.025;
        PROBABILITE_VENT_CHAUD_PAR_DESERT = 0.025;
        PROBABILITE_VENT_FROID_PAR_BANQUISE = 0.025;
        PROBABILITE_POLLUTION_PAR_VILLE = 0.02;
        PROBABILITE_PLUVIE_PAR_MER = 0.02;
        PROBABILITE_ORAGE_PAR_MER = 0.0125;
        PROBABILITE_GRELE_PAR_BANQUISE = 0.025;
        PROBABILITE_TORNADE_PAR_DESERT = 0.015;
        PROBABILITE_PLUIEBENITE_PAR_FORET = 0.025;
        PROBABILITE_ZEPHYR_PAR_DESERT = 0.025;
        PROBABILITE_TONNERRE_PAR_MER = 0.0075;
        PROBABILITE_SMOG_PAR_VILLE = 0.015;
        PROBABILITE_NUAGETOXIQUE_PAR_VILLE = 0.015;
        PROBABILITE_POLLUTION_PAR_VILLAGE = 0.02;
    }
}
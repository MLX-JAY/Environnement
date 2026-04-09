package config;

public class ConfigurationCreationEvenement {

    public static final int METEORE_RAYON_TRANSFORMATION = 2;
    
    public static final int CHANCE_TERMINAISON_APRES_DUREE_MINIMALE = 3;
    
    public static final int MAX_EVENEMENTS_SIMULTANES = 150;
    public static final int DUREE_MINIMALE_EVENEMENT = 10;
    public static final int NB_EVENEMENTS_ALEATOIRES_PAR_ROUND = 3;
    
    // Probabilités réduites de 75% (50% puis encore 50%)
    public static final double PROBABILITE_PURIFICATION_PAR_FORET = 0.025;
    public static final double PROBABILITE_VENT_CHAUD_PAR_DESERT = 0.025;
    public static final double PROBABILITE_VENT_FROID_PAR_BANQUISE = 0.025;
    public static final double PROBABILITE_POLLUTION_PAR_VILLE = 0.02;
    public static final double PROBABILITE_PLUVIE_PAR_MER = 0.02;
    
    // Nouveaux événements - probabilités
    public static final double PROBABILITE_ORAGE_PAR_MER = 0.0125;
    public static final double PROBABILITE_GRELE_PAR_BANQUISE = 0.025;
    public static final double PROBABILITE_TORNADE_PAR_DESERT = 0.015;
    public static final double PROBABILITE_PLUIEBENITE_PAR_FORET = 0.025;
    public static final double PROBABILITE_ZEPHYR_PAR_DESERT = 0.025;
    public static final double PROBABILITE_TONNERRE_PAR_MER = 0.0075;
    public static final double PROBABILITE_SMOG_PAR_VILLE = 0.015;
    public static final double PROBABILITE_NUAGETOXIQUE_PAR_VILLE = 0.015;
    public static final double PROBABILITE_POLLUTION_PAR_VILLAGE = 0.02;
    
    // Formations pour chaque événement
    public static final Formation FORMATION_PLUIE = Formation.CARRE_2X2;
    public static final Formation FORMATION_PLUIE_ACIDE = Formation.CARRE_2X2;
    public static final Formation FORMATION_VENT_FROID = Formation.LIGNE_HORIZONTALE;
    public static final Formation FORMATION_VENT_CHAUD = Formation.LIGNE_HORIZONTALE;
    public static final Formation FORMATION_POLLUTION = Formation.UNIQUE;
    public static final Formation FORMATION_PURIFICATION = Formation.UNIQUE;
    public static final Formation FORMATION_METEO = Formation.UNIQUE;
    public static final Formation FORMATION_ORAGE = Formation.CARRE_2X2;
    public static final Formation FORMATION_GRELE = Formation.LIGNE_VERTICALE;
    public static final Formation FORMATION_TORNADE = Formation.CROIX;
    public static final Formation FORMATION_PLUIE_BENITE = Formation.CARRE_2X2;
    public static final Formation FORMATION_ZEPHYR = Formation.DIAGONALE;
    public static final Formation FORMATION_TONNERRE = Formation.T;
    public static final Formation FORMATION_SMOG = Formation.CERCLE;
    public static final Formation FORMATION_NUAGE_TOXIQUE = Formation.CERCLE;
    
}
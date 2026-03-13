package config;

public class ConfigurationDirection {
    
    public static final int[][] DIRECTIONS = {
        {0, -1},   // Haut
        {0, 1},    // Bas
        {-1, 0},   // Gauche
        {1, 0},    // Droite
        {-1, -1},  // Haut-gauche
        {1, -1},   // Haut-droite
        {-1, 1},   // Bas-gauche
        {1, 1}     // Bas-droite
    };
    
}

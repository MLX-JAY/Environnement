package moteur.processus.usine;

import config.ConfigurationBiome;
import moteur.donne.biome.Biome;
import moteur.donne.biome.Desert;
import moteur.donne.biome.Foret;
import moteur.donne.biome.Mer;
import moteur.donne.biome.Village;
import moteur.donne.carte.Bloc;

public class BiomeFactory {
    
    public static Biome creerBiomeAleatoire(Bloc bloc) {
        int choixHasard = (int) (Math.random() * 4);
        
        switch (choixHasard) {
            case 0:
                return new Foret(ConfigurationBiome.FORET_TEMP, ConfigurationBiome.FORET_POLLUTION, ConfigurationBiome.FORET_PURIFICATION, ConfigurationBiome.FORET_HUMIDITE, 0, bloc);
            case 1:
                return new Desert(ConfigurationBiome.DESERT_TEMP, ConfigurationBiome.DESERT_POLLUTION, ConfigurationBiome.DESERT_PURIFICATION, ConfigurationBiome.DESERT_HUMIDITE, 0, bloc);
            case 2:
                return new Mer(ConfigurationBiome.MER_TEMP, ConfigurationBiome.MER_POLLUTION, ConfigurationBiome.MER_PURIFICATION, ConfigurationBiome.MER_HUMIDITE, 0, bloc);
            case 3:
                return new Village(ConfigurationBiome.VILLAGE_TEMP, ConfigurationBiome.VILLAGE_POLLUTION, ConfigurationBiome.VILLAGE_PURIFICATION, ConfigurationBiome.VILLAGE_HUMIDITE, 0, bloc);
            default:
                return new Foret(ConfigurationBiome.FORET_TEMP, ConfigurationBiome.FORET_POLLUTION, ConfigurationBiome.FORET_PURIFICATION, ConfigurationBiome.FORET_HUMIDITE, 0, bloc);
        }
    }
}

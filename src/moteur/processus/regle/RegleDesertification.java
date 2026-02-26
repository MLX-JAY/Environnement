package moteur.processus.regle;

import config.ConfigurationBiome;
import config.ConfigurationTransformation;
import moteur.donne.biome.Biome;
import moteur.donne.biome.Desert;

public class RegleDesertification implements RegleTransformation {

    @Override
    public Biome evaluer(Biome biome) {
        boolean conditionHumidite = biome.getHumidite() < ConfigurationTransformation.DESERTIFICATION_HUMIDITE_MIN || biome.getHumidite() >= ConfigurationTransformation.DESERTIFICATION_HUMIDITE_MIN_2;
        if (biome.getTemperature() > ConfigurationTransformation.DESERTIFICATION_TEMP_MIN 
            && conditionHumidite 
            && biome.getPollution() < ConfigurationTransformation.DESERTIFICATION_POLLUTION_MAX 
            && !(biome instanceof Desert)) {
            return new Desert(ConfigurationBiome.DESERT_TEMP, ConfigurationBiome.DESERT_POLLUTION,
                    ConfigurationBiome.DESERT_PURIFICATION, ConfigurationBiome.DESERT_HUMIDITE,
                    0, biome.getPosition());
        }
        return null;
    }
}

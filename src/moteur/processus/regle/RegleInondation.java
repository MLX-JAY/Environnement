package moteur.processus.regle;

import config.ConfigurationBiome;
import config.ConfigurationTransformation;
import moteur.donne.biome.Biome;
import moteur.donne.biome.Mer;

public class RegleInondation implements RegleTransformation {

    @Override
    public Biome evaluer(Biome biome) {
        if (biome.getTemperature() > ConfigurationTransformation.INONDATION_TEMP_MIN 
            && biome.getHumidite() >= ConfigurationTransformation.INONDATION_HUMIDITE_MIN 
            && biome.getPollution() <= ConfigurationTransformation.INONDATION_POLLUTION_MAX 
            && !(biome instanceof Mer)) {
            return new Mer(ConfigurationBiome.MER_TEMP, ConfigurationBiome.MER_POLLUTION, 
                           ConfigurationBiome.MER_PURIFICATION, ConfigurationBiome.MER_HUMIDITE, 
                           0, biome.getPosition());
        }
        return null;
    }
}
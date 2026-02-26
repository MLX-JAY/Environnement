package moteur.processus.regle;

import config.ConfigurationBiome;
import config.ConfigurationTransformation;
import moteur.donne.biome.Biome;
import moteur.donne.biome.Montagne;

public class RegleErosion implements RegleTransformation {

    @Override
    public Biome evaluer(Biome biome) {
        if (biome.getHumidite() < ConfigurationTransformation.EROSION_HUMIDITE_MAX 
            && biome.getPollution() <= ConfigurationTransformation.EROSION_POLLUTION_MAX
            && biome.getPurification() >= ConfigurationTransformation.EROSION_PURIFICATION_MIN
            && !(biome instanceof Montagne)) {
            return new Montagne(ConfigurationBiome.MONTAGNE_TEMP, ConfigurationBiome.MONTAGNE_POLLUTION,
                    ConfigurationBiome.MONTAGNE_PURIFICATION, ConfigurationBiome.MONTAGNE_HUMIDITE,
                    0, biome.getPosition());
        }
        return null;
    }
}

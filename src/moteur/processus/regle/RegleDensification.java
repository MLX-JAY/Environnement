package moteur.processus.regle;

import config.ConfigurationBiome;
import config.ConfigurationTransformation;
import moteur.donne.biome.Biome;
import moteur.donne.biome.Village;
import moteur.donne.biome.Ville;

public class RegleDensification implements RegleTransformation {

    @Override
    public Biome evaluer(Biome biome) {
        if (biome.getTemperature() >= ConfigurationTransformation.DENSIFICATION_TEMP_MIN && biome.getTemperature() <= ConfigurationTransformation.DENSIFICATION_TEMP_MAX
            && biome.getHumidite() >= ConfigurationTransformation.DENSIFICATION_HUMIDITE_MIN && biome.getHumidite() <= ConfigurationTransformation.DENSIFICATION_HUMIDITE_MAX
            && biome.getPollution() >= ConfigurationTransformation.DENSIFICATION_POLLUTION_MIN && biome.getPollution() <= ConfigurationTransformation.DENSIFICATION_POLLUTION_MAX
            && biome.getPurification() >= ConfigurationTransformation.DENSIFICATION_PURIFICATION_MIN
            && !(biome instanceof Ville)
            && (biome instanceof Village)) {
            return new Ville(ConfigurationBiome.VILLE_TEMP, ConfigurationBiome.VILLE_POLLUTION,
                    ConfigurationBiome.VILLE_PURIFICATION, ConfigurationBiome.VILLE_HUMIDITE,
                    0, biome.getPosition());
        }
        return null;
    }
}

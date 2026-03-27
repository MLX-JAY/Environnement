package moteur.processus.regle;

import config.ConfigurationBiome;
import config.ConfigurationTransformation;
import moteur.donne.biome.Biome;
import moteur.donne.biome.Foret;
import moteur.donne.biome.Montagne;

public class RegleForestation implements RegleTransformation {

    @Override
    public Biome evaluer(Biome biome) {
        if (biome.getTemperature() >= ConfigurationTransformation.FORESTATION_TEMP_MIN && biome.getTemperature() <= ConfigurationTransformation.FORESTATION_TEMP_MAX
            && biome.getHumidite() >= ConfigurationTransformation.FORESTATION_HUMIDITE_MIN && biome.getHumidite() <= ConfigurationTransformation.FORESTATION_HUMIDITE_MAX
            && biome.getPollution() < ConfigurationTransformation.FORESTATION_POLLUTION_MAX
            && biome.getPurification() >= ConfigurationTransformation.FORESTATION_PURIFICATION_MIN
            && !(biome instanceof Foret)
            && !(biome instanceof Montagne)) {
            return new Foret(ConfigurationBiome.FORET_TEMP, ConfigurationBiome.FORET_POLLUTION,
                    ConfigurationBiome.FORET_PURIFICATION, ConfigurationBiome.FORET_HUMIDITE,
                    0, biome.getPosition());
        }
        return null;
    }
}

package moteur.processus.regle;

import config.ConfigurationBiome;
import config.ConfigurationTransformation;
import moteur.donne.biome.Biome;
import moteur.donne.biome.Foret;
import moteur.donne.biome.Montagne;
import org.apache.log4j.Logger;
import util.LoggerUtility;

public class RegleForestation implements RegleTransformation {

    private static final Logger logger = LoggerUtility.getLogger(RegleForestation.class);

    @Override
    public Biome evaluer(Biome biome) {
        logger.debug("[Evaluation] RegleForestation pour " + biome.getClass().getSimpleName() + 
                    " en (" + biome.getPosition().getX() + "," + biome.getPosition().getY() + ")");
        
        if (biome.getTemperature() >= ConfigurationTransformation.FORESTATION_TEMP_MIN && biome.getTemperature() <= ConfigurationTransformation.FORESTATION_TEMP_MAX
            && biome.getHumidite() >= ConfigurationTransformation.FORESTATION_HUMIDITE_MIN && biome.getHumidite() <= ConfigurationTransformation.FORESTATION_HUMIDITE_MAX
            && biome.getPollution() < ConfigurationTransformation.FORESTATION_POLLUTION_MAX
            && biome.getPurification() >= ConfigurationTransformation.FORESTATION_PURIFICATION_MIN
            && !(biome instanceof Foret)
            && !(biome instanceof Montagne)) {
            
            logger.info("[Transformation] " + biome.getClass().getSimpleName() + "(" + 
                       biome.getPosition().getX() + "," + biome.getPosition().getY() + ") -> Foret");
            
            return new Foret(ConfigurationBiome.FORET_TEMP, ConfigurationBiome.FORET_POLLUTION,
                    ConfigurationBiome.FORET_PURIFICATION, ConfigurationBiome.FORET_HUMIDITE,
                    0, biome.getPosition());
        }
        logger.debug("[NonApplicable] RegleForestation");
        return null;
    }
}

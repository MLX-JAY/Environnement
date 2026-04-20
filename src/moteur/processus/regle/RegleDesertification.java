package moteur.processus.regle;

import config.ConfigurationBiome;
import config.ConfigurationTransformation;
import moteur.donne.biome.Biome;
import moteur.donne.biome.Desert;
import moteur.donne.biome.Montagne;
import org.apache.log4j.Logger;
import util.LoggerUtility;

public class RegleDesertification implements RegleTransformation {

    private static final Logger logger = LoggerUtility.getLogger(RegleDesertification.class);

    @Override
    public Biome evaluer(Biome biome) {
        logger.debug("[Evaluation] RegleDesertification pour " + biome.getClass().getSimpleName() + 
                    " en (" + biome.getPosition().getX() + "," + biome.getPosition().getY() + ")");
        
        boolean conditionHumidite = biome.getHumidite() < ConfigurationTransformation.DESERTIFICATION_HUMIDITE_MIN || biome.getHumidite() >= ConfigurationTransformation.DESERTIFICATION_HUMIDITE_MIN_2;
        if (biome.getTemperature() > ConfigurationTransformation.DESERTIFICATION_TEMP_MIN 
            && conditionHumidite 
            && biome.getPollution() < ConfigurationTransformation.DESERTIFICATION_POLLUTION_MAX 
            && !(biome instanceof Desert)
            && !(biome instanceof Montagne)) {
            
            logger.info("[Transformation] " + biome.getClass().getSimpleName() + "(" + 
                       biome.getPosition().getX() + "," + biome.getPosition().getY() + ") -> Desert");
            
            return new Desert(ConfigurationBiome.DESERT_TEMP, ConfigurationBiome.DESERT_POLLUTION,
                    ConfigurationBiome.DESERT_PURIFICATION, ConfigurationBiome.DESERT_HUMIDITE,
                    0, biome.getPosition());
        }
        logger.debug("[NonApplicable] RegleDesertification");
        return null;
    }
}

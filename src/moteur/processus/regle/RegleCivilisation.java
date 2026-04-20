package moteur.processus.regle;

import config.ConfigurationBiome;
import config.ConfigurationTransformation;
import moteur.donne.biome.Biome;
import moteur.donne.biome.Village;
import moteur.donne.biome.Montagne;
import org.apache.log4j.Logger;
import util.LoggerUtility;

public class RegleCivilisation implements RegleTransformation {

    private static final Logger logger = LoggerUtility.getLogger(RegleCivilisation.class);

    @Override
    public Biome evaluer(Biome biome) {
        logger.debug("[Evaluation] RegleCivilisation pour " + biome.getClass().getSimpleName() + 
                    " en (" + biome.getPosition().getX() + "," + biome.getPosition().getY() + ")");
        
        if (biome.getTemperature() >= ConfigurationTransformation.CIVILISATION_TEMP_MIN && biome.getTemperature() <= ConfigurationTransformation.CIVILISATION_TEMP_MAX
            && biome.getHumidite() >= ConfigurationTransformation.CIVILISATION_HUMIDITE_MIN && biome.getHumidite() <= ConfigurationTransformation.CIVILISATION_HUMIDITE_MAX
            && biome.getPollution() <= ConfigurationTransformation.CIVILISATION_POLLUTION_MAX
            && biome.getPurification() >= ConfigurationTransformation.CIVILISATION_PURIFICATION_MIN
            && !(biome instanceof Village)
            && !(biome instanceof Montagne)) {
            
            logger.info("[Transformation] " + biome.getClass().getSimpleName() + "(" + 
                       biome.getPosition().getX() + "," + biome.getPosition().getY() + ") -> Village");
            
            return new Village(ConfigurationBiome.VILLAGE_TEMP, ConfigurationBiome.VILLAGE_POLLUTION,
                    ConfigurationBiome.VILLAGE_PURIFICATION, ConfigurationBiome.VILLAGE_HUMIDITE,
                    0, biome.getPosition());
        }
        logger.debug("[NonApplicable] RegleCivilisation");
        return null;
    }
}

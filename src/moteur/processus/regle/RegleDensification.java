package moteur.processus.regle;

import config.ConfigurationBiome;
import config.ConfigurationTransformation;
import moteur.donne.biome.Biome;
import moteur.donne.biome.Village;
import moteur.donne.biome.Ville;
import org.apache.log4j.Logger;
import util.LoggerUtility;

public class RegleDensification implements RegleTransformation {

    private static final Logger logger = LoggerUtility.getLogger(RegleDensification.class);

    @Override
    public Biome evaluer(Biome biome) {
        logger.debug("[Evaluation] RegleDensification pour " + biome.getClass().getSimpleName() + 
                    " en (" + biome.getPosition().getX() + "," + biome.getPosition().getY() + ")");
        
        if (biome.getTemperature() >= ConfigurationTransformation.DENSIFICATION_TEMP_MIN && biome.getTemperature() <= ConfigurationTransformation.DENSIFICATION_TEMP_MAX
            && biome.getHumidite() >= ConfigurationTransformation.DENSIFICATION_HUMIDITE_MIN && biome.getHumidite() <= ConfigurationTransformation.DENSIFICATION_HUMIDITE_MAX
            && biome.getPollution() >= ConfigurationTransformation.DENSIFICATION_POLLUTION_MIN && biome.getPollution() <= ConfigurationTransformation.DENSIFICATION_POLLUTION_MAX
            && biome.getPurification() >= ConfigurationTransformation.DENSIFICATION_PURIFICATION_MIN
            && !(biome instanceof Ville)
            && (biome instanceof Village)) {
            
            logger.info("[Transformation] " + biome.getClass().getSimpleName() + "(" + 
                       biome.getPosition().getX() + "," + biome.getPosition().getY() + ") -> Ville");
            
            return new Ville(ConfigurationBiome.VILLE_TEMP, ConfigurationBiome.VILLE_POLLUTION,
                    ConfigurationBiome.VILLE_PURIFICATION, ConfigurationBiome.VILLE_HUMIDITE,
                    0, biome.getPosition());
        }
        logger.debug("[NonApplicable] RegleDensification");
        return null;
    }
}

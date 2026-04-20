package moteur.processus.regle;

import config.ConfigurationBiome;
import config.ConfigurationTransformation;
import moteur.donne.biome.Biome;
import moteur.donne.biome.Mer;
import moteur.donne.biome.Montagne;
import org.apache.log4j.Logger;
import util.LoggerUtility;

public class RegleInondation implements RegleTransformation {

    private static final Logger logger = LoggerUtility.getLogger(RegleInondation.class);

    @Override
    public Biome evaluer(Biome biome) {
        logger.debug("[Evaluation] RegleInondation pour " + biome.getClass().getSimpleName() + 
                    " en (" + biome.getPosition().getX() + "," + biome.getPosition().getY() + ")");
        
        if (biome.getTemperature() > ConfigurationTransformation.INONDATION_TEMP_MIN 
            && biome.getHumidite() >= ConfigurationTransformation.INONDATION_HUMIDITE_MIN 
            && biome.getPollution() <= ConfigurationTransformation.INONDATION_POLLUTION_MAX 
            && !(biome instanceof Mer)
            && !(biome instanceof Montagne)) {
            
            logger.info("[Transformation] " + biome.getClass().getSimpleName() + "(" + 
                       biome.getPosition().getX() + "," + biome.getPosition().getY() + ") -> Mer");
            
            return new Mer(ConfigurationBiome.MER_TEMP, ConfigurationBiome.MER_POLLUTION, 
                           ConfigurationBiome.MER_PURIFICATION, ConfigurationBiome.MER_HUMIDITE, 
                           0, biome.getPosition());
        }
        logger.debug("[NonApplicable] RegleInondation");
        return null;
    }
}
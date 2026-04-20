package moteur.processus.regle;

import config.ConfigurationBiome;
import config.ConfigurationTransformation;
import moteur.donne.biome.Biome;
import moteur.donne.biome.Banquise;
import moteur.donne.biome.Mer;
import org.apache.log4j.Logger;
import util.LoggerUtility;

public class RegleGlaciation implements RegleTransformation {

    private static final Logger logger = LoggerUtility.getLogger(RegleGlaciation.class);

    @Override
    public Biome evaluer(Biome biome) {
        logger.debug("[Evaluation] RegleGlaciation pour " + biome.getClass().getSimpleName() + 
                    " en (" + biome.getPosition().getX() + "," + biome.getPosition().getY() + ")");
        
        if (biome.getTemperature() <= ConfigurationTransformation.GLACIATION_TEMP_MAX 
            && biome.getHumidite() > ConfigurationTransformation.GLACIATION_HUMIDITE_MIN 
            && biome.getPollution() <= ConfigurationTransformation.GLACIATION_POLLUTION_MAX 
            && !(biome instanceof Banquise)
            && (biome instanceof Mer)) {
            
            logger.info("[Transformation] " + biome.getClass().getSimpleName() + "(" + 
                       biome.getPosition().getX() + "," + biome.getPosition().getY() + ") -> Banquise");
            
            return new Banquise(ConfigurationBiome.BANQUISE_TEMP, ConfigurationBiome.BANQUISE_POLLUTION,
                    ConfigurationBiome.BANQUISE_PURIFICATION, ConfigurationBiome.BANQUISE_HUMIDITE,
                    0, biome.getPosition());
        }
        logger.debug("[NonApplicable] RegleGlaciation");
        return null;
    }
}

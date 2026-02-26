package moteur.processus.regle;

import config.ConfigurationBiome;
import config.ConfigurationTransformation;
import moteur.donne.biome.Biome;
import moteur.donne.biome.Banquise;
import moteur.donne.biome.Mer;

public class RegleGlaciation implements RegleTransformation {

    @Override
    public Biome evaluer(Biome biome) {
        if (biome.getTemperature() <= ConfigurationTransformation.GLACIATION_TEMP_MAX 
            && biome.getHumidite() > ConfigurationTransformation.GLACIATION_HUMIDITE_MIN 
            && biome.getPollution() <= ConfigurationTransformation.GLACIATION_POLLUTION_MAX 
            && !(biome instanceof Banquise)
            && (biome instanceof Mer)) {
            return new Banquise(ConfigurationBiome.BANQUISE_TEMP, ConfigurationBiome.BANQUISE_POLLUTION,
                    ConfigurationBiome.BANQUISE_PURIFICATION, ConfigurationBiome.BANQUISE_HUMIDITE,
                    0, biome.getPosition());
        }
        return null;
    }
}

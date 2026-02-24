package moteur.processus.regle;

import config.ConfigurationBiome;
import moteur.donne.biome.Biome;
import moteur.donne.biome.Mer;

public class RegleInondation implements RegleTransformation {

    @Override
    public Biome evaluer(Biome biome) {
        // Règle : Si l'humidité atteint 100 et que ce n'est pas déjà une Mer, ça devient une Mer
        if (biome.getHumidite() >= 100 && !(biome instanceof Mer)) {
            return new Mer(ConfigurationBiome.MER_TEMP, ConfigurationBiome.MER_POLLUTION, 
                           ConfigurationBiome.MER_PURIFICATION, ConfigurationBiome.MER_HUMIDITE, 
                           0, biome.getPosition());
        }
        return null;
    }
}
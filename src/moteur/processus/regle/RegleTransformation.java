package moteur.processus.regle;

import moteur.donne.biome.Biome;

public interface RegleTransformation {
    /**
     * Evalue si un biome doit se transformer selon cette règle.
     * @param biome Le biome actuel
     * @return Le nouveau biome s'il y a eu transformation, sinon null
     */
    Biome evaluer(Biome biome);
}
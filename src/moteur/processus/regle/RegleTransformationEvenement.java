package moteur.processus.regle;

import moteur.donne.biome.Biome;
import moteur.donne.evenement.Evenement;

public interface RegleTransformationEvenement {
    /**
     * Evalue si un événement doit se transformer selon cette règle.
     * @param evenement L'événement actuel
     * @param biome Le biome sur lequel l'événement agit
     * @return Le nouvel événement s'il y a eu transformation, sinon null
     */
    Evenement evaluer(Evenement evenement, Biome biome);
}

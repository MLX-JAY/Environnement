package moteur.processus.regle;

import config.ConfigurationEvenement;
import config.ConfigurationTransformationEvenement;
import moteur.donne.biome.Biome;
import moteur.donne.evenement.Evenement;
import moteur.donne.evenement.mobile.GroupePluie;
import moteur.donne.evenement.mobile.GroupePluieAcide;
import moteur.donne.evenement.mobile.Pluie;
import moteur.donne.evenement.mobile.PluieAcide;

public class ReglePluieEnAcide implements RegleTransformationEvenement {

    @Override
    public Evenement evaluer(Evenement evenement, Biome biome) {
        if (!evenement.isPluie()) {
            return null;
        }
        
        if (biome.getPollution() < ConfigurationTransformationEvenement.PLUIE_ACIDE_POLLUTION_MIN) {
            return null;
        }
        
        if (evenement instanceof GroupePluie) {
            return new GroupePluieAcide(
                evenement.getPosition(),
                ConfigurationEvenement.PLUIEACIDE_IMPACT_DUREE,
                ConfigurationEvenement.PLUIEACIDE_IMPACT_TEMPERATURE,
                ConfigurationEvenement.PLUIEACIDE_IMPACT_HUMIDITE,
                ConfigurationEvenement.PLUIEACIDE_IMPACT_POLLUTION,
                ConfigurationEvenement.PLUIEACIDE_IMPACT_PURIFICATION
            );
        }
        
        if (evenement instanceof Pluie) {
            return new PluieAcide(
                evenement.getPosition(),
                ConfigurationEvenement.PLUIEACIDE_IMPACT_DUREE,
                ConfigurationEvenement.PLUIEACIDE_IMPACT_TEMPERATURE,
                ConfigurationEvenement.PLUIEACIDE_IMPACT_HUMIDITE,
                ConfigurationEvenement.PLUIEACIDE_IMPACT_POLLUTION,
                ConfigurationEvenement.PLUIEACIDE_IMPACT_PURIFICATION
            );
        }
        
        return null;
    }
}

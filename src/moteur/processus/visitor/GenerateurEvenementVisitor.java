package moteur.processus.visitor;

import java.util.Random;

import config.ConfigurationCreationEvenement;
import config.ConfigurationEvenement;
import moteur.donne.biome.Banquise;
import moteur.donne.biome.Desert;
import moteur.donne.biome.Foret;
import moteur.donne.biome.Mer;
import moteur.donne.biome.Montagne;
import moteur.donne.biome.Ville;
import moteur.donne.biome.Village;
import moteur.donne.carte.Bloc;
import moteur.donne.evenement.Evenement;
import moteur.donne.evenement.mobile.GroupePluie;
import moteur.donne.evenement.mobile.Pollution;
import moteur.donne.evenement.mobile.Purification;
import moteur.donne.evenement.mobile.VentChaud;
import moteur.donne.evenement.mobile.VentFroid;

public class GenerateurEvenementVisitor implements BiomeVisitor {
    
    private final Random random = new Random();
    
    @Override
    public Evenement visit(Foret foret) {
        if (random.nextDouble() < ConfigurationCreationEvenement.PROBABILITE_PURIFICATION_PAR_FORET) {
            Bloc position = foret.getPosition();
            return new Purification(
                position,
                ConfigurationEvenement.PURIFICATION_DUREE,
                ConfigurationEvenement.PURIFICATION_IMPACT_TEMPERATURE,
                ConfigurationEvenement.PURIFICATION_IMPACT_HUMIDITE,
                ConfigurationEvenement.PURIFICATION_IMPACT_POLLUTION,
                ConfigurationEvenement.PURIFICATION_IMPACT_PURIFICATION
            );
        }
        return null;
    }
    
    @Override
    public Evenement visit(Desert desert) {
        if (random.nextDouble() < ConfigurationCreationEvenement.PROBABILITE_VENT_CHAUD_PAR_DESERT) {
            Bloc position = desert.getPosition();
            return new VentChaud(
                position,
                ConfigurationEvenement.VENT_CHAUD_DUREE,
                ConfigurationEvenement.VENTFROID_IMPACT_TEMPERATURE,
                ConfigurationEvenement.VENTFROID_IMPACT_HUMIDITE,
                ConfigurationEvenement.VENTFROID_IMPACT_POLLUTION,
                ConfigurationEvenement.VENTFROID_IMPACT_PURIFICATION
            );
        }
        return null;
    }
    
    @Override
    public Evenement visit(Mer mer) {
        if (random.nextDouble() < ConfigurationCreationEvenement.PROBABILITE_PLUVIE_PAR_MER) {
            Bloc position = mer.getPosition();
            return new GroupePluie(
                position,
                ConfigurationEvenement.PLUIE_IMPACT_DUREE,
                ConfigurationEvenement.PLUIE_IMPACT_TEMPERATURE,
                ConfigurationEvenement.PLUIE_IMPACT_HUMIDITE,
                ConfigurationEvenement.PLUIE_IMPACT_POLLUTION,
                ConfigurationEvenement.PLUIE_IMPACT_PURIFICATION
            );
        }
        return null;
    }
    
    @Override
    public Evenement visit(Montagne montagne) {
        return null;
    }
    
    @Override
    public Evenement visit(Ville ville) {
        if (random.nextDouble() < ConfigurationCreationEvenement.PROBABILITE_POLLUTION_PAR_VILLE) {
            Bloc position = ville.getPosition();
            return new Pollution(
                position,
                ConfigurationEvenement.POLLUTION_IMPACT_DUREE,
                ConfigurationEvenement.POLLUTION_IMPACT_TEMPERATURE,
                ConfigurationEvenement.POLLUTION_IMPACT_HUMIDITE,
                ConfigurationEvenement.POLLUTION_IMPACT_POLLUTION,
                ConfigurationEvenement.POLLUTION_IMPACT_PURIFICATION
            );
        }
        return null;
    }
    
    @Override
    public Evenement visit(Village village) {
        return null;
    }
    
    @Override
    public Evenement visit(Banquise banquise) {
        if (random.nextDouble() < ConfigurationCreationEvenement.PROBABILITE_VENT_FROID_PAR_BANQUISE) {
            Bloc position = banquise.getPosition();
            return new VentFroid(
                position,
                ConfigurationEvenement.VENT_FROID_DUREE,
                ConfigurationEvenement.VENTFROID_IMPACT_TEMPERATURE,
                ConfigurationEvenement.VENTFROID_IMPACT_HUMIDITE,
                ConfigurationEvenement.VENTFROID_IMPACT_POLLUTION,
                ConfigurationEvenement.VENTFROID_IMPACT_PURIFICATION
            );
        }
        return null;
    }
    
}

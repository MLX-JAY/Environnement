package moteur.processus.usine;

import java.util.Random;

import config.ConfigurationEvenement;
import moteur.donne.carte.Bloc;
import moteur.donne.carte.Carte;
import moteur.donne.evenement.Evenement;
import moteur.donne.evenement.mobile.Pluie;
import moteur.donne.evenement.mobile.PluieAcide;
import moteur.donne.evenement.mobile.Pollution;
import moteur.donne.evenement.mobile.Purification;
import moteur.donne.evenement.mobile.VentChaud;
import moteur.donne.evenement.mobile.VentFroid;
import moteur.donne.evenement.statique.Meteore;

public class EvenementFactory {
    
    private final Random random = new Random();
    private final Carte carte;
    
    public EvenementFactory(Carte carte) {
        this.carte = carte;
    }
    
    public Evenement creerEvenementAleatoire() {
        Bloc position = getPositionAleatoire();
        
        // Vérifier si on crée une Météore selon la probabilité
        if (random.nextDouble() < ConfigurationEvenement.METEORE_PROBABILITE) {
            return creerMeteore(position);
        }
        
        return null;
    }
    
    public Pluie creerPluie(Bloc position) {
        return new Pluie(
            position,
            ConfigurationEvenement.PLUIE_IMPACT_DUREE,
            ConfigurationEvenement.PLUIE_IMPACT_TEMPERATURE,
            ConfigurationEvenement.PLUIE_IMPACT_HUMIDITE,
            ConfigurationEvenement.PLUIE_IMPACT_POLLUTION,
            ConfigurationEvenement.PLUIE_IMPACT_PURIFICATION
        );
    }
    
    public VentFroid creerVentFroid(Bloc position) {
        return new VentFroid(
            position,
            ConfigurationEvenement.VENTFROID_IMPACT_DUREE,
            ConfigurationEvenement.VENTFROID_IMPACT_TEMPERATURE,
            ConfigurationEvenement.VENTFROID_IMPACT_HUMIDITE,
            ConfigurationEvenement.VENTFROID_IMPACT_POLLUTION,
            ConfigurationEvenement.VENTFROID_IMPACT_PURIFICATION
        );
    }
    
    public VentChaud creerVentChaud(Bloc position) {
        return new VentChaud(
            position,
            ConfigurationEvenement.VENT_CHAUD_DUREE,
            ConfigurationEvenement.VENTFROID_IMPACT_TEMPERATURE,
            ConfigurationEvenement.VENTFROID_IMPACT_HUMIDITE,
            ConfigurationEvenement.VENTFROID_IMPACT_POLLUTION,
            ConfigurationEvenement.VENTFROID_IMPACT_PURIFICATION
        );
    }
    
    public PluieAcide creerPluieAcide(Bloc position) {
        return new PluieAcide(
            position,
            ConfigurationEvenement.PLUIEACIDE_IMPACT_DUREE,
            ConfigurationEvenement.PLUIEACIDE_IMPACT_TEMPERATURE,
            ConfigurationEvenement.PLUIEACIDE_IMPACT_HUMIDITE,
            ConfigurationEvenement.PLUIEACIDE_IMPACT_POLLUTION,
            ConfigurationEvenement.PLUIEACIDE_IMPACT_PURIFICATION
        );
    }
    
    public Pollution creerPollution(Bloc position) {
        return new Pollution(
            position,
            ConfigurationEvenement.POLLUTION_IMPACT_DUREE,
            ConfigurationEvenement.POLLUTION_IMPACT_TEMPERATURE,
            ConfigurationEvenement.POLLUTION_IMPACT_HUMIDITE,
            ConfigurationEvenement.POLLUTION_IMPACT_POLLUTION,
            ConfigurationEvenement.POLLUTION_IMPACT_PURIFICATION
        );
    }
    
    public Meteore creerMeteore(Bloc position) {
        return new Meteore(
            position,
            ConfigurationEvenement.METEORE_IMPACT_DUREE,
            ConfigurationEvenement.METEORE_IMPACT_TEMPERATURE,
            ConfigurationEvenement.METEORE_IMPACT_HUMIDITE,
            ConfigurationEvenement.METEORE_IMPACT_POLLUTION,
            ConfigurationEvenement.METEORE_IMPACT_PURIFICATION
        );
    }
    
    public Purification creerPurification(Bloc position) {
        return new Purification(
            position,
            ConfigurationEvenement.PURIFICATION_DUREE,
            ConfigurationEvenement.PURIFICATION_IMPACT_TEMPERATURE,
            ConfigurationEvenement.PURIFICATION_IMPACT_HUMIDITE,
            ConfigurationEvenement.PURIFICATION_IMPACT_POLLUTION,
            ConfigurationEvenement.PURIFICATION_IMPACT_PURIFICATION
        );
    }
    
    public Bloc getPositionAleatoire() {
        int ligne = random.nextInt(carte.getGrandeurX());
        int colonne = random.nextInt(carte.getGrandeurY());
        return carte.getBloc(ligne, colonne);
    }
    
}

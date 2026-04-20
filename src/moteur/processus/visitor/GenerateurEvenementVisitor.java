package moteur.processus.visitor;

import java.util.ArrayList;
import java.util.Random;
import java.util.List;

import config.ConfigurationCreationEvenement;
import config.ConfigurationEvenement;
import moteur.donne.biome.Banquise;
import moteur.donne.biome.Biome;
import moteur.donne.biome.Desert;
import moteur.donne.biome.Foret;
import moteur.donne.biome.Mer;
import moteur.donne.biome.Montagne;
import moteur.donne.biome.Ville;
import moteur.donne.biome.Village;
import moteur.donne.carte.Bloc;
import moteur.donne.evenement.Evenement;
import moteur.processus.usine.EvenementFactory;
import org.apache.log4j.Logger;
import util.LoggerUtility;

public class GenerateurEvenementVisitor implements BiomeVisitor<Evenement> {
    
    private static final Logger logger = LoggerUtility.getLogger(GenerateurEvenementVisitor.class);
    
    private final Random random = new Random();
    private final EvenementFactory factory;
    
    public GenerateurEvenementVisitor(EvenementFactory factory) {
        this.factory = factory;
    }
    
    @Override
    public Evenement visit(Foret foret) {
        // Purification
        if (random.nextDouble() < ConfigurationCreationEvenement.PROBABILITE_PURIFICATION_PAR_FORET) {
            Bloc position = foret.getPosition();
            List<Evenement> liste = factory.creerPurificationMultiple(position);
            if (liste != null && !liste.isEmpty()) {
                return liste.get(0);
            }
        }
        // Pluie Bénite
        if (random.nextDouble() < ConfigurationCreationEvenement.PROBABILITE_PLUIEBENITE_PAR_FORET) {
            Bloc position = foret.getPosition();
            List<Evenement> liste = factory.creerPluieBeniteMultiple(position);
            if (liste != null && !liste.isEmpty()) {
                return liste.get(0);
            }
        }
        return null;
    }
    
    @Override
    public Evenement visit(Desert desert) {
        // Vent Chaud
        if (random.nextDouble() < ConfigurationCreationEvenement.PROBABILITE_VENT_CHAUD_PAR_DESERT) {
            Bloc position = desert.getPosition();
            List<Evenement> liste = factory.creerVentChaudMultiple(position);
            if (liste != null && !liste.isEmpty()) {
                return liste.get(0);
            }
        }
        // Zephyr
        if (random.nextDouble() < ConfigurationCreationEvenement.PROBABILITE_ZEPHYR_PAR_DESERT) {
            Bloc position = desert.getPosition();
            List<Evenement> liste = factory.creerZephyrMultiple(position);
            if (liste != null && !liste.isEmpty()) {
                return liste.get(0);
            }
        }
        // Tornade
        if (random.nextDouble() < ConfigurationCreationEvenement.PROBABILITE_TORNADE_PAR_DESERT) {
            Bloc position = desert.getPosition();
            List<Evenement> liste = factory.creerTornadeMultiple(position);
            if (liste != null && !liste.isEmpty()) {
                return liste.get(0);
            }
        }
        return null;
    }
    
    @Override
    public Evenement visit(Mer mer) {
        // Pluie
        if (random.nextDouble() < ConfigurationCreationEvenement.PROBABILITE_PLUVIE_PAR_MER) {
            Bloc position = mer.getPosition();
            List<Evenement> liste = factory.creerPluieMultiple(position);
            if (liste != null && !liste.isEmpty()) {
                return liste.get(0);
            }
        }
        // Orage
        if (random.nextDouble() < ConfigurationCreationEvenement.PROBABILITE_ORAGE_PAR_MER) {
            Bloc position = mer.getPosition();
            List<Evenement> liste = factory.creerOrageMultiple(position);
            if (liste != null && !liste.isEmpty()) {
                return liste.get(0);
            }
        }
        // Tonnerre
        if (random.nextDouble() < ConfigurationCreationEvenement.PROBABILITE_TONNERRE_PAR_MER) {
            Bloc position = mer.getPosition();
            List<Evenement> liste = factory.creerTonnerreMultiple(position);
            if (liste != null && !liste.isEmpty()) {
                return liste.get(0);
            }
        }
        return null;
    }
    
    @Override
    public Evenement visit(Montagne montagne) {
        return null;
    }
    
    @Override
    public Evenement visit(Ville ville) {
        // Pollution
        if (random.nextDouble() < ConfigurationCreationEvenement.PROBABILITE_POLLUTION_PAR_VILLE) {
            Bloc position = ville.getPosition();
            List<Evenement> liste = factory.creerPollutionMultiple(position);
            if (liste != null && !liste.isEmpty()) {
                return liste.get(0);
            }
        }
        // Smog
        if (random.nextDouble() < ConfigurationCreationEvenement.PROBABILITE_SMOG_PAR_VILLE) {
            Bloc position = ville.getPosition();
            List<Evenement> liste = factory.creerSmogMultiple(position);
            if (liste != null && !liste.isEmpty()) {
                return liste.get(0);
            }
        }
        // Nuage Toxique
        if (random.nextDouble() < ConfigurationCreationEvenement.PROBABILITE_NUAGETOXIQUE_PAR_VILLE) {
            Bloc position = ville.getPosition();
            List<Evenement> liste = factory.creerNuageToxiqueMultiple(position);
            if (liste != null && !liste.isEmpty()) {
                return liste.get(0);
            }
        }
        return null;
    }
    
    @Override
    public Evenement visit(Village village) {
        // Pollution
        if (random.nextDouble() < ConfigurationCreationEvenement.PROBABILITE_POLLUTION_PAR_VILLAGE) {
            Bloc position = village.getPosition();
            List<Evenement> liste = factory.creerPollutionMultiple(position);
            if (liste != null && !liste.isEmpty()) {
                return liste.get(0);
            }
        }
        return null;
    }
    
    @Override
    public Evenement visit(Banquise banquise) {
        // Vent Froid
        if (random.nextDouble() < ConfigurationCreationEvenement.PROBABILITE_VENT_FROID_PAR_BANQUISE) {
            Bloc position = banquise.getPosition();
            List<Evenement> liste = factory.creerVentFroidMultiple(position);
            if (liste != null && !liste.isEmpty()) {
                return liste.get(0);
            }
        }
        // Grèle
        if (random.nextDouble() < ConfigurationCreationEvenement.PROBABILITE_GRELE_PAR_BANQUISE) {
            Bloc position = banquise.getPosition();
            List<Evenement> liste = factory.creerGreleMultiple(position);
            if (liste != null && !liste.isEmpty()) {
                return liste.get(0);
            }
        }
        return null;
    }
    
    public List<Evenement> genererTousEvenements(Biome biome) {
        List<Evenement> resultats = new ArrayList<>();
        
        if (biome instanceof Foret) {
            Foret foret = (Foret) biome;
            if (random.nextDouble() < ConfigurationCreationEvenement.PROBABILITE_PURIFICATION_PAR_FORET) {
                List<Evenement> liste = factory.creerPurificationMultiple(foret.getPosition());
                resultats.addAll(liste);
            }
            if (random.nextDouble() < ConfigurationCreationEvenement.PROBABILITE_PLUIEBENITE_PAR_FORET) {
                List<Evenement> liste = factory.creerPluieBeniteMultiple(foret.getPosition());
                resultats.addAll(liste);
            }
        }
        else if (biome instanceof Desert) {
            Desert desert = (Desert) biome;
            if (random.nextDouble() < ConfigurationCreationEvenement.PROBABILITE_VENT_CHAUD_PAR_DESERT) {
                resultats.addAll(factory.creerVentChaudMultiple(desert.getPosition()));
            }
            if (random.nextDouble() < ConfigurationCreationEvenement.PROBABILITE_ZEPHYR_PAR_DESERT) {
                resultats.addAll(factory.creerZephyrMultiple(desert.getPosition()));
            }
            if (random.nextDouble() < ConfigurationCreationEvenement.PROBABILITE_TORNADE_PAR_DESERT) {
                resultats.addAll(factory.creerTornadeMultiple(desert.getPosition()));
            }
        }
        else if (biome instanceof Mer) {
            Mer mer = (Mer) biome;
            if (random.nextDouble() < ConfigurationCreationEvenement.PROBABILITE_PLUVIE_PAR_MER) {
                resultats.addAll(factory.creerPluieMultiple(mer.getPosition()));
            }
            if (random.nextDouble() < ConfigurationCreationEvenement.PROBABILITE_ORAGE_PAR_MER) {
                resultats.addAll(factory.creerOrageMultiple(mer.getPosition()));
            }
            if (random.nextDouble() < ConfigurationCreationEvenement.PROBABILITE_TONNERRE_PAR_MER) {
                resultats.addAll(factory.creerTonnerreMultiple(mer.getPosition()));
            }
        }
        else if (biome instanceof Ville) {
            Ville ville = (Ville) biome;
            if (random.nextDouble() < ConfigurationCreationEvenement.PROBABILITE_POLLUTION_PAR_VILLE) {
                resultats.addAll(factory.creerPollutionMultiple(ville.getPosition()));
            }
            if (random.nextDouble() < ConfigurationCreationEvenement.PROBABILITE_SMOG_PAR_VILLE) {
                resultats.addAll(factory.creerSmogMultiple(ville.getPosition()));
            }
            if (random.nextDouble() < ConfigurationCreationEvenement.PROBABILITE_NUAGETOXIQUE_PAR_VILLE) {
                resultats.addAll(factory.creerNuageToxiqueMultiple(ville.getPosition()));
            }
        }
        else if (biome instanceof Village) {
            Village village = (Village) biome;
            if (random.nextDouble() < ConfigurationCreationEvenement.PROBABILITE_POLLUTION_PAR_VILLAGE) {
                resultats.addAll(factory.creerPollutionMultiple(village.getPosition()));
            }
        }
        else if (biome instanceof Banquise) {
            Banquise banquise = (Banquise) biome;
            if (random.nextDouble() < ConfigurationCreationEvenement.PROBABILITE_VENT_FROID_PAR_BANQUISE) {
                resultats.addAll(factory.creerVentFroidMultiple(banquise.getPosition()));
            }
            if (random.nextDouble() < ConfigurationCreationEvenement.PROBABILITE_GRELE_PAR_BANQUISE) {
                resultats.addAll(factory.creerGreleMultiple(banquise.getPosition()));
            }
        }
        
        return resultats;
    }
    
}
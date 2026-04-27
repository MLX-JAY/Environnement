package moteur.processus.usine;

import config.ConfigurationDirection;
import config.ConfigurationEvenement;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import moteur.donne.carte.Bloc;
import moteur.donne.carte.Carte;
import moteur.donne.evenement.Evenement;
import moteur.donne.evenement.mobile.Grele;
import moteur.donne.evenement.mobile.NuageToxique;
import moteur.donne.evenement.mobile.Orage;
import moteur.donne.evenement.mobile.Pluie;
import moteur.donne.evenement.mobile.PluieAcide;
import moteur.donne.evenement.mobile.PluieBenite;
import moteur.donne.evenement.mobile.Pollution;
import moteur.donne.evenement.mobile.Purification;
import moteur.donne.evenement.mobile.Smog;
import moteur.donne.evenement.mobile.Tonnerre;
import moteur.donne.evenement.mobile.Tornade;
import moteur.donne.evenement.mobile.VentChaud;
import moteur.donne.evenement.mobile.VentFroid;
import moteur.donne.evenement.mobile.Zephyr;
import moteur.donne.evenement.statique.Meteore;

public class EvenementFactory {
    
    private static final int METEORE_DISTANCE_CHUTE = 3;
    
    private final Random random = new Random();
    private final Carte carte;
    
    public EvenementFactory(Carte carte) {
        this.carte = carte;
    }
    
    public Evenement creerEvenementAleatoire() {
        Bloc position = getPositionAleatoire();
        
        if (random.nextDouble() < ConfigurationEvenement.METEORE_PROBABILITE) {
            return creerMeteore(getPositionAleatoireMeteore());
        }
        
        return null;
    }
    
    // ==================== PLUIE ====================
    
    public Pluie creerPluie(Bloc position) {
        if (!carte.estCoordonneeValide(position.getX(), position.getY())) {
            return null;
        }
        return new Pluie(
            position,
            ConfigurationEvenement.PLUIE_IMPACT_DUREE,
            ConfigurationEvenement.PLUIE_IMPACT_TEMPERATURE,
            ConfigurationEvenement.PLUIE_IMPACT_HUMIDITE,
            ConfigurationEvenement.PLUIE_IMPACT_POLLUTION,
            ConfigurationEvenement.PLUIE_IMPACT_PURIFICATION
        );
    }
    
    public List<Evenement> creerPluieMultiple(Bloc position) {
        List<Evenement> liste = new ArrayList<>();
        int x = position.getX();
        int y = position.getY();
        
        // Générer une direction commune pour toute la formation
        int dirIndex = random.nextInt(ConfigurationDirection.DIRECTIONS.length);
        int dx = ConfigurationDirection.DIRECTIONS[dirIndex][0];
        int dy = ConfigurationDirection.DIRECTIONS[dirIndex][1];
        
        // CARRE_2X2 autour de (x, y)
        if (carte.estCoordonneeValide(x-1, y-1)) {
            Pluie e = creerPluie(new Bloc(x-1, y-1));
            if (e != null) {
                e.setDirection(dx, dy);
                liste.add(e);
            }
        }
        if (carte.estCoordonneeValide(x, y-1)) {
            Pluie e = creerPluie(new Bloc(x, y-1));
            if (e != null) {
                e.setDirection(dx, dy);
                liste.add(e);
            }
        }
        if (carte.estCoordonneeValide(x-1, y)) {
            Pluie e = creerPluie(new Bloc(x-1, y));
            if (e != null) {
                e.setDirection(dx, dy);
                liste.add(e);
            }
        }
        if (carte.estCoordonneeValide(x, y)) {
            Pluie e = creerPluie(new Bloc(x, y));
            if (e != null) {
                e.setDirection(dx, dy);
                liste.add(e);
            }
        }
        
        return liste;
    }
    
    // ==================== PLUIE ACIDE ====================
    
    public PluieAcide creerPluieAcide(Bloc position) {
        if (!carte.estCoordonneeValide(position.getX(), position.getY())) {
            return null;
        }
        return new PluieAcide(
            position,
            ConfigurationEvenement.PLUIEACIDE_IMPACT_DUREE,
            ConfigurationEvenement.PLUIEACIDE_IMPACT_TEMPERATURE,
            ConfigurationEvenement.PLUIEACIDE_IMPACT_HUMIDITE,
            ConfigurationEvenement.PLUIEACIDE_IMPACT_POLLUTION,
            ConfigurationEvenement.PLUIEACIDE_IMPACT_PURIFICATION
        );
    }
    
    public List<Evenement> creerPluieAcideMultiple(Bloc position) {
        List<Evenement> liste = new ArrayList<>();
        int x = position.getX();
        int y = position.getY();
        
        int dirIndex = random.nextInt(ConfigurationDirection.DIRECTIONS.length);
        int dx = ConfigurationDirection.DIRECTIONS[dirIndex][0];
        int dy = ConfigurationDirection.DIRECTIONS[dirIndex][1];
        
        if (carte.estCoordonneeValide(x-1, y-1)) {
            PluieAcide e = creerPluieAcide(new Bloc(x-1, y-1));
            if (e != null) { e.setDirection(dx, dy); liste.add(e); }
        }
        if (carte.estCoordonneeValide(x, y-1)) {
            PluieAcide e = creerPluieAcide(new Bloc(x, y-1));
            if (e != null) { e.setDirection(dx, dy); liste.add(e); }
        }
        if (carte.estCoordonneeValide(x-1, y)) {
            PluieAcide e = creerPluieAcide(new Bloc(x-1, y));
            if (e != null) { e.setDirection(dx, dy); liste.add(e); }
        }
        if (carte.estCoordonneeValide(x, y)) {
            PluieAcide e = creerPluieAcide(new Bloc(x, y));
            if (e != null) { e.setDirection(dx, dy); liste.add(e); }
        }
        
        return liste;
    }
    
    // ==================== VENT FROID ====================
    
    public VentFroid creerVentFroid(Bloc position) {
        if (!carte.estCoordonneeValide(position.getX(), position.getY())) {
            return null;
        }
        return new VentFroid(
            position,
            ConfigurationEvenement.VENTFROID_IMPACT_DUREE,
            ConfigurationEvenement.VENTFROID_IMPACT_TEMPERATURE,
            ConfigurationEvenement.VENTFROID_IMPACT_HUMIDITE,
            ConfigurationEvenement.VENTFROID_IMPACT_POLLUTION,
            ConfigurationEvenement.VENTFROID_IMPACT_PURIFICATION
        );
    }
    
    public List<Evenement> creerVentFroidMultiple(Bloc position) {
        List<Evenement> liste = new ArrayList<>();
        int x = position.getX();
        int y = position.getY();
        
        int dirIndex = random.nextInt(ConfigurationDirection.DIRECTIONS.length);
        int dx = ConfigurationDirection.DIRECTIONS[dirIndex][0];
        int dy = ConfigurationDirection.DIRECTIONS[dirIndex][1];
        
        if (carte.estCoordonneeValide(x-1, y)) {
            VentFroid e = creerVentFroid(new Bloc(x-1, y));
            if (e != null) { e.setDirection(dx, dy); liste.add(e); }
        }
        if (carte.estCoordonneeValide(x, y)) {
            VentFroid e = creerVentFroid(new Bloc(x, y));
            if (e != null) { e.setDirection(dx, dy); liste.add(e); }
        }
        if (carte.estCoordonneeValide(x+1, y)) {
            VentFroid e = creerVentFroid(new Bloc(x+1, y));
            if (e != null) { e.setDirection(dx, dy); liste.add(e); }
        }
        
        return liste;
    }
    
    // ==================== VENT CHAUD ====================
    
    public VentChaud creerVentChaud(Bloc position) {
        if (!carte.estCoordonneeValide(position.getX(), position.getY())) {
            return null;
        }
        return new VentChaud(
            position,
            ConfigurationEvenement.VENT_CHAUD_DUREE,
            ConfigurationEvenement.VENTFROID_IMPACT_TEMPERATURE,
            ConfigurationEvenement.VENTFROID_IMPACT_HUMIDITE,
            ConfigurationEvenement.VENTFROID_IMPACT_POLLUTION,
            ConfigurationEvenement.VENTFROID_IMPACT_PURIFICATION
        );
    }
    
    public List<Evenement> creerVentChaudMultiple(Bloc position) {
        List<Evenement> liste = new ArrayList<>();
        int x = position.getX();
        int y = position.getY();
        
        int dirIndex = random.nextInt(ConfigurationDirection.DIRECTIONS.length);
        int dx = ConfigurationDirection.DIRECTIONS[dirIndex][0];
        int dy = ConfigurationDirection.DIRECTIONS[dirIndex][1];
        
        if (carte.estCoordonneeValide(x-1, y)) {
            VentChaud e = creerVentChaud(new Bloc(x-1, y));
            if (e != null) { e.setDirection(dx, dy); liste.add(e); }
        }
        if (carte.estCoordonneeValide(x, y)) {
            VentChaud e = creerVentChaud(new Bloc(x, y));
            if (e != null) { e.setDirection(dx, dy); liste.add(e); }
        }
        if (carte.estCoordonneeValide(x+1, y)) {
            VentChaud e = creerVentChaud(new Bloc(x+1, y));
            if (e != null) { e.setDirection(dx, dy); liste.add(e); }
        }
        
        return liste;
    }
    
    // ==================== POLLUTION ====================
    
    public Pollution creerPollution(Bloc position) {
        if (!carte.estCoordonneeValide(position.getX(), position.getY())) {
            return null;
        }
        return new Pollution(
            position,
            ConfigurationEvenement.POLLUTION_IMPACT_DUREE,
            ConfigurationEvenement.POLLUTION_IMPACT_TEMPERATURE,
            ConfigurationEvenement.POLLUTION_IMPACT_HUMIDITE,
            ConfigurationEvenement.POLLUTION_IMPACT_POLLUTION,
            ConfigurationEvenement.POLLUTION_IMPACT_PURIFICATION
        );
    }
    
    public List<Evenement> creerPollutionMultiple(Bloc position) {
        List<Evenement> liste = new ArrayList<>();
        
        int dirIndex = random.nextInt(ConfigurationDirection.DIRECTIONS.length);
        int dx = ConfigurationDirection.DIRECTIONS[dirIndex][0];
        int dy = ConfigurationDirection.DIRECTIONS[dirIndex][1];
        
        if (carte.estCoordonneeValide(position.getX(), position.getY())) {
            Pollution e = creerPollution(position);
            if (e != null) { e.setDirection(dx, dy); liste.add(e); }
        }
        return liste;
    }
    
    // ==================== PURIFICATION ====================
    
    public Purification creerPurification(Bloc position) {
        if (!carte.estCoordonneeValide(position.getX(), position.getY())) {
            return null;
        }
        return new Purification(
            position,
            ConfigurationEvenement.PURIFICATION_DUREE,
            ConfigurationEvenement.PURIFICATION_IMPACT_TEMPERATURE,
            ConfigurationEvenement.PURIFICATION_IMPACT_HUMIDITE,
            ConfigurationEvenement.PURIFICATION_IMPACT_POLLUTION,
            ConfigurationEvenement.PURIFICATION_IMPACT_PURIFICATION
        );
    }
    
    public List<Evenement> creerPurificationMultiple(Bloc position) {
        List<Evenement> liste = new ArrayList<>();
        
        int dirIndex = random.nextInt(ConfigurationDirection.DIRECTIONS.length);
        int dx = ConfigurationDirection.DIRECTIONS[dirIndex][0];
        int dy = ConfigurationDirection.DIRECTIONS[dirIndex][1];
        
        if (carte.estCoordonneeValide(position.getX(), position.getY())) {
            Purification e = creerPurification(position);
            if (e != null) { e.setDirection(dx, dy); liste.add(e); }
        }
        return liste;
    }
    
    // ==================== METEORE ====================
    
    public Meteore creerMeteore(Bloc position) {
        if (!carte.estCoordonneeValide(position.getX(), position.getY())) {
            return null;
        }
        int cibleX = position.getX();
        int cibleY = position.getY();
        int departY = Math.max(0, cibleY - METEORE_DISTANCE_CHUTE);
        Bloc depart = carte.getBloc(cibleX, departY);
        Bloc cible = carte.getBloc(cibleX, cibleY);
        int pasRestants = cibleY - departY;
        return new Meteore(
            depart,
            cible,
            pasRestants,
            ConfigurationEvenement.METEORE_IMPACT_DUREE,
            ConfigurationEvenement.METEORE_IMPACT_TEMPERATURE,
            ConfigurationEvenement.METEORE_IMPACT_HUMIDITE,
            ConfigurationEvenement.METEORE_IMPACT_POLLUTION,
            ConfigurationEvenement.METEORE_IMPACT_PURIFICATION
        );
    }
    
    public List<Evenement> creerMeteoreMultiple(Bloc position) {
        List<Evenement> liste = new ArrayList<>();
        
        int dirIndex = random.nextInt(ConfigurationDirection.DIRECTIONS.length);
        int dx = ConfigurationDirection.DIRECTIONS[dirIndex][0];
        int dy = ConfigurationDirection.DIRECTIONS[dirIndex][1];
        
        if (carte.estCoordonneeValide(position.getX(), position.getY())) {
            Meteore e = creerMeteore(position);
            if (e != null) { e.setDirection(dx, dy); liste.add(e); }
        }
        return liste;
    }
    
    // ==================== ORAGE ====================
    
    public Orage creerOrage(Bloc position) {
        if (!carte.estCoordonneeValide(position.getX(), position.getY())) {
            return null;
        }
        return new Orage(
            position,
            ConfigurationEvenement.ORAGE_IMPACT_DUREE,
            ConfigurationEvenement.ORAGE_IMPACT_TEMPERATURE,
            ConfigurationEvenement.ORAGE_IMPACT_HUMIDITE,
            ConfigurationEvenement.ORAGE_IMPACT_POLLUTION,
            ConfigurationEvenement.ORAGE_IMPACT_PURIFICATION
        );
    }
    
    public List<Evenement> creerOrageMultiple(Bloc position) {
        List<Evenement> liste = new ArrayList<>();
        int x = position.getX();
        int y = position.getY();
        
        int dirIndex = random.nextInt(ConfigurationDirection.DIRECTIONS.length);
        int dx = ConfigurationDirection.DIRECTIONS[dirIndex][0];
        int dy = ConfigurationDirection.DIRECTIONS[dirIndex][1];
        
        if (carte.estCoordonneeValide(x-1, y-1)) {
            Orage e = creerOrage(new Bloc(x-1, y-1));
            if (e != null) { e.setDirection(dx, dy); liste.add(e); }
        }
        if (carte.estCoordonneeValide(x, y-1)) {
            Orage e = creerOrage(new Bloc(x, y-1));
            if (e != null) { e.setDirection(dx, dy); liste.add(e); }
        }
        if (carte.estCoordonneeValide(x-1, y)) {
            Orage e = creerOrage(new Bloc(x-1, y));
            if (e != null) { e.setDirection(dx, dy); liste.add(e); }
        }
        if (carte.estCoordonneeValide(x, y)) {
            Orage e = creerOrage(new Bloc(x, y));
            if (e != null) { e.setDirection(dx, dy); liste.add(e); }
        }
        
        return liste;
    }
    
    // ==================== GRELE ====================
    
    public Grele creerGrele(Bloc position) {
        if (!carte.estCoordonneeValide(position.getX(), position.getY())) {
            return null;
        }
        return new Grele(
            position,
            ConfigurationEvenement.GRELE_IMPACT_DUREE,
            ConfigurationEvenement.GRELE_IMPACT_TEMPERATURE,
            ConfigurationEvenement.GRELE_IMPACT_HUMIDITE,
            ConfigurationEvenement.GRELE_IMPACT_POLLUTION,
            ConfigurationEvenement.GRELE_IMPACT_PURIFICATION
        );
    }
    
    public List<Evenement> creerGreleMultiple(Bloc position) {
        List<Evenement> liste = new ArrayList<>();
        int x = position.getX();
        int y = position.getY();
        
        int dirIndex = random.nextInt(ConfigurationDirection.DIRECTIONS.length);
        int dx = ConfigurationDirection.DIRECTIONS[dirIndex][0];
        int dy = ConfigurationDirection.DIRECTIONS[dirIndex][1];
        
        if (carte.estCoordonneeValide(x, y-1)) {
            Grele e = creerGrele(new Bloc(x, y-1));
            if (e != null) { e.setDirection(dx, dy); liste.add(e); }
        }
        if (carte.estCoordonneeValide(x, y)) {
            Grele e = creerGrele(new Bloc(x, y));
            if (e != null) { e.setDirection(dx, dy); liste.add(e); }
        }
        if (carte.estCoordonneeValide(x, y+1)) {
            Grele e = creerGrele(new Bloc(x, y+1));
            if (e != null) { e.setDirection(dx, dy); liste.add(e); }
        }
        
        return liste;
    }
    
    // ==================== TORNADE ====================
    
    public Tornade creerTornade(Bloc position) {
        if (!carte.estCoordonneeValide(position.getX(), position.getY())) {
            return null;
        }
        return new Tornade(
            position,
            ConfigurationEvenement.TORNADE_IMPACT_DUREE,
            ConfigurationEvenement.TORNADE_IMPACT_TEMPERATURE,
            ConfigurationEvenement.TORNADE_IMPACT_HUMIDITE,
            ConfigurationEvenement.TORNADE_IMPACT_POLLUTION,
            ConfigurationEvenement.TORNADE_IMPACT_PURIFICATION
        );
    }
    
    public List<Evenement> creerTornadeMultiple(Bloc position) {
        List<Evenement> liste = new ArrayList<>();
        int x = position.getX();
        int y = position.getY();
        
        int dirIndex = random.nextInt(ConfigurationDirection.DIRECTIONS.length);
        int dx = ConfigurationDirection.DIRECTIONS[dirIndex][0];
        int dy = ConfigurationDirection.DIRECTIONS[dirIndex][1];
        
        if (carte.estCoordonneeValide(x, y)) {
            Tornade e = creerTornade(new Bloc(x, y));
            if (e != null) { e.setDirection(dx, dy); liste.add(e); }
        }
        if (carte.estCoordonneeValide(x-1, y)) {
            Tornade e = creerTornade(new Bloc(x-1, y));
            if (e != null) { e.setDirection(dx, dy); liste.add(e); }
        }
        if (carte.estCoordonneeValide(x+1, y)) {
            Tornade e = creerTornade(new Bloc(x+1, y));
            if (e != null) { e.setDirection(dx, dy); liste.add(e); }
        }
        if (carte.estCoordonneeValide(x, y-1)) {
            Tornade e = creerTornade(new Bloc(x, y-1));
            if (e != null) { e.setDirection(dx, dy); liste.add(e); }
        }
        if (carte.estCoordonneeValide(x, y+1)) {
            Tornade e = creerTornade(new Bloc(x, y+1));
            if (e != null) { e.setDirection(dx, dy); liste.add(e); }
        }
        
        return liste;
    }
    
    // ==================== PLUIE BENITE ====================
    
    public PluieBenite creerPluieBenite(Bloc position) {
        if (!carte.estCoordonneeValide(position.getX(), position.getY())) {
            return null;
        }
        return new PluieBenite(
            position,
            ConfigurationEvenement.PLUIEBENITE_IMPACT_DUREE,
            ConfigurationEvenement.PLUIEBENITE_IMPACT_TEMPERATURE,
            ConfigurationEvenement.PLUIEBENITE_IMPACT_HUMIDITE,
            ConfigurationEvenement.PLUIEBENITE_IMPACT_POLLUTION,
            ConfigurationEvenement.PLUIEBENITE_IMPACT_PURIFICATION
        );
    }
    
    public List<Evenement> creerPluieBeniteMultiple(Bloc position) {
        List<Evenement> liste = new ArrayList<>();
        int x = position.getX();
        int y = position.getY();
        
        int dirIndex = random.nextInt(ConfigurationDirection.DIRECTIONS.length);
        int dx = ConfigurationDirection.DIRECTIONS[dirIndex][0];
        int dy = ConfigurationDirection.DIRECTIONS[dirIndex][1];
        
        if (carte.estCoordonneeValide(x-1, y-1)) {
            PluieBenite e = creerPluieBenite(new Bloc(x-1, y-1));
            if (e != null) { e.setDirection(dx, dy); liste.add(e); }
        }
        if (carte.estCoordonneeValide(x, y-1)) {
            PluieBenite e = creerPluieBenite(new Bloc(x, y-1));
            if (e != null) { e.setDirection(dx, dy); liste.add(e); }
        }
        if (carte.estCoordonneeValide(x-1, y)) {
            PluieBenite e = creerPluieBenite(new Bloc(x-1, y));
            if (e != null) { e.setDirection(dx, dy); liste.add(e); }
        }
        if (carte.estCoordonneeValide(x, y)) {
            PluieBenite e = creerPluieBenite(new Bloc(x, y));
            if (e != null) { e.setDirection(dx, dy); liste.add(e); }
        }
        
        return liste;
    }
    
    // ==================== ZEPHYR ====================
    
    public Zephyr creerZephyr(Bloc position) {
        if (!carte.estCoordonneeValide(position.getX(), position.getY())) {
            return null;
        }
        return new Zephyr(
            position,
            ConfigurationEvenement.ZEPHYR_IMPACT_DUREE,
            ConfigurationEvenement.ZEPHYR_IMPACT_TEMPERATURE,
            ConfigurationEvenement.ZEPHYR_IMPACT_HUMIDITE,
            ConfigurationEvenement.ZEPHYR_IMPACT_POLLUTION,
            ConfigurationEvenement.ZEPHYR_IMPACT_PURIFICATION
        );
    }
    
    public List<Evenement> creerZephyrMultiple(Bloc position) {
        List<Evenement> liste = new ArrayList<>();
        int x = position.getX();
        int y = position.getY();
        
        int dirIndex = random.nextInt(ConfigurationDirection.DIRECTIONS.length);
        int dx = ConfigurationDirection.DIRECTIONS[dirIndex][0];
        int dy = ConfigurationDirection.DIRECTIONS[dirIndex][1];
        
        if (carte.estCoordonneeValide(x-1, y-1)) {
            Zephyr e = creerZephyr(new Bloc(x-1, y-1));
            if (e != null) { e.setDirection(dx, dy); liste.add(e); }
        }
        if (carte.estCoordonneeValide(x, y)) {
            Zephyr e = creerZephyr(new Bloc(x, y));
            if (e != null) { e.setDirection(dx, dy); liste.add(e); }
        }
        if (carte.estCoordonneeValide(x+1, y+1)) {
            Zephyr e = creerZephyr(new Bloc(x+1, y+1));
            if (e != null) { e.setDirection(dx, dy); liste.add(e); }
        }
        
        return liste;
    }
    
    // ==================== TONNERRE ====================
    
    public Tonnerre creerTonnerre(Bloc position) {
        if (!carte.estCoordonneeValide(position.getX(), position.getY())) {
            return null;
        }
        return new Tonnerre(
            position,
            ConfigurationEvenement.TONNERRE_IMPACT_DUREE,
            ConfigurationEvenement.TONNERRE_IMPACT_TEMPERATURE,
            ConfigurationEvenement.TONNERRE_IMPACT_HUMIDITE,
            ConfigurationEvenement.TONNERRE_IMPACT_POLLUTION,
            ConfigurationEvenement.TONNERRE_IMPACT_PURIFICATION
        );
    }
    
    public List<Evenement> creerTonnerreMultiple(Bloc position) {
        List<Evenement> liste = new ArrayList<>();
        int x = position.getX();
        int y = position.getY();
        
        int dirIndex = random.nextInt(ConfigurationDirection.DIRECTIONS.length);
        int dx = ConfigurationDirection.DIRECTIONS[dirIndex][0];
        int dy = ConfigurationDirection.DIRECTIONS[dirIndex][1];
        
        if (carte.estCoordonneeValide(x-1, y)) {
            Tonnerre e = creerTonnerre(new Bloc(x-1, y));
            if (e != null) { e.setDirection(dx, dy); liste.add(e); }
        }
        if (carte.estCoordonneeValide(x, y)) {
            Tonnerre e = creerTonnerre(new Bloc(x, y));
            if (e != null) { e.setDirection(dx, dy); liste.add(e); }
        }
        if (carte.estCoordonneeValide(x+1, y)) {
            Tonnerre e = creerTonnerre(new Bloc(x+1, y));
            if (e != null) { e.setDirection(dx, dy); liste.add(e); }
        }
        if (carte.estCoordonneeValide(x, y-1)) {
            Tonnerre e = creerTonnerre(new Bloc(x, y-1));
            if (e != null) { e.setDirection(dx, dy); liste.add(e); }
        }
        
        return liste;
    }
    
    // ==================== SMOG ====================
    
    public Smog creerSmog(Bloc position) {
        if (!carte.estCoordonneeValide(position.getX(), position.getY())) {
            return null;
        }
        return new Smog(
            position,
            ConfigurationEvenement.SMOG_IMPACT_DUREE,
            ConfigurationEvenement.SMOG_IMPACT_TEMPERATURE,
            ConfigurationEvenement.SMOG_IMPACT_HUMIDITE,
            ConfigurationEvenement.SMOG_IMPACT_POLLUTION,
            ConfigurationEvenement.SMOG_IMPACT_PURIFICATION
        );
    }
    
    public List<Evenement> creerSmogMultiple(Bloc position) {
        List<Evenement> liste = new ArrayList<>();
        int x = position.getX();
        int y = position.getY();
        
        int dirIndex = random.nextInt(ConfigurationDirection.DIRECTIONS.length);
        int dirDx = ConfigurationDirection.DIRECTIONS[dirIndex][0];
        int dirDy = ConfigurationDirection.DIRECTIONS[dirIndex][1];
        
        int[] dx = {-1, 0, 1, -1, 1, -1, 0, 1};
        int[] dy = {-1, -1, -1, 0, 0, 1, 1, 1};
        
        for (int i = 0; i < 8; i++) {
            int nx = x + dx[i];
            int ny = y + dy[i];
            if (carte.estCoordonneeValide(nx, ny)) {
                Smog e = creerSmog(new Bloc(nx, ny));
                if (e != null) { e.setDirection(dirDx, dirDy); liste.add(e); }
            }
        }
        if (carte.estCoordonneeValide(x, y)) {
            Smog e = creerSmog(new Bloc(x, y));
            if (e != null) { e.setDirection(dirDx, dirDy); liste.add(e); }
        }
        
        return liste;
    }
    
    // ==================== NUAGE TOXIQUE ====================
    
    public NuageToxique creerNuageToxique(Bloc position) {
        if (!carte.estCoordonneeValide(position.getX(), position.getY())) {
            return null;
        }
        return new NuageToxique(
            position,
            ConfigurationEvenement.NUAGETOXIQUE_IMPACT_DUREE,
            ConfigurationEvenement.NUAGETOXIQUE_IMPACT_TEMPERATURE,
            ConfigurationEvenement.NUAGETOXIQUE_IMPACT_HUMIDITE,
            ConfigurationEvenement.NUAGETOXIQUE_IMPACT_POLLUTION,
            ConfigurationEvenement.NUAGETOXIQUE_IMPACT_PURIFICATION
        );
    }
    
    public List<Evenement> creerNuageToxiqueMultiple(Bloc position) {
        List<Evenement> liste = new ArrayList<>();
        int x = position.getX();
        int y = position.getY();
        
        int dirIndex = random.nextInt(ConfigurationDirection.DIRECTIONS.length);
        int dirDx = ConfigurationDirection.DIRECTIONS[dirIndex][0];
        int dirDy = ConfigurationDirection.DIRECTIONS[dirIndex][1];
        
        int[] dx = {-1, 0, 1, -1, 1, -1, 0, 1};
        int[] dy = {-1, -1, -1, 0, 0, 1, 1, 1};
        
        for (int i = 0; i < 8; i++) {
            int nx = x + dx[i];
            int ny = y + dy[i];
            if (carte.estCoordonneeValide(nx, ny)) {
                NuageToxique e = creerNuageToxique(new Bloc(nx, ny));
                if (e != null) { e.setDirection(dirDx, dirDy); liste.add(e); }
            }
        }
        if (carte.estCoordonneeValide(x, y)) {
            NuageToxique e = creerNuageToxique(new Bloc(x, y));
            if (e != null) { e.setDirection(dirDx, dirDy); liste.add(e); }
        }
        
        return liste;
    }
    
    // ==================== UTILITAIRE ====================
    
    public Bloc getPositionAleatoire() {
        int ligne = random.nextInt(carte.getNombreLignes());
        int colonne = random.nextInt(carte.getNombreColonnes());
        return carte.getBloc(ligne, colonne);
    }

    private Bloc getPositionAleatoireMeteore() {
        int ligne = random.nextInt(carte.getNombreLignes());
        int colonne;
        if (carte.getNombreColonnes() > METEORE_DISTANCE_CHUTE) {
            colonne = METEORE_DISTANCE_CHUTE + random.nextInt(carte.getNombreColonnes() - METEORE_DISTANCE_CHUTE);
        } else {
            colonne = random.nextInt(carte.getNombreColonnes());
        }
        return carte.getBloc(ligne, colonne);
    }
    
}

package moteur.processus.visitor;

import config.ConfigurationCreationEvenement;
import config.ConfigurationDirection;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;
import moteur.donne.biome.Biome;
import moteur.donne.biome.Montagne;
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
import org.apache.log4j.Logger;
import util.LoggerUtility;

public class GestionDeplacementVisitor implements EvenementVisitor<Void> {
    
    private static final Logger logger = LoggerUtility.getLogger(GestionDeplacementVisitor.class);
    
    private final Carte carte;
    private final Map<Bloc, Biome> biomeMap;
    private final Random random = new Random();
    private final List<Evenement> evenementsExpirés = new ArrayList<>();
    
    public GestionDeplacementVisitor(Carte carte, Map<Bloc, Biome> biomeMap) {
        this.carte = carte;
        this.biomeMap = biomeMap;
    }
    
    public List<Evenement> getEvenementsExpirés() {
        return new ArrayList<>(evenementsExpirés);
    }
    
    @Override
    public Void visit(Pluie pluie) {
        deplacerEvenementMobile(pluie);
        return null;
    }
    
    @Override
    public Void visit(PluieAcide pluieAcide) {
        deplacerEvenementMobile(pluieAcide);
        return null;
    }
    
    @Override
    public Void visit(Pollution pollution) {
        deplacerEvenementMobile(pollution);
        return null;
    }
    
    @Override
    public Void visit(VentChaud ventChaud) {
        deplacerEvenementMobile(ventChaud);
        return null;
    }
    
    @Override
    public Void visit(VentFroid ventFroid) {
        deplacerEvenementMobile(ventFroid);
        return null;
    }
    
    @Override
    public Void visit(Meteore meteore) {
        traiterEvenementStatique(meteore);
        return null;
    }
    
    @Override
    public Void visit(Purification purification) {
        deplacerEvenementMobile(purification);
        return null;
    }
    
    @Override
    public Void visit(Orage orage) {
        deplacerEvenementMobile(orage);
        return null;
    }
    
    @Override
    public Void visit(Grele grele) {
        deplacerEvenementMobile(grele);
        return null;
    }
    
    @Override
    public Void visit(Tornade tornade) {
        deplacerEvenementMobile(tornade);
        return null;
    }
    
    @Override
    public Void visit(PluieBenite pluieBenite) {
        deplacerEvenementMobile(pluieBenite);
        return null;
    }
    
    @Override
    public Void visit(Zephyr zephyr) {
        deplacerEvenementMobile(zephyr);
        return null;
    }
    
    @Override
    public Void visit(Tonnerre tonnerre) {
        deplacerEvenementMobile(tonnerre);
        return null;
    }
    
    @Override
    public Void visit(Smog smog) {
        deplacerEvenementMobile(smog);
        return null;
    }
    
    @Override
    public Void visit(NuageToxique nuageToxique) {
        deplacerEvenementMobile(nuageToxique);
        return null;
    }

    private boolean doitSeTerminer(Evenement evenement) {
        if (evenement.getDureeRestante() > 0) {
            return false;
        }

        return random.nextInt(ConfigurationCreationEvenement.CHANCE_TERMINAISON_APRES_DUREE_MINIMALE) == 0;
    }
    
    private void deplacerEvenementMobile(Evenement evenement) {
        if (!evenement.estAnimationTerminee()) {
            return;
        }
        
        if (doitSeTerminer(evenement)) {
            logger.debug("[Expire] " + evenement.getClass().getSimpleName() + " en (" + 
                        evenement.getPosition().getX() + "," + evenement.getPosition().getY() + ")");
            evenementsExpirés.add(evenement);
            return;
        }
        
        Bloc position = evenement.getPosition();
        Bloc nouvellePosition = null;
        int dx, dy;
        
        // Utiliser la direction existante si elle existe, sinon aléatoire
        if (evenement.getDirectionX() != 0 || evenement.getDirectionY() != 0) {
            dx = evenement.getDirectionX();
            dy = evenement.getDirectionY();
        } else {
            int dirIndex = random.nextInt(ConfigurationDirection.DIRECTIONS.length);
            dx = ConfigurationDirection.DIRECTIONS[dirIndex][0];
            dy = ConfigurationDirection.DIRECTIONS[dirIndex][1];
            evenement.setDirection(dx, dy);
        }
        
        int nouvelleX = position.getX() + dx;
        int nouvelleY = position.getY() + dy;
        
        if (!carte.estCoordonneeValide(nouvelleX, nouvelleY)) {
            // Si hors carte, changer de direction
            int dirIndex = random.nextInt(ConfigurationDirection.DIRECTIONS.length);
            dx = ConfigurationDirection.DIRECTIONS[dirIndex][0];
            dy = ConfigurationDirection.DIRECTIONS[dirIndex][1];
            nouvelleX = position.getX() + dx;
            nouvelleY = position.getY() + dy;
            
            if (!carte.estCoordonneeValide(nouvelleX, nouvelleY)) {
                logger.warn("[HorsCarte] " + evenement.getClass().getSimpleName() + " en (" + 
                           position.getX() + "," + position.getY() + ")");
                return;
            }
        }
        
        Bloc candidate = carte.getBloc(nouvelleX, nouvelleY);
        Biome biomeDestination = biomeMap.get(candidate);
        
        if (biomeDestination instanceof Montagne) {
            // Si montagne, changer de direction
            int dirIndex = random.nextInt(ConfigurationDirection.DIRECTIONS.length);
            dx = ConfigurationDirection.DIRECTIONS[dirIndex][0];
            dy = ConfigurationDirection.DIRECTIONS[dirIndex][1];
            evenement.setDirection(dx, dy);
            
            nouvelleX = position.getX() + dx;
            nouvelleY = position.getY() + dy;
            
            if (!carte.estCoordonneeValide(nouvelleX, nouvelleY)) {
                return;
            }
            
            candidate = carte.getBloc(nouvelleX, nouvelleY);
            biomeDestination = biomeMap.get(candidate);
            
            if (biomeDestination instanceof Montagne) {
                logger.debug("[BloqueMontagne] " + evenement.getClass().getSimpleName() + " en (" + 
                           position.getX() + "," + position.getY() + ")");
                return;
            }
        }
        
        nouvellePosition = candidate;
        
        if (nouvellePosition == null) {
            return;
        }
        
        logger.debug("[Deplacement] " + evenement.getClass().getSimpleName() + " (" + 
                    position.getX() + "," + position.getY() + ") -> (" + 
                    nouvelleX + "," + nouvelleY + ")");
        
        evenement.definirPositionCible(nouvellePosition);
        if (evenement.getDureeRestante() > 0) {
            evenement.setDureeRestante(evenement.getDureeRestante() - 1);
        }
    }
    
    private void traiterEvenementStatique(Evenement evenement) {
        if (evenement.getDureeRestante() > 0) {
            evenement.setDureeRestante(evenement.getDureeRestante() - 1);
        } else if (doitSeTerminer(evenement)) {
            evenementsExpirés.add(evenement);
        }
    }
    
}
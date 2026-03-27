package moteur.processus.visitor;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;

import config.ConfigurationDirection;
import moteur.donne.biome.Biome;
import moteur.donne.biome.Montagne;
import moteur.donne.carte.Bloc;
import moteur.donne.carte.Carte;
import moteur.donne.evenement.Evenement;
import moteur.donne.evenement.mobile.GroupePluie;
import moteur.donne.evenement.mobile.GroupePluieAcide;
import moteur.donne.evenement.mobile.Pluie;
import moteur.donne.evenement.mobile.PluieAcide;
import moteur.donne.evenement.mobile.Pollution;
import moteur.donne.evenement.mobile.Purification;
import moteur.donne.evenement.mobile.VentChaud;
import moteur.donne.evenement.mobile.VentFroid;
import moteur.donne.evenement.statique.Meteore;

public class GestionDeplacementVisitor implements EvenementVisitor {
    
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
    public void visit(Pluie pluie) {
        deplacerEvenementMobile(pluie);
    }
    
    @Override
    public void visit(PluieAcide pluieAcide) {
        deplacerEvenementMobile(pluieAcide);
    }
    
    @Override
    public void visit(Pollution pollution) {
        deplacerEvenementMobile(pollution);
    }
    
    @Override
    public void visit(VentChaud ventChaud) {
        deplacerEvenementMobile(ventChaud);
    }
    
    @Override
    public void visit(VentFroid ventFroid) {
        deplacerEvenementMobile(ventFroid);
    }
    
    @Override
    public void visit(Meteore meteore) {
        traiterEvenementStatique(meteore);
    }
    
    @Override
    public void visit(Purification purification) {
        deplacerEvenementMobile(purification);
    }
    
    private void deplacerEvenementMobile(Evenement evenement) {
        if (!evenement.isAnimationComplete()) {
            return;
        }
        
        if (evenement.getDureeRestante() == 0) {
            evenementsExpirés.add(evenement);
            return;
        }
        
        Bloc position = evenement.getPosition();
        Bloc nouvellePosition = null;
        
        for (int i = 0; i < ConfigurationDirection.DIRECTIONS.length; i++) {
            int dirIndex = random.nextInt(ConfigurationDirection.DIRECTIONS.length);
            int dx = ConfigurationDirection.DIRECTIONS[dirIndex][0];
            int dy = ConfigurationDirection.DIRECTIONS[dirIndex][1];
            
            int nouvelleX = position.getX() + dx;
            int nouvelleY = position.getY() + dy;
            
            if (!carte.estCoordonneeValide(nouvelleX, nouvelleY)) {
                continue;
            }
            
            Bloc candidate = carte.getBloc(nouvelleX, nouvelleY);
            Biome biomeDestination = biomeMap.get(candidate);
            
            if (biomeDestination instanceof Montagne) {
                continue;
            }
            
            nouvellePosition = candidate;
            break;
        }
        
        if (nouvellePosition == null) {
            return;
        }
        
        evenement.setTargetPosition(nouvellePosition);
        evenement.setDureeRestante(evenement.getDureeRestante() - 1);
    }
    
    private void traiterEvenementStatique(Evenement evenement) {
        if (evenement.getDureeRestante() > 0) {
            evenement.setDureeRestante(evenement.getDureeRestante() - 1);
        } else {
            evenementsExpirés.add(evenement);
        }
    }
    
    @Override
    public void visit(GroupePluie groupePluie) {
        deplacerGroupe(groupePluie);
    }
    
    @Override
    public void visit(GroupePluieAcide groupePluieAcide) {
        deplacerGroupe(groupePluieAcide);
    }
    
    private void deplacerGroupe(Evenement groupe) {
        if (!groupe.isAnimationComplete()) {
            return;
        }
        
        if (groupe.getDureeRestante() == 0) {
            evenementsExpirés.add(groupe);
            return;
        }
        
        List<Evenement> unites = new ArrayList<>();
        if (groupe instanceof GroupePluie) {
            unites = new ArrayList<>(((GroupePluie) groupe).getPluieUnitaires());
        } else if (groupe instanceof GroupePluieAcide) {
            unites = new ArrayList<>(((GroupePluieAcide) groupe).getPluieAcideUnitaires());
        }
        
        Bloc positionPrincipale = groupe.getPosition();
        boolean deplacementReussi = false;
        
        for (int attempt = 0; attempt < ConfigurationDirection.DIRECTIONS.length; attempt++) {
            int dirIndex = random.nextInt(ConfigurationDirection.DIRECTIONS.length);
            int dx = ConfigurationDirection.DIRECTIONS[dirIndex][0];
            int dy = ConfigurationDirection.DIRECTIONS[dirIndex][1];
            
            boolean directionValide = true;
            boolean mortCollective = false;
            
            for (Evenement unite : unites) {
                Bloc posUnite = unite.getPosition();
                int nouvelleX = posUnite.getX() + dx;
                int nouvelleY = posUnite.getY() + dy;
                
                if (!carte.estCoordonneeValide(nouvelleX, nouvelleY)) {
                    mortCollective = true;
                    directionValide = false;
                    break;
                }
                
                Bloc candidate = carte.getBloc(nouvelleX, nouvelleY);
                Biome biomeDestination = biomeMap.get(candidate);
                
                if (biomeDestination instanceof Montagne) {
                    directionValide = false;
                    break;
                }
            }
            
            if (mortCollective) {
                evenementsExpirés.add(groupe);
                return;
            }
            
            if (directionValide) {
                for (Evenement unite : unites) {
                    Bloc posUnite = unite.getPosition();
                    int nouvelleX = posUnite.getX() + dx;
                    int nouvelleY = posUnite.getY() + dy;
                    Bloc nouvellePosition = carte.getBloc(nouvelleX, nouvelleY);
                    unite.setTargetPosition(nouvellePosition);
                }
                
                int nouvelleXPrincipale = positionPrincipale.getX() + dx;
                int nouvelleYPrincipale = positionPrincipale.getY() + dy;
                groupe.setTargetPosition(carte.getBloc(nouvelleXPrincipale, nouvelleYPrincipale));
                
                deplacementReussi = true;
                break;
            }
        }
        
        if (deplacementReussi) {
            groupe.setDureeRestante(groupe.getDureeRestante() - 1);
        }
    }
    
}

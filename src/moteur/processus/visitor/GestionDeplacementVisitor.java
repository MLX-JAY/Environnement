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
        
        int directionAleatoire = random.nextInt(ConfigurationDirection.DIRECTIONS.length);
        int dx = ConfigurationDirection.DIRECTIONS[directionAleatoire][0];
        int dy = ConfigurationDirection.DIRECTIONS[directionAleatoire][1];
        
        int nouvelleX = position.getX() + dx;
        int nouvelleY = position.getY() + dy;
        
        if (!carte.estCoordonneeValide(nouvelleX, nouvelleY)) {
            evenementsExpirés.add(evenement);
            return;
        }
        
        Bloc nouvellePosition = carte.getBloc(nouvelleX, nouvelleY);
        
        // Vérifier si une montagne bloque l'événement
        Biome biomeDestination = biomeMap.get(nouvellePosition);
        if (biomeDestination instanceof Montagne) {
            // L'événement est bloqué par la montagne, il reste sur place
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
    
}

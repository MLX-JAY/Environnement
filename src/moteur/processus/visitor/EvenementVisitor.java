package moteur.processus.visitor;
import moteur.donne.evenement.mobile.GroupePluie;
import moteur.donne.evenement.mobile.GroupePluieAcide;
import moteur.donne.evenement.mobile.Pluie;
import moteur.donne.evenement.mobile.PluieAcide;
import moteur.donne.evenement.mobile.Pollution;
import moteur.donne.evenement.mobile.Purification;
import moteur.donne.evenement.mobile.VentChaud;
import moteur.donne.evenement.mobile.VentFroid;
import moteur.donne.evenement.statique.Meteore;

public interface EvenementVisitor {
    
    void visit(Pluie pluie);
    
    void visit(PluieAcide pluieAcide);
    
    void visit(Pollution pollution);
    
    void visit(VentChaud ventChaud);
    
    void visit(VentFroid ventFroid);
    
    void visit(Meteore meteore);
    
    void visit(Purification purification);
    
    void visit(GroupePluie groupePluie);
    
    void visit(GroupePluieAcide groupePluieAcide);
    
}

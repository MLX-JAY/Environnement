package moteur.processus.visitor;

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

public interface EvenementVisitor<T> {
    
    T visit(Pluie pluie);
    
    T visit(PluieAcide pluieAcide);
    
    T visit(Pollution pollution);
    
    T visit(VentChaud ventChaud);
    
    T visit(VentFroid ventFroid);
    
    T visit(Meteore meteore);
    
    T visit(Purification purification);
    
    T visit(Orage orage);
    
    T visit(Grele grele);
    
    T visit(Tornade tornade);
    
    T visit(PluieBenite pluieBenite);
    
    T visit(Zephyr zephyr);
    
    T visit(Tonnerre tonnerre);
    
    T visit(Smog smog);
    
    T visit(NuageToxique nuageToxique);
    
}

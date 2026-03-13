package moteur.processus.visitor;

import moteur.donne.biome.Banquise;
import moteur.donne.biome.Desert;
import moteur.donne.biome.Foret;
import moteur.donne.biome.Mer;
import moteur.donne.biome.Montagne;
import moteur.donne.biome.Ville;
import moteur.donne.biome.Village;
import moteur.donne.evenement.Evenement;

public interface BiomeVisitor {
    
    Evenement visit(Foret foret);
    
    Evenement visit(Desert desert);
    
    Evenement visit(Mer mer);
    
    Evenement visit(Montagne montagne);
    
    Evenement visit(Ville ville);
    
    Evenement visit(Village village);
    
    Evenement visit(Banquise banquise);
    
}

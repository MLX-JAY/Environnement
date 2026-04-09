package moteur.processus.visitor;

import moteur.donne.biome.Banquise;
import moteur.donne.biome.Desert;
import moteur.donne.biome.Foret;
import moteur.donne.biome.Mer;
import moteur.donne.biome.Montagne;
import moteur.donne.biome.Ville;
import moteur.donne.biome.Village;

public interface BiomeVisitor<T> {
    
    T visit(Foret foret);
    
    T visit(Desert desert);
    
    T visit(Mer mer);
    
    T visit(Montagne montagne);
    
    T visit(Ville ville);
    
    T visit(Village village);
    
    T visit(Banquise banquise);
    
}

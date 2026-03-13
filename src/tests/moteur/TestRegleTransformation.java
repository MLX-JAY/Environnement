package tests.moteur;

import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import moteur.donne.biome.Biome;
import moteur.donne.biome.Foret;
import moteur.donne.biome.Desert;
import moteur.donne.biome.Mer;
import moteur.donne.carte.Bloc;
import moteur.processus.regle.RegleDesertification;
import moteur.processus.regle.RegleInondation;
import moteur.processus.regle.RegleGlaciation;

public class TestRegleTransformation {
    
    private Bloc position;
    
    @Before
    public void setUp() {
        position = new Bloc(5, 5);
    }
    
    @Test
    public void testRegleDesertification() {
        Foret foret = new Foret(85.0, 5.0, 5.0, 5.0, 0.0, position);
        RegleDesertification regle = new RegleDesertification();
        
        Biome resultat = regle.evaluer(foret);
        
        assertNotNull(resultat);
        assertTrue(resultat instanceof Desert);
    }
    
    @Test
    public void testRegleDesertificationNonRemplie() {
        Foret foret = new Foret(20.0, 10.0, 5.0, 50.0, 0.0, position);
        RegleDesertification regle = new RegleDesertification();
        
        assertNull(regle.evaluer(foret));
    }
    
    @Test
    public void testRegleInondation() {
        Foret foret = new Foret(20.0, 10.0, 5.0, 95.0, 0.0, position);
        RegleInondation regle = new RegleInondation();
        
        Biome resultat = regle.evaluer(foret);
        
        assertNotNull(resultat);
        assertTrue(resultat instanceof Mer);
    }
    
    @Test
    public void testRegleGlaciation() {
        Mer mer = new Mer(-5.0, 10.0, 5.0, 50.0, 0.0, position);
        RegleGlaciation regle = new RegleGlaciation();
        
        Biome resultat = regle.evaluer(mer);
        
        assertNotNull(resultat);
    }
}

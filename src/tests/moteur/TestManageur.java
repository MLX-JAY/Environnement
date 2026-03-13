package tests.moteur;

import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import moteur.donne.carte.Carte;
import moteur.donne.biome.Biome;
import moteur.processus.ManageurBasique;
import moteur.donne.evenement.Evenement;
import java.util.ArrayList;

public class TestManageur {
    
    private Carte carte;
    private ManageurBasique manageur;
    
    @Before
    public void setUp() {
        carte = new Carte(10, 10);
        manageur = new ManageurBasique(carte);
    }
    
    @Test
    public void testCarteHasard() {
        manageur.CarteHasard();
        
        ArrayList<Biome> biomes = manageur.getBiomes();
        assertEquals(100, biomes.size());
    }
    
    @Test
    public void testGetBiomes() {
        manageur.CarteHasard();
        
        ArrayList<Biome> biomes = manageur.getBiomes();
        assertNotNull(biomes);
        assertFalse(biomes.isEmpty());
    }
    
    @Test
    public void testAjouterEvenement() {
        for (int i = 0; i < 50; i++) {
            manageur.ajouterEvenement();
            if (manageur.getEvenements().size() > 0) {
                break;
            }
        }
        assertTrue(manageur.getEvenements().size() > 0);
    }
    
    @Test
    public void testTransformation() {
        manageur.CarteHasard();
        manageur.transformation();
        
        assertNotNull(manageur.getBiomes());
    }
}

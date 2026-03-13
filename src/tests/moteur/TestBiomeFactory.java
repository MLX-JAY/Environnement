package tests.moteur;

import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import moteur.donne.carte.Carte;
import moteur.donne.carte.Bloc;
import moteur.processus.usine.BiomeFactory;
import moteur.donne.biome.Biome;

public class TestBiomeFactory {
    
    private Carte carte;
    
    @Before
    public void setUp() {
        carte = new Carte(10, 10);
    }
    
    @Test
    public void testCreerBiomeAleatoire() {
        Bloc bloc = carte.getBloc(5, 5);
        Biome biome = BiomeFactory.creerBiomeAleatoire(bloc);
        
        assertNotNull(biome);
        assertEquals(bloc, biome.getPosition());
    }
    
    @Test
    public void testCreerBiomePositionValide() {
        for (int i = 0; i < 50; i++) {
            Bloc bloc = new Bloc(i % 10, i / 10);
            Biome biome = BiomeFactory.creerBiomeAleatoire(bloc);
            assertEquals(bloc, biome.getPosition());
        }
    }
}

package tests.moteur;

import java.util.Map;

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

    @Test
    public void testCreerBiomesCoherentsRemplitTouteLaCarte() {
        Map<Bloc, Biome> biomes = BiomeFactory.creerBiomesCoherents(carte);

        assertEquals(100, biomes.size());
        for (int i = 0; i < carte.getGrandeurX(); i++) {
            for (int j = 0; j < carte.getGrandeurY(); j++) {
                Bloc bloc = carte.getBloc(i, j);
                assertNotNull(biomes.get(bloc));
                assertEquals(bloc, biomes.get(bloc).getPosition());
            }
        }
    }
}

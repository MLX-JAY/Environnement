package tests.moteur;

import java.util.Map;

import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import moteur.donne.biome.Banquise;
import moteur.donne.carte.Carte;
import moteur.donne.carte.Bloc;
import moteur.donne.biome.Montagne;
import moteur.processus.usine.BiomeFactory;
import moteur.processus.usine.BiomeFactory.TypeBiome;
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
        for (int i = 0; i < carte.getNombreLignes(); i++) {
            for (int j = 0; j < carte.getNombreColonnes(); j++) {
                Bloc bloc = carte.getBloc(i, j);
                assertNotNull(biomes.get(bloc));
                assertEquals(bloc, biomes.get(bloc).getPosition());
            }
        }
    }

    @Test
    public void testCreerBiomeParTypeBanquiseEtMontagne() {
        Bloc blocBanquise = carte.getBloc(2, 2);
        Bloc blocMontagne = carte.getBloc(7, 7);

        Biome banquise = BiomeFactory.creerBiomeParType(TypeBiome.BANQUISE, blocBanquise);
        Biome montagne = BiomeFactory.creerBiomeParType(TypeBiome.MONTAGNE, blocMontagne);

        assertTrue(banquise instanceof Banquise);
        assertTrue(montagne instanceof Montagne);
        assertEquals(blocBanquise, banquise.getPosition());
        assertEquals(blocMontagne, montagne.getPosition());
    }
}

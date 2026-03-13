package tests.moteur;

import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import moteur.donne.carte.Carte;
import moteur.donne.carte.Bloc;

public class TestCarte {
    
    private Carte carte;
    
    @Before
    public void setUp() {
        carte = new Carte(10, 15);
    }
    
    @Test
    public void testConstructeurDimensions() {
        assertEquals(10, carte.getGrandeurX());
        assertEquals(15, carte.getGrandeurY());
    }
    
    @Test
    public void testGetBloc() {
        Bloc bloc = carte.getBloc(0, 0);
        assertNotNull(bloc);
        assertEquals(0, bloc.getX());
        assertEquals(0, bloc.getY());
    }
    
    @Test
    public void testEstCoordonneeValide() {
        assertTrue(carte.estCoordonneeValide(0, 0));
        assertTrue(carte.estCoordonneeValide(9, 14));
        assertFalse(carte.estCoordonneeValide(-1, 5));
        assertFalse(carte.estCoordonneeValide(10, 5));
    }
    
    @Test
    public void testEstSurBordure() {
        assertTrue(carte.estSurBordure(new Bloc(0, 0)));
        assertTrue(carte.estSurBordure(new Bloc(0, 14)));
        assertTrue(carte.estSurBordure(new Bloc(9, 0)));
        assertTrue(carte.estSurBordure(new Bloc(9, 14)));
        assertFalse(carte.estSurBordure(new Bloc(5, 7)));
    }
    
    @Test
    public void testGetBlocs() {
        Bloc[][] blocs = carte.getBlocs();
        assertNotNull(blocs);
        assertEquals(10, blocs.length);
        assertEquals(15, blocs[0].length);
    }
}

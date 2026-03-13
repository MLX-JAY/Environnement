package tests.moteur;

import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import moteur.donne.carte.Bloc;

public class TestBloc {
    
    private Bloc bloc;
    
    @Before
    public void setUp() {
        bloc = new Bloc(5, 10);
    }
    
    @Test
    public void testConstructeur() {
        assertEquals(5, bloc.getX());
        assertEquals(10, bloc.getY());
    }
    
    @Test
    public void testEquals() {
        Bloc bloc2 = new Bloc(5, 10);
        assertTrue(bloc.equals(bloc2));
        assertFalse(bloc.equals(new Bloc(3, 7)));
    }
}

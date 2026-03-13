package tests.moteur;

import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import moteur.donne.biome.Foret;
import moteur.donne.carte.Bloc;

public class TestBiome {
    
    private Foret foret;
    private Bloc position;
    
    @Before
    public void setUp() {
        position = new Bloc(5, 5);
        foret = new Foret(20.0, 10.0, 5.0, 50.0, 0.0, position);
    }
    
    @Test
    public void testConstructeur() {
        assertEquals(20.0, foret.getTemperature(), 0.01);
        assertEquals(10.0, foret.getPollution(), 0.01);
        assertEquals(5.0, foret.getPurification(), 0.01);
        assertEquals(50.0, foret.getHumidite(), 0.01);
        assertEquals(0.0, foret.getEvolution(), 0.01);
        assertEquals(position, foret.getPosition());
    }
    
    @Test
    public void testSetTemperature() {
        foret.setTemperature(30.0);
        assertEquals(30.0, foret.getTemperature(), 0.01);
    }
    
    @Test
    public void testSetHumidite() {
        foret.setHumidite(80.0);
        assertEquals(80.0, foret.getHumidite(), 0.01);
    }
    
    @Test
    public void testSetPosition() {
        Bloc nouvellePosition = new Bloc(3, 7);
        foret.setPosition(nouvellePosition);
        assertEquals(nouvellePosition, foret.getPosition());
    }
}

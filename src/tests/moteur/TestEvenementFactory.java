package tests.moteur;

import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import moteur.donne.carte.Carte;
import moteur.donne.carte.Bloc;
import moteur.processus.usine.EvenementFactory;
import moteur.donne.evenement.mobile.Pluie;
import moteur.donne.evenement.statique.Meteore;

public class TestEvenementFactory {
    
    private Carte carte;
    private EvenementFactory factory;
    
    @Before
    public void setUp() {
        carte = new Carte(10, 10);
        factory = new EvenementFactory(carte);
    }
    
    @Test
    public void testCreerPluie() {
        Bloc bloc = carte.getBloc(5, 5);
        Pluie pluie = factory.creerPluie(bloc);
        
        assertNotNull(pluie);
        assertEquals(bloc, pluie.getPosition());
    }
    
    @Test
    public void testCreerMeteore() {
        Bloc bloc = carte.getBloc(5, 5);
        Meteore meteore = factory.creerMeteore(bloc);
        
        assertNotNull(meteore);
        assertTrue(meteore.getImpactTemperature() > 0);
    }
    
    @Test
    public void testGetPositionAleatoire() {
        Bloc position = factory.getPositionAleatoire();
        
        assertNotNull(position);
        assertTrue(carte.estCoordonneeValide(position.getX(), position.getY()));
    }
}

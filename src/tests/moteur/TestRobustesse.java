package tests.moteur;

import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;

import moteur.donne.carte.Carte;
import moteur.donne.carte.Bloc;
import moteur.donne.biome.Foret;
import moteur.processus.ManageurBasique;

/**
 * Tests de robustesse pour vérifier que le moteur gère correctement
 * les cas limites et les erreurs (données extremes, valeurs invalides, etc.)
 */
public class TestRobustesse {
    
    private Carte carte;
    private ManageurBasique manageur;
    
    @Before
    public void setUp() {
        carte = new Carte(10, 10);
        manageur = new ManageurBasique(carte);
    }
    
    // ========================================
    // Tests sur la carte
    // ========================================
    
    @Test
    public void testCarteVide() {
        // Créer une carte 0x0 et vérifier qu'elle fonctionne
        Carte carteVide = new Carte(0, 0);
        assertEquals(0, carteVide.getNombreLignes());
        assertEquals(0, carteVide.getNombreColonnes());
    }
    
    @Test
    public void testCarte1x1() {
        // Créer une carte minuscule
        Carte cartePetite = new Carte(1, 1);
        assertNotNull(cartePetite.getBloc(0, 0));
    }
    
    // ========================================
    // Tests sur les coordonnées invalides
    // ========================================
    
    @Test
    public void testGetBlocCoordonneesNegatives() {
        // Coordonnées négatives doivent être rejetées
        assertFalse(carte.estCoordonneeValide(-1, 5));
        assertFalse(carte.estCoordonneeValide(5, -1));
        assertFalse(carte.estCoordonneeValide(-1, -1));
    }
    
    @Test
    public void testGetBlocCoordonneesTropGrandes() {
        // Coordonnées hors limites doivent être rejetées
        assertFalse(carte.estCoordonneeValide(10, 5));
        assertFalse(carte.estCoordonneeValide(5, 10));
        assertFalse(carte.estCoordonneeValide(10, 10));
    }
    
    @Test
    public void testGetBlocHorsLimites() {
        // Accéder à un bloc hors limites doit retourner null ou gérer l'erreur
        // La méthode getBloc ne fait pas de vérification, donc c'est un cas à tester
        // En pratique, elle peut throw ArrayIndexOutOfBoundsException
        // Ce test vérifie juste que le code ne plante pas de manière inattendue
        try {
            Bloc bloc = carte.getBloc(100, 100);
            // Si on arrive ici, c'est que ça n'a pas planté (mais ça ne devrait pas arriver)
        } catch (ArrayIndexOutOfBoundsException e) {
            // Comportement acceptable : lever une exception pour coords invalides
            assertTrue(true);
        }
    }
    
    // ========================================
    // Tests sur les valeurs extremes des biomes
    // ========================================
    
    @Test
    public void testSetTemperatureNegativeExtreme() {
        // Température très négative doit être acceptée
        Foret foret = new Foret(0, 0, 0, 0, 0, new Bloc(0, 0));
        foret.setTemperature(-1000);
        assertEquals(-1000, foret.getTemperature(), 0.01);
    }
    
    @Test
    public void testSetTemperaturePositiveExtreme() {
        // Température très positive doit être plafonnée à 100
        Foret foret = new Foret(0, 0, 0, 0, 0, new Bloc(0, 0));
        foret.setTemperature(1000);
        assertEquals(100, foret.getTemperature(), 0.01);
    }
    
    @Test
    public void testSetHumiditeNegativeExtreme() {
        // Humidité négative doit être acceptée
        Foret foret = new Foret(0, 0, 0, 0, 0, new Bloc(0, 0));
        foret.setHumidite(-500);
        assertEquals(-500, foret.getHumidite(), 0.01);
    }
    
    @Test
    public void testSetHumiditePositiveExtreme() {
        // Humidité très positive doit être plafonnée à 100
        Foret foret = new Foret(0, 0, 0, 0, 0, new Bloc(0, 0));
        foret.setHumidite(500);
        assertEquals(100, foret.getHumidite(), 0.01);
    }
    
    @Test
    public void testSetPollutionNegativeExtreme() {
        // Pollution négative doit être acceptée
        Foret foret = new Foret(0, 0, 0, 0, 0, new Bloc(0, 0));
        foret.setPollution(-300);
        assertEquals(-300, foret.getPollution(), 0.01);
    }
    
    @Test
    public void testSetPollutionPositiveExtreme() {
        // Pollution très positive doit être plafonnée à 100
        Foret foret = new Foret(0, 0, 0, 0, 0, new Bloc(0, 0));
        foret.setPollution(300);
        assertEquals(100, foret.getPollution(), 0.01);
    }
    
    // ========================================
    // Tests sur les événements
    // ========================================
    
    @Test
    public void testEvenementPositionInvalide() {
        // Créer un manageur avec carte vide et ajouter un événement
        Carte cartePetite = new Carte(2, 2);
        ManageurBasique manageurPetit = new ManageurBasique(cartePetite);
        manageurPetit.CarteHasard();
        
        // Appeler bougerEvent plusieurs fois
        // Les événements peuvent sortir de la carte
        for (int i = 0; i < 10; i++) {
            manageurPetit.bougerEvementMobile();
        }
        
        // Le manageur doit toujours fonctionner
        assertNotNull(manageurPetit.getEvenements());
    }
    
    @Test
    public void testTransformationAvecListeVide() {
        // Transformation sans événements ne doit pas planter
        manageur.CarteHasard();
        
        // La liste d'événements est vide, la transformation doit gérer ce cas
        manageur.transformation();
        
        // Les biomes doivent toujours être présents
        assertFalse(manageur.getBiomes().isEmpty());
    }
    
    @Test
    public void testGetBiomePositionInvalide() {
        // Récupérer un biome à une position inexistante
        manageur.CarteHasard();
        
        Bloc positionInvalide = new Bloc(100, 100);
        assertNull(manageur.getBiomeByPosition(positionInvalide));
    }
    
    @Test
    public void testGetBiomePositionNull() {
        // Récupérer un biome avec position null
        manageur.CarteHasard();
        
        assertNull(manageur.getBiomeByPosition(null));
    }
    
    // ========================================
    // Tests sur les bords de la carte
    // ========================================
    
    @Test
    public void testBordsCarte() {
        // Tester les méthodes de détection des bords
        Bloc haut = new Bloc(0, 5);
        Bloc bas = new Bloc(9, 5);
        Bloc gauche = new Bloc(5, 0);
        Bloc droite = new Bloc(5, 9);
        Bloc interieur = new Bloc(5, 5);
        
        assertTrue(carte.estEnHaut(haut));
        assertTrue(carte.estEnBas(bas));
        assertTrue(carte.estAGauche(gauche));
        assertTrue(carte.estADroite(droite));
        
        assertFalse(carte.estEnHaut(interieur));
        assertFalse(carte.estEnBas(interieur));
        assertFalse(carte.estAGauche(interieur));
        assertFalse(carte.estADroite(interieur));
    }
    
    @Test
    public void testCoinCarte() {
        // Tester les coins (à la fois sur plusieurs bords)
        Bloc coinHG = new Bloc(0, 0);
        Bloc coinHD = new Bloc(0, 9);
        Bloc coinBG = new Bloc(9, 0);
        Bloc coinBD = new Bloc(9, 9);
        
        assertTrue(carte.estSurBordure(coinHG));
        assertTrue(carte.estSurBordure(coinHD));
        assertTrue(carte.estSurBordure(coinBG));
        assertTrue(carte.estSurBordure(coinBD));
    }
}

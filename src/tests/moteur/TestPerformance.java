package tests.moteur;

import org.junit.Test;
import org.junit.Before;
import org.junit.After;
import static org.junit.Assert.*;

import moteur.donne.carte.Carte;
import moteur.processus.ManageurBasique;
import moteur.donne.biome.Biome;
import moteur.donne.evenement.Evenement;
import java.util.ArrayList;

/**
 * Test de performance pour le moteur de l'automate cellulaire.
 * 
 * Ce test vérifie que le moteur peut gérer une grande carte (1000x1000)
 * et exécuter 100 tours de simulation dans un temps raisonnable.
 * 
 * But du test :
 * - Prouver que le code est optimisé pour les grandes échelles
 * - Mesurer le temps d'exécution de la simulation
 * - Identifier d'éventuels goulots d'étranglement
 */
public class TestPerformance {
    
    // Paramètres du test de performance
    private static final int TAILLE_CARTE = 1000;      // Carte de 1000x1000 = 1 million de blocs
    private static final int NB_TOURS = 100;             // Nombre de tours à simuler
    
    private Carte carte;
    private ManageurBasique manageur;
    
    // Variables pour mesurer le temps
    private long tempsDebut;
    private long tempsFin;
    private long tempsTotal;
    
    @Before
    public void setUp() {
        // Création de la carte géante (1000x1000)
        // Cela représente 1 000 000 de cellules à gérer
        carte = new Carte(TAILLE_CARTE, TAILLE_CARTE);
        manageur = new ManageurBasique(carte);
    }
    
    @Test
    public void testPerformanceSimulation1000x1000() {
        
        // ========================================
        // ÉTAPE 1 : Création de la carte aléatoire
        // ========================================
        System.out.println("=== TEST DE PERFORMANCE ===");
        System.out.println("Taille de la carte : " + TAILLE_CARTE + " x " + TAILLE_CARTE);
        System.out.println("Nombre de tours : " + NB_TOURS);
        System.out.println("");
        
        System.out.println("1. Création de la carte avec biomes aléatoires...");
        
        // Création de tous les biomes sur la carte
        // Cela équivaut à peupler 1 million de cellules
        manageur.CarteHasard();
        
        // Vérification que la carte est bien remplie
        ArrayList<Biome> biomes = manageur.getBiomes();
        System.out.println("   -> Nombre de biomes créés : " + biomes.size());
        
        // ========================================
        // ÉTAPE 2 : Démarrage du chronomètre
        // ========================================
        System.out.println("");
        System.out.println("2. Démarrage de la simulation...");
        
        // System.currentTimeMillis() retourne le temps actuel en millisecondes
        // C'est une méthode simple et efficace pour mesurer le temps d'exécution
        tempsDebut = System.currentTimeMillis();
        
        // ========================================
        // ÉTAPE 3 : Boucle de simulation (100 tours)
        // ========================================
        // Chaque tour = génération d'événements + déplacement + transformation des biomes
        for (int i = 0; i < NB_TOURS; i++) {
            manageur.nextRound();
            
            // Afficher la progression tous les 10 tours
            if ((i + 1) % 10 == 0) {
                System.out.println("   -> Tour " + (i + 1) + " / " + NB_TOURS + " terminé");
            }
        }
        
        // ========================================
        // ÉTAPE 4 : Arrêt du chronomètre
        // ========================================
        tempsFin = System.currentTimeMillis();
        tempsTotal = tempsFin - tempsDebut;
        
        // ========================================
        // ÉTAPE 5 : Affichage des résultats
        // ========================================
        System.out.println("");
        System.out.println("=== RÉSULTATS DU TEST ===");
        System.out.println("Temps total d'exécution : " + tempsTotal + " ms");
        System.out.println("Temps moyen par tour : " + (tempsTotal / NB_TOURS) + " ms");
        System.out.println("Nombre d'événements générés : " + manageur.getEvenements().size());
        
        // Calcul du nombre de cellules traitées par seconde
        // (100 tours × 1 000 000 de cellules) / temps en secondes
        double secondes = tempsTotal / 1000.0;
        double cellulesParSeconde = (TAILLE_CARTE * TAILLE_CARTE * NB_TOURS) / secondes;
        System.out.println("Cellules traitées par seconde : " + String.format("%.0f", cellulesParSeconde));
        
        // ========================================
        // ÉTAPE 6 : Vérifications
        // ========================================
        // Le test passe si :
        // 1. La carte a encore des biomes (pas d'erreur)
        // 2. Le temps total est raisonnable (< 60 secondes pour ce test)
        
        assertFalse("La carte doit avoir des biomes après simulation", biomes.isEmpty());
        assertTrue("Le temps d'exécution doit être raisonnable (< 60 secondes)", tempsTotal < 60000);
        
        System.out.println("");
        System.out.println("=== TEST RÉUSSI ===");
    }
    
    @Test
    public void testPerformanceGenerationEvenements() {
        // Test spécifique pour mesurer la génération d'événements
        
        manageur.CarteHasard();
        
        System.out.println("");
        System.out.println("=== TEST: Performance génération événements ===");
        
        tempsDebut = System.currentTimeMillis();
        
        // Générer beaucoup d'événements
        for (int i = 0; i < 50; i++) {
            manageur.ajouterEvenement();
        }
        
        tempsFin = System.currentTimeMillis();
        tempsTotal = tempsFin - tempsDebut;
        
        System.out.println("Temps pour générer 50 événements : " + tempsTotal + " ms");
        System.out.println("Nombre d'événements créés : " + manageur.getEvenements().size());
    }
    
    @Test
    public void testPerformanceTransformationBiomes() {
        // Test spécifique pour mesurer les transformations de biomes
        
        manageur.CarteHasard();
        
        // D'abord générer des événements
        for (int i = 0; i < 10; i++) {
            manageur.ajouterEvenement();
        }
        
        System.out.println("");
        System.out.println("=== TEST: Performance transformation biomes ===");
        
        tempsDebut = System.currentTimeMillis();
        
        // Lancer les transformations
        manageur.transformation();
        
        tempsFin = System.currentTimeMillis();
        tempsTotal = tempsFin - tempsDebut;
        
        System.out.println("Temps pour transformer les biomes : " + tempsTotal + " ms");
        
        // Vérifier que la transformation s'est exécutée
        assertNotNull(manageur.getBiomes());
    }
    
    @After
    public void tearDown() {
        // Nettoyage après le test
        // Pas strictement nécessaire pour ce test, mais bonne pratique
        carte = null;
        manageur = null;
    }
}

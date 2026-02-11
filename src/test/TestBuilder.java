package test;

import java.util.ArrayList;

import moteur.processus.Builder;
import moteur.processus.Manageur;
import moteur.processus.ManageurBasique;
import moteur.donne.carte.Carte;
import moteur.donne.biome.Biome;
import config.GameConfiguration; // Assure-toi que l'import est bon

public class TestBuilder {

    public static void main(String[] args) {
        System.out.println("=== TEST DU BUILDER ===");

        // ---------------------------------------------------------
        // ÉTAPE 1 : Test de la création de la carte
        // ---------------------------------------------------------
        System.out.println("\n[1] Création de la Carte via le Builder...");
        
        Carte maCarte = Builder.construireCarte();

        if (maCarte != null) {
            System.out.println(" -> Carte créée avec succès !");
            // Vérification des dimensions (si tu as des getters)
            System.out.println(" -> Dimensions : " + maCarte.getGrandeurX() + "x" + maCarte.getGrandeurY());
        } else {
            System.out.println(" -> ERREUR : La carte est null !");
            return; // On arrête le test ici si ça rate
        }

        // ---------------------------------------------------------
        // ÉTAPE 2 : Test de l'initialisation du Manageur
        // ---------------------------------------------------------
        System.out.println("\n[2] Initialisation du Manageur et remplissage...");

        Manageur monManageur = Builder.initCarte(maCarte);

        if (monManageur != null) {
            System.out.println(" -> Manageur instancié avec succès !");
            
            // Pour vérifier si les biomes sont là, on doit regarder dans le ManageurBasique
            // On fait un "cast" car l'interface 'Manageur' n'a pas forcément getBiomes()
            if (monManageur instanceof ManageurBasique) {
                Manageur basique = monManageur;
                ArrayList<Biome> biomes = basique.getBiomes();

                int totalAttendu = GameConfiguration.NOMBRE_LIGNES * GameConfiguration.NOMBRE_COLONNES;
                int totalReel = biomes.size();

                System.out.println(" -> Biomes générés : " + totalReel + " (Attendu : " + totalAttendu + ")");

                if (totalReel == totalAttendu && totalReel > 0) {
                    System.out.println(" -> SUCCÈS : La carte est bien remplie de biomes !");
                    
                    // Petit bonus : afficher le premier biome pour être sûr
                    System.out.println(" -> Exemple case (0,0) : " + biomes.get(0).getClass().getSimpleName());
                } else {
                    System.out.println(" -> ÉCHEC : Le nombre de biomes est incorrect.");
                }
            }
        } else {
            System.out.println(" -> ERREUR : Le Manageur est null !");
        }
        
        System.out.println("\n=== FIN DU TEST ===");
    }
}
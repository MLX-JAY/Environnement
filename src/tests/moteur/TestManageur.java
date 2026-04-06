package tests.moteur;

import java.util.ArrayDeque;
import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import moteur.donne.carte.Carte;
import moteur.donne.carte.Bloc;
import moteur.donne.biome.Biome;
import moteur.donne.biome.Desert;
import moteur.donne.biome.Foret;
import moteur.donne.biome.Mer;
import moteur.donne.biome.Ville;
import moteur.processus.ManageurBasique;
import java.util.ArrayList;

public class TestManageur {
    
    private Carte carte;
    private ManageurBasique manageur;
    
    @Before
    public void setUp() {
        carte = new Carte(10, 10);
        manageur = new ManageurBasique(carte);
    }
    
    @Test
    public void testCarteHasard() {
        manageur.CarteHasard();
        
        ArrayList<Biome> biomes = manageur.getBiomes();
        assertEquals(100, biomes.size());
    }
    
    @Test
    public void testGetBiomes() {
        manageur.CarteHasard();
        
        ArrayList<Biome> biomes = manageur.getBiomes();
        assertNotNull(biomes);
        assertFalse(biomes.isEmpty());
    }
    
    @Test
    public void testAjouterEvenement() {
        for (int i = 0; i < 50; i++) {
            manageur.ajouterEvenement();
            if (!manageur.getEvenements().isEmpty()) {
                break;
            }
        }
        assertTrue(!manageur.getEvenements().isEmpty());
    }
    
    @Test
    public void testTransformation() {
        manageur.CarteHasard();
        manageur.transformation();
        
        assertNotNull(manageur.getBiomes());
    }

    @Test
    public void testCarteHasardGenereUneSeuleMasseDEau() {
        manageur.CarteHasard();

        boolean[][] eau = new boolean[carte.getGrandeurX()][carte.getGrandeurY()];
        int nbCasesEau = 0;
        Bloc depart = null;

        for (Biome biome : manageur.getBiomes()) {
            if (biome instanceof Mer) {
                Bloc position = biome.getPosition();
                eau[position.getX()][position.getY()] = true;
                nbCasesEau++;
                if (depart == null) {
                    depart = position;
                }
            }
        }

        assertTrue(nbCasesEau > 0);
        assertEquals(nbCasesEau, compterMasseEauConnectee(eau, depart));
    }

    @Test
    public void testDesertJamaisColleALaMer() {
        manageur.CarteHasard();

        for (Biome biome : manageur.getBiomes()) {
            if (!(biome instanceof Desert)) {
                continue;
            }

            Bloc position = biome.getPosition();
            assertFalse(estAdjacentALaMer(position));
        }
    }

    @Test
    public void testCarteHasardPlaceLeauDansUnCoin() {
        manageur.CarteHasard();

        assertTrue(correspondAUnScenarioDEauValide());
    }

    @Test
    public void testCarteHasardPlaceAussiDeLeauVersLeMilieu() {
        boolean vuCoin = false;
        boolean vuHaut = false;
        boolean vuMilieu = false;

        for (int tentative = 0; tentative < 40; tentative++) {
            manageur.CarteHasard();
            if (estDeLeauDansUnCoin()) {
                vuCoin = true;
            }
            if (estDeLeauEnHautAGaucheOuADroite()) {
                vuHaut = true;
            }
            if (estUnLacInterieur()) {
                vuMilieu = true;
            }
        }

        assertTrue(vuCoin);
        assertTrue(vuHaut);
        assertTrue(vuMilieu);
    }

    @Test
    public void testCarteHasardGenereDesVillesDesLeDebut() {
        manageur.CarteHasard();

        int nbVilles = 0;
        int nbDeserts = 0;
        int nbForets = 0;
        for (Biome biome : manageur.getBiomes()) {
            if (biome instanceof Ville) {
                nbVilles++;
            }
            if (biome instanceof Desert) {
                nbDeserts++;
            }
            if (biome instanceof Foret) {
                nbForets++;
            }
        }

        assertTrue(nbVilles > 0);
        assertTrue(nbDeserts >= (carte.getGrandeurX() * carte.getGrandeurY() * 0.15));
        assertTrue(nbForets < (carte.getGrandeurX() * carte.getGrandeurY() * 0.55));
        assertTrue(compterZonesDesertiques() >= 2);
    }

    private int compterMasseEauConnectee(boolean[][] eau, Bloc depart) {
        boolean[][] visite = new boolean[eau.length][eau[0].length];
        ArrayDeque<Bloc> file = new ArrayDeque<>();
        int compteur = 0;

        file.add(depart);
        visite[depart.getX()][depart.getY()] = true;

        while (!file.isEmpty()) {
            Bloc courant = file.removeFirst();
            compteur++;

            for (int[] direction : new int[][] { { 1, 0 }, { -1, 0 }, { 0, 1 }, { 0, -1 } }) {
                int voisinX = courant.getX() + direction[0];
                int voisinY = courant.getY() + direction[1];

                if (carte.estCoordonneeValide(voisinX, voisinY)
                    && eau[voisinX][voisinY]
                    && !visite[voisinX][voisinY]) {
                    visite[voisinX][voisinY] = true;
                    file.add(carte.getBloc(voisinX, voisinY));
                }
            }
        }

        return compteur;
    }

    private boolean estAdjacentALaMer(Bloc position) {
        for (int[] direction : new int[][] { { 1, 0 }, { -1, 0 }, { 0, 1 }, { 0, -1 } }) {
            int voisinX = position.getX() + direction[0];
            int voisinY = position.getY() + direction[1];

            if (!carte.estCoordonneeValide(voisinX, voisinY)) {
                continue;
            }

            Biome voisin = manageur.getBiomeByPosition(carte.getBloc(voisinX, voisinY));
            if (voisin instanceof Mer) {
                return true;
            }
        }

        return false;
    }

    private boolean estDeLeauDansUnCoin() {
        return manageur.getBiomeByPosition(carte.getBloc(0, 0)) instanceof Mer
            || manageur.getBiomeByPosition(carte.getBloc(0, carte.getGrandeurY() - 1)) instanceof Mer
            || manageur.getBiomeByPosition(carte.getBloc(carte.getGrandeurX() - 1, 0)) instanceof Mer
            || manageur.getBiomeByPosition(carte.getBloc(carte.getGrandeurX() - 1, carte.getGrandeurY() - 1)) instanceof Mer;
    }

    private boolean estDeLeauAuMilieu() {
        int minX = carte.getGrandeurX() / 3;
        int maxX = (carte.getGrandeurX() * 2) / 3;
        int minY = carte.getGrandeurY() / 3;
        int maxY = (carte.getGrandeurY() * 2) / 3;

        for (int i = minX; i <= maxX; i++) {
            for (int j = minY; j <= maxY; j++) {
                if (manageur.getBiomeByPosition(carte.getBloc(i, j)) instanceof Mer) {
                    return true;
                }
            }
        }

        return false;
    }

    private boolean estDeLeauEnHautAGaucheOuADroite() {
        int limiteGauche = Math.max(0, carte.getGrandeurY() / 3);
        int limiteDroite = Math.max(0, (carte.getGrandeurY() * 2) / 3);

        for (int j = 0; j <= limiteGauche; j++) {
            if (manageur.getBiomeByPosition(carte.getBloc(0, j)) instanceof Mer) {
                return true;
            }
        }
        for (int j = limiteDroite; j < carte.getGrandeurY(); j++) {
            if (manageur.getBiomeByPosition(carte.getBloc(0, j)) instanceof Mer) {
                return true;
            }
        }

        return false;
    }

    private boolean estUnLacInterieur() {
        return estDeLeauAuMilieu() && !estDeLeauSurLaBordure();
    }

    private boolean estDeLeauSurLaBordure() {
        for (int i = 0; i < carte.getGrandeurX(); i++) {
            if (manageur.getBiomeByPosition(carte.getBloc(i, 0)) instanceof Mer
                || manageur.getBiomeByPosition(carte.getBloc(i, carte.getGrandeurY() - 1)) instanceof Mer) {
                return true;
            }
        }
        for (int j = 0; j < carte.getGrandeurY(); j++) {
            if (manageur.getBiomeByPosition(carte.getBloc(0, j)) instanceof Mer
                || manageur.getBiomeByPosition(carte.getBloc(carte.getGrandeurX() - 1, j)) instanceof Mer) {
                return true;
            }
        }

        return false;
    }

    private boolean correspondAUnScenarioDEauValide() {
        return estDeLeauDansUnCoin() || estDeLeauEnHautAGaucheOuADroite() || estUnLacInterieur();
    }

    private int compterZonesDesertiques() {
        boolean[][] desert = new boolean[carte.getGrandeurX()][carte.getGrandeurY()];

        for (Biome biome : manageur.getBiomes()) {
            if (biome instanceof Desert) {
                Bloc position = biome.getPosition();
                desert[position.getX()][position.getY()] = true;
            }
        }

        boolean[][] visite = new boolean[carte.getGrandeurX()][carte.getGrandeurY()];
        int zones = 0;

        for (int i = 0; i < carte.getGrandeurX(); i++) {
            for (int j = 0; j < carte.getGrandeurY(); j++) {
                if (desert[i][j] && !visite[i][j]) {
                    zones++;
                    marquerZoneDesertique(desert, visite, i, j);
                }
            }
        }

        return zones;
    }

    private void marquerZoneDesertique(boolean[][] desert, boolean[][] visite, int departX, int departY) {
        ArrayDeque<Bloc> file = new ArrayDeque<>();
        file.add(carte.getBloc(departX, departY));
        visite[departX][departY] = true;

        while (!file.isEmpty()) {
            Bloc courant = file.removeFirst();
            for (int[] direction : new int[][] { { 1, 0 }, { -1, 0 }, { 0, 1 }, { 0, -1 } }) {
                int voisinX = courant.getX() + direction[0];
                int voisinY = courant.getY() + direction[1];

                if (carte.estCoordonneeValide(voisinX, voisinY)
                    && desert[voisinX][voisinY]
                    && !visite[voisinX][voisinY]) {
                    visite[voisinX][voisinY] = true;
                    file.add(carte.getBloc(voisinX, voisinY));
                }
            }
        }
    }
}

package moteur.processus.usine;

import config.ConfigurationBiome;
import java.util.ArrayDeque;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import moteur.donne.biome.Banquise;
import moteur.donne.biome.Biome;
import moteur.donne.biome.Desert;
import moteur.donne.biome.Foret;
import moteur.donne.biome.Mer;
import moteur.donne.biome.Montagne;
import moteur.donne.biome.Village;
import moteur.donne.biome.Ville;
import moteur.donne.carte.Bloc;
import moteur.donne.carte.Carte;

public class BiomeFactory {

    private static final int[][] DIRECTIONS = {
        { 1, 0 },
        { -1, 0 },
        { 0, 1 },
        { 0, -1 }
    };
    private static final Random RANDOM = new Random();

    public enum TypeBiome {
        FORET,
        DESERT,
        MER,
        BANQUISE,
        MONTAGNE,
        VILLAGE,
        VILLE
    }

    private enum ScenarioEau {
        COIN,
        HAUT,
        MILIEU
    }
    
    public static Biome creerBiomeParType(TypeBiome type, Bloc bloc) {
        return creerBiome(type, bloc);
    }
    
    public static Biome creerBiomeAleatoire(Bloc bloc) {
        TypeBiome[] types = TypeBiome.values();
        return creerBiome(types[RANDOM.nextInt(types.length)], bloc);
    }

    public static Map<Bloc, Biome> creerBiomesCoherents(Carte carte) {
        Map<Bloc, Biome> biomes = new HashMap<>();
        int nombreLignes = carte.getNombreLignes();
        int nombreColonnes = carte.getNombreColonnes();

        if (nombreLignes == 0 || nombreColonnes == 0) {
            return biomes;
        }

        boolean[][] eau = genererMasseEauConnectee(nombreLignes, nombreColonnes, RANDOM);
        int[][] distanceAEau = calculerDistanceAEau(eau);
        boolean[][] desert = genererZonesDesertiques(eau, distanceAEau, RANDOM);
        boolean[][] village = genererZoneVillageoise(eau, desert, distanceAEau, RANDOM);
        boolean[][] ville = genererZoneUrbaine(eau, desert, village, distanceAEau, RANDOM);
        boolean[][] banquise = genererZonesBanquise(eau, RANDOM);
        boolean[][] montagne = genererZonesMontagne(eau, desert, village, ville, distanceAEau, RANDOM);

        for (int i = 0; i < nombreLignes; i++) {
            for (int j = 0; j < nombreColonnes; j++) {
                Bloc bloc = carte.getBloc(i, j);
                TypeBiome type = TypeBiome.FORET;

                if (banquise[i][j]) {
                    type = TypeBiome.BANQUISE;
                } else if (eau[i][j]) {
                    type = TypeBiome.MER;
                } else if (montagne[i][j]) {
                    type = TypeBiome.MONTAGNE;
                } else if (desert[i][j]) {
                    type = TypeBiome.DESERT;
                } else if (ville[i][j]) {
                    type = TypeBiome.VILLE;
                } else if (village[i][j]) {
                    type = TypeBiome.VILLAGE;
                }

                biomes.put(bloc, creerBiome(type, bloc));
            }
        }

        return biomes;
    }

    private static boolean[][] genererMasseEauConnectee(int nombreLignes, int nombreColonnes, Random random) {
        boolean[][] eau = new boolean[nombreLignes][nombreColonnes];
        ScenarioEau scenario = ScenarioEau.values()[random.nextInt(ScenarioEau.values().length)];

        switch (scenario) {
            case HAUT:
                genererEauEnHaut(eau, random);
                break;
            case MILIEU:
                genererLacAuMilieu(eau, random);
                break;
            case COIN:
            default:
                genererEauDepuisUnCoin(eau, random);
                break;
        }

        return eau;
    }

    private static void genererEauDepuisUnCoin(boolean[][] eau, Random random) {
        int coin = random.nextInt(4);
        double rayonX = Math.max(2.0, eau.length / 3.1);
        double rayonY = Math.max(2.0, eau[0].length / 3.3);
        double phase = random.nextDouble() * Math.PI;

        for (int i = 0; i < eau.length; i++) {
            for (int j = 0; j < eau[i].length; j++) {
                int distanceX = calculerDistanceDepuisCoinX(i, eau.length, coin);
                int distanceY = calculerDistanceDepuisCoinY(j, eau[i].length, coin);
                double distanceNormalisee = Math.pow(distanceX / rayonX, 2)
                    + Math.pow(distanceY / rayonY, 2);
                double littoral = 1.0 + Math.sin((distanceX + distanceY) * 0.65 + phase) * 0.12;

                if (distanceNormalisee <= littoral) {
                    eau[i][j] = true;
                }
            }
        }

        if (random.nextBoolean()) {
            prolongerEauVersMilieu(eau, coin, random);
        }
    }

    private static void genererEauEnHaut(boolean[][] eau, Random random) {
        boolean aGauche = random.nextBoolean();
        double centreX = 0;
        double centreY = aGauche ? eau[0].length / 4.0 : (eau[0].length * 3.0) / 4.0;
        double rayonX = Math.max(2.0, eau.length / 4.2);
        double rayonY = Math.max(2.0, eau[0].length / 4.0);

        for (int i = 0; i < eau.length; i++) {
            for (int j = 0; j < eau[i].length; j++) {
                double distanceNormalisee = Math.pow((i - centreX) / rayonX, 2)
                    + Math.pow((j - centreY) / rayonY, 2);
                if (distanceNormalisee <= 1.0) {
                    eau[i][j] = true;
                }
            }
        }
    }

    private static void genererLacAuMilieu(boolean[][] eau, Random random) {
        double centreX = eau.length / 2.0 + (random.nextDouble() - 0.5) * Math.max(1.0, eau.length / 8.0);
        double centreY = eau[0].length / 2.0 + (random.nextDouble() - 0.5) * Math.max(1.0, eau[0].length / 8.0);
        double rayonX = Math.max(1.5, eau.length / 5.5);
        double rayonY = Math.max(1.5, eau[0].length / 5.5);

        for (int i = 1; i < eau.length - 1; i++) {
            for (int j = 1; j < eau[i].length - 1; j++) {
                double distanceNormalisee = Math.pow((i - centreX) / rayonX, 2)
                    + Math.pow((j - centreY) / rayonY, 2);
                if (distanceNormalisee <= 1.0) {
                    eau[i][j] = true;
                }
            }
        }
    }

    private static void prolongerEauVersMilieu(boolean[][] eau, int coin, Random random) {
        int[] source = trouverSourcePourBrasDEau(eau, coin);
        int[] cible = determinerCibleCentrale(eau.length, eau[0].length, random);

        if (source == null || cible.length == 0) {
            return;
        }

        tracerBrasDEau(eau, source, cible, random);
    }

    private static int[] trouverSourcePourBrasDEau(boolean[][] eau, int coin) {
        int[] meilleureSource = null;
        int meilleurScore = Integer.MIN_VALUE;

        for (int i = 0; i < eau.length; i++) {
            for (int j = 0; j < eau[i].length; j++) {
                if (!eau[i][j]) {
                    continue;
                }

                int score = calculerDistanceDepuisCoinX(i, eau.length, coin)
                    + calculerDistanceDepuisCoinY(j, eau[i].length, coin);
                if (score > meilleurScore) {
                    meilleurScore = score;
                    meilleureSource = new int[] { i, j };
                }
            }
        }

        return meilleureSource;
    }

    private static int[] determinerCibleCentrale(int nombreLignes, int nombreColonnes, Random random) {
        if (nombreLignes == 0 || nombreColonnes == 0) {
            return new int[0];
        }

        int minX = Math.max(0, nombreLignes / 3);
        int maxX = Math.max(minX, (nombreLignes * 2) / 3);
        int minY = Math.max(0, nombreColonnes / 3);
        int maxY = Math.max(minY, (nombreColonnes * 2) / 3);

        int cibleX = minX + random.nextInt(Math.max(1, maxX - minX + 1));
        int cibleY = minY + random.nextInt(Math.max(1, maxY - minY + 1));
        return new int[] { cibleX, cibleY };
    }

    private static void tracerBrasDEau(boolean[][] eau, int[] source, int[] cible, Random random) {
        int x = source[0];
        int y = source[1];
        int largeur = 0;

        dessinerEauAutour(eau, x, y, largeur);

        while (x != cible[0] || y != cible[1]) {
            if (x != cible[0] && (y == cible[1] || random.nextBoolean())) {
                x += Integer.compare(cible[0], x);
            } else if (y != cible[1]) {
                y += Integer.compare(cible[1], y);
            }

            dessinerEauAutour(eau, x, y, largeur);
            if (random.nextInt(7) == 0 || (x == cible[0] && y == cible[1])) {
                dessinerEauAutour(eau, x, y, 1);
            }
        }
    }

    private static void dessinerEauAutour(boolean[][] eau, int centreX, int centreY, int rayon) {
        for (int i = centreX - rayon; i <= centreX + rayon; i++) {
            for (int j = centreY - rayon; j <= centreY + rayon; j++) {
                if (i >= 0 && i < eau.length && j >= 0 && j < eau[i].length) {
                    eau[i][j] = true;
                }
            }
        }
    }

    private static int[][] calculerDistanceAEau(boolean[][] eau) {
        int nombreLignes = eau.length;
        int nombreColonnes = nombreLignes == 0 ? 0 : eau[0].length;
        int[][] distances = new int[nombreLignes][nombreColonnes];
        ArrayDeque<int[]> file = new ArrayDeque<>();

        initialiserDistances(eau, distances, file);

        while (!file.isEmpty()) {
            int[] cellule = file.removeFirst();
            propagerDistance(cellule, distances, file);
        }

        return distances;
    }

    private static void initialiserDistances(boolean[][] eau, int[][] distances, ArrayDeque<int[]> file) {
        for (int i = 0; i < eau.length; i++) {
            for (int j = 0; j < eau[i].length; j++) {
                distances[i][j] = eau[i][j] ? 0 : Integer.MAX_VALUE;
                if (eau[i][j]) {
                    file.add(new int[] { i, j });
                }
            }
        }
    }

    private static void propagerDistance(int[] cellule, int[][] distances, ArrayDeque<int[]> file) {
        int nombreLignes = distances.length;
        int nombreColonnes = nombreLignes == 0 ? 0 : distances[0].length;

        for (int[] direction : DIRECTIONS) {
            int voisinX = cellule[0] + direction[0];
            int voisinY = cellule[1] + direction[1];

            if (voisinX >= 0 && voisinX < nombreLignes && voisinY >= 0 && voisinY < nombreColonnes
                && distances[voisinX][voisinY] > distances[cellule[0]][cellule[1]] + 1) {
                distances[voisinX][voisinY] = distances[cellule[0]][cellule[1]] + 1;
                file.add(new int[] { voisinX, voisinY });
            }
        }
    }

    private static boolean[][] genererZonesDesertiques(boolean[][] eau, int[][] distanceAEau, Random random) {
        int nombreLignes = eau.length;
        int nombreColonnes = nombreLignes == 0 ? 0 : eau[0].length;
        boolean[][] desert = new boolean[nombreLignes][nombreColonnes];
	    int distanceMinimaleAEau = Math.max(1, Math.min(nombreLignes, nombreColonnes) / 6);
	    int[][] centres = trouverCentresDesert(eau, distanceAEau, distanceMinimaleAEau, random);

        if (centres.length == 0) {
            return desert;
        }

        double[] rayons = {
            Math.max(2.6, nombreLignes / 3.7),
            Math.max(2.1, nombreColonnes / 5.0)
        };

        for (int i = 0; i < nombreLignes; i++) {
            for (int j = 0; j < nombreColonnes; j++) {
                if (eau[i][j] || distanceAEau[i][j] < distanceMinimaleAEau) {
                    continue;
                }

                if (appartientAUneZoneDesertique(i, j, centres, distanceAEau[i][j], distanceMinimaleAEau,
                        rayons, random)) {
                    desert[i][j] = true;
                }
            }
        }

	    assurerDeuxZonesDesertiques(desert, eau, distanceAEau, centres, distanceMinimaleAEau, random);
        assurerTailleMinimaleDuDesert(desert, eau, centres);
        supprimerDesertsAdjacentsAEau(desert, eau);

        return desert;
    }

    private static boolean appartientAUneZoneDesertique(int x, int y, int[][] centres, int distanceAEau,
            int distanceMinimaleAEau, double[] rayons, Random random) {
        for (int[] centre : centres) {
            if (centre.length == 0) {
                continue;
            }

            double distanceNormalisee = Math.pow((x - centre[0]) / rayons[0], 2)
                + Math.pow((y - centre[1]) / rayons[1], 2);
            double irregularite = (random.nextDouble() - 0.5) * 0.22;

            if (distanceNormalisee + irregularite <= 1.0
                || estDansCouronneAride(distanceAEau, distanceMinimaleAEau, distanceNormalisee, random)) {
                return true;
            }
        }

        return false;
    }

    private static int[][] trouverCentresDesert(boolean[][] eau, int[][] distanceAEau,
            int distanceMinimaleAEau, Random random) {
        int[] premierCentre = trouverCentreDesertAleatoire(eau, distanceAEau, distanceMinimaleAEau,
            new int[0], random);

        if (premierCentre.length == 0) {
            return new int[0][];
        }

        int[] secondCentre = trouverCentreDesertAleatoire(eau, distanceAEau, distanceMinimaleAEau,
            premierCentre, random);

        if (secondCentre.length == 0) {
            return new int[][] { premierCentre };
        }

        return new int[][] { premierCentre, secondCentre };
    }

    private static int[] trouverCentreDesertAleatoire(boolean[][] eau, int[][] distanceAEau,
            int distanceMinimaleAEau, int[] centreExistant, Random random) {
        int[] centreChoisi = new int[0];
        int candidats = 0;
        int separationMinimale = Math.max(3, Math.min(eau.length, eau[0].length) / 4);

        for (int i = 0; i < eau.length; i++) {
            for (int j = 0; j < eau[i].length; j++) {
                if (!estCelluleValidePourCentreDesert(eau, distanceAEau, i, j, distanceMinimaleAEau)) {
                    continue;
                }

                if (centreExistant.length > 0
                    && Math.abs(centreExistant[0] - i) + Math.abs(centreExistant[1] - j) < separationMinimale) {
                    continue;
                }

                candidats++;
                if (random.nextInt(candidats) == 0) {
                    centreChoisi = new int[] { i, j };
                }
            }
        }

        return centreChoisi;
    }

    private static boolean estCelluleValidePourCentreDesert(boolean[][] eau, int[][] distanceAEau, int x, int y,
            int distanceMinimaleAEau) {
        return !eau[x][y]
            && distanceAEau[x][y] >= Math.max(2, distanceMinimaleAEau)
            && !estAdjacentAEau(eau, x, y);
    }

    private static void assurerDeuxZonesDesertiques(boolean[][] desert, boolean[][] eau, int[][] distanceAEau,
            int[][] centres, int distanceMinimaleAEau, Random random) {
        if (compterZonesConnectees(desert) >= 2) {
            return;
        }

        int[] centreReference = centres.length == 0 ? new int[0] : centres[0];
        int[] centreSecondaire = trouverCentreDesertAleatoire(eau, distanceAEau, distanceMinimaleAEau,
            centreReference, random);
        if (centreSecondaire.length == 0) {
            centreSecondaire = trouverCentreSecondaireDesert(eau, desert, distanceAEau, random);
        }
        if (centreSecondaire.length == 0) {
            return;
        }

        creerPatchDesertique(desert, eau, centreSecondaire, 1);
    }

    private static void assurerTailleMinimaleDuDesert(boolean[][] desert, boolean[][] eau, int[][] centres) {
        int tailleMinimale = Math.max(6, (desert.length * desert[0].length) / 4);
        int rayon = 1;

        while (compterCasesActives(desert) < tailleMinimale && rayon <= 3) {
            for (int[] centre : centres) {
                if (centre.length > 0) {
                    creerPatchDesertique(desert, eau, centre, rayon);
                }
            }
            rayon++;
        }
    }

    private static int[] trouverCentreSecondaireDesert(boolean[][] eau, boolean[][] desert, int[][] distanceAEau,
            Random random) {
        int[] meilleurCentre = new int[0];
        double meilleurScore = Double.NEGATIVE_INFINITY;

        for (int i = 0; i < eau.length; i++) {
            for (int j = 0; j < eau[i].length; j++) {
                if (eau[i][j] || desert[i][j]) {
                    continue;
                }

                int distanceAuDesert = calculerDistanceAuDesertLePlusProche(desert, i, j);
                if (distanceAuDesert < 3) {
                    continue;
                }

	            double score = distanceAEau[i][j] * 2.8 + distanceAuDesert * 0.45 + random.nextDouble() * 2.0;
                if (score > meilleurScore) {
                    meilleurScore = score;
                    meilleurCentre = new int[] { i, j };
                }
            }
        }

        return meilleurCentre;
    }

    private static int calculerDistanceAuDesertLePlusProche(boolean[][] desert, int x, int y) {
        int meilleureDistance = Integer.MAX_VALUE;

        for (int i = 0; i < desert.length; i++) {
            for (int j = 0; j < desert[i].length; j++) {
                if (desert[i][j]) {
                    int distance = Math.abs(i - x) + Math.abs(j - y);
                    if (distance < meilleureDistance) {
                        meilleureDistance = distance;
                    }
                }
            }
        }

        return meilleureDistance == Integer.MAX_VALUE ? Math.max(desert.length, desert[0].length) : meilleureDistance;
    }

	private static void creerPatchDesertique(boolean[][] desert, boolean[][] eau, int[] centre, int rayon) {
        for (int i = centre[0] - rayon; i <= centre[0] + rayon; i++) {
            for (int j = centre[1] - rayon; j <= centre[1] + rayon; j++) {
                if (i >= 0 && i < desert.length && j >= 0 && j < desert[i].length
                    && !eau[i][j]
	                && !estAdjacentAEau(eau, i, j)) {
                    desert[i][j] = true;
                }
            }
        }
    }

    private static void supprimerDesertsAdjacentsAEau(boolean[][] desert, boolean[][] eau) {
        boolean[][] aSupprimer = new boolean[desert.length][desert[0].length];

        for (int i = 0; i < desert.length; i++) {
            for (int j = 0; j < desert[i].length; j++) {
                if (desert[i][j] && estAdjacentAEau(eau, i, j)) {
                    aSupprimer[i][j] = true;
                }
            }
        }

        for (int i = 0; i < desert.length; i++) {
            for (int j = 0; j < desert[i].length; j++) {
                if (aSupprimer[i][j]) {
                    desert[i][j] = false;
                }
            }
        }
    }

    private static boolean estAdjacentAEau(boolean[][] eau, int x, int y) {
        for (int[] direction : DIRECTIONS) {
            int voisinX = x + direction[0];
            int voisinY = y + direction[1];

            if (voisinX >= 0 && voisinX < eau.length && voisinY >= 0 && voisinY < eau[0].length && eau[voisinX][voisinY]) {
                return true;
            }
        }

        return false;
    }

    private static int compterZonesConnectees(boolean[][] carte) {
        boolean[][] visite = new boolean[carte.length][carte[0].length];
        int zones = 0;

        for (int i = 0; i < carte.length; i++) {
            for (int j = 0; j < carte[i].length; j++) {
                if (carte[i][j] && !visite[i][j]) {
                    zones++;
                    marquerZone(carte, visite, i, j);
                }
            }
        }

        return zones;
    }

    private static void marquerZone(boolean[][] carte, boolean[][] visite, int departX, int departY) {
        ArrayDeque<int[]> file = new ArrayDeque<>();
        file.add(new int[] { departX, departY });
        visite[departX][departY] = true;

        while (!file.isEmpty()) {
            int[] courant = file.removeFirst();
            for (int[] direction : DIRECTIONS) {
                int voisinX = courant[0] + direction[0];
                int voisinY = courant[1] + direction[1];

                if (voisinX >= 0 && voisinX < carte.length && voisinY >= 0 && voisinY < carte[0].length
                    && carte[voisinX][voisinY] && !visite[voisinX][voisinY]) {
                    visite[voisinX][voisinY] = true;
                    file.add(new int[] { voisinX, voisinY });
                }
            }
        }
    }

    private static int compterCasesActives(boolean[][] carte) {
        int compteur = 0;

        for (int i = 0; i < carte.length; i++) {
            for (int j = 0; j < carte[i].length; j++) {
                if (carte[i][j]) {
                    compteur++;
                }
            }
        }

        return compteur;
    }

    private static boolean[][] genererZoneVillageoise(boolean[][] eau, boolean[][] desert, int[][] distanceAEau,
            Random random) {
        int nombreLignes = eau.length;
        int nombreColonnes = nombreLignes == 0 ? 0 : eau[0].length;
        boolean[][] village = new boolean[nombreLignes][nombreColonnes];
        int[] centre = trouverCentreVillage(eau, desert, distanceAEau, random);

        if (centre == null) {
            return village;
        }

        double rayonX = Math.max(2.0, nombreLignes / 4.8);
        double rayonY = Math.max(2.0, nombreColonnes / 4.8);

        for (int i = 0; i < nombreLignes; i++) {
            for (int j = 0; j < nombreColonnes; j++) {
                boolean candidatVillage = peutDevenirVillage(eau, desert, distanceAEau, i, j);
                double distanceNormalisee = Math.pow((i - centre[0]) / rayonX, 2)
                    + Math.pow((j - centre[1]) / rayonY, 2);
                if (candidatVillage && distanceNormalisee <= 1.0) {
                    village[i][j] = true;
                }
            }
        }

        return village;
    }

    private static boolean[][] genererZoneUrbaine(boolean[][] eau, boolean[][] desert, boolean[][] village,
            int[][] distanceAEau, Random random) {
        int nombreLignes = eau.length;
        int nombreColonnes = nombreLignes == 0 ? 0 : eau[0].length;
        boolean[][] ville = new boolean[nombreLignes][nombreColonnes];
        int[] centre = trouverCentreVille(eau, desert, village, distanceAEau, random);

        if (centre == null) {
            return ville;
        }

        double rayonX = Math.max(1.0, nombreLignes / 9.5);
        double rayonY = Math.max(1.0, nombreColonnes / 9.5);

        for (int i = 0; i < nombreLignes; i++) {
            for (int j = 0; j < nombreColonnes; j++) {
                if (!village[i][j]) {
                    continue;
                }

                double distanceNormalisee = Math.pow((i - centre[0]) / rayonX, 2)
                    + Math.pow((j - centre[1]) / rayonY, 2);
                if (distanceNormalisee <= 1.0) {
                    ville[i][j] = true;
                }
            }
        }

        return ville;
    }

    private static boolean[][] genererZonesBanquise(boolean[][] eau, Random random) {
        int nombreLignes = eau.length;
        int nombreColonnes = nombreLignes == 0 ? 0 : eau[0].length;
        boolean[][] banquise = new boolean[nombreLignes][nombreColonnes];

        if (nombreLignes == 0 || nombreColonnes == 0) {
            return banquise;
        }

        int nombreCasesEau = compterCasesActives(eau);
        if (nombreCasesEau == 0) {
            return banquise;
        }

        int[] centrePrincipal = trouverCentreBanquise(eau, random, new int[0]);
        if (centrePrincipal.length == 0) {
            return banquise;
        }

        int[][] centres = new int[][] { centrePrincipal };
        if (random.nextDouble() < 0.45) {
            int[] centreSecondaire = trouverCentreBanquise(eau, random, centrePrincipal);
            if (centreSecondaire.length > 0) {
                centres = new int[][] { centrePrincipal, centreSecondaire };
            }
        }

        double rayonX = Math.max(1.7, nombreLignes / 8.0);
        double rayonY = Math.max(1.7, nombreColonnes / 8.0);

        for (int i = 0; i < nombreLignes; i++) {
            for (int j = 0; j < nombreColonnes; j++) {
                if (!eau[i][j]) {
                    continue;
                }

                if (appartientZoneBanquise(i, j, centres, rayonX, rayonY, random)) {
                    banquise[i][j] = true;
                }
            }
        }

        int tailleMinimale = Math.max(3, nombreCasesEau / 12);
        int rayon = 1;
        while (compterCasesActives(banquise) < tailleMinimale && rayon <= 3) {
            for (int[] centre : centres) {
                etendreBanquiseAutour(banquise, eau, centre, rayon);
            }
            rayon++;
        }

        return banquise;
    }

    private static boolean appartientZoneBanquise(int x, int y, int[][] centres, double rayonX, double rayonY,
            Random random) {
        for (int[] centre : centres) {
            double distanceNormalisee = Math.pow((x - centre[0]) / rayonX, 2)
                + Math.pow((y - centre[1]) / rayonY, 2);
            double irregularite = (random.nextDouble() - 0.5) * 0.24;
            if (distanceNormalisee + irregularite <= 1.0) {
                return true;
            }
        }

        return false;
    }

    private static int[] trouverCentreBanquise(boolean[][] eau, Random random, int[] centreExistant) {
        int[] centreChoisi = new int[0];
        double poidsCumules = 0.0;
        int distanceMaxBordure = Math.max(1, Math.min(eau.length, eau[0].length) / 5);
        int separationMinimale = Math.max(2, Math.min(eau.length, eau[0].length) / 5);

        for (int i = 0; i < eau.length; i++) {
            for (int j = 0; j < eau[i].length; j++) {
                if (!eau[i][j]) {
                    continue;
                }

                int distanceBordure = Math.min(Math.min(i, eau.length - 1 - i),
                    Math.min(j, eau[i].length - 1 - j));
                if (distanceBordure > distanceMaxBordure) {
                    continue;
                }

                if (centreExistant.length > 0
                    && Math.abs(centreExistant[0] - i) + Math.abs(centreExistant[1] - j) < separationMinimale) {
                    continue;
                }

                double poids = distanceMaxBordure - distanceBordure + 1.0;
                poidsCumules += poids;
                if (random.nextDouble() * poidsCumules < poids) {
                    centreChoisi = new int[] { i, j };
                }
            }
        }

        if (centreChoisi.length > 0) {
            return centreChoisi;
        }

        return trouverCentreBanquiseSansContrainte(eau, random, centreExistant, separationMinimale);
    }

    private static int[] trouverCentreBanquiseSansContrainte(boolean[][] eau, Random random, int[] centreExistant,
            int separationMinimale) {
        int[] centreChoisi = new int[0];
        int candidats = 0;

        for (int i = 0; i < eau.length; i++) {
            for (int j = 0; j < eau[i].length; j++) {
                if (!eau[i][j]) {
                    continue;
                }

                if (centreExistant.length > 0
                    && Math.abs(centreExistant[0] - i) + Math.abs(centreExistant[1] - j) < separationMinimale) {
                    continue;
                }

                candidats++;
                if (random.nextInt(candidats) == 0) {
                    centreChoisi = new int[] { i, j };
                }
            }
        }

        return centreChoisi;
    }

    private static void etendreBanquiseAutour(boolean[][] banquise, boolean[][] eau, int[] centre, int rayon) {
        for (int i = centre[0] - rayon; i <= centre[0] + rayon; i++) {
            for (int j = centre[1] - rayon; j <= centre[1] + rayon; j++) {
                if (i >= 0 && i < eau.length && j >= 0 && j < eau[i].length
                    && eau[i][j]
                    && Math.abs(i - centre[0]) + Math.abs(j - centre[1]) <= rayon * 2) {
                    banquise[i][j] = true;
                }
            }
        }
    }

    private static boolean[][] genererZonesMontagne(boolean[][] eau, boolean[][] desert, boolean[][] village,
            boolean[][] ville, int[][] distanceAEau, Random random) {
        int nombreLignes = eau.length;
        int nombreColonnes = nombreLignes == 0 ? 0 : eau[0].length;
        boolean[][] montagne = new boolean[nombreLignes][nombreColonnes];

        if (nombreLignes == 0 || nombreColonnes == 0) {
            return montagne;
        }

        int[] centrePrincipal = trouverCentreMontagne(eau, desert, village, ville, distanceAEau, random,
            new int[0]);
        if (centrePrincipal.length == 0) {
            return montagne;
        }

        int[][] centres = new int[][] { centrePrincipal };
        if (random.nextDouble() < 0.60) {
            int[] centreSecondaire = trouverCentreMontagne(eau, desert, village, ville, distanceAEau, random,
                centrePrincipal);
            if (centreSecondaire.length > 0) {
                centres = new int[][] { centrePrincipal, centreSecondaire };
            }
        }

        double rayonX = Math.max(1.8, nombreLignes / 9.0);
        double rayonY = Math.max(1.8, nombreColonnes / 9.0);

        for (int[] centre : centres) {
            creerMassifMontagne(montagne, eau, desert, village, ville, centre, rayonX, rayonY, random);
        }

        if (centres.length > 1) {
            tracerCreteMontagne(montagne, eau, desert, village, ville, centres[0], centres[1]);
        }

        int tailleMinimale = Math.max(4, (nombreLignes * nombreColonnes) / 30);
        int rayon = 1;
        while (compterCasesActives(montagne) < tailleMinimale && rayon <= 2) {
            for (int[] centre : centres) {
                marquerMontagneAutour(montagne, eau, desert, village, ville, centre[0], centre[1], rayon);
            }
            rayon++;
        }

        return montagne;
    }

    private static int[] trouverCentreMontagne(boolean[][] eau, boolean[][] desert, boolean[][] village,
            boolean[][] ville, int[][] distanceAEau, Random random, int[] centreExistant) {
        int[] meilleurCentre = new int[0];
        double poidsCumules = 0.0;
        int separationMinimale = Math.max(3, Math.min(eau.length, eau[0].length) / 4);

        for (int i = 0; i < eau.length; i++) {
            for (int j = 0; j < eau[i].length; j++) {
                if (!estCelluleValideMontagne(eau, desert, village, ville, i, j)) {
                    continue;
                }

                if (centreExistant.length > 0
                    && Math.abs(centreExistant[0] - i) + Math.abs(centreExistant[1] - j) < separationMinimale) {
                    continue;
                }

                double poids = Math.max(0.25, distanceAEau[i][j] * 1.45 + random.nextDouble() * 1.20);
                poidsCumules += poids;
                if (random.nextDouble() * poidsCumules < poids) {
                    meilleurCentre = new int[] { i, j };
                }
            }
        }

        return meilleurCentre;
    }

    private static boolean estCelluleValideMontagne(boolean[][] eau, boolean[][] desert, boolean[][] village,
            boolean[][] ville, int x, int y) {
        return !eau[x][y] && !desert[x][y] && !village[x][y] && !ville[x][y];
    }

    private static void creerMassifMontagne(boolean[][] montagne, boolean[][] eau, boolean[][] desert,
            boolean[][] village, boolean[][] ville, int[] centre, double rayonX, double rayonY, Random random) {
        for (int i = 0; i < montagne.length; i++) {
            for (int j = 0; j < montagne[i].length; j++) {
                if (!estCelluleValideMontagne(eau, desert, village, ville, i, j)) {
                    continue;
                }

                double distanceNormalisee = Math.pow((i - centre[0]) / rayonX, 2)
                    + Math.pow((j - centre[1]) / rayonY, 2);
                double irregularite = (random.nextDouble() - 0.5) * 0.22;
                if (distanceNormalisee + irregularite <= 1.0) {
                    montagne[i][j] = true;
                }
            }
        }
    }

    private static void tracerCreteMontagne(boolean[][] montagne, boolean[][] eau, boolean[][] desert,
            boolean[][] village, boolean[][] ville, int[] source, int[] cible) {
        int x = source[0];
        int y = source[1];

        marquerMontagneAutour(montagne, eau, desert, village, ville, x, y, 1);

        while (x != cible[0] || y != cible[1]) {
            if (x != cible[0]) {
                x += Integer.compare(cible[0], x);
            }
            if (y != cible[1]) {
                y += Integer.compare(cible[1], y);
            }

            marquerMontagneAutour(montagne, eau, desert, village, ville, x, y, 1);
        }
    }

    private static void marquerMontagneAutour(boolean[][] montagne, boolean[][] eau, boolean[][] desert,
            boolean[][] village, boolean[][] ville, int centreX, int centreY, int rayon) {
        for (int i = centreX - rayon; i <= centreX + rayon; i++) {
            for (int j = centreY - rayon; j <= centreY + rayon; j++) {
                if (i >= 0 && i < montagne.length && j >= 0 && j < montagne[i].length
                    && estCelluleValideMontagne(eau, desert, village, ville, i, j)) {
                    montagne[i][j] = true;
                }
            }
        }
    }

    private static int[] trouverCentreVillage(boolean[][] eau, boolean[][] desert, int[][] distanceAEau,
            Random random) {
        int nombreLignes = eau.length;
        int nombreColonnes = nombreLignes == 0 ? 0 : eau[0].length;
        int[] meilleurCentre = null;
        double meilleurScore = Double.NEGATIVE_INFINITY;

        for (int i = 0; i < nombreLignes; i++) {
            for (int j = 0; j < nombreColonnes; j++) {
                if (peutDevenirVillage(eau, desert, distanceAEau, i, j)) {
                    double score = 4.0 - Math.abs(distanceAEau[i][j] - 2.0);
                    score += random.nextDouble();

                    if (score > meilleurScore) {
                    meilleurScore = score;
                    meilleurCentre = new int[] { i, j };
                    }
                }
            }
        }

        return meilleurCentre;
    }

    private static int[] trouverCentreVille(boolean[][] eau, boolean[][] desert, boolean[][] village,
            int[][] distanceAEau, Random random) {
        int nombreLignes = eau.length;
        int nombreColonnes = nombreLignes == 0 ? 0 : eau[0].length;
        int[] meilleurCentre = null;
        double meilleurScore = Double.NEGATIVE_INFINITY;

        for (int i = 0; i < nombreLignes; i++) {
            for (int j = 0; j < nombreColonnes; j++) {
                if (!village[i][j] || eau[i][j] || desert[i][j]) {
                    continue;
                }

                double score = 5.0 - Math.abs(distanceAEau[i][j] - 2.0);
                score += random.nextDouble();

                if (score > meilleurScore) {
                    meilleurScore = score;
                    meilleurCentre = new int[] { i, j };
                }
            }
        }

        return meilleurCentre;
    }

    private static int calculerDistanceDepuisCoinX(int ligne, int nombreLignes, int coin) {
        if (coin == 0 || coin == 1) {
            return ligne;
        }
        return nombreLignes - 1 - ligne;
    }

    private static int calculerDistanceDepuisCoinY(int colonne, int nombreColonnes, int coin) {
        if (coin == 0 || coin == 2) {
            return colonne;
        }
        return nombreColonnes - 1 - colonne;
    }

    private static boolean peutDevenirVillage(boolean[][] eau, boolean[][] desert, int[][] distanceAEau, int x, int y) {
        return !eau[x][y] && !desert[x][y] && distanceAEau[x][y] >= 1 && distanceAEau[x][y] <= 4;
    }

    private static boolean estDansCouronneAride(int distanceAEau, int distanceMinimaleAEau,
            double distanceNormalisee, Random random) {
        if (distanceAEau <= distanceMinimaleAEau || distanceNormalisee > 1.85) {
            return false;
        }

        double scoreAridite = (distanceAEau - distanceMinimaleAEau) * 0.18;
        scoreAridite += (1.85 - distanceNormalisee) * 0.40;
        scoreAridite += random.nextDouble() * 0.20;
        return scoreAridite >= 0.30;
    }

    private static Biome creerBiome(TypeBiome type, Bloc bloc) {
        switch (type) {
            case DESERT:
                return new Desert(ConfigurationBiome.DESERT_TEMP, ConfigurationBiome.DESERT_POLLUTION,
                    ConfigurationBiome.DESERT_PURIFICATION, ConfigurationBiome.DESERT_HUMIDITE, 0, bloc);
            case MER:
                return new Mer(ConfigurationBiome.MER_TEMP, ConfigurationBiome.MER_POLLUTION,
                    ConfigurationBiome.MER_PURIFICATION, ConfigurationBiome.MER_HUMIDITE, 0, bloc);
            case BANQUISE:
                return new Banquise(ConfigurationBiome.BANQUISE_TEMP, ConfigurationBiome.BANQUISE_POLLUTION,
                    ConfigurationBiome.BANQUISE_PURIFICATION, ConfigurationBiome.BANQUISE_HUMIDITE, 0, bloc);
            case MONTAGNE:
                return new Montagne(ConfigurationBiome.MONTAGNE_TEMP, ConfigurationBiome.MONTAGNE_POLLUTION,
                    ConfigurationBiome.MONTAGNE_PURIFICATION, ConfigurationBiome.MONTAGNE_HUMIDITE, 0, bloc);
            case VILLAGE:
                return new Village(ConfigurationBiome.VILLAGE_TEMP, ConfigurationBiome.VILLAGE_POLLUTION,
                    ConfigurationBiome.VILLAGE_PURIFICATION, ConfigurationBiome.VILLAGE_HUMIDITE, 0, bloc);
            case VILLE:
                return new Ville(ConfigurationBiome.VILLE_TEMP, ConfigurationBiome.VILLE_POLLUTION,
                    ConfigurationBiome.VILLE_PURIFICATION, ConfigurationBiome.VILLE_HUMIDITE, 0, bloc);
            case FORET:
            default:
                return new Foret(ConfigurationBiome.FORET_TEMP, ConfigurationBiome.FORET_POLLUTION,
                    ConfigurationBiome.FORET_PURIFICATION, ConfigurationBiome.FORET_HUMIDITE, 0, bloc);
        }
    }
}

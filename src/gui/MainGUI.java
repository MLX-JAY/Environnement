package gui;

import config.GameConfiguration;
import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Dimension;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.SwingUtilities;
import moteur.donne.carte.Carte;
import moteur.processus.Builder;
import moteur.processus.Manageur;
import moteur.processus.ManageurBasique;

public class MainGUI extends JFrame implements Runnable 
{
  	
	private MainDisplayer displayerEdition;
	private MainDisplayer displayerSimulation;
	private final static Dimension tailleFenetre = new Dimension(GameConfiguration.FENETRE_LONGEUR, GameConfiguration.FENETRE_LARGEUR);
	private Carte carte;
	private PanelStatistique panelStats;
	private PanelTemps panelTemps;
	private Manageur manageur;
	private PanelEdition panelEdition;
	private PanelBilanDeFin panelBilan;
    private CardLayout cardLayout;
    private JPanel mainPanel;
    private JPanel vueEdition;
    private JPanel vueSimulation;
    private JPanel vueBilan;
    private boolean simulationEnCours = false;

	public MainGUI(String title) {
        super(title);
        init();
    }

    public void init() {
        
        panelTemps = new PanelTemps(this);
        panelStats = new PanelStatistique();

        // 2. Configuration du CardLayout pour les changements de vue
        cardLayout = new CardLayout();
        mainPanel = new JPanel(cardLayout);

        // CARTE 1 : L'ÉDITION
        vueEdition = new JPanel(new BorderLayout());
        vueEdition.setBackground(new Color(163, 177, 138));
        
        panelEdition = new PanelEdition(() -> demarrerSimulation(), () -> genererCarte(), this::ouvrirFenetreTuto);
        panelEdition.setVueEdition(vueEdition);
        
        vueEdition.add(panelEdition, BorderLayout.NORTH);
        
        if (panelEdition.getPanelConfig() != null) {
            vueEdition.add(panelEdition.getPanelConfig(), BorderLayout.WEST);
        }

        // CARTE 2 : LA SIMULATION
        vueSimulation = new JPanel(new BorderLayout());
        vueSimulation.add(panelTemps, BorderLayout.NORTH);
        vueSimulation.add(panelStats, BorderLayout.WEST);

        mainPanel.add(vueEdition, "EDITION");
        mainPanel.add(vueSimulation, "SIMU");

        this.setContentPane(mainPanel);
        cardLayout.show(mainPanel, "EDITION");

        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(tailleFenetre);
        this.setLocationRelativeTo(null);
        this.setExtendedState(JFrame.MAXIMIZED_BOTH);
        this.setVisible(true);
        
        genererCarte();
    }

    public void ouvrirFenetreTuto() {
        JFrame fenetreTuto = new JFrame("Tutoriel");
        fenetreTuto.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        fenetreTuto.setSize(1200, 600);
        fenetreTuto.setLocationRelativeTo(this);

JTabbedPane onglets = new JTabbedPane();
        
        String[] expSections = {"Objectif du jeu", "Comment jouer", "Les biomes", "Les événements", "Gagner ou perdre"};
        String[][] expContenu = {
            {"Simulation d'écosysteme avec évolution automatique. Genere des aléas climatiques (pluie, orage, pollution...) qui transforment les biomes au fil du temps.",
             "But : Observer comment l'écosysteme évolue et créer différentes scénarios grâce aux différents paramètres disponibles."},
            {"1. <b>Génerer une carte</b> en mode Edition ou la modifier manuellement.",
             "2. <b>Ajuster les probabilités</b> d'apparition des évenements.",
             "3. <b>Lancer la simulation</b> et observer l'évolution.",
             "4. <b>Mettre en pause</b> ou <b>accélérer</b> si besoin."},
            {"<b>Foret</b> : Génère Purification (nettoie la pollution). <span style='color:#64c864'>+</span>",
             "<b>Mer</b> : Génère Pluie, Orage (humidite). <span style='color:#64c864'>+</span>",
             "<b>Desert</b> : Génère Vent chaud, Tornade (sec). <span style='color:#dcdc64'>~</span>",
             "<b>Ville</b> : Génère Pollution (mauvais). <span style='color:#dc6464'>-</span>",
             "<b>Montagne</b> : Neutre, ne genere rien, mais permet d'annihiler les evenements adjacents. <span style='color:#dcdc64'>!</span>"},
            {"<b>Evenements positifs</b> : Pluie, Purification, Pluie benite - Ameliorent l'ecosysteme",
             "<b>Evenements negatifs</b> : Pollution, Smog, Nuage toxique, Pluie Acide - Detruisent l'ecosysteme",
             "<b>Evenements neutres</b> : Grèle, Orage, Tonnerre, Vent Chaud/Froid, Zephyr - Modifient les equilibres"},
            {"Survie des <b>Forets</b> et <b>Mers</b> = <span style='color:#64c864'>Bon signe</span>",
             "Pollution croissante = <span style='color:#dc6464'>Mauvais signe</span>",
             "Biodiversite (types de biomes) = <span style='color:#ffc864'>Indicateur de developpement sain</span>"}
};
        String[][] expImages = {
            {"explication_generale", "720", "480"},
            {"", "0", "0"},
            {"biomes_vue", "480", "100"},
            {"", "0", "0"},
            {"", "0", "0"}
        };
        onglets.addTab("explication", new PanleTuto("Explication generale", expSections, expContenu, expImages));
        
        String[] editSections = {"Generer une carte", "Modifier les biomes", "Configurer les probabilites", "Lancer la simulation"};
        String[][] editContenu = {
            {"Cliquer autant de fois que necessaire sur <b>Generer Carte</b> pour creer une carte pseudo-aleatoire.",
             "La taille depend de la configuration du jeu."},
            {"Cliquer sur une <b>icone de biome</b> (cercles en haut) pour la selectionner.",
             "Cliquer sur la carte pour placer le biome choisi.",
             "Le contour rouge indique le biome selectionne."},
            {"Les <b>sliders</b> a gauche permettent d'ajuster les probabilites.",
             "Chaque biome a ses propres evenements configurables.",
             "<b>Reinitialiser</b> : Remet les valeurs par defaut.",
             "<b>Mettre a zero</b> : Desactive tous les evenements."},
            {"Cliquer sur <b>Lancer</b> pour demarrer la simulation.",
             "Les parametres sont sauvegardes automatiquement."}
        };
        String[][] editImages = {
            {"edition_generer", "120", "60"},
            {"edition_biomes", "600", "120"},
            {"edition_config", "360", "240"},
            {"edition_lancer", "120", "60"}
        };
        PanleTuto panelEdition = new PanleTuto("Mode edition", editSections, editContenu, editImages);
        panelEdition.setImageSize(180, 120);
        onglets.addTab("edition", panelEdition);
        
        String[] simuSections = {"Principe", "Controles", "Statistiques", "Objectif"};
        String[][] simuContenu = {
            {"La simulation evolue carte en temps reel. A chaque tour, le jeu applique les evenements et les regles de transformation.",
             "Les biomes peuvent se transformer selon les conditions (temperature, humidite, pollution, purification)."},
            {"<b>Pause</b> : Met en pause la simulation.",
             "<b>Play</b> : Relance la simulation.",
             "<b>Vitesse x</b> : Accelere le rythme (1x, 2x, 4x).",
             "<b>Statistiques</b> : Ouvre une fenetre avec graphiques."},
            {"Selectionner un biome pour voir ses statistiques detaillees de la Partie.",
             "Les differents condittions des biomes, le pourcentage d'evenements/ biomes, le tout en temps reel.",
             "<b>Bilan de fin</b> : Affiche le resume quand la partie se termine, avec un cetain score de l'etat de l'ecosysteme."},
            {"Observer l'evolution et maintenir des conditions favorables.",
             "Prevention : Maintenir Forets et Mers pour un ecosysteme sain."}
        };
        String[][] simuImages = {
            {"", "0", "0"},
            {"simulation_controles", "900", "60"},
            {"simulation_stats",  "720", "480"},
            {"", "0", "0"}
        };
        PanleTuto panelSimulation = new PanleTuto("Mode simulation", simuSections, simuContenu, simuImages);
        panelSimulation.setImageSize(180, 100);
        onglets.addTab("simulation", panelSimulation);
        onglets.addTab("évenements et biomes", new PanleTutoDraw());

        fenetreTuto.setContentPane(onglets);
        fenetreTuto.setVisible(true);
    }

    private void demarrerSimulation() {
        if (displayerSimulation != null) {
            displayerSimulation.setManageur(manageur);
        }
        cardLayout.show(mainPanel, "SIMU");
        this.simulationEnCours = true;
    }

    private void genererCarte() {
        carte = Builder.construireCarte();
        manageur = Builder.initCarte(carte);
        
        int carteLargeur = GameConfiguration.NOMBRE_COLONNES * GameConfiguration.TAILLE_BLOC;
        int carteHauteur = GameConfiguration.NOMBRE_LIGNES * GameConfiguration.TAILLE_BLOC;
        
        if (vueEdition != null && displayerEdition != null) {
            vueEdition.remove(displayerEdition);
        }
        if (vueSimulation != null && displayerSimulation != null) {
            vueSimulation.remove(displayerSimulation);
        }
        
        displayerEdition = new MainDisplayer(carte, manageur);
        displayerEdition.setPreferredSize(new Dimension(carteLargeur, carteHauteur));
        displayerEdition.setMinimumSize(new Dimension(carteLargeur, carteHauteur));
        displayerEdition.setMaximumSize(new Dimension(carteLargeur * 2, carteHauteur * 2));
        
        displayerSimulation = new MainDisplayer(carte, manageur);
        displayerSimulation.setPreferredSize(new Dimension(carteLargeur, carteHauteur));
        displayerSimulation.setPanelStats(panelStats);
        
        if (vueEdition != null) {
            vueEdition.add(displayerEdition, BorderLayout.CENTER);
        }
        if (vueSimulation != null) {
            vueSimulation.add(displayerSimulation, BorderLayout.CENTER);
        }
        
        panelEdition.setDisplayer(displayerEdition);
        panelEdition.setManageur(manageur);
        
        SwingUtilities.invokeLater(() -> {
            mainPanel.revalidate();
            mainPanel.repaint();
            panelStats.repaint();
            revalidate();
            repaint();
        });
    }

    public void arreterSimulation() {
        this.simulationEnCours = false;
    }
    
    public void afficherBilan() {
        this.simulationEnCours = false;
        
        if (manageur instanceof ManageurBasique) {
            ((ManageurBasique) manageur).clearEvenements();
        }
        
        if (vueBilan != null) {
            mainPanel.remove(vueBilan);
        }
        
        vueBilan = new JPanel(new BorderLayout());
        vueBilan.setBackground(new Color(40, 54, 24));
        
        panelBilan = new PanelBilanDeFin(manageur, displayerSimulation);
        vueBilan.add(panelBilan, BorderLayout.CENTER);
        
        mainPanel.add(vueBilan, "BILAN");
        
        cardLayout.show(mainPanel, "BILAN");
        
        SwingUtilities.invokeLater(() -> {
            mainPanel.revalidate();
            mainPanel.repaint();
        });
    }
    
    public void afficherEdition() {
        this.simulationEnCours = false;
        cardLayout.show(mainPanel, "EDITION");
    }
    
    public void nouvelleSession() {
        this.simulationEnCours = false;
        genererCarte();
        cardLayout.show(mainPanel, "EDITION");
    }

    public void reprendresimulation() {
        this.simulationEnCours = true;
    }
    
    public Manageur getManageur() {
        return manageur;
    }
    
    public int getRoundActuel() {
        if (manageur instanceof ManageurBasique) {
            return ((ManageurBasique) manageur).getRoundActuel();
        }
        return 0;
    }

    @Override
    public void run() {
        long lastRoundTime = System.currentTimeMillis();
        
        while (true) {
            long currentTime = System.currentTimeMillis();
            
            if (simulationEnCours && (currentTime - lastRoundTime >= config.GameConfiguration.VITESSE_JEU)) {
                manageur.nextRound();
                lastRoundTime = currentTime;
            }
            
            if (displayerSimulation != null) {
                displayerSimulation.repaint();
            }
            if (displayerEdition != null) {
                displayerEdition.repaint();
            }
            panelStats.repaint();
            
            try {
                Thread.sleep(16);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
    
}

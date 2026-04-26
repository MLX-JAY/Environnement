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
        fenetreTuto.setSize(760, 520);
        fenetreTuto.setLocationRelativeTo(this);

        JTabbedPane onglets = new JTabbedPane();
        onglets.addTab("explication", new PanleTuto(
            "Explication générale",
            "Ce projet simule l'évolution d'un environnement sur une carte.\n\n"
                + "Chaque tour applique des règles qui influencent les biomes et les événements.\n"
                + "Tu peux d'abord préparer la carte en mode édition, puis lancer la simulation."
        ));
        onglets.addTab("edition", new PanleTuto(
            "Mode édition",
            "Dans le mode édition, tu peux générer aléatoirement une carte et en modifier les biomes."
            + "Tu peux aussi changer les probabilités d'apparition de chaques événements.\n\n"
                + "Menu du haut :\n- Générer Carte -> permet de générer une carte aléatoire.\n"
                + "- Lancer Simulation -> démarre la simulation avec la carte actuelle.\n- Réinitialiser -> remet les probabilités à leurs valeurs par défaut.\n"
                + "- Mettre à zéro -> remet les probabilités à 0%.\n"
                + "- Boutons Biomes -> En cliquant sur l'un des 6 boutons, change le biome sélectionné en suite.\n\n"
                + "Menu de gauche :\n- Permet d'ajuster les probabilités d'apparition de chaque événement."
        ));
        onglets.addTab("simulation", new PanleTuto(
            "Mode simulation",
            "Le mode simulation est le coeur de ce projet, il montre l'évolution de la carte en temps réel en fonction de l'impaxt des événements et des règles appliquées à chaque tour.\n\n"
                + "Menu du haut :\n- Pause -> met en pause la simulation.\n- Play -> relance la simulation.\n- Vitesse x -> augmente la vitesse de la simulation.\n"
                + "- Statistiques -> ouvre une fenêtre affichant des statistiques générales de la session en temps réel.\n\n"
                + "Menu de gauche :\n- Affiche les statistiques d'un biome sélectionné."
        ));
        onglets.addTab("événements et biomes", new PanleTutoDraw());

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

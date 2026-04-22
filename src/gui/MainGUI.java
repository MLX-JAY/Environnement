package gui;

import config.GameConfiguration;

import java.awt.Color;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Dimension;

import javax.swing.JFrame;
import javax.swing.JPanel;
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
    private CardLayout cardLayout;
    private JPanel mainPanel;
    private JPanel vueEdition;
    private JPanel vueSimulation;
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
        
        panelEdition = new PanelEdition(() -> demarrerSimulation(), () -> genererCarte());
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
                panelEdition.mettreAJourBoutonModifier();
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

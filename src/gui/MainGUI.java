package gui;

import config.GameConfiguration;
import java.awt.Font;

import java.awt.Color;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Dimension;
import java.awt.GridBagLayout;
import java.awt.Image;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import moteur.donne.carte.Carte;
import moteur.processus.Builder;
import moteur.processus.Manageur;
import moteur.processus.ManageurBasique;

public class MainGUI extends JFrame implements Runnable 
{
	
	private MainDisplayer displayer;
	private final static Dimension tailleFenetre = new Dimension(GameConfiguration.FENETRE_LONGEUR, GameConfiguration.FENETRE_LARGEUR);
	private Carte carte;
	private PanelStatistique panelStats;
	private PanelTemps panelTemps;
	private Manageur manageur;
    private CardLayout cardLayout;
    private JPanel mainPanel;
    private boolean simulationEnCours = false;

	public MainGUI(String title) {
        super(title);
        init();
    }

    public void init() {

        // 1. Initialisation des données
        carte = Builder.construireCarte();
        manageur = Builder.initCarte(carte);
        
        displayer = new MainDisplayer(carte, manageur);
        panelTemps = new PanelTemps(this);
        panelStats = new PanelStatistique();
        displayer.setPanelStats(panelStats);

        // 2. Configuration du CardLayout pour les changements de vue
        cardLayout = new CardLayout();
        mainPanel = new JPanel(cardLayout);

        // CARTE 1 : L'ÉDITION

        JPanel vueEdition = new JPanel(new BorderLayout());
        vueEdition.add(displayer, BorderLayout.CENTER);
        vueEdition.setBackground(new Color(163, 177, 138));
        
        PanelEdition menuEdition = new PanelEdition(() -> demarrerSimulation()); // si bouton FIN cliqué, on démarre la simulation
        
        vueEdition.add(menuEdition, BorderLayout.NORTH);
        vueEdition.add(displayer, BorderLayout.CENTER);

        JPanel panelDev = new JPanel(new GridBagLayout()); // GridBag pour centrer le texte
        panelDev.setPreferredSize(new Dimension(250, 0)); // Même largeur que PanelStats
        panelDev.setBackground(new Color(52, 78, 65)); // Un peu plus clair que le fond
        
        JLabel txtDev = new JLabel("<html><center><h2>MODE ÉDITION</h2>En cours de développement</center></html>");
        txtDev.setForeground(new Color(240, 240, 230)); // Blanc cassé
        txtDev.setFont(new Font("Segoe UI", Font.ITALIC, 14));
        
        panelDev.add(txtDev);
        vueEdition.add(panelDev, BorderLayout.WEST);

        //  CARTE 2 : LA SIMULATION

        JPanel vueSimulation = new JPanel(new BorderLayout());
        vueSimulation.add(panelTemps, BorderLayout.NORTH);
        vueSimulation.add(displayer, BorderLayout.CENTER);
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
    }

    private void demarrerSimulation() {
        cardLayout.show(mainPanel, "SIMU"); // Affiche la vue de simulation
        this.simulationEnCours = true;
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
            
            // On ne fait avancer le jeu que si on a cliqué sur FIN
            if (simulationEnCours && (currentTime - lastRoundTime >= config.GameConfiguration.VITESSE_JEU)) {
                manageur.nextRound();
                lastRoundTime = currentTime;
            }
            
            // On redessine TOUJOURS (même en édition pour voir ce qu'on fait)
            displayer.repaint();
            panelStats.repaint();
            if (panelStats != null) {
                panelStats.repaint(); 
            }
            
            try {
                Thread.sleep(16);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
    
}

package gui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import javax.swing.JFrame;
import javax.swing.JPanel;

import moteur.donne.carte.Carte;
import moteur.processus.Builder;
import moteur.processus.Manageur;

public class MainGUI extends JFrame implements Runnable 
{
	
	private MainDisplayer displayer;
	private final static Dimension tailleFenetre = new Dimension(10, 10);
	private Carte carte;
	private PanelStatistique panelStats;
	private PanelTemps panelTemps;
	private Manageur manageur;

	public MainGUI(String title) {
        super(title);
        init();
    }

    private void init() {
        // Premiere chose a faire : On construit la carte et le manager via le Builder
        carte = Builder.construireCarte();
        manageur = Builder.initCarte(carte);
        manageur.ajouterEvenement();
        
        displayer = new MainDisplayer(carte, manageur);
        
        panelStats = new PanelStatistique();
        panelTemps = new PanelTemps();
        
        
        // Configuration de la fenêtre
        this.setLayout(new BorderLayout());
        
        this.add(displayer, BorderLayout.CENTER);
        this.add(panelStats, BorderLayout.WEST);
        this.add(panelTemps, BorderLayout.SOUTH);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setExtendedState(JFrame.MAXIMIZED_BOTH);
        this.setVisible(true);
    }

    @Override
    public void run() {
        while (true) {
            try {
                Thread.sleep(config.GameConfiguration.VITESSE_JEU);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            manageur.nextRound(); 
            displayer.repaint(); 
        }
    }
    
}

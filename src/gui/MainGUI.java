package gui;

import config.GameConfiguration;
import java.awt.BorderLayout;
import java.awt.Dimension;
import javax.swing.JFrame;
import moteur.donne.carte.Carte;
import moteur.processus.Builder;
import moteur.processus.Manageur;

public class MainGUI extends JFrame implements Runnable 
{
	
	private MainDisplayer displayer;
	private final static Dimension tailleFenetre = new Dimension(GameConfiguration.FENETRE_LONGEUR, GameConfiguration.FENETRE_LARGEUR);
	private Carte carte;
	private PanelStatistique panelStats;
	private PanelTemps panelTemps;
	private Manageur manageur;

	public MainGUI(String title) {
        super(title);
        init();
    }

    public void init() {
    carte = Builder.construireCarte();
    manageur = Builder.initCarte(carte);
    
    displayer = new MainDisplayer(carte, manageur);
    panelTemps = new PanelTemps(this);
    panelStats = new PanelStatistique();

    this.setLayout(new BorderLayout());

    this.add(panelTemps, BorderLayout.NORTH); 
    this.add(displayer, BorderLayout.CENTER);
    this.add(panelStats, BorderLayout.WEST);
    
    displayer.setPreferredSize(tailleFenetre);
    this.add(displayer);
    this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    this.pack(); 
    this.setLocationRelativeTo(null);
    this.setVisible(true);
}

    @Override
    public void run() {
        long lastRoundTime = System.currentTimeMillis();
        long frameRate = 16; // ~60 FPS
        
        while (true) {
            long currentTime = System.currentTimeMillis();
            
            // classique tour
            if (currentTime - lastRoundTime >= config.GameConfiguration.VITESSE_JEU) {
                manageur.nextRound();
                lastRoundTime = currentTime;
            }
            
            // Redessiner à ~60 FPS pour l'animation fluide
            displayer.repaint();
            panelStats.repaint();
            
            try {
                Thread.sleep(frameRate);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
    
}

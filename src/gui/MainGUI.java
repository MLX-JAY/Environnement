package gui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import javax.swing.JFrame;
import javax.swing.JPanel;

import config.ConfigurationEvenement;
import config.GameConfiguration;
import moteur.donne.carte.Carte;
import moteur.processus.Builder;
import moteur.processus.Manageur;
import java.awt.BorderLayout;

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
        while (true) {
            try {
                Thread.sleep(config.GameConfiguration.VITESSE_JEU);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            manageur.nextRound(); 
            displayer.repaint();
            panelStats.repaint();
        }
    }
    
}

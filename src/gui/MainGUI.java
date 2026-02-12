package gui;

import java.awt.Dimension;
import javax.swing.JFrame;
import moteur.donne.carte.Carte;
import moteur.processus.Builder;
import moteur.processus.Manageur;

public class MainGUI extends JFrame implements Runnable 
{
	
	private MainDisplayer displayer;
	private final static Dimension tailleFenetre = new Dimension(config.GameConfiguration.FENETRE_LONGEUR, config.GameConfiguration.FENETRE_LARGEUR);
	private Carte carte;
	private Manageur manageur;

	public MainGUI(String title) {
        super(title);
        init();
    }

    private void init() {
        // 1. On construit la carte et le manager via le Builder
        carte = Builder.construireCarte();
        manageur = Builder.initCarte(carte);
        manageur.ajouterEvenement();

        // 2. On crée le panneau d'affichage (le GameDisplay)
        displayer = new MainDisplayer(carte, manageur);
        
        
        // 3. Configuration de la fenêtre
        this.add(displayer);
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

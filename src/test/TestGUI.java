package test;


import java.awt.Graphics;

import javax.swing.JFrame;
import javax.swing.JPanel;

import gui.StrategiePeinture;
import moteur.donne.biome.Desert;
import moteur.donne.biome.Foret;
import moteur.donne.biome.Mer;
import moteur.donne.carte.Carte;
import moteur.processus.Manageur;
import moteur.processus.Builder;

public class TestGUI extends JPanel
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Carte carte;
	private StrategiePeinture strategie = new StrategiePeinture();
    private Manageur manageur;

    public TestGUI() {
    	carte = Builder.construireCarte();
    	manageur = Builder.initCarte(carte);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        
        // TEST DE LA STRATEGIE : On dessine ce que le manager contient
        // On récupère la liste des biomes du manager
        for (Object b : manageur.getBiomes()) {
            if (b instanceof Foret) strategie.paint((Foret) b, g);
            if (b instanceof Desert) strategie.paint((Desert) b, g);
            if (b instanceof Mer) strategie.paint((Mer) b, g);
        }
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("Test Manager & Strategie");
        frame.add(new TestGUI());
        frame.setSize(600, 400);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
        
        System.out.println("Test lancé : Vérifiez si les biomes s'affichent correctement.");
    }
}

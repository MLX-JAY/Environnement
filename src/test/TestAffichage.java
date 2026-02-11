package test;

import javax.swing.*;

import gui.StrategiePeinture;

import java.awt.*;
import java.util.ArrayList;
import moteur.donne.biome.*;
import moteur.donne.carte.Bloc;

public class TestAffichage extends JPanel {

	private static final long serialVersionUID = 1L;
	private StrategiePeinture strategie = new StrategiePeinture();
    private ArrayList<Object> biomesDeTest = new ArrayList<>();

    public TestAffichage() {
        // On crée quelques biomes manuellement pour le test
        // On les place à des coordonnées différentes (x, y)
        biomesDeTest.add(new Foret(0, 0, 0, 0, 0, new Bloc(1, 1)));
        biomesDeTest.add(new Desert(0, 0, 0, 0, 0,new Bloc(2, 1)));
        biomesDeTest.add(new Mer(0, 0, 0, 0, 0, new Bloc(3, 1)));
        biomesDeTest.add(new Ville(0, 0, 0, 0, 0, new Bloc(1, 2)));
        biomesDeTest.add(new Village(0, 0, 0, 0, 0, new Bloc(2, 2)));
        biomesDeTest.add(new Montagne(0, 0, 0, 0, 0, new Bloc(3, 2)));
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        
        // On dessine un fond noir pour bien voir les cases
        g.setColor(Color.BLACK);
        g.fillRect(0, 0, getWidth(), getHeight());

        // On appelle la bonne méthode de la stratégie pour chaque biome
        for (Object b : biomesDeTest) {
            if (b instanceof Foret) strategie.paint((Foret) b, g);
            if (b instanceof Desert) strategie.paint((Desert) b, g);
            if (b instanceof Mer) strategie.paint((Mer) b, g);
            if (b instanceof Ville) strategie.paint((Ville) b, g);
            if (b instanceof Village) strategie.paint((Village) b, g);
            if (b instanceof Montagne) strategie.paint((Montagne) b, g);
        }
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("Test de ma Strategie de Peinture");
        frame.add(new TestAffichage());
        frame.setSize(500, 500);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}
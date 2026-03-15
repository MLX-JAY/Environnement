package gui;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.Shape;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Path2D;

import javax.swing.JPanel;

public class PanelStatistique extends JPanel 
{
	
        private int temperature;
        private int humidite;
        private int pollution;
        private int purification;
        private Dimension taillePanel = new Dimension(250, 0);


        public PanelStatistique() {
        this.setBackground(new Color(52, 78, 65));
        this.setPreferredSize(taillePanel);
        }

        public void paintComponent (Graphics g) 
        {
                super.paintComponent(g);
                Graphics2D g2 = (Graphics2D) g;

                dessinerCercle(g2, 75, 100, 100, "Température", 70, Color.ORANGE);
                dessinerCercle(g2, 75, 300, 100, "Humidité", 45, Color.CYAN);
                dessinerCercle(g2, 75, 500, 100, "Pollution", 20, Color.RED);
                dessinerCercle(g2, 75, 700, 100, "Purification", 90, Color.GREEN);
        }

        private void dessinerCercle(Graphics2D g2, int x, int y, int taille, String nom, double pourcentage, Color couleurLiquide) {
        
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        //Dessiner le fond du cercle
        g2.setColor(new Color(30, 30, 30));
        g2.fillOval(x, y, taille, taille);

        // Créer la zone de découpe
        Shape cercle = new Ellipse2D.Float(x, y, taille, taille);
        g2.setClip(cercle);

        // Calculer la hauteur du liquide
        int hauteurLiquide = (int) (taille * (pourcentage / 100.0));
        int niveauY = y + taille - hauteurLiquide;

        // Animation du liquide , on utilise une fonction sinus 
        long temps = System.currentTimeMillis();
        Path2D.Float vague = new Path2D.Float();
        
        vague.moveTo(x, niveauY);
        for (int i = 0; i <= taille; i++) {
                // Formule  : (vitesse * temps + décalage_horizontal) * amplitude
                float oscillation = (float) Math.sin(temps * 0.0035 + i * 0.05) * 5; 
                vague.lineTo(x + i, niveauY + oscillation);
        }
        
        // Fermer le polygone du liquide vers le bas
        vague.lineTo(x + taille, y + taille);
        vague.lineTo(x, y + taille);
        vague.closePath();

        // Remplissage avec un dégradé
        GradientPaint degrade = new GradientPaint(x, niveauY, couleurLiquide.brighter(), x, y + taille, couleurLiquide.darker());
        g2.setPaint(degrade);
        g2.fill(vague);

        // Ajouter des petites bulles
        g2.setColor(new Color(255, 255, 255, 80)); // Blanc transparent
        for (int j = 0; j < 3; j++) {
                long decalage = temps + (j * 500);
                int bx = x + (int)((decalage * 0.02) % taille);
                int by = (y + taille) - (int)((decalage * 0.05) % hauteurLiquide) - 5;
                if (by > niveauY) g2.fillOval(bx, by, 4, 4);
        }

        // Nettoyer le clip pour la suite du dessin
        g2.setClip(null);

        // Dessiner le contour du cercle
        g2.setColor(Color.WHITE);
        g2.setStroke(new BasicStroke(2f));
        g2.drawOval(x, y, taille, taille);
        g2.drawString(nom + " : " + pourcentage + "%", x + 10, y + taille + 20);
        }
}

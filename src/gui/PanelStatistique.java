package gui;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.Shape;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Path2D;
import javax.swing.JPanel;
import moteur.donne.biome.Biome;

public class PanelStatistique extends JPanel 
{
	
        private int temperature;
        private int humidite;
        private int pollution;
        private int purification;
        private String biomeNom;
        private Biome biomeSelectionne;
        private Dimension taillePanel = new Dimension(250, 0);


        public PanelStatistique() {
        this.setBackground(new Color(52, 78, 65));
        this.setPreferredSize(taillePanel);
        }

		private void synchroniserStatistiquesSelection() {
				if (biomeSelectionne == null) {
					return;
				}

				temperature = (int) biomeSelectionne.getTemperature();
				humidite = (int) biomeSelectionne.getHumidite();
				pollution = (int) biomeSelectionne.getPollution();
				purification = (int) biomeSelectionne.getPurification();
				biomeNom = biomeSelectionne.getClass().getSimpleName();
		}

        public void setBiomeSelectionne(Biome biome) {
                this.biomeSelectionne = biome;
				synchroniserStatistiquesSelection();
                repaint();
        }

		public void actualiserBiomeSelectionne(Biome biome) {
				this.biomeSelectionne = biome;
				synchroniserStatistiquesSelection();
		}

		@Override
        public void paintComponent (Graphics g) 
        {
                super.paintComponent(g);
                Graphics2D g2 = (Graphics2D) g;
				synchroniserStatistiquesSelection();

                if (biomeSelectionne != null) {
                        g2.setColor(Color.WHITE);
                        g2.setFont(new Font("Segoe UI", Font.BOLD, 16));
                        g2.drawString("Biome: " + biomeNom, 20, 40);
                        g2.drawString("Pos: (" + biomeSelectionne.getPosition().getX() + 
                                     ", " + biomeSelectionne.getPosition().getY() + ")", 20, 60);
                        
                        dessinerCercle(g2, 75, 100, 100, "Température", temperature, Color.ORANGE);
                        dessinerCercle(g2, 75, 300, 100, "Humidité", humidite, Color.CYAN);
                        dessinerCercle(g2, 75, 500, 100, "Pollution", pollution, Color.RED);
                        dessinerCercle(g2, 75, 700, 100, "Purification", purification, Color.GREEN);
                } else {
                        g2.setColor(Color.WHITE);
                        g2.setFont(new Font("Segoe UI", Font.BOLD, 18));
                        String msg = "Cliquez sur un biome";
                        g2.drawString(msg, 50, 400);
                        g2.setFont(new Font("Segoe UI", Font.PLAIN, 14));
                        String msg2 = "pour voir ses statistiques";
                        g2.drawString(msg2, 40, 430);
                }
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

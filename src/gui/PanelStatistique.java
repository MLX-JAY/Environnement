package gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Shape;
import java.awt.geom.Ellipse2D;

import javax.swing.JPanel;

public class PanelStatistique extends JPanel 
{
	
	private int temperature;
	private int humidite;
	private int pollution;
	private int purification;
	private Dimension taillePanel = new Dimension(250, 0);

	
	public PanelStatistique() {
        this.setBackground(new Color(0x243e36));
        this.setPreferredSize(taillePanel);
	}
	
	public void paintComponent (Graphics g) 
	{
		super.paintComponent(g); // Garde le background
        Graphics2D g2 = (Graphics2D) g;

        dessinerCercle(g2, 75, 100, "Température", 70, Color.ORANGE);
        dessinerCercle(g2, 75, 300, "Humidité", 45, Color.CYAN);
        dessinerCercle(g2, 75, 500, "Pollution", 20, Color.RED);
        dessinerCercle(g2, 75, 700, "Purification", 90, Color.GREEN);
	}
	
	private void dessinerCercle(Graphics2D g2, int x, int y, String nom, int pourcent, Color couleur) {
        int tailleCercle = 110;
        
        g2.setColor(new Color(0x3f4739));
        g2.fillOval(x, y, tailleCercle, tailleCercle);
        
        
        Shape oldClip = g2.getClip();
        g2.setClip(new Ellipse2D.Float(x, y, tailleCercle, tailleCercle));
        
        g2.setColor(couleur);
        int jauge = (pourcent * tailleCercle) / 100;
        g2.fillRect(x, y + (tailleCercle - jauge), tailleCercle, jauge);
        
        g2.setClip(oldClip);
        
        
        g2.setColor(Color.WHITE);
        g2.setStroke(new java.awt.BasicStroke(2));
        g2.drawOval(x, y, tailleCercle, tailleCercle);
        g2.drawString(nom + " : " + pourcent + "%", x + 10, y + tailleCercle + 20);
    }
}

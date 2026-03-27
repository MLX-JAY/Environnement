package gui;

import java.awt.BasicStroke;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JPanel;

import java.awt.Color;
import moteur.donne.biome.Banquise;
import moteur.donne.biome.Biome;
import moteur.donne.biome.Desert;
import moteur.donne.biome.Foret;
import moteur.donne.biome.Mer;
import moteur.donne.biome.Montagne;
import moteur.donne.biome.Village;
import moteur.donne.biome.Ville;
import moteur.donne.biome.Banquise;
import moteur.donne.carte.Carte;
import moteur.donne.evenement.Evenement;
import moteur.donne.evenement.mobile.GroupePluie;
import moteur.donne.evenement.mobile.GroupePluieAcide;
import moteur.donne.evenement.mobile.Pluie;
import moteur.donne.evenement.mobile.Pollution;
import moteur.donne.evenement.mobile.Purification;
import moteur.donne.evenement.mobile.VentChaud;
import moteur.donne.evenement.mobile.VentFroid;
import moteur.donne.evenement.statique.Meteore;
import moteur.processus.Manageur;
import config.GameConfiguration;

public class MainDisplayer extends JPanel 
{
	
	private static final long serialVersionUID = 1L;
	private Carte carte;
	private StrategiePeinture stratDePeinture=new StrategiePeinture();
	private Manageur manageur;
	private PanelStatistique panelStats;
	private Biome biomeSelectionne;
	
	public MainDisplayer(Carte carte, Manageur manageur) {  
		this.carte = carte;
		this.manageur = manageur;
		this.setBackground(new Color(163, 177, 138));
		
		this.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				int blocX = e.getX() / GameConfiguration.TAILLE_BLOC;
				int blocY = e.getY() / GameConfiguration.TAILLE_BLOC;
				
				if (carte.estCoordonneeValide(blocX, blocY)) {
					Biome biomeClique = trouverBiome(blocX, blocY);
					if (biomeClique != null) {
						biomeSelectionne = biomeClique;
						if (panelStats != null) {
							panelStats.setBiomeSelectionne(biomeClique);
						}
					}
				}
			}
		});
	}
	
	public void setPanelStats(PanelStatistique panelStats) {
		this.panelStats = panelStats;
	}
	
	private Biome trouverBiome(int blocX, int blocY) {
		for (Biome b : manageur.getBiomes()) {
			if (b.getPosition().getX() == blocX && b.getPosition().getY() == blocY) {
				return b;
			}
		}
		return null;
	}

	@Override
	public void paintComponent (Graphics g)
	{
		super.paintComponent(g);
		
		for (Evenement e : manageur.getEvenements()) {
			e.updateAnimation();
		}
		for (Evenement danger : manageur.getDangers()) {
			danger.updateAnimation();
		}
        
        for (Biome b : manageur.getBiomes()) 
		{
            if (b instanceof Foret) stratDePeinture.paint((Foret) b, g);
            if (b instanceof Desert) stratDePeinture.paint((Desert) b, g);
            if (b instanceof Mer) stratDePeinture.paint((Mer) b, g);
            if (b instanceof Village) stratDePeinture.paint((Village) b, g);
            if (b instanceof Ville) stratDePeinture.paint((Ville) b, g);
            if (b instanceof Montagne) stratDePeinture.paint((Montagne) b, g);
            if (b instanceof Banquise) stratDePeinture.paint((Banquise) b, g);
        }
		for (Evenement e : manageur.getEvenements() )
		{
			if (e instanceof GroupePluie) {
				GroupePluie groupe = (GroupePluie) e;
				for (Pluie pluie : groupe.getPluieUnitaires()) {
					stratDePeinture.paint(pluie, g);
				}
			} else if (e instanceof GroupePluieAcide) {
				GroupePluieAcide groupe = (GroupePluieAcide) e;
				for (moteur.donne.evenement.mobile.PluieAcide pluie : groupe.getPluieAcideUnitaires()) {
					stratDePeinture.paint(pluie, g);
				}
			} else if (e instanceof Pluie) {
				stratDePeinture.paint((Pluie)e, g);
			}
			if (e instanceof VentFroid) stratDePeinture.paint((VentFroid)e, g);
			if (e instanceof VentChaud) stratDePeinture.paint((VentChaud)e, g);
			if (e instanceof Purification) stratDePeinture.paint((Purification)e, g);
			if (e instanceof Pollution) stratDePeinture.paint((Pollution)e, g);
		}
		for (Evenement danger : manageur.getDangers()) {
			
			if (danger instanceof Meteore) {
				if (danger.getDuree() > 10) {
					if (danger.getDuree() % 2 == 0) {
						stratDePeinture.paintDanger(danger, g);
					}
				}
				else{
					stratDePeinture.paint((Meteore)danger, g);
				}
					
			}
		}
		
		if (biomeSelectionne != null) {
			dessinerSurbrillance(g, biomeSelectionne);
		}
	}
	
	private void dessinerSurbrillance(Graphics g, Biome biome) {
		int size = config.GameConfiguration.TAILLE_BLOC;
		int x = biome.getPosition().getX() * size;
		int y = biome.getPosition().getY() * size;
		
		long temps = System.currentTimeMillis();
		int alpha = (int) (128 + 127 * Math.sin(temps * 0.008));
		
		g.setColor(new Color(255, 0, 0, alpha));
		Graphics2D g2 = (Graphics2D) g;
		g2.setStroke(new BasicStroke(3));
		g2.drawRect(x + 1, y + 1, size - 2, size - 2);
		
		g.setColor(new Color(255, 0, 0, alpha / 3));
		g.fillRect(x, y, size, size);
	}
}

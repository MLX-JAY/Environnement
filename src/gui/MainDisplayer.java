package gui;

import config.GameConfiguration;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JPanel;
import moteur.donne.biome.Biome;
import moteur.donne.biome.Desert;
import moteur.donne.biome.Foret;
import moteur.donne.biome.Mer;
import moteur.donne.biome.Village;
import moteur.donne.biome.Ville;
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

public class MainDisplayer extends JPanel 
{
	
	private static final long serialVersionUID = 1L;
	private final transient Carte carte;
	private final transient StrategiePeinture stratDePeinture = new StrategiePeinture();
	private final transient Manageur manageur;
	private PanelStatistique panelStats;
	private transient Biome biomeSelectionne;
	
	public MainDisplayer(Carte carte, Manageur manageur) {  
		this.carte = carte;
		this.manageur = manageur;
		this.setBackground(new Color(163, 177, 138));
		
		this.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				int blocX = e.getX() / GameConfiguration.TAILLE_BLOC;
				int blocY = e.getY() / GameConfiguration.TAILLE_BLOC;
				
				if (MainDisplayer.this.carte.estCoordonneeValide(blocX, blocY)) {
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

		if (biomeSelectionne != null) {
			biomeSelectionne = trouverBiome(
				biomeSelectionne.getPosition().getX(),
				biomeSelectionne.getPosition().getY()
			);
			if (panelStats != null) {
				panelStats.actualiserBiomeSelectionne(biomeSelectionne);
			}
		}
		
		for (Evenement e : manageur.getEvenements()) {
			e.mettreAJourAnimation();
		}
		for (Evenement danger : manageur.getDangers()) {
			danger.mettreAJourAnimation();
		}
        
		for (Biome b : manageur.getBiomes()) 
		{
			dessinerBiome(g, b);
        }
		for (Evenement e : manageur.getEvenements() )
		{
			dessinerEvenement(g, e);
		}
		for (Evenement danger : manageur.getDangers()) {
			dessinerDanger(g, danger);
		}
		
		if (biomeSelectionne != null) {
			dessinerSurbrillance(g, biomeSelectionne);
		}
	}

	private void dessinerBiome(Graphics g, Biome biome) {
		if (biome instanceof Foret foret) {
			stratDePeinture.paint(foret, g);
		}
		if (biome instanceof Desert desert) {
			stratDePeinture.paint(desert, g);
		}
		if (biome instanceof Mer mer) {
			stratDePeinture.paint(mer, g);
		}
		if (biome instanceof Village village) {
			stratDePeinture.paint(village, g);
		}
		if (biome instanceof Ville ville) {
			stratDePeinture.paint(ville, g);
		}
	}

	private void dessinerEvenement(Graphics g, Evenement evenement) {
		if (evenement instanceof Pluie pluie) {
			stratDePeinture.paint(pluie, g);
		}
		if (evenement instanceof VentFroid ventFroid) {
			stratDePeinture.paint(ventFroid, g);
		}
		if (evenement instanceof VentChaud ventChaud) {
			stratDePeinture.paint(ventChaud, g);
		}
		if (evenement instanceof Pollution pollution) {
			stratDePeinture.paint(pollution, g);
		}
		if (evenement instanceof Purification purification) {
			stratDePeinture.paint(purification, g);
		}
	}

	private void dessinerDanger(Graphics g, Evenement danger) {
		if (danger instanceof Meteore meteore) {
			if (danger.getDuree() > 10) {
				if (danger.getDuree() % 2 == 0) {
					stratDePeinture.paintDanger(danger, g);
				}
			} else {
				stratDePeinture.paint(meteore, g);
			}
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

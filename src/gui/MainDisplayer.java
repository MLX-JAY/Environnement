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
import moteur.donne.evenement.mobile.Pluie;
import moteur.donne.evenement.mobile.Pollution;
import moteur.donne.evenement.mobile.Purification;
import moteur.donne.evenement.mobile.VentChaud;
import moteur.donne.evenement.mobile.VentFroid;
import moteur.donne.evenement.mobile.Orage;
import moteur.donne.evenement.mobile.Grele;
import moteur.donne.evenement.mobile.Tornade;
import moteur.donne.evenement.mobile.PluieBenite;
import moteur.donne.evenement.mobile.Zephyr;
import moteur.donne.evenement.mobile.Tonnerre;
import moteur.donne.evenement.mobile.Smog;
import moteur.donne.evenement.mobile.NuageToxique;
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
		if (biome instanceof Foret) {
			stratDePeinture.paint((Foret) biome, g);
		}
		if (biome instanceof Desert) {
			stratDePeinture.paint((Desert) biome, g);
		}
		if (biome instanceof Mer) {
			stratDePeinture.paint((Mer) biome, g);
		}
		if (biome instanceof Village) {
			stratDePeinture.paint((Village) biome, g);
		}
		if (biome instanceof Ville) {
			stratDePeinture.paint((Ville) biome, g);
		}
	}

	private void dessinerEvenement(Graphics g, Evenement evenement) {
		if (evenement instanceof Pluie) {
			stratDePeinture.paint((Pluie) evenement, g);
		}
		if (evenement instanceof VentFroid) {
			stratDePeinture.paint((VentFroid) evenement, g);
		}
		if (evenement instanceof VentChaud) {
			stratDePeinture.paint((VentChaud) evenement, g);
		}
		if (evenement instanceof Pollution) {
			stratDePeinture.paint((Pollution) evenement, g);
		}
		if (evenement instanceof Purification) {
			stratDePeinture.paint((Purification) evenement, g);
		}
		if (evenement instanceof Orage) {
			stratDePeinture.paint((Orage) evenement, g);
		}
		if (evenement instanceof Grele) {
			stratDePeinture.paint((Grele) evenement, g);
		}
		if (evenement instanceof Tornade) {
			stratDePeinture.paint((Tornade) evenement, g);
		}
		if (evenement instanceof PluieBenite) {
			stratDePeinture.paint((PluieBenite) evenement, g);
		}
		if (evenement instanceof Zephyr) {
			stratDePeinture.paint((Zephyr) evenement, g);
		}
		if (evenement instanceof Tonnerre) {
			stratDePeinture.paint((Tonnerre) evenement, g);
		}
		if (evenement instanceof Smog) {
			stratDePeinture.paint((Smog) evenement, g);
		}
		if (evenement instanceof NuageToxique) {
			stratDePeinture.paint((NuageToxique) evenement, g);
		}
	}

	private void dessinerDanger(Graphics g, Evenement danger) {
		if (danger instanceof Meteore) {
			Meteore meteore = (Meteore) danger;
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

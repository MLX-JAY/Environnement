package gui;

import config.GameConfiguration;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JPanel;
import moteur.donne.biome.Banquise;
import moteur.donne.biome.Biome;
import moteur.donne.biome.Desert;
import moteur.donne.biome.Foret;
import moteur.donne.biome.Mer;
import moteur.donne.biome.Montagne;
import moteur.donne.biome.Village;
import moteur.donne.biome.Ville;
import moteur.donne.carte.Bloc;
import moteur.donne.carte.Carte;
import moteur.donne.evenement.Evenement;
import moteur.donne.evenement.mobile.Grele;
import moteur.donne.evenement.mobile.NuageToxique;
import moteur.donne.evenement.mobile.Orage;
import moteur.donne.evenement.mobile.Pluie;
import moteur.donne.evenement.mobile.PluieAcide;
import moteur.donne.evenement.mobile.PluieBenite;
import moteur.donne.evenement.mobile.Pollution;
import moteur.donne.evenement.mobile.Purification;
import moteur.donne.evenement.mobile.Smog;
import moteur.donne.evenement.mobile.Tonnerre;
import moteur.donne.evenement.mobile.Tornade;
import moteur.donne.evenement.mobile.VentChaud;
import moteur.donne.evenement.mobile.VentFroid;
import moteur.donne.evenement.mobile.Zephyr;
import moteur.donne.evenement.statique.Meteore;
import moteur.processus.Manageur;
import moteur.processus.ManageurBasique;
import moteur.processus.usine.BiomeFactory;
import moteur.processus.usine.BiomeFactory.TypeBiome;

public class MainDisplayer extends JPanel 
{
	
	private static final long serialVersionUID = 1L;
	private Carte carte;
	private StrategiePeinture stratDePeinture = new StrategiePeinture();
	private Manageur manageur;
	private PanelStatistique panelStats;
	private Biome biomeSelectionne;
	private boolean modePeinture = false;
	private TypeBiome biomeAPlacer = null;
	private Bloc blocSelectionne = null;
	
	public MainDisplayer(Carte carte, Manageur manageur) {  
		this.carte = carte;
		this.manageur = manageur;
		this.setBackground(new Color(163, 177, 138));
		
		if (carte != null) {
			int largeur = carte.getNombreColonnes() * config.GameConfiguration.TAILLE_BLOC;
			int hauteur = carte.getNombreLignes() * config.GameConfiguration.TAILLE_BLOC;
			this.setPreferredSize(new java.awt.Dimension(largeur, hauteur));
		}
		
		this.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				int blocX = e.getX() / GameConfiguration.TAILLE_BLOC;
				int blocY = e.getY() / GameConfiguration.TAILLE_BLOC;
				
				if (MainDisplayer.this.carte.estCoordonneeValide(blocX, blocY)) {
					blocSelectionne = new Bloc(blocX, blocY);
					if (modePeinture && biomeAPlacer != null) {
						Biome nouveauBiome = BiomeFactory.creerBiomeParType(biomeAPlacer, blocSelectionne);
						if (manageur instanceof ManageurBasique) {
							((ManageurBasique) manageur).remplacerBiome(blocSelectionne, nouveauBiome);
						}
						biomeSelectionne = nouveauBiome;
						blocSelectionne = null;
						if (panelStats != null) {
							panelStats.setBiomeSelectionne(nouveauBiome);
						}
						repaint();
					} else {
						Biome biomeClique = trouverBiome(blocX, blocY);
						if (biomeClique != null) {
							biomeSelectionne = biomeClique;
							if (panelStats != null) {
								panelStats.setBiomeSelectionne(biomeClique);
							}
						}
					}
				}
			}
		});
	}
	
	public void setPanelStats(PanelStatistique panelStats) {
		this.panelStats = panelStats;
	}
	
	public void setModePeinture(boolean actif, TypeBiome type) {
		this.modePeinture = actif;
		this.biomeAPlacer = type;
		if (!actif) {
			biomeSelectionne = null;
		}
		repaint();
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
		
		g.setColor(Color.BLACK);
		g.drawRect(0, 0, getWidth() - 1, getHeight() - 1);
		
		if (carte == null || manageur == null) {
			g.setColor(Color.GRAY);
			g.fillRect(0, 0, getWidth(), getHeight());
			g.setColor(Color.WHITE);
			g.drawString("Carte non initialisee", 10, 20);
			return;
		}
		
		int nbBiomes = manageur.getBiomes().size();
		g.setColor(Color.WHITE);
		g.drawString("Biomes: " + nbBiomes, 5, 15);
		g.drawString("Carte: " + getWidth() + "x" + getHeight(), 5, 35);

		if (biomeSelectionne != null) {
			biomeSelectionne = trouverBiome(
				biomeSelectionne.getPosition().getX(),
				biomeSelectionne.getPosition().getY()
			);
			if (panelStats != null) {
				panelStats.actualiserBiomeSelectionne(biomeSelectionne);
			}
		}
		
		List<Evenement> evenements = new ArrayList<>(manageur.getEvenements());
		List<Evenement> dangers = new ArrayList<>(manageur.getDangers());
		List<Biome> biomes = new ArrayList<>(manageur.getBiomes());
		
		for (Evenement e : evenements) {
			if (!(e instanceof Meteore)) {
				e.mettreAJourAnimation();
			}
		}
		for (Evenement danger : dangers) {
			danger.mettreAJourAnimation();
		}
        
		for (Biome b : biomes) 
		{
			dessinerBiome(g, b);
        }
		for (Evenement e : evenements)
		{
			if (!(e instanceof Meteore)) {
				dessinerEvenement(g, e);
			}
		}
		for (Evenement danger : dangers) {
			dessinerDanger(g, danger);
		}
		
		if (biomeSelectionne != null) {
			dessinerSurbrillance(g, biomeSelectionne);
		}
		
		if (blocSelectionne != null && biomeSelectionne == null) {
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
		if (biome instanceof Banquise) {
			stratDePeinture.paint((Banquise) biome, g);
		}
		if (biome instanceof Montagne) {
			stratDePeinture.paint((Montagne) biome, g);
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
		if (evenement instanceof PluieAcide) {
			stratDePeinture.paint((PluieAcide) evenement, g);
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
			if (meteore.getDureeRestante() > Meteore.SEUIL_AVERTISSEMENT) {
				if (((int) meteore.getDureeRestante()) % 2 == 0) {
					stratDePeinture.paintDanger(meteore.getCibleImpact(), g);
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
		int alpha = (int) (128 + 127 * Math.sin(temps / 250.0));
		
		g.setColor(new Color(255, 0, 0, alpha));
		Graphics2D g2 = (Graphics2D) g;
		g2.setStroke(new BasicStroke(2));
		g2.drawRect(x + 2, y + 2, size - 4, size - 4);
		
		g.setColor(new Color(255, 0, 0, alpha / 2));
		g.fillRect(x + 2, y + 2, size - 4, size - 4);
	}
	
	public void setCarte(Carte carte) {
		this.carte = carte;
		if (carte != null) {
			int largeur = carte.getNombreColonnes() * config.GameConfiguration.TAILLE_BLOC;
			int hauteur = carte.getNombreLignes() * config.GameConfiguration.TAILLE_BLOC;
			this.setPreferredSize(new java.awt.Dimension(largeur, hauteur));
		}
		repaint();
	}
	
	public void setManageur(Manageur manageur) {
		this.manageur = manageur;
		repaint();
	}
	
	public Biome getBiomeSelectionne() {
		return biomeSelectionne;
	}
	
	public void setManageurPourModification(Manageur manageur) {
		this.manageur = manageur;
	}
}

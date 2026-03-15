package gui;

import java.awt.Graphics;
import javax.swing.JPanel;

import java.awt.Color;
import moteur.donne.biome.Biome;
import moteur.donne.biome.Desert;
import moteur.donne.biome.Foret;
import moteur.donne.biome.Mer;
import moteur.donne.biome.Village;
import moteur.donne.carte.Carte;
import moteur.donne.evenement.Evenement;
import moteur.donne.evenement.mobile.Pluie;
import moteur.donne.evenement.mobile.Purification;
import moteur.donne.evenement.mobile.VentFroid;
import moteur.donne.evenement.statique.Meteore;
import moteur.processus.Manageur;

public class MainDisplayer extends JPanel 
{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Carte carte;
	private StrategiePeinture stratDePeinture=new StrategiePeinture();
	private Manageur manageur;
	
	public MainDisplayer(Carte carte, Manageur manageur) {  
		this.carte = carte;
		this.manageur = manageur;
		this.setBackground(new Color(163, 177, 138));
	}

	@Override
	public void paintComponent (Graphics g)
	{
		super.paintComponent(g);
		
		// Mettre à jour les animations de tous les événements
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
        }
		for (Evenement e : manageur.getEvenements() )
		{
			if (e instanceof Pluie) stratDePeinture.paint((Pluie)e, g);
			if (e instanceof VentFroid) stratDePeinture.paint((VentFroid)e, g);
			if (e instanceof Purification) stratDePeinture.paint((Purification)e, g);
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
	}
}

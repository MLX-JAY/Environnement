package gui;

import java.awt.Graphics;

import javax.swing.JPanel;

import moteur.donne.biome.Biome;
import moteur.donne.biome.Desert;
import moteur.donne.biome.Foret;
import moteur.donne.biome.Mer;
import moteur.donne.biome.Village;
import moteur.donne.carte.Carte;
import moteur.donne.evenement.Evenement;
import moteur.donne.evenement.mobile.Pluie;
import moteur.processus.Manageur;

public class MainDisplayer extends JPanel 
{
	
	private Carte carte;
	private StrategiePeinture stratDePeinture=new StrategiePeinture();
	private Manageur manageur;
	
	public MainDisplayer(Carte carte, Manageur manageur) {  // il manque les panels
		this.carte = carte;
		this.manageur = manageur;
	}

	@Override
	public void paintComponent (Graphics g)
	{
		super.paintComponent(g);
        
        // TEST DE LA STRATEGIE : On dessine ce que le manager contient
        // On récupère la liste des biomes du manager
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
		}
	}
	
	
}

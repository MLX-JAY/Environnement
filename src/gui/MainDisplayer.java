package gui;

import java.awt.Graphics;

import javax.swing.JPanel;

import moteur.donne.carte.Carte;
import moteur.processus.Manageur;

public class MainDisplayer extends JPanel 
{
	
	private Carte carte;
	private PanelStatistique panneauStatistique;
	private PanelTemps panneauTemps;
	private StrategiePeinture stratDePeinture=new StrategiePeinture();
	private Manageur manageur;
	
	public MainDisplayer(Carte carte, Manageur manageur) {  //PROOVISOIR CAR on a pas encore fait les stats
		this.carte = carte;
		this.manageur = manageur;
	}

	@Override
	public void paintComponent (Graphics g)
	{
		super.paintComponent(g);
		
		
	}
	
	
}

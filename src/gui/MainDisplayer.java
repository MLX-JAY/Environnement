package gui;

import java.awt.Graphics;

import javax.swing.JPanel;

import moteur.processus.Manageur;

public class MainDisplayer extends JPanel 
{
	
	private PanelStatistique panneauStatistique;
	private PanelTemps panneauTemps;
	private StrategiePeinture stratDePeinture=new StrategiePeinture();
	private Manageur manageur;
	public MainDisplayer(PanelStatistique panneauStatistique, PanelTemps panneauTemps, Manageur manageur) {
		this.panneauStatistique = panneauStatistique;
		this.panneauTemps = panneauTemps;
		this.manageur = manageur;
	}

	@Override
	public void paintComponent (Graphics g)
	{
		super.paintComponent(g);
		
		
	}
	
	
}

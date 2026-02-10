package gui;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.io.ObjectInputFilter.Config;

import javax.swing.JFrame;
import javax.swing.JTextField;

import config.GameConfiguration;
import moteur.donne.carte.Carte;
import moteur.processus.ManageurInterface;

public class MainGUI extends JFrame implements Runnable 
{
	
	private MainDisplayer displayer;
	private final static Dimension tailleFenetre = new Dimension(config.GameConfiguration.FENETRE_LONGEUR, config.GameConfiguration.FENETRE_LARGEUR);
	private Carte carte;
	private ManageurInterface manageur;

	public MainGUI(String title) {
		super(title);
		init();
	}

	private void init() {

		Container contentPane = getContentPane();
		contentPane.setLayout(new BorderLayout());

		map = GameBuilder.buildMap();
		manager = GameBuilder.buildInitMobile(map);
		dashboard = new GameDisplay(map, manager);

		dashboard.setPreferredSize(preferredSize);
		contentPane.add(dashboard, BorderLayout.CENTER);

		setDefaultCloseOperation(EXIT_ON_CLOSE);
		pack();
		setVisible(true);
		setPreferredSize(tailleFenetre);
		setResizable(false);
	}
	
	//ne pas oublier de faire les innerClasses pour les keycontrol tout sa

	@Override
	public void run() 
	{
		// TODO Auto-generated method stub

	}

}

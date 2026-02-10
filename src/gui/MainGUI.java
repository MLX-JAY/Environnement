package gui;
import java.awt.Dimension;
import javax.swing.JFrame;

import config.GameConfiguration;

public class MainGUI extends JFrame implements Runnable 
{
	
	private MainDisplayer displayer;
	private PanelStatistique panelStatistique;
	private PanelTemps panelTemps;
	//ne pas oublier de faire les innerClasses pour les keycontrol tout sa
	private final static Dimension preferredSize = new Dimension(GameConfiguration.FENETRE_LARGEUR, GameConfiguration.FENETRE_LONGEUR);
	@Override
	public void run() 
	{
		// TODO Auto-generated method stub

	}

}

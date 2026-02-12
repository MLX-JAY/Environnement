package gui;

import java.awt.Color;
import java.awt.Dimension;

import javax.swing.JButton;
import javax.swing.JPanel;

public class PanelTemps extends JPanel 
{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JButton stop;
	private JButton play;
	private Dimension taillePanel = new Dimension(0, 80);
	
	
	public PanelTemps() {
		this.setBackground(Color.ORANGE);
		this.setPreferredSize(taillePanel);
	}
}

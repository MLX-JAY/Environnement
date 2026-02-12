package gui;

import java.awt.Color;
import java.awt.Dimension;

import javax.swing.JPanel;

public class PanelStatistique extends JPanel 
{
	
	private JPanel temperature;
	private JPanel humidite;
	private JPanel pollution;
	private JPanel purification;
	private Dimension taillePanel = new Dimension(250, 0);

	
	public PanelStatistique() {
		this.setBackground(Color.YELLOW);
		this.setPreferredSize(taillePanel);
	}
}

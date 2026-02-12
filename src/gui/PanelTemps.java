package gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.BorderFactory; // Important pour les marges
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class PanelTemps extends JPanel 
{
    private static final long serialVersionUID = 1L;
    private JButton boutonPause;
    private JLabel labelTour;
    private MainGUI mainGUI; // Référence vers la fenêtre principale pour contrôler la boucle

    public PanelTemps(MainGUI mainGUI) {
        this.mainGUI = mainGUI;
        this.setBackground(Color.DARK_GRAY);
		
        this.setPreferredSize(new Dimension(0, 80));
        this.setLayout(new FlowLayout(FlowLayout.CENTER, 30, 20));
        this.setLayout(new FlowLayout());

        initComposants();
    }

    private void initComposants() {
        Font police = new Font("Arial", Font.BOLD, 24);
        boutonPause = new JButton("Pause");
		boutonPause.setFont(police);
        boutonPause.setFocusable(false);
		boutonPause.setPreferredSize(new Dimension(250, 50));
        this.add(boutonPause);
    }

    public void mettreAJourTour(int numeroTour) {
        labelTour.setText("Tour : " + numeroTour);
    }
}
package gui;

import java.awt.*;
import javax.swing.*;
import config.GameConfiguration;

public class PanelTemps extends JPanel {
    private JButton btnPause, btnPlay, btnX2, btnStop;
    private MainGUI mainGUI;

    public PanelTemps(MainGUI mainGUI) {
        this.mainGUI = mainGUI;
        this.setBackground(new Color(40, 54, 24));
        this.setPreferredSize(new Dimension(0, 80)); // Un peu plus haut pour l'esthétique
        this.setLayout(new FlowLayout(FlowLayout.CENTER, 20, 15));

        initComposants();
    }

    private void initComposants() {
        btnPause = deseign.creerBeauBouton("Pause", new Color(200, 60, 60));
        this.add(btnPause);
        btnPlay = deseign.creerBeauBouton("Play", new Color(60, 200, 60));
        this.add(btnPlay);
        btnX2 = deseign.creerBeauBouton("Vitesse x2", new Color(60, 60, 200));
        this.add(btnX2);
        btnStop = deseign.creerBeauBouton("Bilan de fin", new Color(200, 200, 60));
        this.add(btnStop);

        btnPlay.setEnabled(false);

        btnPause.addActionListener(e -> {
            btnPause.setEnabled(false);
            btnPlay.setEnabled(true);
            mainGUI.arreterSimulation();
        });

        btnPlay.addActionListener(e -> {
            btnPlay.setEnabled(false);
            btnPause.setEnabled(true);
            mainGUI.reprendresimulation();
        });
        
        btnStop.addActionListener(e -> {
            System.out.println("Ouverture du bilan de fin .");
        });

        btnX2.addActionListener(e -> {
            if (GameConfiguration.VITESSE_JEU > 250) { // Limite pour éviter une vitesse trop rapide
                GameConfiguration.VITESSE_JEU /= 2;
                System.out.println("Vitesse de simulation doublée : " + GameConfiguration.VITESSE_JEU + " ms par tour.");
            }
        });

    }
}
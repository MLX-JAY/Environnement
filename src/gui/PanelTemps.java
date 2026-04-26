package gui;

import config.GameConfiguration;
import java.awt.*;
import javax.swing.*;

public class PanelTemps extends JPanel {
    private JButton btnPause, btnPlay, btnX2, btnStop, btnStats;
    private MainGUI mainGUI;

    public PanelTemps(MainGUI mainGUI) {
        this.mainGUI = mainGUI;
        this.setBackground(new Color(40, 54, 24));
        this.setPreferredSize(new Dimension(0, 80));
        this.setLayout(new FlowLayout(FlowLayout.CENTER, 20, 15));

        initComposants();
    }

    private void initComposants() {
        btnPause = deseign.creerBeauBouton("Pause", new Color(200, 60, 60));
        this.add(btnPause);
        btnPlay = deseign.creerBeauBouton("Play", new Color(60, 200, 60));
        this.add(btnPlay);
        btnX2 = deseign.creerBeauBouton("Vitesse x0", new Color(60, 60, 200));
        this.add(btnX2);
        btnStop = deseign.creerBeauBouton("Bilan de fin", new Color(200, 200, 60));
        this.add(btnStop);
        btnStats = deseign.creerBeauBouton("Statistiques", new Color(100, 100, 180));
        this.add(btnStats);

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
            mainGUI.arreterSimulation();
            mainGUI.afficherBilan();
            GameConfiguration.VITESSE_JEU = 1000; // Réinitialiser la vitesse à la valeur par défaut
        });

        btnX2.addActionListener(e -> {
            if (GameConfiguration.VITESSE_JEU > 250) {
                GameConfiguration.VITESSE_JEU /= 2;
                System.out.println("Vitesse de simulation doublée : " + GameConfiguration.VITESSE_JEU + " ms par tour.");
                if (GameConfiguration.VITESSE_JEU == 500) {
                    btnX2.setText("Vitesse x1");
                }
                else if (GameConfiguration.VITESSE_JEU == 250) {
                    btnX2.setText("Vitesse x2");
                }
            }
            else {
                GameConfiguration.VITESSE_JEU = 1000; // Limite minimale pour éviter une vitesse trop rapide
                btnX2.setText("Vitesse x0");
            }
        });
        
        btnStats.addActionListener(e -> {
            if (mainGUI.getManageur() != null) {
                JFrameStatistiques frame = new JFrameStatistiques(mainGUI.getManageur());
                frame.setVisible(true);
            }
        });

    }
}
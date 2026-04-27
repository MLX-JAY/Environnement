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
        this.setLayout(new BorderLayout());

        initComposants();
    }

    private void initComposants() {
        JPanel panelGauche = new JPanel(new FlowLayout(FlowLayout.LEFT, 18, 18));
        panelGauche.setOpaque(false);
        
        JButton btnAide = deseign.creerBeauBouton("?", new Color(90, 120, 190));
        btnAide.setPreferredSize(new Dimension(55, 45));
        btnAide.addActionListener(e -> mainGUI.ouvrirFenetreTuto());
        panelGauche.add(btnAide);
        
        this.add(panelGauche, BorderLayout.WEST);
        
        JPanel panelCentre = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 15));
        panelCentre.setOpaque(false);
        panelCentre.setBackground(new Color(40, 54, 24));
        
        btnPause = deseign.creerBeauBouton("Pause", new Color(200, 60, 60));
        panelCentre.add(btnPause);
        btnPlay = deseign.creerBeauBouton("Play", new Color(60, 200, 60));
        panelCentre.add(btnPlay);
        btnX2 = deseign.creerBeauBouton("Vitesse x1", new Color(60, 60, 200));
        panelCentre.add(btnX2);
        btnStop = deseign.creerBeauBouton("Bilan de fin", new Color(200, 200, 60));
        panelCentre.add(btnStop);
        btnStats = deseign.creerBeauBouton("Statistiques", new Color(100, 100, 180));
        panelCentre.add(btnStats);
        
        this.add(panelCentre, BorderLayout.CENTER);

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
                    btnX2.setText("Vitesse x2");
                }
                else if (GameConfiguration.VITESSE_JEU == 250) {
                    btnX2.setText("Vitesse x4");
                }
            }
            else {
                GameConfiguration.VITESSE_JEU = 1000; // Limite minimale pour éviter une vitesse trop rapide
                btnX2.setText("Vitesse x1");
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
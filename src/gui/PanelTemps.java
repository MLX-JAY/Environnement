package gui;

import java.awt.*;
import javax.swing.*;
import config.GameConfiguration;

public class PanelTemps extends JPanel {
    private JButton btnPause, btnPlay, btnX2, btnStop;
    private MainGUI mainGUI;

    public PanelTemps(MainGUI mainGUI) {
        this.mainGUI = mainGUI;
        this.setBackground(new Color(45, 45, 45));
        this.setPreferredSize(new Dimension(GameConfiguration.FENETRE_LONGEUR, 70));
        this.setLayout(new FlowLayout(FlowLayout.CENTER, 20, 10));

        initComposants();
    }

    private void initComposants() {
        Font police = new Font("SansSerif", Font.BOLD, 16);

        btnPlay = new JButton("Play");
        btnPause = new JButton("Pause");
        btnX2 = new JButton("Vitesse x2");
        btnStop = new JButton("Stop (Bilan)");

        JButton[] boutons = {btnPlay, btnPause, btnX2, btnStop};
        for (JButton b : boutons) {
            b.setFont(police);
            b.setFocusable(false);
            b.setPreferredSize(new Dimension(150, 40));
            this.add(b);
        }

        btnPlay.setEnabled(false);

        btnPause.addActionListener(e -> {
            btnPause.setEnabled(false);
            btnPlay.setEnabled(true);
        });

        btnPlay.addActionListener(e -> {
            btnPlay.setEnabled(false);
            btnPause.setEnabled(true);
        });
        
        btnStop.addActionListener(e -> {
            System.out.println("Ouverture du bilan de fin .");
        });
    }
}
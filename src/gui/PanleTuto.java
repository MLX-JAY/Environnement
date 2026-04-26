package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

public class PanleTuto extends JPanel {

    public PanleTuto(String titre, String contenu) {
        setLayout(new BorderLayout(10, 10));
        setBackground(new Color(40, 54, 24));
        setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        JLabel labelTitre = new JLabel(titre);
        labelTitre.setForeground(new Color(255, 220, 140));
        labelTitre.setFont(new Font("Segoe UI", Font.BOLD, 24));

        JTextArea zoneTexte = new JTextArea(contenu);
        zoneTexte.setEditable(false);
        zoneTexte.setLineWrap(true);
        zoneTexte.setWrapStyleWord(true);
        zoneTexte.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        zoneTexte.setForeground(Color.WHITE);
        zoneTexte.setBackground(new Color(52, 78, 65));
        zoneTexte.setBorder(BorderFactory.createEmptyBorder(12, 12, 12, 12));

        JScrollPane scroll = new JScrollPane(zoneTexte);
        scroll.setBorder(BorderFactory.createEmptyBorder());

        add(labelTitre, BorderLayout.NORTH);
        add(scroll, BorderLayout.CENTER);
    }
}

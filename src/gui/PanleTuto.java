package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.EmptyBorder;

public class PanleTuto extends JPanel {

    private static final Color COULEUR_FOND = new Color(40, 54, 24);
    private static final Color COULEUR_FOND_SECTION = new Color(52, 78, 65);
    private static final Color COULEUR_TITRE = new Color(255, 220, 140);
    private static final Color COULEUR_SOUS_TITRE = new Color(200, 180, 120);
    private static final Color COULEUR_TEXTE = Color.WHITE;


    public PanleTuto(String titre, String[] sections, String[][] contenuSections) {
        setLayout(new BorderLayout(10, 10));
        setBackground(COULEUR_FOND);
        setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        JLabel labelTitre = new JLabel(titre);
        labelTitre.setForeground(COULEUR_TITRE);
        labelTitre.setFont(new Font("Segoe UI", Font.BOLD, 24));

        JPanel contenu = new JPanel();
        contenu.setLayout(new BoxLayout(contenu, BoxLayout.Y_AXIS));
        contenu.setBackground(COULEUR_FOND);
        contenu.setBorder(BorderFactory.createEmptyBorder(12, 12, 12, 6));

        for (int i = 0; i < sections.length; i++) {
            ajouterSection(contenu, sections[i]);
            String[] lignes = contenuSections[i];
            for (String ligne : lignes) {
                ajouterLigne(contenu, ligne);
            }
            contenu.add(Box.createVerticalStrut(12));
        }

        JScrollPane scroll = new JScrollPane(contenu);
        scroll.setBorder(BorderFactory.createEmptyBorder());
        scroll.getVerticalScrollBar().setUnitIncrement(16);

        add(labelTitre, BorderLayout.NORTH);
        add(scroll, BorderLayout.CENTER);
    }

    private void ajouterSection(JPanel parent, String texte) {
        JLabel section = new JLabel(texte);
        section.setForeground(COULEUR_TITRE);
        section.setFont(new Font("Segoe UI", Font.BOLD, 18));
        section.setAlignmentX(LEFT_ALIGNMENT);
        parent.add(section);
        parent.add(Box.createVerticalStrut(8));
        parent.add(creerLigneSeparatrice());
        parent.add(Box.createVerticalStrut(8));
    }

    private JPanel creerLigneSeparatrice() {
        JPanel ligne = new JPanel();
        ligne.setBackground(COULEUR_TITRE);
        ligne.setMaximumSize(new Dimension(Integer.MAX_VALUE, 1));
        ligne.setMinimumSize(new Dimension(1, 1));
        ligne.setPreferredSize(new Dimension(1, 1));
        ligne.setAlignmentX(LEFT_ALIGNMENT);
        return ligne;
    }

    private void ajouterLigne(JPanel parent, String texte) {
        JPanel ligne = new JPanel();
        ligne.setLayout(new BorderLayout(0, 0));
        ligne.setBackground(COULEUR_FOND_SECTION);
        ligne.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(75, 105, 78), 1),
            BorderFactory.createEmptyBorder(8, 8, 8, 8)
        ));
        ligne.setAlignmentX(LEFT_ALIGNMENT);
        ligne.setMaximumSize(new Dimension(Integer.MAX_VALUE, 60));

        JLabel texteLabel = new JLabel("<html>" + texte.replace("\n", "<br>") + "</html>");
        texteLabel.setForeground(COULEUR_TEXTE);
        texteLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14));

        ligne.add(texteLabel, BorderLayout.CENTER);
        parent.add(ligne);
        parent.add(Box.createVerticalStrut(6));
    }
}

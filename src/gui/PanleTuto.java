package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Image;
import java.awt.MediaTracker;
import java.awt.image.BufferedImage;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
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
    
    private int imageLargeurDefaut = 200;
    private int imageHauteurDefaut = 150;

    
    public PanleTuto(String titre, String[] sections, String[][] contenuSections, String[][] images) {
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
            String[] imageInfo = (images != null && i < images.length && images[i].length >= 3) ? images[i] : null;
            String imageNom = (imageInfo != null) ? imageInfo[0] : null;
            int imageLarg = (imageInfo != null && imageInfo[1] != null) ? Integer.parseInt(imageInfo[1]) : imageLargeurDefaut;
            int imageHaut = (imageInfo != null && imageInfo[2] != null) ? Integer.parseInt(imageInfo[2]) : imageHauteurDefaut;
            for (int j = 0; j < lignes.length; j++) {
                boolean ajouterImage = (j == lignes.length - 1) && imageNom != null;
                ajouterLigne(contenu, lignes[j], ajouterImage ? imageNom : null, ajouterImage ? imageLarg : -1, ajouterImage ? imageHaut : -1);
            }
            contenu.add(Box.createVerticalStrut(12));
        }

        JScrollPane scroll = new JScrollPane(contenu);
        scroll.setBorder(BorderFactory.createEmptyBorder());
        scroll.getVerticalScrollBar().setUnitIncrement(16);

        add(labelTitre, BorderLayout.NORTH);
        add(scroll, BorderLayout.CENTER);
    }
    
    public void setImageSize(int largeur, int hauteur) {
        this.imageLargeurDefaut = largeur;
        this.imageHauteurDefaut = hauteur;
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

    private void ajouterLigne(JPanel parent, String texte, String imagePath, int customLar, int customHaut) {
        int lar = (customLar > 0) ? customLar : imageLargeurDefaut;
        int haut = (customHaut > 0) ? customHaut : imageHauteurDefaut;
        
        JPanel ligne = new JPanel();
        ligne.setLayout(new BoxLayout(ligne, BoxLayout.Y_AXIS));
        ligne.setBackground(COULEUR_FOND_SECTION);
        ligne.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(75, 105, 78), 1),
            BorderFactory.createEmptyBorder(8, 8, 8, 8)
        ));
        ligne.setAlignmentX(LEFT_ALIGNMENT);
        ligne.setMaximumSize(new Dimension(Integer.MAX_VALUE, 2000));
        
        JLabel texteLabel = new JLabel("<html>" + texte.replace("\n", "<br>") + "</html>");
        texteLabel.setForeground(COULEUR_TEXTE);
        texteLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        texteLabel.setAlignmentX(LEFT_ALIGNMENT);
        ligne.add(texteLabel);
        
        if (imagePath != null && !imagePath.isEmpty()) {
            ligne.add(Box.createVerticalStrut(8));
            JPanel imagePanel = new JPanel();
            imagePanel.setLayout(new BorderLayout(0, 0));
            imagePanel.setOpaque(false);
            imagePanel.setPreferredSize(new Dimension(lar, haut));
            imagePanel.setMaximumSize(new Dimension(lar, haut));
            imagePanel.setAlignmentX(LEFT_ALIGNMENT);
            
            try {
                String basePath = System.getProperty("user.dir");
                String fullPath = basePath + "/src/image/" + imagePath;
                
                java.io.File filePng = new java.io.File(fullPath + ".png");
                java.io.File fileJpg = new java.io.File(fullPath + ".jpg");
                
                String actualPath = null;
                if (filePng.exists()) {
                    actualPath = fullPath + ".png";
                } else if (fileJpg.exists()) {
                    actualPath = fullPath + ".jpg";
                }
                
                System.out.println("Found: " + actualPath);
                
                if (actualPath != null) {
                    ImageIcon icon = new ImageIcon(actualPath);
                    Image img = icon.getImage();
                    Image scaledImg = img.getScaledInstance(lar, haut, Image.SCALE_SMOOTH);
                    ImageIcon scaledIcon = new ImageIcon(scaledImg);
                    
                    JLabel imageLabel = new JLabel(scaledIcon);
                    imageLabel.setBorder(BorderFactory.createLineBorder(new Color(60, 60, 60), 2));
                    imagePanel.add(imageLabel, BorderLayout.CENTER);
                } else {
                    JLabel placeholder = new JLabel("[IMAGE]");
                    placeholder.setPreferredSize(new Dimension(lar, haut));
                    placeholder.setForeground(new Color(80, 80, 80));
                    placeholder.setBackground(new Color(30, 35, 30));
                    placeholder.setOpaque(true);
                    placeholder.setBorder(BorderFactory.createLineBorder(new Color(60, 60, 60), 2));
                    placeholder.setHorizontalAlignment(JLabel.CENTER);
                    placeholder.setVerticalAlignment(JLabel.CENTER);
                    imagePanel.add(placeholder, BorderLayout.CENTER);
                }
            } catch (Exception e) {
                JLabel placeholder = new JLabel("[ERREUR]");
                placeholder.setPreferredSize(new Dimension(lar, haut));
                placeholder.setForeground(new Color(150, 50, 50));
                placeholder.setBackground(new Color(30, 35, 30));
                placeholder.setOpaque(true);
                placeholder.setBorder(BorderFactory.createLineBorder(new Color(150, 50, 50), 2));
                placeholder.setHorizontalAlignment(JLabel.CENTER);
                placeholder.setVerticalAlignment(JLabel.CENTER);
                imagePanel.add(placeholder, BorderLayout.CENTER);
            }
            
            ligne.add(imagePanel, BorderLayout.SOUTH);
        }
        
        parent.add(ligne);
        parent.add(Box.createVerticalStrut(6));
    }
}

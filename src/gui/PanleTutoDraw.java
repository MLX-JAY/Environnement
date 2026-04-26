package gui;

import config.GameConfiguration;
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
import moteur.donne.biome.Banquise;
import moteur.donne.biome.Biome;
import moteur.donne.biome.Desert;
import moteur.donne.biome.Foret;
import moteur.donne.biome.Mer;
import moteur.donne.biome.Montagne;
import moteur.donne.biome.Village;
import moteur.donne.biome.Ville;
import moteur.donne.carte.Bloc;
import moteur.donne.carte.Carte;
import moteur.donne.evenement.Evenement;
import moteur.donne.evenement.mobile.Grele;
import moteur.donne.evenement.mobile.NuageToxique;
import moteur.donne.evenement.mobile.Orage;
import moteur.donne.evenement.mobile.Pluie;
import moteur.donne.evenement.mobile.PluieAcide;
import moteur.donne.evenement.mobile.PluieBenite;
import moteur.donne.evenement.mobile.Pollution;
import moteur.donne.evenement.mobile.Purification;
import moteur.donne.evenement.mobile.Smog;
import moteur.donne.evenement.mobile.Tonnerre;
import moteur.donne.evenement.mobile.Tornade;
import moteur.donne.evenement.mobile.VentChaud;
import moteur.donne.evenement.mobile.VentFroid;
import moteur.donne.evenement.mobile.Zephyr;
import moteur.donne.evenement.statique.Meteore;
import moteur.processus.usine.BiomeFactory;
import moteur.processus.usine.BiomeFactory.TypeBiome;
import moteur.processus.usine.EvenementFactory;

public class PanleTutoDraw extends JPanel {

    private final StrategiePeinture strategie;
    private final EvenementFactory evenementFactory;
    private final Bloc blocReference;

    public PanleTutoDraw() {
        setLayout(new BorderLayout(10, 10));
        setBackground(new Color(40, 54, 24));
        setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        this.strategie = new StrategiePeinture();
        Carte carteApercu = new Carte(1, 1);
        this.evenementFactory = new EvenementFactory(carteApercu);
        this.blocReference = new Bloc(0, 0);

        JLabel titre = new JLabel("Apercu visuel des draws");
        titre.setForeground(new Color(255, 220, 140));
        titre.setFont(new Font("Segoe UI", Font.BOLD, 24));

        JPanel contenu = new JPanel();
        contenu.setLayout(new BoxLayout(contenu, BoxLayout.Y_AXIS));
        contenu.setBackground(new Color(52, 78, 65));
        contenu.setBorder(BorderFactory.createEmptyBorder(12, 12, 12, 12));

        ajouterSection(contenu, "Evenements");
        ajouterLigneEvenement(contenu, "Pluie", "Humidite +5", evenementFactory.creerPluie(blocReference));
        ajouterLigneEvenement(contenu, "Orage", "Humidite +10", evenementFactory.creerOrage(blocReference));
        ajouterLigneEvenement(contenu, "Tonnerre", "Humidite +8", evenementFactory.creerTonnerre(blocReference));
        ajouterLigneEvenement(contenu, "Vent froid", "Temperature -5", evenementFactory.creerVentFroid(blocReference));
        ajouterLigneEvenement(contenu, "Grele", "Temperature -10, Humidite +5", evenementFactory.creerGrele(blocReference));
        ajouterLigneEvenement(contenu, "Vent chaud", "Temperature -5 (impl. actuelle)", evenementFactory.creerVentChaud(blocReference));
        ajouterLigneEvenement(contenu, "Zephyr", "Temperature +5", evenementFactory.creerZephyr(blocReference));
        ajouterLigneEvenement(contenu, "Tornade", "Humidite +5, Pollution +5", evenementFactory.creerTornade(blocReference));
        ajouterLigneEvenement(contenu, "Pollution", "Pollution +10", evenementFactory.creerPollution(blocReference));
        ajouterLigneEvenement(contenu, "Smog", "Pollution +15", evenementFactory.creerSmog(blocReference));
        ajouterLigneEvenement(contenu, "Nuage toxique", "Pollution +20", evenementFactory.creerNuageToxique(blocReference));
        ajouterLigneEvenement(contenu, "Purification", "Purification +5", evenementFactory.creerPurification(blocReference));
        ajouterLigneEvenement(contenu, "Pluie benite", "Humidite +8, Pollution -5, Purification +10", evenementFactory.creerPluieBenite(blocReference));
        ajouterLigneEvenement(contenu, "Pluie acide", "Humidite +5, Pollution +10", evenementFactory.creerPluieAcide(blocReference));
        ajouterLigneEvenement(contenu, "Meteore", "Temperature +50, Pollution +20", evenementFactory.creerMeteore(blocReference));

        ajouterSection(contenu, "Biomes");
        ajouterLigneBiome(contenu, "Foret", "Genere: Purification, Pluie benite", TypeBiome.FORET);
        ajouterLigneBiome(contenu, "Desert", "Genere: Vent chaud, Zephyr, Tornade", TypeBiome.DESERT);
        ajouterLigneBiome(contenu, "Mer", "Genere: Pluie, Orage, Tonnerre", TypeBiome.MER);
        ajouterLigneBiome(contenu, "Banquise", "Genere: Vent froid, Grele", TypeBiome.BANQUISE);
        ajouterLigneBiome(contenu, "Ville", "Genere: Pollution, Smog, Nuage toxique", TypeBiome.VILLE);
        ajouterLigneBiome(contenu, "Village", "Genere: Pollution", TypeBiome.VILLAGE);
        ajouterLigneBiome(contenu, "Montagne", "Ne genere pas d'evenement", TypeBiome.MONTAGNE);

        JScrollPane scroll = new JScrollPane(contenu);
        scroll.setBorder(BorderFactory.createEmptyBorder());
        scroll.getVerticalScrollBar().setUnitIncrement(16);

        add(titre, BorderLayout.NORTH);
        add(scroll, BorderLayout.CENTER);
    }

    private void ajouterSection(JPanel parent, String texte) {
        JLabel section = new JLabel(texte);
        section.setForeground(new Color(255, 220, 140));
        section.setFont(new Font("Segoe UI", Font.BOLD, 20));
        section.setAlignmentX(LEFT_ALIGNMENT);

        parent.add(section);
        parent.add(Box.createVerticalStrut(8));
    }

    private void ajouterLigneEvenement(JPanel parent, String nom, String details, Evenement evenement) {
        JPanel ligne = creerLigneBase(nom, details, g2 -> dessinerEvenement(g2, evenement));
        parent.add(ligne);
        parent.add(Box.createVerticalStrut(8));
    }

    private void ajouterLigneBiome(JPanel parent, String nom, String details, TypeBiome typeBiome) {
        Biome biome = BiomeFactory.creerBiomeParType(typeBiome, blocReference);
        JPanel ligne = creerLigneBase(nom, details, g2 -> dessinerBiome(g2, biome));
        parent.add(ligne);
        parent.add(Box.createVerticalStrut(8));
    }

    private JPanel creerLigneBase(String nom, String details, DessinApercu dessinApercu) {
        JPanel ligne = new JPanel(new BorderLayout(12, 0));
        ligne.setBackground(new Color(44, 67, 56));
        ligne.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(75, 105, 78), 1),
            BorderFactory.createEmptyBorder(8, 8, 8, 8)
        ));
        ligne.setAlignmentX(LEFT_ALIGNMENT);
        ligne.setMaximumSize(new Dimension(Integer.MAX_VALUE, 110));

        JPanel vignette = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

                g2.setColor(new Color(20, 28, 25));
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 10, 10);

                double marge = 6.0;
                double echelle = Math.min(
                    (getWidth() - marge * 2.0) / GameConfiguration.TAILLE_BLOC,
                    (getHeight() - marge * 2.0) / GameConfiguration.TAILLE_BLOC
                );
                double decalageX = (getWidth() - GameConfiguration.TAILLE_BLOC * echelle) / 2.0;
                double decalageY = (getHeight() - GameConfiguration.TAILLE_BLOC * echelle) / 2.0;

                g2.translate(decalageX, decalageY);
                g2.scale(echelle, echelle);
                dessinApercu.dessiner(g2);
                g2.dispose();
            }
        };
        vignette.setPreferredSize(new Dimension(86, 86));
        vignette.setOpaque(false);

        JPanel texte = new JPanel();
        texte.setLayout(new BoxLayout(texte, BoxLayout.Y_AXIS));
        texte.setBackground(new Color(44, 67, 56));

        JLabel nomLabel = new JLabel(nom);
        nomLabel.setForeground(new Color(255, 230, 165));
        nomLabel.setFont(new Font("Segoe UI", Font.BOLD, 16));
        nomLabel.setAlignmentX(LEFT_ALIGNMENT);

        JLabel detailsLabel = new JLabel("<html>" + details + "</html>");
        detailsLabel.setForeground(Color.WHITE);
        detailsLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        detailsLabel.setAlignmentX(LEFT_ALIGNMENT);

        texte.add(nomLabel);
        texte.add(Box.createVerticalStrut(4));
        texte.add(detailsLabel);

        ligne.add(vignette, BorderLayout.WEST);
        ligne.add(texte, BorderLayout.CENTER);
        return ligne;
    }

    private void dessinerEvenement(Graphics2D g2, Evenement evenement) {
        if (evenement == null) {
            return;
        }
        if (evenement instanceof Pluie) {
            strategie.paint((Pluie) evenement, g2);
        }
        if (evenement instanceof PluieAcide) {
            strategie.paint((PluieAcide) evenement, g2);
        }
        if (evenement instanceof VentFroid) {
            strategie.paint((VentFroid) evenement, g2);
        }
        if (evenement instanceof VentChaud) {
            strategie.paint((VentChaud) evenement, g2);
        }
        if (evenement instanceof Pollution) {
            strategie.paint((Pollution) evenement, g2);
        }
        if (evenement instanceof Purification) {
            strategie.paint((Purification) evenement, g2);
        }
        if (evenement instanceof Orage) {
            strategie.paint((Orage) evenement, g2);
        }
        if (evenement instanceof Grele) {
            strategie.paint((Grele) evenement, g2);
        }
        if (evenement instanceof Tornade) {
            strategie.paint((Tornade) evenement, g2);
        }
        if (evenement instanceof PluieBenite) {
            strategie.paint((PluieBenite) evenement, g2);
        }
        if (evenement instanceof Zephyr) {
            strategie.paint((Zephyr) evenement, g2);
        }
        if (evenement instanceof Tonnerre) {
            strategie.paint((Tonnerre) evenement, g2);
        }
        if (evenement instanceof Smog) {
            strategie.paint((Smog) evenement, g2);
        }
        if (evenement instanceof NuageToxique) {
            strategie.paint((NuageToxique) evenement, g2);
        }
        if (evenement instanceof Meteore) {
            Meteore meteore = (Meteore) evenement;
            strategie.paintDanger(meteore, g2);
            strategie.paint(meteore, g2);
        }
    }

    private void dessinerBiome(Graphics2D g2, Biome biome) {
        if (biome == null) {
            return;
        }
        if (biome instanceof Foret) {
            strategie.paint((Foret) biome, g2);
        }
        if (biome instanceof Desert) {
            strategie.paint((Desert) biome, g2);
        }
        if (biome instanceof Mer) {
            strategie.paint((Mer) biome, g2);
        }
        if (biome instanceof Banquise) {
            strategie.paint((Banquise) biome, g2);
        }
        if (biome instanceof Montagne) {
            strategie.paint((Montagne) biome, g2);
        }
        if (biome instanceof Village) {
            strategie.paint((Village) biome, g2);
        }
        if (biome instanceof Ville) {
            strategie.paint((Ville) biome, g2);
        }
    }

    @FunctionalInterface
    private interface DessinApercu {
        void dessiner(Graphics2D g2);
    }
}

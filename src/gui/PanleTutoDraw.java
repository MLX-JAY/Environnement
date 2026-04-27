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

    private static final Color COULEUR_HUMIDITE = new Color(64, 144, 224);
    private static final Color COULEUR_TEMPERATURE = new Color(224, 112, 32);
    private static final Color COULEUR_POLLUTION = new Color(112, 112, 112);
    private static final Color COULEUR_PURIFICATION = new Color(64, 160, 64);
    private static final Color COULEUR_MIXTE = new Color(128, 96, 160);
    private static final Color COULEUR_NEUTRE = new Color(150, 150, 150);
    
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
        ajouterLigneEvenement(contenu, "Pluie", "Humidite +5", COULEUR_HUMIDITE, 5, 10, evenementFactory.creerPluie(blocReference));
        ajouterLigneEvenement(contenu, "Orage", "Humidite +10", COULEUR_HUMIDITE, 10, 10, evenementFactory.creerOrage(blocReference));
        ajouterLigneEvenement(contenu, "Tonnerre", "Humidite +8", COULEUR_HUMIDITE, 8, 10, evenementFactory.creerTonnerre(blocReference));
        ajouterLigneEvenement(contenu, "Vent froid", "Temperature -5", COULEUR_TEMPERATURE, -5, 10, evenementFactory.creerVentFroid(blocReference));
        ajouterLigneEvenement(contenu, "Grele", "Temp -10, Humid +5", COULEUR_MIXTE, 10, 10, evenementFactory.creerGrele(blocReference));
        ajouterLigneEvenement(contenu, "Vent chaud", "Temperature +10", COULEUR_TEMPERATURE, 10, 10, evenementFactory.creerVentChaud(blocReference));
        ajouterLigneEvenement(contenu, "Zephyr", "Temp +5, Purif +10", COULEUR_TEMPERATURE, 10, 10, evenementFactory.creerZephyr(blocReference));
        ajouterLigneEvenement(contenu, "Tornade", "Humid +5, Poll +5", COULEUR_MIXTE, 5, 10, evenementFactory.creerTornade(blocReference));
        ajouterLigneEvenement(contenu, "Pollution", "Pollution +10", COULEUR_POLLUTION, 10, 10, evenementFactory.creerPollution(blocReference));
        ajouterLigneEvenement(contenu, "Smog", "Pollution +15", COULEUR_POLLUTION, 15, 10, evenementFactory.creerSmog(blocReference));
        ajouterLigneEvenement(contenu, "Nuage toxique", "Pollution +20", COULEUR_POLLUTION, 20, 10, evenementFactory.creerNuageToxique(blocReference));
        ajouterLigneEvenement(contenu, "Purification", "Purification +5", COULEUR_PURIFICATION, 5, 10, evenementFactory.creerPurification(blocReference));
        ajouterLigneEvenement(contenu, "Pluie benite", "Humid +8, Poll -5", COULEUR_PURIFICATION, 8, 10, evenementFactory.creerPluieBenite(blocReference));
        ajouterLigneEvenement(contenu, "Pluie acide", "Humid +5, Poll +10", COULEUR_MIXTE, 10, 10, evenementFactory.creerPluieAcide(blocReference));
        ajouterLigneEvenement(contenu, "Meteore", "Temp +50, Poll +20", COULEUR_MIXTE, 20, 10, evenementFactory.creerMeteore(blocReference));

        ajouterSection(contenu, "Biomes");
        ajouterLigneBiome(contenu, "Foret", new Object[]{TypeBiome.FORET}, 
            new Evenement[]{evenementFactory.creerPurification(blocReference), evenementFactory.creerPluieBenite(blocReference)});
        ajouterLigneBiome(contenu, "Desert", new Object[]{TypeBiome.DESERT}, 
            new Evenement[]{evenementFactory.creerVentChaud(blocReference), evenementFactory.creerZephyr(blocReference), evenementFactory.creerTornade(blocReference)});
        ajouterLigneBiome(contenu, "Mer", new Object[]{TypeBiome.MER}, 
            new Evenement[]{evenementFactory.creerPluie(blocReference), evenementFactory.creerOrage(blocReference), evenementFactory.creerTonnerre(blocReference)});
        ajouterLigneBiome(contenu, "Banquise", new Object[]{TypeBiome.BANQUISE}, 
            new Evenement[]{evenementFactory.creerVentFroid(blocReference), evenementFactory.creerGrele(blocReference)});
        ajouterLigneBiome(contenu, "Ville", new Object[]{TypeBiome.VILLE}, 
            new Evenement[]{evenementFactory.creerPollution(blocReference), evenementFactory.creerSmog(blocReference), evenementFactory.creerNuageToxique(blocReference)});
        ajouterLigneBiome(contenu, "Village", new Object[]{TypeBiome.VILLAGE}, 
            new Evenement[]{evenementFactory.creerPollution(blocReference)});
        ajouterLigneBiome(contenu, "Montagne", new Object[]{TypeBiome.MONTAGNE}, 
            new Evenement[]{});

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

    private void ajouterLigneEvenement(JPanel parent, String nom, String details, Color couleurBadge, int valeur, int maxValeur, Evenement evenement) {
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
                dessinerEvenement(g2, evenement);
                g2.dispose();
            }
        };
        vignette.setPreferredSize(new Dimension(86, 86));
        vignette.setOpaque(false);

        JPanel texte = new JPanel();
        texte.setLayout(new BoxLayout(texte, BoxLayout.Y_AXIS));
        texte.setBackground(new Color(44, 67, 56));

        JPanel header = new JPanel(new BorderLayout(0, 0));
        header.setBackground(new Color(44, 67, 56));
        
        JLabel nomLabel = new JLabel(nom);
        nomLabel.setForeground(new Color(255, 230, 165));
        nomLabel.setFont(new Font("Segoe UI", Font.BOLD, 18));
        
        JLabel genereLabel = new JLabel("Génère : ");
        genereLabel.setForeground(couleurBadge);
        genereLabel.setFont(new Font("Segoe UI", Font.BOLD, 14));
        
        JLabel effetLabel = new JLabel(details);
        effetLabel.setForeground(Color.WHITE);
        effetLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        
        JPanel detailsPanel = new JPanel(new BorderLayout(0, 0));
        detailsPanel.setBackground(new Color(44, 67, 56));
        detailsPanel.add(genereLabel, BorderLayout.WEST);
        detailsPanel.add(effetLabel, BorderLayout.CENTER);

        header.add(nomLabel, BorderLayout.NORTH);
        
        JPanel generePanel = new JPanel(new BorderLayout(0, 0));
        generePanel.setBackground(new Color(44, 67, 56));
        generePanel.add(detailsPanel, BorderLayout.CENTER);

        JPanel barrePanel = new JPanel(new BorderLayout(0, 0));
        barrePanel.setBackground(new Color(44, 67, 56));
        
        int barreLargeur = Math.min(100, Math.abs(valeur) * 8);
        JPanel barre = new JPanel();
        barre.setPreferredSize(new Dimension(barreLargeur, 12));
        barre.setBackground(couleurBadge);
        
        barrePanel.add(barre, BorderLayout.WEST);

        texte.add(header);
        texte.add(Box.createVerticalStrut(2));
        texte.add(generePanel);
        texte.add(barrePanel);

        ligne.add(texte, BorderLayout.WEST);
        ligne.add(vignette, BorderLayout.EAST);
        parent.add(ligne);
        parent.add(Box.createVerticalStrut(8));
    }

    private void ajouterLigneBiome(JPanel parent, String nom, Object[] typeBiome, Evenement[] evenements) {
        JPanel ligne = new JPanel(new BorderLayout(12, 0));
        ligne.setBackground(new Color(44, 67, 56));
        ligne.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(75, 105, 78), 1),
            BorderFactory.createEmptyBorder(8, 8, 8, 8)
        ));
        ligne.setAlignmentX(LEFT_ALIGNMENT);
        ligne.setMaximumSize(new Dimension(Integer.MAX_VALUE, 110));

        TypeBiome type = (TypeBiome) typeBiome[0];
        Biome biome = BiomeFactory.creerBiomeParType(type, blocReference);

        JPanel texte = new JPanel();
        texte.setLayout(new BoxLayout(texte, BoxLayout.Y_AXIS));
        texte.setBackground(new Color(44, 67, 56));

        JPanel header = new JPanel(new BorderLayout(0, 0));
        header.setBackground(new Color(44, 67, 56));
        
        JLabel nomLabel = new JLabel(nom);
        nomLabel.setForeground(new Color(255, 230, 165));
        nomLabel.setFont(new Font("Segoe UI", Font.BOLD, 18));
        
        JLabel genereLabel = new JLabel("Génère : ");
        genereLabel.setForeground(new Color(255, 220, 140));
        genereLabel.setFont(new Font("Segoe UI", Font.BOLD, 14));
        
        JLabel aucunLabel = new JLabel("Aucun");
        aucunLabel.setForeground(new Color(150, 150, 150));
        aucunLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14));

        JPanel eventsPanel = new JPanel();
        eventsPanel.setLayout(new BoxLayout(eventsPanel, BoxLayout.Y_AXIS));
        eventsPanel.setBackground(new Color(44, 67, 56));

        if (evenements.length == 0) {
            eventsPanel.add(aucunLabel);
        } else {
            JPanel eventsLigne = new JPanel();
            eventsLigne.setLayout(new BoxLayout(eventsLigne, BoxLayout.X_AXIS));
            eventsLigne.setBackground(new Color(44, 67, 56));
            
            for (Evenement evt : evenements) {
                JPanel miniVignette = new JPanel() {
                    @Override
                    protected void paintComponent(Graphics g) {
                        super.paintComponent(g);
                        Graphics2D g2 = (Graphics2D) g.create();
                        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                        g2.setColor(new Color(30, 35, 30));
                        g2.fillRoundRect(0, 0, getWidth(), getHeight(), 6, 6);
                        double echelle = Math.min(getWidth(), getHeight()) * 0.8 / GameConfiguration.TAILLE_BLOC;
                        double decalageX = (getWidth() - GameConfiguration.TAILLE_BLOC * echelle) / 2.0;
                        double decalageY = (getHeight() - GameConfiguration.TAILLE_BLOC * echelle) / 2.0;
                        g2.translate(decalageX, decalageY);
                        g2.scale(echelle, echelle);
                        dessinerEvenement(g2, evt);
                        g2.dispose();
                    }
                };
                miniVignette.setPreferredSize(new Dimension(36, 36));
                miniVignette.setMaximumSize(new Dimension(36, 36));
                eventsLigne.add(miniVignette);
                eventsLigne.add(Box.createHorizontalStrut(2));
            }
            eventsPanel.add(eventsLigne);
        }

        header.add(nomLabel, BorderLayout.NORTH);
        
        JPanel generePanel = new JPanel(new BorderLayout(0, 0));
        generePanel.setBackground(new Color(44, 67, 56));
        generePanel.add(genereLabel, BorderLayout.WEST);
        generePanel.add(eventsPanel, BorderLayout.CENTER);
        
        texte.add(header);
        texte.add(Box.createVerticalStrut(2));
        texte.add(generePanel);

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
                dessinerBiome(g2, biome);
                g2.dispose();
            }
        };
        vignette.setPreferredSize(new Dimension(86, 86));
        vignette.setOpaque(false);

        ligne.add(texte, BorderLayout.WEST);
        ligne.add(vignette, BorderLayout.EAST);
        parent.add(ligne);
        parent.add(Box.createVerticalStrut(8));
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

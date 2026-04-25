package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.util.ArrayList;
import java.util.HashMap;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import moteur.processus.Manageur;
import moteur.processus.ManageurBasique;
import moteur.processus.ManageurBasique.StatRound;

public class PanelBilanDeFin extends JPanel {

    private static final Color COULEUR_FOND = new Color(40, 54, 24);
    private static final Color COULEUR_FOND_SECTION = new Color(52, 78, 65);
    private static final Color COULEUR_TITRE = new Color(255, 220, 140);
    private static final Color COULEUR_POSITIF = new Color(100, 200, 100);
    private static final Color COULEUR_NEGATIF = new Color(200, 100, 100);
    private static final Color COULEUR_NEUTRE = new Color(150, 150, 150);
    private static final Color COULEUR_TEMP = new Color(255, 140, 0);
    private static final Color COULEUR_HUMID = new Color(0, 191, 255);
    private static final Color COULEUR_POLL = new Color(220, 20, 60);
    private static final Color COULEUR_PURIF = new Color(50, 205, 50);

    private Manageur manageur;
    private MainDisplayer displayer;

    public PanelBilanDeFin(Manageur manageur, MainDisplayer displayer) {
        this.manageur = manageur;
        this.displayer = displayer;

        this.setBackground(COULEUR_FOND);
        this.setLayout(new BorderLayout(10, 10));

        initComposants();
    }

    private void initComposants() {
        JPanel haut = new JPanel(new FlowLayout(FlowLayout.CENTER, 30, 15));
        haut.setBackground(COULEUR_FOND);

        JLabel titre = new JLabel("BILAN DE FIN");
        titre.setForeground(COULEUR_TITRE);
        titre.setFont(new Font("Segoe UI", Font.BOLD, 28));
        haut.add(titre);

        this.add(haut, BorderLayout.NORTH);

        JPanel centre = new JPanel(new BorderLayout(20, 20));
        centre.setBackground(COULEUR_FOND);
        centre.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        if (displayer != null) {
            centre.add(displayer, BorderLayout.CENTER);
        }
        
        JPanel contenu = new JPanel(new GridBagLayout());
        contenu.setBackground(COULEUR_FOND);
        contenu.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 8, 5, 8);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;
        gbc.weighty = 0;

        int row = 0;

        gbc.gridx = 0;
        gbc.gridy = row;
        gbc.gridwidth = 2;
        contenu.add(creerBlocDuree(), gbc);
        row++;

        gbc.gridwidth = 1;
        gbc.gridy = row;
        contenu.add(creerBlocBiomes(), gbc);
        gbc.gridx = 1;
        contenu.add(creerBlocEvenements(), gbc);
        
        row++;
        gbc.gridx = 0;
        gbc.gridy = row;
        contenu.add(creerBlocMetriques(), gbc);
        gbc.gridx = 1;
        contenu.add(creerBlocSante(), gbc);
        
        row++;
        gbc.gridx = 0;
        gbc.gridy = row;
        gbc.gridwidth = 2;
        contenu.add(creerBlocMoteur(), gbc);

        centre.add(contenu, BorderLayout.EAST);

        this.add(centre, BorderLayout.CENTER);

        JPanel bas = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 15));
        bas.setBackground(COULEUR_FOND);

        JButton btnRetour = deseign.creerBeauBouton("Recommencer", new Color(100, 100, 180));
        btnRetour.addActionListener(e -> {
            MainGUI gui = (MainGUI) javax.swing.SwingUtilities.getWindowAncestor(this);
            if (gui != null) {
                gui.nouvelleSession();
            }
        });
        bas.add(btnRetour);

        JButton btnStat = deseign.creerBeauBouton("Statistiques", new Color(180, 100, 100));
        btnStat.addActionListener(e -> {
            MainGUI gui = (MainGUI) javax.swing.SwingUtilities.getWindowAncestor(this);
            if (gui != null) {
                JFrameStatistiques frame = new JFrameStatistiques(gui.getManageur());
                frame.setVisible(true);
            }
        });
        bas.add(btnStat);

        this.add(bas, BorderLayout.SOUTH);
    }

    private int obtenirValeur(HashMap<String, Integer> map, String cle) {
        Integer val = map.get(cle);
        return (val != null) ? val : 0;
    }

    private JPanel creerBlocDuree() {
        JPanel bloc = new JPanel(new BorderLayout());
        bloc.setBackground(COULEUR_FOND_SECTION);
        bloc.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(COULEUR_TITRE, 2),
            BorderFactory.createEmptyBorder(10, 10, 10, 10)
        ));

        if (!(manageur instanceof ManageurBasique)) {
            JLabel lbl = new JLabel("Pas de donnees disponibles");
            lbl.setForeground(Color.WHITE);
            lbl.setFont(new Font("Segoe UI", Font.BOLD, 16));
            bloc.add(lbl, BorderLayout.CENTER);
            return bloc;
        }

        ManageurBasique mb = (ManageurBasique) manageur;
        ArrayList<StatRound> historique = mb.getHistorique();
        int duree = historique != null && !historique.isEmpty() ? historique.get(historique.size() - 1).round : 0;

        JPanel content = new JPanel(new GridBagLayout());
        content.setBackground(COULEUR_FOND_SECTION);
        GridBagConstraints gbc = new GridBagConstraints();

        JLabel lblDuree = new JLabel("DUREE DE LA PARTIE");
        lblDuree.setForeground(COULEUR_TITRE);
        lblDuree.setFont(new Font("Segoe UI", Font.BOLD, 14));
        gbc.gridx = 0;
        gbc.gridy = 0;
        content.add(lblDuree, gbc);

        JLabel lblValeur = new JLabel(duree + " rounds");
        lblValeur.setForeground(Color.WHITE);
        lblValeur.setFont(new Font("Segoe UI", Font.BOLD, 24));
        gbc.gridy = 1;
        gbc.insets = new Insets(5, 0, 0, 0);
        content.add(lblValeur, gbc);

        bloc.add(content, BorderLayout.CENTER);
        return bloc;
    }

    private JPanel creerBlocBiomes() {
        JPanel bloc = creerBlocSection("BIOMES", new Color(34, 120, 34));
        
        if (!(manageur instanceof ManageurBasique)) {
            JLabel lbl = new JLabel("Pas de donnees");
            lbl.setForeground(Color.WHITE);
            bloc.add(lbl, BorderLayout.CENTER);
            return bloc;
        }

        ManageurBasique mb = (ManageurBasique) manageur;
        ArrayList<StatRound> historique = mb.getHistorique();
        if (historique == null || historique.isEmpty()) {
            JLabel lbl = new JLabel("Aucune donnee");
            lbl.setForeground(Color.WHITE);
            bloc.add(lbl, BorderLayout.CENTER);
            return bloc;
        }

        StatRound dernier = historique.get(historique.size() - 1);
        HashMap<String, Integer> biomes = dernier.compteBiomes;
        
        String meilleur = "";
        String moinsBon = "";
        int maxVal = Integer.MIN_VALUE;
        int minVal = Integer.MAX_VALUE;

        for (String key : biomes.keySet()) {
            int val = obtenirValeur(biomes, key);
            if (val > maxVal) { maxVal = val; meilleur = key; }
            if (val < minVal) { minVal = val; moinsBon = key; }
        }

        JPanel content = new JPanel(new GridBagLayout());
        content.setBackground(COULEUR_FOND_SECTION);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(2, 10, 2, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;

        gbc.gridx = 0;
        gbc.gridy = 0;
        content.add(creerLigne("Le meilleur bloc est :", COULEUR_NEUTRE), gbc);
        gbc.gridy = 1;
        content.add(creerLigne(meilleur + " (" + maxVal + ")", COULEUR_POSITIF), gbc);
        gbc.gridy = 2;
        content.add(creerLigne("Le moins bon bloc est :", COULEUR_NEUTRE), gbc);
        gbc.gridy = 3;
        content.add(creerLigne(moinsBon + " (" + minVal + ")", COULEUR_NEGATIF), gbc);

        bloc.add(content, BorderLayout.CENTER);
        return bloc;
    }

    private JPanel creerBlocEvenements() {
        JPanel bloc = creerBlocSection("EVENEMENTS", new Color(150, 100, 180));
        
        if (!(manageur instanceof ManageurBasique)) {
            JLabel lbl = new JLabel("Pas de donnees");
            lbl.setForeground(Color.WHITE);
            bloc.add(lbl, BorderLayout.CENTER);
            return bloc;
        }

        ManageurBasique mb = (ManageurBasique) manageur;
        ArrayList<StatRound> historique = mb.getHistorique();
        if (historique == null || historique.isEmpty()) {
            JLabel lbl = new JLabel("Aucune donnee");
            lbl.setForeground(Color.WHITE);
            bloc.add(lbl, BorderLayout.CENTER);
            return bloc;
        }

        HashMap<String, Integer> totalEvents = new HashMap<>();
        for (StatRound sr : historique) {
            for (String key : sr.compteEvenements.keySet()) {
                int val = obtenirValeur(totalEvents, key) + obtenirValeur(sr.compteEvenements, key);
                totalEvents.put(key, val);
            }
        }

        String meilleur = "";
        String moinsBon = "";
        int maxVal = Integer.MIN_VALUE;
        int minVal = Integer.MAX_VALUE;

        for (String key : totalEvents.keySet()) {
            int val = obtenirValeur(totalEvents, key);
            if (val > maxVal) { maxVal = val; meilleur = key; }
            if (val < minVal) { minVal = val; moinsBon = key; }
        }

        JPanel content = new JPanel(new GridBagLayout());
        content.setBackground(COULEUR_FOND_SECTION);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(2, 10, 2, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;

        gbc.gridx = 0;
        gbc.gridy = 0;
        content.add(creerLigne("L'evenement le plus frequent :", COULEUR_NEUTRE), gbc);
        gbc.gridy = 1;
        content.add(creerLigne(meilleur + " (" + maxVal + ")", COULEUR_POSITIF), gbc);
        gbc.gridy = 2;
        content.add(creerLigne("L'evenement le moins frequent :", COULEUR_NEUTRE), gbc);
        gbc.gridy = 3;
        content.add(creerLigne(moinsBon + " (" + minVal + ")", COULEUR_NEGATIF), gbc);

        bloc.add(content, BorderLayout.CENTER);
        return bloc;
    }

    private JPanel creerBlocMetriques() {
        JPanel bloc = creerBlocSection("METRIQUES GLOBALES", COULEUR_TITRE);
        
        if (!(manageur instanceof ManageurBasique)) {
            JLabel lbl = new JLabel("Pas de donnees");
            lbl.setForeground(Color.WHITE);
            bloc.add(lbl, BorderLayout.CENTER);
            return bloc;
        }

        ManageurBasique mb = (ManageurBasique) manageur;
        ArrayList<StatRound> historique = mb.getHistorique();
        if (historique == null || historique.isEmpty()) {
            JLabel lbl = new JLabel("Aucune donnee");
            lbl.setForeground(Color.WHITE);
            bloc.add(lbl, BorderLayout.CENTER);
            return bloc;
        }

        StatRound dernier = historique.get(historique.size() - 1);
        double temp = dernier.moyennes[0];
        double humid = dernier.moyennes[1];
        double poll = dernier.moyennes[2];
        double purif = dernier.moyennes[3];

        JPanel content = new JPanel(new GridBagLayout());
        content.setBackground(COULEUR_FOND_SECTION);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(2, 10, 2, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;

        int r = 0;
        gbc.gridx = 0;
        gbc.gridy = r;
        content.add(creerLigne("Temperature", COULEUR_NEUTRE), gbc);
        gbc.gridy = ++r;
        content.add(creerLigne(String.format("%.1f%%", temp), COULEUR_TEMP), gbc);
        gbc.gridy = ++r;
        content.add(creerLigne("Humidite", COULEUR_NEUTRE), gbc);
        gbc.gridy = ++r;
        content.add(creerLigne(String.format("%.1f%%", humid), COULEUR_HUMID), gbc);
        gbc.gridy = ++r;
        content.add(creerLigne("Pollution", COULEUR_NEUTRE), gbc);
        gbc.gridy = ++r;
        content.add(creerLigne(String.format("%.1f%%", poll), COULEUR_POLL), gbc);
        gbc.gridy = ++r;
        content.add(creerLigne("Purification", COULEUR_NEUTRE), gbc);
        gbc.gridy = ++r;
        content.add(creerLigne(String.format("%.1f%%", purif), COULEUR_PURIF), gbc);

        bloc.add(content, BorderLayout.CENTER);
        return bloc;
    }

    private JPanel creerBlocSante() {
        JPanel bloc = creerBlocSection("SANTE ECOSYSTEME", COULEUR_POSITIF);
        
        if (!(manageur instanceof ManageurBasique)) {
            JLabel lbl = new JLabel("Pas de donnees");
            lbl.setForeground(Color.WHITE);
            bloc.add(lbl, BorderLayout.CENTER);
            return bloc;
        }

        ManageurBasique mb = (ManageurBasique) manageur;
        ArrayList<StatRound> historique = mb.getHistorique();
        if (historique == null || historique.isEmpty()) {
            JLabel lbl = new JLabel("Aucune donnee");
            lbl.setForeground(Color.WHITE);
            bloc.add(lbl, BorderLayout.CENTER);
            return bloc;
        }

        StatRound premier = historique.get(0);
        StatRound dernier = historique.get(historique.size() - 1);
        
        int foret = obtenirValeur(dernier.compteBiomes, "Foret");
        int mer = obtenirValeur(dernier.compteBiomes, "Mer");
        
        int total = 0;
        for (String key : dernier.compteBiomes.keySet()) {
            total += obtenirValeur(dernier.compteBiomes, key);
        }
        
        double tauxSurvie = total > 0 ? ((foret + mer) * 100.0 / total) : 0;
        
        double pollDebut = premier.moyennes[2];
        double pollFin = dernier.moyennes[2];
        double evolPoll = pollFin - pollDebut;
        
        int typesRestants = 0;
        for (String key : dernier.compteBiomes.keySet()) {
            if (obtenirValeur(dernier.compteBiomes, key) > 0) {
                typesRestants++;
            }
        }

        JPanel content = new JPanel(new GridBagLayout());
        content.setBackground(COULEUR_FOND_SECTION);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(2, 10, 2, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;

        gbc.gridx = 0;
        gbc.gridy = 0;
        Color couleurSurvie = tauxSurvie > 30 ? COULEUR_POSITIF : (tauxSurvie > 15 ? COULEUR_TITRE : COULEUR_NEGATIF);
        content.add(creerLigne("Taux de survie", COULEUR_NEUTRE), gbc);
        gbc.gridy = 1;
        content.add(creerLigne(String.format("%.0f%%", tauxSurvie), couleurSurvie), gbc);
        gbc.gridy = 2;
        content.add(creerLigne("Biodiversite", COULEUR_NEUTRE), gbc);
        gbc.gridy = 3;
        content.add(creerLigne(typesRestants + " types", Color.WHITE), gbc);
        gbc.gridy = 4;
        Color couleurPoll = evolPoll > 0 ? COULEUR_NEGATIF : COULEUR_POSITIF;
        String signe = evolPoll > 0 ? "+" : "";
        content.add(creerLigne("Evolution pollution", COULEUR_NEUTRE), gbc);
        gbc.gridy = 5;
        content.add(creerLigne(signe + String.format("%.0f%%", evolPoll), couleurPoll), gbc);

        bloc.add(content, BorderLayout.CENTER);
        return bloc;
    }

    private JPanel creerBlocMoteur() {
        JPanel bloc = creerBlocSection("MOTEUR", new Color(100, 150, 200));
        
        if (!(manageur instanceof ManageurBasique)) {
            JLabel lbl = new JLabel("Pas de donnees");
            lbl.setForeground(Color.WHITE);
            bloc.add(lbl, BorderLayout.CENTER);
            return bloc;
        }

        ManageurBasique mb = (ManageurBasique) manageur;
        ArrayList<StatRound> historique = mb.getHistorique();
        if (historique == null || historique.isEmpty()) {
            JLabel lbl = new JLabel("Aucune donnee");
            lbl.setForeground(Color.WHITE);
            bloc.add(lbl, BorderLayout.CENTER);
            return bloc;
        }

        int totalTransfos = 0;
        int purification = 0;
        int pollution = 0;
        
        for (StatRound sr : historique) {
            totalTransfos += sr.nbTransformations;
            for (String key : sr.compteEvenements.keySet()) {
                if (key.equals("Purification")) {
                    purification += obtenirValeur(sr.compteEvenements, key);
                }
                if (key.equals("Pollution")) {
                    pollution += obtenirValeur(sr.compteEvenements, key);
                }
            }
        }
        
        int diferentiel = purification - pollution;

        JPanel content = new JPanel(new GridBagLayout());
        content.setBackground(COULEUR_FOND_SECTION);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(2, 8, 2, 8);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;

        gbc.gridx = 0;
        gbc.gridy = 0;
        content.add(creerLigne("Nombre de transformations", COULEUR_NEUTRE), gbc);
        gbc.gridy = 1;
        content.add(creerLigne("" + totalTransfos, Color.WHITE), gbc);
        gbc.gridy = 2;
        Color couleurDiff = diferentiel >= 0 ? COULEUR_POSITIF : COULEUR_NEGATIF;
        content.add(creerLigne("Purification - Pollution", COULEUR_NEUTRE), gbc);
        gbc.gridy = 3;
        String signe = diferentiel >= 0 ? "+" : "";
        String explanation = diferentiel >= 0 ? "La nature domine" : "L'urbanisation domine";
        content.add(creerLigne(signe + diferentiel + " (" + explanation + ")", couleurDiff), gbc);

        bloc.add(content, BorderLayout.CENTER);
        return bloc;
    }

    private JPanel creerBlocSection(String titre, Color couleurBande) {
        JPanel bloc = new JPanel(new BorderLayout());
        bloc.setBackground(COULEUR_FOND_SECTION);
        
        JPanel bande = new JPanel();
        bande.setPreferredSize(new Dimension(6, 0));
        bande.setBackground(couleurBande);
        bloc.add(bande, BorderLayout.WEST);
        
        JPanel haut = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 5));
        haut.setBackground(COULEUR_FOND_SECTION);
        JLabel lbl = new JLabel(titre);
        lbl.setForeground(couleurBande);
        lbl.setFont(new Font("Segoe UI", Font.BOLD, 14));
        haut.add(lbl);
        
        bloc.add(haut, BorderLayout.NORTH);
        bloc.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(couleurBande, 1),
            BorderFactory.createEmptyBorder(30, 10, 8, 10)
        ));
        
        return bloc;
    }

    private JPanel creerLigne(String texte, Color couleur) {
        JPanel panel = new JPanel();
        panel.setBackground(COULEUR_FOND_SECTION);
        
        JLabel lbl = new JLabel(texte);
        lbl.setForeground(couleur);
        lbl.setFont(new Font("Segoe UI", Font.BOLD, 12));
        panel.add(lbl);
        
        return panel;
    }
}
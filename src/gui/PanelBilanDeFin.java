package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.util.ArrayList;
import java.util.HashMap;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import moteur.processus.Manageur;
import moteur.processus.ManageurBasique;
import moteur.processus.ManageurBasique.StatRound;

public class PanelBilanDeFin extends JPanel {

    private static final Color COULEUR_FOND = new Color(40, 54, 24);
    private static final Color COULEUR_TITRE = new Color(255, 220, 140);
    private static final Color COULEUR_POSITIF = new Color(100, 200, 100);
    private static final Color COULEUR_NEGATIF = new Color(200, 100, 100);
    private static final Color COULEUR_NEUTRE = new Color(150, 150, 150);

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
        centre.setBorder(javax.swing.BorderFactory.createEmptyBorder(20, 20, 20, 20));

        if (displayer != null) {
            centre.add(displayer, BorderLayout.CENTER);
        }

        JPanel resumePanel = creerResumePanel();
        centre.add(resumePanel, BorderLayout.EAST);

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

    private boolean contientCle(HashMap<String, Integer> map, String cle) {
        return map.get(cle) != null;
    }

    private JPanel creerResumePanel() {
        JPanel panel = new JPanel();
        panel.setBackground(new Color(52, 78, 65));
        panel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 12, 8, 12);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;

        int row = 0;

        if (!(manageur instanceof ManageurBasique)) {
            JLabel lbl = new JLabel("Pas de donnees disponibles");
            lbl.setForeground(Color.WHITE);
            panel.add(lbl, gbc);
            return panel;
        }

        ManageurBasique mb = (ManageurBasique) manageur;
        ArrayList<StatRound> historique = mb.getHistorique();

        if (historique == null || historique.isEmpty()) {
            JLabel lbl = new JLabel("Aucune donnee disponible");
            lbl.setForeground(Color.WHITE);
            panel.add(lbl, gbc);
            return panel;
        }

        int taille = historique.size();
        StatRound premier = historique.get(0);
        StatRound dernier = historique.get(taille - 1);
        int duree = dernier.round;

        JLabel lblDuree = creerTitre("Duree de la Partie: " + duree + " rounds");
        gbc.gridx = 0;
        gbc.gridy = row;
        gbc.gridwidth = 2;
        panel.add(lblDuree, gbc);
        row++;

        gbc.gridwidth = 1;
        JLabel lblBiomes = creerTitre("Biomes");
        gbc.gridy = row;
        panel.add(lblBiomes, gbc);
        row++;

        HashMap<String, Integer> biomes = dernier.compteBiomes;
        String biomePlus = "";
        String biomeMoins = "";
        int maxVal = Integer.MIN_VALUE;
        int minVal = Integer.MAX_VALUE;

        for (String key : biomes.keySet()) {
            int val = obtenirValeur(biomes, key);
            if (val > maxVal) {
                maxVal = val;
                biomePlus = key;
            }
            if (val < minVal) {
                minVal = val;
                biomeMoins = key;
            }
        }

        gbc.gridx = 0;
        gbc.gridy = row;
        panel.add(creerLabel("Plus present:", biomePlus, COULEUR_POSITIF), gbc);
        gbc.gridx = 1;
        panel.add(creerLabel("Moins present:", biomeMoins, COULEUR_NEGATIF), gbc);
        row++;

        gbc.gridx = 0;
        gbc.gridy = row;
        panel.add(creerLabel("Effectif:", String.valueOf(maxVal), COULEUR_NEUTRE), gbc);
        gbc.gridx = 1;
        panel.add(creerLabel("Effectif:", String.valueOf(minVal), COULEUR_NEUTRE), gbc);
        row++;

        gbc.gridx = 0;
        JLabel lblEvents = creerTitre("Evenements");
        gbc.gridy = row;
        gbc.gridwidth = 2;
        panel.add(lblEvents, gbc);
        row++;

        HashMap<String, Integer> events = dernier.compteEvenements;
        String eventPlus = "";
        String eventMoins = "";
        int maxEvent = Integer.MIN_VALUE;
        int minEvent = Integer.MAX_VALUE;

        for (String key : events.keySet()) {
            int val = obtenirValeur(events, key);
            if (val > maxEvent) {
                maxEvent = val;
                eventPlus = key;
            }
            if (val < minEvent) {
                minEvent = val;
                eventMoins = key;
            }
        }

        gbc.gridwidth = 1;
        gbc.gridx = 0;
        gbc.gridy = row;
        panel.add(creerLabel("Plus frequent:", eventPlus, COULEUR_POSITIF), gbc);
        gbc.gridx = 1;
        panel.add(creerLabel("Moins frequent:", eventMoins, COULEUR_NEGATIF), gbc);
        row++;

        gbc.gridx = 0;
        gbc.gridy = row;
        panel.add(creerLabel("Total:", String.valueOf(maxEvent), COULEUR_NEUTRE), gbc);
        gbc.gridx = 1;
        panel.add(creerLabel("Total:", String.valueOf(minEvent), COULEUR_NEUTRE), gbc);
        row++;

        gbc.gridwidth = 2;
        JLabel lblMetriques = creerTitre("Metriques Globales");
        gbc.gridx = 0;
        gbc.gridy = row;
        panel.add(lblMetriques, gbc);
        row++;

        double tempMoy = dernier.moyennes[0];
        double humidMoy = dernier.moyennes[1];
        double pollMoy = dernier.moyennes[2];
        double purifMoy = dernier.moyennes[3];

        gbc.gridwidth = 1;
        gbc.gridx = 0;
        gbc.gridy = row;
        panel.add(creerLabel("Temperature:", String.format("%.1f%%", tempMoy), COULEUR_TITRE), gbc);
        gbc.gridx = 1;
        panel.add(creerLabel("Humidite:", String.format("%.1f%%", humidMoy), COULEUR_TITRE), gbc);
        row++;

        gbc.gridx = 0;
        gbc.gridy = row;
        panel.add(creerLabel("Pollution:", String.format("%.1f%%", pollMoy), COULEUR_TITRE), gbc);
        gbc.gridx = 1;
        panel.add(creerLabel("Purification:", String.format("%.1f%%", purifMoy), COULEUR_TITRE), gbc);
        row++;

        gbc.gridwidth = 2;
        JLabel lblSante = creerTitre("Sante de l'Ecosysteme");
        gbc.gridx = 0;
        gbc.gridy = row;
        panel.add(lblSante, gbc);
        row++;

        int foret = obtenirValeur(biomes, "Foret");
        int mer = obtenirValeur(biomes, "Mer");
        
        int totalBiomes = 0;
        for (String key : biomes.keySet()) {
            totalBiomes += obtenirValeur(biomes, key);
        }
        
        double tauxSurvie = totalBiomes > 0 ? ((double)(foret + mer) / totalBiomes * 100) : 0;

        double pollDebut = premier.moyennes[2];
        double pollFin = dernier.moyennes[2];
        double evolutionPoll = pollFin - pollDebut;

        int typesBiomesRestants = 0;
        for (String key : biomes.keySet()) {
            if (obtenirValeur(biomes, key) > 0) {
                typesBiomesRestants++;
            }
        }

        gbc.gridwidth = 1;
        gbc.gridx = 0;
        gbc.gridy = row;
        panel.add(creerLabel("Taux survie:", String.format("%.1f%%", tauxSurvie), COULEUR_NEUTRE), gbc);
        gbc.gridx = 1;
        panel.add(creerLabel("Biodiversite:", typesBiomesRestants + " types", COULEUR_NEUTRE), gbc);
        row++;

        gbc.gridwidth = 2;
        gbc.gridx = 0;
        gbc.gridy = row;
        Color couleurEvol = evolutionPoll > 0 ? COULEUR_NEGATIF : COULEUR_POSITIF;
        String signe = evolutionPoll > 0 ? "+" : "";
        panel.add(creerLabel("Evolution pollution:", signe + String.format("%.1f%%", evolutionPoll), couleurEvol), gbc);
        row++;

        gbc.gridwidth = 2;
        JLabel lblPerf = creerTitre("Performance du Moteur");
        gbc.gridy = row;
        panel.add(lblPerf, gbc);
        row++;

        int totalTransfos = 0;
        int totalEvents = 0;
        
        for (int i = 0; i < taille; i++) {
            StatRound sr = historique.get(i);
            totalTransfos += sr.nbTransformations;
            
            HashMap<String, Integer> evtMap = sr.compteEvenements;
            for (String key : evtMap.keySet()) {
                totalEvents += obtenirValeur(evtMap, key);
            }
        }

        int purificationNaturelle = 0;
        int pollutionUrbaine = 0;
        
        for (int i = 0; i < taille; i++) {
            StatRound sr = historique.get(i);
            if (contientCle(sr.compteEvenements, "Purification")) {
                purificationNaturelle += obtenirValeur(sr.compteEvenements, "Purification");
            }
            if (contientCle(sr.compteEvenements, "Pollution")) {
                pollutionUrbaine += obtenirValeur(sr.compteEvenements, "Pollution");
            }
        }
        int diferencial = purificationNaturelle - pollutionUrbaine;

        gbc.gridwidth = 1;
        gbc.gridx = 0;
        gbc.gridy = row;
        panel.add(creerLabel("Transformations:", String.valueOf(totalTransfos), COULEUR_NEUTRE), gbc);
        gbc.gridx = 1;
        panel.add(creerLabel("Evenements:", String.valueOf(totalEvents), COULEUR_NEUTRE), gbc);
        row++;

        gbc.gridwidth = 2;
        gbc.gridx = 0;
        gbc.gridy = row;
        Color couleurDiff = diferencial >= 0 ? COULEUR_POSITIF : COULEUR_NEGATIF;
        panel.add(creerLabel("Differentiel:", (diferencial >= 0 ? "+" : "") + diferencial, couleurDiff), gbc);
        row++;

        return panel;
    }

    private JLabel creerTitre(String texte) {
        JLabel label = new JLabel(texte);
        label.setForeground(COULEUR_TITRE);
        label.setFont(new Font("Segoe UI", Font.BOLD, 16));
        return label;
    }

    private JLabel creerLabel(String nom, String valeur, Color couleur) {
        JLabel label = new JLabel(nom + " " + valeur);
        label.setForeground(couleur);
        label.setFont(new Font("Segoe UI", Font.BOLD, 13));
        return label;
    }
}
package gui;

import config.ConfigurationCreationEvenement;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import moteur.donne.biome.Biome;
import moteur.processus.Manageur;
import moteur.processus.usine.BiomeFactory.TypeBiome;

public class PanelEdition extends JPanel implements ChangeListener {
    
    private static final Color COULEUR_ACTION = new Color(60, 140, 60); 
    private static final Color COULEUR_SELECT = Color.RED;

    private static final Color COULEUR_FORET = new Color(34, 120, 34);
    private static final Color COULEUR_DESERT = new Color(180, 140, 80);
    private static final Color COULEUR_MER = new Color(30, 100, 180);
    private static final Color COULEUR_VILLE = new Color(90, 90, 100);
    private static final Color COULEUR_VILLAGE = new Color(139, 100, 60);
    private static final Color COULEUR_BANQUISE = new Color(150, 200, 220);
    private static final Color COULEUR_MONTAGNE = new Color(120, 120, 120);
    private static final Color COULEUR_TITRE = new Color(255, 220, 140); 

    private JButton btnFin;
    private JButton btnReinitialiser;
    private JButton btnZero;
    private JButton btnGenererCarte;
    private JPanel panelSelecteurBiomes;
    private TypeBiome biomeTypeSelectionne = null;
    private StrategiePeinture strategiePeinture = new StrategiePeinture();
    private Runnable actionGenererCarte;
    private MainDisplayer displayer;
    private JPanel vueEdition;
    
    private JSlider sliderPluie, sliderOrage, sliderTonnerre;
    private JSlider sliderVentChaud, sliderTornade, sliderZephyr;
    private JSlider sliderVentFroid, sliderGrele;
    private JSlider sliderPollutionVille, sliderSmog, sliderNuageToxique;
    private JSlider sliderPollutionVillage;
    private JSlider sliderPurification, sliderPluieBenite;
    
    private JLabel lblPluie, lblOrage, lblTonnerre;
    private JLabel lblVentChaud, lblTornade, lblZephyr;
    private JLabel lblVentFroid, lblGrele;
    private JLabel lblPollutionVille, lblSmog, lblNuageToxique;
    private JLabel lblPollutionVillage;
    private JLabel lblPurification, lblPluieBenite;
    
    private JPanel panelConfig;

    private Manageur manageur;
    
    public PanelEdition(Runnable actionFin, Runnable actionGenererCarte, Runnable actionAide) {
        this.actionGenererCarte = actionGenererCarte;
        
        this.setBackground(new Color(40, 54, 24));
        this.setPreferredSize(new Dimension(0, 80));
        this.setLayout(new BorderLayout());

        JPanel ligneCommandes = new JPanel(new BorderLayout());
        ligneCommandes.setOpaque(false);

        Dimension zoneAide = new Dimension(85, 50);

        JPanel panelAide = new JPanel(new FlowLayout(FlowLayout.LEFT, 18, 18));
        panelAide.setOpaque(false);
        panelAide.setPreferredSize(zoneAide);

        JButton btnAide = deseign.creerBeauBouton("?", new Color(90, 120, 190));
        btnAide.setPreferredSize(new Dimension(55, 45));
        if (actionAide != null) {
            btnAide.addActionListener(e -> actionAide.run());
        }
        panelAide.add(btnAide);

        JPanel panelActionsCentrees = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 15));
        panelActionsCentrees.setOpaque(false);

        JPanel compensateurDroite = new JPanel();
        compensateurDroite.setOpaque(false);
        compensateurDroite.setPreferredSize(zoneAide);

        ligneCommandes.add(panelAide, BorderLayout.WEST);
        ligneCommandes.add(panelActionsCentrees, BorderLayout.CENTER);
        ligneCommandes.add(compensateurDroite, BorderLayout.EAST);
        this.add(ligneCommandes, BorderLayout.CENTER);

        btnGenererCarte = deseign.creerBeauBouton("Générer Carte", new Color(100, 100, 180));
        if (actionGenererCarte != null) {
            btnGenererCarte.addActionListener(e -> actionGenererCarte.run());
            panelActionsCentrees.add(btnGenererCarte);
        } else {
            btnGenererCarte.setEnabled(false);
            panelActionsCentrees.add(btnGenererCarte);
        }

        btnFin = deseign.creerBeauBouton("Lancer", COULEUR_ACTION);
        btnFin.addActionListener(e -> {
            sauvegarder();
            actionFin.run();
        });
        panelActionsCentrees.add(btnFin);

        btnReinitialiser = deseign.creerBeauBouton("Réinitialiser", COULEUR_ACTION);
        btnReinitialiser.addActionListener(e -> reinitialiser());
        panelActionsCentrees.add(btnReinitialiser);

        btnZero = deseign.creerBeauBouton("Mettre à zéro", new Color(180, 60, 60));
        btnZero.addActionListener(e -> mettreAZero());
        panelActionsCentrees.add(btnZero);

        panelSelecteurBiomes = creerPanelSelecteurBiomes();
        panelActionsCentrees.add(panelSelecteurBiomes);

        panelConfig = creerPanneauConfig();
    }
    
    private JPanel creerPanelSelecteurBiomes() {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 5));
        panel.setBackground(new Color(40, 54, 24));
        panel.setPreferredSize(new Dimension(400, 50));
        
        TypeBiome[] types = {TypeBiome.FORET, TypeBiome.DESERT, TypeBiome.MER, 
                           TypeBiome.VILLE, TypeBiome.VILLAGE, TypeBiome.MONTAGNE};
        Color[] couleurs = {COULEUR_FORET, COULEUR_DESERT, COULEUR_MER, 
                          COULEUR_VILLE, COULEUR_VILLAGE, COULEUR_MONTAGNE};
        
        for (int i = 0; i < types.length; i++) {
            final TypeBiome type = types[i];
            Color couleur = couleurs[i];
            
            JButton btn = new JButton() {
                private java.awt.Image img = null;
                {
                    img = strategiePeinture.creerIconeBiome(type, 38);
                }
                @Override
                protected void paintComponent(java.awt.Graphics g) {
                    int w = getWidth();
                    int h = getHeight();
                    int size = Math.min(w, h) + 4;
                    int ox = (w - size) / 2;
                    int oy = (h - size) / 2;
                    
                    java.awt.Graphics2D g2 = (java.awt.Graphics2D) g;
                    g2.setRenderingHint(java.awt.RenderingHints.KEY_ANTIALIASING, java.awt.RenderingHints.VALUE_ANTIALIAS_ON);
                    
                    java.awt.Shape circle = new java.awt.geom.Ellipse2D.Double(ox, oy, size, size);
                    g2.setClip(circle);
                    
                    g2.setColor(couleur);
                    g2.fill(circle);
                    if (img != null) {
                        g2.drawImage(img, ox, oy, size, size, null);
                    }
                    
                    g2.setClip(null);
                    if (biomeTypeSelectionne == type) {
                        g2.setColor(COULEUR_SELECT);
                        g2.setStroke(new java.awt.BasicStroke(3));
                        g2.drawOval(ox + 1, oy + 1, size - 2, size - 2);
                    } else {
                        g2.setColor(new Color(0, 0, 0, 80));
                        g2.setStroke(new java.awt.BasicStroke(1));
                        g2.drawOval(ox, oy, size, size);
                    }
                }
            };
            btn.setPreferredSize(new Dimension(40, 40));
            btn.setContentAreaFilled(false);
            btn.setBorderPainted(false);
            btn.setFocusPainted(false);
            btn.setToolTipText(type.name());
            btn.addActionListener(e -> {
                biomeTypeSelectionne = type;
                if (displayer != null) {
                    displayer.setModePeinture(true, type);
                }
                panelSelecteurBiomes.repaint();
            });
            panel.add(btn);
        }
        
        return panel;
    }
    
    public void setBiomeSelectionne(Biome biome) { 
    }
    
    public void setDisplayer(MainDisplayer displayer) {
        this.displayer = displayer;
        if (displayer != null && vueEdition != null) {
            vueEdition.add(displayer, BorderLayout.CENTER);
            javax.swing.SwingUtilities.invokeLater(() -> {
                vueEdition.revalidate();
                vueEdition.repaint();
            });
        }
    }
    
    public void setVueEdition(JPanel vueEdition) {
        this.vueEdition = vueEdition;
        if (displayer != null && vueEdition != null) {
            vueEdition.add(displayer, BorderLayout.CENTER);
        }
    }
    
    public void setManageur(Manageur manageur) {
        this.manageur = manageur;
        if (displayer != null) {
            displayer.setManageurPourModification(manageur);
        }
    }
    
    private JPanel creerPanneauConfig() {
        JPanel panel = new JPanel();
        panel.setBackground(new Color(52, 78, 65));
        panel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(2, 8, 2, 8);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;
        
        int row = 0;
        
        JLabel titre = creerSousTitre("CONFIGURATION DES ÉVÉNEMENTS", COULEUR_TITRE);
        gbc.gridx = 0;
        gbc.gridy = row;
        gbc.gridwidth = 3;
        panel.add(titre, gbc);
        row++;
        
        gbc.gridwidth = 1;

        // === FORET ===
        row = ajouterSection(panel, row, "Événements des Forêts", COULEUR_FORET);
        
        sliderPurification = creerSlider(ConfigurationCreationEvenement.PROBABILITE_PURIFICATION_PAR_FORET, COULEUR_FORET);
        lblPurification = creerLabelValeur(ConfigurationCreationEvenement.PROBABILITE_PURIFICATION_PAR_FORET);
        row++;
        addRow(panel, "  Purification:", sliderPurification, lblPurification, gbc, row);
        row++;
        
        sliderPluieBenite = creerSlider(ConfigurationCreationEvenement.PROBABILITE_PLUIEBENITE_PAR_FORET, COULEUR_FORET);
        lblPluieBenite = creerLabelValeur(ConfigurationCreationEvenement.PROBABILITE_PLUIEBENITE_PAR_FORET);
        row++;
        addRow(panel, "  Pluie Bénite:", sliderPluieBenite, lblPluieBenite, gbc, row);
        row++;
        
        // === DESERT ===
        row++;
        row = ajouterSection(panel, row, "Événements du Désert", COULEUR_DESERT);
        
        sliderVentChaud = creerSlider(ConfigurationCreationEvenement.PROBABILITE_VENT_CHAUD_PAR_DESERT, COULEUR_DESERT);
        lblVentChaud = creerLabelValeur(ConfigurationCreationEvenement.PROBABILITE_VENT_CHAUD_PAR_DESERT);
        row++;
        addRow(panel, "  Vent Chaud:", sliderVentChaud, lblVentChaud, gbc, row);
        row++;
        
        sliderTornade = creerSlider(ConfigurationCreationEvenement.PROBABILITE_TORNADE_PAR_DESERT, COULEUR_DESERT);
        lblTornade = creerLabelValeur(ConfigurationCreationEvenement.PROBABILITE_TORNADE_PAR_DESERT);
        row++;
        addRow(panel, "  Tornade:", sliderTornade, lblTornade, gbc, row);
        row++;
        
        sliderZephyr = creerSlider(ConfigurationCreationEvenement.PROBABILITE_ZEPHYR_PAR_DESERT, COULEUR_DESERT);
        lblZephyr = creerLabelValeur(ConfigurationCreationEvenement.PROBABILITE_ZEPHYR_PAR_DESERT);
        row++;
        addRow(panel, "  Zéphyr:", sliderZephyr, lblZephyr, gbc, row);
        row++;

        // === MER ===
        row++;
        row = ajouterSection(panel, row, "Événements de la Mer", COULEUR_MER);
        
        sliderPluie = creerSlider(ConfigurationCreationEvenement.PROBABILITE_PLUVIE_PAR_MER, COULEUR_MER);
        lblPluie = creerLabelValeur(ConfigurationCreationEvenement.PROBABILITE_PLUVIE_PAR_MER);
        row++;
        addRow(panel, "  Pluie:", sliderPluie, lblPluie, gbc, row);
        row++;
        
        sliderOrage = creerSlider(ConfigurationCreationEvenement.PROBABILITE_ORAGE_PAR_MER, COULEUR_MER);
        lblOrage = creerLabelValeur(ConfigurationCreationEvenement.PROBABILITE_ORAGE_PAR_MER);
        row++;
        addRow(panel, "  Orage:", sliderOrage, lblOrage, gbc, row);
        row++;
        
        sliderTonnerre = creerSlider(ConfigurationCreationEvenement.PROBABILITE_TONNERRE_PAR_MER, COULEUR_MER);
        lblTonnerre = creerLabelValeur(ConfigurationCreationEvenement.PROBABILITE_TONNERRE_PAR_MER);
        row++;
        addRow(panel, "  Tonnerre:", sliderTonnerre, lblTonnerre, gbc, row);
        row++;

        // === VILLE ===
        row++;
        row = ajouterSection(panel, row, "Événements des Villes", COULEUR_VILLE);
        
        sliderPollutionVille = creerSlider(ConfigurationCreationEvenement.PROBABILITE_POLLUTION_PAR_VILLE, COULEUR_VILLE);
        lblPollutionVille = creerLabelValeur(ConfigurationCreationEvenement.PROBABILITE_POLLUTION_PAR_VILLE);
        row++;
        addRow(panel, "  Pollution:", sliderPollutionVille, lblPollutionVille, gbc, row);
        row++;
        
        sliderSmog = creerSlider(ConfigurationCreationEvenement.PROBABILITE_SMOG_PAR_VILLE, COULEUR_VILLE);
        lblSmog = creerLabelValeur(ConfigurationCreationEvenement.PROBABILITE_SMOG_PAR_VILLE);
        row++;
        addRow(panel, "  Smog:", sliderSmog, lblSmog, gbc, row);
        row++;
        
        sliderNuageToxique = creerSlider(ConfigurationCreationEvenement.PROBABILITE_NUAGETOXIQUE_PAR_VILLE, COULEUR_VILLE);
        lblNuageToxique = creerLabelValeur(ConfigurationCreationEvenement.PROBABILITE_NUAGETOXIQUE_PAR_VILLE);
        row++;
        addRow(panel, "  Nuage Toxique:", sliderNuageToxique, lblNuageToxique, gbc, row);
        row++;

        // === VILLAGE ===
        row++;
        row = ajouterSection(panel, row, "Événements des Villages", COULEUR_VILLAGE);
        
        sliderPollutionVillage = creerSlider(ConfigurationCreationEvenement.PROBABILITE_POLLUTION_PAR_VILLAGE, COULEUR_VILLAGE);
        lblPollutionVillage = creerLabelValeur(ConfigurationCreationEvenement.PROBABILITE_POLLUTION_PAR_VILLAGE);
        row++;
        addRow(panel, "  Pollution:", sliderPollutionVillage, lblPollutionVillage, gbc, row);
        row++;

        // === BANQUISE ===
        row++;
        row = ajouterSection(panel, row, "Événements de la Banquise", COULEUR_BANQUISE);
        
        sliderVentFroid = creerSlider(ConfigurationCreationEvenement.PROBABILITE_VENT_FROID_PAR_BANQUISE, COULEUR_BANQUISE);
        lblVentFroid = creerLabelValeur(ConfigurationCreationEvenement.PROBABILITE_VENT_FROID_PAR_BANQUISE);
        row++;
        addRow(panel, "  Vent Froid:", sliderVentFroid, lblVentFroid, gbc, row);
        row++;
        
        sliderGrele = creerSlider(ConfigurationCreationEvenement.PROBABILITE_GRELE_PAR_BANQUISE, COULEUR_BANQUISE);
        lblGrele = creerLabelValeur(ConfigurationCreationEvenement.PROBABILITE_GRELE_PAR_BANQUISE);
        row++;
        addRow(panel, "  Grêle:", sliderGrele, lblGrele, gbc, row);
        row++;
        
        return panel;
    }
    
    private JSlider creerSlider(double valeur, Color couleur) {
        JSlider slider = deseign.creerBeauSlider((int)(valeur * 1000), 0, 50, couleur);
        slider.addChangeListener(this);
        return slider;
    }
    
    private JLabel creerSousTitre(String texte, Color couleur) {
        JLabel label = new JLabel(texte);
        label.setForeground(couleur);
        label.setFont(new java.awt.Font("Segoe UI", java.awt.Font.BOLD, 14));
        label.setBorder(javax.swing.BorderFactory.createEmptyBorder(8, 8, 8, 8));
        return label;
    }
    
    private int ajouterSection(JPanel panel, int row, String titre, Color couleur) {
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 8, 4, 8);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;
        
        JPanel sectionPanel = new JPanel(new BorderLayout());
        sectionPanel.setOpaque(true);
        sectionPanel.setBackground(new Color(couleur.getRed(), couleur.getGreen(), couleur.getBlue(), 70));
        
        JLabel sectionTitre = new JLabel(titre);
        sectionTitre.setForeground(Color.WHITE);
        sectionTitre.setFont(new java.awt.Font("Segoe UI", java.awt.Font.BOLD, 13));
        sectionTitre.setBorder(javax.swing.BorderFactory.createEmptyBorder(6, 10, 6, 8));
        
        sectionPanel.add(sectionTitre, BorderLayout.CENTER);
        
        JPanel barreCouleurs = new JPanel();
        barreCouleurs.setPreferredSize(new Dimension(8, 0));
        barreCouleurs.setOpaque(true);
        barreCouleurs.setBackground(couleur);
        sectionPanel.add(barreCouleurs, BorderLayout.WEST);
        
        gbc.gridx = 0;
        gbc.gridy = row;
        gbc.gridwidth = 3;
        panel.add(sectionPanel, gbc);
        
        return row + 1;
    }
    
    private JLabel creerLabelValeur(double valeur) {
        String texte = String.format("%.1f%%", valeur * 100);
        JLabel label = new JLabel(texte);
        label.setForeground(Color.WHITE);
        label.setPreferredSize(new Dimension(45, 20));
        label.setHorizontalAlignment(JLabel.RIGHT);
        return label;
    }
    
    private void addRow(JPanel panel, String nom, JSlider slider, JLabel label, GridBagConstraints gbc, int row) {
        if (panel == null || slider == null || label == null) return;
        JLabel lblNom = new JLabel(nom);
        lblNom.setForeground(Color.WHITE);
        gbc.gridx = 0;
        gbc.gridy = row;
        gbc.gridwidth = 2;
        panel.add(lblNom, gbc);
        
        gbc.gridx = 2;
        gbc.gridwidth = 1;
        panel.add(label, gbc);
        
        gbc.gridx = 0;
        gbc.gridy = row + 1;
        gbc.gridwidth = 3;
        panel.add(slider, gbc);
    }
    
    private void majLabels() {
        lblPluie.setText(String.format("%.1f%%", sliderPluie.getValue() / 10.0));
        lblOrage.setText(String.format("%.1f%%", sliderOrage.getValue() / 10.0));
        lblTonnerre.setText(String.format("%.1f%%", sliderTonnerre.getValue() / 10.0));
        lblVentChaud.setText(String.format("%.1f%%", sliderVentChaud.getValue() / 10.0));
        lblTornade.setText(String.format("%.1f%%", sliderTornade.getValue() / 10.0));
        lblZephyr.setText(String.format("%.1f%%", sliderZephyr.getValue() / 10.0));
        lblVentFroid.setText(String.format("%.1f%%", sliderVentFroid.getValue() / 10.0));
        lblGrele.setText(String.format("%.1f%%", sliderGrele.getValue() / 10.0));
        lblPollutionVille.setText(String.format("%.1f%%", sliderPollutionVille.getValue() / 10.0));
        lblSmog.setText(String.format("%.1f%%", sliderSmog.getValue() / 10.0));
        lblNuageToxique.setText(String.format("%.1f%%", sliderNuageToxique.getValue() / 10.0));
        lblPollutionVillage.setText(String.format("%.1f%%", sliderPollutionVillage.getValue() / 10.0));
        lblPurification.setText(String.format("%.1f%%", sliderPurification.getValue() / 10.0));
        lblPluieBenite.setText(String.format("%.1f%%", sliderPluieBenite.getValue() / 10.0));
    }
    
    @Override
    public void stateChanged(ChangeEvent e) {
        majLabels();
        if (e.getSource() instanceof JSlider) {
            JSlider slider = (JSlider) e.getSource();
            slider.repaint();
            panelConfig.repaint();
        }
    }
    
    public void sauvegarder() {
        ConfigurationCreationEvenement.PROBABILITE_PLUVIE_PAR_MER = sliderPluie.getValue() / 1000.0;
        ConfigurationCreationEvenement.PROBABILITE_ORAGE_PAR_MER = sliderOrage.getValue() / 1000.0;
        ConfigurationCreationEvenement.PROBABILITE_TONNERRE_PAR_MER = sliderTonnerre.getValue() / 1000.0;
        ConfigurationCreationEvenement.PROBABILITE_VENT_CHAUD_PAR_DESERT = sliderVentChaud.getValue() / 1000.0;
        ConfigurationCreationEvenement.PROBABILITE_TORNADE_PAR_DESERT = sliderTornade.getValue() / 1000.0;
        ConfigurationCreationEvenement.PROBABILITE_ZEPHYR_PAR_DESERT = sliderZephyr.getValue() / 1000.0;
        ConfigurationCreationEvenement.PROBABILITE_VENT_FROID_PAR_BANQUISE = sliderVentFroid.getValue() / 1000.0;
        ConfigurationCreationEvenement.PROBABILITE_GRELE_PAR_BANQUISE = sliderGrele.getValue() / 1000.0;
        ConfigurationCreationEvenement.PROBABILITE_POLLUTION_PAR_VILLE = sliderPollutionVille.getValue() / 1000.0;
        ConfigurationCreationEvenement.PROBABILITE_SMOG_PAR_VILLE = sliderSmog.getValue() / 1000.0;
        ConfigurationCreationEvenement.PROBABILITE_NUAGETOXIQUE_PAR_VILLE = sliderNuageToxique.getValue() / 1000.0;
        ConfigurationCreationEvenement.PROBABILITE_POLLUTION_PAR_VILLAGE = sliderPollutionVillage.getValue() / 1000.0;
        ConfigurationCreationEvenement.PROBABILITE_PURIFICATION_PAR_FORET = sliderPurification.getValue() / 1000.0;
        ConfigurationCreationEvenement.PROBABILITE_PLUIEBENITE_PAR_FORET = sliderPluieBenite.getValue() / 1000.0;
    }
    
    public void reinitialiser() {
        ConfigurationCreationEvenement.reinitialiser();
        
        sliderPluie.setValue((int)(ConfigurationCreationEvenement.PROBABILITE_PLUVIE_PAR_MER * 1000));
        sliderOrage.setValue((int)(ConfigurationCreationEvenement.PROBABILITE_ORAGE_PAR_MER * 1000));
        sliderTonnerre.setValue((int)(ConfigurationCreationEvenement.PROBABILITE_TONNERRE_PAR_MER * 1000));
        sliderVentChaud.setValue((int)(ConfigurationCreationEvenement.PROBABILITE_VENT_CHAUD_PAR_DESERT * 1000));
        sliderTornade.setValue((int)(ConfigurationCreationEvenement.PROBABILITE_TORNADE_PAR_DESERT * 1000));
        sliderZephyr.setValue((int)(ConfigurationCreationEvenement.PROBABILITE_ZEPHYR_PAR_DESERT * 1000));
        sliderVentFroid.setValue((int)(ConfigurationCreationEvenement.PROBABILITE_VENT_FROID_PAR_BANQUISE * 1000));
        sliderGrele.setValue((int)(ConfigurationCreationEvenement.PROBABILITE_GRELE_PAR_BANQUISE * 1000));
        sliderPollutionVille.setValue((int)(ConfigurationCreationEvenement.PROBABILITE_POLLUTION_PAR_VILLE * 1000));
        sliderSmog.setValue((int)(ConfigurationCreationEvenement.PROBABILITE_SMOG_PAR_VILLE * 1000));
        sliderNuageToxique.setValue((int)(ConfigurationCreationEvenement.PROBABILITE_NUAGETOXIQUE_PAR_VILLE * 1000));
        sliderPollutionVillage.setValue((int)(ConfigurationCreationEvenement.PROBABILITE_POLLUTION_PAR_VILLAGE * 1000));
        sliderPurification.setValue((int)(ConfigurationCreationEvenement.PROBABILITE_PURIFICATION_PAR_FORET * 1000));
        sliderPluieBenite.setValue((int)(ConfigurationCreationEvenement.PROBABILITE_PLUIEBENITE_PAR_FORET * 1000));
        
        majLabels();
    }

    public void mettreAZero() {
        sliderPluie.setValue(0);
        sliderOrage.setValue(0);
        sliderTonnerre.setValue(0);
        sliderVentChaud.setValue(0);
        sliderTornade.setValue(0);
        sliderZephyr.setValue(0);
        sliderVentFroid.setValue(0);
        sliderGrele.setValue(0);
        sliderPollutionVille.setValue(0);
        sliderSmog.setValue(0);
        sliderNuageToxique.setValue(0);
        sliderPollutionVillage.setValue(0);
        sliderPurification.setValue(0);
        sliderPluieBenite.setValue(0);
        
        majLabels();
    }
    
    public JPanel getPanelConfig() {
        return panelConfig;
    }
}
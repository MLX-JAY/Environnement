package gui;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JSlider;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.Dimension;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.BorderLayout;

import config.ConfigurationCreationEvenement;
import moteur.donne.biome.Biome;
import moteur.donne.carte.Bloc;
import moteur.processus.Manageur;
import moteur.processus.ManageurBasique;
import moteur.processus.usine.BiomeFactory;
import moteur.processus.usine.BiomeFactory.TypeBiome;

public class PanelEdition extends JPanel implements ChangeListener {
    
    private static final Color COULEUR_ACTION = new Color(60, 140, 60); 
    private static final Color COULEUR_MODIFIER = new Color(180, 120, 60); 

    private static final Color COULEUR_FORET = new Color(34, 120, 34);
    private static final Color COULEUR_DESERT = new Color(180, 140, 80);
    private static final Color COULEUR_MER = new Color(30, 100, 180);
    private static final Color COULEUR_VILLE = new Color(90, 90, 100);
    private static final Color COULEUR_VILLAGE = new Color(139, 100, 60);
    private static final Color COULEUR_BANQUISE = new Color(150, 200, 220);
    private static final Color COULEUR_TITRE = new Color(255, 220, 140); 

    private JButton btnFin;
    private JButton btnReinitialiser;
    private JButton btnZero;
    private JButton btnGenererCarte;
    private JButton btnModifier;
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
    
    public PanelEdition(Runnable actionFin, Runnable actionGenererCarte) {
        this.actionGenererCarte = actionGenererCarte;
        
        this.setBackground(new Color(40, 54, 24));
        this.setPreferredSize(new Dimension(0, 80));
        this.setLayout(new FlowLayout(FlowLayout.CENTER, 20, 15));

        btnGenererCarte = deseign.creerBeauBouton("Générer Carte", new Color(100, 100, 180));
        if (actionGenererCarte != null) {
            btnGenererCarte.addActionListener(e -> actionGenererCarte.run());
            this.add(btnGenererCarte);
        } else {
            btnGenererCarte.setEnabled(false);
            this.add(btnGenererCarte);
        }

        btnFin = deseign.creerBeauBouton("Lancer", COULEUR_ACTION);
        btnFin.addActionListener(e -> {
            sauvegarder();
            actionFin.run();
        });
        this.add(btnFin);

        btnReinitialiser = deseign.creerBeauBouton("Réinitialiser", COULEUR_ACTION);
        btnReinitialiser.addActionListener(e -> reinitialiser());
        this.add(btnReinitialiser);

        btnZero = deseign.creerBeauBouton("Mettre à zéro", new Color(180, 60, 60));
        btnZero.addActionListener(e -> mettreAZero());
        this.add(btnZero);

        btnModifier = deseign.creerBeauBouton("Modifier", COULEUR_MODIFIER);
        btnModifier.addActionListener(e -> afficherPopupModifier());
        btnModifier.setEnabled(false);
        this.add(btnModifier);

        panelConfig = creerPanneauConfig();
    }
    
    public void setBiomeSelectionne(Biome biome) {
        btnModifier.setEnabled(biome != null);
    }
    
    private void afficherPopupModifier() {
        if (displayer == null || displayer.getBiomeSelectionne() == null) return;
        if (manageur == null || !(manageur instanceof ManageurBasique)) return;
        
        Biome ancienBiome = displayer.getBiomeSelectionne();
        Bloc position = ancienBiome.getPosition();
        
        JPopupMenu popup = new JPopupMenu("Changer le biome");
        
        for (TypeBiome type : TypeBiome.values()) {
            JMenuItem item = new JMenuItem(type.name());
            item.addActionListener(e -> {
                Biome nouveauBiome = BiomeFactory.creerBiomeParType(type, position);
                ((ManageurBasique) manageur).remplacerBiome(position, nouveauBiome);
                popup.setVisible(false);
                if (displayer != null) {
                    displayer.repaint();
                }
            });
            popup.add(item);
        }
        
        popup.show(btnModifier, btnModifier.getWidth() / 2, 0);
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
    
    public void mettreAJourBoutonModifier() {
        if (displayer != null) {
            setBiomeSelectionne(displayer.getBiomeSelectionne());
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
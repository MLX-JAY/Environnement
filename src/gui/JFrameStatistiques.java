package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.util.List;
import java.util.Map;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.Timer;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.plot.PiePlot;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

import moteur.processus.Manageur;
import moteur.processus.ManageurBasique;
import moteur.processus.ManageurBasique.StatRound;

public class JFrameStatistiques extends JFrame {
    
    private Manageur manageur;
    private JTabbedPane onglets;
    private ChartPanel chartTemp, chartHumid, chartPoll, chartPurif;
    private ChartPanel chartBiomesPie, chartBiomesLine;
    private ChartPanel chartEvenementsBar, chartEvenementsLine;
    private ChartPanel chartEvolutionGlobale;
    private Timer refreshTimer;
    private int dernierRound = -1;
    
    private static final Color COULEUR_TEMP = new Color(255, 140, 0);
    private static final Color COULEUR_HUMID = new Color(0, 191, 255);
    private static final Color COULEUR_POLL = new Color(220, 20, 60);
    private static final Color COULEUR_PURIF = new Color(50, 205, 50);
    
    public JFrameStatistiques(Manageur manageur) {
        super("📊 Statistiques - Simulation Environnement");
        this.manageur = manageur;
        
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(1200, 800);
        setLocationRelativeTo(null);
        setBackground(new Color(40, 54, 24));
        
        initComposants();
        demarrerRefresh();
    }
    
    private void initComposants() {
        onglets = new JTabbedPane();
        onglets.setFont(new Font("Segoe UI", Font.BOLD, 14));
        onglets.setBackground(new Color(52, 78, 65));
        onglets.setForeground(Color.WHITE);
        
        // Onglet 1: Apercu
        JPanel panelApercu = creerPanelApercu();
        onglets.addTab("Aperçu", panelApercu);
        
        // Onglet 2: Biomes
        JPanel panelBiomes = creerPanelBiomes();
        onglets.addTab("Biomes", panelBiomes);
        
        // Onglet 3: Evenements
        JPanel panelEvenements = creerPanelEvenements();
        onglets.addTab("Événements", panelEvenements);
        
        // Onglet 4: Evolution Globale
        JPanel panelEvolution = creerPanelEvolution();
        onglets.addTab("Évolution Globale", panelEvolution);
        
        // Ajout au frame avec scroll
        JScrollPane scrollPane = new JScrollPane(onglets);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        
        add(scrollPane);
        
        mettreAJourTousLesGraphiques();
    }
    
    private JPanel creerPanelApercu() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBackground(new Color(45, 45, 45));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        JPanel grille = new JPanel(new BorderLayout(10, 10));
        grille.setBackground(new Color(45, 45, 45));
        
        // 4 graphiques en 2x2
        JPanel haut = new JPanel(new BorderLayout(10, 10));
        haut.setBackground(new Color(45, 45, 45));
        JPanel bas = new JPanel(new BorderLayout(10, 10));
        bas.setBackground(new Color(45, 45, 45));
        
        chartTemp = creerChartCourbe("Température Moyenne", "Round", "Température (%)", COULEUR_TEMP);
        chartHumid = creerChartCourbe("Humidité Moyenne", "Round", "Humidité (%)", COULEUR_HUMID);
        chartPoll = creerChartCourbe("Pollution Moyenne", "Round", "Pollution (%)", COULEUR_POLL);
        chartPurif = creerChartCourbe("Purification Moyenne", "Round", "Purification (%)", COULEUR_PURIF);
        
        haut.add(chartTemp, BorderLayout.WEST);
        haut.add(chartHumid, BorderLayout.EAST);
        bas.add(chartPoll, BorderLayout.WEST);
        bas.add(chartPurif, BorderLayout.EAST);
        
        grille.add(haut, BorderLayout.NORTH);
        grille.add(bas, BorderLayout.SOUTH);
        
        panel.add(grille, BorderLayout.CENTER);
        
        return panel;
    }
    
    private JPanel creerPanelBiomes() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBackground(new Color(45, 45, 45));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        JPanel grille = new JPanel(new BorderLayout(10, 10));
        grille.setBackground(new Color(45, 45, 45));
        
        chartBiomesPie = creerChartPie("Répartition des Biomes");
        chartBiomesLine = creerChartHistoriqueBiomes();
        
        grille.add(chartBiomesPie, BorderLayout.WEST);
        grille.add(chartBiomesLine, BorderLayout.EAST);
        
        panel.add(grille, BorderLayout.CENTER);
        
        return panel;
    }
    
    private JPanel creerPanelEvenements() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBackground(new Color(45, 45, 45));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        JPanel grille = new JPanel(new BorderLayout(10, 10));
        grille.setBackground(new Color(45, 45, 45));
        
        chartEvenementsBar = creerChartBarresEvenements();
        chartEvenementsLine = creerChartTimelineEvenements();
        
        grille.add(chartEvenementsBar, BorderLayout.WEST);
        grille.add(chartEvenementsLine, BorderLayout.EAST);
        
        panel.add(grille, BorderLayout.CENTER);
        
        return panel;
    }
    
    private JPanel creerPanelEvolution() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBackground(new Color(45, 45, 45));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        chartEvolutionGlobale = creerChartEvolutionGlobale();
        
        panel.add(chartEvolutionGlobale, BorderLayout.CENTER);
        
        return panel;
    }
    
    private ChartPanel creerChartCourbe(String titre, String xLabel, String yLabel, Color couleur) {
        XYSeries serie = new XYSeries(titre);
        XYSeriesCollection dataset = new XYSeriesCollection(serie);
        
        JFreeChart chart = ChartFactory.createXYLineChart(
            titre, xLabel, yLabel, dataset
        );
        
        XYPlot plot = chart.getXYPlot();
        plot.setBackgroundPaint(new Color(30, 30, 30));
        plot.setRangeGridlinePaint(new Color(80, 80, 80));
        plot.setDomainGridlinePaint(new Color(80, 80, 80));
        chart.getTitle().setPaint(Color.WHITE);
        chart.setBackgroundPaint(new Color(45, 45, 45));
        
        XYLineAndShapeRenderer renderer = new XYLineAndShapeRenderer(true, true);
        renderer.setSeriesPaint(0, couleur);
        renderer.setSeriesStroke(0, new java.awt.BasicStroke(2.0f));
        plot.setRenderer(renderer);
        
        NumberAxis axisX = (NumberAxis) plot.getDomainAxis();
        axisX.setTickLabelPaint(Color.WHITE);
        axisX.setLabelPaint(Color.WHITE);
        
        NumberAxis axisY = (NumberAxis) plot.getRangeAxis();
        axisY.setTickLabelPaint(Color.WHITE);
        axisY.setLabelPaint(Color.WHITE);
        axisY.setRange(0, 100);
        
        ChartPanel chartPanel = new ChartPanel(chart);
        chartPanel.setPreferredSize(new Dimension(550, 300));
        
        return chartPanel;
    }
    
    private ChartPanel creerChartPie(String titre) {
        org.jfree.data.general.DefaultPieDataset dataset = new org.jfree.data.general.DefaultPieDataset();
        
        JFreeChart chart = ChartFactory.createPieChart(titre, dataset, true, true, false);
        
        PiePlot plot = (PiePlot) chart.getPlot();
        plot.setBackgroundPaint(new Color(30, 30, 30));
        plot.setLabelBackgroundPaint(new Color(45, 45, 45));
        plot.setLabelPaint(Color.WHITE);
        chart.getTitle().setPaint(Color.WHITE);
        chart.setBackgroundPaint(new Color(45, 45, 45));
        
        ChartPanel chartPanel = new ChartPanel(chart);
        chartPanel.setPreferredSize(new Dimension(550, 400));
        
        return chartPanel;
    }
    
    private ChartPanel creerChartHistoriqueBiomes() {
        XYSeriesCollection dataset = new XYSeriesCollection();
        
        String[] typesBiomes = {"Foret", "Desert", "Mer", "Montagne", "Banquise", "Ville", "Village"};
        for (String type : typesBiomes) {
            dataset.addSeries(new XYSeries(type));
        }
        
        JFreeChart chart = ChartFactory.createXYLineChart(
            "Historique des Biomes", "Round", "Nombre", dataset
        );
        
        XYPlot plot = chart.getXYPlot();
        plot.setBackgroundPaint(new Color(30, 30, 30));
        plot.setRangeGridlinePaint(new Color(80, 80, 80));
        plot.setDomainGridlinePaint(new Color(80, 80, 80));
        chart.getTitle().setPaint(Color.WHITE);
        chart.setBackgroundPaint(new Color(45, 45, 45));
        
        ChartPanel chartPanel = new ChartPanel(chart);
        chartPanel.setPreferredSize(new Dimension(550, 400));
        
        return chartPanel;
    }
    
    private ChartPanel creerChartBarresEvenements() {
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        
        JFreeChart chart = ChartFactory.createBarChart(
            "Événements Actifs", "Type", "Nombre", dataset
        );
        
        chart.getCategoryPlot().setBackgroundPaint(new Color(30, 30, 30));
        chart.getCategoryPlot().setRangeGridlinePaint(new Color(80, 80, 80));
        chart.getCategoryPlot().getDomainAxis().setTickLabelPaint(Color.WHITE);
        chart.getCategoryPlot().getRangeAxis().setTickLabelPaint(Color.WHITE);
        chart.getCategoryPlot().getDomainAxis().setLabelPaint(Color.WHITE);
        chart.getCategoryPlot().getRangeAxis().setLabelPaint(Color.WHITE);
        chart.getTitle().setPaint(Color.WHITE);
        chart.setBackgroundPaint(new Color(45, 45, 45));
        
        ChartPanel chartPanel = new ChartPanel(chart);
        chartPanel.setPreferredSize(new Dimension(550, 400));
        
        return chartPanel;
    }
    
    private ChartPanel creerChartTimelineEvenements() {
        XYSeriesCollection dataset = new XYSeriesCollection();
        
        JFreeChart chart = ChartFactory.createXYLineChart(
            "Timeline des Événements", "Round", "Nombre Total", dataset
        );
        
        XYPlot plot = chart.getXYPlot();
        plot.setBackgroundPaint(new Color(30, 30, 30));
        plot.setRangeGridlinePaint(new Color(80, 80, 80));
        plot.setDomainGridlinePaint(new Color(80, 80, 80));
        chart.getTitle().setPaint(Color.WHITE);
        chart.setBackgroundPaint(new Color(45, 45, 45));
        
        ChartPanel chartPanel = new ChartPanel(chart);
        chartPanel.setPreferredSize(new Dimension(550, 400));
        
        return chartPanel;
    }
    
    private ChartPanel creerChartEvolutionGlobale() {
        XYSeries serieTemp = new XYSeries("Température");
        XYSeries serieHumid = new XYSeries("Humidité");
        XYSeries seriePoll = new XYSeries("Pollution");
        XYSeries seriePurif = new XYSeries("Purification");
        
        XYSeriesCollection dataset = new XYSeriesCollection();
        dataset.addSeries(serieTemp);
        dataset.addSeries(serieHumid);
        dataset.addSeries(seriePoll);
        dataset.addSeries(seriePurif);
        
        JFreeChart chart = ChartFactory.createXYLineChart(
            "Évolution Globale de l'Écosystème", "Round", "Valeur (%)", dataset
        );
        
        XYPlot plot = chart.getXYPlot();
        plot.setBackgroundPaint(new Color(30, 30, 30));
        plot.setRangeGridlinePaint(new Color(80, 80, 80));
        plot.setDomainGridlinePaint(new Color(80, 80, 80));
        chart.getTitle().setPaint(Color.WHITE);
        chart.setBackgroundPaint(new Color(45, 45, 45));
        
        XYLineAndShapeRenderer renderer = new XYLineAndShapeRenderer(true, true);
        renderer.setSeriesPaint(0, COULEUR_TEMP);
        renderer.setSeriesPaint(1, COULEUR_HUMID);
        renderer.setSeriesPaint(2, COULEUR_POLL);
        renderer.setSeriesPaint(3, COULEUR_PURIF);
        for (int i = 0; i < 4; i++) {
            renderer.setSeriesStroke(i, new java.awt.BasicStroke(2.0f));
        }
        plot.setRenderer(renderer);
        
        NumberAxis axisY = (NumberAxis) plot.getRangeAxis();
        axisY.setTickLabelPaint(Color.WHITE);
        axisY.setLabelPaint(Color.WHITE);
        axisY.setRange(0, 100);
        
        NumberAxis axisX = (NumberAxis) plot.getDomainAxis();
        axisX.setTickLabelPaint(Color.WHITE);
        axisX.setLabelPaint(Color.WHITE);
        
        ChartPanel chartPanel = new ChartPanel(chart);
        chartPanel.setPreferredSize(new Dimension(1100, 500));
        
        return chartPanel;
    }
    
    private void mettreAJourTousLesGraphiques() {
        if (!(manageur instanceof ManageurBasique)) return;
        
        ManageurBasique mb = (ManageurBasique) manageur;
        List<StatRound> historique = mb.getHistorique();
        
        if (historique.isEmpty()) return;
        
        mettreAJourChartApercu(historique);
        mettreAJourChartBiomes(historique);
        mettreAJourChartEvenements(historique);
        mettreAJourChartEvolution(historique);
    }
    
    private void mettreAJourChartApercu(List<StatRound> historique) {
        XYSeries serieTemp = new XYSeries("Température");
        XYSeries serieHumid = new XYSeries("Humidité");
        XYSeries seriePoll = new XYSeries("Pollution");
        XYSeries seriePurif = new XYSeries("Purification");
        
        for (StatRound stat : historique) {
            serieTemp.add(stat.round, stat.moyennes[0]);
            serieHumid.add(stat.round, stat.moyennes[1]);
            seriePoll.add(stat.round, stat.moyennes[2]);
            seriePurif.add(stat.round, stat.moyennes[3]);
        }
        
        XYSeriesCollection datasetTemp = new XYSeriesCollection(serieTemp);
        ((XYPlot) chartTemp.getChart().getXYPlot()).setDataset(datasetTemp);
        
        XYSeriesCollection datasetHumid = new XYSeriesCollection(serieHumid);
        ((XYPlot) chartHumid.getChart().getXYPlot()).setDataset(datasetHumid);
        
        XYSeriesCollection datasetPoll = new XYSeriesCollection(seriePoll);
        ((XYPlot) chartPoll.getChart().getXYPlot()).setDataset(datasetPoll);
        
        XYSeriesCollection datasetPurif = new XYSeriesCollection(seriePurif);
        ((XYPlot) chartPurif.getChart().getXYPlot()).setDataset(datasetPurif);
    }
    
    private void mettreAJourChartBiomes(List<StatRound> historique) {
        // Pie chart - dernier etat
        StatRound dernier = historique.get(historique.size() - 1);
        org.jfree.data.general.DefaultPieDataset pieDataset = new org.jfree.data.general.DefaultPieDataset();

        for (Map.Entry<String, Integer> entry : dernier.compteBiomes.entrySet()) {
            pieDataset.setValue(entry.getKey(), entry.getValue());
        }
        ((PiePlot) chartBiomesPie.getChart().getPlot()).setDataset(pieDataset);
        
        // Line chart - historique
        XYSeriesCollection dataset = new XYSeriesCollection();
        String[] typesBiomes = {"Foret", "Desert", "Mer", "Montagne", "Banquise", "Ville", "Village"};
        
        for (String type : typesBiomes) {
            XYSeries serie = new XYSeries(type);
            for (StatRound stat : historique) {
                Integer count = stat.compteBiomes.get(type);
                serie.add(stat.round, count != null ? count : 0);
            }
            dataset.addSeries(serie);
        }
        ((XYPlot) chartBiomesLine.getChart().getXYPlot()).setDataset(dataset);
    }
    
    private void mettreAJourChartEvenements(List<StatRound> historique) {
        // Bar chart - actuel
        StatRound dernier = historique.get(historique.size() - 1);
        DefaultCategoryDataset barDataset = new DefaultCategoryDataset();
        for (Map.Entry<String, Integer> entry : dernier.compteEvenements.entrySet()) {
            barDataset.setValue(entry.getValue(), "Événements", entry.getKey());
        }
        chartEvenementsBar.getChart().getCategoryPlot().setDataset(barDataset);
        
        // Line chart - historique total
        XYSeries serieTotal = new XYSeries("Total Événements");
        for (StatRound stat : historique) {
            int total = stat.compteEvenements.values().stream().mapToInt(Integer::intValue).sum();
            serieTotal.add(stat.round, total);
        }
        XYSeriesCollection dataset = new XYSeriesCollection(serieTotal);
        ((XYPlot) chartEvenementsLine.getChart().getXYPlot()).setDataset(dataset);
    }
    
    private void mettreAJourChartEvolution(List<StatRound> historique) {
        XYSeries serieTemp = new XYSeries("Température");
        XYSeries serieHumid = new XYSeries("Humidité");
        XYSeries seriePoll = new XYSeries("Pollution");
        XYSeries seriePurif = new XYSeries("Purification");
        
        for (StatRound stat : historique) {
            serieTemp.add(stat.round, stat.moyennes[0]);
            serieHumid.add(stat.round, stat.moyennes[1]);
            seriePoll.add(stat.round, stat.moyennes[2]);
            seriePurif.add(stat.round, stat.moyennes[3]);
        }
        
        XYSeriesCollection dataset = new XYSeriesCollection();
        dataset.addSeries(serieTemp);
        dataset.addSeries(serieHumid);
        dataset.addSeries(seriePoll);
        dataset.addSeries(seriePurif);
        
        ((XYPlot) chartEvolutionGlobale.getChart().getXYPlot()).setDataset(dataset);
    }
    
    private void demarrerRefresh() {
        refreshTimer = new Timer(500, e -> {
            if (manageur instanceof ManageurBasique) {
                ManageurBasique mb = (ManageurBasique) manageur;
                int round = mb.getRoundActuel();
                if (round != dernierRound) {
                    dernierRound = round;
                    mettreAJourTousLesGraphiques();
                }
            }
        });
        refreshTimer.start();
    }
    
    @Override
    public void dispose() {
        if (refreshTimer != null) {
            refreshTimer.stop();
        }
        super.dispose();
    }
}

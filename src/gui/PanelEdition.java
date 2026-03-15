package gui;

import javax.swing.JButton;
import javax.swing.JPanel;
import java.awt.Dimension;
import java.awt.Color;
import java.awt.FlowLayout;

public class PanelEdition extends JPanel {
    
    private static final Color COULEUR_BOIS = new Color(139, 90, 43); 
    private static final Color COULEUR_ACTION = new Color(60, 140, 60); 
    private static final Color COULEUR_DANGER = new Color(180, 60, 60); 

    private JButton btnAjouter, btnSupprimer, btnModifier, btnFin;

    public PanelEdition(Runnable actionFin) {

        this.setBackground(new Color(40, 54, 24));
        this.setPreferredSize(new Dimension(0, 80));
        this.setLayout(new FlowLayout(FlowLayout.CENTER, 20, 15));

        btnAjouter = deseign.creerBeauBouton("Végétaliser", COULEUR_BOIS);
        btnSupprimer = deseign.creerBeauBouton("Détruire", COULEUR_DANGER);
        btnModifier = deseign.creerBeauBouton("Modifier", COULEUR_BOIS);
        btnFin = deseign.creerBeauBouton("Lancer", COULEUR_ACTION);

        this.add(btnAjouter);
        this.add(btnSupprimer);
        this.add(btnModifier);
        this.add(btnFin);

        // Action du bouton Fin evite d'importer MainGUI dans ce panel , on utilise une fonction (Runnable)
        btnFin.addActionListener(e -> actionFin.run());
    }

    
}

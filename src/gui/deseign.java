package gui;

import javax.swing.JButton;

import javafx.scene.Cursor;

import java.awt.Font;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;


public class deseign {

    protected static final Color COULEUR_BORDURE = new Color(0, 0, 0, 150); // Noir semi-transparent pour la bordure

    
    public static JButton creerBeauBouton(String texte, Color couleurFond) {
        JButton btn = new JButton(texte) {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

                Color c = getBackground();
                
                // 1. LE DÉGRADÉ (Du haut vers le bas)
                GradientPaint degrade = new GradientPaint(0, 0, c.brighter(), 0, getHeight(), c.darker());
                
                // boutton pressé 
                if (getModel().isPressed()) {
                    degrade = new GradientPaint(0, 0, c.darker(), 0, getHeight(), c.brighter());
                }

                g2.setPaint(degrade);

                // On dessine le fond arrondi avec le dégradé
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 45, 45);

                // 2. LA BORDURE
                g2.setColor(COULEUR_BORDURE);
                g2.setStroke(new BasicStroke(4.0f));
                g2.drawRoundRect(1, 1, getWidth()-2, getHeight()-2, 45, 45);

                g2.dispose();
                super.paintComponent(g);
            }
        };

        btn.setFont(new Font("Segoe UI", Font.BOLD, 20));
        btn.setForeground(Color.WHITE);
        btn.setBackground(couleurFond);
        
        btn.setContentAreaFilled(false); 
        btn.setBorderPainted(false);
        btn.setFocusPainted(false);
        btn.setOpaque(false);
        
        btn.setPreferredSize(new Dimension(170, 50));

        btn.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btn.setBackground(couleurFond.brighter());
            }
            @Override
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btn.setBackground(couleurFond);
            }
            
        });

        return btn;
    }
}

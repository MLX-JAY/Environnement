package gui;

import javax.swing.JButton;
import javax.swing.JSlider;
import java.awt.Font;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;


public class deseign {

    protected static final Color COULEUR_BORDURE = new Color(0, 0, 0, 150);
    
    public static final Color COULEUR_SLIDER_TRACK = new Color(30, 35, 30);
    public static final Color COULEUR_SLIDER_THUMB = new Color(100, 200, 150);
    public static final Color COULEUR_SLIDER_FILL = new Color(80, 160, 120);
    
    public static JButton creerBeauBouton(String texte, Color couleurFond) {
        JButton btn = new JButton(texte) {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

                Color c = getBackground();
                
                GradientPaint degrade = new GradientPaint(0, 0, c.brighter(), 0, getHeight(), c.darker());
                
                if (getModel().isPressed()) {
                    degrade = new GradientPaint(0, 0, c.darker(), 0, getHeight(), c.brighter());
                }

                g2.setPaint(degrade);
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 45, 45);

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
    
    public static JSlider creerBeauSlider(int valeurInitiale, int min, int max) {
        return creerBeauSlider(valeurInitiale, min, max, null);
    }
    
    public static JSlider creerBeauSlider(int valeurInitiale, int min, int max, Color couleurBg) {
        JSlider slider = new JSlider(min, max, valeurInitiale) {
            private Color couleurPersonnalisee = couleurBg;
            
            public void setCouleurBg(Color c) {
                this.couleurPersonnalisee = c;
                repaint();
            }
            
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                
                int w = getWidth();
                int h = getHeight();
                
                // Fond du slider (personnalisé ou par défaut)
                if (couleurPersonnalisee != null) {
                    g2.setColor(couleurPersonnalisee);
                    g2.fillRoundRect(0, 0, w, h, 6, 6);
                } else {
                    g2.setColor(new Color(0, 0, 0, 0));
                    g2.fillRect(0, 0, w, h);
                }
                
                // Fond du slider (track)
                g2.setColor(COULEUR_SLIDER_TRACK);
                g2.fillRoundRect(2, h/2 - 4, w - 4, 8, 4, 4);
                
                // Remplissage
                double range = getMaximum() - getMinimum();
                int valuePos = range > 0 ? (int)((double)(getValue() - getMinimum()) / range * (w - 8)) : 0;
                if (valuePos < 0) valuePos = 0;
                if (valuePos > w - 8) valuePos = w - 8;
                
                g2.setColor(COULEUR_SLIDER_FILL);
                g2.fillRoundRect(2, h/2 - 2, valuePos, 4, 2, 4);
                
                // Puce (thumb)
                int thumbX = valuePos + 2;
                int thumbY = h / 2;
                int thumbR = 8;
                
                g2.setColor(COULEUR_SLIDER_THUMB);
                g2.fillOval(thumbX - thumbR + 2, thumbY - thumbR, thumbR * 2, thumbR * 2);
                
                // Contour
                g2.setColor(COULEUR_BORDURE);
                g2.setStroke(new BasicStroke(2f));
                g2.drawOval(thumbX - thumbR + 2, thumbY - thumbR, thumbR * 2, thumbR * 2);
                
                // Reflet
                g2.setColor(new Color(255, 255, 255, 100));
                g2.fillOval(thumbX - 3, thumbY - 4, 4, 4);
                
                g2.dispose();
            }
        };
        return slider;
        
        }
}
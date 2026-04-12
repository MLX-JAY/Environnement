package gui;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.geom.QuadCurve2D;
import java.util.Random;
import javax.swing.ImageIcon;
import moteur.donne.biome.Banquise;
import moteur.donne.biome.Desert;
import moteur.donne.biome.Foret;
import moteur.donne.biome.Mer;
import moteur.donne.biome.Montagne;
import moteur.donne.biome.Village;
import moteur.donne.biome.Ville;
import moteur.donne.carte.Bloc;
import moteur.donne.evenement.Evenement;
import moteur.donne.evenement.mobile.Pluie;
import moteur.donne.evenement.mobile.Pollution;
import moteur.donne.evenement.mobile.Purification;
import moteur.donne.evenement.mobile.VentChaud;
import moteur.donne.evenement.mobile.VentFroid;
import moteur.donne.evenement.mobile.Orage;
import moteur.donne.evenement.mobile.Grele;
import moteur.donne.evenement.mobile.Tornade;
import moteur.donne.evenement.mobile.PluieBenite;
import moteur.donne.evenement.mobile.Zephyr;
import moteur.donne.evenement.mobile.Tonnerre;
import moteur.donne.evenement.mobile.Smog;
import moteur.donne.evenement.mobile.NuageToxique;
import moteur.donne.evenement.statique.Meteore;

public class StrategiePeinture 
{
    private Image flocon = new ImageIcon(getClass().getResource("/image/flocon.png")).getImage();
    private Image danger = new ImageIcon(getClass().getResource("/image/danger.png")).getImage();
    private Image meteore = new ImageIcon(getClass().getResource("/image/meteore.png")).getImage(); 
    private Random rand = new Random(); 

    private void dessinerNuage(Graphics2D g2, int x, int y, int size) {
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        // Ombre portée
        g2.setColor(new Color(90, 100, 120, 70));
        g2.fillOval(x + size / 8 + 2, y + size / 3 + 2, size / 3,     size / 4);
        g2.fillOval(x + size / 3 + 2, y + size / 5 + 2, size / 2,     size / 2);
        g2.fillOval(x + size / 2 + 2, y + size / 3 + 2, size / 3,     size / 4);
        // Corps principal
        g2.setColor(new Color(190, 205, 222, 235));
        g2.fillOval(x + size / 8,  y + size / 3, size / 3, size / 4);
        g2.fillOval(x + size / 3,  y + size / 5, size / 2, size / 2);
        g2.fillOval(x + size / 2,  y + size / 3, size / 3, size / 4);
        // Reflet clair en haut
        g2.setColor(new Color(248, 252, 255, 190));
        g2.fillOval(x + size / 3 + 4, y + size / 4, size * 3 / 10, size / 7);
    }

    private void dessinerPollution(Graphics2D g2, int x, int y, int size) {
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        // 3 panaches de fumée montants et sinusoïdaux
        int[][] sources = {
            {x + size / 5,      y + size * 4 / 5},
            {x + size / 2,      y + size * 3 / 4},
            {x + size * 3 / 4,  y + size * 4 / 5}
        };
        for (int[] src : sources) {
            for (int j = 0; j < 6; j++) {
                float t = (float) j / 5f;
                int py = src[1] - (int)(t * size * 0.6f);
                int px = src[0] + (int)(Math.sin(t * Math.PI * 1.8) * size * 0.07f);
                int r  = (int)(size * 0.07f * (1f + t * 1.5f));
                int alpha = (int)(160 - t * 110);
                int gray  = 65 + (int)(t * 35);
                g2.setColor(new Color(gray, gray - 5, gray - 12, Math.max(alpha, 10)));
                g2.fillOval(px - r, py - r, r * 2, r * 2);
            }
        }
        // Particules orange de combustion
        g2.setColor(new Color(180, 90, 30, 140));
        g2.fillOval(x + size / 5 - 2, y + size * 4 / 5 - 3, 5, 5);
        g2.fillOval(x + size / 2 - 2, y + size * 3 / 4 - 3, 5, 5);
        g2.fillOval(x + size * 3 / 4 - 2, y + size * 4 / 5 - 3, 5, 5);
    }

    private void dessinerFeuille(Graphics2D g2, int cx, int cy, int size, double rotation) {
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        java.awt.geom.AffineTransform old = g2.getTransform();
        g2.translate(cx, cy);
        g2.rotate(rotation);
        int w = size / 2, h = (size * 3) / 5;
        // Corps de la feuille
        g2.setColor(new Color(45, 140, 65, 220));
        g2.fillOval(-w / 2, -h / 2, w, h);
        // Reflet
        g2.setColor(new Color(100, 200, 110, 160));
        g2.fillOval(-w / 4, -h / 2 + 3, w / 3, h / 3);
        // Nervure centrale
        g2.setColor(new Color(215, 250, 215, 200));
        g2.setStroke(new BasicStroke(1.8f, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
        g2.drawLine(0, -h / 2 + 2, 0, h / 2 - 2);
        // Nervures latérales
        g2.setStroke(new BasicStroke(1f, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
        g2.drawLine(0, -h / 6, w / 3, -h / 10);
        g2.drawLine(0,  h / 6, w / 3,  h / 10);
        g2.drawLine(0, -h / 6, -w / 3, -h / 10);
        g2.drawLine(0,  h / 6, -w / 3,  h / 10);
        g2.setTransform(old);
    }

    private void dessinerFlocon(Graphics2D g2, int cx, int cy, int radius) {
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setStroke(new BasicStroke(2f, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
        g2.setColor(new Color(200, 235, 255, 230));
        for (int i = 0; i < 6; i++) {
            double angle = Math.toRadians(i * 60);
            int ex = cx + (int)(radius * Math.cos(angle));
            int ey = cy + (int)(radius * Math.sin(angle));
            g2.drawLine(cx, cy, ex, ey);
            // Branches secondaires
            int mx = cx + (int)(radius * 0.55 * Math.cos(angle));
            int my = cy + (int)(radius * 0.55 * Math.sin(angle));
            int bLen = radius / 3;
            double a1 = angle + Math.toRadians(60);
            double a2 = angle - Math.toRadians(60);
            g2.drawLine(mx, my, mx + (int)(bLen * Math.cos(a1)), my + (int)(bLen * Math.sin(a1)));
            g2.drawLine(mx, my, mx + (int)(bLen * Math.cos(a2)), my + (int)(bLen * Math.sin(a2)));
        }
        g2.setColor(new Color(255, 255, 255, 220));
        g2.fillOval(cx - 3, cy - 3, 6, 6);
    }

    private void dessinerVentChaud(Graphics2D g2, int x, int y, int size) {
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setStroke(new BasicStroke(2.5f, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));

        // 4 ondes de chaleur horizontales ondulées
        int[] offsetsY = {size / 5, size * 2 / 5, size * 3 / 5, size * 4 / 5};
        Color[] couleurs = {
            new Color(220, 80, 30, 210),
            new Color(245, 130, 20, 190),
            new Color(255, 170, 50, 165),
            new Color(230, 100, 45, 145)
        };
        for (int i = 0; i < 4; i++) {
            g2.setColor(couleurs[i]);
            int lineY = y + offsetsY[i];
            g2.drawArc(x + size / 10,              lineY - size / 8, size / 4, size / 4,   0, 180);
            g2.drawArc(x + size / 10 + size / 4,   lineY - size / 8, size / 4, size / 4, 180, 180);
            g2.drawArc(x + size / 10 + size / 2,   lineY - size / 8, size / 4, size / 4,   0, 180);
        }

        // Particules de chaleur flottantes
        Random r = new Random(x + y * 31);
        g2.setColor(new Color(255, 150, 50, 160));
        for (int i = 0; i < 7; i++) {
            int px = x + r.nextInt(size - 4);
            int py = y + r.nextInt(size - 4);
            g2.fillOval(px, py, 3 + r.nextInt(3), 3 + r.nextInt(3));
        }
    }


    
//===========================================  LES BIOMES  ============================================================
    
    public void paint(Foret foret, Graphics graphics) {    
        Bloc position = foret.getPosition();
        int size = config.GameConfiguration.TAILLE_BLOC;
        int x = position.getX() * size;
        int y = position.getY() * size;
        
        graphics.setColor(new Color(0x60993E)); 
        graphics.fillRect(x, y, size, size);
        
        Random rand = new Random(position.getX() * 31 + position.getY());
        
        for (int i = 0; i < 6; i++) {
            int tx = x + rand.nextInt(size - 10);
            int ty = y + rand.nextInt(size - 10);
            int tSize = rand.nextInt(size / 3) + (size / 4);
            
            graphics.setColor(new Color(0x204A18));
            graphics.fillOval(tx + 2, ty + 2, tSize, tSize);

            graphics.setColor(new Color(0x3A7D25));
            graphics.fillOval(tx, ty, tSize, tSize);
            
            graphics.setColor(new Color(0x5CAD45));
            graphics.fillOval(tx + 2, ty + 2, tSize/2, tSize/2);
        }
    }
    
    public void paint(Desert desert, Graphics graphics) 
    {    
        Bloc position = desert.getPosition();
        int size = config.GameConfiguration.TAILLE_BLOC;
        int x = position.getX() * size;
        int y = position.getY() * size;
        
        graphics.setColor(new Color(0xEEDC82));
        graphics.fillRect(x, y, size, size);
        
        Random rand = new Random(position.getX() * 31 + position.getY());
        
        graphics.setColor(new Color(0xD4C066));
        for (int i = 0; i < 3; i++) {
            int dx = x + rand.nextInt(size/2);
            int dy = y + rand.nextInt(size - 10);
            graphics.drawArc(dx, dy, size/2, size/4, 0, 180);
        }
        
        if (rand.nextBoolean()) {
            int cx = x + rand.nextInt(size - 10);
            int cy = y + rand.nextInt(size - 15) + 5;
            graphics.setColor(new Color(0x2E8B57));
            graphics.fillRect(cx, cy, 4, 10); 
            graphics.fillRect(cx-2, cy+2, 2, 4); 
            graphics.fillRect(cx+4, cy+1, 2, 4); 
        }
    }
    
    public void paint(Mer mer, Graphics graphics) 
    {    
        Bloc position = mer.getPosition();
        int size = config.GameConfiguration.TAILLE_BLOC;
        int x = position.getX() * size;
        int y = position.getY() * size;
        
        graphics.setColor(new Color(0x4FA3E0));
        graphics.fillRect(x, y, size, size);

        Random rand = new Random(position.getX() * 31 + position.getY());
        
        graphics.setColor(new Color(255, 255, 255, 120)); 
        
        for (int i = 0; i < 5; i++) {
            int wx = x + rand.nextInt(size - 10);
            int wy = y + rand.nextInt(size - 5);
            graphics.drawLine(wx, wy, wx + rand.nextInt(10) + 5, wy);
        }
    }
    
    public void paint(Village village, Graphics graphics) {
        Bloc position = village.getPosition();
        int size = config.GameConfiguration.TAILLE_BLOC;
        int x = position.getX() * size;
        int y = position.getY() * size;

        graphics.setColor(new Color(0xADB568));
        graphics.fillRect(x, y, size, size);

        int cx = x + size/2 - 6;
        int cy = y + size/2 - 4;
        
        drawMaison(graphics, cx, cy, 12);
        
        drawMaison(graphics, cx - 10, cy + 5, 8);
    }
    
    private void drawMaison(Graphics g, int x, int y, int taille) {
        g.setColor(new Color(0x8B4513)); 
        g.fillRect(x, y, taille, taille);
        
        g.setColor(new Color(0xB22222)); 
        int[] rx = {x - 2, x + taille/2, x + taille + 2};
        int[] ry = {y, y - taille/2, y};
        g.fillPolygon(rx, ry, 3);
        
        g.setColor(new Color(0xFFDEAD)); 
        g.fillRect(x + taille/2 - 2, y + taille/2, 4, taille/2);
    }
    
    public void paint(Banquise banquise, Graphics graphics) 
    {    
        Bloc position = banquise.getPosition();
        int size = config.GameConfiguration.TAILLE_BLOC;
        int x = position.getX() * size;
        int y = position.getY() * size;
        
        graphics.setColor(new Color(0xD8F6FF));
        graphics.fillRect(x, y, size, size);
        
        graphics.setColor(new Color(0xA8E6FF));
        graphics.drawLine(x, y, x+size, y+size);
    }
    
    public void paint(Ville ville, Graphics graphics) 
    {    
        Bloc position = ville.getPosition();
        int size = config.GameConfiguration.TAILLE_BLOC;
        int x = position.getX() * size;
        int y = position.getY() * size;
        
        graphics.setColor(new Color(0xAAAAAA)); 
        graphics.fillRect(x, y, size, size);
        
        Random rand = new Random(position.getX() * 31 + position.getY());
        
        for(int i=0; i<3; i++) {
            int h = rand.nextInt(size/2) + size/3;
            int w = rand.nextInt(6) + 6;
            int bx = x + rand.nextInt(size - 10);
            int by = y + size - h;
            
            graphics.setColor(new Color(0x333333));
            graphics.fillRect(bx, by, w, h);
            
            graphics.setColor(Color.YELLOW);
            graphics.fillRect(bx+2, by+4, 2, 2);
            graphics.fillRect(bx+2, by+8, 2, 2);
        }
    }
    
    public void paint(Montagne montagne, Graphics graphics) 
    {    
        Bloc position = montagne.getPosition();
        int size = config.GameConfiguration.TAILLE_BLOC;
        int x = position.getX() * size;
        int y = position.getY() * size;
        
        graphics.setColor(new Color(0x7D7D7D)); 
        graphics.fillRect(x, y, size, size);
        
        int[] px = {x, x + size/2, x + size};
        int[] py = {y + size, y, y + size};
        
        graphics.setColor(new Color(0x404040)); 
        graphics.fillPolygon(px, py, 3);
        
        int[] sx = {x + size/3 + 2, x + size/2, x + (size*2)/3 - 2};
        int[] sy = {y + size/3 + 5, y, y + size/3 + 5};
        graphics.setColor(Color.WHITE);
        graphics.fillPolygon(sx, sy, 3);
    }
    
//===========================================  LES EVENEMENTS  ============================================================
    

    public void paint(Pluie pluie, Graphics graphics) 
    {
        int size = config.GameConfiguration.TAILLE_BLOC;
        int x = (int) (pluie.getPositionAnimationX() * size);
        int y = (int) (pluie.getPositionAnimationY() * size);
        Graphics2D g2 = (Graphics2D) graphics.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        // Fond bleu nuit
        g2.setColor(new Color(5, 15, 55, 70));
        g2.fillRect(x, y, size, size);

        // Gouttes en forme de larme
        Random rand = new Random((int)pluie.getPositionAnimationX() + (int)pluie.getPositionAnimationY());
        for (int i = 0; i < 10; i++) {
            int gx = x + rand.nextInt(size - 4);
            int gy = y + size / 4 + rand.nextInt(size * 3 / 4 - 4);
            int r = 2 + rand.nextInt(2);
            g2.setColor(new Color(160, 210, 255, 200));
            // Pointe vers le haut
            int[] lx = {gx, gx + r, gx - r};
            int[] ly = {gy - r * 3, gy + r, gy + r};
            g2.fillPolygon(lx, ly, 3);
            g2.fillOval(gx - r, gy - r, r * 2, r * 2);
            // Reflet
            g2.setColor(new Color(230, 245, 255, 180));
            g2.fillOval(gx - 1, gy - r * 2, 2, 2);
        }

        // Flaque translucide en bas
        g2.setColor(new Color(100, 160, 255, 40));
        g2.fillOval(x + size / 8, y + size * 7 / 8, size * 3 / 4, size / 8);

        dessinerNuage(g2, x, y, size);
        g2.dispose();
    }

	public void paint(Pollution pollution, Graphics graphics)
	{
		int size = config.GameConfiguration.TAILLE_BLOC;
		int x = (int) (pollution.getPositionAnimationX() * size);
		int y = (int) (pollution.getPositionAnimationY() * size);
		Graphics2D g2 = (Graphics2D) graphics.create();
		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

		// Fond sombre brun-gris
		g2.setColor(new Color(40, 28, 15, 110));
		g2.fillRect(x, y, size, size);

		dessinerPollution(g2, x, y, size);

		// Contour danger rouge-brun
		g2.setColor(new Color(160, 50, 20, 180));
		g2.setStroke(new BasicStroke(2.5f));
		g2.drawRoundRect(x + 2, y + 2, size - 4, size - 4, 5, 5);

		g2.dispose();
	}

    public void paint(VentChaud ventChaud, Graphics graphics)
    {
        int size = config.GameConfiguration.TAILLE_BLOC;
        int x = (int) (ventChaud.getPositionAnimationX() * size);
        int y = (int) (ventChaud.getPositionAnimationY() * size);
        Graphics2D g2 = (Graphics2D) graphics.create();

        // Fond chaud orangé
        g2.setColor(new Color(180, 60, 0, 65));
        g2.fillRect(x, y, size, size);
        dessinerVentChaud(g2, x, y, size);
        g2.dispose();
    }
    
    public void paint(VentFroid froid, Graphics graphics) 
    {
        int size = config.GameConfiguration.TAILLE_BLOC;
        int x = (int) (froid.getPositionAnimationX() * size);
        int y = (int) (froid.getPositionAnimationY() * size);
        Graphics2D g2 = (Graphics2D) graphics.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        // Fond glacial bleu profond
        g2.setColor(new Color(0, 50, 130, 85));
        g2.fillRect(x, y, size, size);

        // Flocon central bien visible
        dessinerFlocon(g2, x + size / 2, y + size / 2, size / 3);

        // Petits flocons secondaires dispersés
        Random rand = new Random((int)froid.getPositionAnimationX() * 3 + (int)froid.getPositionAnimationY());
        for (int i = 0; i < 3; i++) {
            int fx = x + rand.nextInt(size - 12) + 6;
            int fy = y + rand.nextInt(size - 12) + 6;
            dessinerFlocon(g2, fx, fy, size / 9);
        }

        // Traînées de vent froid
        g2.setStroke(new BasicStroke(1.5f, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
        g2.setColor(new Color(180, 220, 255, 130));
        g2.drawLine(x + size / 10, y + size / 3,      x + size * 2 / 5, y + size / 3 + 3);
        g2.drawLine(x + size / 8,  y + size * 2 / 3,  x + size / 2,     y + size * 2 / 3 - 3);

        g2.dispose();
    }
    
    public void paint(Purification purification, Graphics graphics) 
    {
        int size = config.GameConfiguration.TAILLE_BLOC;
        int x = (int) (purification.getPositionAnimationX() * size);
        int y = (int) (purification.getPositionAnimationY() * size);
        Graphics2D g2 = (Graphics2D) graphics.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        // Fond vert très doux
        g2.setColor(new Color(0, 70, 30, 40));
        g2.fillRect(x, y, size, size);

        // Halo vert centré
        for (int r = size / 2; r > 0; r -= size / 8) {
            int alpha = (int)(30 * (1f - (float) r / (size / 2f)));
            g2.setColor(new Color(60, 190, 90, alpha));
            g2.fillOval(x + size / 2 - r, y + size / 2 - r, r * 2, r * 2);
        }

        // Feuilles multiples à différentes positions et rotations
        double[] rotations = {0.0, 0.6, -0.5, 1.1, -1.0};
        int[]    lx        = {x + size/2, x + size/4, x + size*3/4, x + size/3, x + size*2/3};
        int[]    ly        = {y + size/2, y + size/3, y + size/3,   y + size*2/3, y + size*2/3};
        int[]    ls        = {size,       size*2/3,   size*2/3,     size*2/3,     size*2/3};
        for (int i = 0; i < 5; i++) {
            dessinerFeuille(g2, lx[i], ly[i], ls[i], rotations[i]);
        }

        // Particules dorées scintillantes
        Random rand = new Random((int)purification.getPositionAnimationX() * 7 + (int)purification.getPositionAnimationY());
        g2.setColor(new Color(220, 200, 50, 200));
        for (int i = 0; i < 6; i++) {
            int px = x + rand.nextInt(size - 4);
            int py = y + rand.nextInt(size - 4);
            g2.fillOval(px, py, 3, 3);
            g2.setStroke(new BasicStroke(1f));
            g2.setColor(new Color(220, 200, 50, 100));
            g2.drawLine(px - 4, py, px + 4, py);
            g2.drawLine(px, py - 4, px, py + 4);
            g2.setColor(new Color(220, 200, 50, 200));
        }

        g2.dispose();
    }
    
    public void paintDanger(Evenement e,Graphics graphics) {
    	int size = config.GameConfiguration.TAILLE_BLOC;
        int x = (int) (e.getPositionAnimationX() * size);
        int y = (int) (e.getPositionAnimationY() * size);
        graphics.drawImage(danger, x, y, size*2, size*2, null);
        
    }
    
    public void paint(Meteore meteore,Graphics graphics) {
    	int size = config.GameConfiguration.TAILLE_BLOC;
        int x = (int) (meteore.getPositionAnimationX() * size);
        int y = (int) (meteore.getPositionAnimationY() * size);
        graphics.drawImage(this.meteore, x, y, size, size, null);
    }
    
    public void paint(Orage orage, Graphics graphics) {
        int size = config.GameConfiguration.TAILLE_BLOC;
        int x = (int) (orage.getPositionAnimationX() * size);
        int y = (int) (orage.getPositionAnimationY() * size);
        Graphics2D g2 = (Graphics2D) graphics.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        // Fond orageux bleu-ardoise
        g2.setColor(new Color(15, 20, 60, 90));
        g2.fillRect(x, y, size, size);

        // Pluie diagonale fine
        g2.setColor(new Color(140, 190, 255, 80));
        g2.setStroke(new BasicStroke(1f));
        for (int i = 0; i < 9; i++) {
            int rx = x + (i * size / 8) - size / 8;
            g2.drawLine(rx, y, rx - size / 10, y + size);
        }

        // Nuage d'orage dense et sombre
        g2.setColor(new Color(55, 58, 85, 240));
        g2.fillOval(x + size / 10,    y + size / 12, size * 3 / 5, size / 3);
        g2.fillOval(x + size / 4,     y + size / 20, size / 2,     size * 2 / 5);
        g2.fillOval(x + size * 2 / 5, y + size / 8,  size * 3 / 5, size / 3);

        // Éclair principal : halo + corps + cœur
        int bx = x + size * 9 / 20;
        int by0 = y + size * 2 / 5;
        int bx1 = bx - size / 5,   by1 = by0 + size * 3 / 10;
        int bx2 = bx + size / 8,   by2 = by1  + size / 5;
        int bx3 = bx - size / 10,  by3 = y + size - size / 10;

        g2.setColor(new Color(255, 255, 130, 40));
        g2.setStroke(new BasicStroke(13f, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
        g2.drawLine(bx, by0, bx1, by1); g2.drawLine(bx1, by1, bx2, by2); g2.drawLine(bx2, by2, bx3, by3);

        g2.setColor(new Color(255, 235, 70, 230));
        g2.setStroke(new BasicStroke(3.5f, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
        g2.drawLine(bx, by0, bx1, by1); g2.drawLine(bx1, by1, bx2, by2); g2.drawLine(bx2, by2, bx3, by3);

        g2.setColor(new Color(255, 255, 255, 200));
        g2.setStroke(new BasicStroke(1.5f, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
        g2.drawLine(bx, by0, bx1, by1); g2.drawLine(bx1, by1, bx2, by2); g2.drawLine(bx2, by2, bx3, by3);

        g2.dispose();
    }
    
    public void paint(Grele grele, Graphics graphics) {
        int size = config.GameConfiguration.TAILLE_BLOC;
        int x = (int) (grele.getPositionAnimationX() * size);
        int y = (int) (grele.getPositionAnimationY() * size);
        Graphics2D g2 = (Graphics2D) graphics.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        // Fond bleu acier
        g2.setColor(new Color(10, 35, 100, 75));
        g2.fillRect(x, y, size, size);

        // Grêlons avec traînée + reflet interne
        Random rand = new Random((int)grele.getPositionAnimationX() + (int)grele.getPositionAnimationY());
        for (int i = 0; i < 10; i++) {
            int gx = x + rand.nextInt(size - 6) + 3;
            int gy = y + size / 4 + rand.nextInt(size * 3 / 4 - 6);
            int r  = 3 + rand.nextInt(3);
            // Traînée de chute
            g2.setColor(new Color(180, 215, 255, 50));
            g2.setStroke(new BasicStroke(1f));
            g2.drawLine(gx, gy - r - 1, gx, gy - r - 5 - rand.nextInt(4));
            // Corps du grêlon
            g2.setColor(new Color(205, 235, 255, 210));
            g2.fillOval(gx - r, gy - r, r * 2, r * 2);
            // Reflet interne
            g2.setColor(new Color(255, 255, 255, 200));
            g2.fillOval(gx - r / 2 - 1, gy - r / 2 - 1, r / 2 + 1, r / 2 + 1);
        }

        dessinerNuage(g2, x, y, size);
        g2.dispose();
    }
    
    public void paint(Tornade tornade, Graphics graphics) {
        int size = config.GameConfiguration.TAILLE_BLOC;
        int x = (int) (tornade.getPositionAnimationX() * size);
        int y = (int) (tornade.getPositionAnimationY() * size);
        Graphics2D g2 = (Graphics2D) graphics.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        // Fond orageux sombre
        g2.setColor(new Color(50, 50, 65, 130));
        g2.fillRect(x, y, size, size);

        // Nuage tourbillonnant en haut
        dessinerNuage(g2, x, y, size);

        // Entonnoir : ellipses larges en haut → étroites en bas
        int centerX = x + size / 2;
        int levels = 7;
        for (int i = 0; i < levels; i++) {
            float t = (float) i / (levels - 1);
            int ellipseW = (int)(size * 0.75f * (1f - t * 0.65f));
            int ellipseH = Math.max(4, size / 11);
            int ellipseY = y + size / 4 + (int)(t * size * 0.58f);
            int gray = 130 + (int)(t * 40);
            int alpha = 160 + (int)(t * 70);
            g2.setColor(new Color(gray, gray, gray + 25, Math.min(alpha, 255)));
            g2.setStroke(new BasicStroke(1.8f));
            g2.drawOval(centerX - ellipseW / 2, ellipseY - ellipseH / 2, ellipseW, ellipseH);
        }

        // Point de contact au sol
        g2.setColor(new Color(50, 50, 65, 220));
        g2.fillOval(centerX - 5, y + size - 7, 10, 7);

        // Débris tourbillonnants
        Random rand = new Random((int)tornade.getPositionAnimationX() * 7 + (int)tornade.getPositionAnimationY());
        g2.setColor(new Color(90, 80, 70, 190));
        for (int i = 0; i < 9; i++) {
            int dx = x + size / 5 + rand.nextInt(size * 3 / 5);
            int dy = y + size / 3 + rand.nextInt(size / 2);
            g2.fillRect(dx, dy, 2 + rand.nextInt(3), 2 + rand.nextInt(3));
        }

        g2.dispose();
    }
    
    public void paint(PluieBenite pluieBenite, Graphics graphics) {
        int size = config.GameConfiguration.TAILLE_BLOC;
        int x = (int) (pluieBenite.getPositionAnimationX() * size);
        int y = (int) (pluieBenite.getPositionAnimationY() * size);
        Graphics2D g2 = (Graphics2D) graphics.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        // Fond doré chaud
        g2.setColor(new Color(80, 55, 0, 55));
        g2.fillRect(x, y, size, size);

        // Rayons divins angulaires depuis le nuage
        int srcX = x + size / 2, srcY = y + size / 3;
        double[] angles = {Math.PI/4, Math.PI*3/8, Math.PI/2, Math.PI*5/8, Math.PI*3/4};
        for (double angle : angles) {
            int ex = srcX + (int)(Math.cos(angle) * size * 0.75);
            int ey = srcY + (int)(Math.sin(angle) * size * 0.75);
            g2.setColor(new Color(255, 220, 60, 35));
            g2.setStroke(new BasicStroke(6f, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
            g2.drawLine(srcX, srcY, ex, ey);
            g2.setColor(new Color(255, 235, 120, 90));
            g2.setStroke(new BasicStroke(1.5f, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
            g2.drawLine(srcX, srcY, ex, ey);
        }

        // Gouttes en larme dorée
        Random rand = new Random((int)pluieBenite.getPositionAnimationX() + (int)pluieBenite.getPositionAnimationY());
        for (int i = 0; i < 8; i++) {
            int gx = x + rand.nextInt(size - 6) + 3;
            int gy = y + size / 3 + rand.nextInt(size * 2 / 3 - 6);
            int r = 2 + rand.nextInt(2);
            g2.setColor(new Color(255, 215, 60, 220));
            int[] lxp = {gx, gx + r, gx - r};
            int[] lyp = {gy - r * 3, gy + r, gy + r};
            g2.fillPolygon(lxp, lyp, 3);
            g2.fillOval(gx - r, gy - r, r * 2, r * 2);
            g2.setColor(new Color(255, 250, 200, 180));
            g2.fillOval(gx - 1, gy - r * 2, 2, 2);
        }

        // Étoiles scintillantes aux coins
        int[][] stars = {{x + size/8, y + size*2/5}, {x + size*7/8, y + size*2/5},
                         {x + size/6, y + size*3/4}, {x + size*5/6, y + size*3/4}};
        for (int[] s : stars) {
            g2.setColor(new Color(255, 240, 130, 210));
            g2.fillOval(s[0] - 2, s[1] - 2, 5, 5);
            g2.setStroke(new BasicStroke(1.2f, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
            g2.setColor(new Color(255, 240, 130, 130));
            g2.drawLine(s[0] - 5, s[1], s[0] + 5, s[1]);
            g2.drawLine(s[0], s[1] - 5, s[0], s[1] + 5);
        }

        // Nuage doré lumineux
        g2.setColor(new Color(220, 185, 70, 210));
        g2.fillOval(x + size / 8,     y + size / 3, size / 3, size / 4);
        g2.fillOval(x + size / 3,     y + size / 5, size / 2, size / 2);
        g2.fillOval(x + size / 2,     y + size / 3, size / 3, size / 4);
        g2.setColor(new Color(255, 245, 200, 180));
        g2.fillOval(x + size / 3 + 4, y + size / 4, size * 3 / 10, size / 7);

        g2.dispose();
    }
    
    public void paint(Zephyr zephyr, Graphics graphics) {
        int size = config.GameConfiguration.TAILLE_BLOC;
        int x = (int) (zephyr.getPositionAnimationX() * size);
        int y = (int) (zephyr.getPositionAnimationY() * size);
        Graphics2D g2 = (Graphics2D) graphics.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        // Fond vert très léger et aéré
        g2.setColor(new Color(180, 255, 200, 45));
        g2.fillRect(x, y, size, size);

        // Courbe 1 – brise principale (verte)
        g2.setStroke(new BasicStroke(2.5f, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
        g2.setColor(new Color(70, 185, 110, 215));
        g2.draw(new QuadCurve2D.Float(
            x + size / 10,     y + size / 3,
            x + size / 2,      y + size / 7,
            x + size * 9 / 10, y + size / 3));

        // Courbe 2 – brise secondaire (cyan-vert)
        g2.setColor(new Color(90, 210, 145, 185));
        g2.draw(new QuadCurve2D.Float(
            x + size / 8,      y + size / 2,
            x + size / 2,      y + size * 2 / 3,
            x + size * 7 / 8,  y + size / 2));

        // Courbe 3 – légère, basse
        g2.setStroke(new BasicStroke(1.5f, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
        g2.setColor(new Color(130, 225, 160, 145));
        g2.draw(new QuadCurve2D.Float(
            x + size / 5,      y + size * 2 / 3,
            x + size / 2,      y + size * 5 / 6,
            x + size * 4 / 5,  y + size * 2 / 3));

        // Petites feuilles portées par le vent
        Random rand = new Random((int)zephyr.getPositionAnimationX() * 11 + (int)zephyr.getPositionAnimationY());
        g2.setColor(new Color(90, 190, 110, 190));
        for (int i = 0; i < 5; i++) {
            int lx = x + rand.nextInt(size - 8);
            int ly = y + rand.nextInt(size - 8);
            g2.fillOval(lx, ly, 4, 6);
        }

        g2.dispose();
    }
    
    public void paint(Tonnerre tonnerre, Graphics graphics) {
        int size = config.GameConfiguration.TAILLE_BLOC;
        int x = (int) (tonnerre.getPositionAnimationX() * size);
        int y = (int) (tonnerre.getPositionAnimationY() * size);
        Graphics2D g2 = (Graphics2D) graphics.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        // Fond orageux bleu-noir
        g2.setColor(new Color(15, 15, 55, 160));
        g2.fillRect(x, y, size, size);

        // Nuage d'orage épais et sombre
        g2.setColor(new Color(65, 65, 90, 230));
        g2.fillOval(x + size / 10,     y + size / 10, size * 3 / 5, size / 3);
        g2.fillOval(x + size / 4,      y + size / 20, size / 2,     size / 3);
        g2.fillOval(x + size * 2 / 5,  y + size / 8,  size * 3 / 5, size / 3);

        // Coordonnées de la foudre
        int bx  = x + size * 2 / 5;
        int by0 = y + size / 3;
        int bx1 = bx  - size / 6,  by1 = by0 + size / 4;
        int bx2 = bx  + size / 8,  by2 = by1 + size / 5;
        int bx3 = bx  - size / 12, by3 = y + size - size / 8;

        // Halo lumineux (glow)
        g2.setColor(new Color(255, 255, 160, 45));
        g2.setStroke(new BasicStroke(14f, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
        g2.drawLine(bx, by0, bx1, by1);
        g2.drawLine(bx1, by1, bx2, by2);
        g2.drawLine(bx2, by2, bx3, by3);

        // Foudre jaune épaisse
        g2.setColor(new Color(255, 240, 80, 235));
        g2.setStroke(new BasicStroke(4f, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
        g2.drawLine(bx, by0, bx1, by1);
        g2.drawLine(bx1, by1, bx2, by2);
        g2.drawLine(bx2, by2, bx3, by3);

        // Cœur blanc brillant (fine surbrillance)
        g2.setColor(new Color(255, 255, 255, 210));
        g2.setStroke(new BasicStroke(1.5f, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
        g2.drawLine(bx, by0, bx1, by1);
        g2.drawLine(bx1, by1, bx2, by2);
        g2.drawLine(bx2, by2, bx3, by3);

        g2.dispose();
    }
    
    public void paint(Smog smog, Graphics graphics) {
        int size = config.GameConfiguration.TAILLE_BLOC;
        int x = (int) (smog.getPositionAnimationX() * size);
        int y = (int) (smog.getPositionAnimationY() * size);
        Graphics2D g2 = (Graphics2D) graphics.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        // Fond brun-gris sombre
        g2.setColor(new Color(55, 42, 22, 130));
        g2.fillRect(x, y, size, size);

        // Couches horizontales de brume — densité croissante vers le bas
        for (int i = 0; i < 5; i++) {
            float t = (float) i / 4f;
            int ly = y + (int)(size * (0.18f + t * 0.65f));
            int alpha = (int)(30 + t * 80);
            int gray = 95 + (int)(t * 30);
            g2.setColor(new Color(gray, gray - 8, gray - 18, alpha));
            g2.fillOval(x - size / 8, ly - size / 10, size + size / 4, (int)(size * 0.18f * (1f + t * 0.5f)));
        }

        // Triangle danger au centre
        int tx = x + size / 2, ty = y + size / 4;
        int tw = size * 2 / 5;
        int[] triX = {tx,          tx - tw / 2, tx + tw / 2};
        int[] triY = {ty,          ty + tw,     ty + tw};
        g2.setColor(new Color(200, 120, 20, 200));
        g2.fillPolygon(triX, triY, 3);
        g2.setColor(new Color(255, 200, 50, 230));
        g2.setStroke(new BasicStroke(1.5f));
        g2.drawPolygon(triX, triY, 3);
        // Point d'exclamation
        g2.setColor(new Color(30, 18, 0, 240));
        g2.setStroke(new BasicStroke(2.5f, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
        g2.drawLine(tx, ty + tw / 4, tx, ty + tw * 3 / 5);
        g2.fillOval(tx - 2, ty + tw * 3 / 4, 4, 4);

        g2.dispose();
    }
    
    public void paint(NuageToxique nuageToxique, Graphics graphics) {
        int size = config.GameConfiguration.TAILLE_BLOC;
        int x = (int) (nuageToxique.getPositionAnimationX() * size);
        int y = (int) (nuageToxique.getPositionAnimationY() * size);
        Graphics2D g2 = (Graphics2D) graphics.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        // Fond vert sombre empoisonné
        g2.setColor(new Color(18, 48, 0, 120));
        g2.fillRect(x, y, size, size);

        // Brume toxique verte au sol
        g2.setColor(new Color(80, 160, 10, 55));
        g2.fillOval(x - size / 6, y + size * 3 / 5, size + size / 3, size / 2);

        // Nuage vert acide multi-boules
        g2.setColor(new Color(95, 155, 15, 210));
        g2.fillOval(x + size / 10,     y + size / 4,  size / 2,     size / 3);
        g2.fillOval(x + size / 3,      y + size / 7,  size / 2,     size * 2 / 5);
        g2.fillOval(x + size * 3 / 5,  y + size / 4,  size * 3 / 8, size / 3);
        g2.fillOval(x + size * 2 / 5,  y + size / 3,  size * 2 / 5, size / 4);
        // Reflet clair
        g2.setColor(new Color(165, 230, 50, 150));
        g2.fillOval(x + size * 2 / 5,  y + size / 6,  size / 4,     size / 8);

        // Bulles toxiques flottantes
        Random rand = new Random((int)nuageToxique.getPositionAnimationX() * 13 + (int)nuageToxique.getPositionAnimationY());
        for (int i = 0; i < 6; i++) {
            int bx = x + rand.nextInt(size - 8) + 4;
            int by = y + size / 2 + rand.nextInt(size / 2 - 6);
            int br = 3 + rand.nextInt(4);
            g2.setColor(new Color(140, 215, 30, 50));
            g2.fillOval(bx - br, by - br, br * 2, br * 2);
            g2.setColor(new Color(160, 230, 40, 140));
            g2.setStroke(new BasicStroke(1.2f));
            g2.drawOval(bx - br, by - br, br * 2, br * 2);
            // Reflet bulle
            g2.setColor(new Color(200, 255, 100, 120));
            g2.fillOval(bx - br / 2, by - br / 2, br / 2, br / 2);
        }

        // Crâne simplifié au centre du nuage
        int skX = x + size / 2, skY = y + size * 3 / 10;
        int skR = size / 9;
        // Crâne - calotte
        g2.setColor(new Color(200, 245, 50, 195));
        g2.fillOval(skX - skR, skY - skR, skR * 2, (int)(skR * 1.7));
        // Mâchoire
        g2.fillRect(skX - skR * 2 / 3, skY + skR / 2, skR * 4 / 3, skR * 3 / 4);
        // Yeux
        g2.setColor(new Color(18, 48, 0, 230));
        g2.fillOval(skX - skR / 2 - 1, skY - skR / 4, skR / 2, skR / 2);
        g2.fillOval(skX + 1,            skY - skR / 4, skR / 2, skR / 2);
        // Dents
        g2.setColor(new Color(18, 48, 0, 230));
        int toothW = skR * 4 / 3 / 3;
        for (int i = 0; i < 3; i++) {
            g2.fillRect(skX - skR * 2 / 3 + i * toothW + 1, skY + skR / 2, toothW - 1, skR / 2);
        }

        g2.dispose();
    }
    
    public void paintDangerZone(Evenement e, Graphics graphics) 
    {    
        Bloc position = e.getPosition();
        int size = config.GameConfiguration.TAILLE_BLOC;
        int x = position.getX() * size;
        int y = position.getY() * size;
        
        graphics.setColor(new Color(0xfb8500));
        graphics.fillRect(x, y, size, size);
    }
    

}
package gui;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.geom.Path2D;
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
import moteur.donne.evenement.mobile.Grele;
import moteur.donne.evenement.mobile.NuageToxique;
import moteur.donne.evenement.mobile.Orage;
import moteur.donne.evenement.mobile.Pluie;
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

public class StrategiePeinture 
{
    private Image danger;
    private Image meteore;
    
    public StrategiePeinture() {
        try {
            this.danger = new ImageIcon(getClass().getResource("/image/danger.png")).getImage();
            this.meteore = new ImageIcon(getClass().getResource("/image/meteore.png")).getImage();
        } catch (Exception e) {
            this.danger = null;
            this.meteore = null;
        }
    } 

    private void dessinerNuage(Graphics2D g2, int x, int y, int size) {
        g2.setColor(new Color(210, 220, 228, 220));
        g2.fillOval(x + size / 8, y + size / 3, size / 3, size / 4);
        g2.fillOval(x + size / 3, y + size / 5, size / 2, size / 2);
        g2.fillOval(x + size / 2, y + size / 3, size / 3, size / 4);
        g2.setColor(new Color(240, 245, 250, 180));
        g2.fillOval(x + size / 3, y + size / 4, size / 3, size / 5);
    }

    private void dessinerPollution(Graphics2D g2, int x, int y, int size) {
        g2.setColor(new Color(70, 70, 70, 150));
        g2.fillOval(x + size / 10, y + size / 4, size / 2, size / 2);
        g2.fillOval(x + size / 3, y + size / 8, size / 2, size / 2);
        g2.fillOval(x + size / 2, y + size / 3, size / 3, size / 3);
        g2.setColor(new Color(110, 110, 110, 100));
        g2.fillOval(x + size / 4, y + size / 6, size / 2, size / 2);
    }

    private void dessinerFeuille(Graphics2D g2, int x, int y, int size) {
        g2.setColor(new Color(52, 140, 78));
        g2.fillOval(x + size / 4, y + size / 5, size / 2, (size * 3) / 5);
        g2.setColor(new Color(84, 190, 104));
        g2.fillOval(x + size / 3, y + size / 4, size / 4, size / 3);
        g2.setColor(new Color(210, 245, 210, 180));
        g2.setStroke(new BasicStroke(2f));
        g2.drawLine(x + size / 2, y + size / 4, x + size / 2, y + (size * 3) / 4);
        g2.drawLine(x + size / 2, y + size / 2, x + (size * 2) / 3, y + size / 3);
        g2.drawLine(x + size / 2, y + (size * 3) / 5, x + size / 3, y + size / 2);
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

        // 4 ondes de chaleur horizontales ondulÃ©es
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


    private void dessinerTornade(Graphics2D g2, int x, int y, int size) {
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        Color contour = new Color(33, 49, 86, 235);
        Color corps = new Color(247, 247, 243, 215);
        Color ombre = new Color(166, 176, 190, 110);
        Color reflet = new Color(255, 255, 255, 95);

        float epaisseurContour = Math.max(2.4f, size / 9f);
        float epaisseurInterne = Math.max(1.2f, size / 18f);

        Path2D.Float silhouette = new Path2D.Float();
        silhouette.moveTo(x + size * 0.31f, y + size * 0.18f);
        silhouette.curveTo(x + size * 0.14f, y + size * 0.23f, x + size * 0.13f, y + size * 0.35f,
            x + size * 0.26f, y + size * 0.42f);
        silhouette.curveTo(x + size * 0.09f, y + size * 0.51f, x + size * 0.15f, y + size * 0.64f,
            x + size * 0.34f, y + size * 0.69f);
        silhouette.curveTo(x + size * 0.20f, y + size * 0.76f, x + size * 0.26f, y + size * 0.85f,
            x + size * 0.42f, y + size * 0.87f);
        silhouette.curveTo(x + size * 0.37f, y + size * 0.93f, x + size * 0.41f, y + size * 0.99f,
            x + size * 0.50f, y + size * 0.97f);
        silhouette.curveTo(x + size * 0.57f, y + size * 0.94f, x + size * 0.63f, y + size * 0.88f,
            x + size * 0.58f, y + size * 0.80f);
        silhouette.curveTo(x + size * 0.76f, y + size * 0.76f, x + size * 0.87f, y + size * 0.61f,
            x + size * 0.69f, y + size * 0.49f);
        silhouette.curveTo(x + size * 0.85f, y + size * 0.40f, x + size * 0.84f, y + size * 0.25f,
            x + size * 0.63f, y + size * 0.18f);
        silhouette.curveTo(x + size * 0.53f, y + size * 0.08f, x + size * 0.40f, y + size * 0.10f,
            x + size * 0.31f, y + size * 0.18f);
        silhouette.closePath();

        g2.setColor(ombre);
        g2.fillOval(Math.round(x + size * 0.23f), Math.round(y + size * 0.27f), Math.round(size * 0.38f),
            Math.round(size * 0.52f));

        g2.setColor(corps);
        g2.fill(silhouette);

        g2.setColor(contour);
        g2.setStroke(new BasicStroke(epaisseurContour, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
        g2.drawOval(Math.round(x + size * 0.17f), Math.round(y + size * 0.05f), Math.round(size * 0.69f),
            Math.round(size * 0.24f));
        g2.draw(silhouette);

        g2.setStroke(new BasicStroke(epaisseurInterne, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
        g2.drawOval(Math.round(x + size * 0.28f), Math.round(y + size * 0.09f), Math.round(size * 0.44f),
            Math.round(size * 0.10f));

        g2.setColor(contour);
        g2.setStroke(new BasicStroke(epaisseurContour * 0.75f, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
        g2.drawArc(Math.round(x + size * 0.16f), Math.round(y + size * 0.24f), Math.round(size * 0.58f),
            Math.round(size * 0.16f), 190, 160);
        g2.drawArc(Math.round(x + size * 0.18f), Math.round(y + size * 0.43f), Math.round(size * 0.46f),
            Math.round(size * 0.13f), 190, 160);
        g2.drawArc(Math.round(x + size * 0.25f), Math.round(y + size * 0.64f), Math.round(size * 0.28f),
            Math.round(size * 0.10f), 190, 165);

        g2.setColor(reflet);
        g2.setStroke(new BasicStroke(epaisseurInterne * 0.9f, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
        g2.drawArc(Math.round(x + size * 0.28f), Math.round(y + size * 0.29f), Math.round(size * 0.28f),
            Math.round(size * 0.08f), 200, 110);
        g2.drawArc(Math.round(x + size * 0.29f), Math.round(y + size * 0.49f), Math.round(size * 0.20f),
            Math.round(size * 0.06f), 200, 110);
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

		g2.setColor(new Color(50, 30, 20, 90));
		g2.fillRect(x, y, size, size);
		dessinerPollution(g2, x, y, size);
		
		g2.setColor(new Color(30, 20, 10, 150));
		g2.setStroke(new BasicStroke(2f));
		g2.drawRoundRect(x + 2, y + 2, size - 4, size - 4, 5, 5);
		
		g2.dispose();
	}

    public void paint(VentChaud ventChaud, Graphics graphics)
    {
        int size = config.GameConfiguration.TAILLE_BLOC;
        int x = (int) (ventChaud.getPositionAnimationX() * size);
        int y = (int) (ventChaud.getPositionAnimationY() * size);
        Graphics2D g2 = (Graphics2D) graphics.create();

        // Fond chaud orangÃ©
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

        // Petits flocons secondaires dispersÃ©s
        Random rand = new Random((int)froid.getPositionAnimationX() * 3 + (int)froid.getPositionAnimationY());
        for (int i = 0; i < 3; i++) {
            int fx = x + rand.nextInt(size - 12) + 6;
            int fy = y + rand.nextInt(size - 12) + 6;
            dessinerFlocon(g2, fx, fy, size / 9);
        }

        // TraÃ®nÃ©es de vent froid
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
        
        g2.setColor(new Color(0, 80, 40, 35));
        g2.fillRect(x, y, size, size);

        dessinerFeuille(g2, x, y, size);
        g2.dispose();
    }
    
    public void paintDanger(Evenement e,Graphics graphics) {
    	int size = config.GameConfiguration.TAILLE_BLOC;
        int x = (int) (e.getPositionAnimationX() * size);
        int y = (int) (e.getPositionAnimationY() * size);
        if (danger != null) {
            graphics.drawImage(danger, x, y, size*2, size*2, null);
        } else {
            graphics.setColor(Color.RED);
            graphics.fillRect(x, y, size, size);
        }
        
    }
    
    public void paint(Meteore meteore,Graphics graphics) {
    	int size = config.GameConfiguration.TAILLE_BLOC;
        int x = (int) (meteore.getPositionAnimationX() * size);
        int y = (int) (meteore.getPositionAnimationY() * size);
        if (this.meteore != null) {
            graphics.drawImage(this.meteore, x, y, size, size, null);
        } else {
            graphics.setColor(Color.ORANGE);
            graphics.fillOval(x, y, size, size);
        }
    }
    
public void paint(Orage orage, Graphics graphics) {
        int size = config.GameConfiguration.TAILLE_BLOC;
        int x = (int) (orage.getPositionAnimationX() * size);
        int y = (int) (orage.getPositionAnimationY() * size);
        Graphics2D g2 = (Graphics2D) graphics.create();
        
        // Fond de l'orage - sombre
        g2.setColor(new Color(40, 45, 70, 60));
        g2.fillRect(x, y, size, size);
        
        // Nuages d'orage - couches sombres
        // Couche arriÃ¨re foncÃ©e
        g2.setColor(new Color(30, 30, 55, 220));
        g2.fillOval(x + size/8, y + size/6, size - size/4, size/2 + 4);
        g2.fillOval(x + size/3, y + size/8, size/2 + 8, size/2);
        
        // Couche intermÃ©diaire
        g2.setColor(new Color(60, 60, 90, 200));
        g2.fillOval(x + size/6, y + size/5, size/2 + 6, size/3);
        g2.fillOval(x + size/3, y + size/6, size/2, size/2);
        g2.fillOval(x + size/2 - 2, y + size/4, size/3, size/3);
        
        // Couche Ã©claircie
        g2.setColor(new Color(90, 90, 120, 180));
        g2.fillOval(x + size/4, y + size/4, size/3, size/4);
        
        // Ã‰clair principal - jaune/or vif
        Random rand = new Random((int)orage.getPositionAnimationX() * 31 + (int)orage.getPositionAnimationY());
        int lightningX = x + size/3 + rand.nextInt(size/3);
        
        g2.setStroke(new BasicStroke(3f));
        g2.setColor(new Color(255, 255, 180, 230));
        g2.drawLine(lightningX, y + size/3, lightningX - 6, y + size/3 + 12);
        g2.drawLine(lightningX - 6, y + size/3 + 12, lightningX + 4, y + size/3 + 22);
        g2.drawLine(lightningX + 4, y + size/3 + 22, lightningX - 3, y + size);
        
        // DeuxiÃ¨me Ã©clair secondaire
        int lightningX2 = x + size/2 + rand.nextInt(size/4);
        g2.setStroke(new BasicStroke(2f));
        g2.setColor(new Color(255, 255, 220, 180));
        g2.drawLine(lightningX2, y + size/3 + 5, lightningX2 - 4, y + size/3 + 15);
        g2.drawLine(lightningX2 - 4, y + size/3 + 15, lightningX2 + 3, y + size/2 + 8);
        
        // Reflet d'Ã©clair sur les bords du nuage
        g2.setColor(new Color(255, 255, 150, 60));
        g2.fillOval(x + size/4, y + size/3, size/6, size/6);
        
        // Bordure
        g2.setColor(new Color(50, 55, 80, 160));
        g2.setStroke(new BasicStroke(2.5f));
        g2.drawRect(x + 1, y + 1, size - 2, size - 2);
        
        g2.dispose();
    }
    
    public void paint(Grele grele, Graphics graphics) {
        int size = config.GameConfiguration.TAILLE_BLOC;
        int x = (int) (grele.getPositionAnimationX() * size);
        int y = (int) (grele.getPositionAnimationY() * size);
        Graphics2D g2 = (Graphics2D) graphics.create();
        
        g2.setColor(new Color(200, 200, 255, 50));
        g2.fillRect(x, y, size, size);
        
        g2.setColor(Color.WHITE);
        Random rand = new Random((int)grele.getPositionAnimationX() * 31 + (int)grele.getPositionAnimationY());
        for (int i = 0; i < 12; i++) {
            int gx = x + rand.nextInt(size);
            int gy = y + rand.nextInt(size);
            g2.fillOval(gx, gy, 4, 4);
        }
        
        dessinerNuage(g2, x, y, size);
        g2.dispose();
    }
    
    public void paint(Tornade tornade, Graphics graphics) {
        int size = config.GameConfiguration.TAILLE_BLOC;
        int x = (int) (tornade.getPositionAnimationX() * size);
        int y = (int) (tornade.getPositionAnimationY() * size);
        Graphics2D g2 = (Graphics2D) graphics.create();
        
        g2.setColor(new Color(75, 80, 90, 35));
        g2.fillRect(x, y, size, size);

        dessinerTornade(g2, x, y, size);
        
        g2.dispose();
    }
    
    public void paint(PluieBenite pluieBenite, Graphics graphics) {
        int size = config.GameConfiguration.TAILLE_BLOC;
        int x = (int) (pluieBenite.getPositionAnimationX() * size);
        int y = (int) (pluieBenite.getPositionAnimationY() * size);
        Graphics2D g2 = (Graphics2D) graphics.create();
        
        g2.setColor(new Color(100, 200, 255, 60));
        g2.fillRect(x, y, size, size);
        
        g2.setColor(new Color(200, 255, 255, 200));
        Random rand = new Random((int)pluieBenite.getPositionAnimationX() + (int)pluieBenite.getPositionAnimationY());
        for (int i = 0; i < 10; i++) {
            int gx = x + rand.nextInt(size);
            int gy = y + rand.nextInt(size);
            g2.drawLine(gx, gy, gx - 2, gy + 8);
        }
        
        g2.setColor(new Color(255, 215, 0, 150));
        g2.fillOval(x + size/4, y + size/4, size/2, size/2);
        
        g2.setColor(new Color(255, 255, 200, 80));
        g2.setStroke(new BasicStroke(2.5f));
        g2.drawOval(x + size/6, y + size/6, size*2/3, size*2/3);
        
        g2.setColor(new Color(255, 255, 150, 40));
        g2.drawOval(x + size/8, y + size/8, size*3/4, size*3/4);
        
        dessinerNuage(g2, x, y, size);
        g2.dispose();
    }
    
    public void paint(Zephyr zephyr, Graphics graphics) {
        int size = config.GameConfiguration.TAILLE_BLOC;
        int x = (int) (zephyr.getPositionAnimationX() * size);
        int y = (int) (zephyr.getPositionAnimationY() * size);
        Graphics2D g2 = (Graphics2D) graphics.create();
        
        g2.setColor(new Color(180, 240, 180, 60));
        g2.fillRect(x, y, size, size);
        
        g2.setStroke(new BasicStroke(2.5f, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
        g2.setColor(new Color(150, 220, 150, 180));
        
        int baseY = y + size/2;
        g2.drawLine(x + size/6, baseY, x + (size*5)/6, baseY);
        g2.drawArc(x + size/10, y + size/4, size/4, size/3, 270, 180);
        g2.drawArc(x + size/2, y + size/4, size/4, size/3, 270, 180);
        
        g2.setStroke(new BasicStroke(1.5f));
        g2.setColor(new Color(200, 255, 200, 120));
        g2.drawLine(x + size/4, y + size/4, x + size/2, y + size/4 + 3);
        
        g2.dispose();
    }
    
    public void paint(Tonnerre tonnerre, Graphics graphics) {
        int size = config.GameConfiguration.TAILLE_BLOC;
        int x = (int) (tonnerre.getPositionAnimationX() * size);
        int y = (int) (tonnerre.getPositionAnimationY() * size);
        Graphics2D g2 = (Graphics2D) graphics.create();
        
        g2.setColor(new Color(40, 40, 80, 100));
        g2.fillRect(x, y, size, size);
        
        g2.setColor(new Color(255, 255, 220, 230));
        g2.fillOval(x + size/4, y + size/4, size/2, size/2);
        
        g2.setColor(new Color(255, 255, 150, 220));
        int startX = x + size/3;
        int startY = y + size/2;
        g2.setStroke(new BasicStroke(4));
        g2.drawLine(startX, startY, startX - 10, startY + 15);
        g2.drawLine(startX - 10, startY + 15, startX + 6, startY + 28);
        g2.drawLine(startX + 6, startY + 28, startX - 4, startY + size);
        
        g2.setColor(new Color(255, 255, 255, 100));
        g2.setStroke(new BasicStroke(2));
        g2.drawLine(startX - 8, startY + 10, startX + 4, startY + 20);
        
        g2.dispose();
    }
    
    public void paint(Smog smog, Graphics graphics) {
        int size = config.GameConfiguration.TAILLE_BLOC;
        int x = (int) (smog.getPositionAnimationX() * size);
        int y = (int) (smog.getPositionAnimationY() * size);
        Graphics2D g2 = (Graphics2D) graphics.create();
        
        g2.setColor(new Color(90, 70, 50, 120));
        g2.fillRect(x, y, size, size);
        
        g2.setColor(new Color(110, 90, 60, 180));
        g2.fillOval(x + size/8, y + size/4, size/2, size/2);
        g2.fillOval(x + size/3, y + size/6, size/2, size/2);
        g2.fillOval(x + size/2, y + size/3, size/3, size/3);
        
        g2.setColor(new Color(80, 60, 40, 200));
        g2.setStroke(new BasicStroke(2f));
        g2.drawRoundRect(x + 2, y + 2, size - 4, size - 4, 5, 5);
        
        Random randSmog = new Random((int)smog.getPositionAnimationX() * 7 + (int)smog.getPositionAnimationY());
        g2.setColor(new Color(60, 50, 40, 100));
        for (int i = 0; i < 4; i++) {
            int px = x + randSmog.nextInt(size - 4);
            int py = y + randSmog.nextInt(size - 4);
            g2.fillOval(px, py, 3, 3);
        }
        
        g2.dispose();
    }
    
    public void paint(NuageToxique nuageToxique, Graphics graphics) {
        int size = config.GameConfiguration.TAILLE_BLOC;
        int x = (int) (nuageToxique.getPositionAnimationX() * size);
        int y = (int) (nuageToxique.getPositionAnimationY() * size);
        Graphics2D g2 = (Graphics2D) graphics.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        // Fond vert sombre empoisonnÃ©
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

        // CrÃ¢ne simplifiÃ© au centre du nuage
        int skX = x + size / 2, skY = y + size * 3 / 10;
        int skR = size / 9;
        // CrÃ¢ne - calotte
        g2.setColor(new Color(200, 245, 50, 195));
        g2.fillOval(skX - skR, skY - skR, skR * 2, (int)(skR * 1.7));
        // MÃ¢choire
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
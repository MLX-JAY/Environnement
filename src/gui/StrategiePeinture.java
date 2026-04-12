package gui;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
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

    private void dessinerVentChaud(Graphics2D g2, int x, int y, int size) {
        g2.setStroke(new BasicStroke(3f, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
        g2.setColor(new Color(210, 35, 35, 220));

        int baseY = y + (size * 4) / 5;
        g2.drawLine(x + size / 6, baseY, x + (size * 5) / 6, baseY);

        g2.drawArc(x + size / 10, y + size / 8, size / 4, (size * 5) / 8, 280, 190);
        g2.drawArc(x + (size * 7) / 20, y + size / 8, size / 4, (size * 5) / 8, 280, 190);
        g2.drawArc(x + (size * 3) / 5, y + size / 8, size / 4, (size * 5) / 8, 280, 190);

        g2.setColor(new Color(255, 110, 110, 120));
        g2.drawArc(x + size / 8, y + size / 5, size / 5, size / 2, 285, 170);
        g2.drawArc(x + (size * 2) / 5, y + size / 5, size / 5, size / 2, 285, 170);
        g2.drawArc(x + (size * 13) / 20, y + size / 5, size / 5, size / 2, 285, 170);
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
        
        g2.setColor(new Color(0, 0, 50, 50));
        g2.fillRect(x, y, size, size);

        g2.setColor(new Color(200, 230, 255)); 
        Random rand = new Random((int)pluie.getPositionAnimationX() + (int)pluie.getPositionAnimationY());
        
        for (int i = 0; i < 8; i++) {
            int gx = x + rand.nextInt(size);
            int gy = y + rand.nextInt(size);
            g2.drawLine(gx, gy, gx - 2, gy + 8); 
        }

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

        g2.setColor(new Color(120, 0, 0, 28));
        g2.fillRect(x, y, size, size);
        dessinerVentChaud(g2, x, y, size);
        g2.dispose();
    }
    
    public void paint(VentFroid froid, Graphics graphics) 
    {
        int size = config.GameConfiguration.TAILLE_BLOC;
        int x = (int) (froid.getPositionAnimationX() * size);
        int y = (int) (froid.getPositionAnimationY() * size);
        
        graphics.setColor(new Color(0, 0, 50, 50));
        graphics.fillRect(x, y, size, size);

        graphics.drawImage(flocon, x, y, size, size, null);
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
        
        g2.setColor(new Color(50, 50, 80, 60));
        g2.fillRect(x, y, size, size);
        
        g2.setColor(new Color(255, 255, 200, 180));
        g2.fillOval(x + size/4, y + size/4, size/2, size/2);
        
        g2.setColor(new Color(100, 100, 150, 150));
        for (int i = 0; i < 5; i++) {
            int lightningX = x + rand.nextInt(size);
            int startY = y + size/4;
            int endY = y + size;
            g2.setStroke(new BasicStroke(2));
            g2.drawLine(lightningX, startY, lightningX - 5, startY + 10);
            g2.drawLine(lightningX - 5, startY + 10, lightningX + 3, startY + 20);
            g2.drawLine(lightningX + 3, startY + 20, lightningX - 2, endY);
        }
        
        dessinerNuage(g2, x, y, size);
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
        Random rand = new Random((int)grele.getPositionAnimationX() + (int)grele.getPositionAnimationY());
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
        
        g2.setColor(new Color(80, 80, 90, 50));
        g2.fillRect(x, y, size, size);
        
        g2.setColor(new Color(150, 150, 160, 200));
        int centerX = x + size/2;
        int centerY = y + size/2;
        
        for (int i = 0; i < 4; i++) {
            int offset = 8 * (i + 1);
            g2.drawArc(centerX - offset, centerY - offset/2, offset*2, offset, 0, 180);
        }
        
        g2.setColor(new Color(200, 200, 210, 150));
        g2.fillOval(x + size/4, y + size/4, size/2, size/3);
        
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
        
        g2.setColor(new Color(100, 140, 30, 100));
        g2.fillRect(x, y, size, size);
        
        g2.setColor(new Color(140, 180, 40, 180));
        g2.fillOval(x + size/8, y + size/4, size/2, size/2);
        g2.fillOval(x + size/3, y + size/6, size/2, size/2);
        g2.fillOval(x + size/2, y + size/3, size/3, size/3);
        
        g2.setColor(new Color(180, 220, 60, 150));
        g2.drawArc(x + size/4, y + size/2, size/2, size/4, 0, 180);
        
        g2.setColor(new Color(200, 255, 0, 100));
        g2.setStroke(new BasicStroke(2f));
        g2.drawRoundRect(x + 2, y + 2, size - 4, size - 4, 5, 5);
        
        Random randTox = new Random((int)nuageToxique.getPositionAnimationX() * 13 + (int)nuageToxique.getPositionAnimationY());
        g2.setColor(new Color(150, 200, 20, 120));
        for (int i = 0; i < 5; i++) {
            int px = x + randTox.nextInt(size - 4);
            int py = y + randTox.nextInt(size - 4);
            g2.fillOval(px, py, 4, 4);
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
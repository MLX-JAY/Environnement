package gui;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.util.Random;

import javax.swing.ImageIcon;

import moteur.donne.biome.Banquise;
import moteur.donne.biome.Biome;
import moteur.donne.biome.Desert;
import moteur.donne.biome.Foret;
import moteur.donne.biome.Mer;
import moteur.donne.biome.Montagne;
import moteur.donne.biome.Village;
import moteur.donne.biome.Ville;
import moteur.donne.carte.Bloc;
import moteur.donne.evenement.mobile.Pluie;

public class StrategiePeinture 
{
    private Image nuage = new ImageIcon(getClass().getResource("/image/pluie.png")).getImage();
    
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

    public void paint(Pluie pluie, Graphics graphics) 
    {
        int size = config.GameConfiguration.TAILLE_BLOC;
        int x = pluie.getPosition().getX() * size;
        int y = pluie.getPosition().getY() * size;
        
        graphics.setColor(new Color(0, 0, 50, 50));
        graphics.fillRect(x, y, size, size);

        graphics.setColor(new Color(200, 230, 255)); 
        Random rand = new Random(x + y);
        
        for (int i = 0; i < 8; i++) {
            int gx = x + rand.nextInt(size);
            int gy = y + rand.nextInt(size);
            graphics.drawLine(gx, gy, gx - 2, gy + 8); 
        }

        graphics.drawImage(nuage, x, y, size, size, null);
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
    
}
package gui;

import java.awt.Color;
import java.awt.Graphics;
import java.util.Random;

import moteur.donne.biome.Banquise;
import moteur.donne.biome.Desert;
import moteur.donne.biome.Foret;
import moteur.donne.biome.Mer;
import moteur.donne.biome.Montagne;
import moteur.donne.biome.Village;
import moteur.donne.biome.Ville;
import moteur.donne.carte.Bloc;

public class StrategiePeinture 
{
	public void paint(Foret foret, Graphics graphics) 
	{    
			Bloc position = foret.getPosition();
		    int size = config.GameConfiguration.TAILLE_BLOC;
		    int x = position.getX() * size;
		    int y = position.getY() * size;
		    
		    graphics.setColor(new Color(0x1F362F)); //vert foncé
		    graphics.fillRect(x, y, size, size);
		    graphics.setColor(graphics.getColor().darker());
		    graphics.drawRect(x, y, size, size);
	}
	
	public void paint(Desert desert, Graphics graphics) 
	{    
			Bloc position = desert.getPosition();
		    int size = config.GameConfiguration.TAILLE_BLOC;
		    int x = position.getX() * size;
		    int y = position.getY() * size;
		    
		    graphics.setColor(new Color(0xF1EDD0));
		    graphics.fillRect(x, y, size, size);
		    graphics.setColor(graphics.getColor().darker());
		    graphics.drawRect(x, y, size, size);
		    
		    Random rand = new Random(position.getX() * 31 + position.getY());
		    
		    graphics.setColor(new Color(0xFFFFFF));
		    for (int i = 0; i < 1000; i++) {
		        int xGrain = x + rand.nextInt(size);
		        int yGrain = y + rand.nextInt(size);
		        graphics.fillRect(xGrain, yGrain, 1, 1);
		    }
		    
		    rand = new Random(position.getX() * 30 + position.getY());
		    
		    graphics.setColor(new Color(0xD0C962));
		    for (int i = 0; i < 1000; i++) {
		        int xGrain = x + rand.nextInt(size);
		        int yGrain = y + rand.nextInt(size);
		        graphics.fillRect(xGrain, yGrain, 1, 1);
		    }
		    
		    rand = new Random(position.getX() * 20 + position.getY());
		    
		    graphics.setColor(new Color(0x3FA249));
		    for (int i = 0; i < 20; i++) {
		        int xGrain = x + rand.nextInt(size);
		        int yGrain = y + rand.nextInt(size);
		        int longe = rand.nextInt(2, 4);
		        graphics.fillRect(xGrain, yGrain, longe, longe*4);
		    }
		    
		    
	}
	
	public void paint(Mer mer, Graphics graphics) 
	{    
		Bloc position = mer.getPosition();
	    int size = config.GameConfiguration.TAILLE_BLOC;
	    int x = position.getX() * size;
	    int y = position.getY() * size;
	    
	    // 1. Fond bleu (Ta couleur de base)
	    graphics.setColor(new Color(0x4FA3E0)); // Un bleu mer
	    graphics.fillRect(x, y, size, size);

	    // 2. Random fixe pour que les reflets ne bougent pas tout seuls
	    Random rand = new Random(position.getX() * 31 + position.getY());
	    
	    // 3. Dessiner des "reflets" (petites vagues claires)
	    graphics.setColor(new Color(255, 255, 255, 100)); // Blanc avec transparence (alpha 100)
	    
	    int nbReflets = 50; // Peu de reflets pour ne pas surcharger
	    for (int i = 0; i < nbReflets; i++) {
	        // Position aléatoire dans la case
	        int xReflet = x + rand.nextInt(size - 10);
	        int yReflet = y + rand.nextInt(size - 5);
	        int largeurReflet = rand.nextInt(5, 12); // Longueur de la petite vague
	        
	        // Dessine un petit trait horizontal un peu épais (2px)
	        graphics.fillRoundRect(xReflet, yReflet, largeurReflet, 2, 2, 2);
	    }
	}
	
	public void paint(Banquise banquise, Graphics graphics) 
	{    
			Bloc position = banquise.getPosition();
		    int size = config.GameConfiguration.TAILLE_BLOC;
		    int x = position.getX() * size;
		    int y = position.getY() * size;
		    
		    graphics.setColor(Color.CYAN);
		    graphics.fillRect(x, y, size, size);
		    graphics.setColor(graphics.getColor().darker());
		    graphics.drawRect(x, y, size, size);
	}
	
	public void paint(Montagne montagne, Graphics graphics) 
	{    
			Bloc position = montagne.getPosition();
		    int size = config.GameConfiguration.TAILLE_BLOC;
		    int x = position.getX() * size;
		    int y = position.getY() * size;
		    
		    graphics.setColor(Color.GRAY);
		    graphics.fillRect(x, y, size, size);
		    graphics.setColor(graphics.getColor().darker());
		    graphics.drawRect(x, y, size, size);
	}
	
	public void paint(Village village, Graphics graphics) 
	{    
			Bloc position = village.getPosition();
		    int size = config.GameConfiguration.TAILLE_BLOC;
		    int x = position.getX() * size;
		    int y = position.getY() * size;
		    
		    graphics.setColor(new Color(0x7DCD85)); // vert clair
		    graphics.fillRect(x, y, size, size);
		    graphics.setColor(graphics.getColor().darker());
		    graphics.drawRect(x, y, size, size);
	}
	
	public void paint(Ville ville, Graphics graphics) 
	{    
			Bloc position = ville.getPosition();
		    int size = config.GameConfiguration.TAILLE_BLOC;
		    int x = position.getX() * size;
		    int y = position.getY() * size;
		    
		    graphics.setColor(new Color(0x54273D)); // le violet
		    graphics.fillRect(x, y, size, size);
		    graphics.setColor(graphics.getColor().darker());
		    graphics.drawRect(x, y, size, size);
	}
}
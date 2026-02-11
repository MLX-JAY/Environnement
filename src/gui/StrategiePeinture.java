package gui;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Shape;
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
	public void paint(Foret foret, Graphics graphics) {    
	    Bloc position = foret.getPosition();
	    int size = config.GameConfiguration.TAILLE_BLOC;
	    int x = position.getX() * size;
	    int y = position.getY() * size;
	    
	    Shape oldClip = graphics.getClip(); // Pour pas que sa depasse
	    graphics.setClip(x, y, size, size);
	    
	    //c'est le fond
	    graphics.setColor(new Color(0xd9ed92)); 
	    graphics.fillRect(x, y, size, size);
	    
	    //la je fais les herbes
	    
	    Random rand = new Random(position.getX() * 31 + position.getY());
	    
	    graphics.setColor(new Color(0x52b69a));
	    for (int i = 0; i < 1000; i++) {
	        int xGrain = x + rand.nextInt(size);
	        int yGrain = y + rand.nextInt(size);
	        graphics.fillRect(xGrain, yGrain, 1, 3);
	    }
	    
	    rand = new Random(position.getX() * 15 + position.getY());
	    
	    graphics.setColor(new Color(0x99d98c));
	    for (int i = 0; i < 1000; i++) 
	    {
	        int xGrain = x + rand.nextInt(size);
	        int yGrain = y + rand.nextInt(size);
	        graphics.fillRect(xGrain, yGrain, 1, 3);
	    }
	    
	    
	    for (int i = 0; i < 15; i++) 
	    {
	    	int tx = x + rand.nextInt(size);
	        int ty = y + rand.nextInt(size);
	    	graphics.setColor(new Color(0x6c584c));
	    	graphics.fillRect(tx, ty, 2, 7);
	            
	   
			graphics.setColor(new Color(0x1F362F));
			graphics.fillOval(tx - 3, ty - 4, 8, 8);
	    }
	    
	    graphics.setClip(oldClip);
	    
	    // 4. Bordure
	    graphics.setColor(Color.BLACK);
	    graphics.drawRect(x, y, size, size);
	}
	
	public void paint(Desert desert, Graphics graphics) 
	{    
			Bloc position = desert.getPosition();
		    int size = config.GameConfiguration.TAILLE_BLOC;
		    int x = position.getX() * size;
		    int y = position.getY() * size;
		    
		    Shape oldClip = graphics.getClip(); // Pour pas que sa depasse
		    graphics.setClip(x, y, size, size);
		    
		    graphics.setColor(new Color(0xF1EDD0));
		    graphics.fillRect(x, y, size, size);
		    
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
		    
		    graphics.setClip(oldClip);
		    
		    graphics.setColor(Color.BLACK);
		    graphics.drawRect(x, y, size, size);
		    
	}
	
	public void paint(Mer mer, Graphics graphics) 
	{    
		Bloc position = mer.getPosition();
	    int size = config.GameConfiguration.TAILLE_BLOC;
	    int x = position.getX() * size;
	    int y = position.getY() * size;
	    
	    Shape oldClip = graphics.getClip(); // Pour pas que sa depasse
	    graphics.setClip(x, y, size, size);
	    
	    //le fond en bleu
	    graphics.setColor(new Color(0x4FA3E0));
	    graphics.fillRect(x, y, size, size);

	    //je fais des layer transparent c'est des quart de cercles mis en bas a gauche
	    graphics.setColor(new Color(0, 119, 182, 160)); 
	    graphics.fillOval(x, y + size/3, size*2, size);
	    
	    graphics.setColor(new Color(3, 4, 94,100)); 
	    graphics.fillOval(x, y + size/2, size*2, size);
	    
	    Random rand = new Random(position.getX() * 31 + position.getY());
	    
	    //la c'est des reflet et RoundRect() c'est pour donner un effet vague
	    graphics.setColor(new Color(255, 255, 255, 100)); 
	    
	    int nbReflets = 10;
	    for (int i = 0; i < nbReflets; i++) {
	        int xReflet = x + rand.nextInt(size - 5);
	        int yReflet = y + rand.nextInt(size - 5);
	        int largeurReflet = rand.nextInt(5, 20); // Longueur de la petite vague
	        
	        graphics.fillRoundRect(xReflet, yReflet, largeurReflet, 3, 10, 5);
	    }
	    
	    graphics.setClip(oldClip);
	    //sa c'est la bordure
	    graphics.setColor(Color.BLACK);
	    graphics.drawRect(x, y, size, size);
	    
	    
	}
	
	public void paint(Village village, Graphics graphics) {
	    Bloc position = village.getPosition();
	    int size = config.GameConfiguration.TAILLE_BLOC;
	    int x = position.getX() * size;
	    int y = position.getY() * size;

	    Shape oldClip = graphics.getClip();
	    graphics.setClip(x, y, size, size);

	    graphics.setColor(new Color(0x76c893));
	    graphics.fillRect(x, y, size, size);

	    graphics.setColor(new Color(0xBC8F8F));
	    graphics.fillRect(x + size / 2, y, 8, size);
	    
	    for (int i = 0; i < 3; i++) {
	        int[] espacementX = {x + 10, x + size - 20}; //on dit que horizontalement y'en a un a gauche et un a droite
	        for (int posX : espacementX) {
	            int posY = y + 5 + (i * (size / 3)); //pour celui de gauche et droite on defini le y
	            
	            
	            //la on construit la maison 
	            graphics.setColor(new Color(0x603813)); //on met un rect marron foncé
	            graphics.fillRect(posX, posY + 4, 10, 8);
	            
	            graphics.setColor(new Color(0xAE2012)); //couleur rouge pour le toit
	            
	            //on fait un triangle avec 3 points x et y
	            int[] rx = {posX - 2, posX + 5, posX + 12};
	            int[] ry = {posY + 4, posY, posY + 4};
	            graphics.fillPolygon(rx, ry, 3);
	            
	            //carré clair de la maison
	            graphics.setColor(new Color(0xFED9B7));
	            graphics.fillRect(posX + 2, posY + 6, 6, 6);
	        }
	    }

	    graphics.setClip(oldClip);
	    graphics.setColor(Color.BLACK);
	    graphics.drawRect(x, y, size, size);
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
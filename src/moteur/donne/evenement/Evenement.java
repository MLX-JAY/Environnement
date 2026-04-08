package moteur.donne.evenement;


import config.GameConfiguration;
import moteur.donne.carte.Bloc;
import moteur.processus.visitor.EvenementVisitor;

public abstract class Evenement 
{
	private Bloc position;

	private int directionX; 
    private int directionY;

	private double dureeRestante;
	
	private int impactTemperature;
	
	private int impactHumidite;
	
	private int impactPollution;
	
	private int impactPurification;
	
	// Coordonnées fluides pour l'animation
	private double positionAnimationX;
	private double positionAnimationY;
	private double positionDepartAnimationX;
	private double positionDepartAnimationY;
	private double positionCibleAnimationX;
	private double positionCibleAnimationY;
	private double progressionAnimation = 0.0;
	private long tempsDebutAnimationMs;
	private long dureeAnimationMs;

	private double calculerProgressionAnimationActuelle() {
		if (progressionAnimation >= 1.0) {
			return 1.0;
		}

		long tempsEcouleMs = System.currentTimeMillis() - tempsDebutAnimationMs;
		double duree = Math.max(1L, dureeAnimationMs);
		return Math.min(1.0, tempsEcouleMs / duree);
	}

	

    public Evenement(Bloc position, double dureeRestante, int impactTemperature, int impactHumidite,
			int impactPollution, int impactPurification) 
	{
		super();
		this.position = position;
		this.dureeRestante = dureeRestante;
		this.impactTemperature = impactTemperature;
		this.impactHumidite = impactHumidite;
		this.impactPollution = impactPollution;
		this.impactPurification = impactPurification;
		
		// Initialiser les coordonnées fluides
		this.positionDepartAnimationX = position.getX();
		this.positionDepartAnimationY = position.getY();
		this.positionAnimationX = position.getX();
		this.positionAnimationY = position.getY();
		this.positionCibleAnimationX = position.getX();
		this.positionCibleAnimationY = position.getY();
		this.progressionAnimation = 1.0;
		this.tempsDebutAnimationMs = System.currentTimeMillis();
		this.dureeAnimationMs = Math.max(16, GameConfiguration.VITESSE_JEU);
	}
    
    public int getDirectionX() { return directionX; }
    public int getDirectionY() { return directionY; }
    
    public void setDirection(int dx, int dy) {
        this.directionX = dx;
        this.directionY = dy;
    }


	public double getDureeRestante() {
		return dureeRestante;
	}




	public int getImpactTemperature() {
		return impactTemperature;
	}




	public int getImpactHumidite() {
		return impactHumidite;
	}




	public int getImpactPollution() {
		return impactPollution;
	}




	public int getImpactPurification() {
		return impactPurification;
	}




	public Bloc getPosition() 
	{
		return position;
	}
    

	public double getDuree() 
	{
		return dureeRestante;
	}


    public void setPosition(Bloc position) {
        this.position = position;
    }


    public void setDureeRestante(double dureeRestante) {
        this.dureeRestante = dureeRestante;
    }
    
    // Méthodes pour l'animation fluide
    public void mettreAJourAnimation() {
	    	if (progressionAnimation >= 1.0) {
	    		positionAnimationX = positionCibleAnimationX;
	    		positionAnimationY = positionCibleAnimationY;
	    		return;
	    	}

	    	progressionAnimation = calculerProgressionAnimationActuelle();

	    	positionAnimationX = positionDepartAnimationX + (positionCibleAnimationX - positionDepartAnimationX) * progressionAnimation;
	    	positionAnimationY = positionDepartAnimationY + (positionCibleAnimationY - positionDepartAnimationY) * progressionAnimation;
    }
    
    public void definirPositionCible(Bloc cible) {
    	// Sauvegarder la position actuelle comme point de départ
	    	mettreAJourAnimation();
	    	this.positionDepartAnimationX = positionAnimationX;
	    	this.positionDepartAnimationY = positionAnimationY;
    	
    	// Définir la cible et réinitialiser l'animation
	    	this.positionCibleAnimationX = cible.getX();
	    	this.positionCibleAnimationY = cible.getY();
	    	this.progressionAnimation = 0.0;
	    	this.tempsDebutAnimationMs = System.currentTimeMillis();
	    	this.dureeAnimationMs = Math.max(16, GameConfiguration.VITESSE_JEU);
    	
    	// maj de la position
	    	this.position = cible;
    }
    
    public double getPositionAnimationX() {
	    	return positionAnimationX;
    }
    
    public double getPositionAnimationY() {
	    	return positionAnimationY;
    }
    
    public boolean estAnimationTerminee() {
	    	return calculerProgressionAnimationActuelle() >= 1.0;
    }
    
    public abstract boolean isPluie();
    public abstract boolean isPluieAcide();
    
    public abstract void accept(EvenementVisitor visitor);
}

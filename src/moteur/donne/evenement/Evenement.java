package moteur.donne.evenement;


import moteur.donne.carte.Bloc;

public abstract class Evenement 
{
	private Bloc position;
	
	private double dureeRestante;
	
	private int impactTemperature;
	
	private int impactHumidite;
	
	private int impactPollution;
	
	private int impactPurification;
	
	// Coordonnées fluides pour l'animation
	private double positionX; // Position en pourcentage (0 à 1) pour interpolation
	private double positionY;
	private double fromX; // Position de départ pour l'interpolation
	private double fromY;
	private double targetPositionX; // Position cible
	private double targetPositionY;
	private double animationProgress = 0.0; // 0.0 à 1.0
	private static final double ANIMATION_SPEED = 0.05; // Vitesse de l'animation (ajuster selon les besoins)

	

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
		this.fromX = position.getX();
		this.fromY = position.getY();
		this.positionX = position.getX();
		this.positionY = position.getY();
		this.targetPositionX = position.getX();
		this.targetPositionY = position.getY();
		this.animationProgress = 1.0; // Animation complète au démarrage
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
    public void updateAnimation() {
    	if (animationProgress < 1.0) {
    		animationProgress += ANIMATION_SPEED;
    		if (animationProgress > 1.0) animationProgress = 1.0;
    		
    		// Interpolation linéaire entre fromX/fromY et targetPositionX/targetPositionY
    		positionX = fromX + (targetPositionX - fromX) * animationProgress;
    		positionY = fromY + (targetPositionY - fromY) * animationProgress;
    	}
    }
    
    public void setTargetPosition(Bloc target) {
    	// Sauvegarder la position actuelle comme point de départ
    	this.fromX = positionX;
    	this.fromY = positionY;
    	
    	// Définir la cible et réinitialiser l'animation
    	this.targetPositionX = target.getX();
    	this.targetPositionY = target.getY();
    	this.animationProgress = 0.0;
    	
    	// maj de la position
    	this.position = target;
    }
    
    public double getAnimationX() {
    	return positionX;
    }
    
    public double getAnimationY() {
    	return positionY;
    }
    
    public boolean isAnimationComplete() {
    	return animationProgress >= 1.0;
    }
}

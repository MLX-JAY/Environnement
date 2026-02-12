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
}

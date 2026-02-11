package moteur.donne.evenement;


import moteur.donne.carte.Bloc;

public abstract class Evenement 
{
	private Bloc position;
	private double dureeRestante;

	public Evenement(Bloc position, double dureeRestante) {
        this.position = position;
        this.dureeRestante = dureeRestante;
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

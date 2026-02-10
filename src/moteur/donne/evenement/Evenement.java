package moteur.donne.evenement;

import java.util.ArrayList;

import moteur.donne.carte.Bloc;

public abstract class Evenement 
{
	private Bloc position;
	private int dureeRestante;

	public Evenement(Bloc position, int dureeRestante) {
        this.position = position;
        this.dureeRestante = dureeRestante;
    }


    public Bloc getPosition() 
	{
		return position;
	}
    

	public int getDuree() 
	{
		return dureeRestante;
	}


    public void setPosition(Bloc position) {
        this.position = position;
    }


    public void setDureeRestante(int dureeRestante) {
        this.dureeRestante = dureeRestante;
    }
}

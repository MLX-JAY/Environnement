package moteur.donne.evenement.statique;

import moteur.donne.carte.Bloc;
import moteur.donne.evenement.Evenement;

public class Seisme extends Evenement
{

	public Seisme(Bloc position, int dureeRestante) {
		super(position, dureeRestante);
	}
	
}

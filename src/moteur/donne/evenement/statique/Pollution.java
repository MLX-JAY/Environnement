package moteur.donne.evenement.statique;

import moteur.donne.carte.Bloc;
import moteur.donne.evenement.Evenement;

public class Pollution extends Evenement
{

	public Pollution(Bloc position, int dureeRestante) {
		super(position, dureeRestante);
	}

	
}

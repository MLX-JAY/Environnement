package moteur.donne.evenement.statique;

import moteur.donne.carte.Bloc;
import moteur.donne.evenement.Evenement; 

public class Purification extends Evenement
{

	public Purification(Bloc position, int dureeRestante) {
		super(position, dureeRestante);
	}
	
}

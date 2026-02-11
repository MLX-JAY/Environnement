package moteur.donne.evenement.mobile;

import moteur.donne.carte.Bloc;
import moteur.donne.evenement.Evenement;

public class VentFroid extends Evenement
{

	public VentFroid(Bloc position, int dureeRestante) {
		super(position, dureeRestante);
	}

}

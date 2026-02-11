package moteur.donne.evenement.mobile;

import moteur.donne.carte.Bloc;
import moteur.donne.evenement.Evenement;

public class VentFroid extends Evenement
{

	public VentFroid(Bloc position, double dureeRestante) {
		super(position, dureeRestante);
	}

}

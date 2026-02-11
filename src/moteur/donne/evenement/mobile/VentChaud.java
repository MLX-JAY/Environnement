package moteur.donne.evenement.mobile;

import moteur.donne.carte.Bloc;
import moteur.donne.evenement.Evenement;

public class VentChaud extends Evenement
{

	public VentChaud(Bloc position, double dureeRestante) {
		super(position, dureeRestante);
	}
	
}

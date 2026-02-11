package moteur.donne.evenement.mobile;

import moteur.donne.carte.Bloc;
import moteur.donne.evenement.Evenement;

public class VentChaud extends Evenement
{

	public VentChaud(Bloc position, int dureeRestante) {
		super(position, dureeRestante);
	}
	
}

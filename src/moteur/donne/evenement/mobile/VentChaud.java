package moteur.donne.evenement.mobile;

import moteur.donne.carte.Bloc;
import moteur.donne.evenement.Evenement;

public class VentChaud extends Evenement
{

	public VentChaud(Bloc position, double dureeRestante, int impactTemperature, int impactHumidite,
			int impactPollution, int impactPurification) {
		super(position, dureeRestante, impactTemperature, impactHumidite, impactPollution, impactPurification);
		// TODO Auto-generated constructor stub
	}

}

package moteur.donne.evenement.mobile;

import moteur.donne.carte.Bloc;
import moteur.donne.evenement.Evenement;
import moteur.processus.visitor.EvenementVisitor;

public class Pollution extends Evenement
{

	public Pollution(Bloc position, double dureeRestante, int impactTemperature, int impactHumidite,
			int impactPollution, int impactPurification) {
		super(position, dureeRestante, impactTemperature, impactHumidite, impactPollution, impactPurification);
	}

	@Override
	public void accept(EvenementVisitor visitor) {
		visitor.visit(this);
	}

}

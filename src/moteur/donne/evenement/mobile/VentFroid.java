package moteur.donne.evenement.mobile;

import moteur.donne.carte.Bloc;
import moteur.donne.evenement.Evenement;
import moteur.processus.visitor.EvenementVisitor;

public class VentFroid extends Evenement
{

	public VentFroid(Bloc position, double dureeRestante, int impactTemperature, int impactHumidite,
			int impactPollution, int impactPurification) {
		super(position, dureeRestante, impactTemperature, impactHumidite, impactPollution, impactPurification);
	}

	@Override
	public <T> T accept(EvenementVisitor<T> visitor) {
		return visitor.visit(this);
	}

	@Override
	public boolean isPluie() {
		return false;
	}

	@Override
	public boolean isPluieAcide() {
		return false;
	}


}

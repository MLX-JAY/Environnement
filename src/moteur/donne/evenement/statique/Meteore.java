package moteur.donne.evenement.statique;

import moteur.donne.carte.Bloc;
import moteur.donne.evenement.Evenement;
import moteur.processus.visitor.EvenementVisitor;

public class Meteore extends Evenement
{

	public Meteore(Bloc position, double dureeRestante, int impactTemperature, int impactHumidite, int impactPollution,
			int impactPurification) {
		super(position, dureeRestante, impactTemperature, impactHumidite, impactPollution, impactPurification);
	}

	@Override
	public void accept(EvenementVisitor visitor) {
		visitor.visit(this);
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

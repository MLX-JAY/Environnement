package moteur.donne.evenement.statique;

import moteur.donne.carte.Bloc;
import moteur.donne.evenement.Evenement;
import moteur.processus.visitor.EvenementVisitor;

public class Meteore extends Evenement
{
	public static final int SEUIL_AVERTISSEMENT = 10;

	private final Bloc cibleImpact;
	private int pasRestants;

	public Meteore(Bloc positionDepart, Bloc cibleImpact, int pasRestants, double dureeRestante,
			int impactTemperature, int impactHumidite, int impactPollution, int impactPurification) {
		super(positionDepart, dureeRestante, impactTemperature, impactHumidite, impactPollution, impactPurification);
		this.cibleImpact = cibleImpact;
		this.pasRestants = pasRestants;
	}

	public Meteore(Bloc position, double dureeRestante, int impactTemperature, int impactHumidite, int impactPollution,
			int impactPurification) {
		this(position, position, 0, dureeRestante, impactTemperature, impactHumidite, impactPollution,
				impactPurification);
	}

	public Bloc getCibleImpact() {
		return cibleImpact;
	}

	public int getPasRestants() {
		return pasRestants;
	}

	public void decrementerPasRestants() {
		if (pasRestants > 0) {
			pasRestants--;
		}
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

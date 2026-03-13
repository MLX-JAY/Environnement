package moteur.donne.biome;

import moteur.donne.carte.Bloc;
import moteur.donne.evenement.Evenement;
import moteur.processus.visitor.BiomeVisitor;

public class Ville extends Biome 
{

	public Ville(double temperature, double pollution, double purification, double humidite, double evolution,
			Bloc position) {
		super(temperature, pollution, purification, humidite, evolution, position);
	}

	@Override
	public Evenement accept(BiomeVisitor visitor) {
		return visitor.visit(this);
	}

}

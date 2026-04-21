package moteur.donne.biome;

import moteur.donne.carte.Bloc;
import moteur.processus.visitor.BiomeVisitor;

public class Desert extends Biome 
{

	public Desert(double temperature, double pollution, double purification, double humidite, double evolution,
			Bloc position) {
		super(temperature, pollution, purification, humidite, evolution, position);
	}

	@Override
	public <T> T accept(BiomeVisitor<T> visitor) {
		return visitor.visit(this);
	}

}
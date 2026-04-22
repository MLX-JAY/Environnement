package moteur.donne.biome;

import moteur.donne.carte.Bloc;
import moteur.processus.visitor.BiomeVisitor;
import org.apache.log4j.Logger;
import util.LoggerUtility;

public abstract class Biome 
{
	
	private static final Logger logger = LoggerUtility.getLogger(Biome.class);
	
	private double temperature;
	private double pollution;
	private double purification;
	private double humidite;
	private double evolution;
	private Bloc position;
	
	
	public Biome(double temperature, double pollution, double purification, 
			double humidite, double evolution, Bloc position) 
	{
		this.temperature = temperature;
		this.pollution = pollution;
		this.purification = purification;
		this.humidite = humidite;
		this.evolution = evolution;
		this.position = position;
	}


	public double getTemperature() {
		return temperature;
	}


	public double getPollution() {
		return pollution;
	}


	public double getPurification() {
		return purification;
	}


	public double getHumidite() {
		return humidite;
	}


	public double getEvolution() {
		return evolution;
	}


	public Bloc getPosition() {
		return position;
	}

	@Override
	public String toString() {
		return "Biome [temperature=" + temperature + ", pollution=" + pollution + ", purification=" + purification
				+ ", humidite=" + humidite + ", evolution=" + evolution + ", position=" + position + "]";
	}


	public void setTemperature(double temperature) {
        double oldVal = this.temperature;
        this.temperature = Math.max(0, Math.min(100, temperature));
        logger.debug("[Impact] Temperature en (" + position.getX() + "," + position.getY() + "): " + oldVal + " -> " + this.temperature);
    }

    public void setPollution(double pollution) {
        double oldVal = this.pollution;
        this.pollution = Math.max(0, Math.min(100, pollution));
        logger.debug("[Impact] Pollution en (" + position.getX() + "," + position.getY() + "): " + oldVal + " -> " + this.pollution);
    }

    public void setPurification(double purification) {
        double oldVal = this.purification;
        this.purification = Math.max(0, Math.min(100, purification));
        logger.debug("[Impact] Purification en (" + position.getX() + "," + position.getY() + "): " + oldVal + " -> " + this.purification);
    }

    public void setHumidite(double humidite) {
        double oldVal = this.humidite;
        this.humidite = Math.max(0, Math.min(100, humidite));
        logger.debug("[Impact] Humidite en (" + position.getX() + "," + position.getY() + "): " + oldVal + " -> " + this.humidite);
    }

    public void setEvolution(double evolution) {
        this.evolution = (evolution > 100) ? 100 : evolution;
    }

    public void setPosition(Bloc position) {
        this.position = position;
    }
    
    public abstract <T> T accept(BiomeVisitor<T> visitor);
	
	
}

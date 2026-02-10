package moteur.donne.biome;

import moteur.donne.carte.Bloc;

public abstract class Biome 
{
	
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
	
	
}

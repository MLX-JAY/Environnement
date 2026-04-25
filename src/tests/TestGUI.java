package tests;

import gui.MainGUI;


public class TestGUI {
	public static void main(String[] args) {

		MainGUI gameMainGUI = new MainGUI("Environnement");

		Thread gameThread = new Thread(gameMainGUI);
		gameThread.start();
	}
}
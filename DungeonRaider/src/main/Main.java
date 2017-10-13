package main;

import gameEngine.engine.Engine;
/**
 * Run this class to start the game
 * @author Jono Yan
 *
 */
public class Main {

	public static void main(String[] args) {
		Engine game = new Engine();
		Thread thread = new Thread(game);
		thread.start();
	}

}

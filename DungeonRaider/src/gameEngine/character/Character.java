package gameEngine.character;

import gameEngine.map.Map;

public interface Character {

	public int lightAttack();
	public int heavyAttack();

	public void walkLeft();
	public void walkRight();
	public void walkUp();
	public void walkDown();

	public void runLeft();
	public void runRight();
	public void runUp();
	public void runDown();

	/*	*//**
	 * Checks the engine to return the size of the map and then checks if the player is moving out of the map
	 * @param engine the engine of the game
	 * @param newX the new X coordinate that will be set after movement
	 * @param newY the new Y coordinate that will be set after movement
	 * @return returns whether the player is moving out of the map
	 */
	public default boolean checkBoundry(Map currentMap, int newX, int newY) {
		return currentMap.onWall(newX, newY);
	}

}

package gameEngine.character;

import gameEngine.engine.Engine;
import gameEngine.map.Map;
import gameEngine.util.Box;

public interface Character {

	public int heavyAttack();

	public void walkLeft();
	public void walkRight();
	public void walkUp();
	public void walkDown();

	/**

	 * Checks the engine to return the size of the map and then checks if the
	 * player is moving out of the map
	 * @param engine the engine of the game
	 * @param newX the new X coordisnate that will be set after movement
	 * @param newY the new Y coordinate that will be set after movement
	 * @return returns whether the player is moving out of the map
	 */
	public default boolean checkBoundry(Map currentMap, Box box) {
		return currentMap.onWall(box);
	}

	/**
	 * 1 for forwards teleporting
	 * -1 for teleporting backwards
	 * 0 for no teleportation tile found
	 * @param currentMap
	 * @param box
	 * @return
	 */
	public default int checkTeleporter(Map currentMap, Box box) {
		return currentMap.onTeleporter(box);
	}

	/**
	 *
	 * @param mx
	 * @param my
	 * @param engine
	 */
	public void attack(Engine engine);
}

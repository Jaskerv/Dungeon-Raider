package dungeonraider.character;

import java.awt.Image;
import java.util.List;

import dungeonraider.item.Item;

public class NPC implements Character {

	private String name;
	private String description;
	private int coin;
	private List<Item> inventory;
	private Image spriteImage;

	/**
	 * NPC Constructor constructor
	 */
	public NPC() {

	}

	/**
	 * This method is for when a user interacts with this NPC. A JDialog will
	 * pop up and an action will occur.
	 */
	public void interact() {

	}

	/**
	 * Gives the item(s) that the user requests
	 */
	public void trade() {

	}

	@Override
	public int lightAttack() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int heavyAttack() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void walkLeft() {
		// TODO Auto-generated method stub

	}

	@Override
	public void walkRight() {
		// TODO Auto-generated method stub

	}

	@Override
	public void walkUp() {
		// TODO Auto-generated method stub

	}

	@Override
	public void walkDown() {
		// TODO Auto-generated method stub

	}

	@Override
	public void runLeft() {
		// TODO Auto-generated method stub

	}

	@Override
	public void runRight() {
		// TODO Auto-generated method stub

	}

	@Override
	public void runUp() {
		// TODO Auto-generated method stub

	}

	@Override
	public void runDown() {
		// TODO Auto-generated method stub

	}

}

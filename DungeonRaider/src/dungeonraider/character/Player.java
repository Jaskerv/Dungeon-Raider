package dungeonraider.character;

import java.util.List;

import dungeonraider.item.Armour;
import dungeonraider.item.Item;
import dungeonraider.item.Weapon;
import dungeonraider.util.Position;

public class Player implements Character{

	private int lives;
	private int healthPoints;
	private int gold;
	private int stamina;
	private boolean sprint;
	private int currentCapacity;



	private Position position;
	private Weapon primaryWeapon;
	private Weapon secondaryWeapon;
	private Armour armour;
	private Inventory inventory;
	//private Sprite

	private static final int MAX_CAPACITY = 20;

	public Player() {

	}

	@Override
	public void lightAttack() {
		// TODO Auto-generated method stub

	}
	@Override
	public void heavyAttack() {
		// TODO Auto-generated method stub

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

	public void interact() {

	}






}

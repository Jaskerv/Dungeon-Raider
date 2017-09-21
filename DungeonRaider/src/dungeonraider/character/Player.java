package dungeonraider.character;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

import dungeonraider.item.Armour;
import dungeonraider.item.Weapon;
import dungeonraider.util.Position;

public class Player implements Character {

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
	private BufferedImage spriteImage;

	private static final int MAX_CAPACITY = 20;

	public Player() {
		this.spriteImage = new BufferedImage(10, 30,
				BufferedImage.TYPE_INT_RGB);
		Graphics g = spriteImage.getGraphics();
		g.setColor(Color.RED);
		g.fillRect(0, 0, 10, 30);
		g.dispose();
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

	public BufferedImage getSpriteImage() {
		return spriteImage;
	}

	public void render(Graphics g) {
		g.drawImage(this.getSpriteImage(), 10, 10, null);
	}


	public int getLives() {
		return lives;
	}


	public void setLives(int lives) {
		this.lives = lives;
	}


	public int getHealthPoints() {
		return healthPoints;
	}


	public void setHealthPoints(int healthPoints) {
		this.healthPoints = healthPoints;
	}


	public int getGold() {
		return gold;
	}


	public void setGold(int gold) {
		this.gold = gold;
	}


	public int getStamina() {
		return stamina;
	}


	public void setStamina(int stamina) {
		this.stamina = stamina;
	}


	public boolean isSprint() {
		return sprint;
	}


	public void setSprint(boolean sprint) {
		this.sprint = sprint;
	}


	public int getCurrentCapacity() {
		return currentCapacity;
	}


	public void setCurrentCapacity(int currentCapacity) {
		this.currentCapacity = currentCapacity;
	}


	public Position getPosition() {
		return position;
	}


	public void setPosition(Position position) {
		this.position = position;
	}


	public Weapon getPrimaryWeapon() {
		return primaryWeapon;
	}


	public void setPrimaryWeapon(Weapon primaryWeapon) {
		this.primaryWeapon = primaryWeapon;
	}


	public Weapon getSecondaryWeapon() {
		return secondaryWeapon;
	}


	public void setSecondaryWeapon(Weapon secondaryWeapon) {
		this.secondaryWeapon = secondaryWeapon;
	}


	public Armour getArmour() {
		return armour;
	}


	public void setArmour(Armour armour) {
		this.armour = armour;
	}


	public Inventory getInventory() {
		return inventory;
	}


	public void setInventory(Inventory inventory) {
		this.inventory = inventory;
	}


	public void setSpriteImage(BufferedImage spriteImage) {
		this.spriteImage = spriteImage;
	}



}

package dungeonraider.character;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

import dungeonraider.controller.KeyController;
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
	private int x;
	private int y;

	private Position position;
	private Weapon primaryWeapon;
	private Weapon secondaryWeapon;
	private Armour armour;
	private Inventory inventory;
	private BufferedImage spriteImage;

	private static final int MAX_CAPACITY = 20;


	public Player() {
		this.x = 0;
		this.y = 0;
		this.spriteImage = new BufferedImage(10, 30,
				BufferedImage.TYPE_INT_RGB);
		Graphics g = spriteImage.getGraphics();
		g.setColor(Color.RED);
		g.fillRect(this.x, this.y, 10, 30);
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
		this.x -= 3;
	}

	@Override
	public void walkRight() {
		// TODO Auto-generated method stub
		this.x += 3;
	}

	@Override
	public void walkUp() {
		// TODO Auto-generated method stub
		this.y -= 3;
	}

	@Override
	public void walkDown() {
		// TODO Auto-generated method stub
		this.y += 3;
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
		g.drawImage(this.spriteImage, x, y, null);
	}




}

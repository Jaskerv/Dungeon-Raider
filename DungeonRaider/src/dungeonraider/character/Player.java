package dungeonraider.character;

import dungeonraider.item.Armour;
import dungeonraider.item.Weapon;
import dungeonraider.sprite.Sprite;
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
	public static final int speed = 3;

	private Position position;
	private Weapon primaryWeapon;
	private Weapon secondaryWeapon;
	private Armour armour;
	private Inventory inventory;
	private Sprite spriteImage;

	private static final int MAX_CAPACITY = 20;
	private static final int SPRINT_MODIFIER = 2;


	public Player(int x, int y, int stamina, Sprite sprite) {
		this.x = x;
		this.y = y;
		this.spriteImage = sprite;
		this.stamina = stamina;
//		Graphics g = spriteImage.getGraphics();
//		g.setColor(Color.RED);
//		g.fillRect(this.x, this.y, 10, 30);
//		g.dispose();
	}


	@Override
	public int lightAttack() {
		// TODO Auto-generated method stub
		return secondaryWeapon.getDamage();
	}

	@Override
	public int heavyAttack() {
		// TODO Auto-generated method stub
		return primaryWeapon.getDamage();
	}

	@Override
	public void walkLeft() {
		// TODO Auto-generated method stub
		this.x -= speed;
	}

	@Override
	public void walkRight() {
		// TODO Auto-generated method stub
		this.x += speed;
	}

	@Override
	public void walkUp() {
		// TODO Auto-generated method stub
		this.y -= speed;
	}

	@Override
	public void walkDown() {
		// TODO Auto-generated method stub
		this.y += speed;
	}

	@Override
	public void runLeft() {
		// TODO Auto-generated method stub
		this.x -= speed*SPRINT_MODIFIER;
	}

	@Override
	public void runRight() {
		// TODO Auto-generated method stub
		this.x += speed*SPRINT_MODIFIER;
	}

	@Override
	public void runUp() {
		// TODO Auto-generated method stub
		this.y += speed*SPRINT_MODIFIER;
	}

	@Override
	public void runDown() {
		// TODO Auto-generated method stub
		this.y -= speed*SPRINT_MODIFIER;
	}

	public void interact() {

	}

	public int getGoldTotal() {
		return gold;
	}

	public void spendGold(int amountSpent) {
		gold = gold - amountSpent;
	}

	public void collectgold(int amountRecieved) {
		gold = gold + amountRecieved;
	}

	public int getX() {
		return x;
	}


	public void setX(int x) {
		this.x = x;
	}


	public int getY() {
		return y;
	}


	public void setY(int y) {
		this.y = y;
	}


	public Sprite getSpriteImage() {
		return spriteImage;
	}


	public int getSpeed() {
		return speed;
	}





}

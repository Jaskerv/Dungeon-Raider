package dungeonraider.character;

import java.util.PriorityQueue;
import java.util.Queue;

import dungeonraider.controller.KeyController;
import dungeonraider.engine.Engine;
import dungeonraider.engine.GameObject;
import dungeonraider.engine.Renderer;
import dungeonraider.item.Armour;
import dungeonraider.item.Weapon;
import dungeonraider.sprite.Sprite;
import dungeonraider.util.Box;
import dungeonraider.util.Camera;
import dungeonraider.util.Position;
import dungeonraider.util.Rectangle;

public class Player implements Character, GameObject {

	private int lives;
	private int hp;
	private int hpMax;
	private int gold;
	private int stamina;
	private boolean sprint;
	private int currentCapacity;
	private int x;
	private int y;
	public static final int speed = 7;

	private Position position;
	private Weapon primaryWeapon;
	private Weapon secondaryWeapon;
	private Armour armour;
	private Inventory inventory;
	private Sprite spriteImage;

	private static final int MAX_CAPACITY = 20;
	private static final int SPRINT_MODIFIER = 2;
	private boolean guiUpdate;

	private int zoom;
	private Queue<Integer> damageQueue;

	public Player(int x, int y, int stamina, Sprite sprite, int zoom, int hp, int hpMax) {
		this.x = x;
		this.y = y;
		this.zoom = zoom;
		this.spriteImage = sprite;
		this.stamina = stamina;
	}

	public Player(Position center, int stamina, Sprite playerSprite, int zoom, int hp, int hpMax) {
		this.damageQueue = new PriorityQueue<>();
		this.spriteImage = playerSprite;
		this.zoom = zoom;
		this.x = center.getX() - (playerSprite.getWidth() / 2 * zoom);
		this.y = center.getY() - (playerSprite.getHeight() / 2 * zoom);
		this.hp = hp;
		this.hpMax = hpMax;
		this.guiUpdate = false;
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
		this.x -= speed * SPRINT_MODIFIER;
	}

	@Override
	public void runRight() {
		// TODO Auto-generated method stub
		this.x += speed * SPRINT_MODIFIER;
	}

	@Override
	public void runUp() {
		// TODO Auto-generated method stub
		this.y += speed * SPRINT_MODIFIER;
	}

	@Override
	public void runDown() {
		// TODO Auto-generated method stub
		this.y -= speed * SPRINT_MODIFIER;
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

	/**
	 * @return the zoom
	 */
	public int getZoom() {
		return zoom;
	}

	@Override
	public void render(Renderer renderer, int xZoom, int yZoom) {
		renderer.renderArray(spriteImage.getPixels(), spriteImage.getWidth(), spriteImage.getWidth(), x, y, zoom, zoom);

	}

	@Override
	public void update(Engine engine) {
		KeyController keyBinds = engine.getKeyBinds();
		/** Player */
		if (keyBinds.isUp()) {
			walkUp();
		}
		if (keyBinds.isDown()) {
			walkDown();
		}
		if (keyBinds.isLeft()) {
			walkLeft();
		}
		if (keyBinds.isRight()) {
			walkRight();
		}
		this.updateCamera(engine.getRenderer().getCamera());
		if (!damageQueue.isEmpty()) {
			int damage = damageQueue.poll();
			this.hp += damage;
			this.guiUpdate = true;
		}
		// /** Player */
		// Camera camera = renderer.getCamera();
		// if (keyBinds.isUp()) {
		// if (player.getY() >= TOP_WALL) {
		// camera.moveCamera(0, -player.getSpeed());
		// player.walkUp();
		// }
		// }
		// if (keyBinds.isDown()) {
		// if (player.getY() <= BOTTOM_WALL) {
		// camera.moveCamera(0, player.getSpeed());
		// player.walkDown();
		// }
		// }
		// if (keyBinds.isLeft()) {
		// if (player.getX() >= LEFT_WALL) {
		// camera.moveCamera(-player.getSpeed(), 0);
		// player.walkLeft();
		// }
		// }
		// if (keyBinds.isRight()) {
		// if (player.getX() <= RIGHT_WALL) {
		// camera.moveCamera(player.getSpeed(), 0);
		// player.walkRight();
		// }
		// }
	}

	/**
	 * Updates the camera's position to center the player
	 * 
	 * @param camera
	 */
	private void updateCamera(Box camera) {
		camera.setX(x - (camera.getWidth() / 2) + (this.spriteImage.getWidth() * zoom / 2));
		camera.setY(y - (camera.getHeight() / 2) + (this.spriteImage.getHeight() * zoom / 2));
	}

	/**
	 * @return the hp
	 */
	public int getHp() {
		return hp;
	}

	/**
	 * @return the hpMax
	 */
	public int getHpMax() {
		return hpMax;
	}

	/**
	 * Damages player with positive numbers, heals player with negative numbers
	 * 
	 * @param i
	 */
	public void damage(int i) {
		this.damageQueue.offer(-i);
	}

	/**
	 * @return the damageQueue
	 */
	public Queue<Integer> getDamageQueue() {
		return damageQueue;
	}

	/**
	 * @return the guiUpdate
	 */
	public boolean isGuiUpdate() {
		return guiUpdate;
	}

	/**
	 * @param guiUpdate the guiUpdate to set
	 */
	public void setGuiUpdate(boolean guiUpdate) {
		this.guiUpdate = guiUpdate;
	}

}

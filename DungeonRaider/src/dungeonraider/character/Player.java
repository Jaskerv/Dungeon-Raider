package dungeonraider.character;

import java.util.PriorityQueue;
import java.util.Queue;

import dungeonraider.controller.KeyController;
import dungeonraider.controller.MouseController;
import dungeonraider.engine.Engine;
import dungeonraider.engine.GameObject;
import dungeonraider.engine.Renderer;
import dungeonraider.item.Shield;
import dungeonraider.item.Weapon;
import dungeonraider.map.Map;
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
	private int currentCapacity;
	private int x;
	private int y;

	private Position position;
	private Weapon primaryWeapon;
	private Weapon secondaryWeapon;
	private Shield armour;
	private Inventory inventory;
	private Sprite spriteImage;
	private Box playerBoundBox;
	private Box pickUpRadius;

	private static final int MAX_CAPACITY = 20;
	public static final int SPEED = 2;
	public static final int SPRINT = 7;
	private int zoom;
	private Queue<Integer> damageQueue;

	public Player(int x, int y, int stamina, Sprite sprite, int zoom, int hp, int hpMax) {
		this.x = x;
		this.y = y;
		this.zoom = zoom;
		this.spriteImage = sprite;
		this.stamina = stamina;
		this.playerBoundBox = new Box(x, y, sprite.getWidth()*zoom, sprite.getHeight()*zoom);
		//Pick up radius of the player starts half of the players width to the left of the player and extends to twice the players width meaning the pick up radius is twice the size of the player
		this.pickUpRadius	= new Box(x-((sprite.getWidth()*zoom)/2), y-((sprite.getHeight()*zoom)/2), (sprite.getWidth()*zoom)*2, (sprite.getHeight()*zoom)*2);
	}

	public Player(Position center, int stamina, Sprite playerSprite, int zoom, int hp, int hpMax) {
		this.damageQueue = new PriorityQueue<>();
		this.spriteImage = playerSprite;
		this.zoom = zoom;
		this.x = center.getX() - (playerSprite.getWidth() / 2 * zoom);
		this.y = center.getY() - (playerSprite.getHeight() / 2 * zoom);
		this.hp = hp;
		this.hpMax = hpMax;
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
		//if(checkBoundry(this.x - speed, this.y))
		this.x -= SPEED;
	}

	@Override
	public void walkRight() {
		// TODO Auto-generated method stub
		//if(checkBoundry(this.x + speed, this.y))
		this.x += SPEED;
	}

	@Override
	public void walkUp() {
		// TODO Auto-generated method stub
		//if(checkBoundry(this.x, this.y-speed))
		this.y -= SPEED;
	}

	@Override
	public void walkDown() {
		// TODO Auto-generated method stub
		//if(checkBoundry(this.x, this.y+speed))
		this.y += SPEED;
	}

	@Override
	public void runLeft() {
		// TODO Auto-generated method stub
		this.x -= SPRINT;
	}

	@Override
	public void runRight() {
		// TODO Auto-generated method stub
		this.x += SPRINT;
	}

	@Override
	public void runUp() {
		// TODO Auto-generated method stub
		this.y -= SPRINT;
	}

	@Override
	public void runDown() {
		// TODO Auto-generated method stub
		this.y += SPRINT;
	}

	public void interact() {

	}

	/**
	 * Renders the players current position
	 */
	@Override
	public void render(Renderer renderer, int xZoom, int yZoom) {
		renderer.renderArray(spriteImage.getPixels(), spriteImage.getWidth(), spriteImage.getWidth(), x, y, zoom, zoom);
	}

	/**
	 * Updates the player dependant on the key and mouse controllers in the engine and what booleans have been activated
	 * via the user pushing keys / the mouse.
	 */
	@Override
	public void update(Engine engine) {
		KeyController keyBinds = engine.getKeyBinds();
		Map currentMap = engine.getCurrentMap();
		MouseController mouseActions = engine.getMouseListener();

		int width = spriteImage.getWidth() * zoom;
		int height = spriteImage.getHeight() * zoom;


		/**
		 * Player walking connection with key controller:
		 * Also checks for player connection with walls
		 */
		if(!keyBinds.isRun()) {
			if (keyBinds.isUp()) {
				if(checkBoundry(currentMap, x + width, y - SPEED + height/2))	
					if(checkBoundry(currentMap, x, y - SPEED + height/2))
						walkUp();
			}
			if (keyBinds.isDown()) {
				if(checkBoundry(currentMap, x + width, y + height + SPEED))
					if(checkBoundry(currentMap, x, y + height + SPEED))
						walkDown();
			}
			if (keyBinds.isLeft()) {
				if(checkBoundry(currentMap, x - SPEED, y + height/2))	
					if(checkBoundry(currentMap, x - SPEED, y + height))
						walkLeft();
			}
			if (keyBinds.isRight()) {
				if(checkBoundry(currentMap, x + width + SPEED, y + height/2))
					if(checkBoundry(currentMap, x + width + SPEED, y + height))
						walkRight();
			}
		}

		/**
		 * Player running connection with key controller:
		 * Also checks for player connection with walls
		 */
		if(keyBinds.isRun()) {
			if (keyBinds.isUp()) {
				if(checkBoundry(currentMap, x + width, y - SPRINT + height/2))	
					if(checkBoundry(currentMap, x, y - SPRINT + height/2))
						runUp();
			}
			if (keyBinds.isDown()) {
				if(checkBoundry(currentMap, x + width, y + height + SPRINT))
					if(checkBoundry(currentMap, x, y + height + SPRINT))
						runDown();
			}
			if (keyBinds.isLeft()) {
				if(checkBoundry(currentMap, x - SPRINT, y + height/2))	
					if(checkBoundry(currentMap, x - SPRINT, y + height))
						runLeft();
			}
			if (keyBinds.isRight()) {
				if(checkBoundry(currentMap, x + width + SPRINT, y + height/2))
					if(checkBoundry(currentMap, x + width + SPRINT, y + height))
						runRight();
			}
		}

		//Checking if player is attempting to pick up and whether there is anything to pick up
		if(keyBinds.isPickUp()) {
			//need to check if any of the items locations

		}

		if(mouseActions.getAttacks() > 0) {
			mouseActions.setAttacks(mouseActions.getAttacks() - 1);
			int mx = mouseActions.getMx();
			int my = mouseActions.getMy();
			System.out.println("Attack");
			System.out.println(mx);
			System.out.println(my);
			attack(mx, my, engine);
		}

		this.updateCamera(engine.getRenderer().getCamera());
		if (!damageQueue.isEmpty()) {
			int damage = damageQueue.poll();
			this.hp += damage;
		}
	}

	public void attack(int mx, int my, Engine engine) {

	}

	/*	*//**
	 * Checks the engine to return the size of the map and then checks if the player is moving out of the map
	 * @param engine the engine of the game
	 * @param newX the new X coordinate that will be set after movement
	 * @param newY the new Y coordinate that will be set after movement
	 * @return returns whether the player is moving out of the map
	 */
	public boolean checkBoundry(Map currentMap, int newX, int newY) {
		return currentMap.onWall(newX, newY);
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
	 * Getters and setters for the player class
	 */

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

	public Queue<Integer> getDamageQueue() {
		return damageQueue;
	}

	public int getGoldTotal() {
		return gold;
	}

	public void spendGold(int amountSpent) {
		gold = gold - amountSpent;
	}

	public void collectgold(int amountRecieved) {
		gold = gold + amountRecieved;;
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
		return SPEED;
	}

	public int getZoom() {
		return zoom;
	}


}

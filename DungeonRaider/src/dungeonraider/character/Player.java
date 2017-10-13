package dungeonraider.character;

import java.util.List;
import java.util.PriorityQueue;
import java.util.Queue;

import dungeonraider.controller.KeyController;
import dungeonraider.controller.MouseController;
import dungeonraider.engine.Engine;
import dungeonraider.engine.GameObject;
import dungeonraider.engine.Renderer;
import dungeonraider.item.Item;
import dungeonraider.item.Shield;
import dungeonraider.item.Weapon;
import dungeonraider.map.Map;
import dungeonraider.sprite.Sprite;
import dungeonraider.util.Box;
import dungeonraider.util.Camera;
import dungeonraider.util.Position;
import dungeonraider.util.Rectangle;


/**
 * Need to implement equiping of weapon then test if attacking works because right now null pointer exeception
 * when trying to find weapon range
 * @author Gabriel Tennent
 *
 */

public class Player implements Character, GameObject {

	private int lives;
	private int hp;
	private int hpMax;
	private int gold;
	private int stamina;
	private int currentCapacity;
	private int x;
	private int y;

	/**
	 * Item and bounding box variables
	 */
	private boolean primaryEquipped;
	private boolean secondaryEquipped;
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


	public Player(Position center, int stamina, Sprite playerSprite, int zoom, int hp, int hpMax) {
		this.damageQueue = new PriorityQueue<>();
		this.spriteImage = playerSprite;
		this.zoom = zoom;
		this.x = center.getX() - (playerSprite.getWidth() / 2 * zoom);
		this.y = center.getY() - (playerSprite.getHeight() / 2 * zoom);
		this.hp = hp;
		this.hpMax = hpMax;
		this.playerBoundBox = new Box(x, y, playerSprite.getWidth()*zoom, playerSprite.getHeight()*zoom);
		//Pick up radius of the player starts half of the players width to the left of the player and
		//extends to twice the players width meaning the pick up radius is twice the size of the player
		this.pickUpRadius	= new Box(x-((playerSprite.getWidth()*zoom)), y-((playerSprite.getHeight()*zoom)),
				(playerSprite.getWidth()*zoom)*4, (playerSprite.getHeight()*zoom)*4);
		this.inventory = new Inventory(20);
		this.primaryEquipped = false;
		this.secondaryEquipped = false;
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
			this.pickUpRadius	= new Box(x, y,
					(spriteImage.getWidth()*zoom), (spriteImage.getHeight()*zoom));
			//need to check if any of the items locations
			List<Item> itemsOnMap = engine.getCurrentMap().getItems();
			for(Item item : itemsOnMap) {
				Position itemPos = item.getPosition();
				if(pickUpRadius.contains(itemPos.getX(), itemPos.getY())){
					this.inventory.add(item);
					item.setPickedUp(true);
					itemsOnMap.remove(item);
					break;
				}
			}
			System.out.println(this.inventory.getInventory().isEmpty());
		}

		/**
		 * Returns left clicks and their co-ordinates to tell where for the character to attack
		 */
		while(!mouseActions.getAttackPositions().isEmpty()) {
			//Gets the attack position from the mouse controller
			Position attackPos = mouseActions.getAttackPos();

			//Prevents player from being able to run and attack at the same time
			keyBinds.setRun(false);

			int mx = attackPos.getX();
			int my = attackPos.getY();
			if(primaryEquipped)	attack(mx, my, engine);
		}

		/**
		 * Updates the players hpbar if they have taken damage
		 */
		if(!damageQueue.isEmpty()) {
			int damage = damageQueue.poll();
			this.hp += damage;
		}

		/**
		 * Attempts to use a players item to heal the player
		 */
		if(keyBinds.isUseItem()) {
			if(!this.inventory.getInventory().isEmpty()) {
			this.hp += 10;
			this.inventory.removeItem();
			}
		}

		/**
		 * Updates camera
		 */
		this.updateCamera(engine.getRenderer().getCamera());

	}

	/**
	 *
	 * @param mx
	 * @param my
	 * @param engine
	 */
	public void attack(int mx, int my, Engine engine) {
		int width = spriteImage.getWidth() * zoom;
		int height = spriteImage.getHeight() * zoom;
		Position center = engine.getRenderer().getCamera().getCenter();
		//right attack calculated from the position of the camera
		if(mx > center.getX()) {
			//right side bounding box = to range of weapon and half player height
			Box rightPrimaryAttackRad = new Box(x + width, y, primaryWeapon.getRange(), height/2);
			//attackRight();
			System.out.println("x: " + x);
			System.out.println("attack right");

		}
		//left attack calculated from the position of the camera
		if(mx < center.getX()) {
			//left side bounding box = to range of weapon and half player height
			Box leftPrimaryAttackRad = new Box(x-primaryWeapon.getRange(), y, primaryWeapon.getRange(), height/2);
			//attackLeft();
			System.out.println("x: " + x);
			System.out.println("attack left");
		}
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

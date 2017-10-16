package gameEngine.character;

import java.awt.Color;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Queue;

import gameEngine.controller.KeyController;
import gameEngine.controller.MouseController;
import gameEngine.engine.Engine;
import gameEngine.engine.GameObject;
import gameEngine.engine.Renderer;
import gameEngine.item.Consumable;
import gameEngine.item.Item;
import gameEngine.item.Weapon;
import gameEngine.map.Map;
import gameEngine.sprite.AnimatedSprite;
import gameEngine.sprite.Sprite;
import gameEngine.util.Box;
import gameEngine.util.Position;
import gameEngine.util.Rectangle;

/**
 * Need to implement equiping of weapon then test if attacking works because
 * right now null pointer exeception when trying to find weapon range
 * 
 * @author Gabriel Tennent
 *
 */

public class Player implements Character, GameObject {

	private int hp;
	private int hpMax;
	private int gold;
	private int x;
	private int y;

	/**
	 * Item and bounding box variables
	 */
	private boolean primaryEquipped;
	private Weapon primaryWeapon;
	private Inventory inventory;
	private Sprite spriteImage;
	private Rectangle playerBoundBox;
	private Box pickUpRadius;

	public static final int SPEED = 2;
	public static final int SPRINT = 7;
	private int zoom;
	private Queue<Integer> damageQueue;

	private Sprite sprite;
	private AnimatedSprite animatedSprite = null;
	// 0 == right, 1 == left, 2 == up, 3 == down. This is based off the Player.png
	private int direction = 0;

	public Player(Position center, int stamina, Sprite playerSprite, int zoom, int hp, int hpMax) {
		this.damageQueue = new PriorityQueue<>();
		this.spriteImage = playerSprite;
		this.zoom = zoom;
		this.zoom = 3;
		this.x = center.getX() - (playerSprite.getWidth() / 2 * zoom);
		this.y = center.getY() - (playerSprite.getHeight() / 2 * zoom);
		this.hp = hp;
		this.hpMax = hpMax;

		// Pick up radius of the player starts half of the players width to the left of
		// the player and
		// extends to twice the players width meaning the pick up radius is twice the
		// size of the player
		this.pickUpRadius = new Box(x - ((playerSprite.getWidth() * zoom)), y - ((playerSprite.getHeight() * zoom)),
				(playerSprite.getWidth() * zoom) * 4, (playerSprite.getHeight() * zoom) * 4);
		this.inventory = new Inventory(20);
		this.primaryEquipped = true;
		this.primaryWeapon = new Weapon("Start", 0, 0, 10, 300, 10, playerSprite);
		this.sprite = playerSprite;
		if (sprite != null && sprite instanceof AnimatedSprite) {
			this.animatedSprite = (AnimatedSprite) playerSprite;
		}
		this.playerBoundBox = new Rectangle(x + 10, y + 63, animatedSprite.getWidth(),
				(int) (animatedSprite.getHeight() * 0.4));
		this.playerBoundBox.generateGraphics(Color.blue.getRGB());
		updateDirection();
	}

	private void updateDirection() {
		if (animatedSprite != null) {
			animatedSprite.setAnimationRange(direction * 8, direction * 8 + 7);
		}
	}

	@Override
	public int heavyAttack() {
		return primaryWeapon.getDamage();
	}

	@Override
	public void walkLeft() {
		this.x -= SPEED;
		this.playerBoundBox.setX(this.playerBoundBox.getX() - SPEED);
	}

	@Override
	public void walkRight() {
		this.x += SPEED;
		this.playerBoundBox.setX(this.playerBoundBox.getX() + SPEED);
	}

	@Override
	public void walkUp() {
		this.y -= SPEED;
		this.playerBoundBox.setY(this.playerBoundBox.getY() - SPEED);
	}

	@Override
	public void walkDown() {
		this.y += SPEED;
		this.playerBoundBox.setY(this.playerBoundBox.getY() + SPEED);
	}

	public void runLeft() {
		this.x -= SPRINT;
		this.playerBoundBox.setX(this.playerBoundBox.getX() - SPRINT);
	}

	public void runRight() {
		this.x += SPRINT;
		this.playerBoundBox.setX(this.playerBoundBox.getX() + SPRINT);
	}

	public void runUp() {
		this.y -= SPRINT;
		this.playerBoundBox.setY(this.playerBoundBox.getY() - SPRINT);
	}

	public void runDown() {
		this.y += SPRINT;
		this.playerBoundBox.setY(this.playerBoundBox.getY() + SPRINT);
	}

	public void interact() {

	}

	/**
	 * Renders the players current position
	 */
	@Override
	public void render(Renderer renderer, int xZoom, int yZoom) {
		// renderer.renderArray(spriteImage.getPixels(), spriteImage.getWidth(),
		// spriteImage.getWidth(), x, y, zoom, zoom);

		// introducing the animated sprite here. initially rendering a static sprite.
		renderer.renderRectangle(playerBoundBox, xZoom, yZoom);
		if (animatedSprite != null)
			renderer.renderSprite(animatedSprite, x + animatedSprite.getWidth() / 2, y + animatedSprite.getHeight() / 2,
					xZoom, yZoom);
		else if (sprite != null)
			renderer.renderSprite(sprite, x + animatedSprite.getWidth() / 2, y + animatedSprite.getHeight() / 2, xZoom,
					yZoom);
		else
			renderer.renderArray(spriteImage.getPixels(), spriteImage.getWidth(), spriteImage.getWidth(), x, y, zoom,
					zoom);
	}

	/**
	 * Updates the player dependant on the key and mouse controllers in the engine
	 * and what booleans have been activated via the user pushing keys / the mouse.
	 */
	@Override
	public void update(Engine engine) {
		KeyController keyBinds = engine.getKeyBinds();
		Map currentMap = engine.getCurrentMap();
		MouseController mouseActions = engine.getMouseListener();

		int width = spriteImage.getWidth() * zoom;
		int height = spriteImage.getHeight() * zoom;

		// for the player animated sprites
		boolean didMove = false;
		int newDirection = direction;

		Box curBox = playerBoundBox;
		
		/**
		 * Player walking connection with key controller: Also checks for player
		 * connection with walls
		 */
		if (!keyBinds.isRun()) {
			if (keyBinds.isUp()) {
				Box up = new Box(curBox.getX(), curBox.getY(), curBox.getWidth(), curBox.getHeight());
				up.setY(up.getY()-SPEED);
				if(checkBoundry(currentMap, up)) {
					newDirection = 2;
					didMove = true;
					walkUp();
				}
			}
			if (keyBinds.isDown()) {
				Box down = new Box(curBox.getX(), curBox.getY(), curBox.getWidth(), curBox.getHeight());
				down.setY(down.getY()+SPEED);
				if(checkBoundry(currentMap, down)) {
					newDirection = 3;
					didMove = true;
					walkDown();
				}
			}
			if (keyBinds.isLeft()) {
				Box left = new Box(curBox.getX(), curBox.getY(), curBox.getWidth(), curBox.getHeight());
				left.setX(left.getX()-SPEED);
				if(checkBoundry(currentMap, left)) {
					newDirection = 1;
					didMove = true;
					walkLeft();
				}
			}
			if (keyBinds.isRight()) {
				Box right = new Box(curBox.getX(), curBox.getY(), curBox.getWidth(), curBox.getHeight());
				right.setX(right.getX()+SPEED);
				if(checkBoundry(currentMap, right)) {
					newDirection = 0;
					didMove = true;
					walkRight();
				}
			}
		}

		/**
		 * Player running connection with key controller: Also checks for player
		 * connection with walls
		 */
		if (keyBinds.isRun() ) {
			if (keyBinds.isUp()) {
				Box up = new Box(curBox.getX(), curBox.getY(), curBox.getWidth(), curBox.getHeight());
				up.setY(up.getY()-SPRINT);
				if(checkBoundry(currentMap, up)) {
					newDirection = 2;
					didMove = true;
					runUp();
				}
			}
			if (keyBinds.isDown()) {
				Box down = new Box(curBox.getX(), curBox.getY(), curBox.getWidth(), curBox.getHeight());
				down.setY(down.getY()+SPRINT);
				if(checkBoundry(currentMap, down)) {
					newDirection = 3;
					didMove = true;
					runDown();
				}
			}
			if (keyBinds.isLeft()) {
				Box left = new Box(curBox.getX(), curBox.getY(), curBox.getWidth(), curBox.getHeight());
				left.setX(left.getX()-SPRINT);
				if(checkBoundry(currentMap, left)) {
					newDirection = 1;
					didMove = true;
					runLeft();
				}
			}
			if (keyBinds.isRight()) {
				Box right = new Box(curBox.getX(), curBox.getY(), curBox.getWidth(), curBox.getHeight());
				right.setX(right.getX()+SPRINT);
				if(checkBoundry(currentMap, right)) {
					newDirection = 0;
					didMove = true;
					runRight();
				}
			}
		}

		// Checking if player is attempting to pick up and whether there is anything to
		// pick up
		if (keyBinds.isPickUp()) {
			this.pickUpRadius = new Box(x, y, (spriteImage.getWidth() * zoom), (spriteImage.getHeight() * zoom));
			// need to check if any of the items locations
			List<Item> itemsOnMap = engine.getCurrentMap().getItems();
			for (Item item : itemsOnMap) {
				Box itemPos = item.getBoundingBox();
				if (pickUpRadius.contains(itemPos)) {
					this.inventory.add(item);
					item.setPickedUp(true);
					itemsOnMap.remove(item);
					break;
				}
			}
		}

		/**
		 * Returns left clicks and their co-ordinates to tell where for the character to
		 * attack
		 */
		while (!mouseActions.getAttackPositions().isEmpty()) {
			// Gets the attack position from the mouse controller
			Position attackPos = mouseActions.getAttackPos();
			// Prevents player from being able to run and attack at the same time
			keyBinds.setRun(false);
			int mx = attackPos.getX();
			int my = attackPos.getY();
			if (primaryEquipped)
				attack(mx, my, engine);
		}

		/**
		 * Updates the players hpbar if they have taken damage
		 */
		if (!damageQueue.isEmpty()) {
			int damage = damageQueue.poll();
			this.hp += damage;
		}

		/**
		 * Attempts to use a players item to heal the player
		 */
		if (keyBinds.isUseItem()) {
			if (!this.inventory.getInventory().isEmpty()) {
				Consumable healthPot = (Consumable) this.inventory.returnFirstItem();
				this.heal(healthPot.getHealingStrength());
			}
			keyBinds.setUseItem(false);
		}

		/**
		 * Updates camera
		 */
		// only update the direction if the player moves in a different direction
		if (newDirection != direction) {
			direction = newDirection;
			updateDirection();
		}

		if (!didMove) {
			// makes sure that the sprite doesnt stop mid movement
			animatedSprite.reset();
		}

		this.updateCamera(engine.getRenderer().getCamera());

		// update the counter
		if (didMove) {
			animatedSprite.update(engine);
		}

		if (this.x >= 1940 && this.y <= 100) {
			engine.setCurrentMap(engine.getMapList().get(engine.getCurrentMapNumber()));
			this.x = 200;
			this.y = 200;
		}
	}

	/**
	 *
	 * @param mx
	 * @param my
	 * @param engine
	 */
	@Override
	public void attack(int mx, int my, Engine engine) {
		int width = spriteImage.getWidth() * zoom;
		int height = spriteImage.getHeight() * zoom;
		Position center = engine.getRenderer().getCamera().getCenter();
		List<GameObject> monsters = engine.getCurrentMap().getMonsters();
		// right attack calculated from the position of the camera
		if (mx > center.getX()) {
			// right side bounding box = to range of weapon and half player height
			Box rightPrimaryAttackRad = new Box(x, y, primaryWeapon.getRange(), height);
			// attackRight();
			for (GameObject _monster : monsters) {
				Monster monster = (Monster) _monster;
				if (rightPrimaryAttackRad.contains(monster.getBoundingBox())) {
					monster.damage(heavyAttack());
					System.out.println(monster.getHealth());
					checkForMonsterDeath(monster, monsters);
				}
			}
		}
		// left attack calculated from the position of the camera
		if (mx < center.getX()) {
			// left side bounding box = to range of weapon and half player height
			Box leftPrimaryAttackRad = new Box(x - primaryWeapon.getRange(), y, primaryWeapon.getRange() + width,
					height);
			// attackLeft();
			for (GameObject _monster : monsters) {
				Monster monster = (Monster) _monster;
				if (leftPrimaryAttackRad.contains(monster.getBoundingBox())) {
					monster.damage(heavyAttack());
					System.out.println(monster.getHealth());
					checkForMonsterDeath(monster, monsters);
				}
			}
		}
	}

	public void checkForMonsterDeath(Monster monster, List<GameObject> monsters) {
		if (monster.getHealth() <= 0) {
			this.gold = this.gold + 1;
			removeMonsterFromMap(monster, monsters);
		}

	}

	public void removeMonsterFromMap(Monster monster, List<GameObject> monsters) {
		monsters.remove(monster);
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

	public boolean isDead() {
		if (hp <= 0)
			return true;
		else
			return false;
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
	 * Heals player with negative numbers
	 *
	 * @param i
	 */
	public void heal(int i) {
		this.damageQueue.offer(i);
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
		gold = gold + amountRecieved;
		;
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

	public Box getPlayerBoundBox() {
		return playerBoundBox;
	}

	public void setPlayerBoundBox(Box playerBoundBox) {
		this.playerBoundBox = (Rectangle) playerBoundBox;
	}

	public Inventory getInventory() {
		return inventory;
	}

	public void setInventory(Inventory inventory) {
		this.inventory = inventory;
	}

}

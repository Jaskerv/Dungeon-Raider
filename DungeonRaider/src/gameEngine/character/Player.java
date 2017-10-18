package gameEngine.character;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.Iterator;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Queue;

import gameEngine.controller.KeyController;
import gameEngine.engine.Engine;
import gameEngine.engine.GameObject;
import gameEngine.engine.Renderer;
import gameEngine.item.Consumable;
import gameEngine.item.Item;
import gameEngine.item.Weapon;
import gameEngine.map.Map;
import gameEngine.sprite.AnimatedSprite;
import gameEngine.sprite.Sprite;
import gameEngine.sprite.SpriteSheet;
import gameEngine.util.Box;
import gameEngine.util.Position;
import gameEngine.util.Rectangle;
import library3.Movement;
import library4.Saveable;

/**
 * Need to implement equiping of weapon then test if attacking works because
 * right now null pointer exeception when trying to find weapon range
 *
 * @author Gabriel Tennent
 *
 */

public class Player implements Character, GameObject, Saveable {

	private int hp;
	private int hpMax;
	private int gold;
	private int x;
	private int y;

	/**
	 * Item and bounding box variables
	 */
	private Weapon primaryWeapon;
	private Inventory inventory;
	private Sprite spriteImage;
	private Rectangle playerBoundBox;
	private int zoom;
	private Queue<Integer> damageQueue;

	private Sprite sprite;
	private AnimatedSprite animatedSprite = null;
	// 0 == right, 1 == left, 2 == up, 3 == down. This is based off the
	// Player.png
	private int direction = 0;

	public Player(Position center, int stamina, int zoom, int hp, int hpMax) {
		this.damageQueue = new PriorityQueue<>();
		loadSprites();
		this.zoom = zoom;
		this.zoom = 3;
		this.x = center.getX() - (spriteImage.getWidth() / 2 * zoom);
		this.y = center.getY() - (spriteImage.getHeight() / 2 * zoom);
		this.hp = hp;
		this.hpMax = hpMax;
		this.inventory = new Inventory(20);
		this.primaryWeapon = new Weapon("Start", 0, 0, 10, 300, 10,
				spriteImage);
		this.sprite = spriteImage;
		if (sprite != null && sprite instanceof AnimatedSprite) {
			this.animatedSprite = (AnimatedSprite) spriteImage;
		}
		this.playerBoundBox = new Rectangle(x + 16, y + 63,
				(int) (animatedSprite.getWidth() * .8),
				(int) (animatedSprite.getHeight() * 0.4) - 1);
		this.playerBoundBox.generateGraphics(Color.green.getRGB());
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
		this.x -= Movement.WALK_SPEED;
		// this.playerBoundBox.setX(this.playerBoundBox.getX() - SPEED);
		this.playerBoundBox.setX(Movement.walkLeft(this.playerBoundBox.getX()));
	}

	@Override
	public void walkRight() {
		this.x += Movement.WALK_SPEED;
		// this.playerBoundBox.setX(this.playerBoundBox.getX() + SPEED);
		this.playerBoundBox
				.setX(Movement.walkRight(this.playerBoundBox.getX()));
	}

	@Override
	public void walkUp() {
		this.y -= Movement.WALK_SPEED;
		// this.playerBoundBox.setY(this.playerBoundBox.getY() - SPEED);
		this.playerBoundBox.setY(Movement.walkUp(this.playerBoundBox.getY()));
	}

	@Override
	public void walkDown() {
		this.y += Movement.WALK_SPEED;
		// this.playerBoundBox.setY(this.playerBoundBox.getY() + SPEED);
		this.playerBoundBox.setY(Movement.walkDown(this.playerBoundBox.getY()));
	}

	public void runLeft() {
		this.x -= Movement.SPRINT_SPEED;
		// this.playerBoundBox.setX(this.playerBoundBox.getX() - SPRINT);
		this.playerBoundBox
				.setX(Movement.sprintLeft(this.playerBoundBox.getX()));
	}

	public void runRight() {
		this.x += Movement.SPRINT_SPEED;
		// this.playerBoundBox.setX(this.playerBoundBox.getX() + SPRINT);
		this.playerBoundBox
				.setX(Movement.sprintRight(this.playerBoundBox.getX()));
	}

	public void runUp() {
		this.y -= Movement.SPRINT_SPEED;
		// this.playerBoundBox.setY(this.playerBoundBox.getY() - SPRINT);
		this.playerBoundBox.setY(Movement.sprintUp(this.playerBoundBox.getY()));
	}

	public void runDown() {
		this.y += Movement.SPRINT_SPEED;
		// this.playerBoundBox.setY(this.playerBoundBox.getY() + SPRINT);
		this.playerBoundBox
				.setY(Movement.sprintDown(this.playerBoundBox.getY()));
	}

	public void interact() {

	}

	/**
	 * Renders the players current position
	 */
	@Override
	public void render(Renderer renderer, int xZoom, int yZoom) {
		// introducing the animated sprite here. initially rendering a static
		// sprite.
		renderer.renderRectangle(playerBoundBox, zoom, zoom);
		if (animatedSprite != null)
			renderer.renderSprite(animatedSprite,
					x + animatedSprite.getWidth() / 2,
					y + animatedSprite.getHeight() / 2, xZoom, yZoom);
		else if (sprite != null)
			renderer.renderSprite(sprite, x + animatedSprite.getWidth() / 2,
					y + animatedSprite.getHeight() / 2, xZoom, yZoom);
		else
			renderer.renderArray(spriteImage.getPixels(),
					spriteImage.getWidth(), spriteImage.getWidth(), x, y, zoom,
					zoom);
	}

	/**
	 * Updates the player dependant on the key and mouse controllers in the
	 * engine and what booleans have been activated via the user pushing keys /
	 * the mouse.
	 */
	@Override
	public void update(Engine engine) {
		KeyController keyBinds = engine.getKeyBinds();
		Map currentMap = engine.getCurrentMap();

		// for the player animated sprites
		boolean didMove = false;
		int newDirection = direction;
		boolean couldntRun = false;

		Box curBox = this.playerBoundBox;

		/**
		 * Player running connection with key controller: Also checks for player
		 * connection with walls
		 */
		if (keyBinds.isRun()) {
			if (keyBinds.isUp()) {
				Box up = new Box(curBox.getX(), curBox.getY(),
						curBox.getWidth() * zoom, curBox.getHeight() * zoom);
				// up.setY(up.getY() - SPRINT);
				up.setY(Movement.sprintUp(up.getY()));
				if (checkBoundry(currentMap, up)) {
					newDirection = 2;
					didMove = true;
					runUp();
				} else {
					couldntRun = true;
				}
			}
			if (keyBinds.isDown()) {
				Box down = new Box(curBox.getX(), curBox.getY(),
						curBox.getWidth() * zoom, curBox.getHeight() * zoom);
				// down.setY(down.getY() + SPRINT);
				down.setY(Movement.sprintDown(down.getY()));
				if (checkBoundry(currentMap, down)) {
					newDirection = 3;
					didMove = true;
					runDown();
				} else {
					couldntRun = true;
				}
			}
			if (keyBinds.isLeft()) {
				Box left = new Box(curBox.getX(), curBox.getY(),
						curBox.getWidth() * zoom, curBox.getHeight() * zoom);
				// left.setX(left.getX() - SPRINT);
				left.setX(Movement.sprintLeft(left.getX()));
				if (checkBoundry(currentMap, left)) {
					newDirection = 1;
					didMove = true;
					runLeft();
				} else {
					couldntRun = true;
				}
			}
			if (keyBinds.isRight()) {
				Box right = new Box(curBox.getX(), curBox.getY(),
						curBox.getWidth() * zoom, curBox.getHeight() * zoom);
				// right.setX(right.getX() + SPRINT);
				right.setX(Movement.sprintRight(right.getX()));
				if (checkBoundry(currentMap, right)) {
					newDirection = 0;
					didMove = true;
					runRight();
				} else {
					couldntRun = true;
				}
			}
		}

		/**
		 * Player walking connection with key controller: Also checks for player
		 * connection with walls
		 */
		if (!keyBinds.isRun() || couldntRun) {
			if (keyBinds.isUp()) {
				Box up = new Box(curBox.getX(),
						curBox.getY() - Movement.WALK_SPEED,
						curBox.getWidth() * zoom, curBox.getHeight() * zoom);
				if (checkBoundry(currentMap, up)) {
					newDirection = 2;
					didMove = true;
					walkUp();
				}
			}
			if (keyBinds.isDown()) {
				Box down = new Box(curBox.getX(),
						curBox.getY() + Movement.WALK_SPEED,
						curBox.getWidth() * zoom, curBox.getHeight() * zoom);
				if (checkBoundry(currentMap, down)) {
					newDirection = 3;
					didMove = true;
					walkDown();
				}
			}
			if (keyBinds.isLeft()) {
				Box left = new Box(curBox.getX() - Movement.WALK_SPEED,
						curBox.getY(), curBox.getWidth() * zoom,
						curBox.getHeight() * zoom);
				if (checkBoundry(currentMap, left)) {
					newDirection = 1;
					didMove = true;
					walkLeft();
				}
			}
			if (keyBinds.isRight()) {
				Box right = new Box(curBox.getX() + Movement.WALK_SPEED,
						curBox.getY(), curBox.getWidth() * zoom,
						curBox.getHeight() * zoom);
				if (checkBoundry(currentMap, right)) {
					newDirection = 0;
					didMove = true;
					walkRight();
				}
			}
			couldntRun = false;
		}

		// Checking if player is attempting to pick up and whether there is
		// anything to
		// pick up
		if (keyBinds.isPickUp()) {
			// this.pickUpRadius = new Rectangle(x, y, (spriteImage.getWidth() *
			// zoom),
			// (spriteImage.getHeight() * zoom));
			// need to check if any of the items locations
			List<Item> itemsOnMap = engine.getCurrentMap().getItems();
			for (Item item : itemsOnMap) {
				Box itemPos = item.getBoundingBox();
				if (this.playerBoundBox.contains(itemPos)) {
					this.inventory.add(item);
					item.setPickedUp(true);
					itemsOnMap.remove(item);
					break;
				}
			}
		}

		if (keyBinds.isAttak()) {

			List<GameObject> monsters = engine.getCurrentMap().getMonsters();
			if (monsters.size() == 0) {
				// break;
			}
			Iterator<GameObject> iterator = monsters.iterator();
			while (iterator.hasNext()) {
				Monster mon = (Monster) iterator.next();
				// if player is looking to the right
				if (this.direction == 0) {

					attackMonsterToTheRight(mon, monsters, iterator);
					// if the player is looking left
				} else if (this.direction == 1) {
					attackMonstertoTheLeft(mon, monsters, iterator);
					// if the player is looking up
				} else if (this.direction == 2) {
					attackMonsterAbove(mon, monsters, iterator);
				} else if (this.direction == 3) {
					attackMonsterBelow(mon, monsters, iterator);
				}
			}

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
				Consumable healthPot = (Consumable) this.inventory
						.returnFirstItem();
				this.heal(healthPot.getHealingStrength());
			}
			keyBinds.setUseItem(false);
		}

		/**
		 * Updates camera
		 */
		// only update the direction if the player moves in a different
		// direction
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
			if (engine.getCurrentMapNumber() == 3) {

				// so it doesn't go to a non-existing map

				return;
			}
			engine.setCurrentMap(
					engine.getMapList().get(engine.getCurrentMapNumber()));
			this.x = 200;
			this.y = 200;
			this.playerBoundBox = new Rectangle(x + 10, y + 63,
					animatedSprite.getWidth(),
					(int) (animatedSprite.getHeight() * 0.4));
			this.playerBoundBox.generateGraphics(Color.blue.getRGB());
		}
	}

	public void attackMonsterToTheRight(Monster mon, List<GameObject> monsters,
			Iterator<GameObject> iterator) {

		int playerX = this.playerBoundBox.getX();
		int playerY = this.playerBoundBox.getY();
		int playerYHeight = playerY + this.playerBoundBox.getHeight();
		int monsterX = mon.getBoundingBox().getX();
		int monsterXWidth = monsterX + mon.getBoundingBox().getWidth();
		int monsterY = mon.getBoundingBox().getY();
		// int monsterYHeight = monsterY + mon.getBoundingBox().getHeight();
		int swordLength = 50;

		if ((playerX + swordLength >= monsterX)
				&& (playerX + swordLength <= monsterXWidth)) {
			if ((playerY >= monsterY) || (playerYHeight >= monsterY)) {
				System.out.println("Fucking REEEEEEE");
				mon.setHealth(0);
				checkForMonsterDeath(mon, monsters, iterator);
			}
		}
	}

	public void attackMonstertoTheLeft(Monster mon, List<GameObject> monsters,
			Iterator<GameObject> iterator) {

		int playerX = this.playerBoundBox.getX();
		int playerY = this.playerBoundBox.getY();
		int playerYHeight = playerY + this.playerBoundBox.getHeight();
		int monsterX = mon.getBoundingBox().getX();
		int monsterXWidth = monsterX + mon.getBoundingBox().getWidth();
		int monsterY = mon.getBoundingBox().getY();
		// int monsterYHeight = monsterY + mon.getBoundingBox().getHeight();
		int swordLength = 50;

		if ((playerX - swordLength >= monsterX)
				&& (playerX - swordLength <= monsterXWidth)) {
			if ((playerY >= monsterY) || (playerYHeight >= monsterY)) {
				System.out.println("Fucking REEEEEEE");
				mon.setHealth(0);
				checkForMonsterDeath(mon, monsters, iterator);
			}
		}

	}

	public void attackMonsterAbove(Monster mon, List<GameObject> monsters,
			Iterator<GameObject> iterator) {

		int playerX = this.playerBoundBox.getX();
		int playerXWidth = playerX + this.playerBoundBox.getWidth();
		int playerY = this.playerBoundBox.getY();
		// int playerYHeight = playerY + this.playerBoundBox.getHeight();
		int monsterX = mon.getBoundingBox().getX();
		// int monsterXWidth = monsterX + mon.getBoundingBox().getWidth();
		int monsterY = mon.getBoundingBox().getY();
		int monsterYHeight = monsterY + mon.getBoundingBox().getHeight();
		int swordLength = 50;

		if ((playerY - swordLength >= monsterY)
				&& (playerY - swordLength <= monsterYHeight)) {
			if ((playerX >= monsterX) || (playerXWidth >= monsterX)) {
				System.out.println("Fucking REEEEEEE");
				mon.setHealth(0);
				checkForMonsterDeath(mon, monsters, iterator);
			}
		}

	}

	public void attackMonsterBelow(Monster mon, List<GameObject> monsters,
			Iterator<GameObject> iterator) {

		int playerX = this.playerBoundBox.getX();
		int playerXWidth = playerX + this.playerBoundBox.getWidth();
		int playerY = this.playerBoundBox.getY();
		// int playerYHeight = playerY + this.playerBoundBox.getHeight();
		int monsterX = mon.getBoundingBox().getX();
		// int monsterXWidth = monsterX + mon.getBoundingBox().getWidth();
		int monsterY = mon.getBoundingBox().getY();
		int monsterYHeight = monsterY + mon.getBoundingBox().getHeight();
		int swordLength = 50;

		if ((playerY + swordLength >= monsterY)
				&& (playerY + swordLength <= monsterYHeight)) {
			if ((playerX >= monsterX) || (playerXWidth >= monsterX)) {
				System.out.println("Fucking REEEEEEE");
				mon.setHealth(0);
				checkForMonsterDeath(mon, monsters, iterator);
			}
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

	}

	public void checkForMonsterDeath(Monster monster, List<GameObject> monsters,
			Iterator<GameObject> iter) {
		if (monster.getHealth() <= 0) {
			this.gold = this.gold + 1;
			System.out.println("monster died");
			iter.remove();
		}

	}

	/**
	 * Updates the camera's position to center the player
	 *
	 * @param camera
	 */
	private void updateCamera(Box camera) {
		camera.setX(x - (camera.getWidth() / 2)
				+ (this.spriteImage.getWidth() * zoom / 2));
		camera.setY(y - (camera.getHeight() / 2)
				+ (this.spriteImage.getHeight() * zoom / 2));
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
		return Movement.WALK_SPEED;
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

	@Override
	public String save() {
		String s = "Player	{\n";
		s += "int	hp	" + hp + "\n";
		s += "int	hpMax	" + hpMax + "\n";
		s += "int	gold	" + gold + "\n";
		s += "int	x	" + x + "\n";
		s += "int	y	" + y + "\n";
		s += "}	\n";

		return s;
	}

	@Override
	public void load(File file) {
		// TODO Auto-generated method stub

	}

	public Weapon getPrimaryWeapon() {
		return primaryWeapon;
	}

	private void loadSprites() {
		BufferedImage playerSheetImage = Engine
				.loadImage("resources/images/Player.png");
		SpriteSheet playerSheet = new SpriteSheet(playerSheetImage);
		playerSheet.loadSprites(20, 26);

		AnimatedSprite playerAnimations = new AnimatedSprite(playerSheet, 5);
		this.spriteImage = playerAnimations;
	}
}
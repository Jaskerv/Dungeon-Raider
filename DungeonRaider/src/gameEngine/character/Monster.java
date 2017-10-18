package gameEngine.character;

import java.awt.Color;
import java.util.Iterator;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Queue;

import gameEngine.engine.Engine;
import gameEngine.engine.GameObject;
import gameEngine.engine.Renderer;
import gameEngine.map.Map;
import gameEngine.sprite.Sprite;
import gameEngine.sprite.SpriteSheet;
import gameEngine.util.Box;
import gameEngine.util.Position;
import gameEngine.util.Rectangle;
import library3.Movement;
import library5.StatModifier;

/**
 * Monster class
 */
public class Monster implements Character, GameObject {

	private String name;

	private int x;
	private int y;

	private int health;
	private Sprite spriteImage;

	private int speed;

	private static final int ZOOM = 5;
	private static final String SPRITE_SHEET_2_PATH = "resources/"
			+ "tiles/DungeonTileset4.png";

	private static final SpriteSheet SPRITE_SHEET_2 = new SpriteSheet(
			Engine.loadImage(SPRITE_SHEET_2_PATH));

	private int realHeight;
	private int realWidth;

	private Queue<Integer> damageQueue;

	private Rectangle boundingBox;

	private int attackTimer;

	private int ID;

	/**
	 * Monster
	 */
	public Monster(String name, int x, int y, int speed, int health, int ID,
			Sprite sprite) {
		SPRITE_SHEET_2.loadSprites(16, 16);
		this.name = name;
		this.x = x;
		this.y = y;
		this.speed = speed;
		this.spriteImage = sprite;
		this.health = health;
		this.ID = ID;
		this.realHeight = spriteImage.getHeight() * ZOOM;
		this.realWidth = spriteImage.getWidth() * ZOOM;
		this.damageQueue = new PriorityQueue<>();
		this.boundingBox = new Rectangle(this.x,
				this.y + (int) (this.realHeight * 0.7), this.realWidth,
				(int) (this.realHeight * 0.3));

		this.boundingBox.generateGraphics(Color.BLUE.getRGB());
		this.attackTimer = 0;
	}

	@Override
	public int heavyAttack() {
		// TODO Auto-generated method stub
		return 1;
	}

	@Override
	public void walkLeft() {
		this.x -= Movement.WALK_SPEED;
		// this.playerBoundBox.setX(this.playerBoundBox.getX() - SPEED);
		this.boundingBox
				.setX(Movement.walkLeft(this.boundingBox.getX(), this.speed));
	}

	@Override
	public void walkRight() {
		this.x += Movement.WALK_SPEED;
		// this.playerBoundBox.setX(this.playerBoundBox.getX() + SPEED);
		this.boundingBox
				.setX(Movement.walkRight(this.boundingBox.getX(), this.speed));
	}

	@Override
	public void walkUp() {
		this.y -= Movement.WALK_SPEED;
		// this.playerBoundBox.setY(this.playerBoundBox.getY() - SPEED);
		this.boundingBox
				.setY(Movement.walkUp(this.boundingBox.getY(), this.speed));
	}

	@Override
	public void walkDown() {
		this.y += Movement.WALK_SPEED;
		// this.playerBoundBox.setY(this.playerBoundBox.getY() + SPEED);
		this.boundingBox
				.setY(Movement.walkDown(this.boundingBox.getY(), this.speed));
	}

	@Override
	public void render(Renderer renderer, int xZoom, int yZoom) {
		renderer.renderRectangle(this.boundingBox, 1, 1);
		renderer.renderArray(spriteImage.getPixels(), spriteImage.getWidth(),
				spriteImage.getHeight(), x, y, ZOOM, ZOOM);
	}

	@Override
	public void update(Engine engine) {

		// Checks to see if player should attack
		checkAttack(engine);
		// Checks to see if player should move
		checkMovement(engine);
		// Checks to see if player has taken damage
		checkDamage();

	}

	@Override
	public void attack(int mx, int my, Engine engine) {
		// TODO Auto-generated method stub
		Player player = engine.getPlayer();
		engine.getPlayer().damage((int) StatModifier.calcDamage(heavyAttack(),
				player.getHp(), 0, 0));
	}

	public void checkAttack(Engine engine) {
		// Keeps track of how many updates have passed inbetween each attack so
		// monster cant
		// attack to quickly
		if (this.attackTimer < 10) {
			// cant attack yet
			attackTimer++;
		} else {
			// attacks now
			if (boundingBox.contains(engine.getPlayer().getPlayerBoundBox())) {
				attackTimer = 0;
				attack(x, y, engine);
			}
		}
	}

	public void checkDamage() {
		if (!damageQueue.isEmpty()) {
			this.health += damageQueue.poll();
		}
	}

	/**
	 * Calculates all of the monsters moving in the engine
	 *
	 * @param engine
	 *            is the engine of the game that contains of necessary elements
	 *            that need to be modified during movement
	 */
	public void checkMovement(Engine engine) {
		// Gets player Position
		Player player = engine.getPlayer();
		// Sets players x and y locations
		int playerX = engine.getPlayer().getPlayerBoundBox().getX();
		int playerY = engine.getPlayer().getPlayerBoundBox().getY();

		// Retusn the current map being loaded
		Map currentMap = engine.getCurrentMap();
		Box b = this.getBoundingBox();
		// Player is left move -> walk left
		if (playerX < b.getX()) {
			if (playerX > (b.getX() - speed)) {

			} else {
				Box next = this.boundingBox.clone();
				next.setX(b.getX() - speed);
				if (!player.getPlayerBoundBox().contains(next)) {
					if (!monsterCollision(engine, next))
						if (checkBoundry(currentMap, next))
							walkLeft();
				}
			}
		}
		// Player is right -> walk right
		else if (playerX > b.getX()) {
			if (playerX < (b.getX() + speed)) {

			} else {
				Box next = this.boundingBox.clone();
				next.setX(b.getX() + speed);
				if (!player.getPlayerBoundBox().contains(next)) {
					if (!monsterCollision(engine, next))
						if (checkBoundry(currentMap, next))
							walkRight();
				}
			}
		}
		// Player is below -> walk down
		if (playerY > b.getY()) {
			if (playerY < (b.getY() + speed)) {

			} else {
				Box next = this.boundingBox.clone();
				next.setY(b.getY() + speed);
				if (!player.getPlayerBoundBox().contains(next)) {
					if (!monsterCollision(engine, next))
						if (checkBoundry(currentMap, next))
							walkDown();
				}
			}
		}
		// Player is above -> walk above
		else if (playerY < b.getY()) {
			if (playerY > (b.getY() - speed)) {

			} else {
				Box next = this.boundingBox.clone();
				next.setY(b.getY() - speed);
				if (!player.getPlayerBoundBox().contains(next)) {
					if (!monsterCollision(engine, next))
						if (checkBoundry(currentMap, next))
							walkUp();
				}
			}
		}
	}

	public boolean monsterCollision(Engine engine, Box nextMovement) {
		// Returns all of the monsters in the engine
		List<GameObject> monsters = engine.getCurrentMap().getMonsters();
		Iterator<GameObject> iterator = monsters.iterator();
		// Iterating through all monsters
		boolean check = false;
		while (iterator.hasNext()) {
			Monster mon = (Monster) iterator.next();
			// Checking next monster isnt the current monster
			if (mon != this) {
				// checks to see whether this monsters bounding box contains the
				// next movement to be computed by the monster moving
				if (mon.getBoundingBox().contains(nextMovement)) {
					// Yes a collision will occur
					check = true;
				}
				continue;
			}
		}
		// collision will not occur
		return check;
	}

	/**
	 * Damages monster with positive numbers, heals monster with negative
	 * numbers
	 *
	 * @param i
	 */
	public void damage(int i) {
		this.damageQueue.offer(-i);
	}

	public Sprite getSpriteImage() {
		return spriteImage;
	}

	public void setSpriteImage(Sprite spriteImage) {
		this.spriteImage = spriteImage;
	}

	public int getHealth() {
		return health;
	}

	public void setHealth(int health) {
		this.health = health;
	}

	public int getID() {
		return ID;
	}

	public void setID(int iD) {
		ID = iD;
	}

	public void setBoundingBox(Rectangle boundingBox) {
		this.boundingBox = boundingBox;
	}

	public Box getBoundingBox() {
		return boundingBox;
	}

}

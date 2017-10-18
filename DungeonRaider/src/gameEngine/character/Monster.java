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
	private int height;
	private int width;
	private Queue<Integer> damageQueue;
	private Box boundingBox;
	private int attackTimer;
	private Rectangle rect;
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
		this.height = spriteImage.getHeight();
		this.width = spriteImage.getWidth();
		this.damageQueue = new PriorityQueue<>();
		this.boundingBox = new Box(this.x, this.y, this.width, this.height);
		this.attackTimer = 0;

		// testing monster bounding boxes
		this.rect = new Rectangle(this.x, this.y, this.width, this.height);
		this.rect.generateGraphics(Color.BLUE.getRGB());
	}

	@Override
	public int heavyAttack() {
		// TODO Auto-generated method stub
		return 1;
	}

	@Override
	public void walkLeft() {
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
	public void render(Renderer renderer, int xZoom, int yZoom) {
		renderer.renderRectangle(this.rect, ZOOM, ZOOM);
		renderer.renderArray(spriteImage.getPixels(), spriteImage.getWidth(),
				spriteImage.getHeight(), x, y, ZOOM, ZOOM);
	}

	@Override
	public void update(Engine engine) {
		// Gets player Position
		Player player = engine.getPlayer();
		Map currentMap = engine.getCurrentMap();
		int playerX = player.getX();// +((player.getSpriteImage().getWidth()*player.getZoom())/2);
		int playerY = player.getY();// +((player.getSpriteImage().getWidth()*player.getZoom()/2));

		// returns all of the monsters on the map
		List<GameObject> monsters = engine.getCurrentMap().getMonsters();

		// Updating monster attack range
		this.boundingBox = new Box(this.x, this.y, this.width, this.height);
		this.rect = new Rectangle(this.x, this.y, this.width, this.height);
		this.rect.generateGraphics(Color.BLUE.getRGB());

		// Keeps track of how many updates have passed inbetween each attack so
		// monster cant
		// attack to quickly
		if (this.attackTimer < 10) {
			// cant attack yet
			attackTimer++;
		} else {
			// attacks now
			if (boundingBox.contains(player.getPlayerBoundBox())) {
				attackTimer = 0;
				attack(x, y, engine);
			}
		}

		// player is to the left of this monster
		if (playerX < this.x) {
			if (!boundingBox.contains(player.getPlayerBoundBox())) {
				Box left = new Box(this.x - speed, this.y, this.width * ZOOM,
						this.height * ZOOM);
				if (checkBoundry(currentMap, left))
					walkLeft();
			}

		}
		if (playerX > this.x) {
			if (!boundingBox.contains(player.getPlayerBoundBox())) {
				Box right = new Box(this.x + speed, this.y, this.width * ZOOM,
						this.height * ZOOM);
				if (checkBoundry(currentMap, right))
					walkRight();
			}
		}
		// player is to the bottom of this monster
		if (playerY > this.y) {
			if (!boundingBox.contains(player.getPlayerBoundBox())) {
				Box down = new Box(this.x, this.y + speed, this.width * ZOOM,
						this.height * ZOOM);
				if (checkBoundry(currentMap, down))
					walkDown();
			}
		}
		if (playerY < this.y) {
			if (!boundingBox.contains(player.getPlayerBoundBox())) {
				Box up = new Box(this.x, this.y - speed, this.width * ZOOM,
						this.height * ZOOM);
				if (checkBoundry(currentMap, up))
					walkUp();
			}
		}

		if (!damageQueue.isEmpty()) {
			this.health += damageQueue.poll();
		}

	}

	@Override
	public void attack(int mx, int my, Engine engine) {
		// TODO Auto-generated method stub
		Player player = engine.getPlayer();
		engine.getPlayer().damage((int) StatModifier.calcDamage(heavyAttack(),
				player.getHp(), 0, 0));
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

	public Box getBoundingBox() {
		return boundingBox;
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

	public void setBoundingBox(Box boundingBox) {
		this.boundingBox = boundingBox;
	}

}

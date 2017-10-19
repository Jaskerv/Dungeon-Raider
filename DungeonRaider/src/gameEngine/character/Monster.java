package gameEngine.character;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
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

	// Monsters name
	private String name;

	// Monsters x and y coordinates
	private int x;
	private int y;

	// Monsters health
	private int health;
	private int healthMax;
	// Monsters sprite image
	private Sprite spriteImage;
	private BufferedImage image;
	private String path;

	// Monsters speed
	private int speed;

	// Monsters zoom file
	private static final int ZOOM = 5;

	// The monsters height and width based on the size of the monsters sprite
	// times by the amount that sprite is bring moved in
	private int realHeight;
	private int realWidth;

	// A queue keeping track of all of the damage the monster has taken
	private Queue<Integer> damageQueue;

	// A bounding box for the monsters movement collisions and a attack radius
	private Rectangle boundingBox;
	private Rectangle attackRadius;

	// Keeps track of the monsters last attack and when they can next attack
	private int attackTimer;
	// The amount of update methods called before it can attack again
	private int attackSpeed;

	// Amount of damage the monster does
	private int damage;
	// hp bar
	private Box hpBar;
	private int hpMax;

	/**
	 * Monster constructor
	 *
	 * @param name
	 *            The name of the monster
	 * @param x
	 *            The x coordinate of the monster
	 * @param y
	 *            The y coordinate of the monster
	 * @param speed
	 *            The speed of the monster
	 * @param health
	 *            The health of the monster. Starts at 100.
	 * @param damage
	 *            The damage of the monster
	 * @param attackSpeed
	 *            The attackSpeed of the monster
	 * @param sprite
	 *            The sprite to represent the monster
	 */
	public Monster(String name, int x, int y, int speed, int health, int damage,
			int attackSpeed, BufferedImage image, String path) {
		this.name = name;
		this.x = x;
		this.y = y;
		this.speed = speed;
		this.health = health;
		this.healthMax = health;
		this.damage = damage;
		this.image = image;
		this.path = path;
		this.spriteImage = new Sprite(image);
		this.attackSpeed = attackSpeed;

		// Generates height and width values that account for zoom
		this.realHeight = spriteImage.getHeight() * ZOOM;
		this.realWidth = spriteImage.getWidth() * ZOOM;
		this.hpMax = realWidth;
		// Initiates the collision radius of the monster
		this.boundingBox = new Rectangle(this.x,
				this.y + (int) (this.realHeight * 0.7), this.realWidth,
				(int) (this.realHeight * 0.3));
		this.boundingBox.generateGraphics(Color.BLUE.getRGB());

		// Initiates the attack radius of the monster
		this.attackRadius = new Rectangle(this.x - (this.realWidth),
				this.y - (this.realHeight),
				(int) (this.realWidth + (this.realWidth * 0.4)),
				(int) (this.realHeight + (this.realHeight * 0.4)));
		this.attackRadius.generateGraphics(Color.BLUE.getRGB());

		// Initiates attack timer and damage queue
		this.attackTimer = 0;
		this.damageQueue = new PriorityQueue<>();
	}

	@Override
	public int heavyAttack() {
		// TODO Auto-generated method stub
		return this.damage;
	}

	@Override
	public void walkLeft() {
		this.x -= speed;
		this.boundingBox
				.setX(Movement.walkLeft(this.boundingBox.getX(), this.speed));
	}

	@Override
	public void walkRight() {
		this.x += speed;
		this.boundingBox
				.setX(Movement.walkRight(this.boundingBox.getX(), this.speed));
	}

	@Override
	public void walkUp() {
		this.y -= speed;
		this.boundingBox
				.setY(Movement.walkUp(this.boundingBox.getY(), this.speed));
	}

	@Override
	public void walkDown() {
		this.y += speed;
		this.boundingBox
				.setY(Movement.walkDown(this.boundingBox.getY(), this.speed));
	}

	/**
	 * Render method for the chosen rectangle bounding box to display visually
	 *
	 * @param The
	 *            renderer that will render the monster
	 * @param The
	 *            scale multiplier for monster width
	 * @param The
	 *            scale multiplier for monster height
	 */
	@Override
	public void render(Renderer renderer, int xZoom, int yZoom) {
		BufferedImage img = new BufferedImage(
				(int) (realWidth + (realWidth * .5)),
				(int) (realHeight + (realHeight * .5)),
				BufferedImage.TYPE_INT_RGB);
		Graphics g = img.getGraphics();
		// Draw anything with graphics after this
		renderer.renderArray(spriteImage.getPixels(), spriteImage.getWidth(),
				spriteImage.getHeight(), x, y, ZOOM, ZOOM);
		drawMonsterHealthBar(g);
		Sprite s = new Sprite(img);
		renderer.renderSprite(s, x, (int) (y - (realHeight * .2)), 1, 1);
	}

	/**
	 *
	 *
	 * @param g
	 */
	public void drawMonsterHealthBar(Graphics g) {
		g.setColor(Color.RED);
		g.fillRect(0, 0, hpMax, 5);
		g.fillRect(0, 0, hpMax, 5);
		double hpPerc = ((double) health) / ((double) healthMax);
		int hpBar = (int) (hpPerc * (double) hpMax);
		if (hpBar <= hpMax && hpBar > 0) {
			g.setColor(Color.GREEN);
			g.fillRect(0, 0, hpBar, 5);
		}
	}

	/**
	 * Updates the monster based on recent events inside the engine
	 */
	@Override
	public void update(Engine engine) {

		// sets the new attack radius co-ordinates
		this.attackRadius.setX((int) (this.x - (this.realWidth * 0.2)));
		this.attackRadius.setY((int) (this.y - (this.realHeight * 0.2)));
		// Checks to see if monster should attack
		checkAttack(engine);
		// Checks to see if monster should move
		checkMovement(engine);
	}

	/**
	 * Deals damage to the player = to that of the monsters damage set
	 */
	@Override
	public void attack(Engine engine) {
		// TODO Auto-generated method stub
		engine.getPlayer().damage(heavyAttack());
	}

	/**
	 * Checks to see if the monster can attack and if so attempts to attack the
	 * player
	 *
	 * @param engine
	 *            is the game engine containing all the information needed to
	 *            calculate the attack
	 */
	public void checkAttack(Engine engine) {
		// Keeps track of how many updates have passed inbetween each attack so
		// monster cant
		// attack to quickly
		if (this.attackTimer < attackSpeed) {
			// cant attack yet
			attackTimer++;
		} else {
			// attacks now
			if (attackRadius.contains(engine.getPlayer().getPlayerBoundBox())) {
				attackTimer = 0;
				attack(engine);
			}
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
			// If the players distance is within the next movement
			if (playerX > (b.getX() - speed)) {

			} else {
				Box next = this.boundingBox.clone();
				next.setX(b.getX() - speed); // Creates a bounding box = to that
												// of the next movement
				if (!player.getPlayerBoundBox().contains(next)) { // Checks if
																	// the
																	// monster
																	// will
																	// collide
																	// with the
																	// player
					if (!monsterCollision(engine, next)) // Checks if the
															// monster will
															// collide with
															// another monster
						if (checkBoundry(currentMap, next)) // Checks if the
															// monster will
															// collide with a
															// wall
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

	/**
	 * Computes whether two monsters are colliding in the movement
	 *
	 * @param engine
	 *            is the game engine containing all the information needed to
	 *            calculate the collision
	 * @param nextMovement
	 *            is the next movement that could occur if there are no
	 *            collisions
	 * @return true if the is a collision false if there is not
	 */
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
	 * Adds damage into the monsters damage queue to be dealt to the monster
	 *
	 * @param i
	 *            the amount of damage being dealt.
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

	public void setBoundingBox(Rectangle boundingBox) {
		this.boundingBox = boundingBox;
	}

	public Box getBoundingBox() {
		return boundingBox;
	}

}

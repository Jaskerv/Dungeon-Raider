package gameEngine.character;

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
	private static final String SPRITE_SHEET_2_PATH =
			"resources/tiles/DungeonTileset4.png";
	private static final SpriteSheet SPRITE_SHEET_2 =
			new SpriteSheet(Engine.loadImage(SPRITE_SHEET_2_PATH));
	private int height;
	private int width;
	private Queue<Integer> damageQueue;
	private Box boundingBox;
	private int attackTimer;

	/**
	 * Monster
	 */
	public Monster(String name, int x, int y, int speed, int health, Sprite sprite) {
		SPRITE_SHEET_2.loadSprites(16, 16);
		this.name = name;
		this.x = x;
		this.y = y;
		this.speed = speed;
		this.spriteImage = sprite;
		this.health = health;
		this.height = spriteImage.getHeight()*ZOOM;
		this.width = spriteImage.getWidth()*ZOOM;
		this.damageQueue = new PriorityQueue<>();
		this.boundingBox = new Box(this.x, this.y, this.width, this.height);
		this.attackTimer = 0;
	}
	
	@Override
	public int lightAttack() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int heavyAttack() {
		// TODO Auto-generated method stub
		return 1;
	}

	@Override
	public void walkLeft() {
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


	@Override
	public void render(Renderer renderer, int xZoom, int yZoom) {
		renderer.renderArray(spriteImage.getPixels(), spriteImage.getWidth(),
				spriteImage.getHeight(), x, y, ZOOM, ZOOM);
	}

	@Override
	public void update(Engine engine) {
		//Gets player Position
		Player player = engine.getPlayer();
		Position playerPos = new Position(player.getX()+((player.getSpriteImage().getWidth()*player.getZoom())/2)
						, player.getY()+((player.getSpriteImage().getWidth()*player.getZoom()/2)));
			
				
		Map currentMap = engine.getCurrentMap();
		int playerX = player.getX();//+((player.getSpriteImage().getWidth()*player.getZoom())/2);
		int playerY = player.getY();//+((player.getSpriteImage().getWidth()*player.getZoom()/2));
		
		//Updating monster attack range
		this.boundingBox = new Box(this.x, this.y, this.width, this.height);
		
		//Keeps track of how many updates have passed inbetween each attack so monster cant
		//attack to quickly
		if(this.attackTimer < 10) {
			//cant attack yet
			attackTimer++;
		} else {
			//attacks now
			if(boundingBox.contains(player.getPlayerBoundBox())) {
				attackTimer = 0;
				attack(x, y, engine);
			}
		}		
		
		//player is to the left of this monster
		if (playerX < this.x) {
			if(checkBoundry(currentMap, x - speed, y + height/2))
				if(checkBoundry(currentMap, x - speed, y + height))
					walkLeft();
		}
		if (playerX > this.x) {
			if(checkBoundry(currentMap, x + width + speed, y + height/2))
				if(checkBoundry(currentMap, x + width + speed, y + height))
					walkRight();
		}
		//player is to the bottom of this monster
		if (playerY > this.y) {
			if(checkBoundry(currentMap, x + width, y + height + speed))
				if(checkBoundry(currentMap, x, y + height + speed))
					walkDown();
		}
		if (playerY < this.y) {
			if(checkBoundry(currentMap, x + width, y - speed + height/2))
				if(checkBoundry(currentMap, x, y - speed + height/2))
					walkUp();
		}
		
	}

	@Override
	public void attack(int mx, int my, Engine engine) {
		// TODO Auto-generated method stub
		Player player = engine.getPlayer();
		engine.getPlayer().damage((int) StatModifier.calcDamage(heavyAttack(), player.getHp(), 0, 0));
	}

	/**
	 * Damages monster with positive numbers, heals monster with negative numbers
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
}

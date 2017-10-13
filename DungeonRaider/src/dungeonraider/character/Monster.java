package dungeonraider.character;

import dungeonraider.engine.Engine;
import dungeonraider.engine.GameObject;
import dungeonraider.engine.Renderer;
import dungeonraider.sprite.Sprite;
import dungeonraider.sprite.SpriteSheet;

/**
 * Monster class
 */
public class Monster implements Character, GameObject {

	private String name;
	private int x;
	private int y;
	private Sprite spriteImage;
	private int speed;
	private static final int ZOOM = 5;
	private static final String SPRITE_SHEET_2_PATH =
			"resources/tiles/DungeonTileset4.png";
	private static final SpriteSheet SPRITE_SHEET_2 =
			new SpriteSheet(Engine.loadImage(SPRITE_SHEET_2_PATH));

	/**
	 * Monster
	 */
	public Monster(String name, int x, int y, Sprite spriteImage, int speed) {
		SPRITE_SHEET_2.loadSprites(16, 16);
		this.name = name;
		this.x = x;
		this.y = y;
		this.spriteImage = spriteImage;
		this.speed = speed;
	}
	


	public Sprite getSpriteImage() {
		return spriteImage;
	}



	public void setSpriteImage(Sprite spriteImage) {
		this.spriteImage = spriteImage;
	}



	@Override
	public int lightAttack() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int heavyAttack() {
		// TODO Auto-generated method stub
		return 0;
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
		int playerX = engine.getPlayer().getX();
		int playerY = engine.getPlayer().getY();
		//player is to the left of this monster
		if (playerX < this.x) {
			walkLeft();
		}
		if (playerX > this.x) {
			walkRight();
		}
		//player is to the bottom of this monster
		if (playerY > this.y) {
			walkDown();
		}
		if (playerY < this.y) {
			walkUp();
		}
	}


}

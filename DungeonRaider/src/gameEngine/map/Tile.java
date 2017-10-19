package gameEngine.map;

import java.util.Random;

import gameEngine.engine.Engine;
import gameEngine.item.Item;
import gameEngine.sprite.Sprite;
import gameEngine.sprite.SpriteSheet;
import gameEngine.util.Box;

/**
 * This represents a tile within the Map.
 *
 * @author harry
 *
 */
public class Tile {

	public enum Type {
		Floor,
		Wall,
		Teleporter,
		RandomTeleporter;
	}

	/** Type of tile */
	private Type type;
	/** Location, size */
	private int x, y;
	private final int width;
	private final int height;
	private int nextRandom = -1;
	/** Sprite image resources */
	private Sprite sprite;
	private static final String SPRITE_SHEET_1_PATH =
			"resources/tiles/DungeonTileset1.png";
	private static final String SPRITE_SHEET_2_PATH =
			"resources/tiles/DungeonTileset4.png";
	private static final SpriteSheet SPRITE_SHEET_1 =
			new SpriteSheet(Engine.loadImage(SPRITE_SHEET_1_PATH));
	private static final SpriteSheet SPRITE_SHEET_2 =
			new SpriteSheet(Engine.loadImage(SPRITE_SHEET_2_PATH));
	private Box boundingBox;
	/** If item is on top of this tile */
	private Item item = null;

	/**
	 * This initialises the tile instance. Each tile instance is contained
	 * inside the Map's 2D array (of tiles).
	 * @param symbol
	 * @param x
	 * @param y
	 * @param width
	 * @param height
	 * @param boundary
	 */
	public Tile(char symbol, int x, int y, int width, int height) {
		SPRITE_SHEET_1.loadSprites(16, 16);
		SPRITE_SHEET_2.loadSprites(16, 16);
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		this.sprite = getImage(symbol);
		this.boundingBox = new Box(this.x, this.y, this.width, this.height);
	}

	/**
	 * This method will find the appropriate image for the character symbol
	 * being parsed in. For example, a symbol 'W' indicates a Wall.
	 * @param symbol  the character
	 * @return  the sprite related to the character symbol
	 */
	public Sprite getImage(char symbol) {
		switch (symbol) {
			//standard centre grass tile
			case 'W':
				this.type = Type.Floor;
				return SPRITE_SHEET_1.getSprite(4, 8);
			//north wall
			case '1':
				this.type = Type.Wall;
				return SPRITE_SHEET_1.getSprite(0, 1);
			//west wall
			case '2':
				this.type = Type.Wall;
				return SPRITE_SHEET_1.getSprite(0, 1);
			//south wall
			case '3':
				this.type = Type.Wall;
				return SPRITE_SHEET_1.getSprite(0, 1);
			//east wall
			case '4':
				this.type = Type.Wall;
				return SPRITE_SHEET_1.getSprite(0, 1);
			case 'N':
				this.type = Type.Teleporter;
				return SPRITE_SHEET_1.getSprite(4, 7);
			case 'M':
				this.type = Type.RandomTeleporter;
				this.nextRandom = calculateTileRandom();
				return SPRITE_SHEET_1.getSprite(4, 7);
		}
		return null;
	}
	
	

	/**
	 * Calculates a value between 0-3.
	 * 0 indicates go back to tutorial map
	 * 1 indicates go back to the map after the tutorial map
	 * 2 indicates the player will die
	 * 3 indicates the player will win
	 * @return
	 */
	private int calculateTileRandom() {
		Random random = new Random();
		int min = 0;
		int max = 3;
		int result = min + random.nextInt(max - min + 1);
		return result;
	}

	public boolean contains(int x, int y) {
		return this.boundingBox.contains(x, y);
	}

	public boolean contains(Box box) {
		return this.boundingBox.contains(box);
	}

	/**
	 * Checks if the given tile is a wall
	 */
	public boolean checkWall(Tile tile) {
		return tile.isBoundary();
	}

	public int getWidth() { return width; }

	public int getHeight() { return height; }

	public Sprite getSprite() { return sprite; }

	public void setSprite(Sprite sprite) { this.sprite = sprite; }

	public int getX() { return x; }

	public void setX(int x) { this.x = x; }

	public int getY() { return y; }

	public void setY(int y) { this.y = y; }

	public boolean isBoundary() { return this.type == Type.Wall; }

	public void setBoundary(Type boundary) { this.type = boundary; }

	public boolean isTeleporter() { return this.type == Type.Teleporter; }

	public Item getItem() { return item; }

	public void setItem(Item item) { this.item = item; }

}

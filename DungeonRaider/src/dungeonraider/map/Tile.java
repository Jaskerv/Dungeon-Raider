package dungeonraider.map;

import dungeonraider.engine.Engine;
import dungeonraider.sprite.Sprite;
import dungeonraider.sprite.SpriteSheet;

/**
 * This represents a tile within the Map.
 *
 * @author harry
 *
 */
public class Tile {

	/** Location, size */
	private int x, y;
	private final int width;
	private final int height;
	private boolean boundary;
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
	public Tile(char symbol, int x, int y, int width, int height,
			boolean boundary) {
		SPRITE_SHEET_1.loadSprites(16, 16);
		SPRITE_SHEET_2.loadSprites(16, 16);
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		this.boundary = boundary;
		this.sprite = getImage(symbol);
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
				return SPRITE_SHEET_1.getSprite(4, 8);
			//north wall
			case '1':
				this.boundary = true;
				return SPRITE_SHEET_1.getSprite(0, 1);
			//west wall
			case '2':
				this.boundary = true;
				return SPRITE_SHEET_1.getSprite(0, 1);
			//south wall
			case '3':
				this.boundary = true;
				return SPRITE_SHEET_1.getSprite(0, 1);
			//east wall
			case '4':
				this.boundary = true;
				return SPRITE_SHEET_1.getSprite(0, 1);
			case 'T':
				return SPRITE_SHEET_2.getSprite(14, 11);
		}
		return null;
	}

	public int getWidth() { return width; }

	public int getHeight() { return height; }

	public Sprite getSprite() { return sprite; }

	public void setSprite(Sprite sprite) { this.sprite = sprite; }

	public int getX() { return x; }

	public void setX(int x) { this.x = x; }

	public int getY() { return y; }

	public void setY(int y) { this.y = y; }

}

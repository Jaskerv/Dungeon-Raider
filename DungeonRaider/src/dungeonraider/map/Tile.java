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
	private static final String SPRITE_SHEET_PATH = "resources/tiles/Tiles1.png";
	private Sprite sprite;
	private static final SpriteSheet SPRITE_SHEET =
			new SpriteSheet(Engine.loadImage(SPRITE_SHEET_PATH));

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
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		this.boundary = boundary;
		SPRITE_SHEET.loadSprites(16, 16);
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
				this.boundary = true;
				return SPRITE_SHEET.getSprite(0, 2);
			//north wall
			case '1':
				this.boundary = true;
				return SPRITE_SHEET.getSprite(5, 1);
			//west wall
			case '2':
				this.boundary = true;
				return SPRITE_SHEET.getSprite(4, 1);
			//south wall
			case '3':
				this.boundary = true;
				return SPRITE_SHEET.getSprite(5, 2);
			//east wall
			case '4':
				this.boundary = true;
				return SPRITE_SHEET.getSprite(6, 1);
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

package dungeonraider.map;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import dungeonraider.engine.Engine;

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
	private BufferedImage spriteImage;
	private static final String SPRITE_SHEET_PATH = "resources/tiles/Tiles1.png";
	private static final BufferedImage SPRITE_SHEET =
			Engine.loadImage(SPRITE_SHEET_PATH);

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
		this.spriteImage = getImage(symbol);
	}

	/**
	 * This method will find the appropriate image for the character symbol
	 * being parsed in. For example, a symbol 'W' indicates a Wall.
	 * @param symbol  the character
	 * @return  the image related to the character
	 */
	public BufferedImage getImage(char symbol) {
		switch (symbol) {
			case 'W':
				//grass tile for now
				return SPRITE_SHEET.getSubimage(0, 16, 16, 16);
		}
		return null;
	}

	/**
	 * Renders the tile image to the graphics
	 * @param g
	 */
	public void render(Graphics g) {
		g.drawImage(spriteImage, x, y, width, height, null);
	}

	public BufferedImage getSpriteImage() { return spriteImage; }

	public void setSpriteImage(BufferedImage spriteImage) {
		this.spriteImage = spriteImage;
	}

	public int getX() { return x; }

	public void setX(int x) { this.x = x; }

	public int getY() { return y; }

	public void setY(int y) { this.y = y; }

}

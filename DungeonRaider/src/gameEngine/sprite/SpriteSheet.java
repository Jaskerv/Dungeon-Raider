package gameEngine.sprite;

import java.awt.image.BufferedImage;

/**
 * This class imports a BufferedImage which it can sub image into different
 * sprites.
 *
 * @author Jono Yan
 *
 */
public class SpriteSheet {
	private int[] pixels;
	private BufferedImage sheet;
	public final int SIZEX;
	public final int SIZEY;
	private Sprite[] loadedSprites;
	private boolean spritesLoaded;

	private int spriteSizeX;

	public SpriteSheet(BufferedImage sheetImage) {
		this.sheet = sheetImage;
		this.SIZEX = sheetImage.getWidth();
		this.SIZEY = sheetImage.getHeight();
		this.loadedSprites = null;
		this.spritesLoaded = false;
		this.pixels = new int[SIZEX * SIZEY];
		this.pixels = sheetImage.getRGB(0, 0, SIZEX, SIZEY, pixels, 0, SIZEX);
	}

	/**
	 * Separates and loads sprites into the loadedSprites field
	 *
	 * @param spriteSizeX The width of the sprite sheet
	 * @param spriteSizeY The height of the sprite sheet
	 */
	public void loadSprites(int spriteSizeX, int spriteSizeY) {
		this.spriteSizeX = spriteSizeX;
		loadedSprites = new Sprite[(SIZEX / spriteSizeX)
				* (SIZEY / spriteSizeY)];

		int spriteID = 0;
		for (int y = 0; y < SIZEY; y += spriteSizeY) {
			for (int x = 0; x < SIZEX; x += spriteSizeX) {
				loadedSprites[spriteID] = new Sprite(this, x, y, spriteSizeX,
						spriteSizeY);
				spriteID++;
			}
		}

		spritesLoaded = true;
	}

	/**
	 * returns a sprite gathered from the loadedSprite field from the given x
	 * and y coordinates
	 *
	 * @param x
	 *            The column of the sprite that is returned
	 * @param y
	 *            The row of the sprite that is returned
	 * @return The sprite from the given x and y coordinates
	 */
	public Sprite getSprite(int x, int y) {
		if (spritesLoaded) {
			int spriteID = x + y * (SIZEX / spriteSizeX);

			if (spriteID < loadedSprites.length) {
				return loadedSprites[spriteID];
			} else {
				System.out.println("SpriteID of " + spriteID
						+ " is out of bounds with a length of "
						+ loadedSprites.length + ".");
			}

		} else {
			System.out.println(
					"SpriteSheet could not get a sprite with no loaded sprites.");
		}

		return null;
	}

	/**
	 * @return The array of sprites gathered from the sprite sheet.
	 */
	public Sprite[] getLoadedSprites() {
		return loadedSprites;
	}

	/**
	 * @return the array of int representing the pixels
	 */
	public int[] getPixels() {
		return pixels;
	}

	/**
	 * @return the bufferedImage of sprites
	 */
	public BufferedImage getSheet() {
		return sheet;
	}

}
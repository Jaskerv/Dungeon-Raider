package dungeonraider.sprite;

import java.awt.image.BufferedImage;

import dungeonraider.sprite.Sprite;

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

	public void loadSprites(int spriteSizeX, int spriteSizeY) {
		this.spriteSizeX = spriteSizeX;
		loadedSprites = new Sprite[(SIZEX / spriteSizeX) * (SIZEY / spriteSizeY)];

		int spriteID = 0;
		for (int y = 0; y < SIZEY; y += spriteSizeY) {
			for (int x = 0; x < SIZEX; x += spriteSizeX) {
				loadedSprites[spriteID] = new Sprite(this, x, y, spriteSizeX, spriteSizeY);
				spriteID++;
			}
		}

		spritesLoaded = true;
	}

	public Sprite getSprite(int x, int y) {
		if (spritesLoaded) {
			int spriteID = x + y * (SIZEX / spriteSizeX);

			if (spriteID < loadedSprites.length)
				return loadedSprites[spriteID];
			else
				System.out.println(
						"SpriteID of " + spriteID + " is out of bounds with a length of " + loadedSprites.length + ".");
		} else
			System.out.println("SpriteSheet could not get a sprite with no loaded sprites.");

		return null;
	}

	public Sprite[] getLoadedSprites() {
		return loadedSprites;
	}

	/**
	 * @return the pixels
	 */
	public int[] getPixels() {
		return pixels;
	}

	/**
	 * @return the sheet
	 */
	public BufferedImage getSheet() {
		return sheet;
	}

}
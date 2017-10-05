package dungeonraider.sprite;

import java.awt.image.BufferedImage;

/**
 * This is an individual sprite
 * 
 * @author Jono Yan
 *
 */
public class Sprite {
	private int width, height;
	private int[] pixels;

	public Sprite(SpriteSheet sheet, int startX, int startY, int width, int height) {
		this.width = width;
		this.height = height;
		this.pixels = new int[width * height];
		sheet.getSheet().getRGB(startX, startY, width, height, pixels, 0, width);
	}

	public Sprite(BufferedImage image) {
		width = image.getWidth();
		height = image.getHeight();
		pixels = new int[width * height];
		image.getRGB(0, 0, width, height, pixels, 0, width);
	}

	/**
	 * @return the width
	 */
	public int getWidth() {
		return width;
	}

	/**
	 * @return the height
	 */
	public int getHeight() {
		return height;
	}

	/**
	 * @return the pixels
	 */
	public int[] getPixels() {
		return pixels;
	}
	
	/**
	 * @param pixels the pixels to set
	 */
	public void setPixels(int[] pixels) {
		this.pixels = pixels;
	}

	public String toString() {
		StringBuilder s = new StringBuilder();
		for (int i = 0; i < pixels.length; i++) {
			s.append(pixels[i]);
		}
		return s.toString();
	}
}

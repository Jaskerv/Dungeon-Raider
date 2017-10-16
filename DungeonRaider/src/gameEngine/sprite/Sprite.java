package gameEngine.sprite;

import java.awt.image.BufferedImage;

import gameEngine.engine.Engine;

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

	// a default constructor
	public Sprite() {

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
	 * @param pixels
	 *            the pixels to set
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

	/**
	 * Draws the input sprite onto this sprite
	 * 
	 * @param sprite
	 */
	public void drawOnSprite(Sprite sprite, int xPos, int yPos, int xZoom, int yZoom) {
		int[] array = sprite.getPixels();
		int width = sprite.getWidth();
		int height = sprite.getHeight();
		for (int y = 0; y < sprite.height; y++)
			for (int x = 0; x < sprite.width; x++)
				for (int yZoomIndex = 0; yZoomIndex < yZoom; yZoomIndex++)
					for (int xZoomIndex = 0; xZoomIndex < xZoom; xZoomIndex++)
						setPixel(array[x + y * width], (x * xZoom) + xPos + xZoomIndex,
								(y * yZoom) + yPos + yZoomIndex);

	}

	/**
	 * Sets pixel in this pixels array
	 * 
	 * @param pixel
	 * @param x
	 * @param y
	 */
	private void setPixel(int pixel, int x, int y) {
		int pixelIndex = x + y * width;
		if (pixels.length > pixelIndex && !Engine.alpha.match(pixel)) {
			pixels[pixelIndex] = pixel;
		}
	}
}

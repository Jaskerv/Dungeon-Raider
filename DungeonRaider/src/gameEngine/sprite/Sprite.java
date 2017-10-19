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

	/**
	 * A default constructor
	 */
	public Sprite() {

	}

	/**
	 * Gets a sprite and renders it to the array of pixel values for drawing.
	 *
	 * @param sprite The sprite to be rendered into array of pixel values
	 * @param xPos The x position of the sprite
	 * @param yPos The y position of the sprite
	 * @param xZoom Scales the sprite in its width
	 * @param yZoom Scales the sprite in its height
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

	/**
	 * @return The width of the sprite
	 */
	public int getWidth() {
		return width;
	}

	/**
	 * @return The height of the sprite
	 */
	public int getHeight() {
		return height;
	}

	/**
	 * @return The array of pixel values for the sprite
	 */
	public int[] getPixels() {
		return pixels;
	}

	/**
	 * @param The array of pixel values for the sprite
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

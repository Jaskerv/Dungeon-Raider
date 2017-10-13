package gameEngine.util;

import gameEngine.engine.Engine;

/**
 * This is a visual representation of a rectangle
 *
 * @author Jono Yan
 *
 */
public class Rectangle extends Box {
	protected int[] pixels;

	public Rectangle(int x, int y, int width, int height) {
		super(x, y, width, height);
	}

	/**
	 * Generates a filled Rectangle
	 *
	 * @param color
	 */
	public void generateGraphics(int color) {
		pixels = new int[width * height];
		for (int y = 0; y < height; y++) {
			for (int x = 0; x < width; x++) {
				pixels[x + y * width] = color;
			}
		}
	}

	/**
	 * Generates a bordered Rectangle
	 *
	 * @param borderWidth
	 * @param color
	 */
	public void generateGraphics(int borderWidth, int color) {
		pixels = new int[width * height];
		for (int y = 0; y < height; y++) {
			for (int x = 0; x < width; x++) {
				if (x < borderWidth || (width - borderWidth) < x || y < borderWidth || (height - borderWidth) < y) {
					pixels[x + y * width] = color;
				} else {
					pixels[x + y * width] = Engine.alpha.getValue();
				}
			}
		}
	}

	/**
	 * @return the pixels
	 */
	public int[] getPixels() {
		if (pixels != null) {
			return pixels;
		} else {
			System.out.println("Error,attempted to get pixels without generating pixels");
			return null;
		}
	}

}

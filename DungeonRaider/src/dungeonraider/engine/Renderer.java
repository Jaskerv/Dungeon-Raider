package dungeonraider.engine;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;

/**
 * Renders view to window
 *
 * @author Jono Yan
 *
 */
public class Renderer {
	private BufferedImage view;
	private int[] pixels;
	private boolean black;

	public Renderer(int width, int height) {
		/** Creates view */
		this.view = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
		/** Creates array for pixels */
		pixels = ((DataBufferInt) view.getRaster().getDataBuffer()).getData();

		for (int row = 0; row < height; row++) {
			int color = (int) 0;
			for (int col = 0; col < width; col++) {
				pixels[row * width + col] = color;
			}
		}
		this.black = true;

	}

	/**
	 * This method will render pixel array in Renderer onto the screen
	 */
	public void render(Graphics g) {
		/** Draws image to JFrame */
		g.drawImage(view, 0, 0, view.getWidth(), view.getHeight(), null);

	}

	/**
	 * Render image to pixel array.
	 * 
	 * @param img
	 * @param x
	 * @param y
	 */
	public void renderImage(BufferedImage img, int xPos, int yPos) {
		int[] imgPixels = ((DataBufferInt) img.getRaster().getDataBuffer()).getData();
		for (int y = 0; y < img.getHeight(); y++) {
			for (int x = 0; x < img.getWidth(); x++) {
				pixels[(x + xPos) + (y + yPos) * view.getWidth()] = imgPixels[x + y * img.getWidth()];
			}
		}
	}

	/**
	 * When JFrame is resized, updates view and pixel
	 */
	public void updateSize(int width, int height) {
		/** Creates view */
		this.view = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
		/** Creates array for pixels */
		pixels = ((DataBufferInt) view.getRaster().getDataBuffer()).getData();
		for (int i = 0; i < pixels.length; i++) {
			int color = (int) (Math.random() * 0xFFFFFF);
			pixels[i] = color;
		}
		this.black = false;

	}
}

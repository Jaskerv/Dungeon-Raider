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
	 * Renders pixel to the screen
	 */
	public void render(Graphics g) {
		/** Draws image to JFrame */
		if (black) {
			g.drawImage(view, 0, 0, view.getWidth(), view.getHeight(), null);
		} else {
			random();
			g.drawImage(view, 0, 0, view.getWidth(), view.getHeight(), null);
		}
	}

	private void random() {
		for (int i = 0; i < pixels.length; i++) {
			int color = (int) (Math.random() * 0xFFFFFF);
			pixels[i] = color;
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

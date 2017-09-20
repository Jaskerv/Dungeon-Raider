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

	public Renderer(int width, int height) {
		/** Creates view */
		this.view = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
		/** Creates array for pixels */
		pixels = ((DataBufferInt) view.getRaster().getDataBuffer()).getData();
	}

	/**
	 * Renders pixel to the screen
	 */
	public void render(Graphics g) {
		for (int i = 0; i < pixels.length; i++) {
			pixels[i] = (int) (Math.random() * 0xFFFFFF);
		}
		/** Draws image to JFrame */
		g.drawImage(view, 0, 0, view.getWidth(), view.getHeight(), null);
	}
}

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
	 * Renders Image to the Pixel array. For default size, please use xZoom and
	 * yZoom = 1.
	 * 
	 * @param img
	 *            image to draw
	 * @param xPos
	 *            x position
	 * @param yPos
	 *            y position
	 * @param xZoom
	 *            horizontal resize, xZoom of 2 will make the width of the image 2
	 *            times bigger
	 * @param yZoom
	 *            vertical resize, yZoom of 2 will make the height of the image 2
	 *            times bigger
	 */
	public void renderImage(BufferedImage img, int xPos, int yPos, int xZoom, int yZoom) {
		int[] imgPixels = ((DataBufferInt) img.getRaster().getDataBuffer()).getData();
		for (int y = 0; y < img.getHeight(); y++)
			for (int x = 0; x < img.getWidth(); x++)
				for (int yZoomIndex = 0; yZoomIndex < yZoom; yZoomIndex++)
					for (int xZoomIndex = 0; xZoomIndex < xZoom; xZoomIndex++)
						setPixel(imgPixels[x + y * img.getWidth()], (x * xZoom) + xPos + xZoomIndex,
								(y * yZoom) + yPos + yZoomIndex);
	}

	private void setPixel(int pixel, int x, int y) {
		int pixelIndex = x + y * view.getWidth();
		if (pixels.length >= pixelIndex) {
			pixels[pixelIndex] = pixel;
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

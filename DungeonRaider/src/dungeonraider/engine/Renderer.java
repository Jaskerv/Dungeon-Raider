package dungeonraider.engine;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;

import dungeonraider.map.Map;
import dungeonraider.map.Tile;
import dungeonraider.sprite.Sprite;
import dungeonraider.util.Camera;
import dungeonraider.util.Rectangle;

/**
 * Renders view to window
 *
 * @author Jono Yan
 *
 */
public class Renderer {
	private BufferedImage view;
	private int[] pixels;
	/** The camera that the player sees from */
	private Camera camera;

	public Renderer(int width, int height) {
		/** Creates view */
		this.view = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
		this.camera = new Camera(0, 0, view.getWidth(), view.getHeight());
		// this.camera.setX(-100);
		// this.camera.setY(-30);
		/** Creates array for pixels */
		pixels = ((DataBufferInt) view.getRaster().getDataBuffer()).getData();

		for (int row = 0; row < height; row++) {
			int color = (int) 0;
			for (int col = 0; col < width; col++) {
				pixels[row * width + col] = color;
			}
		}

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
		renderArray(imgPixels, img.getWidth(), img.getHeight(), xPos, yPos, xZoom, yZoom);
	}

	/**
	 * Renders sprite using its pixel Array at xPos and yPos
	 *
	 * @param sprite
	 * @param xPos
	 * @param yPos
	 * @param xZoom
	 *            Horizontal zoom multiplier
	 * @param yZoom
	 *            Vertical zoom multiplier
	 */
	public void renderSprite(Sprite sprite, int xPos, int yPos, int xZoom, int yZoom) {
		renderArray(sprite.getPixels(), sprite.getWidth(), sprite.getHeight(), xPos, yPos, xZoom, yZoom);
	}

	/**
	 * Renders Rectangle using its pixel Array at xPos and yPos of Rectangle
	 *
	 * @param sprite
	 * @param xPos
	 * @param yPos
	 * @param xZoom
	 *            Horizontal zoom multiplier
	 * @param yZoom
	 *            Vertical zoom multiplier
	 */
	public void renderRectangle(Rectangle rect, int xZoom, int yZoom) {
		int[] rectPixels = rect.getPixels();
		if (rectPixels != null) {
			renderArray(rectPixels, rect.getWidth(), rect.getHeight(), rect.getX(), rect.getY(), xZoom, yZoom);
		}
	}

	/**
	 * This method renders the map by rendering each tile's pixels.
	 * 
	 * @param map
	 *            this is the current map that will be displayed
	 */
	public void renderMap(Map map) {
		for (int y = 0; y < 22; y++) {
			for (int x = 0; x < 22; x++) {
				Tile tile = map.getMap()[x][y];
				Sprite tileSprite = tile.getSprite();
				renderArray(tileSprite.getPixels(), tileSprite.getWidth(), tileSprite.getHeight(), tile.getX(),
						tile.getY(), 6, 6);
			}
		}
	}

	/**
	 * Renders pixels from int array.
	 *
	 * @param renderPixel
	 * @param renderWidth
	 * @param renderHeight
	 * @param xPos
	 * @param yPos
	 * @param xZoom
	 *            Horizontal zoom multiplier
	 * @param yZoom
	 *            Vertical zoom multiplier
	 */
	public void renderArray(int[] renderPixel, int renderWidth, int renderHeight, int xPos, int yPos, int xZoom,
			int yZoom) {
		for (int y = 0; y < renderHeight; y++)
			for (int x = 0; x < renderWidth; x++)
				for (int yZoomIndex = 0; yZoomIndex < yZoom; yZoomIndex++)
					for (int xZoomIndex = 0; xZoomIndex < xZoom; xZoomIndex++)
						setPixel(renderPixel[x + y * renderWidth], (x * xZoom) + xPos + xZoomIndex,
								(y * yZoom) + yPos + yZoomIndex);
	}

	/**
	 * Sets pixel to Pixels array.
	 *
	 * @param pixel
	 * @param x
	 * @param y
	 */
	private void setPixel(int pixel, int x, int y) {
		if (this.camera.contains(x, y)) {
			int pixelIndex = (x - this.camera.getX()) + (y - this.camera.getY()) * view.getWidth();
			if (pixels.length > pixelIndex && !Engine.alpha.match(pixel)) {
				pixels[pixelIndex] = pixel;
			}
			// else if (pixels.length > pixelIndex && pixel != Engine.alpha) {
			// pixels[pixelIndex] = 0;
			// }
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

	}

	public Camera getCamera() {
		return camera;
	}

	public void clearArray() {
		for (int i = 0; i < this.pixels.length; i++) {
			this.pixels[i] = 0;
		}
	}

}

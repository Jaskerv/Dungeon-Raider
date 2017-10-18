package gameEngine.sprite;

import java.awt.image.BufferedImage;

import gameEngine.engine.Engine;
import gameEngine.engine.GameObject;
import gameEngine.engine.Renderer;
import gameEngine.util.Rectangle;

/**
 *
 * The animated sprite class that handle the animation
 *
 * @author Charnon
 *
 */
public class AnimatedSprite extends Sprite implements GameObject {

	private Sprite[] sprites;
	private int currentSprite = 0;
	private int speed;
	private int counter = 0;
	private int startSprite = 0;
	private int endSprite = 0;

	/**
	 *
	 * Constructor for AnimatedSprite
	 *
	 * @param sheet
	 *            The sprite sheet that the animations will be drawn upon
	 * @param positions
	 * @param speed
	 *            How many frames pass until the current sprite changes. eg, if
	 *            speed = 60, then the sprite changes every second. 60fps
	 */
	public AnimatedSprite(SpriteSheet sheet, Rectangle[] positions, int speed) {
		sprites = new Sprite[positions.length];
		this.speed = speed;
		this.endSprite = positions.length - 1;

		for (int i = 0; i < positions.length; i++) {
			sprites[i] = new Sprite(sheet, positions[i].getX(),
					positions[i].getY(), positions[i].getWidth(),
					positions[i].getHeight());
		}
	}

	/**
	 *
	 * Another constructor for AnimatedSprite
	 *
	 * @param sheet
	 *            The sprite sheet that the animations will be drawn upon
	 * @param speed
	 *            How many frames pass until the current sprite changes. eg, if
	 *            speed = 60, then the sprite changes every second. 60fps
	 */
	public AnimatedSprite(SpriteSheet sheet, int speed) {
		sprites = sheet.getLoadedSprites();
		this.speed = speed;
		this.endSprite = sprites.length - 1;
	}

	/**
	 * Another constructor for AnimatedSprite
	 *
	 * @param images
	 * @param speed
	 *            How many frames pass until the current sprite changes. eg, if
	 *            speed = 60, then the sprite changes every second. 60fps
	 */
	public AnimatedSprite(BufferedImage[] images, int speed) {

		sprites = new Sprite[images.length];
		this.speed = speed;
		this.startSprite = images.length - 1;

		// fill the sprite array from here
		for (int i = 0; i < images.length; i++) {
			sprites[i] = new Sprite(images[i]);
		}
	}

	/**
	 * Render is dealt in the Layer class.
	 *
	 * @param renderer
	 *            The renderer that will draw the game objects
	 * @param xZoom
	 *            The x multiplicative factor for the game objects
	 * @param yZoom
	 *            The y multiplicative factor for the game objects
	 */
	public void render(Renderer renderer, int xZoom, int yZoom) {

	}

	/**
	 * Sets the current sprite to the last sprite from the previous movement
	 */
	public void reset() {
		counter = 0;
		currentSprite = startSprite;
	}

	@Override
	public void update(Engine engine) {
		// TODO Auto-generated method stub
		counter++;
		if (counter >= speed) {
			counter = 0;
			incrementSprite();
		}
	}

	/**
	 * Increment the current sprite to draw the next sprite for the animation
	 */
	public void incrementSprite() {
		// TODO Auto-generated method stub
		this.currentSprite++;
		// re-loop through the array of sprites when you reach the end.
		if (this.currentSprite >= endSprite) {
			this.currentSprite = startSprite;
		}
	}

	/**
	 * Sets the sprite range for the animations to be played. Each range is 8
	 * sprites.
	 *
	 * @param startSprite
	 * @param endSprite
	 */

	public void setAnimationRange(int startSprite, int endSprite) {
		this.startSprite = startSprite;
		this.endSprite = endSprite;
		reset();
	}

	/**
	 * Gets the width of the current sprite
	 *
	 * @return The width of the sprite
	 */
	public int getWidth() {
		return this.sprites[this.currentSprite].getWidth();
	}

	/**
	 * Gets the height of the current sprite
	 *
	 * @return The height of the sprite
	 */
	public int getHeight() {
		return this.sprites[this.currentSprite].getHeight();
	}

	/**
	 * Gets the pixels of the current sprite for rendering
	 *
	 * @return The pixels of the current sprite
	 */
	public int[] getPixels() {
		return this.sprites[this.currentSprite].getPixels();
	}

}

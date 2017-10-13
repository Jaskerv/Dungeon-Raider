package dungeonraider.sprite;

import java.awt.image.BufferedImage;
import java.util.PrimitiveIterator.OfDouble;

import org.junit.experimental.theories.Theories;

import dungeonraider.engine.Engine;
import dungeonraider.engine.GameObject;
import dungeonraider.engine.Renderer;
import dungeonraider.util.Rectangle;

/**
 *
 * The animated sprite class that handle the animation
 *
 * @author Charnon
 *
 */
public class AnimatedSprite extends Sprite implements GameObject{

	private Sprite[] sprites;
	private int currentSprite = 0;
	private int speed;
	private int counter=0;
	private int startSprite= 0;
	private int endSprite = 0;


	public AnimatedSprite(SpriteSheet sheet, Rectangle[] positions, int speed) {
		sprites = new Sprite[positions.length];
		this.speed = speed;
		this.endSprite = positions.length-1;

		for(int i =0; i< positions.length; i++) {
			sprites[i] = new Sprite(sheet, positions[i].getX(),positions[i].getY(),
					positions[i].getWidth(), positions[i].getHeight());
		}
	}

	public AnimatedSprite(SpriteSheet sheet, int speed) {
		sprites = sheet.getLoadedSprites();
		this.speed = speed;
		this.endSprite = sprites.length-1;

	}


	// speed is how many frames pass until the sprite changes. eg, if 60 then the sprite changes every second
	public AnimatedSprite(BufferedImage[] images, int speed) {

		sprites = new Sprite[images.length];
		this.speed = speed;
		this.startSprite = images.length-1;


		//fill the sprite array from here
		for(int i =0; i < images.length; i++) {
			sprites[i] = new Sprite(images[i]);
		}
	}

	//render is dealt in the Layer class.
	public void render(Renderer renderer, int xZoom, int yZoom) {

	}


	@Override
	public void update(Engine engine) {
		// TODO Auto-generated method stub
		counter++;
		if(counter >= speed) {
			counter = 0;
			incrementSprite();
		}
	}

	public void incrementSprite() {
		// TODO Auto-generated method stub
		this.currentSprite++;
		//re loop through the array of sprites when you reach the end.
		if(this.currentSprite >= endSprite) {

			this.currentSprite = startSprite;
		}
	}

	public void setAnimationrange(int startSprite, int endSprite) {
		this.startSprite = startSprite;
		this.endSprite = endSprite;
	}


	public int getWidth() {
		return this.sprites[this.currentSprite].getWidth();
	}

	/**
	 * @return the height of the current sprite in the array
	 */
	public int getHeight() {
		return this.sprites[this.currentSprite].getHeight();
	}

	/**
	 * @return the pixels
	 */
	public int[] getPixels() {
		return this.sprites[this.currentSprite].getPixels();
	}

}

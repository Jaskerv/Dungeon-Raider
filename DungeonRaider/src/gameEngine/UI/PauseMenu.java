package gameEngine.UI;

import java.awt.image.BufferedImage;

import gameEngine.engine.Engine;
import gameEngine.engine.GameObject;
import gameEngine.engine.Renderer;
import gameEngine.sprite.Sprite;

public class PauseMenu implements GameObject {
	private Sprite background;
	private boolean paused;

	public PauseMenu(BufferedImage backgroundImage) {
		this.background = new Sprite(backgroundImage);
		/**
		 * Default not active
		 */
		this.paused = false;
	}

	@Override
	public void render(Renderer renderer, int xZoom, int yZoom) {
		renderer.renderGUI(background);
	}

	@Override
	public void update(Engine engine) {

	}

	/**
	 * @return the paused
	 */
	public boolean isPaused() {
		return paused;
	}

	/**
	 * @param paused
	 *            the paused to set
	 */
	public void setPaused(boolean paused) {
		this.paused = paused;
	}

}

package gameEngine.UI;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;

import gameEngine.engine.Engine;
import gameEngine.engine.GameObject;
import gameEngine.engine.Renderer;
import gameEngine.sprite.Sprite;
import gameEngine.util.FontImporter;
import library4.SaveBoi;

/**
 * GUI for pause menu
 *
 * @author Jono Yan
 *
 */
public class PauseMenu implements GameObject {
	private BufferedImage img;
	private Sprite background;
	private boolean paused;
	private int[] menu;
	private int index;
	private int cursorX;
	private final int CURSORSIZE;

	private boolean up;
	private boolean down;
	private boolean enter;

	private int count;
	private boolean save;
	private boolean load;
	private Font font;

	public PauseMenu(BufferedImage backgroundImage) {
		this.background = new Sprite(backgroundImage);
		this.img = backgroundImage;
		/**
		 * Default not active
		 */
		this.paused = false;
		this.menu = new int[4];
		this.menu[0] = 365;
		this.menu[1] = 435;
		this.menu[2] = 505;
		this.menu[3] = 635;
		this.index = 0;
		this.cursorX = 500;
		this.CURSORSIZE = 10;
		this.up = false;
		this.down = false;
		this.enter = false;

		this.count = 0;
		this.save = false;
		this.load = false;
		this.font = FontImporter
				.fontImport("resources/fonts/Perfect DOS VGA 437.ttf");
	}

	@Override
	public void render(Renderer renderer, int xZoom, int yZoom) {
		BufferedImage clone = new BufferedImage(img.getWidth(), img.getHeight(),
				BufferedImage.TYPE_INT_RGB);
		Graphics g = clone.getGraphics();
		g.drawImage(img, 0, 0, null);
		g.setColor(Color.WHITE);
		g.fillRect(cursorX, this.menu[index], CURSORSIZE, CURSORSIZE);
		if (save) {
			g.setFont(font.deriveFont(Font.PLAIN, 30));
			g.drawString("Game Saved", 30, 50);
		} else if (load && !save) {
			g.setFont(font.deriveFont(Font.PLAIN, 30));
			g.drawString("Game Loaded", 30, 50);
		}
		background = new Sprite(clone);
		renderer.renderGUI(background);
	}

	@Override
	public void update(Engine engine) {
		if (save) {
			if (count == 100) {
				count = 0;
				save = false;
			}
			count++;
		} else if (load && !save) {
			if (count == 100) {
				count = 0;
				load = false;
			}
			count++;
		}
		if (up == true) {
			cursorUp(engine);
			this.up = false;
		} else if (down == true) {
			cursorDown(engine);
			this.down = false;
		} else if (enter == true) {
			cursorEnter(engine);
			this.enter = false;
		}
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

	/**
	 * moves cursor up
	 *
	 * @param engine
	 */
	public void cursorUp(Engine engine) {
		if (index > 0) {
			index--;
			engine.getSoundLibrary().playClip("cursorMove", -10f);
		}
	}

	/**
	 * moves cursor down
	 *
	 * @param engine
	 */
	public void cursorDown(Engine engine) {
		if (index < 3) {
			index++;
			engine.getSoundLibrary().playClip("cursorMove", -10f);
		}
	}

	/**
	 * Cursor enter at the right spot
	 *
	 * @param engine
	 */
	public void cursorEnter(Engine engine) {
		/** Resume */
		if (index == 0) {
			this.paused = false;
		}
		/** Save */
		else if (index == 1) {
			save(engine);
			this.save = true;
		}
		/** load */
		else if (index == 2) {
			load(engine);
			this.load = true;
		}
		/** Exit */
		else if (index == 3) {
			System.exit(0);
		}
		engine.getSoundLibrary().playClip("cursorReady", -10f);
	}

	private void load(Engine engine) {
		try {
			engine.load(new File("resources/save/save09.txt"));
		} catch (Exception e) {
			System.out.println("Error");
		}
	}

	private void save(Engine engine) {
		SaveBoi save = new SaveBoi(engine);
		save.save();

	}

	/**
	 * @return the up
	 */
	public boolean isUp() {
		return up;
	}

	/**
	 * @return the down
	 */
	public boolean isDown() {
		return down;
	}

	/**
	 * @return the enter
	 */
	public boolean isEnter() {
		return enter;
	}

	/**
	 * @param up
	 *            the up to set
	 */
	public void setUp(boolean up) {
		this.up = up;
	}

	/**
	 * @param down
	 *            the down to set
	 */
	public void setDown(boolean down) {
		this.down = down;
	}

	/**
	 * @param enter
	 *            the enter to set
	 */
	public void setEnter(boolean enter) {
		this.enter = enter;
	}

	/**
	 * @return the index
	 */
	public int getIndex() {
		return index;
	}

	/**
	 * @param index
	 *            the index to set
	 */
	public void setIndex(int index) {
		this.index = index;
	}

}
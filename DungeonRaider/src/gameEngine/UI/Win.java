package gameEngine.UI;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;

import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;

import gameEngine.engine.Engine;
import gameEngine.engine.GameObject;
import gameEngine.engine.Renderer;
import gameEngine.sprite.Sprite;
import gameEngine.util.FontImporter;

/**
 *
 * @author Jono Yan
 *
 */
public class Win implements GameObject {
	private BufferedImage img;
	private int[] menu;
	private int index;
	private int cursorX;
	private final int CURSORSIZE;
	private boolean music;
	private boolean win;
	private boolean up;
	private boolean down;
	private boolean enter;
	private Font font;
	private Clip clip;
	private Graphics g;

	public Win() {
		this.win = false;
		this.menu = new int[2];
		this.menu[0] = 365;
		this.menu[1] = 505;
		this.index = 0;
		this.cursorX = 500;
		this.CURSORSIZE = 10;
		this.up = false;
		this.down = false;
		this.enter = false;
		this.music = false;
		this.font = FontImporter
				.fontImport("resources/fonts/Perfect DOS VGA 437.ttf");
		this.img = new BufferedImage(Engine.WIDTH, Engine.HEIGHT,
				BufferedImage.TYPE_INT_RGB);
		g = img.getGraphics();
		g.setColor(Color.BLACK);
		g.fillRect(0, 0, img.getWidth(), img.getHeight());
	}

	@Override
	public void render(Renderer renderer, int xZoom, int yZoom) {
		BufferedImage clone = new BufferedImage(img.getWidth(), img.getHeight(),
				BufferedImage.TYPE_INT_RGB);
		Graphics g = clone.getGraphics();
		g.drawImage(img, 0, 0, null);
		g.setColor(Color.WHITE);
		g.setFont(font.deriveFont(100f));
		g.drawString("You Win", 460, 147);
		g.setFont(font.deriveFont(30f));
		g.drawString("New Game", 570, 377);
		g.drawString("Exit", 610, 517);
		g.fillRect(cursorX, this.menu[index], CURSORSIZE, CURSORSIZE);
		Sprite background = new Sprite(clone);
		renderer.renderGUI(background);
	}

	@Override
	public void update(Engine engine) {
		if (engine.getPlayer().isDead() && music == false) {
			music = true;
			playMusic(engine);
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
		if (index < 1) {
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
		if (index == 0) {
			engine.load(new File("resources/save/newGame.txt"));
			this.setWin(false);
		} else if (index == 1) {
			System.exit(0);
		}
		engine.getSoundLibrary().playClip("cursorReady", -10f);
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

	public boolean isWin() {
		return win;
	}

	public void setWin(boolean win) {
		this.win = win;
	}

	/**
	 * Plays death music
	 *
	 * @param engine
	 */
	public void playMusic(Engine engine) {
		clip = engine.getSoundLibrary().getClip("deathMusic");
		FloatControl gainControl = (FloatControl) clip
				.getControl(FloatControl.Type.MASTER_GAIN);
		gainControl.setValue(-5f);
		clip.loop(Clip.LOOP_CONTINUOUSLY);
		clip.start();
	}

	/**
	 * Stops death music
	 */
	public void stopMusic() {
		clip.stop();
		music = false;
	}
}

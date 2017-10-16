package gameEngine.controller;

import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Observable;

import gameEngine.character.Player;
import gameEngine.engine.Engine;

public class KeyController extends Observable implements KeyListener, FocusListener {

	/**
	 * Control Booleans for player and other key events
	 */
	private boolean up;
	private boolean down;
	private boolean left;
	private boolean right;
	private boolean hurtPlayer;
	private boolean pickUp;
	private boolean run;
	private boolean useItem;

	private Player player;
	private Engine engine;

	public KeyController(Player player, Engine engine) {
		this.up = false;
		this.down = false;
		this.left = false;
		this.right = false;
		this.hurtPlayer = false;
		this.player = player;
		this.pickUp = false;
		this.engine = engine;
		this.run = false;
		this.useItem = false;
	}

	@Override
	public void keyTyped(KeyEvent e) {

	}

	@Override
	public void keyPressed(KeyEvent e) {
		int key = e.getKeyCode();
		switch (key) {
		case KeyEvent.VK_W:
			// move up
			this.up = true;
			// System.out.println("w pressed");
			break;
		case KeyEvent.VK_A:
			// move left
			this.left = true;
			// System.out.println("a pressed");
			break;
		case KeyEvent.VK_S:
			// move down
			this.down = true;
			// System.out.println("s pressed");
			break;
		case KeyEvent.VK_D:
			// move right
			this.right = true;
			// System.out.println("d pressed");
			break;
		case KeyEvent.VK_I:
			// myModel.displayInventory()...
			break;
		case KeyEvent.VK_F:
			this.pickUp = true;
			break;
		case KeyEvent.VK_SHIFT:
			this.run = true;
			break;
		case KeyEvent.VK_G:
			this.useItem = true;
			break;
		// Deals 10 damage to player when pushing "p" for testing purposes
		case KeyEvent.VK_P:
			this.player.damage(10);
			break;
		// case KeyEvent.VK_P:
		// hurtPlayer = true;
		// break;
		}

	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		int key = e.getKeyCode();
		switch (key) {
		case KeyEvent.VK_W:
			// move up
			this.up = false;
			// System.out.println("w releasedd");

			if (engine.isPaused()) {
				engine.getPauseMenu().setUp(true);
			}
			break;
		case KeyEvent.VK_A:
			// move left
			this.left = false;
			// System.out.println("a released");
			break;
		case KeyEvent.VK_S:
			// move down
			this.down = false;
			// System.out.println("s released");

			if (engine.isPaused()) {
				engine.getPauseMenu().setDown(true);
			}
			break;
		case KeyEvent.VK_D:
			// move right
			this.right = false;
			// System.out.println("d released");
			break;
		case KeyEvent.VK_I:
			// myModel.displayInventory()...
			break;
		case KeyEvent.VK_F:
			this.pickUp = false;
			break;
		case KeyEvent.VK_SHIFT:
			this.run = false;
			break;
		case KeyEvent.VK_G:
			this.useItem = false;
			break;
		case KeyEvent.VK_ESCAPE:
			if (this.engine.isPaused()) {
				this.engine.getPauseMenu().setPaused(false);
				break;
			} else {
				this.engine.getPauseMenu().setPaused(true);
				engine.getSoundLibrary().playClip("cursorReady", -10f);
				break;
			}
		case KeyEvent.VK_ENTER:
			if (engine.isPaused()) {
				engine.getPauseMenu().setEnter(true);
			}
			break;
		}
	}

	public boolean isUp() {
		return up;
	}

	public boolean isDown() {
		return down;
	}

	public boolean isLeft() {
		return left;
	}

	public boolean isRight() {
		return right;
	}

	public boolean isPickUp() {
		return pickUp;
	}

	public boolean isRun() {
		return run;
	}

	public void setPickUp(boolean pickUp) {
		this.pickUp = pickUp;
	}

	public void setRun(boolean run) {
		this.run = run;
	}

	public boolean isUseItem() {
		return useItem;
	}

	public void setUseItem(boolean useItem) {
		this.useItem = useItem;
	}

	/**
	 * @return the hurtPlayer
	 */
	public boolean isHurtPlayer() {
		return hurtPlayer;
	}

	@Override
	public void focusGained(FocusEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void focusLost(FocusEvent e) {
		// TODO Auto-generated method stub

	}

}

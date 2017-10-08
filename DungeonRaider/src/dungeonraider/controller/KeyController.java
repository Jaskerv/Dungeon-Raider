package dungeonraider.controller;

import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Observable;

import dungeonraider.character.Player;
import dungeonraider.engine.Engine;
import dungeonraider.util.Camera;

public class KeyController extends Observable implements KeyListener, FocusListener {

	private boolean up;
	private boolean down;
	private boolean left;
	private boolean right;
	private boolean hurtPlayer;
	private Player player;
	private Engine engine;

	public KeyController(Player player, Engine engine) {
		this.up = false;
		this.down = false;
		this.left = false;
		this.right = false;
		this.hurtPlayer = false;
		this.player = player;
		this.engine = engine;
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
			break;
		case KeyEvent.VK_D:
			// move right
			this.right = false;
			// System.out.println("d released");
			break;
		case KeyEvent.VK_I:
			// myModel.displayInventory()...
			break;
		case KeyEvent.VK_P:
			this.player.damage(10);
		}
	}

	public boolean isUp() {
		if (!engine.isMenu())
			return up;
		return false;
	}

	public boolean isDown() {
		if (!engine.isMenu())
			return down;
		return false;
	}

	public boolean isLeft() {
		if (!engine.isMenu())
			return left;
		return false;
	}

	public boolean isRight() {
		if (!engine.isMenu())
			return right;
		return false;
	}

	/**
	 * @return the hurtPlayer
	 */
	public boolean isHurtPlayer() {
		if (!engine.isMenu())
			return hurtPlayer;
		return false;
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

package dungeonraider.controller;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Observable;

import dungeonraider.character.Player;
import dungeonraider.engine.Engine;
import dungeonraider.util.Camera;

public class KeyController extends Observable implements KeyListener {

	private boolean up;
	private boolean down;
	private boolean left;
	private boolean right;

	public KeyController() {
		this.up = false;
		this.down = false;
		this.left = false;
		this.right = false;
	}

	@Override
	public void keyTyped(KeyEvent e) {

	}

	@Override
	public void keyPressed(KeyEvent e) {
		int key = e.getKeyCode();
		switch (key) {
		case KeyEvent.VK_W:
			//move up
			this.up = true;
			//System.out.println("w pressed");
			break;
		case KeyEvent.VK_A:
			//move left
			this.left = true;
			//System.out.println("a pressed");
			break;
		case KeyEvent.VK_S:
			//move down
			this.down = true;
			//System.out.println("s pressed");
			break;
		case KeyEvent.VK_D:
			//move right
			this.right = true;
			//System.out.println("d pressed");
			break;
		case KeyEvent.VK_I:
			// myModel.displayInventory()...
			break;
		}
	}



	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		int key = e.getKeyCode();
		switch (key) {
		case KeyEvent.VK_W:
			//move up
			this.up = false;
			//System.out.println("w releasedd");
			break;
		case KeyEvent.VK_A:
			//move left
			this.left = false;
			//System.out.println("a released");
			break;
		case KeyEvent.VK_S:
			//move down
			this.down = false;
			//System.out.println("s released");
			break;
		case KeyEvent.VK_D:
			//move right
			this.right = false;
			//System.out.println("d released");
			break;
		case KeyEvent.VK_I:
			// myModel.displayInventory()...
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

}

package dungeonraider.controller;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Observable;

import dungeonraider.character.Player;
import dungeonraider.engine.Engine;

public class KeyController extends Observable implements KeyListener {

	private Player player;

	public KeyController(Player player) {
		this.player = player;
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
			this.player.walkUp();
			//System.out.println("w pressed");
			break;
		case KeyEvent.VK_A:
			//move left
			this.player.walkLeft();
			//System.out.println("a pressed");
			break;
		case KeyEvent.VK_S:
			//move down
			this.player.walkDown();
			//System.out.println("s pressed");
			break;
		case KeyEvent.VK_D:
			this.player.walkRight();
			//System.out.println("d pressed");
			//move right
			break;
		case KeyEvent.VK_I:
			// myModel.displayInventory()...
			break;
		}

	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub

	}

}

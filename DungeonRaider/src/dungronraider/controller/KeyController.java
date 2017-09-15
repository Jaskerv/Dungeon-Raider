package dungronraider.controller;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import dungeonraider.main.DungeonRaider;

public class KeyController implements KeyListener{

	private DungeonRaider myModel;

	public KeyController(DungeonRaider myModel) {
		this.myModel = myModel;
	}

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		int direction = e.getKeyCode();
		switch (direction) {
			case KeyEvent.VK_W:
				break;
			case KeyEvent.VK_A:
				break;
			case KeyEvent.VK_S:
				break;
			case KeyEvent.VK_D:
				break;
		}

	}

	@Override
	public void keyPressed(KeyEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub

	}

}

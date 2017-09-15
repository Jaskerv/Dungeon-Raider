package dungeonraider.controller;

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
		int key = e.getKeyCode();
		switch (key) {
			case KeyEvent.VK_W:
				//myModel.moveUp()...
				break;
			case KeyEvent.VK_A:
				break;
			case KeyEvent.VK_S:
				break;
			case KeyEvent.VK_D:
				break;
			case KeyEvent.VK_I:
				//myModel.displayInventory()...
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

package dungeonraider.engine;

import java.awt.Dimension;

import javax.swing.JFrame;

/**
 * The main exe
 * 
 * @author Jono Yan
 *
 */
public class Engine implements Runnable {
	private JFrame view;

	public Engine() {
		this.view = new JFrame("Dungeon Raider");
		this.view.setVisible(true);
	}

	@Override
	public void run() {
		

	}

	public static void main(String[] args) {
		Engine game = new Engine();
		game.run();
	}

}

package dungeonraider.engine;

import java.awt.Canvas;
import java.awt.Dimension;
import java.awt.Toolkit;

import javax.swing.JFrame;

/**
 * The main exe
 * 
 * @author Jono Yan
 *
 */
public class Engine extends JFrame implements Runnable {

	private Canvas canvas;
	private Toolkit tk;

	public Engine() {
		this.canvas = new Canvas();
		this.tk = this.getToolkit();
		/** Sets name of JFrame window */
		setTitle("Dungeon Raider");
		/** Close program on exit */
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		/** Set location of JFrame window and size */
		setBounds(0, 0, (int) tk.getScreenSize().getWidth() / 2, (int) tk.getScreenSize().getHeight() / 2);
		/** Sets window to center */
		setLocationRelativeTo(null);
		add(canvas);
		setVisible(true);
	}

	@Override
	public void run() {

	}

	public static void main(String[] args) {
		Engine game = new Engine();
		Thread thread = new Thread(game);
		thread.start();
	}

}

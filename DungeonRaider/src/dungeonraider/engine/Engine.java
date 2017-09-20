package dungeonraider.engine;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Toolkit;
import java.awt.image.BufferStrategy;

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
		setBounds(0, 0, 1000, 1000);
		/** Sets window to center */
		setLocationRelativeTo(null);
		/** Adds canvas to JFrame */
		add(canvas);
		/** Sets JFrame to visible */
		setVisible(true);
		/** Creates 3 buffer renderer */
		canvas.createBufferStrategy(3);
		BufferStrategy b = canvas.getBufferStrategy();
		int i = 0;
		int x = 0;
		while (true) {
			i++;
			if (i == 10) {
				i = 0;
				x++;
			}
			b = canvas.getBufferStrategy();
			Graphics gr = b.getDrawGraphics();
			super.paint(gr);
			gr.setColor(Color.blue);
			gr.fillOval(x, 100, 100, 100);
			gr.dispose();
			b.show();
		}
	}

	public void render() {

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

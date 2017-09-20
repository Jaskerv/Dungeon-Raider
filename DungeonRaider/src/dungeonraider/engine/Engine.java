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
	private Renderer renderer;
	private int width = 1000;
	private int height = 800;

	public Engine() {
		this.canvas = new Canvas();
		this.tk = this.getToolkit();
		/** Sets name of JFrame window */
		setTitle("Dungeon Raider");
		/** Close program on exit */
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		/** Set location of JFrame window and size */
		setBounds(0, 0, width, height);
		/** Sets window to center */
		setLocationRelativeTo(null);
		/** Adds canvas to JFrame */
		add(canvas);
		/** Sets JFrame to visible */
		setVisible(true);
		/** Creates 2 buffer renderer */
		canvas.createBufferStrategy(3);

		this.renderer = new Renderer(getWidth(), getHeight());
	}

	private int x = 0;

	/**
	 * This method will render everything onto the screen
	 */
	public void render() {
		BufferStrategy b = canvas.getBufferStrategy();
		Graphics g = b.getDrawGraphics();
		super.paint(g);
		renderer.render(g);
		// gr.setColor(Color.blue);
		// gr.fillOval(x, 100, 100, 100);
		g.dispose();
		b.show();
	}

	/**
	 * This method will update to the buffer. EG. char movement
	 * 
	 * This method will run at a specified speed.
	 */
	public void update() {
		x += 1;
	}

	/**
	 * Thread will execute this code and run the game.
	 */
	@Override
	public void run() {
		long lastTime = System.nanoTime();
		/** 60 FPS */
		double nanoSecondConversion = 1000000000.0 / 60;
		double changeInSeconds = 0;
		while (true) {
			long now = System.nanoTime();
			changeInSeconds += (now - lastTime) / nanoSecondConversion;

			while (changeInSeconds >= 1) {
				update();
				changeInSeconds = 0;
			}
			render();
			lastTime = now;
		}
	}

	public static void main(String[] args) {
		Engine game = new Engine();
		Thread thread = new Thread(game);
		thread.start();
	}

}

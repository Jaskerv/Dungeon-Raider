package dungeonraider.engine;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Toolkit;
import java.awt.event.ActionListener;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.image.BufferStrategy;

import javax.swing.JFrame;
import javax.swing.Timer;

/**
 * The main engine class which implements runnable and also contains the main method.
 * 
 * @author Jono Yan
 *
 */
public class Engine extends JFrame implements Runnable {

	private Canvas canvas;
	private Toolkit tk;
	private Renderer renderer;
	private boolean initialized;
	private int width = 1000;
	private int height = 800;

	public Engine() {
		this.canvas = new Canvas();
		this.tk = this.getToolkit();
		this.initialized = false;
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
		/** Disable Resizeable*/
		setResizable(false);
		/** Sets JFrame to visible */
		setVisible(true);
		/** Component listener to see if JFrame is resized */
		/** Creates 2 buffer renderer */
		canvas.createBufferStrategy(3);

		this.renderer = new Renderer(getWidth(), getHeight());
		addComponentListener(new ResizeListener(this));
	}
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
		this.initialized = true;
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

	/**
	 * Updates Renderer if JFrame is resized
	 */
	public void updateFrame() {
		this.renderer.updateSize(this.getWidth(), this.getHeight());
	}

	public static void main(String[] args) {
		Engine game = new Engine();
		Thread thread = new Thread(game);
		thread.start();
	}

	private class ResizeListener implements ComponentListener {
		private Engine window;
		private final int DELAY = 1000;

		public ResizeListener(Engine window) {
			this.window = window;
		}

		@Override
		public void componentResized(ComponentEvent e) {
			if (window.initialized) {
//				System.out.println("Updated");
				this.window.updateFrame();
			}
		}

		@Override
		public void componentMoved(ComponentEvent e) {
			// TODO Auto-generated method stub

		}

		@Override
		public void componentShown(ComponentEvent e) {
			// TODO Auto-generated method stub

		}

		@Override
		public void componentHidden(ComponentEvent e) {
			// TODO Auto-generated method stub

		}

	}
}

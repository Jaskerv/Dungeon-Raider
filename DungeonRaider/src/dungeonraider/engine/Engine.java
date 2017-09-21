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
import java.util.Observable;
import java.util.Observer;

import javax.swing.JFrame;
import javax.swing.Timer;

import dungeonraider.map.Map;
import dungeonraider.map.Tile;

/**
 * The main engine class which implements runnable and also contains the main method.
 * 
 * @author Jono Yan
 *
 */
public class Engine extends JFrame implements Runnable, Observer {

	private static final long serialVersionUID = 1L;
	private Canvas canvas;
	private Toolkit tk;
	private Renderer renderer;
	private static final int WIDTH = 1000;
	private static final int HEIGHT = 800;
	int x = 0;

	public Engine() {
		this.canvas = new Canvas();
		this.tk = this.getToolkit();
		/** Sets name of JFrame window */
		setTitle("Dungeon Raider");
		/** Close program on exit */
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		/** Set location of JFrame window and size */
		setBounds(0, 0, WIDTH, HEIGHT);
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
	}
	/**
	 * This method will render everything onto the screen
	 */
	public void render() {
		BufferStrategy b = canvas.getBufferStrategy();
		Graphics g = b.getDrawGraphics();
		super.paint(g);
		renderer.render(g);
		g.setColor(Color.blue);
		Map map = new Map(0, 0, 0);
		map.intialiseMap();
		for (int y = 0; y < 22; y++) {
			for (int x = 0; x < 22; x++) {
				Tile tile = map.getMap()[x][y];
				g.drawRect(tile.getX(), tile.getY(), 30, 30);
			}
		}
		g.fillOval(x, 100, 100, 100);
		g.dispose();
		b.show();
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


	/**
	 * This method gets called when the Observable class calls
	 * setChanged() & notifyObservers()
	 */
	@Override
	public void update(Observable o, Object arg) { 
		update(); 
	}
	
	/**
	 * This method will update to the buffer. EG. char movement
	 * 
	 * This method will run at a specified speed.
	 */
	public void update() {
		x++;
	}
	
	
	
	public int getWidth() { return WIDTH; }
	
	public int getHeight() { return HEIGHT; }
	
	
}

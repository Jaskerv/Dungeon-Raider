package dungeonraider.engine;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Toolkit;
import java.awt.image.BufferStrategy;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JFrame;

import dungeonraider.character.Player;
import dungeonraider.controller.KeyController;
import dungeonraider.map.Map;
import dungeonraider.map.Tile;

/**
 * The main engine class which implements runnable and also contains the main
 * method.
 *
 * @author Jono Yan
 *
 */
public class Engine extends JFrame implements Runnable, Observer {

	private static final long serialVersionUID = 1L;
	private Canvas canvas;
	private Toolkit tk;
	private Renderer renderer;
	public static int WIDTH = 1280;
	public static int HEIGHT = 720;
	int x = 0;
	Player player;
	private Map currentMap;

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
		/** Disable Resizeable */
		setResizable(false);
		/** Sets JFrame to visible */
		setVisible(true);
		/** Component listener to see if JFrame is resized */
		/** Creates 2 buffer renderer */
		canvas.createBufferStrategy(3);
		this.addKeyListener(new KeyController());
		this.renderer = new Renderer(getWidth(), getHeight());
		this.player = new Player();
		this.currentMap = new Map(0, 0, 0);
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
		currentMap.render(g);
		player.render(g);
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
		this.renderer.updateSize(WIDTH, HEIGHT);
	}

	public static void main(String[] args) {
		Engine game = new Engine();
		Thread thread = new Thread(game);
		thread.start();
	}

	/**
	 * This method gets called when the Observable class calls setChanged() &
	 * notifyObservers()
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

}

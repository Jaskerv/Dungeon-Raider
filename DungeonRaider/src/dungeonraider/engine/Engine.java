package dungeonraider.engine;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Toolkit;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Observable;
import java.util.Observer;

import javax.imageio.ImageIO;
import javax.swing.JFrame;

import dungeonraider.character.Player;
import dungeonraider.controller.KeyController;
import dungeonraider.map.Map;
import dungeonraider.sprite.Sprite;
import dungeonraider.sprite.SpriteSheet;
import dungeonraider.util.Rectangle;

/**
 * The main engine class which implements runnable and also contains the main
 * method.
 *
 * @author Jono Yan
 *
 */
public class Engine extends JFrame implements Runnable, Observer {

	public static final int alpha = 0xFF00DC;
	private static final long serialVersionUID = 1L;
	private Canvas canvas;
	private Toolkit tk;
	private Renderer renderer;
	public static int WIDTH = 1280;
	public static int HEIGHT = 720;
	int x = 0;
	Player player;

	/** This will contain the list of maps from start to finish */
	private HashMap<Integer, Map> mapList = initialiseMaps();
	private Map currentMap = mapList.get(0);

	/** Test Objects */
	private BufferedImage testImage;
	private Rectangle testRect;
	private Sprite testSprite;
	private SpriteSheet testSpriteSheet;

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
		this.renderer = new Renderer(getWidth(), getHeight());
		this.player = new Player();
		this.addKeyListener(new KeyController(this.player));
		this.setFocusable(true);

		/**
		 * Testing Objects
		 */
		this.testRect = new Rectangle(30, 90, 40, 40);
		this.testRect.generateGraphics(10, 356);
		testImage = loadImage("resources/tiles/grassTile.PNG");
		BufferedImage sheet = loadImage("resources/tiles/Tiles1.png");
		testSpriteSheet = new SpriteSheet(sheet);
		testSpriteSheet.loadSprites(16, 16);
		this.testSprite = testSpriteSheet.getSprite(1, 1);
	}

	/**
	 * This method will render everything onto the screen
	 */
	public void render() {
		BufferStrategy b = canvas.getBufferStrategy();
		Graphics g = b.getDrawGraphics();
		super.paint(g);
		renderer.renderRectangle(testRect, 10, 10);
		/** Need to render all the images first */
		renderer.renderSprite(testSprite, 0, 0, 5, 5);
		// renderer.renderImage(test, 0, 0, 10, 10);
		// g.setColor(Color.blue);
		// currentMap.render(g);
		// this.player.render(g);
		/** Then render the Renderer */
		renderer.render(g);
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

	/**
	 * Returns a buffered image from the path
	 * 
	 * @param path
	 */
	private BufferedImage loadImage(String path) {
		BufferedImage loadedImage;
		try {
			loadedImage = ImageIO.read(new FileInputStream(path));
			BufferedImage format = new BufferedImage(loadedImage.getWidth(), loadedImage.getHeight(),
					BufferedImage.TYPE_INT_RGB);
			format.getGraphics().drawImage(loadedImage, 0, 0, null);
			return format;
		} catch (IOException e) {
			e.printStackTrace();
			loadedImage = new BufferedImage(30, 30, BufferedImage.TYPE_INT_RGB);
			loadedImage.getGraphics().setColor(Color.PINK);
			loadedImage.getGraphics().fillRect(0, 0, 30, 30);
			return loadedImage;
		}

	}

	/**
	 * This method is responsible for creating every map instance at the beginning,
	 * and storing it for later use until the player traverses through to each map.
	 * 
	 * @return list of maps
	 */
	private HashMap<Integer, Map> initialiseMaps() {
		HashMap<Integer, Map> mapList = new HashMap<Integer, Map>();
		int count = 0;
		// Tutorial map
		Map tutMap = new Map();
		tutMap.initialiseMap("TutorialMap");
		mapList.put(count, tutMap);
		count++;
		return mapList;

	}

}

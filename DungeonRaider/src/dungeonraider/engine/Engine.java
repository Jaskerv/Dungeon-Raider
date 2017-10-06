package dungeonraider.engine;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Toolkit;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

import javax.imageio.ImageIO;
import javax.swing.JFrame;

import dungeonraider.UI.IngameInterface;
import dungeonraider.character.Player;
import dungeonraider.controller.KeyController;
import dungeonraider.controller.MouseController;
import dungeonraider.map.Map;
import dungeonraider.sprite.Sprite;
import dungeonraider.sprite.SpriteSheet;
import dungeonraider.util.Camera;
import dungeonraider.util.PatternInt;
import dungeonraider.util.Position;
import dungeonraider.util.Rectangle;

/**
 * The main engine class which implements runnable and also contains the main
 * method.
 *
 * @author Jono Yan
 *
 */
public class Engine extends JFrame implements Runnable, Observer {
	public static final PatternInt alpha = new PatternInt(0xFFFF00DC, -16777216);
	private static final long serialVersionUID = 1L;
	private Canvas canvas;
	private Toolkit tk;
	private Renderer renderer;
	public static int WIDTH = 1280;
	public static int HEIGHT = 720;

	/**
	 * Key listener - keeps track of cameras movement
	 */
	private KeyController keyBinds;
	private MouseController mouseListener;
	private Player player;
	/**
	 *
	 */
	private IngameInterface GUI;
	/** This will contain the list of maps from start to finish */
	private HashMap<Integer, Map> mapList = initialiseMaps();
	private Map currentMap = mapList.get(0);
	/** Test Objects */
	private Sprite playerSprite;
	private SpriteSheet testSpriteSheet;
	private SpriteSheet dungeonTiles = new SpriteSheet(loadImage("resources/tiles/DungeonTileset1.png"));
	private GameObject[] object;
	/** Tutorial map wall boundaries */
	private static final int LEFT_WALL = 60;
	private static final int TOP_WALL = 100;
	private static final int RIGHT_WALL = 1895;
	private static final int BOTTOM_WALL = 1850;

	public Engine() {
		this.canvas = new Canvas();
		this.tk = this.getToolkit();
		this.object = new GameObject[1];
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
		/**
		 * Testing Objects
		 */
		BufferedImage sheet = loadImage("resources/tiles/Tiles1.png");
		testSpriteSheet = new SpriteSheet(sheet);
		testSpriteSheet.loadSprites(16, 16);
		dungeonTiles.loadSprites(16, 16);
		this.playerSprite = dungeonTiles.getSprite(4, 6);

		/**
		 * Initiating the players
		 */
		this.player = new Player(new Position(200, 200), 100, playerSprite, 5, 100, 300);
		this.object[0] = player;
		/** GUI */
		this.GUI = new IngameInterface(player, WIDTH, HEIGHT);

		/**
		 * initiating key listener
		 */
		this.keyBinds = new KeyController(player);
		this.mouseListener = new MouseController(this);
		this.canvas.addKeyListener(keyBinds);
		this.canvas.addFocusListener(keyBinds);
		this.canvas.addMouseListener(mouseListener);

		this.addKeyListener(keyBinds);
		this.addFocusListener(keyBinds);
		this.addMouseListener(mouseListener);
	}

	/**
	 * This method will render everything onto the screen
	 */
	public void render() {
		BufferStrategy b = canvas.getBufferStrategy();
		Graphics g = b.getDrawGraphics();
		super.paint(g);
		this.renderer.clearArray();
		// Renders the map first (bottom layer of the image)
		renderer.renderMap(currentMap);
		/** Render Objects */
		for (GameObject gameObject : object) {
			gameObject.render(renderer, 3, 3);
		}
		/** Render GUI */
		this.GUI.render(renderer, GUI.XZOOM, GUI.YZOOM);
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
		for (GameObject gameObject : object) {
			gameObject.update(this);
		}
		this.GUI.update(this);
	}

	/**
	 * Returns a buffered image from the path
	 *
	 * @param path
	 */
	public static BufferedImage loadImage(String path) {
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

	/**
	 * @return the keyBinds
	 */
	public KeyController getKeyBinds() {
		return keyBinds;
	}

	/**
	 * @return the renderer
	 */
	public Renderer getRenderer() {
		return renderer;
	}

	/**
	 * @return the player
	 */
	public Player getPlayer() {
		return player;
	}

}

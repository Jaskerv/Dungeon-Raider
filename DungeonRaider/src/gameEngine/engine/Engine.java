package gameEngine.engine;

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

import gameEngine.UI.IngameInterface;
import gameEngine.character.Monster;
import gameEngine.character.Player;
import gameEngine.controller.KeyController;
import gameEngine.controller.MouseController;
import gameEngine.map.Map;
import gameEngine.sound.SoundMap;
import gameEngine.sprite.AnimatedSprite;
import gameEngine.sprite.Sprite;
import gameEngine.sprite.SpriteSheet;
import gameEngine.util.PatternInt;
import gameEngine.util.Position;

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
	private StartGame startGame;
	private boolean menu;
	/** This will contain the list of maps from start to finish */
	private HashMap<Integer, Map> mapList = initialiseMaps();
	private Map currentMap = mapList.get(0);
	/** Test Objects */
	private Sprite playerSprite;
	private SpriteSheet testSpriteSheet;
	private static SpriteSheet dungeonTiles = new SpriteSheet(loadImage("resources/tiles/DungeonTileset1.png"));
	private static final SpriteSheet SPRITE_SHEET_2 = 
			new SpriteSheet(Engine.loadImage("resources/tiles/DungeonTileset4.png"));
	private List<GameObject> object;
	private Sprite weaponTestSprite;
	/** Tutorial map wall boundaries */
	private static final int LEFT_WALL = 60;
	private static final int TOP_WALL = 100;
	private static final int RIGHT_WALL = 1895;
	private static final int BOTTOM_WALL = 1850;

	private SoundMap soundLibrary;

	public Engine() {
		// this.canvas = new Canvas();
		this.soundLibrary = new SoundMap("resources/sountracks/soundlibrary.txt");
		this.startGame = new StartGame(this);
		this.menu = true;
		this.tk = this.getToolkit();
		this.object = new ArrayList<GameObject>();

		/** Sets name of JFrame window */
		setTitle("Dungeon Raider");

		/** Close program on exit */
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		/** Set location of JFrame window and size */
		setBounds(0, 0, WIDTH, HEIGHT);

		/** Sets window to center */
		setLocationRelativeTo(null);

		// /** Adds canvas to JFrame */
		// add(canvas);
		add(startGame);

		/** Disable Resizeable */
		setResizable(false);

		/** Sets JFrame to visible */
		setVisible(true);

		/** Component listener to see if JFrame is resized */
		/** Creates 2 buffer renderer */
		this.startGame.createBufferStrategy(3);
		this.renderer = new Renderer(getWidth(), getHeight());

		/**
		 * Testing Objects
		 */
		BufferedImage sheet = loadImage("resources/tiles/Tiles1.png");

		testSpriteSheet = new SpriteSheet(sheet);
		testSpriteSheet.loadSprites(16, 16);
		//dungeonTiles.loadSprites(16, 16);
		this.playerSprite = dungeonTiles.getSprite(4, 6);
		
		//Testing the animated sprites for the player
		BufferedImage playerSheetImage = loadImage("resources/images/Player.png");
		SpriteSheet playerSheet = new SpriteSheet(playerSheetImage);
		playerSheet.loadSprites(20,26);
		
		AnimatedSprite playerAnimations = new AnimatedSprite(playerSheet, 5); 
		
		
		/**
		 * Testing melee
		 */
		weaponTestSprite = dungeonTiles.getSprite(10, 1);

		/**
		 * Initiating the players
		 */
		this.player = new Player(new Position(200, 200), 100, playerAnimations, 5, 100, 300);
		this.object = (List<GameObject>) currentMap.getMonsters();
		this.object.add(player);
		/** GUI */
		this.GUI = new IngameInterface(player, WIDTH, HEIGHT);

		/**
		 * initiating key listener
		 */
		this.keyBinds = new KeyController(player, this);
		this.mouseListener = new MouseController(this);
		this.addKeyListener(keyBinds);
		this.addFocusListener(keyBinds);
		this.addMouseListener(mouseListener);
	}

	/**
	 * This method will render everything onto the screen
	 */
	public void render() {
		if (!menu) {
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
		} else if (menu == true) {
			try {
				BufferStrategy b = startGame.getBufferStrategy();
				Graphics g = b.getDrawGraphics();
				super.paint(g);
				this.renderer.clearArray();
				startGame.render(renderer, 1, 1);
				renderer.render(g);
				g.dispose();
				b.show();
			} catch (Exception e) {
				System.out.println("there is error");
			}
		}

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
		soundLibrary.autoClipClose();
		if (menu) {
			startGame.update(this);
		} else {
			for (GameObject gameObject : object) {
				gameObject.update(this);
			}
			this.GUI.update(this);
		}
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

	public void switchCanvas() {
		if (this.menu) {
			this.startGame.stopMusic();
			if (this.canvas == null) {
				this.canvas = new Canvas();
				this.add(canvas);
				this.canvas.createBufferStrategy(3);
				this.canvas.addKeyListener(keyBinds);
				this.canvas.addFocusListener(keyBinds);
				this.canvas.addMouseListener(mouseListener);
				this.addKeyListener(keyBinds);
				this.addFocusListener(keyBinds);
				this.addMouseListener(mouseListener);
				this.setFocusable(true);
			}
			this.menu = false;
			this.remove(startGame);
			this.revalidate();
		} else {

		}
	}
	/**
	 * Finds sprite image based on name that is parsed in
	 * @param name
	 * @return
	 */
	public static Sprite findSprite(String name) {
		dungeonTiles.loadSprites(16, 16);
		SPRITE_SHEET_2.loadSprites(16, 16);
		if (name.equals("Monster_One")) {
			return dungeonTiles.getSprite(3, 6);
		} else if (name.equals("Small_Health_Potion")
				|| name.equals("Big_Health_Potion")) {
			return SPRITE_SHEET_2.getSprite(12, 11);
		}
		return null;
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

	public Map getCurrentMap() {
		return currentMap;
	}

	public void setCurrentMap(Map currentMap) {
		this.currentMap = currentMap;
	}

	/**
	 * @return the menu
	 */
	public boolean isMenu() {
		return menu;
	}

	/**
	 * @param menu
	 *            the menu to set
	 */
	public void setMenu(boolean menu) {
		this.menu = menu;
	}

	/**
	 * @return the soundLibrary
	 */
	public SoundMap getSoundLibrary() {
		return soundLibrary;
	}

	public MouseController getMouseListener() {
		return mouseListener;
	}

}

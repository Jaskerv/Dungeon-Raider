package gameEngine.engine;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Toolkit;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.io.FileInputStream;
import java.io.IOException;
import java.security.KeyStore.Entry;
import java.util.HashMap;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

import javax.imageio.ImageIO;
import javax.swing.JFrame;

import gameEngine.UI.IngameInterface;
import gameEngine.UI.PauseMenu;
import gameEngine.UI.YouDied;
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
import library4.SaveBoi;
import library4.Saveable;

/**
 * The main engine class which implements runnable and also contains the main
 * method.
 *
 * @author Jono Yan
 *
 */
public class Engine extends JFrame implements Runnable, Observer {
	public static final PatternInt alpha = new PatternInt(0xFFFF00DC,
			-16777216);
	private static final long serialVersionUID = 1L;
	private Canvas canvas;
	private Toolkit tk;
	private Renderer renderer;
	public static int WIDTH = 1280;
	public static int HEIGHT = 720;
	private SaveBoi save;

	/**
	 * Key listener - keeps track of cameras movement
	 */
	private KeyController keyBinds;
	private MouseController mouseListener;
	private Player player;
	/**
	 * GUI
	 */
	private IngameInterface GUI;
	private PauseMenu pauseMenu;
	private StartGame startGame;
	private YouDied youDied;
	private boolean menu;
	/** This will contain the list of maps from start to finish */
	private HashMap<Integer, Map> mapList = initialiseMaps();
	private Map currentMap = mapList.get(0);
	private int currentMapNumber = 1;
	/** Test Objects */
	private Sprite playerSprite;
	private SpriteSheet testSpriteSheet;
	private static SpriteSheet dungeonTiles = new SpriteSheet(
			loadImage("resources/tiles/DungeonTileset1.png"));
	private static final SpriteSheet SPRITE_SHEET_2 = new SpriteSheet(
			Engine.loadImage("resources/tiles/DungeonTileset4.png"));
	private List<GameObject> monsters;
	private Sprite weaponTestSprite;

	private SoundMap soundLibrary;

	public Engine() {
		// this.canvas = new Canvas();
		this.soundLibrary = new SoundMap(
				"resources/sountracks/soundlibrary.txt");
		this.startGame = new StartGame(this);
		this.menu = true;
		this.tk = this.getToolkit();

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
		// dungeonTiles.loadSprites(16, 16);
		this.playerSprite = dungeonTiles.getSprite(4, 6);

		// Testing the animated sprites for the player
		BufferedImage playerSheetImage = loadImage(
				"resources/images/Player.png");
		SpriteSheet playerSheet = new SpriteSheet(playerSheetImage);
		playerSheet.loadSprites(20, 26);

		AnimatedSprite playerAnimations = new AnimatedSprite(playerSheet, 5);

		/**
		 * Testing melee
		 */
		weaponTestSprite = dungeonTiles.getSprite(10, 1);

		/**
		 * Initiating the players
		 */
		this.player = new Player(new Position(150, 200), 100, playerAnimations,
				5, 100, 100);
		this.monsters = currentMap.getMonsters();
		// this.object.add(player);
		/** GUI */
		this.GUI = new IngameInterface(player, WIDTH, HEIGHT);
		this.pauseMenu = new PauseMenu(
				this.loadImage("resources/images/Pause.png"));
		this.youDied = new YouDied(
				this.loadImage("resources/images/YouDied.png"));
		/**
		 * initiating key listener
		 */
		this.keyBinds = new KeyController(player, this);
		this.mouseListener = new MouseController(this);
		this.addKeyListener(keyBinds);
		this.addMouseListener(mouseListener);
	}

	/**
	 * This method will render everything onto the screen
	 */
	public void render() {
		/**
		 * if not in menu
		 */
		if (!menu) {

			BufferStrategy b = canvas.getBufferStrategy();
			Graphics g = b.getDrawGraphics();
			super.paint(g);
			this.renderer.clearArray();
			if (player.isDead()) {
				youDied.render(renderer, 1, 1);
			} else {
				/**
				 * If not paused
				 */
				if (!pauseMenu.isPaused()) {
					// Renders the map first (bottom layer of the image)
					renderer.renderMap(currentMap);
					/** Render Objects */
					for (GameObject gameObject : monsters) {
						gameObject.render(renderer, 3, 3);
					}
					this.player.render(renderer, 3, 3);
					/** Render GUI */
					this.GUI.render(renderer, GUI.XZOOM, GUI.YZOOM);
					/** Then render the Renderer */
				}
				/**
				 * If paused
				 */
				else {
					pauseMenu.render(renderer, 1, 1);
				}
			}
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
				/**
				 * Caught expected error
				 */
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
		/**
		 * If player is dead
		 */
		if (player.isDead()) {
			this.youDied.update(this);
		} else {
			/** if in start menu */
			if (menu) {
				startGame.update(this);
			} else {
				/** not paused */
				if (!this.pauseMenu.isPaused()) {
					this.player.update(this);
					for (GameObject gameObject : monsters) {
						gameObject.update(this);
					}
					this.GUI.update(this);
				}
				/**
				 * If paused
				 */
				else {
					this.pauseMenu.update(this);
				}
			}
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
			BufferedImage format = new BufferedImage(loadedImage.getWidth(),
					loadedImage.getHeight(), BufferedImage.TYPE_INT_RGB);
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
	 * This method is responsible for creating every map instance at the
	 * beginning, and storing it for later use until the player traverses
	 * through to each map.
	 *
	 * @return list of maps
	 */
	private HashMap<Integer, Map> initialiseMaps() {
		HashMap<Integer, Map> mapList = new HashMap<Integer, Map>();
		int count = 0;
		Map map_02 = new Map();
		map_02.initialiseMap("Map_02");
		mapList.put(count++, map_02);
		// Tutorial map
		Map tutMap = new Map();
		tutMap.initialiseMap("TutorialMap");
		mapList.put(count++, tutMap);
		// the first proper map
		Map map_01 = new Map();
		map_01.initialiseMap("Map_01");
		mapList.put(count++, map_01);
		// the final maze map.

		mapList.entrySet().stream()
				.forEach(entry -> System.out.println(entry.getValue()));
		return mapList;

	}

	public void setCurrentMap(Map currentMap) {
		this.currentMap = currentMap;
		this.monsters = this.currentMap.getMonsters();
		this.currentMapNumber++;
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
	 *
	 * @param name
	 * @return
	 */
	public static Sprite findSprite(String name) {
		dungeonTiles.loadSprites(16, 16);
		SPRITE_SHEET_2.loadSprites(16, 16);
		if (name.equals("Monster_One")) {
			return dungeonTiles.getSprite(3, 6);
		} else if (name.equals("Monster_Two")) {
			return SPRITE_SHEET_2.getSprite(3, 10);
		} else if (name.equals("Monster_Three")) {
			return SPRITE_SHEET_2.getSprite(3, 12);
		} else if (name.equals("Monster_Four")) {
			return SPRITE_SHEET_2.getSprite(4, 11);
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

	/**
	 * @return the pauseMenu
	 */
	public PauseMenu getPauseMenu() {
		return pauseMenu;
	}

	/*
	 * I pause menu is up
	 */
	public boolean isPaused() {
		return pauseMenu.isPaused();
	}

	/**
	 * @return the youDied
	 */
	public YouDied getYouDied() {
		return youDied;
	}

	public HashMap<Integer, Map> getMapList() {
		return mapList;
	}

	public void setMapList(HashMap<Integer, Map> mapList) {
		this.mapList = mapList;
	}

	public int getCurrentMapNumber() {
		return currentMapNumber;
	}

	public void setCurrentMapNumber(int currentMapNumber) {
		this.currentMapNumber = currentMapNumber;
	}

	public List<GameObject> getMonsters() {
		return this.monsters;
	}

}

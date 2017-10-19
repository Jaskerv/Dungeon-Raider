package gameEngine.map;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

import gameEngine.character.Monster;
import gameEngine.engine.Engine;
import gameEngine.engine.GameObject;
import gameEngine.item.Consumable;
import gameEngine.item.Item;
import gameEngine.util.Box;
import library1.ItemParser;
import library2.MapParser;

/**
 * Map instances store everything about each map, including what tiles
 * are inside the map, what type of map it is, and so forth.
 *
 * @author harry
 */
public class Map {

	private static final int WIDTH = 22;
	private static final int LENGTH = 22;
	private static final int TILE_SIZE = 96;
	/** Fixed size dungeon 2D array containing each individual tile */
	private Tile[][] map = new Tile[WIDTH][LENGTH];
	/** 0 for tutorial map, 1 for dungeon map, 2 for safe room, etc. */
	private int mapType;
	/** Number of monsters that are in the map */
	private int numMonsters;
	/** Time limit for being in this map (if this will be implemented). */
	private int timeLimit;
	/** Items */
	private java.util.Map<String, List<String>> itemMap = new HashMap<>();
	private List<Item> items = new ArrayList<>();
	private List<GameObject> monsters = new ArrayList<>();
	/** Monsters will have random sprite images */
	private int random;
	private String path;

	/**
	 * Initialises the Map
	 */
	public Map() {}

	/**
	 * This method generates the map from reading a file and stores it into
	 * its Map 2D Array.
	 * @param mapName  the name of the map
	 */
	public void initialiseMap(String mapName) {
		String path = "resources/maps/"+mapName+".txt";
		char[][] map = MapParser.parseFileToMapArray(path);
		initialiseFields();
		for (int y = 0; y < LENGTH; y++) {
			for (int x = 0; x < WIDTH; x++) {
				this.map[x][y] = new Tile(map[x][y], x * TILE_SIZE,
						y * TILE_SIZE, TILE_SIZE, TILE_SIZE);
			}
		}
		//reads items
		Scanner scan = null;
		try {
			scan = new Scanner(new File(path));
			itemMap = ItemParser.scan(scan);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} finally {
			scan.close();
		}
		//iterates through the map and creates the items
		itemMap.entrySet()
		.stream()
		.forEach(e -> {
			String category = e.getKey();
			List<String> items = e.getValue();
			createItem(category, items);
		});

	}

	/**
	 * Initialises each item and adds to the list of items.
	 * @param category  the name of the category of the item
	 * @param items  the items, each parsed in as one) string
	 */
	public void createItem(String category, List<String> items) {
		if (category.equals("Weapon")) {
			return; //not implemented yet
		}
		String name = "";
		int index = 0;
		int constructor = 0;
		if (category.equals("Consumable")) {
			constructor = 3;
		}
		else if (category.equals("Monster")) {
			constructor = 6;
		}
		//e.g. x y consumeTime mapNumber for item
		int[] parameters = new int[constructor];
		//each string
		for (int i = 0; i < items.size(); i++) {
			//each char
			for (int j = 0; j < items.get(i).length(); j++) {
				char c = items.get(i).charAt(j);
				if (c != ' ') {
					if (Character.isAlphabetic(c) || c == '_') {
						name += c;
					}
					//reads all of the grouped ints (if there is) in a chain
					else if (Character.isDigit(c)) {
						String value = "";
						int z;
						for (z = j; items.get(i).charAt(z) != ' '; z++)
						{
							//System.out.println(z);
							value += items.get(i).charAt(z);
							//otherwise it will iterate past the string size
							if (z + 1 == items.get(i).length()) {
								break;
							}
						}
						parameters[index++] = Integer.parseInt(value);
						value = "";
						j = z;
					}
				}
			}
			if (category.equals("Consumable")) {
				Item item = new Consumable(name, parameters[0], parameters[1],
						parameters[2],
						Engine.loadImage("resources/images/potion.png"),
						"resources/images/potion.png");
				associateItemToTile(item, parameters[0], parameters[1]);
				this.items.add(item);
			} else if (category.equals("Monster")) {
				GameObject monster = new Monster(name, parameters[0],
						parameters[1], parameters[2], parameters[3],
						parameters[4], parameters [5], randomImage(), this.path);
				monsters.add(monster);
			}
			name = "";
			index = 0;
			parameters = new int[constructor];
		}

	}

	private BufferedImage randomImage() {
		Random random = new Random();
		int min = 0;
		int max = 3;
		this.random = min + random.nextInt(max - min + 1);
		switch (this.random) {
		case 0:
			this.path = "resources/images/blob.png";
			return Engine.loadImage(path);
		case 1:
			this.path = "resources/images/Ghost.png";
			return Engine.loadImage(path);
		case 2:
			this.path = "resources/images/skeleton.png";
			return Engine.loadImage(path);
		case 3:
			this.path = "resources/images/smallOrc.png";
			return Engine.loadImage(path);
		}
		/** ORC (2x2 large boss monster)
		 * case 2:
			this.path = "resources/images/orc.png";
			return Engine.loadImage(path);
		 */
		return null;
	}

	/**
	 * Sets a tile to contain an item depending on where the item is placed
	 * @param item
	 * @param x
	 * @param y
	 */
	private void associateItemToTile(Item item, int x, int y) {
		for (int row = 0; row < LENGTH; row++) {
			for (int col = 0; col < WIDTH; col++) {
				Tile t = this.map[col][row];
				if (t.contains(x, y)) {
					t.setItem(item);
				}
			}
		}
	}

	/**
	 * This method initialises the fields by having the library read the
	 * first three values of the file, and set the fields according to those.
	 */
	private void initialiseFields() {
		int[] mapStates = MapParser.getMapStates();
		this.mapType = mapStates[0];
		this.numMonsters = mapStates[1];
		this.timeLimit = mapStates[2];
	}

	/**
	 * Returns if the player is able to move or not
	 * (checks if there's a wall in the way).
	 * @param box
	 * @return
	 */
	public boolean onWall(Box box) {
		Tile currentTile;
		for (int row = 0; row < LENGTH; row++) {
			for (int col = 0; col < WIDTH; col++) {
				currentTile = map[col][row];
				if(currentTile.contains(box)) {
					if(currentTile.isBoundary()) return false;
				}
			}
		}
		return true;
	}

	/**
	 * This will check if the player is stepping on a teleporting tile.
	 * @param box
	 * @return
	 */
	public int onTeleporter(Box box) {
		Tile currentTile;
		for (int row = 0; row < LENGTH; row++) {
			for (int col = 0; col < WIDTH; col++) {
				currentTile = map[col][row];
				if(currentTile.contains(box)) {
					if(currentTile.isTeleporter()
						|| currentTile.isForwardTeleporter()) {
						return 1;
					}
					else if(currentTile.isBackwardTeleporter()) {
						return -1;
					}
				}
			}
		}
		return 0;
	}


	public Tile[][] getMap() { return map; }

	public void setMap(Tile[][] map) { this.map = map; }

	public List<Item> getItems() { return items; }

	public void setItems(List<Item> items) { this.items = items; }

	public java.util.Map<String, List<String>> getItemMap() { return itemMap; }

	public void setItemMap(java.util.Map<String, List<String>> itemMap) {
		this.itemMap = itemMap;
	}

	public List<GameObject> getMonsters() { return monsters; }

	public void setMonsters(List<GameObject> monsters) {
		this.monsters = monsters;
	}

}

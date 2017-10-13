package dungeonraider.map;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;

import dungeonraider.item.Consumable;
import dungeonraider.item.Item;
import dungeonraider.util.Position;
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
						y * TILE_SIZE, TILE_SIZE, TILE_SIZE, false);
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
	 * @param items  the items, each parsed in as one string
	 */
	public void createItem(String category, List<String> items) {
		switch (category) {
		case "Weapon":
			//Let the Weapon class be finalised first
			break;
		case "Consumable":
			String name = "";
			int index = 0;
			//x, y, consumeTime, mapNumber
			int[] parameters = new int[4];
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
				Item item = new Consumable(name, parameters[0], parameters[1],
						parameters[2], parameters[3], Tile.findSprite(name));
				associateItemToTile(item, parameters[0], parameters[1]);
				this.items.add(item);
				name = "";
				index = 0;
				parameters = new int[4];
			}
			break;
		case "Shield":
			//Let Shield be finalised
			break;
		}
	}
	
	private void associateItemToTile(Item item, int x, int y) {
		for (int row = 0; row < LENGTH; row++) {
			for (int col = 0; col < WIDTH; col++) {
				Tile t = this.map[col][row];
				if (t.contains(x, y)) {
					t.setItem(item);
					System.out.println(t.getItem());
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

	public boolean onWall(int x, int y) {
		Tile currentTile;
		for (int row = 0; row < LENGTH; row++) {
			for (int col = 0; col < WIDTH; col++) {
				currentTile = map[col][row];
				if(currentTile.contains(x, y)) {
					if(currentTile.isBoundary()) return false;
				}
			}
		}
		return true;
	}

	public Tile[][] getMap() { return map; }

	public void setMap(Tile[][] map) { this.map = map; }

	public List<Item> getItems() { return items; }

	public void setItems(List<Item> items) { this.items = items; }
	
	public java.util.Map<String, List<String>> getItemMap() { return itemMap; }

	public void setItemMap(java.util.Map<String, List<String>> itemMap) {
		this.itemMap = itemMap;
	}

}

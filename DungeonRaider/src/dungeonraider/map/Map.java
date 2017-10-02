package dungeonraider.map;

import java.io.File;

import dungeonraider.engine.Engine;
import lib.MapParser;

/**
 * Map instances store everything about each map, including what tiles
 * are inside the map, what type of map it is, and so forth.
 *
 * @author harry
 */
public class Map {
	private static final int WIDTH = 22;
	private static final int LENGTH = 22;
	private static final int TILE_SIZE = findTileSize();
	/** Fixed size dungeon 2D array containing each individual tile */
	private Tile[][] map = new Tile[WIDTH][LENGTH];
	/** 0 for tutorial map, 1 for dungeon map, 2 for safe room, etc. */
	private int mapType;
	/** Number of monsters that are in the map */
	private int numMonsters;
	/** Time limit for being in this map (if this will be implemented). */
	private int timeLimit;

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
		File file = new File(path);
		char[][] map = MapParser.parseStringToMapArray(file);
		initialiseFields();
		for (int y = 0; y < LENGTH; y++) {
			for (int x = 0; x < WIDTH; x++) {
				this.map[x][y] = new Tile(map[x][y], x * TILE_SIZE,
						y * TILE_SIZE, TILE_SIZE, TILE_SIZE, false);
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

//	public static int findTileSize() {
//		int screenWidth = Engine.WIDTH;
//		return screenWidth / 22;
//	}

	public static int findTileSize() {
		return 96;
	}

	public Tile[][] getMap() { return map; }

	public void setMap(Tile[][] map) { this.map = map; }

}

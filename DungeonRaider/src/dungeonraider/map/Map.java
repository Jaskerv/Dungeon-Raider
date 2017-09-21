package dungeonraider.map;

import java.io.File;

import lib.MapParser;

public class Map {
	private static final int WIDTH = 22;
    private static final int LENGTH = 22;
    private static final int TILE_WIDTH = 30;
    private static final int TILE_HEIGHT = 30;
	/** Fixed size dungeon 2D array containing each individual tile */
    private Tile[][] map = new Tile[WIDTH][LENGTH];
	/** 0 for dungeon map, 1 for safe room, etc. */
	private final int mapType;
	/** Number of monsters that are in the map */
	private int numMonsters;
	/** Time limit for being in this map (if this will be implemented). */
	private int timeLimit;

	/**
	 * Map Constructor
	 *
	 * @param mapType
	 * @param numMonsters
	 * @param timeLimit
	 */
	public Map(int mapType, int numMonsters, int timeLimit) {
		this.mapType = mapType;
		this.numMonsters = numMonsters;
		this.timeLimit = timeLimit;
	}

	/**
	 * Assuming you get parsed in the map as a string, e.g. "WWWW\nWWPW" - Where
	 * each W is a wall, and P is the player, then:
	 *
	 * @param mapString
	 */
	public void intialiseMap() {
		try {
			//Tutorial map for now
			String path = "resources/maps/TutorialMap.txt";
			File file = new File(path);
			char[][] map = MapParser.parseStringToMapArray(file);
			for (int y = 0; y < LENGTH; y++) {
				for (int x = 0; x < WIDTH; x++) {
					this.map[x][y] = new Tile(x*TILE_WIDTH, y*TILE_HEIGHT);
				}
			}	
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}

	public Tile[][] getMap() {
		return map;
	}

	public void setMap(Tile[][] map) {
		this.map = map;
	}
	
	

}

package dungeonraider.map;

public class Map {
	
	private static final int SIZE = 50; //placeholder value
	/** Fixed size dungeon 2D array containing each individual tile */
	private Tile[][] map = new Tile[SIZE][SIZE];
	/** 0 for dungeon map, 1 for safe room, etc. */
	private final int mapType;
	/** Number of monsters that are in the map */
	private int numMonsters;
	/** Time limit for being in this map (if this will be implemented). */
	private int timeLimit;
	
	/**
	 * Map Constructor
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
	 * Assuming you get parsed in the map as a string, e.g.
	 * "WWWW\nWWPW" - Where each W is a wall, and P is the player, then:
	 * @param mapString
	 */
	public void intialiseMap(String mapString) {
		int row = 0;
		for (int i = 0; i < mapString.length(); i++) {
			char c = mapString.charAt(i);
			if (c == '\n') {
				row++;
				continue;
			}
			Tile tile = getTileObject(c, i, row);
			map[i][row] = tile;
		} 
	}
	
	/**
	 * Grabs the appropriate tile object for the character that is parsed
	 * in.
	 * @param c - the character being parsed in
	 * @param x - the x co-ordinate of the tile being made
	 * @param y - the y co-ordinate of the tile being made
	 * @return the new tile object, or null if none was found
	 */
	public Tile getTileObject(char c, int x, int y) {
		switch (c) {
			case 'W':
				//return new Tile(Images.Wall, x*SIZE, y*SIZE);
				return new Tile(null, x*SIZE, y*SIZE);
		}
		return null;
	}

}

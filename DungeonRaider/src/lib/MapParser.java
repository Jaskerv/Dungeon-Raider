package lib;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Random;
import java.util.stream.Stream;

/**
 * The Map library contains the logic for the creation of the maps and has
 * helpful map-related methods that can be used throughout building Dungeon
 * Raider.
 *
 * @author nguonharr
 *
 */
public class MapParser {

    /** Every 5th map will be a safe-room */
    private static int safeRoomCounter = 0;
    private static final int WIDTH = 22;
    private static final int LENGTH = 22;
    private static final int NUM_STATES = 3;
    /** Shows map state information e.g. no. of monsters */
    private static int[] mapStates = new int[NUM_STATES];

    /**
     * This method will read a file and covert it to a map (2D array of chars).
     * @param path  the path to the file.
     * @return map  The 2D array map.
     * @throws StringIndexOutOfBoundsException  if the map is too small
     */
    public static char[][] parseFileToMapArray(String path) {
        char[][] map = new char[WIDTH][LENGTH];
        //every fifth room, there's a saferoom
        if (safeRoomCounter % 5 == 0 && safeRoomCounter != 0) { //tut level
            map = generateSafeRoom();
            return map;
        }
        //read the file into the 2d map array
        Stream<String> contentStream = null;
        String contents = null;
        try {
			contentStream = Files.lines(Paths.get(path));
			contents = contentStream
					.reduce((a,b) -> a + b)
					.orElse("end of file")
					.replaceAll(" ", "");
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
        	contentStream.close();
        }
		//If it detects a '{' (item contents of the map), then it skips over it
		//and skips all of the contents contained inside it until it finds a
		//closing brace '}'.
		int index = 0;
		boolean openingBracesFound = false;
		char itemContents = contents.charAt(index);
		if (itemContents == '{') {
			openingBracesFound = true;
			for (index = 1; contents.charAt(index) != '}'; index++);
		}
		if (contents.length() > index + 1 + (LENGTH*WIDTH) + NUM_STATES) {
			//System.out.println(contents.length());
			//System.out.println(index + (LENGTH*WIDTH) + NUM_STATES);
			throw new IllegalArgumentException("File size is too large");
		}

		//the first three values are the map states
		try {
			//if an opening brace was found, then the current scanner pointer
			//will be pointed to a '}'. Instead it should point to the next
			//element, which should be an integer specifying the map states.
			//else, if it didn't find a '{' at the start, then the current
			//scanner pointer element should be the map state integer
			mapStates[0] = openingBracesFound ?
					Character.getNumericValue(contents.charAt(++index)) :
						Character.getNumericValue(contents.charAt(index));
			mapStates[1] = Character.getNumericValue(contents.charAt(++index));
			mapStates[2] = Character.getNumericValue(contents.charAt(++index));
		}
		catch(NumberFormatException e) {
    		throw new IllegalArgumentException("Invalid map syntax. Expected"
    				+ " numbers for the first three letters,"
    				+ " but received characters instead.");
    	}
		//parses all chars into the map array
		try {
	        for (int y = 0; y < LENGTH; y++) {
	        	for (int x = 0; x < WIDTH; x++) {
	        		if (!Character.isDigit(contents.charAt(index+1)) &&
	        				!Character.isLetter(contents.charAt(index+1))) {
	        			throw new IllegalArgumentException("Tiles must be a"
	        					+ " letter or a number");
	        		}
	        		map[x][y] = contents.charAt(++index);
	        	}
	        }
		}
		catch (StringIndexOutOfBoundsException e) {
			throw new StringIndexOutOfBoundsException("Map file is too small");
		}
        safeRoomCounter++;
        return map;
    }

    /**
     * This method returns the safe room map as a 2D array. This is incomplete
     * as the safe rooms have not been designed yet.
     * TODO  generateSafeRoom()
     * @return  The safe room as a 2D array of chars.
     */
    private static char[][] generateSafeRoom() {
    	char[][] safeRoomMap = new char[WIDTH][LENGTH];
    	for (int y = 0; y < LENGTH; y++) {
    		for (int x = 0; x < WIDTH; x++) {
    			safeRoomMap[x][y] = 'T';
    		}
    	}
    	safeRoomCounter++;
    	return safeRoomMap;
    }

    /**
     * Getter for DungeonRaider to access the mapStates array.
     * @return
     */
    public static int[] getMapStates() {
    	return mapStates;
    }

    /**
     * This method generates a number of monsters between the range of 1-10.
     * WARNING (May be useless since we're creating each level individually).
     * @return numMonsters  the number of monsters.
     */
    public static int generateMonsters() {
        Random random = new Random();
        int minimum = 1;
        int maximum = 10;
        int numMonsters = minimum + random.nextInt(maximum - minimum + 1);
        return numMonsters;
    }

}
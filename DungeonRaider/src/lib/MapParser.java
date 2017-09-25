package lib;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Random;
import java.util.Scanner;

import org.junit.experimental.theories.Theories;

/**
 * The Map library contains the logic for the creation of the maps and has
 * helpful map-related methods that can be used throughout building Dungeon
 * Raider.
 *
 * @author nguonharr
 * @version 1.0
 *
 */
public class MapParser {

    /** Every 5th map will be a safe-room */
    private static int safeRoomCounter = 0;
    private static final int WIDTH = 22;
    private static final int LENGTH = 22;
    private static int[] mapStates = new int[3];

    /**
     * This method will convert a string to a map (2D array of chars).
     * @param s  the string being parsed in.
     * @return map  The 2D array map.
     */
    public static char[][] parseStringToMapArray(File file) {
//        if (s.length() > (WIDTH*LENGTH) + 22) {
//            throw new IllegalArgumentException("Map is too large");
//        }
        char[][] map = new char[WIDTH][LENGTH];
        //every fifth room, there's a saferoom
        if (safeRoomCounter % 5 == 0 && safeRoomCounter != 0) { //tut level
            map = generateSafeRoom();
            return map;
        }
        //read the file into the 2d map array
        Scanner scan = null;
		try {
			scan = new Scanner(file);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		//the first three values are the map states
		mapStates[0] = Integer.parseInt(scan.next());
		mapStates[1] = Integer.parseInt(scan.next());
		mapStates[2] = Integer.parseInt(scan.next());
		//parses all chars into the map array
        for (int y = 0; y < LENGTH; y++) {
        	for (int x = 0; x < WIDTH; x++) {
        		map[x][y] = scan.next().charAt(0);
        	}
        }
        safeRoomCounter++;
        return map;
    }
    public static char[][] parseStringToMapArray(String s) {
    	return new char[1][1];
    }
    
    /**
     * This method returns the safe room map as a 2D array.
     * TODO  generateSafeRoom()
     * @return  Safe room as a 2D array of chars.
     */
    private static char[][] generateSafeRoom() {
    	char[][] safeRoomMap = new char[WIDTH][LENGTH];
    	safeRoomCounter++;
    	return safeRoomMap;
    }
    
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
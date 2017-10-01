package lib;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Random;
import java.util.Scanner;

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
    private static int[] mapStates = new int[3];

    /**
     * This method will read a file and covert it to a map (2D array of chars).
     * @param s  the string being parsed in.
     * @return map  The 2D array map.
     */
    public static char[][] parseStringToMapArray(File file) {  	
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
		try {
			mapStates[0] = Integer.parseInt(scan.next());
			mapStates[1] = Integer.parseInt(scan.next());
			mapStates[2] = Integer.parseInt(scan.next());
		}
		catch(NumberFormatException e) {
    		throw new IllegalArgumentException("Invalid map syntax. Expected"
    				+ " numbers for the first three letters,"
    				+ " but received characters instead.");
    	}
		//parses all chars into the map array
        for (int y = 0; y < LENGTH; y++) {
        	for (int x = 0; x < WIDTH; x++) {
        		map[x][y] = scan.next().charAt(0);
        	}
        }
        if (scan.hasNext()) {
            scan.close();
        	throw new IllegalArgumentException("Map is too large");
        }
        scan.close();
        safeRoomCounter++;
        return map;
    }
    
    /**
     * This method is only used for parsing in strings to simulate a file.
     * This method is used for testing purposes (JUnit tests). The logic of
     * this method is the same as the main parseStringToMapArray() method, so
     * the functionality of both methods will be tested through this one.
     * @param s  The 'file' string
     * @return  The 2d map array
     */
    public static char[][] parseStringToMapArray(String s) {
    	//gets rid of all whitespace and \n for easier iteration of the string
    	s = s.replaceAll("\\s+","");
    	if (s.length() > (WIDTH*LENGTH) + NUM_STATES) {
    	    throw new IllegalArgumentException("Map is too large");
    	}
    	if (	   !Character.isDigit(s.charAt(0)) 
    			|| !Character.isDigit(s.charAt(1)) 
    			|| !Character.isDigit(s.charAt(2)) ) {
    		throw new IllegalArgumentException("Invalid map syntax");	
    	}
    	char[][] map = new char[WIDTH][LENGTH];
    	//every fifth room, there's a saferoom
    	if (safeRoomCounter % 5 == 0 && safeRoomCounter != 0) { //tut level
    		map = generateSafeRoom();
    		return map;
    	}
    	//read the String into the 2d map array
    	//the first three values are the map states.
    	int index = 0;
    	try 
    	{
    	mapStates[0] = Integer.parseInt(Character.toString(s.charAt(index++)));
    	mapStates[1] = Integer.parseInt(Character.toString(s.charAt(index++)));
    	mapStates[2] = Integer.parseInt(Character.toString(s.charAt(index++)));
    	}
    	catch(NumberFormatException e) {
    		//First three letters were not all ints
    		throw new IllegalArgumentException("Invalid map syntax. Expected"
    				+ " numbers for the first three letters,"
    				+ " but received characters instead.");
    	}
    	//parses all chars into the map array
    	for (int y = 0; y < LENGTH; y++) {
    		for (int x = 0; x < WIDTH; x++) {
    			map[x][y] = s.charAt(index++);
    		}
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
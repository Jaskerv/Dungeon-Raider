package lib;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import org.junit.Test;

/**
 * This class will test out the functionality of the Map Library.
 * Every test assumes that the 2D map array has the dimensions 50x10.
 * @author harry
 */
public class MapParserTests {

    /**
     * This method will check the boundaries of the map (at each corner).
     */
    @Test
    public void testStringToMap_01() {
    	String tutMap = "0 0 0\n" + 
    			"G W W W W W W W W W W W W W W W W W W W W O \n" + 
    			"W W W W W W W W W W W W W W W W W W W W W W \n" + 
    			"W W W W W W W W W W W W W W W W W W W W W W \n" + 
    			"W W W W W W W W W W W W W W W W W W W W W W \n" + 
    			"W W W W W W W W W W W W W W W W W W W W W W \n" + 
    			"W W W W W W W W W W W W W W W W W W W W W W \n" + 
    			"W W W W W W W W W W W W W W W W W W W W W W \n" + 
    			"W W W W W W W W W W W W W W W W W W W W W W \n" + 
    			"W W W W W W W W W W W W W W W W W W W W W W \n" + 
    			"W W W W W W W W W W W W W W W W W W W W W W \n" + 
    			"W W W W W W W W W W W W W W W W W W W W W W \n" + 
    			"W W W W W W W W W W W W W W W W W W W W W W \n" + 
    			"W W W W W W W W W W W W W W W W W W W W W W \n" + 
    			"W W W W W W W W W W W W W W W W W W W W W W \n" + 
    			"W W W W W W W W W W W W W W W W W W W W W W \n" + 
    			"W W W W W W W W W W W W W W W W W W W W W W \n" + 
    			"W W W W W W W W W W W W W W W W W W W W W W \n" + 
    			"W W W W W W W W W W W W W W W W W W W W W W \n" + 
    			"W W W W W W W W W W W W W W W W W W W W W W \n" + 
    			"W W W W W W W W W W W W W W W W W W W W W W \n" + 
    			"W W W W W W W W W W W W W W W W W W W W W W \n" + 
    			"O W W W W W W W W W W W W W W W W W W W W D \n";	
        try {
            char[][] map = MapParser.parseStringToMapArray(tutMap);
	        assertTrue(map[0][0]  ==  'G');
	        assertTrue(map[21][0] ==  'O');
	        assertTrue(map[0][21]  ==  'O');
	        assertTrue(map[21][21] ==  'D');
        }
        catch (IllegalArgumentException | IndexOutOfBoundsException e) {
            e.printStackTrace();
        }
    }

    
    /**
     * This test ensures that every character from the map string is placed 
     * into the correct tile index.
     */
    @Test
    public void testStringToMap_02() {
    	String randomMap = "0 0 0\n" +
	    			"MCBDIBETBGABAOPPPGMSSB\n" + 
	    			"UVTYRBUXTNEANGVJUPOJID\n" + 
	    			"APJWJKKRTRTASDSOMSWEVB\n" + 
	    			"ECDRAOEVSKIIHCUDHOWRLD\n" + 
	    			"EYSUHURHAQJZEEMYAFKZCB\n" + 
	    			"XBNCAEQBUORMWNOQBGJHQG\n" + 
	    			"MEZTQRENVXBOCFMDUCBJDH\n" + 
	    			"JTBDNXABRONMJGHHHCZEQM\n" + 
	    			"HDATOKALECOXDWJUPUKQKF\n" + 
	    			"PXQKWXYILBRKXRGHCMDFDB\n" + 
	    			"ROCAYJTFJOMRMLWHABYFLA\n" + 
	    			"SSGIKZICXLRASNXWKZCJZZ\n" + 
	    			"STWBCVTHJSLSGPYYCXBZFJ\n" + 
	    			"QEQAIRKMHIPBDWZUMPEXFW\n" + 
	    			"EQKOUALTLZZXKNNTAXJLJD\n" + 
	    			"CPMJXRNVNXNSSYFRNOGCMU\n" + 
	    			"HUDTLHQJJYTMLPROMZLAND\n" + 
	    			"RSUKPOASGPHMCBAGPFINAG\n" + 
	    			"EPNNSDFHHWMQHHLPTRBNND\n" + 
	    			"BZXMEVCMSZNVKKHYILZAKH\n" + 
	    			"WOKQVNQDIEAFDSGETKUYAF\n" + 
	    			"DFJTSQXKNQUFRLEVFGHXPE\n";
    	
    	randomMap = randomMap.replaceAll("\\s+","");
    	char[][] map = MapParser.parseStringToMapArray(randomMap);
    	int index = 3; //First three indexes are the map states, so start at 3
    	for (int y = 0; y < map[0].length; y++) {
    		for (int x = 0; x < map.length; x++) {
    			char c = randomMap.charAt(index) == '\n' ? 
    					randomMap.charAt(++index): randomMap.charAt(index);
    			assertEquals(map[x][y], c);
    			index++;
    		}
    	}
    }
    
    /**
     * Tests if the MapParser successfully throws an IllegalArgumentException if
     * the map string with the wrong syntax has been parsed in. In this case,
     * the first three characters aren't integers. It normally expects three
     * integers at the beginning (which are the map states).
     */
    @Test
    public void testStringToMap_03() {
    	String tutMap = "q w e\n" + 
    			"W W W W W W W W W W W W W W W W W W W W W W \n" + 
    			"W W W W W W W W W W W W W W W W W W W W W W \n" + 
    			"W W W W W W W W W W W W W W W W W W W W W W \n" + 
    			"W W W W W W W W W W W W W W W W W W W W W W \n" + 
    			"W W W W W W W W W W W W W W W W W W W W W W \n" + 
    			"W W W W W W W W W W W W W W W W W W W W W W \n" + 
    			"W W W W W W W W W W W W W W W W W W W W W W \n" + 
    			"W W W W W W W W W W W W W W W W W W W W W W \n" + 
    			"W W W W W W W W W W W W W W W W W W W W W W \n" + 
    			"W W W W W W W W W W W W W W W W W W W W W W \n" + 
    			"W W W W W W W W W W W W W W W W W W W W W W \n" + 
    			"W W W W W W W W W W W W W W W W W W W W W W \n" + 
    			"W W W W W W W W W W W W W W W W W W W W W W \n" + 
    			"W W W W W W W W W W W W W W W W W W W W W W \n" + 
    			"W W W W W W W W W W W W W W W W W W W W W W \n" + 
    			"W W W W W W W W W W W W W W W W W W W W W W \n" + 
    			"W W W W W W W W W W W W W W W W W W W W W W \n" + 
    			"W W W W W W W W W W W W W W W W W W W W W W \n" + 
    			"W W W W W W W W W W W W W W W W W W W W W W \n" + 
    			"W W W W W W W W W W W W W W W W W W W W W W \n" + 
    			"W W W W W W W W W W W W W W W W W W W W W W \n" + 
    			"W W W W W W W W W W W W W W W W W W W W W W \n";	
        try {
            MapParser.parseStringToMapArray(tutMap);
        }
        catch (IllegalArgumentException e) {
        	//good
        }
    }
    
    /**
     * Tests parsing in too large of a map. The MapParser should throw an
     * IllegalArgumentException. This test map string is two rows and one 
     * column too large.
     */
    @Test
    public void testStringToMap_04() {
    	String testMap = "0 0 0\n" + 
    			"W W W W W W W W W W W W W W W W W W W W W W W \n" + 
    			"W W W W W W W W W W W W W W W W W W W W W W W \n" + 
    			"W W W W W W W W W W W W W W W W W W W W W W W \n" + 
    			"W W W W W W W W W W W W W W W W W W W W W W W \n" + 
    			"W W W W W W W W W W W W W W W W W W W W W W W \n" + 
    			"W W W W W W W W W W W W W W W W W W W W W W W \n" + 
    			"W W W W W W W W W W W W W W W W W W W W W W W \n" + 
    			"W W W W W W W W W W W W W W W W W W W W W W W \n" + 
    			"W W W W W W W W W W W W W W W W W W W W W W W \n" + 
    			"W W W W W W W W W W W W W W W W W W W W W W W \n" + 
    			"W W W W W W W W W W W W W W W W W W W W W W W \n" + 
    			"W W W W W W W W W W W W W W W W W W W W W W W \n" + 
    			"W W W W W W W W W W W W W W W W W W W W W W W \n" + 
    			"W W W W W W W W W W W W W W W W W W W W W W W \n" + 
    			"W W W W W W W W W W W W W W W W W W W W W W W \n" + 
    			"W W W W W W W W W W W W W W W W W W W W W W W \n" + 
    			"W W W W W W W W W W W W W W W W W W W W W W W \n" + 
    			"W W W W W W W W W W W W W W W W W W W W W W W \n" + 
    			"W W W W W W W W W W W W W W W W W W W W W W W \n" + 
    			"W W W W W W W W W W W W W W W W W W W W W W W \n" + 
    			"W W W W W W W W W W W W W W W W W W W W W W W \n" + 
    			"W W W W W W W W W W W W W W W W W W W W W W W \n" + 
    			"W W W W W W W W W W W W W W W W W W W W W W W \n" + 
    			"W W W W W W W W W W W W W W W W W W W W W W W \n" + 
    			"W W W W W W W W W W W W W W W W W W W W W W W \n";	
        try {
            MapParser.parseStringToMapArray(testMap);
        }
        catch (IllegalArgumentException e) {
        	//good
        }
    }
    
    /**
     * This tests out the functionality of the MapLibrary.generateMonsters()
     * method. The method ensures that the returned value (rval) is 1 < x < 10.
     */
    @Test
    public void testGenerateMonsters_01() {
    	for (int i = 0; i < 100; i ++) {
    		int rval = MapParser.generateMonsters();
    		if (rval < 1 || rval > 10 ) {
    			fail("The return value has surpassed the specified range");
    		}
    	}
    }
}
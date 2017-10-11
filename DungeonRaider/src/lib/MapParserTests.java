package lib;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import org.junit.Test;

/**
 * This class will test out the functionality of the Map Library.
 * Every test assumes that the 2D map array has the dimensions 50x10.
 * @author harry
 */
public class MapParserTests {

	/**
	 * This method will create a file to test out the map strings created
	 * in the tests. The strings being created in the tests will be parsed
	 * into a temp file, which is then parsed into the MapLibrary parseMap()
	 * method.
	 * @param fileName  name of the temp file being created
	 * @param contents  the string (map string) that will be written to the file
	 * @return  the temp file that will be tested
	 * @return  the path to the temp file (new version with streams)
	 */
	public String createTestFile (String fileName, String contents) {
		try {
			File file = File.createTempFile(fileName, ".txt");
			String path = file.getPath();
			System.out.println(path);
			BufferedWriter bWriter = new BufferedWriter(new FileWriter(file));
			for (int i = 0; i < contents.length(); i++) {
				bWriter.append(contents.charAt(i));
				bWriter.append(" ");
			}
			bWriter.close();
			return path;
		}
		catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * Gets rid of all whitespaces, \n's, etc. This makes it easier to work with
	 * the new string.
	 * @param string
	 * @return
	 */
	public String formStructure(String string) {
		return string.replaceAll("\\s","");
	}


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
    	tutMap = formStructure(tutMap);
        try {
        	String testFile = createTestFile("testFile", tutMap);
            char[][] map = MapParser.parseFileToMapArray(testFile);
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
    	randomMap = formStructure(randomMap);
    	String testFile = createTestFile("testFile", randomMap);
        char[][] map = MapParser.parseFileToMapArray(testFile);
    	int index = 3; //First three indexes are the map states, so start at 3
    	for (int y = 0; y < map[0].length; y++) {
    		for (int x = 0; x < map.length; x++) {
    			char c = randomMap.charAt(index++);
    			assertEquals(map[x][y], c);
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
    	tutMap = formStructure(tutMap);
        try {
        	String testFile = createTestFile("testFile", tutMap);
            MapParser.parseFileToMapArray(testFile);
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
    	testMap = formStructure(testMap);
        try {
        	String testFile = createTestFile("testFile", testMap);
            MapParser.parseFileToMapArray(testFile);
        }
        catch (IllegalArgumentException e) {
        	//good
        }
    }

    /**
     * With the introduction of items being inside the map files, this method
     * tests to ensure that the map is still being generated correctly.
     */
    @Test
    public void testStringToMap_05() {
    	String testMap =
    			"{ \n" +
    			"Chest1 4 3 \n" +
    			"Weapon-Chest1 ShortSword 0 2 10 \n" +
    			"} \n" +
    			"0 0 0\n" +
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
    	testMap = formStructure(testMap);
        try {
        	String testFile = createTestFile("testFile", testMap);
            MapParser.parseFileToMapArray(testFile);
        }
        catch (IllegalArgumentException e) {
        	fail("Nothing bad was supposed to happen");
        }
    }

    /**
     * Tests parsing in an uneven map (not a square)
     */
    @Test
    public void testStringToMap_06() {
    	String testMap =
    			"{ \n" +
    			"Chest1 4 3 \n" +
    			"Weapon-Chest1 ShortSword 0 2 10 \n" +
    			"} \n" +
    			"0 0 0\n" +
    			"W W W W W W W W W W W W W W W W W W W W W W \n" +
    			"W W W W W W W W W W W \n";
    	testMap = formStructure(testMap);
        try {
        	String testFile = createTestFile("testFile", testMap);
            MapParser.parseFileToMapArray(testFile);
        }
        catch (StringIndexOutOfBoundsException e) {
        	//good, this exception was meant to be thrown
        }
    }


    //Checks for special characters
    public void testStringToMap_07() {
       	String testMap =
    			"{ \n" +
    			"Chest1 4 3 \n" +
    			"Weapon-Chest1 ShortSword 0 2 10 \n" +
    			"} \n" +
    			"0 0 0\n" +
    			"W W W W W W W W $ # % ^ W W W W W W W W W \n" +
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
    	testMap = formStructure(testMap);
        try {
        	String testFile = createTestFile("testFile", testMap);
            MapParser.parseFileToMapArray(testFile);
        }
        catch (IllegalArgumentException e) {
        	System.out.println("good");
        }
    }


    /**
     * This tests out the functionality of the MapLibrary.generateMonsters()
     * method. The method ensures that the returned value (rval) 1 < x < 10.
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
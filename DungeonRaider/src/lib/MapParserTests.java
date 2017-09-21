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
     * This method will test the boundaries of the map (at each corner).
     */
    @Test
    public void testStringToMap_01() {
    	
        try {
            char[][] map = MapParser.parseStringToMapArray("");
            map[0][0]  =  'Q';
            map[49][0] =  'Q';
            map[0][9]  =  'Q';
            map[49][9] =  'Q';
        }
        catch (IllegalArgumentException | IndexOutOfBoundsException e) {
            e.printStackTrace();
        }
    }

    /**
     * This test will ensure that the each corner tile is set to the
     * correct index.
     */
    @Test
    public void testStringToMap_02() {
        String testMap =
                    "GWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWO\n" +
                    "WWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWW\n" +
                    "WWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWW\n" +
                    "WWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWW\n" +
                    "WWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWW\n" +
                    "WWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWW\n" +
                    "WWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWW\n" +
                    "WWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWW\n" +
                    "WWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWW\n" +
                    "OWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWD\n";
            char[][] map = MapParser.parseStringToMapArray(testMap);
            assertTrue(map[0][0]  ==  'G');
            assertTrue(map[49][0] ==  'O');
            assertTrue(map[0][9]  ==  'O');
            assertTrue(map[49][9] ==  'D');
            
    }
    
    /**
     * This test ensures that every character from the map string is placed 
     * into the correct tile.
     */
    @Test
    public void testStringToMap_03() {
    	String randomMap = 
	    			"ZJXDWUUWOIFKYVIIPFBLGZKITELKRCBUGUMCXDCZOILIOUHBYD\n" + 
	    			"FCQWXZNOSXMEFQKEBITCLEBUVBPITCXSICUNOKMRILYVRYRAYG\n" + 
	    			"QTXNXRJRUXQUFDTTXYDZEPBVEGHEEMCLSRMZWOYMFJGRUMMYOO\n" + 
	    			"DNWDXAKZGRQPEZYRLZHHZMZMRGDMQMJPBVMPVXXZGWIDTXHOID\n" + 
	    			"SNWAPMDQCUSWEQZEQZDITJOHODPQVRPPJDSNKQSNJSTWCHKFNG\n" + 
	    			"SKZLNCHLPPFNKBDDGXKGYPFMRKCTXPZAWZSZZWDSASXGLVMGHB\n" + 
	    			"FOJBGOVZSNYOUIBBDXCIWRRFSLQGJEWTHORXCXRENLTCFKDTYQ\n" + 
	    			"HWTOYTVJVDQVSENSHKSEGVIFNHLGIFVKMRSPJZICODIWOWDMJA\n" + 
	    			"YGAYNCEIVVOCGDPCRMJTRXNNRNOPGBQWSQEIDWBZIREQPLNTAQ\n" + 
	    			"SAQNFPRHGOUMCTMAVLVKLYNDJMLLWOVWWEXDEKEYUUWCOUBPNI\n";
    	char[][] map = MapParser.parseStringToMapArray(randomMap);
    	int index = 0;
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
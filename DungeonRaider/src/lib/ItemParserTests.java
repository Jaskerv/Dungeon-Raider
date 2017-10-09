package lib;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.List;
import java.util.Map;

import org.junit.Test;

import lib.ItemParser.ParserException;

/**
 * This is a list of JUnit Tests that tests the library
 *
 * @author Jono Yan
 *
 */
public class ItemParserTests {

	/////////////Methods used for tests///////////////
	public void testKey(Map<String, List<String>> mp, String key) {
		if (!mp.containsKey(key)) {
			fail("Supposed to have the key: " + key);
		}
	}

	public void testValue(List<String> s, String value) {
		if (!s.contains(value)) {
			fail("Supposed to have the correct value: " + value
					+ ", Instead got: " + s);
		}
	}

	///////////////////////////////////////////////////

	/**
	 * This tests the input specifications.
	 */
	@org.junit.Test
	public void test_Exception_01() {
		try {
			String text = "";
			Map<String, List<String>> mp = ItemParser.parse(text);
		} catch (Exception e) {
			assertTrue(e.getMessage().equals("Input is empty or null."));
		}
	}

	/**
	 * This tests 1 entry whether the algorithm works or not
	 */
	@org.junit.Test
	public void test_Category_01() {
		try {
			String text = "{\nWeapon Short_Sword 1 2 3 4\n}";
			Map<String, List<String>> mp = ItemParser.parse(text);
			String key = "Weapon";
			String value = "Short_Sword 1 2 3 4";
			testKey(mp, key);
			List<String> s = mp.get(key);
			testValue(s, value);
		} catch (Exception e) {
			fail("Not supposed to throw exception.");
		}
	}

	/**
	 * This tests 1 entry whether the algorithm works or not
	 */
	@org.junit.Test
	public void test_Category_02() {
		try {
			String text = "{\nWeapon Short_Sword 1 2 3 4\n"
					+ "Consumable Small_Health_Potion 30 0\n}";
			Map<String, List<String>> mp = ItemParser.parse(text);
			String key = "Weapon";
			String value = "Short_Sword 1 2 3 4";
			testKey(mp, key);
			List<String> s = mp.get(key);
			testValue(s, value);
			key = "Consumable";
			value = "Small_Health_Potion 30 0";
			testKey(mp, key);
			s = mp.get(key);
			testValue(s, value);
		} catch (Exception e) {
			fail("Not supposed to throw exception.");
		}
	}

	/**
	 * This tests whether inputs are correct
	 */
	@org.junit.Test
	public void test_Category_03() {
		try {
			String text = "{\nWeapon\n}";
			Map<String, List<String>> mp = ItemParser.parse(text);
			if (mp != null) {
				fail("Supposed to return null");
			}
		} catch (Exception e) {
			fail("Not supposed to recieve exception.");
		}
	}
	
	/**
	 * Tests parsing in a proper map file. Any exception thrown will indicate
	 * it's unsuccessful.
	 */
	@Test
	public void testParseMap_01() {
		String testMap = 
    			"{ \n" +	
    			"Weapon Short_Sword 1 2 3 4\n" +
    			"Consumable Small_Health_Potion 30 0 \n" +
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
		try {
			ItemParser.parse(testMap);
		} catch (Exception e) {
			fail("fix me");
		}
	}
	
	/**
	 * Tests invalid input (no opening brace found)
	 */
	@Test
	public void testParseMap_02() {
		String testMap = 
    			"\n" +	
    			"Weapon Short_Sword 1 2 3 4\n" +
    			"Consumable Small_Health_Potion 30 0 \n" +
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
		try {
			ItemParser.parse(testMap);
		} catch (IllegalArgumentException | ParserException e) {
			//good, exception is successfully thrown
		}
	}
	
	/**
	 * Tests invalid input (no closing brace found)
	 */
	@Test
	public void testParseMap_03() {
		String testMap = 
    			"{ \n" +	
    			"Weapon Short_Sword 1 2 3 4\n" +
    			"Consumable Small_Health_Potion 30 0 \n" +
    			"\n" +
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
		try {
			ItemParser.parse(testMap);
		} catch (IllegalArgumentException | ParserException e) {
			//good, exception is successfully thrown
		}
	}

}

package library1;

import static org.junit.Assert.*;

import java.security.KeyStore.Entry;
import java.util.List;
import java.util.Map;

/**
 * This is a list of JUnit Tests that tests the library
 *
 * @author Jono Yan
 *
 */
public class Test {
	/**
	 * This tests the input specifications.
	 */
	@org.junit.Test
	public void test_Exception_01() {
		try {
			String text = "";
			Parser parse = new Parser();
			Map<String, List<String>> mp = parse.parse(text);
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
			String text = "Weapon Short_Sword 1 2 3 4";
			Map<String, List<String>> mp = Parser.parse(text);
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
			String text = "Weapon Short_Sword 1 2 3 4\nConsumable Small_Health_Potion 30 0";
			Map<String, List<String>> mp = Parser.parse(text);
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
			String text = "Weapon";
			Map<String, List<String>> mp = Parser.parse(text);
			if (mp != null) {
				fail("Supposed to return null");
			}
		} catch (Exception e) {
			fail("Not supposed to recieve exception.");
		}
	}

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
}

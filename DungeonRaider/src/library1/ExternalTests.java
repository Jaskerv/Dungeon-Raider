package library1;

import static org.junit.Assert.fail;

import java.util.List;
import java.util.Map;

public class ExternalTests {

	/**
	 * Testing to see whether it removes tabs from string texts
	 */
	@org.junit.Test
	public void test_String_Parsing_01() {
		try {
			String text = "Weapon	Short_Sword		1	 2 3 4";
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
	 * Testing whether you can pass multiple strings into the value
	 */
	@org.junit.Test
	public void test_String_Parsing_02() {
		try {
			String text = "Weapon	Short_Sword Pass 1 2";
			Map<String, List<String>> mp = Parser.parse(text);
			String key = "Weapon";
			String value = "Short_Sword Pass 1 2";
			testKey(mp, key);
			List<String> s = mp.get(key);
			testValue(s, value);
		} catch (Exception e) {
			fail("Not supposed to throw exception.");
		}
	}

	/**
	 * Testing whether you can pass through a line with a single input essentially having a key with a null assigned
	 */
	@org.junit.Test
	public void test_String_Parsing_03() {
		try {
			String text = "Weapon	Glue 10 10 10 10 10\nBlue";
			Map<String, List<String>> mp = Parser.parse(text);
			String key = "Weapon";
			String value = "Glue 10 10 10 10 10";
			testKey(mp, key);
			List<String> s = mp.get(key);
			testValue(s, value);
			String key2 = "Blue";
			testKey(mp, key2);
		} catch (Exception e) {
			fail("Not supposed to throw exception.");
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

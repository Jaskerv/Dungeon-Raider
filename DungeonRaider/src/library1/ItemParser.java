package library1;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

/**
 * This class is the only class in the library where you parse in a string in a
 * certain format, where it then returns it in a map in certain categories and
 * splits it accordingly.
 *
 * @author Jono Yan
 *
 */
public class ItemParser {

	private static List<String> listOfCategories = Arrays.asList("Weapon", 
				"Shield", "Consumable", "Monster");

	/**
	 * Parses a text file to generate a map of items
	 *
	 * @param path
	 * @return
	 * @throws ParserException
	 */
	public static Map<String, List<String>> parse(String text)
			throws ParserException, IllegalArgumentException {
		if (text.isEmpty()) {
			throw new ParserException("Input is empty or null.");
		}
		Map<String, List<String>> mc = null;
		Scanner sc = new Scanner(text);
		mc = scan(sc);
		sc.close();
		if (mc != null && !mc.isEmpty()) {
			return mc;
		} else if (mc.isEmpty()) {
			return null;
		} else {
			throw new ParserException(
					"Unexpected Error, did not return correctly.");
		}
	}

	/**
	 * This method does the scanning.
	 *
	 * Format is type of category + itemName + <attributes>
	 * Examples:
	 * 		Weapon Short_Sword 1 2 3 4
	 * 		Consumable Small_Health_Potion 30 0
	 *
	 * @param sc
	 * @return A map of a string to a list of strings, (category to attributes).
	 */
	public static Map<String, List<String>> scan(Scanner sc)
			throws IllegalArgumentException {
		Map<String, List<String>> mc = new HashMap<>();
		String openingBrace = sc.next();
		if (!openingBrace.equals("{")) {
			throw new IllegalArgumentException("No opening brace found (to"
						+ " indicate it is item contents)");
		}
		boolean closingBraceFound = false;
		while (sc.hasNext()) {
			String line = sc.nextLine();
			//reached the end
			if (line.contains("}")) {
				closingBraceFound = true;
				break;
			}
			//splits each element into an array of chars
			String[] split = line.split("\\s");
			String category = null;
			String item = null;
			for (int i = 0; i < split.length; i++) {
				//First element will be the item category type
				if (category == null) {
					category = split[i];
					if (!listOfCategories.contains(category)) {
						throw new IllegalArgumentException();
					}
					continue;
				}
				//Second element is the item name
				if (item == null) {
					item = split[i];
					continue;
				}
				//Adds all of the item attributes separated by spaces
				if (i < split.length) {
					item += " " + split[i];
				}
			}
			//adds item to category
			if (mc.containsKey(category)) {
				mc.get(category).add(item);
			}
			else {
				if (item != null) {
					List<String> newList = new ArrayList<>();
					newList.add(item);
					mc.put(category, newList);
				}
			}
			category = null;
			item = null;
		}
		if (!closingBraceFound) {
			throw new IllegalArgumentException("Closing brace was never found");
		}
		return mc;

	}

	/**
	 * Custom Exception
	 *
	 * @author Jono Yan
	 *
	 */
	protected static class ParserException extends Exception {
		private static final long serialVersionUID = 1L;

		public ParserException(String msg) {
			super(msg);
		}

	}
}

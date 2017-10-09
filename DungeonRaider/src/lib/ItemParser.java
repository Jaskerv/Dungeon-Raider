package lib;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
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

	/**
	 * Parses a text file to generate a map of items
	 *
	 * @param path
	 * @return
	 * @throws ParserException
	 */
	public static Map<String, List<String>> parse(String text)
			throws ParserException {
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
	 * Format is type of <item, item + attributes>
	 *
	 * @param sc
	 * @return
	 */
	private static Map<String, List<String>> scan(Scanner sc) {
		Map<String, List<String>> mc = new HashMap<>();
		while (sc.hasNext()) {
			String line = sc.nextLine();
			String[] split = line.split("\\s");
			String type = null;
			String item = null;
			for (int i = 0; i < split.length; i++) {
				if (type == null) {
					type = split[i];
					continue;
				}
				if (item == null) {
					item = split[i];
					continue;
				}
				if (i < split.length) {
					item += " " + split[i];
				}
			}
			if (mc.containsKey(type)) {
				mc.get(type).add(item);
			} else {
				if (item != null) {
					List<String> newList = new ArrayList<>();
					newList.add(item);
					mc.put(type, newList);
				}
			}
			type = null;
			item = null;
		}
		return mc;

	}

	/**
	 * Custom Exception
	 *
	 * @author Jono Yan
	 *
	 */
	private static class ParserException extends Exception {
		public ParserException(String msg) {
			super(msg);
		}

	}
}

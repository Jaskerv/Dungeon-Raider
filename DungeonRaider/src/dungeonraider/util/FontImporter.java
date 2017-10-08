package dungeonraider.util;

import java.awt.Font;
import java.awt.FontFormatException;
import java.io.File;
import java.io.IOException;

/**
 * This class imports fonts
 * 
 * @author Jono Yan
 *
 */
public class FontImporter {
	public static Font fontImport(String path) {
		Font f = null;
		try {
			f = Font.createFont(Font.TRUETYPE_FONT, new File(path));
		} catch (IOException | FontFormatException e) {
			e.printStackTrace();
		}
		return f;

	}
}

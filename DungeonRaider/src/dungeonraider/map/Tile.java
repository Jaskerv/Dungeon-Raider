package dungeonraider.map;

import java.awt.Image;

/**
 * This represents a tile within the Map.
 * @author harry
 *
 */
public class Tile {
	
	private Image spriteImage;
	private int x, y;
	
	public Tile(Image spriteImage, int x, int y) {
		this.spriteImage = spriteImage;
		this.x = x;
		this.y = y;
	}
	
}

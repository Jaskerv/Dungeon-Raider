package dungeonraider.map;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;

/**
 * This represents a tile within the Map.
 *
 * @author harry
 *
 */
public class Tile {

	private BufferedImage spriteImage;
	private int x, y;
	private final int SIZE = 30;

	public Tile(BufferedImage spriteImage, int x, int y) {
		this.x = x;
		this.y = y;
		this.spriteImage = new BufferedImage(SIZE, SIZE,
				BufferedImage.TYPE_INT_RGB);
		int color = (int) (Math.random() * 0xFFFFFF);
		Graphics g = spriteImage.getGraphics();
		g.setColor(new Color(color));
		g.drawRect(x, y, spriteImage.getWidth(), spriteImage.getHeight());
	}

}

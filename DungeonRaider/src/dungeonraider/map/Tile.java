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
	private final int width;
	private final int height;

	public Tile(int x, int y, int width, int height) {
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		this.spriteImage = new BufferedImage(this.width, this.height,
				BufferedImage.TYPE_INT_RGB);
		int color = (int) (Math.random() * 0xFFFFFF);
		Graphics g = spriteImage.getGraphics();
		g.setColor(new Color(color));
		g.drawRect(x, y, spriteImage.getWidth(), spriteImage.getHeight());
		g.dispose();
	}

	public BufferedImage getSpriteImage() {
		return spriteImage;
	}

	public void setSpriteImage(BufferedImage spriteImage) {
		this.spriteImage = spriteImage;
	}

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

}

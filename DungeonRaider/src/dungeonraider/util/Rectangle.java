package dungeonraider.util;

public class Rectangle {
	private Position pos;
	private int width;
	private int height;

	public Rectangle(int x, int y, int width, int height) {
		this.pos = new Position(x, y);
		this.width = width;
		this.height = height;
	}

	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}

	public Position getPos() {
		return pos;
	}

	/**
	 * This method will check to see if the new x and y being parsed in is
	 * within the boundaries of the hitbox.
	 * @param x
	 * @param y
	 * @return
	 */
	public boolean contains(int x, int y) {
		if 	(pos.getX() <= x && pos.getX() + width >= x &&
			pos.getY() <= y && pos.getY() + height >= y) {
			return true;
		}
		return false;
	}

}

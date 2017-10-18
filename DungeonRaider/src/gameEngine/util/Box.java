package gameEngine.util;

/**
 * This is a general box
 *
 * @author Jono Yan
 *
 */
public class Box {
	protected int x;
	protected int y;
	protected int width;
	protected int height;

	public Box(int x, int y, int width, int height) {
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
	}

	/**
	 * This method will check to see if the new x and y being parsed in is
	 * within the boundaries of the box.
	 *
	 * @param x
	 * @param y
	 * @return
	 */
	public boolean contains(int x, int y) {
		if (this.x <= x && this.x + width >= x && this.y <= y
				&& this.y + height >= y) {
			return true;
		}
		return false;
	}

	public boolean contains(Box box) {
		return this.x < box.x + box.getWidth() && x + width > box.x
				&& y < box.y + box.getHeight() && y + height > box.y;
	}

	/**
	 * @return the x
	 */
	public int getX() {
		return x;
	}

	/**
	 * @param x
	 *            the x to set
	 */
	public void setX(int x) {
		this.x = x;
	}

	/**
	 * @return the y
	 */
	public int getY() {
		return y;
	}

	/**
	 * @param y
	 *            the y to set
	 */
	public void setY(int y) {
		this.y = y;
	}

	/**
	 * @return the width
	 */
	public int getWidth() {
		return width;
	}

	/**
	 * @param width
	 *            the width to set
	 */
	public void setWidth(int width) {
		this.width = width;
	}

	/**
	 * @return the height
	 */
	public int getHeight() {
		return height;
	}

	/**
	 * @param height
	 *            the height to set
	 */
	public void setHeight(int height) {
		this.height = height;
	}

	public Box clone() {
		return new Box(x, y, width, height);
	}

}

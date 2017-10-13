package gameEngine.util;

public class Camera extends Box {
	public Camera(int x, int y, int width, int height) {
		super(x, y, width, height);
	}

	/**
	 * This moves the camera relative to its current position. example of 10,0 will
	 * move the camera 10 px to the right
	 *
	 * @param dx
	 *            +ve dx will move camera right, -ve dx will move camera left
	 * @param dy
	 *            +ve dy will move camera down, -ve dy will move camera up
	 */
	public void moveCamera(int dx, int dy) {
		this.setX(this.x + dx);
		this.setY(this.y + dy);
	}

	/**
	 * Gets center Position of the camera.
	 *
	 * @param width
	 * @param height
	 * @return
	 */
	public Position getCenter() {
		return new Position(this.width / 2, this.height / 2);
	}
}

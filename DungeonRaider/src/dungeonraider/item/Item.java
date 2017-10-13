package dungeonraider.item;

import dungeonraider.engine.Renderer;
import dungeonraider.sprite.Sprite;
import dungeonraider.util.Position;

public abstract class Item {

	protected Position position;
	protected Boolean pickedUp;
	protected int map;
	private Sprite sprite;

	public Item(int x, int y, int map, Sprite sprite) {
		this.position = new Position(x, y);
		pickedUp = false;
		this.map = map;
		this.sprite = sprite;
	}

	public Boolean getPickedUp() {
		return pickedUp;
	}

	public void setPickedUp(Boolean pickedUp) {
		this.pickedUp = pickedUp;
	}

	public Position getPosition() {
		return position;
	}

	public void setPosition(Position position) {
		this.position = position;
	}

	public Position getItemCenter() {
		Position center = new Position(position.getX() + sprite.getHeight()*Renderer.ZOOM/2, position.getY() + sprite.getHeight()*Renderer.ZOOM/2);
		return center;
	}

	public Sprite getSprite() {
		return sprite;
	}

	public void setSprite(Sprite sprite) {
		this.sprite = sprite;
	}

}

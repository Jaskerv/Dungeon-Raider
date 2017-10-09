package dungeonraider.item;

import dungeonraider.util.Position;

public abstract class Item {
	protected Position position;
	protected Boolean pickedUp;
	protected int map;
	
	public Item(int x, int y, int map) {
		this.position = new Position(x, y);
		pickedUp = false; 
		this.map = 0;
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

	

}

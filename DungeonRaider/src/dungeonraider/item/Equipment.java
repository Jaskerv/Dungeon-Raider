package dungeonraider.item;

public abstract class Equipment extends Item {

	public Equipment(int x, int y, int map) {
		super(x, y, map, null);
	}

	public boolean isEqiupment() {
		return true;
	}
}

package dungeonraider.item;

public abstract class Equipment extends Item {

	public Equipment(int x, int y, int map) {
		super(x, y, map);
	}

	public boolean isEqiupment() {
		return true;
	}
}

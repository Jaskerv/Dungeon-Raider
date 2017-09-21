package dungeonraider.item;

public abstract class Equipment extends Item {

	public Equipment(int cost) {
		super(cost);
	}

	public boolean isEqiupment() {
		return true;
	}
}

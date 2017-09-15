package dungeonraider.item;

public abstract class Item {
	private int buyCost;

	public Item(int cost) {
		this.buyCost = cost;
	}

	/**
	 * Get buyCost
	 * @return
	 */
	public int getBuyCost() {
		return buyCost;
	}

}

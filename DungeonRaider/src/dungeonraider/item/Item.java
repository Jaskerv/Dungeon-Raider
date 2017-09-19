package dungeonraider.item;

public abstract class Item {
	protected int buyCost;

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

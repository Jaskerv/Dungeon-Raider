package dungeonraider.item;

public abstract class Consumables extends Item {
	/** Item consume time */
	protected int consumeTime;

	public Consumables(int cost, int consumeTime) {
		super(cost);
		this.consumeTime = consumeTime;
	}

	/**
	 * Return consumeTime
	 *
	 * @return
	 */
	public int getConsumeTime() {
		return consumeTime;
	}

}

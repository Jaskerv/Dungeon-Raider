package dungeonraider.item;

public abstract class Consumables extends Item {
	/** Item consume time */
	protected int consumeTime;

	public Consumables(int x, int y, int consumeTime, int map) {
		super(x, y, map);
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

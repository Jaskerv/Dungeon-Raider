package dungeonraider.item;

/**
 * Potions
 */
public class Consumable extends Item {
	/** Item consume time */
	protected int consumeTime;

	public Consumable(int x, int y, int consumeTime, int map) {
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

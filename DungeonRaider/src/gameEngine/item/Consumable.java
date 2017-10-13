package gameEngine.item;

import gameEngine.sprite.Sprite;

/**
 * Potions
 */
public class Consumable extends Item {

	private String name;
	/** Item consume time */
	protected int consumeTime;

	public Consumable(String name, int x, int y, int consumeTime, int map,
			Sprite sprite) {
		super(x, y, map, sprite);
		this.consumeTime = consumeTime;
		this.name = name;
	}

	/**
	 * Return consumeTime
	 *
	 * @return
	 */
	public int getConsumeTime() {
		return consumeTime;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}

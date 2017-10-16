package gameEngine.item;

import gameEngine.sprite.Sprite;

/**
 * Potions
 */
public class Consumable extends Item {

	private String name;
	/** Item consume health gained */
	protected int healingStrength;

	public Consumable(String name, int x, int y, int healingStrength,
			Sprite sprite) {
		super(x, y, sprite);
		this.healingStrength = healingStrength;
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public int getHealingStrength() {
		return healingStrength;
	}

	public void setHealingStrength(int healingStrength) {
		this.healingStrength = healingStrength;
	}

	public void setName(String name) {
		this.name = name;
	}

}

package gameEngine.item;

import java.awt.image.BufferedImage;

import gameEngine.engine.Engine;
import gameEngine.sprite.Sprite;

/**
 * Potions
 */
public class Consumable extends Item {

	private String name;
	private BufferedImage image;
	private String path;
	/** Item consume health gained */
	protected int healingStrength;

	private Consumable(int healing) {
		this("", 0, 0, healing, Engine.loadImage("resources/images/potion.png"),
				"");
	}

	public Consumable(String name, int x, int y, int healingStrength,
			BufferedImage image, String path) {
		super(x, y, new Sprite(image));
		this.image = image;
		this.path = path;
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

	public static Consumable createConsumable(int healValue) {
		return new Consumable(healValue);
	}
}

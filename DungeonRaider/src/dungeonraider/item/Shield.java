package dungeonraider.item;

import java.awt.Window.Type;

public class Shield extends Equipment implements Upgradable {


	private String name;
	private int cost;
	private int armour;
	private int damageReflectChance;
	private int numberOfUpgrades = 0;

	public Shield(String name, int cost, int armour,
			int damageReflectChance) {
		super(cost);

		this.name = name;
		this.cost = cost;
		this.armour = armour;
		this.damageReflectChance = damageReflectChance;

	}

	/**
	 * The cost of upgrading is exponential while the upgrade benefit is linear
	 */
	@Override
	public void upgrade() {
	//	defence += upgradeModifier * defence / 2;
	}



	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getCost() {
		return cost;
	}

	public void setCost(int cost) {
		this.cost = cost;
	}

	public int getArmour() {
		return armour;
	}

	public void setArmour(int armour) {
		this.armour = armour;
	}

	public int getDamageReflectChance() {
		return damageReflectChance;
	}

	public void setDamageReflectChance(int damageReflectChance) {
		this.damageReflectChance = damageReflectChance;
	}

	public int getNumberOfUpgrades() {
		return numberOfUpgrades;
	}

	public void setNumberOfUpgrades(int numberOfUpgrades) {
		this.numberOfUpgrades = numberOfUpgrades;
	}




}

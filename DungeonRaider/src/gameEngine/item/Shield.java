package gameEngine.item;

public class Shield extends Equipment implements Upgradable {
	private String name;
	private int armour;
	private int damageReflectChance;
	private int numberOfUpgrades = 0;

	public Shield(String name, int x, int y, int armour, int map, int damageReflectChance) {
		super(x, y, map);
		this.name = name;
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
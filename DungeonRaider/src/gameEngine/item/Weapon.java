package gameEngine.item;

public class Weapon extends Equipment implements Upgradable {


	private String name;
	private int cost;
	private int damage;
	private double critChance;
	private int range;
	private int numberOfUpgrades = 0;

	/**
	 * @param type weapon or shield
	 * @param name
	 * @param cost weapons or shields dropped from monsters cost nothing
	 * @param damage
	 * @param critChance in percentage
	 *
	 */
	public Weapon(String name, int x, int y, int map, int damage, int range, int critChance) {
		super(x, y, map);
		this.name = name;
		this.cost = cost;
		this.damage = damage;
		this.critChance = critChance;
		this.range = range;

	}

	/**
	 * The cost of upgrading is exponential while the upgrade benefit is linear
	 */
	@Override
	public void upgrade() {
		//damage += upgradeModifier * damage / 2;
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

	public int getDamage() {
		return damage;
	}

	public void setDamage(int damage) {
		this.damage = damage;
	}

	public double getCritChance() {
		return critChance;
	}

	public void setCritChance(double critChance) {
		this.critChance = critChance;
	}

	public int getNumberOfUpgrades() {
		return numberOfUpgrades;
	}

	public void setNumberOfUpgrades(int numberOfUpgrades) {
		this.numberOfUpgrades = numberOfUpgrades;
	}

	public int getRange() {
		return range;
	}

	public void setRange(int range) {
		this.range = range;
	}

}
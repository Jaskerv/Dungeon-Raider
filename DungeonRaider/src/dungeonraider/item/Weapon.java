package dungeonraider.item;

public class Weapon extends Equipment implements Upgradable {

	private int damage;
	private double critChance;
	private int range;

	/**
	 *
	 * @param cost
	 * @param damage
	 * @param critChance
	 * @param range
	 */
	public Weapon(int cost, int damage, int critChance, int range) {
		super(cost);
		this.damage = damage;
		this.critChance = critChance;
		this.range = range;
	}

	/**
	 * The cost of upgrading is exponential while the upgrade benefit is linear
	 */
	@Override
	public void upgrade() {

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

	public int getRange() {
		return range;
	}

	public void setRange(int range) {
		this.range = range;
	}



}
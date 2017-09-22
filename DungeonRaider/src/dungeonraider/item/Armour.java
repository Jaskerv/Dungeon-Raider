package dungeonraider.item;

public class Armour extends Equipment implements Upgradable {
	private int defence;
	/** Modifier for upgrade */
	private int upgradeModifier;

	public Armour(int cost, int defence, int modifier) {
		super(cost);
		this.defence = defence;
		this.upgradeModifier = modifier;
	}

	/**
	 * The cost of upgrading is exponential while the upgrade benefit is linear
	 */
	@Override
	public void upgrade() {
		defence += upgradeModifier * defence / 2;
	}

}

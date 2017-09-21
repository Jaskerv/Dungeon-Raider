package dungeonraider.item;

public class Weapon extends Equipment implements Upgradable {

	private int damage;
	private double critChance;

	public Weapon(int cost, int damage, int critChance) {
		super(cost);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void upgrade() {

	}

}
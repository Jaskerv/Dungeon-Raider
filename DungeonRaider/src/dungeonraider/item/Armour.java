package dungeonraider.item;

public class Armour extends Equipment implements Upgradable {
	private int defence;
	private int modifier;

	public Armour(int cost, int defence, int modifier) {
		super(cost);
		this.defence = defence;
	}

	@Override
	public void upgrade() {
		defence += modifier * defence / 2;
	}

}

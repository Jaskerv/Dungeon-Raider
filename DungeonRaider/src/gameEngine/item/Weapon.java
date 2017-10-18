package gameEngine.item;

import java.util.PrimitiveIterator.OfDouble;
import java.util.concurrent.atomic.AtomicBoolean;

import javax.swing.DefaultDesktopManager;
import javax.xml.ws.AsyncHandler;

import gameEngine.sprite.Sprite;
import library4.Saveable;

public class Weapon extends Item implements Upgradable, Saveable {

	private String name;
	private int damage;
	private double critChance;
	private int range;
	private int numberOfUpgrades = 0;

	/**
	 * @param type
	 *            weapon or shield
	 * @param name
	 * @param cost
	 *            weapons or shields dropped from monsters cost nothing
	 * @param damage
	 * @param critChance
	 *            in percentage
	 *
	 */
	public Weapon(String name, int x, int y, int damage, int range, int critChance, Sprite sprite) {
		super(x, y, sprite);
		this.name = name;
		this.damage = damage;
		this.critChance = critChance;
		this.range = range;

	}

	/**
	 * Upgrade weapon by increasing its damage and crit chance. Number of upgrades
	 * capped at 5.
	 */
	@Override
	public void upgrade() {

		if (numberOfUpgrades < 5) {
			setCritChance(critChance + 5);
			setDamage(damage + 10);
			numberOfUpgrades++;
		}
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
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

	@Override
	public String save() {
		String s = "Weapon\n";
		s += "String	name	" + name + "\n";
		s += "int	damage	" + damage + "\n";
		s += "double	critChance	"+ critChance + "\n";
		s += "int	range	"+ range + "\n";
		s += "int	numberOfUpgrades	"+ numberOfUpgrades + "\n";
		return s;
	}

	@Override
	public void load() {

	}

}
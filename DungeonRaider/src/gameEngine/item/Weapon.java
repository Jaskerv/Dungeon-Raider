package gameEngine.item;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.PrimitiveIterator.OfDouble;
import java.util.Scanner;
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
	public Weapon(String name, int x, int y, int damage, int range,
			int critChance, Sprite sprite) {
		super(x, y, sprite);
		this.name = name;
		this.damage = damage;
		this.critChance = critChance;
		this.range = range;

	}

	public Weapon(int x, int y, Sprite sprite, String name, int damage,
			double critChance, int range, int numberOfUpgrades) {
		super(x, y, sprite);
		this.name = name;
		this.damage = damage;
		this.critChance = critChance;
		this.range = range;
		this.numberOfUpgrades = numberOfUpgrades;
	}



	/**
	 * Upgrade weapon by increasing its damage and crit chance. Number of
	 * upgrades capped at 5.
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
		String s = "Weapon\t{\n";
		s += "String\tname\t" + name + "\n";
		s += "int\tdamage\t" + damage + "\n";
		s += "double\tcritChance\t" + critChance + "\n";
		s += "int\trange\t" + range + "\n";
		s += "int\tnumberOfUpgrades\t" + numberOfUpgrades + "\n";
		s += "}\n";
		return s;
	}

	@Override
	public void load(File file) {

	}

}
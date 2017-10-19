package gameEngine.character;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import gameEngine.item.Consumable;
import gameEngine.item.Item;
import library4.Saveable;

/**
 * Inventory for any object that can contain multiple items
 *
 * @author Jono Yan
 *
 */
public class Inventory implements Saveable {
	private List<Item> inventory;

	public Inventory(int currentCapacity) {
		this.inventory = new ArrayList<>();
	}

	public void add(Item e) {
		this.inventory.add(e);
	}

	public void remove(Item e) {
		this.inventory.remove(e);
	}

	public boolean contains(Item e) {
		if (inventory.contains(e)) {
			return true;
		}
		return false;
	}

	public Item returnFirstItem() {
		for (Item item : inventory) {
			inventory.remove(item);
			return item;
		}
		return null;
	}

	public List<Item> getInventory() {
		return inventory;
	}

	public void setInventory(List<Item> inventory) {
		this.inventory = inventory;
	}

	@Override
	public String save() {
		String s = "";
		for (Item item : inventory) {
			if (item instanceof Consumable) {
				Consumable i = (Consumable) item;
				s += i.getHealingStrength() + "\t";
			}
		}
		return s;
	}

	@Override
	public void load(File file) {
		// TODO Auto-generated method stub

	}
}

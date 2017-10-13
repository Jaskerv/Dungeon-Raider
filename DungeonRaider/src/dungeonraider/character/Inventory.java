package dungeonraider.character;

import java.util.ArrayList;
import java.util.List;

import dungeonraider.item.Item;

/**
 * Inventory for any object that can contain multiple items
 *
 * @author Jono Yan
 *
 */
public class Inventory {
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

	public void removeItem() {
		for(Item item : inventory) {
			inventory.remove(item);
			break;
		}
	}

	public List<Item> getInventory() {
		return inventory;
	}

	public void setInventory(List<Item> inventory) {
		this.inventory = inventory;
	}
}

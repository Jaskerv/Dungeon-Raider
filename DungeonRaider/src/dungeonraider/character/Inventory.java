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
	private int currentCapacity;

	public Inventory(int currentCapacity) {
		this.inventory = new ArrayList<>();
		this.currentCapacity = currentCapacity;
	}

	public void add(Item e) {
		if (e != null && currentCapacity < 20) {
			this.inventory.add(e);
			this.currentCapacity++;
		}
	}

	public void remove(Item e) {
		if (e != null) {
			this.inventory.remove(e);
			this.currentCapacity--;
		}
	}

	public boolean contains(Item e) {
		if (inventory.contains(e)) {
			return true;
		}
		return false;
	}
}

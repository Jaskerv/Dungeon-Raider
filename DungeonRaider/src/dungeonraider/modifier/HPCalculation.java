package dungeonraider.modifier;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import org.junit.Test;

import junit.framework.AssertionFailedError;

/**
 *
 * Calculates change in health points with consideration in armour.
 *
 * @author Gabriel Tennent
 *
 */
public class HPCalculation {

	/**
	 * Calculates the players current HP after taking damage
	 * @param damageDone the damage the player is taking
	 * @param currentHP the players HP before taking damage
	 * @return the current HP after taking damage
	 */
	public int calculateDamage(int damageDone, int currentHP) {
		int minHP = 0;
		currentHP = currentHP - damageDone;
		if(minHP > currentHP) return minHP;
		else return currentHP;
	}

	/**
	 * Calculate the players current HP after taking healing
	 * @param healingDone the healing the player is taking
	 * @param currentHP the players HP before taking the healing
	 * @param maxHP the players maximum HP that shouldn't be healed past
	 * @return returns the players new HP after healing
	 */
	public int calculateHealing(int healingDone, int currentHP, int maxHP) {
		if(currentHP == 0) return currentHP;
		currentHP = currentHP + healingDone;
		if(currentHP > maxHP) return maxHP;
		else return currentHP;
	}

	/**
	 * Calculates the players current HP after taking two hits of damage
	 * @param firstDamage the first amount of damage done
	 * @param secondDamage the second amount of damage done
	 * @param currentHP is the players current HP
	 * @return the current HP of player after taking two hits of damage
	 */
	public int calculateDoubleHit(int firstDamage, int secondDamage, int currentHP) {
		int minHP = 0;
		currentHP = currentHP - firstDamage - secondDamage;
		if(minHP > currentHP) return minHP;
		else return currentHP;
	}

	/**
	 * Tests taking damage under a normal circumstance
	 */
	@Test
	public void test_01() {
		int result = calculateDamage(10, 100);
		assertEquals("The remaining HP should be 90 if the current HP is 100 and 10 damage is taken", 90, result);
	}

	/**
	 * Tests taking fatal damage
	 */
	@Test
	public void test_02() {
		int result = calculateDamage(50, 10);
		assertEquals("Upon taking damage that should result in a negative number the remaining HP should be 0", 0, result);
	}

	/**
	 * Tests taking healing under normal circumstance
	 */
	@Test
	public void test_03() {
		int result = calculateHealing(10, 50, 100);
		assertEquals("Upon taking healing a players HP should increase by the amount healed", 60, result);
	}

	/**
	 * Tests taking healing once the player is already at max HP
	 */
	@Test
	public void test_04() {
		int result = calculateHealing(10, 100, 100);
		assertEquals("Upon taking healing a players HP should increase by not past the players Maximum HP", 100, result);
	}

	/**
	 * Tests healing a dead player
	 */
	@Test
	public void test_05() {
		int result = calculateHealing(10, 0, 100);
		boolean stillDead = true;
		if(result > 0) stillDead = false;
		assertTrue("A dead player should not be able to be healed", stillDead);
	}
}

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
public class StatModifier {

	//public static final double sprintModifier = 0.5;
	public static final double armourModifier = 0.5;
	public static final double critModifier = 100;
	public static final double critMultiplier = 2;
	public static final double minDamageDone = 1;

	/**
	 * Calculates the damage being taken dependent on the stats given
	 * @param damageDone damage being done
	 * @param currentHP current hp of the target
	 * @param armourStat armour stat of the target
	 * @param critChanceStat critChance passed through of the character
	 * @return returns the new hp to be given to the target being hit
	 */
	public static double calcDamage(double damageDone, double currentHP, double armourStat, double critChanceStat) {
		double damage = 0;
		double minHP = 0;
		boolean crited = calcCrit(critChanceStat);

		damage = calcArmorReduc(damageDone, armourStat);
		if(crited) damage = damage * critMultiplier;
		currentHP = currentHP - damage;
		if(minHP > currentHP) return minHP;
		else return currentHP;
	}

	/**
	 * Calculates if the hit is a critical hit
	 * @param critChanceStat the chance of the player criting
	 * @return whether the hit is citical or not
	 */
	public static boolean calcCrit(double critChanceStat) {
		double random = Math.random();
		double critChance = critChanceStat / critModifier;
		return random < critChance;
	}

	/**
	 * Calculates the damage being done taking into account the players armour
	 * @param damageDone the damage the player is taking
	 * @param armourStat the players armour stat
	 * @return returns the damage being taken by the player when taking into account the armour
	 */
	public static double calcArmorReduc(double damageDone, double armourStat) {
		double reduction = armourStat * armourModifier;
		double finalDamage = damageDone - reduction;
		if(finalDamage < 1) return minDamageDone;
		else return finalDamage;
	}

	//To be implemented
	/*public static double calcSprint() {
	}*/

	/**
	 * Calculate the players current HP after taking healing
	 * @param healingDone the healing the player is taking
	 * @param currentHP the players HP before taking the healing
	 * @param maxHP the players maximum HP that shouldn't be healed past
	 * @return returns the players new HP after healing
	 */
	public static double calculateHealing(double healingDone, double currentHP, double maxHP) {
		if(currentHP == 0) return currentHP;
		currentHP = currentHP + healingDone;
		if(currentHP > maxHP) return maxHP;
		else return currentHP;
	}

	/**
	 * Tests taking damage under a 100% crit chance circumstance
	 */
	@Test
	public void test_01() {
		double result = calcDamage(10, 100, 10, 100);
		assertTrue("Upon taking damage with 100% crit chance the damage should always crit", result == (90));
	}

	/**
	 * Tests taking damage under a 0% crit chance circumstance
	 */
	@Test
	public void test_02() {
		double result = calcDamage(10, 100, 10, 0);
		assertTrue("Upon taking damage with 100% crit chance the damage should always crit", result == (95));
	}

	/**
	 * Tests taking damage under a 0% crit chance circumstance and 0 armour
	 */
	@Test
	public void test_03() {
		double result = calcDamage(10, 100, 0, 0);
		assertTrue("Upon taking damage with no amrour and no crit chance the damage number is dealth unmodified", result == (90));
	}

	/**
	 * Tests taking fatal damage
	 *//*
	@Test
	public void test_04() {
		double result = calcDamage(90, 100, 0, 0);
		assertEquals("Upon taking fatal damage the amount of HP returned is 0 and not negative", result == (10));
	}*/

	/*	*//**
	 * Tests taking healing under normal circumstance
	 *//*
	@Test
	public void test_03() {
		double result = calculateHealing(10, 50, 100);
		assertEquals("Upon taking healing a players HP should increase by the amount healed", 60, result);
	}

	*//**
	 * Tests taking healing once the player is already at max HP
	 *//*
	@Test
	public void test_04() {
		double result = calculateHealing(10, 100, 100);
		assertEquals("Upon taking healing a players HP should increase by not past the players Maximum HP", 100, result);
	}

	*//**
	 * Tests healing a dead player
	 *//*
	@Test
	public void test_05() {
		double result = calculateHealing(10, 0, 100);
		boolean stillDead = true;
		if(result > 0) stillDead = false;
		assertTrue("A dead player should not be able to be healed", stillDead);
	}*/
}

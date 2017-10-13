package library5;

import static org.junit.Assert.*;

import org.junit.Test;

public class ExternalTestStat {
	/**
	 * Tests if damage is outputting the correct output
	 */
	@Test
	public void test01() {
		double damageDone = 10;
		double currentHP = 300;
		double armourStat = 0;
		double critChanceStat = 100;
		double s = StatModifier.calcDamage(damageDone, currentHP, armourStat, critChanceStat);
		if (s != (currentHP - StatModifier.critMultiplier * damageDone)) {
			fail("Damage not calculated properly");
		}
	}

	/**
	 * See if negative numbers are accounted for
	 */
	@Test
	public void test02() {
		double damageDone = 10;
		double currentHP = -1;
		double armourStat = 0;
		double critChanceStat = 100;
		double s = StatModifier.calcDamage(damageDone, currentHP, armourStat, critChanceStat);
		if (s < 0) {
			fail("Cannot have negative hp");
		}
	}

	/**
	 * See if negative numbers are accounted for
	 */
	@Test
	public void test03() {
		double damageDone = -10;
		double currentHP = 100;
		double armourStat = 0;
		double critChanceStat = 100;
		double s = StatModifier.calcDamage(damageDone, currentHP, armourStat, critChanceStat);
		if (s != 100) {
			fail("Hp is incorrect: " + s);
		}
	}

	/**
	 * Tests if negative crit chance is a thing
	 */
	@Test
	public void test04() {
		double damageDone = 10;
		double currentHP = 100;
		double armourStat = 0;
		double critChanceStat = -100;
		double s = StatModifier.calcDamage(damageDone, currentHP, armourStat, critChanceStat);
		if (s != 90) {
			fail("Hp is incorrect: " + s);
		}
	}

	/**
	 * Tests if negative armour is a thing
	 */
	@Test
	public void test05() {
		double damageDone = 10;
		double currentHP = 100;
		double armourStat = -10;
		double critChanceStat = 0;
		double s = StatModifier.calcDamage(damageDone, currentHP, armourStat, critChanceStat);
		if (s == 90) {
			fail("Hp is incorrect: " + s);
		}
	}

	/**
	 * Tests healing
	 */
	@Test
	public void test06() {
		double healingDone = 20;
		double currentHP = 100;
		double maxHP = 200;
		double s = StatModifier.calculateHealing(healingDone, currentHP, maxHP);
		if (s != 120) {
			fail("Hp is incorrect: " + s);
		}
	}

	/**
	 * Tests max hp
	 */
	@Test
	public void test07() {
		double healingDone = 20;
		double currentHP = 199;
		double maxHP = 200;
		double s = StatModifier.calculateHealing(healingDone, currentHP, maxHP);
		if (s != maxHP) {
			fail("Hp is incorrect: " + s);
		}
	}

	/**
	 * Tests if negative health works
	 */
	@Test
	public void test08() {
		double healingDone = 20;
		double currentHP = -1;
		double maxHP = 200;
		double s = StatModifier.calculateHealing(healingDone, currentHP, maxHP);
		if (s != -1 || s != 0) {
			fail("Hp is incorrect: " + s);
		}
	}

	/**
	 * Tests if health 0
	 */
	@Test
	public void test09() {
		double healingDone = 20;
		double currentHP = 0;
		double maxHP = 200;
		double s = StatModifier.calculateHealing(healingDone, currentHP, maxHP);
		if (s != 0) {
			fail("Hp is incorrect: " + s);
		}
	}

	/**
	 * Tests if healthing is negative
	 */
	@Test
	public void test10() {
		double healingDone = -20;
		double currentHP = 100;
		double maxHP = 200;
		double s = StatModifier.calculateHealing(healingDone, currentHP, maxHP);
		if (s != 100) {
			fail("Hp is incorrect: " + s);
		}
	}

}

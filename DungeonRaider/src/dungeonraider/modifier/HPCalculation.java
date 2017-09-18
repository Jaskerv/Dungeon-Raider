package dungeonraider.modifier;


/**
 *
 * Calculates change in health points with consideration in armour.
 *
 * @author Gabriel Tennent
 *
 */
public class HPCalculation {

	public int calculateDamage(int damageDone, int currentHP) {
		int minHP = 0;
		int curHP = currentHP - damageDone;
		if(minHP > curHP) return minHP;
		else return curHP;
	}
	
	public int calculateHealing(int healingDone, int currentHP, int maxHP) {
		int curHP = currentHP + healingDone;
		if(curHP > maxHP) return maxHP;
		else return curHP;
	} 
	
	public int calculateDoubleHit(int firstDamage, int secondDamage, int currentHP) {
		int minHP = 0;
		int curHP = currentHP - firstDamage - secondDamage;
		if(minHP > curHP) return minHP;
		else return curHP;
	}
}

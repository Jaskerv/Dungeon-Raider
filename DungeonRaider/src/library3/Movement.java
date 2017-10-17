
package library3;

public class Movement {

	public static final int WALK_SPEED = 2;
	public static final int SPRINT_SPEED = 7;


	/*
	 * Methods for walking
	 */
	
	public static int walkUp(int currentY) {
		return currentY - Movement.WALK_SPEED;
	}
	
	public static int walkRight(int currentX) {
		return currentX + Movement.WALK_SPEED;
	}
	
	public static int walkDown(int currentY) {
		return currentY + Movement.WALK_SPEED;
	}
	
	public static int walkLeft(int currentX) {
		return currentX - Movement.WALK_SPEED;
	}
	
	
	
	/*
	 * Methods for sprinting 
	 */
	
	public static int sprintUp(int currentY) {
		return currentY - Movement.SPRINT_SPEED;
	}
	
	public static int sprintRight(int currentX) {
		return currentX + Movement.SPRINT_SPEED;
	}
	
	public static int sprintDown(int currentY) {
		return currentY + Movement.SPRINT_SPEED;
	}
	
	public static int sprintLeft(int currentX) {
		return currentX - Movement.SPRINT_SPEED;
	}
	
	



}
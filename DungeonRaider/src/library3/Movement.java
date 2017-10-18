
package library3;

public class Movement {

	public static final int WALK_SPEED = 2;
	public static final int SPRINT_SPEED = 7;


	/*
	 * Methods for walking
	 */

	public static int walkUp(int currentY,int walkSpeed) {
		return currentY - walkSpeed;
	}

	public static int walkRight(int currentX,int walkSpeed) {
		return currentX + walkSpeed;
	}

	public static int walkDown(int currentY,int walkSpeed) {
		return currentY + walkSpeed;
	}

	public static int walkLeft(int currentX,int walkSpeed) {
		return currentX - walkSpeed;
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
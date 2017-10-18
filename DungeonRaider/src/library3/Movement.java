
package library3;

public class Movement {

	public static final int WALK_SPEED = 2;
	public static final int SPRINT_SPEED = 7;


	/*
	 * Methods for walking
	 */

	public static int walkUp(int currentY) {
		if(currentY <= WALK_SPEED)
			return 0;

		return currentY - WALK_SPEED;
	}

	public static int walkRight(int currentX) {
		return currentX + WALK_SPEED;
	}

	public static int walkDown(int currentY) {
		return currentY + WALK_SPEED;
	}

	public static int walkLeft(int currentX) {
		if(currentX <= WALK_SPEED)
			return 0;
		return currentX - WALK_SPEED;
	}



	/*
	 * Methods for sprinting
	 */

	public static int sprintUp(int currentY) {
		if(currentY <= SPRINT_SPEED)
			return 0;
		return currentY - SPRINT_SPEED;
	}

	public static int sprintRight(int currentX) {
		return currentX + SPRINT_SPEED;
	}

	public static int sprintDown(int currentY) {
		return currentY + SPRINT_SPEED;
	}

	public static int sprintLeft(int currentX) {
		if(currentX <= SPRINT_SPEED)
			return 0;
		return currentX - SPRINT_SPEED;
	}





}
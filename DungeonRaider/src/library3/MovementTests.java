package library3;

import static org.junit.Assert.*;

import org.junit.Test;

public class MovementTests {

	@Test
	public void testWalkLeft_1() throws Exception {
		// walk left works properly
		try {
			int currentX = 10;
			int speed = 2;
			int newX = Movement.walkLeft(currentX, speed);
			assertTrue(newX == 8);

		} catch (IllegalArgumentException | IndexOutOfBoundsException e) {
			e.printStackTrace();
		}

	}

	@Test
	public void testWalkLeft_2() throws Exception {
		// walk left x is 2
		try {
			int currentX = 2;
			int speed = 2;
			int newX = Movement.walkLeft(currentX, speed);
			assertTrue(newX == 0);

		} catch (IllegalArgumentException | IndexOutOfBoundsException e) {
			e.printStackTrace();
		}
	}

	@Test
	public void testWalkLeft_3() throws Exception {
		// walk left works x is 0
		try {
			int currentX = 0;
			int speed = 2;
			int newX = Movement.walkLeft(currentX, speed);
			assertTrue(newX == 0);

		} catch (IllegalArgumentException | IndexOutOfBoundsException e) {
			e.printStackTrace();
		}
	}

	@Test
	public void testWalkLeft_4() throws Exception {
		// walk left works x is 0
		try {
			int currentX = 0;
			int speed = 5;
			int newX = Movement.walkLeft(currentX, speed);
			assertTrue(newX == 0);

		} catch (IllegalArgumentException | IndexOutOfBoundsException e) {
			e.printStackTrace();
		}
	}

	@Test
	public void testWalkLeft_5() throws Exception {
		// walk left works x is 0
		try {
			int currentX = 6;
			int speed = 5;
			int newX = Movement.walkLeft(currentX, speed);
			assertTrue(newX == 1);

		} catch (IllegalArgumentException | IndexOutOfBoundsException e) {
			e.printStackTrace();
		}
	}

	////////////////////////////////////////////////////////////////////////

	@Test
	public void testWalkUp_1() throws Exception {
		// walk left works properly
		try {
			int currentY = 10;
			int speed = 2;
			int newY = Movement.walkUp(currentY, speed);
			assertTrue(newY == 8);

		} catch (IllegalArgumentException | IndexOutOfBoundsException e) {
			e.printStackTrace();
		}
	}

	@Test
	public void testWalkUp_2() throws Exception {
		// walk left works y is 2
		try {
			int currentY = 2;
			int speed = 2;
			int newY = Movement.walkUp(currentY, speed);
			assertTrue(newY == 0);

		} catch (IllegalArgumentException | IndexOutOfBoundsException e) {
			e.printStackTrace();
		}
	}

	@Test
	public void testWalkUp_3() throws Exception {
		// walk left works y is 0
		try {
			int currentY = 0;
			int speed = 2;
			int newY = Movement.walkUp(currentY, speed);
			assertTrue(newY == 0);

		} catch (IllegalArgumentException | IndexOutOfBoundsException e) {
			e.printStackTrace();
		}
	}

	@Test
	public void testWalkUp_4() throws Exception {
		// walk left works y is 0
		try {
			int currentY = 0;
			int speed = 5;
			int newY = Movement.walkUp(currentY, speed);
			assertTrue(newY == 0);

		} catch (IllegalArgumentException | IndexOutOfBoundsException e) {
			e.printStackTrace();
		}
	}

	@Test
	public void testWalkUp_5() throws Exception {
		// walk left works y is 0
		try {
			int currentY = 6;
			int speed = 5;
			int newY = Movement.walkUp(currentY, speed);
			assertTrue(newY == 1);

		} catch (IllegalArgumentException | IndexOutOfBoundsException e) {
			e.printStackTrace();
		}
	}

	///////////////////////////////////////////////////////////////////////////

	@Test
	public void testWalkRight_1() throws Exception {
		// width of JFrame is 1280 but we dont know how big the map will be yet
		try {
			int currentX = 0;
			int speed = 2;
			int newX = Movement.walkRight(currentX, speed);
			assertTrue(newX == 2);

		} catch (IllegalArgumentException | IndexOutOfBoundsException e) {
			e.printStackTrace();
		}
	}

	@Test
	public void testWalkRight_2() throws Exception {
		// width of JFrame is 1280 but we dont know how big the map will be yet
		try {
			int currentX = 10;
			int speed = 2;
			int newX = Movement.walkRight(currentX, speed);
			assertTrue(newX == 12);

		} catch (IllegalArgumentException | IndexOutOfBoundsException e) {
			e.printStackTrace();
		}
	}

	@Test
	public void testWalkRight_3() throws Exception {
		// width of JFrame is 1280 but we dont know how big the map will be yet
		try {
			int currentX = 10;
			int speed = 5;
			int newX = Movement.walkRight(currentX, speed);
			assertTrue(newX == 15);

		} catch (IllegalArgumentException | IndexOutOfBoundsException e) {
			e.printStackTrace();
		}
	}

	//////////////////////////////////////////////////////////////////////////////

	@Test
	public void testWalkDown_1() throws Exception {
		// height of JFrame is 720 but we dont know how big the map will be yet
		try {
			int currentY = 10;
			int speed = 2;
			int newY = Movement.walkDown(currentY, speed);
			assertTrue(newY == 12);

		} catch (IllegalArgumentException | IndexOutOfBoundsException e) {
			e.printStackTrace();
		}
	}

	@Test
	public void testWalkDown_2() throws Exception {
		// height of JFrame is 720 but we dont know how big the map will be yet
		try {
			int currentY = 0;
			int speed = 2;
			int newY = Movement.walkDown(currentY, speed);
			assertTrue(newY == 2);

		} catch (IllegalArgumentException | IndexOutOfBoundsException e) {
			e.printStackTrace();
		}
	}

	@Test
	public void testWalkDown_3() throws Exception {
		// height of JFrame is 720 but we dont know how big the map will be yet
		try {
			int currentY = 0;
			int speed = 5;
			int newY = Movement.walkDown(currentY, speed);
			assertTrue(newY == 5);

		} catch (IllegalArgumentException | IndexOutOfBoundsException e) {
			e.printStackTrace();
		}
	}

	@Test
	public void testWalkDown_4() throws Exception {
		// height of JFrame is 720 but we dont know how big the map will be yet
		try {
			int currentY = 10;
			int speed = 5;
			int newY = Movement.walkDown(currentY, speed);
			assertTrue(newY == 15);

		} catch (IllegalArgumentException | IndexOutOfBoundsException e) {
			e.printStackTrace();
		}
	}

	////////////////////////////////////////////////////////////////////////////

	@Test
	public void testSprintUp_1() throws Exception {
		// test sprint up should work when y = 0. It just doesnt move
		try {
			int currentY = 0;
			int newY = Movement.sprintUp(currentY);
			assertTrue(newY == 0);

		} catch (IllegalArgumentException | IndexOutOfBoundsException e) {
			e.printStackTrace();
		}
	}

	@Test
	public void testSprintUp_2() throws Exception {
		// test sprint up should work when y = 3. It should stop at 0
		try {
			int currentY = 3;
			int newY = Movement.sprintUp(currentY);
			assertTrue(newY == 0);

		} catch (IllegalArgumentException | IndexOutOfBoundsException e) {
			e.printStackTrace();
		}
	}

	@Test
	public void testSprintUp_3() throws Exception {
		// test sprint up should work when y = 10.
		try {
			int currentY = 10;
			int newY = Movement.sprintUp(currentY);
			assertTrue(newY == 3);

		} catch (IllegalArgumentException | IndexOutOfBoundsException e) {
			e.printStackTrace();
		}
	}

	///////////////////////////////////////////////////////////////////////////

	@Test
	public void testSprintLeft_1() throws Exception {
		// test sprint left should work when x = 0. It just doesnt move
		try {
			int currentX = 0;
			int newX = Movement.sprintLeft(currentX);
			assertTrue(newX == 0);

		} catch (IllegalArgumentException | IndexOutOfBoundsException e) {
			e.printStackTrace();
		}
	}

	@Test
	public void testSprintLeft_2() throws Exception {
		// test sprint left should work when x = 3. It moves to x = 0
		try {
			int currentX = 3;
			int newX = Movement.sprintLeft(currentX);
			assertTrue(newX == 0);

		} catch (IllegalArgumentException | IndexOutOfBoundsException e) {
			e.printStackTrace();
		}
	}

	@Test
	public void testSprintLeft_3() throws Exception {
		// test sprint left should work when x = 10.
		try {
			int currentX = 10;
			int newX = Movement.sprintLeft(currentX);
			assertTrue(newX == 3);

		} catch (IllegalArgumentException | IndexOutOfBoundsException e) {
			e.printStackTrace();
		}
	}

	/////////////////////////////////////////////////////////////////////////////

	@Test
	public void testSprintRight_1() throws Exception {
		// test sprint right should work when x = 0.
		try {
			int currentX = 0;
			int newX = Movement.sprintRight(currentX);
			assertTrue(newX == 7);

		} catch (IllegalArgumentException | IndexOutOfBoundsException e) {
			e.printStackTrace();
		}
	}

	@Test
	public void testSprintRight_2() throws Exception {
		// test sprint right should also work when x = 10.
		try {
			int currentX = 10;
			int newX = Movement.sprintRight(currentX);
			assertTrue(newX == 17);

		} catch (IllegalArgumentException | IndexOutOfBoundsException e) {
			e.printStackTrace();
		}
	}

	////////////////////////////////////////////////////////////////////////////

	@Test
	public void testSprintDown_1() throws Exception {
		// test sprint down should work when y = 0.
		try {
			int currentY = 0;
			int newY = Movement.sprintRight(currentY);
			assertTrue(newY == 7);

		} catch (IllegalArgumentException | IndexOutOfBoundsException e) {
			e.printStackTrace();
		}
	}

	@Test
	public void testSprintDown_2() throws Exception {
		// test sprint down should work when y = 10.
		try {
			int currentY = 10;
			int newY = Movement.sprintRight(currentY);
			assertTrue(newY == 17);

		} catch (IllegalArgumentException | IndexOutOfBoundsException e) {
			e.printStackTrace();
		}
	}

}

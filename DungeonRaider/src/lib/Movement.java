package lib;


public class Movement {


	public static int moveLeft(int currentX) {
		if(currentX <= 3) {
			return 0;
		}
		return currentX - 3;
		
	}

	public static int moveRight(int currentX) {
		//need to figure out right wall coordinate
		return currentX + 3;
		
	}

	public static int moveUp(int currentY) {
		if(currentY <= 3) {
			return 0;
		}
		return currentY - 3;
		
	}

	public static int moveDown(int currentY) {
		return currentY + 3;
	}



	//--------------------------------------------------------------------------


//	public static int moveUpRight(int currentX) {
//		
//		throw new UnsupportedOperationException();
//	}
//
//	public static int moveUpLeft(int currentX) {
//		
//		throw new UnsupportedOperationException();
//	}
//
//	public static int moveDownRight(int currentY) {
//		throw new UnsupportedOperationException();
//	}
//
//	public static int moveDownLeft(int currentY) {
//		throw new UnsupportedOperationException();
//	}

}
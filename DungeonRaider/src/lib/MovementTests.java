package lib;


import org.junit.Test;

public class MovementTests {
	
    @Test
    public void testMoveLeft_1() throws Exception {
    	
        try {
           int currentX = 10;
           int newX = Movement.moveLeft(currentX);
           if(newX != 7 ) {
        	   throw new Exception("The value returned is not correct");
           }
        }
        catch (IllegalArgumentException | IndexOutOfBoundsException e) {
            e.printStackTrace();
        }
    }
    
    @Test
    public void testMoveLeft_2() throws Exception {
    	
        try {
           int currentX = 0;
           int newX = Movement.moveLeft(currentX);
           if(newX < 0  ) {
        	   throw new Exception("The value returned is not correct");
           }
        }
        catch (IllegalArgumentException | IndexOutOfBoundsException e) {
            e.printStackTrace();
        }
    }
    
    
    @Test
    public void testMoveRight_1() throws Exception {
    	
        try {
           int currentX = 0;
           int newX = Movement.moveRight(currentX);
           if(newX != 3  ) {
        	   throw new Exception("The value returned is not correct");
           }
        }
        catch (IllegalArgumentException | IndexOutOfBoundsException e) {
            e.printStackTrace();
        }
    }
    
    @Test
    public void testMoveUp_1() throws Exception {
    	
        try {
           int currentY = 10;
           int newY = Movement.moveUp(currentY);
           if(newY != 7  ) {
        	   throw new Exception("The value returned is not correct");
           }
        }
        catch (IllegalArgumentException | IndexOutOfBoundsException e) {
            e.printStackTrace();
        }
    }
    
    
    @Test
    public void testMoveUp_2() throws Exception {
    	
        try {
           int currentY = 0;
           int newY = Movement.moveUp(currentY);
           if(newY <0  ) {
        	   throw new Exception("The value returned is not correct");
           }
        }
        catch (IllegalArgumentException | IndexOutOfBoundsException e) {
            e.printStackTrace();
        }
    }
    
    @Test
    public void testMoveDown_1() throws Exception {
    	
        try {
           int currentY = 0;
           int newY = Movement.moveDown(currentY);
           if(newY != 3  ) {
        	   throw new Exception("The value returned is not correct");
           }
        }
        catch (IllegalArgumentException | IndexOutOfBoundsException e) {
            e.printStackTrace();
        }
    }
    
    @Test
   public void testMoveTopLeft_1() throws Exception{
    	try {
    		int currentX = 10;
    		int currentY = 10;
    		int newX = Movement.moveLeft(currentX);
    		int newY = Movement.moveUp(currentY);
    		if(newX != 7 || newY != 7) {
    			throw new Exception("The value returned is not correct");
    		}
    	}catch(IllegalArgumentException | IndexOutOfBoundsException e) {
    		e.printStackTrace();
    	}
    	
    }
    
    @Test
    public void testMoveTopLeft_2() throws Exception{
     	try {
     		int currentX = 0;
     		int currentY = 10;
     		int newX = Movement.moveLeft(currentX);
     		int newY = Movement.moveUp(currentY);
     		if(newX != 0|| newY != 7) {
     			throw new Exception("The value returned is not correct");
     		}
     	}catch(IllegalArgumentException | IndexOutOfBoundsException e) {
     		e.printStackTrace();
     	}
     	
     }
    
    @Test
    public void testMoveTopLeft_3() throws Exception{
    	try {
    		int currentX = 1;
    		int currentY = 1;
    		int newX = Movement.moveLeft(currentX);
    		int newY = Movement.moveUp(currentY);
    		if(newX != 0 || newY != 0 ) { //should get stuck at the corner at x= 0, y = 0 if its close to the walls
    			throw new Exception("The value returned is not correct");
    		}
    	}catch(IllegalArgumentException | IndexOutOfBoundsException e){
    		e.printStackTrace();
    	}
    }
    
    @Test
    public void testMoveTopLeft_4() throws Exception{
    	try {
    		int currentX = 10;
    		int currentY = 1;
    		int newX = Movement.moveLeft(currentX);
    		int newY = Movement.moveUp(currentY);
    		if(newX != 7 || newY != 0 ) { //x should still move freely. y gets stuck at the top 
    			throw new Exception("The value returned is not correct");
    		}
    	}catch(IllegalArgumentException | IndexOutOfBoundsException e){
    		e.printStackTrace();
    	}
    }
    
    @Test
    public void testMoveTopLeft_5() throws Exception{
    	try {
    		int currentX = 1;
    		int currentY = 10;
    		int newX = Movement.moveLeft(currentX);
    		int newY = Movement.moveUp(currentY);
    		if(newX != 0 || newY != 7) { //y should still move freely. x gets stuck at the left 
    			throw new Exception("The value returned is not correct");
    		}
    	}catch(IllegalArgumentException | IndexOutOfBoundsException e){
    		e.printStackTrace();
    	}
    }
    
    
    @Test
    public void testMoveTopRight_1() throws Exception{
     	try {
     		int currentX = 10;
     		int currentY = 10;
     		int newX = Movement.moveRight(currentX);
     		int newY = Movement.moveUp(currentY);
     		if(newX != 13 || newY != 7) {
     			throw new Exception("The value returned is not correct");
     		}
     	}catch(IllegalArgumentException | IndexOutOfBoundsException e) {
     		e.printStackTrace();
     	}
     	
     }
    
    @Test
    public void testMoveTopRight_2() throws Exception{
     	try {
     		int currentX = 10;
     		int currentY = 0;
     		int newX = Movement.moveRight(currentX);
     		int newY = Movement.moveUp(currentY);
     		if(newX != 13 || newY != 0) {
     			throw new Exception("The value returned is not correct");
     		}
     	}catch(IllegalArgumentException | IndexOutOfBoundsException e) {
     		e.printStackTrace();
     	}
     	
     }
    
    @Test
    public void testMoveTopRight_3() throws Exception{
     	try {
     		int currentX = 10;
     		int currentY = 1;
     		int newX = Movement.moveRight(currentX);
     		int newY = Movement.moveUp(currentY);
     		if(newX != 13 || newY != 0) { // should be able to move right. But the top wont go up any further.
     			throw new Exception("The value returned is not correct");
     		}
     	}catch(IllegalArgumentException | IndexOutOfBoundsException e) {
     		e.printStackTrace();
     	}
     	
     }
    
    @Test
    public void testMoveBotLeft_1() throws Exception{
     	try {
     		int currentX = 10;
     		int currentY = 10;
     		int newX = Movement.moveLeft(currentX);
     		int newY = Movement.moveDown(currentY);
     		if(newX != 7 || newY != 13) {
     			throw new Exception("The value returned is not correct");
     		}
     	}catch(IllegalArgumentException | IndexOutOfBoundsException e) {
     		e.printStackTrace();
     	}
     	
     }
    
    @Test
    public void testMoveBotLeft_2() throws Exception{
     	try {
     		int currentX = 0;
     		int currentY = 10;
     		int newX = Movement.moveLeft(currentX);
     		int newY = Movement.moveDown(currentY);
     		if(newX < 0 || newY != 13) {
     			throw new Exception("The value returned is not correct");
     		}
     	}catch(IllegalArgumentException | IndexOutOfBoundsException e) {
     		e.printStackTrace();
     	}
     	
     }
    
    @Test
    public void testMoveBotLeft_3() throws Exception{
     	try {
     		int currentX = 1;
     		int currentY = 10;
     		int newX = Movement.moveLeft(currentX);
     		int newY = Movement.moveDown(currentY);
     		if(newX != 0  || newY != 13) { // x should be at 0 since the object has hit the left wall. there is still room to move down 
     			throw new Exception("The value returned is not correct");
     		}
     	}catch(IllegalArgumentException | IndexOutOfBoundsException e) {
     		e.printStackTrace();
     	}
     	
     }
    
    
    
    @Test
    public void testMoveBotRight_1() throws Exception{
     	try {
     		int currentX = 10;
     		int currentY = 10;
     		int newX = Movement.moveRight(currentX);
     		int newY = Movement.moveDown(currentY);
     		if(newX != 13 || newY != 13) {
     			throw new Exception("The value returned is not correct");
     		}
     	}catch(IllegalArgumentException | IndexOutOfBoundsException e) {
     		e.printStackTrace();
     	}
     	
     }
    
  
    
    
}


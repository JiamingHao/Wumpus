package tests;

import static org.junit.Assert.*;

import org.junit.Test;

import model.Wumpus;
/*===============================================================
|Author:      Jiaming Hao
|
|Class name:  WumpusTest
|
|Description: A test class tests some of the game's basic performance
|             as well as making sure there is no useless code
*==============================================================*/
public class WumpusTest {

  // test several basic function of the Wumpus class, including
  //  moving the hunter and some helper methods
  @Test
  public void testBasic() {
    Wumpus testingGame = new Wumpus();
    //0 means up, 1 means down, 2 means left, 3 means right
    int ranMove;
    int count = 0;
    
    while(count < 1000)
    {
    	while(testingGame.stillRunning())
    	{
    		ranMove = (int) (Math.random()*4);
    		if (ranMove == 0)
    			testingGame.hunterMove("UP", false);
    		else if (ranMove == 1)
    			testingGame.hunterMove("DOWN", false);
    		else if (ranMove == 2)
    			testingGame.hunterMove("LEFT", false); 
    		else if (ranMove == 3)
    			testingGame.hunterMove("RIGHT", false);
    	}
    	testingGame.getMap();
    	testingGame.getMaskedMap();
    	testingGame.getWumpus();
    	testingGame.getwumpusPos();
    	testingGame.getPlayerPos();
    	testingGame.getHint();
    	assertFalse(testingGame.stillRunning());
    	testingGame.hunterMove("UP", false);
    	testingGame.sendHunterPosition();
    	count++;
    	testingGame.startNewGame();
    }
  }
  //mainly test the shoot method of Wumpus class
  @Test 
  public void testShoot()
  {
	  Wumpus testingGame = new Wumpus();
	  int ranMove;
	  int count = 0;
	  while(count < 1000)
	  {
		  while(testingGame.stillRunning())
		  {
			  ranMove = (int) (Math.random()*4);
			  if (ranMove == 0)
			  {
				  testingGame.hunterMove("UP", false);
				  testingGame.shoot("N");
			  }
			  else if (ranMove == 1)
			  {
				  testingGame.hunterMove("DOWN", false);
				  testingGame.shoot("S");
			  }
			  else if (ranMove == 2)
			  {
				  testingGame.hunterMove("LEFT", false); 
				  testingGame.shoot("W");
			  }
			  else if (ranMove == 3)
			  {
				  testingGame.hunterMove("RIGHT", false);
				  testingGame.shoot("E");
			  }
		  }
		  assertFalse(testingGame.stillRunning());
		  count++;
		  testingGame.startNewGame();
  	  }
	  
  }
}

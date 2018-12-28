package model;

import java.awt.Point;
import java.util.Observable;

/*===============================================================
|Author:      Jiaming Hao
|
|Class name:  Wumpus
|
|Description: The main class of Game Wumpus, including fields about 
|             maps, wumpu, hunter, and other elements like slime, pit
*==============================================================*/
public class Wumpus extends Observable {

	private char[][] map;          // this is the map containing all the elements
	private int size;              // the size of both maps, which is 12
	private Point playerPos;       // keep track of the current player position in the map
	private Point wumpusPos;       // store the position of Wumpus
	private String hint;           // this is only used for textView, indicate the status of hunter
	private char[][] maskedMap;    // this is the map player can see while playing, only showing details about
								   // places that the hunter visited
	private int numOfArrow;        // keep track of the number of arrows, can only be 0 or 1
	private boolean IsWumpusKilled;// indicate whether Wumpus has been killed by arrow or not

	/*---------------------------------------------------------------------
	  | Constructor Wumpus()
	  |
	  |  Purpose:    This one with no arguments is the normal one for common
	  |              game
	  |            
	  |  Parameters: None
	  |
	  |  Returns:    None
	  *-------------------------------------------------------------------*/
	public Wumpus() { 
		size = 12;
		initializeMap();
		numOfArrow = 1;
		IsWumpusKilled = false;
	}

	/*---------------------------------------------------------------------
	  |  Method      initializeMap
	  |
	  |  Purpose:    Only called by the constructor with no arguments, it generates 
	  |              two maps, randomly produces the location of wumpus, hunter, and
	  |              set blood, pit, goop accordingly.
	  |            
	  |  Parameters: None
	  |
	  |  Returns:    None
	  *-------------------------------------------------------------------*/
	private void initializeMap() {
		map = new char[size][size];
		maskedMap = new char[size][size];
		numOfArrow = 1;// reset the number of the arrows
		IsWumpusKilled = false;
		for (int i = 0; i < size; i++) {
			for (int j = 0; j < size; j++)// reset both maps
			{
				map[i][j] = '_';
				maskedMap[i][j] = 'X';
			}
		}
		// randomly generate the position of Wumpus
		int wumpusRow = (int) (Math.random() * 12);
		int wumpusColumn = (int) (Math.random() * 12);
		wumpusPos = new Point(wumpusRow, wumpusColumn);
		// System.out.println("Wumpus's position: "+ wumpusRow+ " "+
		// wumpusColumn);//debug
		map[wumpusRow][wumpusColumn] = 'w';
		// deal with the blood surrounding the Wumpus
		// --------------------------------horizontal--------------------------------------
		if ((wumpusColumn + 1) < size) {
			map[wumpusRow][wumpusColumn + 1] = 'B';
		} else// if exceed the size, wrap around
		{
			map[wumpusRow][wumpusColumn + 1 - size] = 'B';
		}
		if ((wumpusColumn + 2) < size) {
			map[wumpusRow][wumpusColumn + 2] = 'B';
		} else {
			map[wumpusRow][wumpusColumn + 2 - size] = 'B';
		}
		if ((wumpusColumn - 1) > -1) {
			map[wumpusRow][wumpusColumn - 1] = 'B';
		} else {
			map[wumpusRow][wumpusColumn - 1 + size] = 'B';
		}
		if ((wumpusColumn - 2) > -1) {
			map[wumpusRow][wumpusColumn - 2] = 'B';
		} else {
			map[wumpusRow][wumpusColumn - 2 + size] = 'B';
		}
		// ---------------------------------------vertical---------------------------------------
		if ((wumpusRow + 1) < size) {
			map[wumpusRow + 1][wumpusColumn] = 'B';
		} else {
			map[wumpusRow + 1 - size][wumpusColumn] = 'B';
		}
		if ((wumpusRow + 2) < size) {
			map[wumpusRow + 2][wumpusColumn] = 'B';
		} else {
			map[wumpusRow + 2 - size][wumpusColumn] = 'B';
		}
		if ((wumpusRow - 1) > -1) {
			map[wumpusRow - 1][wumpusColumn] = 'B';
		} else {
			map[wumpusRow - 1 + size][wumpusColumn] = 'B';
		}
		if ((wumpusRow - 2) > -1) {
			map[wumpusRow - 2][wumpusColumn] = 'B';
		} else {
			map[wumpusRow - 2 + size][wumpusColumn] = 'B';
		}
		// --------------------------------------- diagonal------------------------------------
		if ((wumpusRow - 1) > -1 && (wumpusColumn - 1) > -1) {
			map[wumpusRow - 1][wumpusColumn - 1] = 'B';
		} else {
			if ((wumpusRow - 1) > -1 && (wumpusColumn - 1) <= -1) {
				map[wumpusRow - 1][wumpusColumn - 1 + size] = 'B';
			} else if ((wumpusRow - 1) <= -1 && (wumpusColumn - 1) > -1) {
				map[wumpusRow - 1 + size][wumpusColumn - 1] = 'B';
			} else {
				map[wumpusRow - 1 + size][wumpusColumn - 1 + size] = 'B';
			}
		}
		if ((wumpusRow + 1) < size && (wumpusColumn + 1) < size) {
			map[wumpusRow + 1][wumpusColumn + 1] = 'B';
		} else {
			if ((wumpusRow + 1) < size && (wumpusColumn + 1) >= size) {
				map[wumpusRow + 1][wumpusColumn + 1 - size] = 'B';
			} else if ((wumpusRow + 1) >= size && (wumpusColumn + 1) < size) {
				map[wumpusRow + 1 - size][wumpusColumn + 1] = 'B';
			} else {
				map[wumpusRow + 1 - size][wumpusColumn + 1 - size] = 'B';
			}
		}
		if ((wumpusRow - 1) > -1 && (wumpusColumn + 1) < size) {
			map[wumpusRow - 1][wumpusColumn + 1] = 'B';
		} else {
			if ((wumpusRow - 1) <= -1 && (wumpusColumn + 1) < size) {
				map[wumpusRow - 1 + size][wumpusColumn + 1] = 'B';
			} else if ((wumpusRow - 1) > -1 && (wumpusColumn + 1) >= size) {
				map[wumpusRow - 1][wumpusColumn + 1 - size] = 'B';
			} else {
				map[wumpusRow - 1 + size][wumpusColumn + 1 - size] = 'B';
			}
		}
		if ((wumpusRow + 1) < size && (wumpusColumn - 1) > -1) {
			map[wumpusRow + 1][wumpusColumn - 1] = 'B';
		} else {
			if ((wumpusRow + 1) >= size && (wumpusColumn - 1) > -1) {
				map[wumpusRow + 1 - size][wumpusColumn - 1] = 'B';
			} else if ((wumpusRow + 1) < size && (wumpusColumn - 1) <= -1) {
				map[wumpusRow + 1][wumpusColumn - 1 + size] = 'B';
			} else {
				map[wumpusRow + 1 - size][wumpusColumn - 1 + size] = 'B';
			}
		}
		// ---------------------------------------blood dealing finished ---------------------------
		
		generateBottomlessSlimePits();
		generatePlayer();
		stillRunning();// set the status of the hunter at the beginning of the game, it should be "safe for now"
						
	}

	/*---------------------------------------------------------------------
	  |  Method getMap()
	  |
	  |  Purpose:    Export the map 2d array so that other classes can use
	  |            
	  |  Parameters: None
	  |
	  |  Returns:    char[][]
	  *-------------------------------------------------------------------*/
	public char[][] getMap() {
		return map;
	}

	/*---------------------------------------------------------------------
	  |  Method getMaskedMap()
	  |
	  |  Purpose:    Export the MaskedMap 2d array so that other classes can use
	  |            
	  |  Parameters: None
	  |
	  |  Returns:    char[][]
	  *-------------------------------------------------------------------*/
	public char[][] getMaskedMap() {
		return maskedMap;
	}

	/*---------------------------------------------------------------------
	  |  Method getPlayerPos()
	  |
	  |  Purpose:    Get the exact location of hunter in row and column number 
	  |              using a Point object
	  |  
	  |  Parameters: None
	  |
	  |  Returns:    Point
	  *-------------------------------------------------------------------*/
	public Point getPlayerPos() {
		return playerPos;
	}

	/*---------------------------------------------------------------------
	  |  Method getwumpus()
	  |
	  |  Purpose:    Get the exact location of wumpus in row and column number 
	  |              using a Point object
	  |  
	  |  Parameters: None
	  |
	  |  Returns:    Point
	  *-------------------------------------------------------------------*/
	public Point getwumpusPos() {
		return wumpusPos;
	}

	/*---------------------------------------------------------------------
	  |  Method generatePlayer()
	  |
	  |  Purpose:    Randomly generate the position of hunter, and store it in
	  |  			 playerPos
	  |
	  |  Parameters: None
	  |
	  |  Returns:    None
	  *-------------------------------------------------------------------*/
	private void generatePlayer() {

		int Row = (int) (Math.random() * 12);
		int Column = (int) (Math.random() * 12);
		while (map[Row][Column] != '_') {
			Row = (int) (Math.random() * 12);
			Column = (int) (Math.random() * 12);
		}
		maskedMap[Row][Column] = 'O';// unless the game ends, only the masked map knows the position of the hunter
		playerPos = new Point(Row, Column);
	}

	/*---------------------------------------------------------------------
	 |  Method generateBottomlessSlimePits()
	 |
	 |  Purpose:    Randomly generate the positions of pits, and set slime 
	 |  			 accordingly surrounding the pits
	 |
	 |  Parameters: None
	 |
	 |  Returns:    None
	 *-------------------------------------------------------------------*/
	private void generateBottomlessSlimePits() {
		int num = (int) (Math.random() * 3 + 3);// randomly pick the number of BottomlessSlimePits between 3 to 5
		// System.out.println("The number of pit is: " + num);//debug
		int Row = (int) (Math.random() * 12);
		int Column = (int) (Math.random() * 12);

		for (int i = 0; i < num; i++) {
			while (map[Row][Column] == 'W' || map[Row][Column] == 'P')// if the position is already a pit or a Wumpus
			{
				// random again
				Row = (int) (Math.random() * 12);
				Column = (int) (Math.random() * 12);
			}
			map[Row][Column] = 'P';// a pit can overwrite slime and blood according the spec
			// the following code deals with the slime surrounding the pit
			if (Row + 1 < size) {
				if (map[Row + 1][Column] == 'B')// if the position already has blood, then it becomes Goop
					map[Row + 1][Column] = 'G';
				else if (map[Row + 1][Column] != 'P')// be careful not to overwrite another pit
					map[Row + 1][Column] = 'S';
			} else// the wrap around situation
			{
				if (map[Row + 1 - size][Column] == 'B')
					map[Row + 1 - size][Column] = 'G';
				else if (map[Row + 1 - size][Column] != 'P')
					map[Row + 1 - size][Column] = 'S';
			}

			if (Row - 1 > -1) {
				if (map[Row - 1][Column] == 'B')
					map[Row - 1][Column] = 'G';
				else if (map[Row - 1][Column] != 'P')
					map[Row - 1][Column] = 'S';
			} else {
				if (map[Row - 1 + size][Column] == 'B')
					map[Row - 1 + size][Column] = 'G';
				else if (map[Row - 1 + size][Column] != 'P')
					map[Row - 1 + size][Column] = 'S';
			}

			if (Column + 1 < size) {
				if (map[Row][Column + 1] == 'B')
					map[Row][Column + 1] = 'G';
				else if (map[Row][Column + 1] != 'P')
					map[Row][Column + 1] = 'S';
			} else {
				if (map[Row][Column + 1 - size] == 'B')
					map[Row][Column + 1 - size] = 'G';
				else if (map[Row][Column + 1 - size] != 'P')
					map[Row][Column + 1 - size] = 'S';
			}

			if (Column - 1 > -1) {
				if (map[Row][Column - 1] == 'B')
					map[Row][Column - 1] = 'G';
				else if (map[Row][Column - 1] != 'P')
					map[Row][Column - 1] = 'S';
			} else {
				if (map[Row][Column - 1 + size] == 'B')
					map[Row][Column - 1 + size] = 'G';
				else if (map[Row][Column - 1 + size] != 'P')
					map[Row][Column - 1 + size] = 'S';
			}
		}
		map[wumpusPos.x][wumpusPos.y] = 'W';// in case that the position of wumpus has been overwritten
	}

	/*---------------------------------------------------------------------
	 |  Method      startNewGame()
	 |
	 |  Purpose:    Clear the current game and start a new game with everything  
	 |				 new	
	 |  
	 |  Parameters: None
	 |
	 |  Returns:    None
	 *-------------------------------------------------------------------*/
	public void startNewGame() {
		// TODO Auto-generated method stub
		initializeMap();
		setChanged();
		notifyObservers("startNewGame()");
	}

	/*---------------------------------------------------------------------
	  |  Method      hunterMove(String ins, boolean testing)
	  |
	  |  Purpose:    Move the hunter on the maskedMap according to the String ins,
	  |              also update the maskedMap at the same time
	  |  
	  |  Parameters: String ins, boolean testing
	  |
	  |  Returns:    None
	  *-------------------------------------------------------------------*/
	public void hunterMove(String ins, boolean testing) {
		if (stillRunning() == false) {
			// System.out.println("Method stillRunning returns false!");//debug
			return;
		}
		// if the game is still running

		if (map[(int) playerPos.getX()][(int) playerPos.getY()] == '_') {
			maskedMap[(int) playerPos.getX()][(int) playerPos.getY()] = ' ';// the place where the hunter visited
																			// becomes space
			map[(int) playerPos.getX()][(int) playerPos.getY()] = ' ';
		} else
			maskedMap[(int) playerPos.getX()][(int) playerPos.getY()] = map[(int) playerPos.getX()][(int) playerPos
					.getY()];

		if (ins.equals("UP")) {
			if (playerPos.getX() - 1 > -1) {
				playerPos.setLocation(playerPos.getX() - 1, playerPos.getY());
			} else {
				playerPos.setLocation(playerPos.getX() - 1 + size, playerPos.getY());
			}
		} else if (ins.equals("DOWN")) {
			if (playerPos.getX() + 1 < size) {
				playerPos.setLocation(playerPos.getX() + 1, playerPos.getY());
			} else {
				playerPos.setLocation(playerPos.getX() + 1 - size, playerPos.getY());
			}
		} else if (ins.equals("LEFT")) {
			if (playerPos.getY() - 1 > -1) {
				playerPos.setLocation(playerPos.getX(), playerPos.getY() - 1);
			} else {
				playerPos.setLocation(playerPos.getX(), playerPos.getY() - 1 + size);
			}
		} else if (ins.equals("RIGHT")) {
			if (playerPos.getY() + 1 < size) {
				playerPos.setLocation(playerPos.getX(), playerPos.getY() + 1);
			} else {
				playerPos.setLocation(playerPos.getX(), playerPos.getY() + 1 - size);
			}
		}
		maskedMap[(int) playerPos.getX()][(int) playerPos.getY()] = 'O';// update the position of hunter
		stillRunning();// this method here only updates the current status of hunter, it does not stop
						// the game
		setChanged();
		notifyObservers();
	}

	/*---------------------------------------------------------------------
	  |  Method      stillRunning()
	  |
	  |  Purpose:    Check if the current game ends or not, every time this method is 
	  |              called, it updates the status of the hunter
	  |  
	  |  Parameters: None
	  |
	  |  Returns:    boolean 
	  *-------------------------------------------------------------------*/
	public boolean stillRunning() {
		int currentRow = (int) playerPos.getX();
		int currentColumn = (int) playerPos.getY();
		if (map[currentRow][currentColumn] == 'W') {
			hint = "You walked into Wumpus room !";
			return false;
		}
		if (map[currentRow][currentColumn] == 'P') {
			hint = "You walked into a pit !";
			return false;
		}
		if (map[currentRow][currentColumn] == 'O') {
			return false;
		}
		if (numOfArrow == 0) {
			if (IsWumpusKilled)
				hint = "Killed Wumpus successfully !";
			else
				hint = " Your Arrow kill yourself !";
			return false;
		}
		if (map[currentRow][currentColumn] == 'B') {
			hint = "I see blood !";
		} else if (map[currentRow][currentColumn] == 'S') {
			hint = "The ground is sticky !";
		} else if (map[currentRow][currentColumn] == 'G') {
			hint = "I smell something foul !";
		} else {
			hint = "Safe for now";
		}

		return true;
	}

	/*---------------------------------------------------------------------
	 |  Method getWumpus()
	 |
	 |  Purpose:    Get the Wumpus object itself 
	 |              
	 |  Parameters: None
	 |
	 |  Returns:    Wumpus
	 *-------------------------------------------------------------------*/
	public Wumpus getWumpus() {
		return this;
	}

	/*---------------------------------------------------------------------
	  |  Method getHint()
	  |
	  |  Purpose:    Get the hunter status, such as "Safe now" 
	  |              
	  |  Parameters: None
	  |
	  |  Returns:    String
	  *-------------------------------------------------------------------*/
	public String getHint() {
		return hint;
	}

	/*---------------------------------------------------------------------
	  |  Method senfHunterPosition()
	  |
	  |  Purpose:    The maskedMap sends out the location of hunter to map, only 
	  |              happens when the current game ends
	  |              
	  |  Parameters: None
	  |
	  |  Returns:    None
	  *-------------------------------------------------------------------*/
	public void sendHunterPosition() {
		map[(int) playerPos.getX()][(int) playerPos.getY()] = 'O';
	}

	/*---------------------------------------------------------------------
	  |  Method shoot(String direction)
	  |
	  |  Purpose:    Hunter shoot using arrow to the direction according to 
	  |              the String parameter
	  |              
	  |  Parameters: String direction
	  |
	  |  Returns:    None
	  *-------------------------------------------------------------------*/
	public void shoot(String direction) {
		numOfArrow = 0;
		if (direction.equals("N")) {
			for (int i = 0; i < 12; i++) {
				if (map[i][(int) playerPos.getY()] == 'W') {
					IsWumpusKilled = true;
					setChanged();
					notifyObservers();
					return;
				}
			}
		} else if (direction.equals("S")) {
			// temp = (int) playerPos.getX();
			for (int i = 0; i < 12; i++) {
				if (map[i][(int) playerPos.getY()] == 'W') {
					IsWumpusKilled = true;
					setChanged();
					notifyObservers();
					return;
				}
			}
		} else if (direction.equals("E")) {
			// temp = (int)playerPos.getY();
			for (int i = 0; i < 12; i++) {
				if (map[(int) playerPos.getX()][i] == 'W') {
					IsWumpusKilled = true;
					setChanged();
					notifyObservers();
					return;
				}
			}
		} else if (direction.equals("W")) {
			// temp = (int)playerPos.getY();
			for (int i = 0; i < 12; i++) {
				if (map[(int) playerPos.getX()][i] == 'W') {
					IsWumpusKilled = true;
					setChanged();
					notifyObservers();
					return;
				}
			}
		}
		setChanged();
		notifyObservers();
	}
}

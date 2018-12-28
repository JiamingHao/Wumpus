package views;

import java.util.Observable;
import java.util.Observer;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import model.Wumpus;
/*===============================================================
|Author: Jiaming Hao
|
|Class Name:ImageView
|
|Description:Compose the drawing view of the Wumpus Game
*==============================================================*/
 

public class ImageView extends GridPane implements Observer{

	private Wumpus theGame;
	private Button NButton;
	private Button SButton;
	private Button EButton; 
	private Button WButton;
	private Canvas canvas;
	private GraphicsContext gc;
	private Image ground;
	private Image blood;
	private Image goop;
	private Image slime;
	private Image slimePit;
	private Image hunter;
	private Image wumpus;
	/*---------------------------------------------------------------------
	  | Constructor  ImageView()
	  |
	  |  Purpose:    Create new ImageView object through Wumpus's data as  
	  |              well as set up all the parts an ImageView needs
	  |  
	  |  Parameters: Wumpus
	  |
	  |  Returns:    None
	  *-------------------------------------------------------------------*/
	public ImageView (Wumpus Game) 
	{
		theGame = Game;
		this.setVgap(10);
		this.setHgap(10);
		ground = new Image("file:images/Ground.png",false);
		blood = new Image("file:images/Blood.png",false);
		goop = new Image("file:images/Goop.png",false);
		slime = new Image("file:images/Slime.png",false);
		slimePit = new Image("file:images/SlimePit.png",false);
		hunter = new Image("file:images/TheHunter.png",false);
		wumpus = new Image("file:images/Wumpus.png",false);
		NButton = new Button("N");
		SButton = new Button("S");
		EButton = new Button("E");
		WButton = new Button("W");
		canvas = new Canvas(396,396);
		canvas.setFocusTraversable(true);
		NButton.setFocusTraversable(false);//make buttons not get focus during game running
		SButton.setFocusTraversable(false);
		EButton.setFocusTraversable(false);
		WButton.setFocusTraversable(false);
		canvas.setOnKeyPressed(new KeyHandler());
		gc = canvas.getGraphicsContext2D();
		initializeCanvas();
		
	}
	/*===============================================================
	|Class Name: KeyHandler
 	|
	|Description:Handle the keyPressed event particular in this game
	*==============================================================*/
	private class KeyHandler implements EventHandler<KeyEvent> {

		@Override
		public void handle(KeyEvent arg0) {
			// System.out.println(arg0.getCode().toString());//debug
			String key = arg0.getCode().toString();
			theGame.hunterMove(key, false);
		}

	}
	/*---------------------------------------------------------------------
	  |  Method      initializeCanvas
	  |
	  |  Purpose:    Called by the constructor, lay out the buttons and  
	  |              set on action, also draw the background.
	  |            
	  |  Parameters: None
	  |
	  |  Returns:    None
	  *-------------------------------------------------------------------*/
	private void initializeCanvas() {
		gc.setFill(Color.BLACK);
		gc.fillRect(0, 0, canvas.getWidth(), canvas.getHeight()); // if there is nothing, the place hunter visited 
		// is black
		this.add(canvas, 0, 0, 5, 3);
		this.add(NButton, 3, 3);
		this.add(SButton, 3, 5);
		this.add(WButton, 2, 4);
		this.add(EButton, 4, 4);
		ButtonListener handler = new ButtonListener();
		NButton.setOnAction(handler);
		SButton.setOnAction(handler);
		WButton.setOnAction(handler);
		EButton.setOnAction(handler);
		updateCanvas();//simply draw the map including the position of hunter for the first time
	}
	/*===============================================================
	|Class Name: ButtonListener
 	|
	|Description:Handle the button click event particular in this game
	*==============================================================*/
	private class ButtonListener implements EventHandler<ActionEvent> {

		@Override
		public void handle(ActionEvent e) {
			// TODO Auto-generated method stub
			 Button buttonClicked = (Button) e.getSource();
			 //System.out.println("The button clicked is: " + buttonClicked.getText());//deubg
			 theGame.shoot(buttonClicked.getText());//figure out which direction hunter shoot
			 //according to the button text 
		}
	
	}
	/*---------------------------------------------------------------------
	  |  Method      updateCanvas
	  |
	  |  Purpose:    Draw details about the game on the canvas, including 
	  |              all the elements using data from Wumpus object
	  |  
	  |  Parameters: None
	  |
	  |  Returns:    None
	  *-------------------------------------------------------------------*/
	private void updateCanvas() {
		char[][] temp = theGame.getMaskedMap();
		char[][] temp2 = theGame.getMap();
		
		for(int i=0;i<12;i++)
		{
			for(int j=0;j<12;j++)
			{
				if(temp[i][j]=='X')
				{
					gc.drawImage(ground, j*33, i*33, 33, 33);
				}
				else if(temp[i][j] == 'O')//hunter
				{
					gc.fillRect(j*33, i*33, 33, 33);
					if(temp2[i][j] == 'B')//blood
						gc.drawImage(blood, j*33, i*33, 33, 33);
					else if(temp2[i][j] == 'G')//goop
						gc.drawImage(goop, j*33, i*33, 33, 33);
					else if(temp2[i][j] == 'W')//Wumpus
						gc.drawImage(wumpus, j*33, i*33, 33, 33);
					else if(temp2[i][j] == 'P')//pit
						gc.drawImage(slimePit, j*33, i*33, 33, 33);
					else if(temp2[i][j] == 'S')//slime
						gc.drawImage(slime, j*33, i*33, 33, 33);
					gc.drawImage(hunter, j*33, i*33, 33, 33);
				} 
				else if(temp[i][j] == ' ')
				{
					gc.fillRect(j*33, i*33, 33, 33);
				}
				else if(temp[i][j] == 'B')
				{
					gc.fillRect(j*33, i*33, 33, 33);
					gc.drawImage(blood, j*33, i*33, 33, 33);
				}
				else if (temp[i][j] == 'G')
				{
					gc.fillRect(j*33, i*33, 33, 33);
					gc.drawImage(goop, j*33, i*33, 33, 33);
				}
				else if(temp[i][j] == 'S')
				{
					gc.fillRect(j*33, i*33, 33, 33);
					gc.drawImage(slime, j*33, i*33, 33, 33);
				}
			}
		}
	}
	/*---------------------------------------------------------------------
	  |  Method     update
	  |
	  |  Purpose:   This method is called by OurObservable's notifyObservers()
	  |  
	  |  Parameters: Observable observable, Object arg1
	  |
	  |  Returns:    None
	  *-------------------------------------------------------------------*/
	@Override
	public void update(Observable observable, Object arg1) {
		theGame = (Wumpus) observable;
		if(theGame.stillRunning()==false)
		{
			//System.out.println("StillRunning shows false");//debug
			showAll();//display the whole map when the game finished
		}
		else
		{
			updateCanvas();
		}
	}
	/*---------------------------------------------------------------------
	  |  Method     showAll
	  |
	  |  Purpose:   This method is called by update() only when the game is 
	  |             over, it reveals all warnings and hazards
	  |  
	  |  Parameters: Observable observable, Object arg1
	  |
	  |  Returns:    None
	  *-------------------------------------------------------------------*/
	private void showAll() {
		char[][] temp = theGame.getMaskedMap();
		char[][] temp2 = theGame.getMap();
		gc.fillRect(0, 0, canvas.getWidth(), canvas.getHeight());
		for(int i=0;i<12;i++)
		{
			for(int j=0;j<12;j++)
			{
				if(temp[i][j]=='X')
				{
					gc.drawImage(ground, j*33, i*33, 33, 33);
					if(temp2[i][j] == 'B')
						gc.drawImage(blood, j*33, i*33, 33, 33);
					else if(temp2[i][j] == 'G')//GOOP
						gc.drawImage(goop, j*33, i*33, 33, 33);
					else if(temp2[i][j] == 'W')
						gc.drawImage(wumpus, j*33, i*33, 33, 33);
					else if(temp2[i][j] == 'P')
						gc.drawImage(slimePit, j*33, i*33, 33, 33);
					else if(temp2[i][j] == 'S')
						gc.drawImage(slime, j*33, i*33, 33, 33);
				}
				else if(temp[i][j] == 'O')
				{
					gc.fillRect(j*33, i*33, 33, 33);
					if(temp2[i][j] == 'B')
						gc.drawImage(blood, j*33, i*33, 33, 33);
					else if(temp2[i][j] == 'G')//GOOP
						gc.drawImage(goop, j*33, i*33, 33, 33);
					else if(temp2[i][j] == 'W')
						gc.drawImage(wumpus, j*33, i*33, 33, 33);
					else if(temp2[i][j] == 'P')
						gc.drawImage(slimePit, j*33, i*33, 33, 33);
					else if(temp2[i][j] == 'S')
						gc.drawImage(slime, j*33, i*33, 33, 33);
					gc.drawImage(hunter, j*33, i*33, 33, 33);
				} 
				else if(temp[i][j] == ' ')
				{
					gc.fillRect(j*33, i*33, 33, 33);
				}
				else if(temp[i][j] == 'B')
				{
					gc.fillRect(j*33, i*33, 33, 33);
					gc.drawImage(blood, j*33, i*33, 33, 33);
				}
				else if (temp[i][j] == 'G')
				{
					gc.fillRect(j*33, i*33, 33, 33);
					gc.drawImage(goop, j*33, i*33, 33, 33);
				}
				else if(temp[i][j] == 'S')
				{
					gc.fillRect(j*33, i*33, 33, 33);
					gc.drawImage(slime, j*33, i*33, 33, 33);
				}
			}
		}
	}
	

}

package views;

import java.util.Observable;
import java.util.Observer;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.GridPane;
import model.Wumpus;
/*===============================================================
|Author: Jiaming Hao
|
|Class Name:ImageView
|
|Description:Compose the text view of the Wumpus Game
*==============================================================*/

public class TextView extends GridPane implements Observer{

	private Wumpus theGame;
	private TextArea Display;
	private Button NButton;
	private Button SButton;
	private Button EButton;
	private Button WButton;
	private Label status;// place where will update the warning message if there is any
	/*---------------------------------------------------------------------
	  | Constructor  TextView()
	  |
	  |  Purpose:    Create new TextView object through Wumpus's data as  
	  |              well as set up all the parts an TextView needs
	  |  
	  |  Parameters: Wumpus 
	  |
	  |  Returns:    None
	  *-------------------------------------------------------------------*/
	public TextView (Wumpus Game)
	{
		theGame = Game; 
		this.setVgap(10);
		this.setHgap(10);
		Display = new TextArea();
		NButton = new Button("N");
		SButton = new Button("S");
		EButton = new Button("E");
		WButton = new Button("W");
		initializePane();
		Display.setOnKeyPressed(new KeyHandler());
		Display.setStyle("-fx-font-family: Monospaced;");
		cheater();
	}
	/*---------------------------------------------------------------------
	  |  Method      cheater
	  |
	  |  Purpose:    A method used for debug, reveals all the elements inside  
	  |              a map when being called
	  |            
	  |  Parameters: None
	  |
	  |  Returns:    None
	  *-------------------------------------------------------------------*/
	private void cheater() {
		char[][] temp = theGame.getMap(); 
		String model = "\n";
		for(int i=0;i<12;i++)
		{
			for(int j=0;j<12;j++)
			{
				if(j!=11)
					model = model + "  " + temp[i][j];
				else
					model = model + "  " + temp[i][j] + "\n";
			}
		}
		System.out.println(model);
	}
	/*---------------------------------------------------------------------
	  |  Method      initializePane
	  |
	  |  Purpose:    Called by the constructor, lay out the buttons and  
	  |              set on action.
	  |            
	  |  Parameters: None
	  |
	  |  Returns:    None
	  *-------------------------------------------------------------------*/
	private void initializePane() {
		status = new Label("Safe for now");
		this.add(Display, 2, 2,3,3);
		Display.setMinHeight(280.0);
		Display.setMaxWidth(350.0);
		Display.setEditable(false);
		this.add(status, 2, 5,3,1);
		this.add(NButton, 3,6);
		this.add(SButton, 3,8);
		this.add(WButton, 2,7);
		this.add(EButton, 4,7);
		ButtonListener handler = new ButtonListener();
		NButton.setOnAction(handler);
		SButton.setOnAction(handler);
		WButton.setOnAction(handler);
		EButton.setOnAction(handler);
		updateDisplay();
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
			 //System.out.println("The button cliked is: " + buttonClicked.getText());//deubg
			 theGame.shoot(buttonClicked.getText());
		}
	
	}
	/*===============================================================
	|Class Name: KeyHandler
 	|
	|Description:Handle the keyPressed event particular in this game
	*==============================================================*/
	private class KeyHandler implements EventHandler<KeyEvent>
	{

		@Override
		public void handle(KeyEvent arg0) {
			//System.out.println(arg0.getCode().toString());//debug
			String key = arg0.getCode().toString();
			//System.out.println(theGame.stillRunning() + "!");//debug
			theGame.hunterMove(key, false);//hunterMove() method will do all the checking
			//updateDisplay();
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
	public void update(Observable observable, Object message) {
		
		theGame = (Wumpus) observable;
		//updateDisplay();
		status.setText(theGame.getHint());
		if(theGame.stillRunning()==false)
		{
			status.setText(theGame.getHint());
			theGame.sendHunterPosition();
			showAll();
		}
		else
		{
			updateDisplay();
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
		char[][] temp = theGame.getMap();
		String model = "\n";
		for(int i=0;i<12;i++)
		{
			for(int j=0;j<12;j++)
			{
				if(j!=11)
					model = model + "  " + temp[i][j];
				else
					model = model + "  " + temp[i][j] + "\n";
			}
		}
		Display.setText(model);
		//System.out.println(model);
	}
	/*---------------------------------------------------------------------
	  |  Method      updateDisplay
	  |
	  |  Purpose:    Prints details about the game on the textAarea, including 
	  |              all the elements using data from Wumpus object
	  |  
	  |  Parameters: None
	  |
	  |  Returns:    None
	  *-------------------------------------------------------------------*/
	public void updateDisplay()
	{
		char[][] temp = theGame.getMaskedMap();
		String model = "\n";
		for(int i=0;i<12;i++)
		{
			for(int j=0;j<12;j++)
			{
				if(j!=11)
					model = model + "  " + temp[i][j];
				else
					model = model + "  " + temp[i][j] + "\n";
			}
		}
		Display.setText(model);
		//System.out.println(model);//debug
	}

}

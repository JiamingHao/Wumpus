package controller;

import java.util.Observer;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import model.Wumpus;
import views.ImageView;
import views.TextView;
/*===============================================================
|Author:      Jiaming Hao
|
|Class name:  WumpusMain
|
|Description: Using this class to start the whole game, mainly 
|             being responsible for building the menu, assembling
|             components together for game running
*==============================================================*/

public class WumpusMain extends Application {

	public static void main(String[] args) {
		launch(args);
	}

	private Wumpus theGame;
	private MenuBar menuBar;
	private Observer currentView;
	private Observer textView;
	private Observer imageView;
	private BorderPane window;
    //set the size of the window
	public static final int width = 390;
	public static final int height = 550;
	/*---------------------------------------------------------------------
	  |  Method: start
	  |
	  |  Purpose:    A method needs to be override from Application, mainly set 
	  |              up the main components of the Wumpus GUI
	  |  
	  |  Parameters: Stage stage
	  |
	  |  Returns:    None
	  *-------------------------------------------------------------------*/
	@Override
	public void start(Stage stage) throws Exception {
		stage.setTitle("Wumpus");
		window = new BorderPane();
		Scene scene = new Scene(window, width, height);
		setupMenus();
		window.setTop(menuBar);
		initializeGameForTheFirstTime();

		textView = new TextView(theGame);
		imageView = new ImageView(theGame);
		theGame.addObserver(textView);
		theGame.addObserver(imageView);
		setViewTo(textView);

		stage.setScene(scene);
		stage.show();
	}
	/*---------------------------------------------------------------------
	  |  Method: setViewTo(Observer newView)
	  |
	  |  Purpose: Switch the view whenever user chooses
	  |            
	  |  Parameters: Observer newView
	  |
	  |  Returns:    None
	  *-------------------------------------------------------------------*/
	private void setViewTo(Observer newView) {
		window.setCenter(null);//avoid overlapping different views
		currentView = newView;
		window.setCenter((Node) currentView);

	}
	/*---------------------------------------------------------------------
	  |  Method: initializeGameForTheFirstTime()
	  |
	  |  Purpose: initialize the game
	  |            
	  |  Parameters: None
	  |
	  |  Returns:    None
	  *-------------------------------------------------------------------*/
	private void initializeGameForTheFirstTime() {
		theGame = new Wumpus();

	}
	/*---------------------------------------------------------------------
	  |  Method: setupMenus()
	  |
	  |  Purpose: Build the menu and set on action 
	  |            
	  |  Parameters: None
	  |
	  |  Returns:    None
	  *-------------------------------------------------------------------*/
	private void setupMenus() {

		MenuItem Text = new MenuItem("Text");
		MenuItem Drawing = new MenuItem("Drawing");
		Menu views = new Menu("Views");
		views.getItems().addAll(Text, Drawing);

		MenuItem newGame = new MenuItem("New Game");
		Menu options = new Menu("Options");
		options.getItems().addAll(newGame, views);

		menuBar = new MenuBar();
		menuBar.getMenus().addAll(options);

		MenuItemListener menuListener = new MenuItemListener();
		newGame.setOnAction(menuListener);
		Text.setOnAction(menuListener);
		Drawing.setOnAction(menuListener);
	}
	/*===============================================================
	|Class name:  MenuItemListener
	|
	|Description:  Handle the user click menu items event particular
	|              in this game
	*==============================================================*/
	private class MenuItemListener implements EventHandler<ActionEvent> {

		@Override
		public void handle(ActionEvent e) {
			String text = ((MenuItem) e.getSource()).getText();
			if (text.equals("Text"))
				setViewTo(textView);
			else if (text.equals("Drawing"))
				setViewTo(imageView);
			else if (text.equals("New Game"))
				theGame.startNewGame();

		}

	}

}

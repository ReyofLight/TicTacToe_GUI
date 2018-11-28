package application;
import javax.swing.GroupLayout.Alignment;

import com.sun.corba.se.impl.protocol.giopmsgheaders.Message;

/*
 * aSSIGNMENT: MAKE A GUI FOR EXISTING TIC TAC TOE CODE
 * This is where everything comes together. 
 * YOU STILL NEED TO ADD AN ANIMATION TO SLOW DOWN THE COMPUTER 
 */
import javafx.animation.Timeline;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;
import javafx.util.Duration;




public class PlayerInterface extends Application {
	
	ModifiedButton one = new ModifiedButton(0,0);
	ModifiedButton two = new ModifiedButton(1,0);
	ModifiedButton three = new ModifiedButton(2,0);
	ModifiedButton four = new ModifiedButton(0,1);
	ModifiedButton five = new ModifiedButton(1,1);
	ModifiedButton six = new ModifiedButton(2,1);
	ModifiedButton seven = new ModifiedButton(0,2);
	ModifiedButton eight = new ModifiedButton(1,2);
	ModifiedButton nine = new ModifiedButton(2,2);
	
	ModifiedButton[][] ugh = {{one, two, three} , {four, five, six} , {seven, eight, nine}};
	private Board game;
	private ComputerPlayer janet;
	protected static Symbol playerSymbol;
	private ToggleGroup pickSide;
	private GridPane organize;
	private RadioButton pickX = new RadioButton("X");
	private RadioButton pickO = new RadioButton("O");
	private String gameStatusMSG = "INACTIVE";
	private Board.Turn humanTurn;
	private Game playinNow;
	private Timeline delay;
	private String computerMessage;
	Button beginPlay;
	Text messageToPlayer;
	
	public void start(Stage primaryStage) {
		
		beginPlay = new Button("Start Game");
		beginPlay.setOnAction(e -> setUpGame(e));
		Button wipe = new Button("Begin Again");
		wipe.setOnAction(e -> wipe(e));
				
		pickSide = new ToggleGroup();
		pickSide.getToggles().addAll(pickX, pickO);
		pickX.setSelected(true);
		
		organize = new GridPane();
		organize.add(one, 0, 0);
		organize.add(two, 1, 0);
		organize.add(three, 2, 0);
		organize.add(four, 0, 1);
		organize.add(five, 1, 1);
		organize.add(six, 2, 1);
		organize.add(seven, 0, 2);
		organize.add(eight, 1, 2);
		organize.add(nine, 2, 2);
		organize.setAlignment(Pos.CENTER);
		organize.setHgap(10);
		organize.setVgap(10);
		one.setOnAction(e -> takeSpace(e));
		two.setOnAction(e -> takeSpace(e));
		three.setOnAction(e -> takeSpace(e));
		four.setOnAction(e -> takeSpace(e));
		five.setOnAction(e -> takeSpace(e));
		six.setOnAction(e -> takeSpace(e));
		seven.setOnAction(e -> takeSpace(e));
		eight.setOnAction(e -> takeSpace(e));
		nine.setOnAction(e -> takeSpace(e));
		for(int i = 0; i<3; i++) {
			for (int j = 0; j<3; j++) {
				ugh[i][j].setDisable(true);
			}
		}
		
		
		
		BorderPane layout = new BorderPane();
		layout.setCenter(organize);
		layout.setLeft(new VBox(beginPlay,new HBox(pickX, pickO), wipe));
		messageToPlayer = new Text(gameStatusMSG); 
		layout.setBottom(messageToPlayer);
		layout.setAlignment(messageToPlayer, Pos.TOP_CENTER);
		layout.setAlignment(organize, Pos.CENTER_RIGHT);
		layout.setAlignment(layout, Pos.CENTER);
		
		Scene gamePlay = new Scene(layout, 400, 400);
		gamePlay.getStylesheets().add("application/application.css");
		primaryStage.setScene(gamePlay);
		primaryStage.show();
		
	}
	
	
//	
	public void chooseSymbol() {
		//use the method
		if(pickSide.getSelectedToggle() == pickX) {
			playerSymbol = Symbol.EX;
			janet.setMark(Symbol.OH);
		}
		else {
			playerSymbol = Symbol.OH;
			janet.setMark(Symbol.EX);
		}
		game.setFirstTurn(playerSymbol);
	}
	
	public void takeSpace(ActionEvent e)  { //event handler for human
		ModifiedButton chosenOne = (ModifiedButton) e.getSource();
		int[] where = {chosenOne.getRow(), chosenOne.getCol()};
		chosenOne.setText(playerSymbol == Symbol.EX ? "X":"O");
		try {
			game.move(where, playerSymbol);
			chosenOne.setDisable(true);
			checkWin();
			game.changeWhoseTurn();
			
			takeSpace();
			checkWin();
			game.changeWhoseTurn();
		}
		catch(IllegalMoveException bad) {
			System.out.println("Can't go there");
		}
		
	}

	public void takeSpace() throws IllegalMoveException{ //backdoor for computer. shouldn't actually throw since the buttons disable themselves but just in case
			int[] chosen = janet.generateMove();
			game.move(chosen, janet.getMark());
			Button chosenOne = ugh[chosen[0]][chosen[1]];
			chosenOne.setText(janet.getSymbol());
			chosenOne.setDisable(true);
			
	}
	
	public void checkWin() {
		if(game.currentState != GameState.PLAYING) {
			setStatus(janet.gameOver(game.currentState));
			game.setState(GameState.INACTIVE);
			beginPlay.setDisable(false);
			for(int i = 0; i<3; i++) {
				for (int j = 0; j<3; j++) {
					ugh[i][j].setDisable(true);
				}
			}
		}
		else {
			int count = 0;
			for(int i = 0; i<3; i++) {
				for (int j = 0; j<3; j++) {
					if(ugh[i][j].isDisabled()) count++;
				}
			}
			if(count == 9) {
				setStatus("ISSA TIE");
			}
			else {
				game.changeWhoseTurn();
			}
			
		}
	}
	
	public void setStatus(String s) {
		messageToPlayer.setText(s);
	}
	
	public void setUpGame(ActionEvent e) {
		game = new Board();
		janet = new ComputerPlayer(game);
		chooseSymbol();
		humanTurn = playerSymbol == Symbol.EX ? Board.Turn.X_TURN: Board.Turn.O_TURN;
		beginPlay.setDisable(true);
		for(int i = 0; i<3; i++) {
			for (int j = 0; j<3; j++) {
				ugh[i][j].setText("");
				ugh[i][j].setDisable(false);
			}
		}
		setStatus("Playing!!");
	}
	public void setUpGame() {
		
		janet = new ComputerPlayer(game);
		chooseSymbol();
		humanTurn = playerSymbol == Symbol.EX ? Board.Turn.X_TURN: Board.Turn.O_TURN;
		for(int i = 0; i<3; i++) {
			for (int j = 0; j<3; j++) {
				ugh[i][j].setText("");
				ugh[i][j].setDisable(false);
			}
		}
		
	}
	
	
	public void wipe(ActionEvent e) {
		game.setState(GameState.INACTIVE);
		for(int i = 0; i<3; i++) {
			for (int j = 0; j<3; j++) {
				ugh[i][j].setText("");
				ugh[i][j].setDisable(false);
			}
		}
		game = new Board();
		janet = new ComputerPlayer(game);
		chooseSymbol();
		humanTurn = playerSymbol == Symbol.EX ? Board.Turn.X_TURN: Board.Turn.O_TURN;
		
		
	}
	
	public static void main(String[] args) {
		launch(args);
		
	}

}

package application;
import java.util.Scanner;

//import Board.Turn;

/*
 * Game.java is for testing and driving. COMMENT OUT AND EVENTUALLY DELETE OLD TEST STUFF.
 */
public class Game {
	protected static Symbol playerSymbol;
	private final static String X = "X";
	private final static String O = "O";
	
	public static void main(String[] args) throws IllegalMoveException{
		String hooman;
		String playAgain = "Y";
		Scanner scan = new Scanner(System.in);
		
		
		while (playAgain.equalsIgnoreCase("Y")) {
			System.out.println("I see you would like to play a game."
					+ "\nWe will be playing Tic Tac Toe. Choose your symbol: ");
			hooman = scan.next();
			
			
			//contingency if they fuck it up goes here
			Board ticTacToe = new Board();
			ComputerPlayer hal = new ComputerPlayer(ticTacToe);
			setSymbols(hooman, hal);
			System.out.println("Your mark is " + hooman + " and the computer will use " + hal.getSymbol());
			System.out.println(" As the human you  can go first.");
			System.out.println("The board looks like this : ");
			System.out.println(ticTacToe);
			ticTacToe.setFirstTurn(playerSymbol);
			Board.Turn humanTurn = ticTacToe.getTurn();
			
			while(ticTacToe.currentState == GameState.PLAYING) {
				//whose turn is it
				if(ticTacToe.getTurn() == hal.myTurn) {//is computer's turn
					ticTacToe.move(hal.generateMove(), hal.getMark());
					System.out.println("and now for my turn...");
					ticTacToe.showBoard();
				}
				if(ticTacToe.getTurn() == humanTurn) {//isHumanTurn
					ticTacToe.humanTurn();
					ticTacToe.showBoard();
				}
				ticTacToe.changeWhoseTurn();
			}
			hal.gameOver(ticTacToe.currentState);
			System.out.println("Would you like to play again?");
			playAgain = scan.next();
		}
		
		
		System.out.println("OK! GREAT! WE'RE DONE! NOW LET ME TAKE A NAP!"
				+ "\nor...like...at least close all those tabs of chrome....");
		
			
	}
	
	
	public static Symbol getMark() {
		return playerSymbol;
	}
	
	public static void setSymbols(String s, ComputerPlayer puter) { //takes player symbol, sets both 
		if (s.equalsIgnoreCase(X)) {
			playerSymbol = Symbol.EX;
			puter.setMark(Symbol.OH);
		}
		else {
			if(s.equalsIgnoreCase(O)) {
				playerSymbol = Symbol.OH;
				puter.setMark(Symbol.EX);
			}
			else {
				System.out.println("Please don't be a smart aleck, just pick x or o");
			}
		}
	}
	

}

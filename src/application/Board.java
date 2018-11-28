package application;
/*
 * Board.java. Important notes below
 * TOSTRING PRINTS BLANK BOARD WITH COORDINATES
 * 
 */
import java.util.Scanner;
public class Board {
	private Cell[][] data = new Cell[3][3];
	protected GameState currentState;
	private int emptyCells;
	public enum Turn {X_TURN, O_TURN, NOT_YET};
	private Turn whoseTurn;
	Scanner scan = new Scanner(System.in);
	private final int three = 3;
	//private Cell lastMove;
	public Board() {
		for (int i = 0; i<data.length; i++) {  
			for (int j = 0; j<data.length; j++) {
				data[i][j] = new Cell();
			}
		}//END loop
		currentState = GameState.PLAYING;
		emptyCells = Cell.countEmpty();
		whoseTurn = Turn.NOT_YET;
	}
	public Cell[][] getArray(){
		return data;
	}
	public Cell getCell(int row, int col) {
		return data[row][col];
	}
	public void setState(GameState current) {
		currentState = current;
	}
	public void setFirstTurn(Symbol human) {//allows game to always make human go first
		switch (human) {
		case EX : 
			whoseTurn = Turn.X_TURN;
			break;
		case OH : 
			whoseTurn = Turn.O_TURN;
			break;
		case EMPTY : 
			System.out.println("wHY WOULD YOU DO THAT?");
			break;
		}
		
	}
	public void changeWhoseTurn() {//switch whose turn after they move 
		Turn nextUp = Turn.NOT_YET;
		switch(whoseTurn) {
		case X_TURN : 
			nextUp = Turn.O_TURN;
			break;
		case O_TURN: 
			nextUp = Turn.X_TURN;
			break;
		case NOT_YET : 
			break;
		}
		whoseTurn = nextUp;
	}
	public Turn getTurn() { //answer to questino whose turn is it
		return whoseTurn;
	}
	//------------------------------------
	//THE ALL IMPORTANT MOVE METHOD
	//------------------------------------
	public void move (int[] coord, Symbol s) throws IllegalMoveException{ 
		int row = coord[0];
		int col = coord[1];
		RulesEngine.checkValid(this.getCell(row, col), row, col);
		data[row][col].draw(s);
		setState(RulesEngine.checkState(this, coord));
	}
	//------------------------------------
	public String getValidMoves() {
		String available = "Valid Moves Remaining (row, column): ";
		for(int i = 0; i < three; i++) {
			for(int j = 0; j < three; j++) {
				if(data[i][j].isEmpty() == true) {
					available += "(" + i +", " + j + ") , ";
				}
			}
		}
		return available;
	}
	public int getEmptyCells() {
		return emptyCells;
	}
	public void humanTurn() { //steps human thru making a move and makes it
		System.out.println(getValidMoves());
		int[] where = new int[2];
		System.out.println("Human player, make your move. Enter the ROW of your desired move:");
		where[0] = scan.nextInt();
		System.out.println("Now the COLUMN: ");
		where[1] = scan.nextInt();
		try {
			RulesEngine.checkValid(this.getCell(where[0], where[1]), where[0], where[1]);
			move(where, Game.getMark());
		}
		catch(IndexOutOfBoundsException | IllegalMoveException e) {
			//do something, see what hapen 
			System.out.println("Looks like you messed up, wanna try again? Remember, "
					+ "you gotta play within the field, and don't pick spaces that are "
					+ "already full. ");
			System.out.println("Specific thing you did wrong: " + e.getMessage());
			humanTurn();
		}
	}
	public void showBoard() { //SHOWS CURRENT BOARD
		for(int i = 0; i<data.length; i++) {
			String row = data[i][0] + "|" + data[i][1] + "|" + data[i][2];
			System.out.println(row);
			if (i != 2) {
				System.out.println("---------------");
			}
		}
	}
	
	public String toString(){ // PRINTS BLANK BOARD
		String blankBoard = " 0,0 | 0,1 | 0,2\n"
				+ "---------------\n"
				+ " 1,0 | 1,1 | 1,2\n"
				+ "---------------\n"
				+ " 2,0 | 2,1 | 2,2 ";
		return blankBoard;
	}

}

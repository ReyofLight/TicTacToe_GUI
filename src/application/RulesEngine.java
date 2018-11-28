package application;
import java.util.ArrayList;
public abstract class RulesEngine {
	private static IllegalMoveException OutOfBoundsEx = new IllegalMoveException("Your move is not allowed. Reason: Move falls outside of grid.");
	private static IllegalMoveException NotEmptyEx = new IllegalMoveException("Your move is not allowed. Reason: The space entered is already full/in play.");
	
	public static void checkValid(Cell c, int row, int col) throws IllegalMoveException {
		if(row>2||row<0||col>2||col<0)
			throw OutOfBoundsEx;
		if (c.isEmpty() != true) {
			throw NotEmptyEx;
		}
	}
	
	public void gameOver() {
		Cell.reset();
	}
	public static GameState checkWin(int [] move, Board playfield) {
		Symbol checkFor = playfield.getCell(move[0], move[1]).getMark();
		GameState newState = playfield.currentState; //instantiated to current state
		if (checkRow(move[0], checkFor, playfield) == true) {
			newState = whoWon(checkFor);
		}
		else {
			if(checkColumn(move[1], checkFor, playfield) == true) {
				newState = whoWon(checkFor);
			}
			else {
				//CHECKS DIAG FROM TOP LEFT TO BOTTOM RIGHt
				if(move[0] == move[1]) { 
					int howMany = 0;
					for(int i = 0; i<3;i++) {
						if(playfield.getCell(i, i).getMark() == checkFor) {
							howMany++;
						}
					}
					newState = howMany == 3? whoWon(checkFor): playfield.currentState;
				}
				//CHECKS DIAG FROM TOP RIGHT TO BOTTOM LEFT
				if((move[0] == 1 && move[1] == 1) ||
						(move[0] == 2 && move[1] == 0) || (move[0] == 0 && move[1] == 2)) {
					int howMany = 0;
					howMany += playfield.getCell(1, 1).getMark() == checkFor ? 1 : 0;
					howMany += playfield.getCell(2, 0).getMark() == checkFor ? 1 : 0;
					howMany += playfield.getCell(0, 2).getMark() == checkFor ? 1 : 0;
					newState = howMany == 3? whoWon(checkFor): playfield.currentState;
				}
			}
		}
		return newState;
	}
	public static GameState whoWon(Symbol s) {
		switch(s) {
		case EX : 
			return GameState.X_WON;
		case OH : 
			return GameState.O_WON;
		case EMPTY: 
			return GameState.PLAYING;
		default: 
			return GameState.PLAYING;
		}
	}
	public static boolean checkRow(int numRow, Symbol look, Board board) {
		boolean threeInRow = false;
		Cell one= board.getCell(numRow, 0);
		Cell two = board.getCell(numRow, 1);
		Cell three = board.getCell(numRow, 2);
		if(one.getMark() == look && two.getMark() == look
				&& three.getMark() == look) {
			threeInRow = true;
		}
		return threeInRow;
	}
	public static boolean checkColumn(int numCol, Symbol look, Board board) {
		boolean threeInCol = false;
		Cell one = board.getCell(0, numCol);
		Cell two = board.getCell(1, numCol);
		Cell three = board.getCell(2, numCol);
		if(one.getMark() == look && two.getMark() == look
				&& three.getMark() == look) {
			threeInCol = true;
		}
		return threeInCol;
	}
	public static GameState checkState(Board b, int[] lastMove) {
		GameState newState = b.currentState;
		if (b.getEmptyCells() != 0) {
			newState = checkWin(lastMove, b);
		}
		else {
			if(b.getEmptyCells() == 0 && b.currentState == GameState.PLAYING) {
				if (checkWin(lastMove, b) == GameState.PLAYING) {
					newState = GameState.DRAW;
				}
			}
		}
		return newState;
	}
	
	
	

}

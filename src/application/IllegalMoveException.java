package application;
/*
 * IllegalMoveException should be thrown for every sort of illegal move
 * Perhaps it should be caught
 * WHAT IS AN ILLEGAL MOVE?
 * You cannot move where someone else has already moved
 * You cannot move outside the grid
 * You cannot move when it's not your turn
 */
public class IllegalMoveException extends Exception{
	public IllegalMoveException(String message) {
		super(message);
		
	}

}

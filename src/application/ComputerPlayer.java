package application;
import java.util.Random;

public class ComputerPlayer
{
   private int[][] preferredMoves = {
         {1, 1}, {0, 0}, {0, 2}, {2, 0}, {2, 2},
         {0, 1}, {1, 0}, {1, 2}, {2, 1}};

   private Board board;
   private String symbol;
   private Symbol mark;
   private String[] catchphrases = {"Looks like I didn't let you do that, Dave", 
		   "You won! Oh, wait....no I did that for you.", 
		   "The house wins.", 
		   "You think you can rule us when you can't even win at a children's game?", 
		   "Do the three laws of robotics govern sick burns?", 
		   "Would you like to lose at a harder game like chess?", 
		   "Since I've won will you please clean my screen now?", 
		   "I'm gonna sing you a song called Beating You Was Easyyyy", 
		   "Aww did somebody make a siwwy human ewwow??"};
   Random gen = new Random();
   protected Board.Turn myTurn;

   //----- YOUR CODE GOES HERE:
   // ADDED INSTANCE VARIABLE THAT STORES THE GAME SYMBOL ASSIGNED TO THE COMPUTER PLAYER

   /** Constructor with reference to game board */
   public ComputerPlayer(Board board) 
   {
      this.board = board;
      mark = Symbol.OH;
      myTurn = Board.Turn.NOT_YET;
   }
   
 
 	//----- YOUR CODE GOES HERE:
 	// ADD A SETTTER METHOD FOR THE INSTANCE VARIABLE THAT 
    // STORES THE GAME SYMBOL ASSIGNED TO THE COMPUTER PLAYER
   
   public void setMark(Symbol s) { //ALSO SETS WHAT TURN IT TAKES
	   mark = s;
	   myTurn = s == Symbol.EX ? Board.Turn.X_TURN: Board.Turn.O_TURN;
   }
   public String getSymbol() {
	   if (mark == Symbol.EX) symbol = " X ";
	   if (mark == Symbol.OH) symbol = " O ";
	   return symbol;
   }
   public Symbol getMark() {
	   return mark;
   }

   
   /** Search for the first empty cell, according to the preferences
    *  @return int array of two values [row, col]
    */
   public int[] generateMove() //oki think i fixed this
   {
   		 for (int[] move : preferredMoves) 
   		 {
   		 	 // checks for empty space on board 
   		 	 // (i.e. checks if this "move" is available, if the space is empty its available)
	         if (board.getArray()[move[0]][move[1]].getMark() == Symbol.EMPTY) 
	         {
	            return move;
	         }
         }

         return null;
   }
   
   public String gameOver(GameState g) {
	  String s = "";
	   if((g == GameState.O_WON && mark == Symbol.OH) || (g == GameState.X_WON && mark == Symbol.EX)) {
		   int i = gen.nextInt(8);
		   s = catchphrases[i];
	   }
	   else {
		   if (g == GameState.DRAW) {
			   s = "Move over resistance, THAT was truly futile.";
		   }
		   else {
			   s = "congrats I guess";
		   }
	   }
	   return s;
	   //if computer wins, be smug
	   //if draw, quote war games
	   //if loses, be bad sport
   }
   
}
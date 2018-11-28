package application;
/*
 * 
 */
public class Cell {
	//private boolean filled;
	private String filledWith;
	private Symbol mark;
	private static int empty;
	public Cell() {
		mark = Symbol.EMPTY;
		filledWith = "    ";
		empty ++;
	}
	public void draw(Symbol xo)  {
		mark = xo;
		setFill();
		empty--;
	}
	public boolean isEmpty() {
		if(mark == Symbol.EMPTY) {
			return true;
		}
		else
			return false;
	}
	public Symbol getMark() {
		return mark;
	}
	public static int countEmpty() {
		return empty;
	}
	public void setFill() {
		if(mark == Symbol.EX) filledWith = " X ";
		if(mark == Symbol.OH) filledWith = " O ";
	}
	public String toString() {
		return filledWith;
	}
	public static void reset() {
		empty = 0;
	}

}

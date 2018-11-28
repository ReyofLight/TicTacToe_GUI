package application;

import javafx.geometry.Insets;
/*
 * A button that knows where it lives
 * I'm gonna use it to make a collection of sorts
 * 
 */
import javafx.scene.control.Button;
import javafx.scene.text.Font;

public class ModifiedButton extends Button {
	private int myRow;
	private int myColumn;
	
	public ModifiedButton() {
		super();
		setText(" ");
		setMinSize(40, 40);
		setMaxSize(100, 100);
		getStyleClass().add("mod-button");
	}
	public ModifiedButton(int col, int row) {
		myRow = row;
		myColumn = col;
	}
	public int getCol() {
		return myColumn;
		
	}
	public int getRow() {
		return myRow;
	}
	public void setWidth(Double value) {
		super.setWidth(value);
	}
	public void setHeight(Double value) {
		super.setHeight(value);
	}
	

}

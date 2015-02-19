package me.jacob.macdougall.gui;

public class CheckBoxes extends GUI_Objects {
	
	String name;
	private boolean isChecked = false;
	
	public CheckBoxes(String name, int x, int y, int width, int height, boolean isChecked) {
		super(name, x, y, width, height);
		this.isChecked = isChecked;
	}
	
	public boolean isChecked() {
		return isChecked;
	}
	
	/**
	 * Toggles whether or not the check box is checked.
	 */
	public void Toggle() {
		isChecked = !isChecked; // Whatever isChecked is currently at, reverse it
	}
	
}

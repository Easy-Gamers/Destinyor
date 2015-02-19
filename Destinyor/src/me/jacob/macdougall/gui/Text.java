package me.jacob.macdougall.gui;

public class Text {
	private int cursor;
	private String text;
	
	public Text(String text, int cursor) {
		this.text = text;
		this.cursor = cursor;
	}
	
	public Text(String text) {
		this.text = text;
		this.cursor = text.length();
	}
	
	public Text() {
		this.text = "";
		this.cursor = 0;
	}
	
	public void add(char character) {
		if(cursor == text.length()) {
			text += character;
			cursor++;
		} else {
			String firstHalf = "";
			
			for(int i = 0; i <= cursor; i++) {
				firstHalf += text.charAt(i);
			}
			
			firstHalf += character;
			
			String secondHalf = "";
			
			for(int i = cursor; i < text.length(); i++) {
				secondHalf += text.charAt(i);
			}
			
			text = firstHalf + secondHalf;
		}
	}
	
	public void remove() {
		if(cursor == text.length()) {
			text = (String) text.subSequence(0, text.length() - 1);
			cursor--;
		} else {
			String firstHalf = (String) text.subSequence(0, cursor);
			String secondHalf = (String) text.subSequence(cursor + 1, text.length());
			text = firstHalf + secondHalf;
		}
	}
	
	public void moveLeft() {
		cursor--;
	}
	
	public void moveRight() {
		cursor++;
	}
	
	public String getText() {
		return text;
	}
}

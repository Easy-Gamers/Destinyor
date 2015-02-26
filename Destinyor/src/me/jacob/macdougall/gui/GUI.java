package me.jacob.macdougall.gui;

import graphic.engine.screen.Screen;
import input.engine.mouse.Mouse;

import java.util.HashMap;
import java.util.Map;

import me.jacob.macdougall.graphics.Sprites;

public class GUI {
	// Do something? Why did I make this class...
	// For each menu screen make a new GUI object
	// IE Options Main
	
	public Map<String, Buttons> buttons = new HashMap<>();
	public Map<String, TextBox> textBoxes = new HashMap<>();
	public Map<String, DropDowns> dropDowns = new HashMap<>();
	public Map<String, CheckBoxes> checkBoxes = new HashMap<>();
	
	public GUI_Objects focused = null;
	
	public String name = "";
	public int id;
	
	public GUI(String name, int id) {
		this.name = name;
		this.id = id;
	}
	
	public void add(Buttons... buttons) {
		for(Buttons button : buttons) {
			this.buttons.put(button.getName(), button);
		}
	}
	
	public void add(GUI_Objects... objects) {
		
	}
	
	public void add(CheckBoxes... checkBoxes) {
		for(CheckBoxes checkbox : checkBoxes) {
			this.checkBoxes.put(checkbox.getName(), checkbox);
		}
	}
	
	public void add(TextBox... textBoxes) {
		for(TextBox textBox : textBoxes) {
			this.textBoxes.put(textBox.getName(), textBox);
		}
	}
	
	public void add(DropDowns... dropDowns) {
		for(DropDowns dropDown : dropDowns) {
			this.dropDowns.put(dropDown.getName(), dropDown);
		}
	}
	
	public void update(Mouse mouse) {
		for(Buttons button : buttons.values()) {
			if(button.isEnabled()) {
				if(button.inBox(mouse.getPressed(Mouse.X), mouse.getPressed(Mouse.Y), mouse.getReleased(Mouse.X), mouse.getReleased(Mouse.Y))) {
					button.setSprite(Sprites.getSprite(Sprites.BUTTON, 0, 0));
					button.setFocused(true);
					focused = button;
				} else if(button.inBox(mouse.getPressed(Mouse.X), mouse.getPressed(Mouse.Y)) && mouse.isPressed()) {
					button.pressed();
				} else if(!button.inBox(mouse.getReleased(Mouse.X), mouse.getReleased(Mouse.Y)) && !mouse.isPressed()) {
					button.setSprite(Sprites.getSprite(Sprites.BUTTON, 0, 0));
				}
					
			}
		}
		for(TextBox textbox : textBoxes.values()) {
			if(textbox.isEnabled())
				if(textbox.inBox(mouse.getPressed(Mouse.X), mouse.getPressed(Mouse.Y), mouse.getReleased(Mouse.X), mouse.getReleased(Mouse.Y))) {
					textbox.setFocused(true);
					focused = textbox;
				}
		}
		for(DropDowns dropDown : dropDowns.values()) {
			if(dropDown.isEnabled()) {
				dropDown.update(mouse);
			}
		}
		for(CheckBoxes checkbox : checkBoxes.values()) {
			if(checkbox.isEnabled()) {
				if(checkbox.inBox(mouse.getPressed(Mouse.X), mouse.getPressed(Mouse.Y), mouse.getReleased(Mouse.X), mouse.getReleased(Mouse.Y))) {
					checkbox.Toggle();
					focused = checkbox;
				}
			}
		}
	}
	
	public void render(Screen screen) { 
		for(Buttons button : buttons.values()) {
			button.render(screen);
		}
		for(TextBox textbox : textBoxes.values()) {
			textbox.render(screen);
		}
		for(DropDowns dropDown : dropDowns.values()) {
			dropDown.render(screen);
		}
		for(CheckBoxes checkbox : checkBoxes.values()) {
			checkbox.render(screen);
		}
	}
	
	public void update() {
		
	}
	
	public void reset() {
		focused = null;
	}
	
	
}

package me.jacob.macdougall.gui;

import input.engine.mouse.Mouse;
import me.jacob.macdougall.Destinyor;
import me.jacob.macdougall.Time;
import me.jacob.macdougall.input.Keys;
import graphic.engine.screen.Screen;

public class DropDowns extends GUI_Objects {

	private Buttons[] options;
	@SuppressWarnings("unused")
	private ScrollBar scroll; // For later use
	private int amount;
	
	private int location;
	
	public DropDowns(String name, int x, int y, int width, int height, int amountPerScroll, int startingLocation, Buttons... options) {
		super(name, x, y, width, height);
		this.options = options;
		amount = amountPerScroll;
		location = startingLocation;
		for(Buttons button : options) {
			button.x = x;
			button.y = y;
		}
	}
	
	public void render(Screen screen) {
		if(focused) {
			for(int i = 0; i < amount; i++) {
				Buttons button = options[selected(amount)[i]]; 
				button.y = (y - 21) + (21 * (i));
				button.render(screen);
			}
		} else {
			getCurrent().y = y;
			getCurrent().render(screen);
		}
	}
	
	public void updateClick(Mouse mouse) {
		if(Time.getKeyTimer(10, false)) {
			for(int i = 0; i < amount; i++) {
				Buttons button = options[selected(amount)[i]];
				if(button.inBox(mouse.getPressed(Mouse.X), mouse.getPressed(Mouse.Y))) {
					Time.resetKeyTimer();
					location = selected(amount)[i]; // Current location + -1 || 0 || 1
					focused = false;
					mouse.resetPressedPos();
					mouse.resetReleasedPos();
					Destinyor.refresh();
				}
			}
		}
	}
	
	public void setselected(Mouse mouse) {
		
	}
	
	public void setSelected(Mouse mouse) {
		if(!mouse.getMouseWheelMoved()) {
			if(Time.getKeyTimer(10, false)) {
				if(Keys.MoveDown()) {
					location++;
					Time.resetKeyTimer();
				}
				if(Keys.MoveUp()) {
					location--;
					Time.resetKeyTimer();
				}
			}
		} else {
			location += mouse.getMouseWheel();
			if(location >= options.length)
				location = 0;
			if(location <= -1)
				location = options.length - 1;
			mouse.setMouseWheelMoved(false);
		}
	}
	
	private int[] selected(int amount) {
		if(location <= -1)
			location = options.length - 1;
		
		if(location >= options.length)
			location = 0;
		
		int[] selectedButtons = new int[amount];
		for(int i = -1; i < amount - 1; i++) {
			if(location + i >= 0) {
				if(location + i <= options.length - 1) {
					selectedButtons[i + 1] = location + i;
				} else {
					selectedButtons[i + 1] = 0;
				}
			} else {
				selectedButtons[i + 1] = options.length - 1;
			}
		}

		return selectedButtons;
	}
	
	/**
	 * Get the current selected button
	 * @return the current selected button
	 */
	public Buttons getCurrent() {
		return options[location];
	}
	
	public void update(Mouse mouse) {
		if(focused) {
			setSelected(mouse);
			updateClick(mouse);
		} else {
			if(inBox(mouse.getPressed(Mouse.X), mouse.getPressed(Mouse.Y))) {
				focused = true;
				
				mouse.resetPressedPos();
			}
		}
	}
	
}

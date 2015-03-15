package me.jacob.macdougall.gui;

import input.engine.mouse.Mouse;
import me.jacob.macdougall.ArrayHandler;
import me.jacob.macdougall.Destinyor;
import me.jacob.macdougall.Time;
import me.jacob.macdougall.input.Keys;
import graphic.engine.screen.GameFont;
import graphic.engine.screen.Screen;

public class DropDowns extends GUI_Objects {

	//private GUI_Objects[] options;
	
	private ArrayHandler ah = new ArrayHandler();
	
	private int amount;
	private int location;
	private int space;
	
	private boolean clickable = true;
	private boolean alwaysFocused = false;
	private boolean moveOnLocationChange = true; // Move if when location is changed?
	
	
	public DropDowns(String name, int x, int y, int width, int height, int amountPerScroll, int startingLocation, GUI_Objects... options) {
		super(name, x, y, width, height);
		for(GUI_Objects objects : options) {
			objects.x = x;
			objects.y = y;
			ah.add(objects);
		}
		
		//this.options = options;
		amount = amountPerScroll;
		location = startingLocation;
		space = 1;
		//ah = new ArrayHandler();
	}
	
	public void add(GUI_Objects option) {
		ah.add(option);
	}
	
	public void remove(GUI_Objects option) {
		ah.remove(option);
	}
	
	public void remove(int i) {
		ah.remove(0, i);
	}
	
	public void alwaysFocus() {
		alwaysFocused = true; // If this method is called than set it to always be focused when rendered
	}
	
	public void dontMoveOnLocationChange() {
		moveOnLocationChange = false;
	}
	
	public void setClickable(boolean clickable) {
		this.clickable = clickable;
	}
	
	public void setSpace(int space) {
		this.space = space;
	}
	
	public void render(Screen screen) {
		if(focused || alwaysFocused) {
			render(screen, amount);
		} else {
			render(screen, 1);
		}
	}
	
	private void render(Screen screen, int amount) {
		for(int i = 0; i < amount; i++) {
			if(amount <= 1) {
				i = 1;
			}
			GUI_Objects option;
			if(moveOnLocationChange)
				option = (GUI_Objects) ah.get(0, selected(i));//options[selected(i)];
			else
				option = (GUI_Objects) ah.get(0, i); //options[i];
			int tempHeight = option.height + space;
			option.y = (y - tempHeight) + (tempHeight * i);
			if(option.getSprite() != null) {
				option.masterRender(screen);
				int x1 = (int) ((option.width / 2.5) - (option.getName().length() * 2)) + option.x;
				int y1  = option.y + 6;
				GameFont.render(option.getName(), screen, x1, y1);
			} else {
				GameFont.render(option.getName(), screen, option.x, option.y);
			}
		}
	}
	
	public void updateClick(Mouse mouse) {
		if(Time.getKeyTimer(10, false)) {
			for(int i = 0; i < amount; i++) {
				GUI_Objects option;
				if(moveOnLocationChange)
					option = (GUI_Objects) ah.get(0, selected(i));//options[selected(i)];
				else
					option = (GUI_Objects) ah.get(0, i); //options[i];
				int tempHeight = option.height + space;
				option.y = (y - tempHeight) + (tempHeight * i);
				if(option.inBox(mouse.getPressed(Mouse.X), mouse.getPressed(Mouse.Y))) {
					Time.resetKeyTimer();
					if(clickable && moveOnLocationChange)
					location = selected(i); // Current location + -1 || 0 || 1
					else if(!moveOnLocationChange) {
						location = i;
					}
					focused = alwaysFocused;
					mouse.resetPressedPos();
					mouse.resetReleasedPos();
					Destinyor.refresh();
				}
			}
		}
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
				if(location >= ah.getLength(0)) {
					location = 0;
				}
				if(location <= -1) {
					location = ah.getLength(0) - 1;
				}
			}
		} else {
			location += mouse.getMouseWheel();
			if(location >= ah.getLength(0))
				location = 0;
			if(location <= -1)
				location = ah.getLength(0) - 1;
			mouse.setMouseWheelMoved(false);
		}
	}
	
	public int selected(int i) {
		return selected()[i];
	}
	
	private int[] selected() {
		if(location <= -1)
			location = ah.getLength(0) - 1;
	
		if(location >= ah.getLength(0))
			location = 0;
		
		int[] selectedButtons = new int[amount];
		GUI_Objects option = (GUI_Objects) ah.get(0,0);
		if(option.getSprite() != null) {
			for(int i = -1; i < amount - 1; i++) {
				if(location + i >= 0) {
					if(location + i <= ah.getLength(0) - 1) {
						selectedButtons[i + 1] = location + i;
					} else {
						selectedButtons[i + 1] = 0;
					}
				} else {
					selectedButtons[i + 1] = ah.getLength(0) - 1;
				}
			}
		} else {
			for(int i = 0; i < amount; i++) {
				if(location + i > 0)
				if(location + i <= ah.getLength(0) - 1) {
					selectedButtons[i] = location + i;
				} else {
					selectedButtons[i] = 0 + i;
				}
				else
					selectedButtons[i] = 0;
			}
		}
		return selectedButtons;
	}
	
	/**
	 * Get the current selected option
	 * @return the current selected button
	 */
	public GUI_Objects getCurrent() {
		return (GUI_Objects) ah.get(0, location);
	}
	
	public int getCurrentLocation() {
		return location;
	}
	
	public int getLength() {
		return ah.getLength(0);
	}
	
	public void update(Mouse mouse) {
		if(focused || alwaysFocused) {
			setSelected(mouse);
			updateClick(mouse);
		} else {
			if(inBox(mouse.getPressed(Mouse.X), mouse.getPressed(Mouse.Y), mouse.getReleased(Mouse.X), mouse.getReleased(Mouse.Y))) {
				focused = true;
				mouse.reset();
			}
		}
	}
	
}

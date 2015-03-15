package me.jacob.macdougall.gui;

import me.jacob.macdougall.ArrayHandler;
import graphic.engine.screen.Screen;
import input.engine.mouse.Mouse;

public class Scroller extends GUI_Objects {

	// A class that contains stuff that has a scroll bar
	private Scroll scrollbar;
	
	private ArrayHandler ah;
	
	private int aps;
	
	private int min, max;
	
	private int space = 0;
	
	public Scroller(String name, int x, int y, int width, int height, int aps, int direction, GUI_Objects... objects) {
		super(name, x, y, width, height);
		ah = new ArrayHandler();
		for(int i = 0; i < objects.length; i++) {
			ah.add(objects[i]);
		}
		this.min = 0;
		this.max = objects.length - aps + 1;
		scrollbar = new Scroll(name + "Scrollbar", x + width + 10, y, 10, 20, direction, x + width + 10, min, x + width + 10, max - 1);
		this.aps = aps;
		//public Scroll(String name, int x, int y, int width, int height, int direction, int xMin, int yMin, int xMax, int yMax) {
	}	
	
	public void setAmountPerScroll(int aps) {
		this.aps = aps;
	}
	
	public void setSpace(int space) {
		this.space = space;
	}
	/**
	 * 
	 * @param minY How many units below the centre will it render for?
	 * @param maxY How many units above the centre will it render for?
	 */
	public void setDefaults(int min, int max) {
		this.min = min;
		this.max = max;
	}
	
	public void update(Mouse mouse) {
		scrollbar.update(mouse);
		updateMouse(mouse);
		updateKeyboard();
	}
	
	public void add(GUI_Objects... objects) {
		for(GUI_Objects object : objects) {
			ah.add(object);
		}
			
			max += objects.length;
	}
	
	public void remove(GUI_Objects object) {
		ah.remove(object);
	}
	
	public void remove(int i) {
		ah.remove(0, i);
	}
	
	public void clear() {
		ah.clear();
	}
	
	private void updateMouse(Mouse mouse) {
		for(GUI_Objects object : getObjects()) {
			if(object.inBox(mouse.getPressed(Mouse.X), mouse.getPressed(Mouse.Y), mouse.getReleased(Mouse.X), mouse.getReleased(Mouse.Y))) {
				this.setFocused(true);
				object.setFocused(true);
			}
		}
	}
	
	private void updateKeyboard() {
		
	}
	
	public void render(Screen screen) {
		GUI_Objects[] objects = getObjects();
			if(objects != null) {
			for(int i = 0; i < objects.length; i++) {
				objects[i].x = x;
				objects[i].y = y + (height * i) + (space * i);
				objects[i].masterRender(screen);
			}
			scrollbar.render(screen);
		}
	}
	
	private GUI_Objects[] getObjects() {
		ArrayHandler temp = new ArrayHandler();
		for(int i = 0; i < aps; i++) {
			try {
				temp.add(ah.get(0, i + getScrollbar()));
			} catch(ArrayIndexOutOfBoundsException e) {
				break;
			}
		}
		GUI_Objects[] returner = null;
		if(temp.get() != null) {
			//GUI_Objects[] returner = new GUI_Objects[temp.getLength(0)];
			returner = new GUI_Objects[temp.getLength(0)];
			for(int i = 0; i < returner.length; i++) {
				returner[i] = (GUI_Objects) temp.get(0, i);
			}
		}
		return returner;
	}
	
	private int getScrollbar() {
		//System.out.println(scrollbar.location());
		if(scrollbar.location() > getMin()) 
			return (scrollbar.location() < getMax()) ? scrollbar.location() : getMax() - 1;
		else
			return (scrollbar.location() > getMin()) ? scrollbar.location() : getMin();
	}
	
	private int getMin() {
		return min;
	}
	
	private int getMax() {
		return max;
	}
	
	public GUI_Objects getCurrent() {
		return (GUI_Objects) ah.get(0, getScrollbar());
	}
	
	public GUI_Objects[] getAllCurrent() {
		return getObjects();
	}
	
	public int getLocation() {
		return getScrollbar();
	}
	
	public GUI_Objects getCurrentFocused() {
		for(int i = 0; i < ah.getLength(0); i++) {
			GUI_Objects obj = (GUI_Objects) ah.get(0, i);
			if(obj.focused) {
				return obj;
			}
		}
		return null;
	}
	
	public int getLength() {
		return ah.getLength(0);
	}
	
	public GUI_Objects get(int i) {
		return (GUI_Objects) ah.get(0, i);
	}
	
}

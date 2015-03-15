package me.jacob.macdougall.gui;

import java.awt.Color;

import me.jacob.macdougall.Time;
import me.jacob.macdougall.input.Keys;
import graphic.engine.screen.Bitmap;
import graphic.engine.screen.Screen;
import input.engine.mouse.Mouse;

public class Scroll extends GUI_Objects {

	public static final int X = 0, Y = 1;
	
	private int xMin, yMin;
	private int xDefault, yDefault;
	private int xMax, yMax;
	private int direction;
	
	private Bitmap scroll;
	
	private int location = 0;
	
	public Scroll(String name, int x, int y, int width, int height, int direction, int xMin, int yMin, int xMax, int yMax) {
		super(name, x, y, width, height);
		this.xMin = xMin;
		this.yMin = yMin;
		xDefault = x;
		yDefault = y;
		this.xMax = xMax;
		this.yMax = yMax;
		this.direction = direction;
		scroll = new Bitmap(width, height);
		for(int i = 0; i < scroll.pixels.length; i++) {
			scroll.pixels[i] = new Color(scroll.pixels[i]).getRGB();
		}
	}
	
	public void render(Screen screen) {
		masterRender(screen);
		screen.render(scroll, xMin, yMin);
	}
	private void updateMouse(Mouse mouse) {
		if(direction == X) {
			if(location > xMin && location < xMax) {
				int move = mouse.getMouseWheel();
				x += move * ((int) (width * ((float) xMax / 5)));
				location += move;
				System.out.println("Moving: " + mouse.getMouseWheel());
				mouse.reset();
			}
		} else {
			int move = mouse.getMouseWheel();
			switch(move) {
				case 0: break;
				case 1: 
					 if(location < yMax) {
						y += move * ((int) (height * ((float) yMax / 5)));
						location += move;
					}
					 mouse.resetMouseWheel();
					 break;
				case -1: 
					if(location > yMin) {
						y += move * ((int) (height * ((float) yMax / 5)));
						location += move;
					}
					mouse.resetMouseWheel();
					break;
			}
		}
	}
	
	private void updateKeyboard() {
		if(Time.getKeyTimer(10, false))
		if(direction == X) {
			if(Keys.MoveLeft() && location > xMin) {
				x -= (int) (width * ((float) xMax / 5));
				location--;
				Time.resetKeyTimer();
			}
			if(Keys.MoveRight() && location < xMax) {
				x += (int) (width * ((float) xMax / 5));
				location++;
				Time.resetKeyTimer();
			}
		} else {
			if(Keys.MoveUp() && location > yMin) {
				y -= (int) (height * ((float) yMax / 5));
				location--;
				Time.resetKeyTimer();
			}
			if(Keys.MoveDown() && location < yMax) {
				y += (int) (height * ((float) yMax / 5));
				location++;
				Time.resetKeyTimer();
			}
		}
	}
	
	public void update(Mouse mouse) {
		updateMouse(mouse);
		updateKeyboard();
	}
	
	public int location() {
		return location;
	}
	
	public int getDirection() {
		return direction;
	}
	
}

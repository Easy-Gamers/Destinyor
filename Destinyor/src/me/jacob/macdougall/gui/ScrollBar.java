package me.jacob.macdougall.gui;

import me.jacob.macdougall.graphics.Sprites;
import input.engine.mouse.Mouse;
import graphic.engine.screen.Bitmap;
import graphic.engine.screen.Screen;

public class ScrollBar extends GUI_Objects {

	private Bitmap sprite;

	protected int defaultX, defaultY;
	protected int mouseX, mouseY;
	
	protected boolean clicked = false;
	
	protected int i = 0;

	public ScrollBar(String name, int x, int y, int width, int height, Bitmap bitmap) {
		super(name, x, y, width, height);
		defaultX = x;
		defaultY = y;
		sprite = Sprites.getSprite(Sprites.SCROLL, 0, 0);
		//sprite = Art.getScrollbars()[0][0];
	}

	public ScrollBar(String name) {
		super(name, 100, 60, 10, 20);
		sprite = Sprites.getSprite(Sprites.SCROLL, 0, 0);
		//sprite = Art.getScrollbars()[0][0];
		defaultX = x;
		defaultY = y;
	}

	public void render(Screen screen) {
		screen.render(sprite, x, y);
	}

	public void update(Mouse mouse) {
		if(inBox(mouse.getPressed(Mouse.X), mouse.getPressed(Mouse.Y))) {
			clicked = true;
			i = mouse.getReleased(Mouse.X);
		}
		if(mouse.isPressed()) {
			move(i);
		}
		if(!mouse.isPressed() && clicked) {
			clicked = false;
		}
			
	}
	
	public void move(int i) {
		
		
	}
	
	public void moveLeft() {
		if(x >= defaultX - 100) {
			x--;
		} else {
			x = defaultX - 100;
		}
	}

	public void moveRight() {
		if(x <= defaultX + 100) {
			x++;
		}
	}

}

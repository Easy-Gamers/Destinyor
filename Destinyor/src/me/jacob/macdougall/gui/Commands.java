package me.jacob.macdougall.gui;

import java.awt.Color;

import me.jacob.macdougall.Destinyor;

import graphic.engine.screen.Bitmap;
import graphic.engine.screen.GameFont;
import graphic.engine.screen.Screen;

public class Commands extends GUI_Objects {
	
	private Bitmap button;
	
	public Commands(String name, int x, int y, int width, int height) {
		super(name, x, y, width, height);
		if(Destinyor.Debug)
			debug();
	}
	
	private void debug() {
		button = new Bitmap(width, height);
		for(int i = 0; i < button.pixels.length; i++) {
			button.pixels[i] = Color.BLACK.getRGB();
		}
	}
	
	public void render(Screen screen) {
		if(button != null)
			screen.render(button, getX(), getY());
		GameFont.render(name, screen, getX(), getY());
		
	}
	
	public void update() {
		
	}

}

package me.jacob.macdougall.gui;

import java.awt.Color;

import me.jacob.macdougall.Destinyor;

import graphic.engine.screen.Bitmap;
import graphic.engine.screen.GameFont;
import graphic.engine.screen.Screen;

public class Commands extends GUI_Objects {
	
	public Commands(String name, int x, int y) {
		super(name, x, y, 8 * name.length(), 8);
		if(Destinyor.Debug)
			debug();
		else
			setSprite(null);
	}
	
	public Commands(String name, int x, int y, int width, int height) {
		super(name, x, y, 8 * name.length(), 8);
		if(Destinyor.Debug)
			debug();
		else
			setSprite(null);
	}
	
	private void debug() {
		setSprite(new Bitmap(width, height));
		for(int i = 0; i < getSprite().pixels.length; i++) {
			getSprite().pixels[i] = Color.BLACK.getRGB();
		}
	}
	
	public void render(Screen screen) {
		if(getSprite() != null)
			masterRender(screen);
		GameFont.render(name, screen, getX(), getY());
		
	}
	
	public void update() {
		
	}

}

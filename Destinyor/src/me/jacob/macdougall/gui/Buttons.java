package me.jacob.macdougall.gui;

import java.awt.Color;

import me.jacob.macdougall.graphics.Sprites;
import graphic.engine.screen.Bitmap;
import graphic.engine.screen.GameFont;
import graphic.engine.screen.Screen;

public class Buttons extends GUI_Objects {
	
	public boolean isScrollbar = false;
	private Bitmap disabledBitmap;
	
	public Buttons(String name, int x, int y, int width, int height) {
		super(name, x, y, width, height);
		setSprite(Sprites.getSprite(Sprites.BUTTON, 0, 0));
	}
	
	public Buttons(String name, int x, int y) {
		super(name, x, y, 120, 20);
		this.name = name;
		this.x = x;
		this.y = y;
		this.width = 120;
		this.height = 20;
		setSprite(Sprites.getSprite(Sprites.BUTTON, 0, 0));
	}
	
	public void render(Screen screen) {
		if(isEnabled()) {
			masterRender(screen);
		} else {
			if(disabledBitmap == null) {
				disabledBitmap = new Bitmap(width, height);
				for(int i = 0; i < disabledBitmap.pixels.length; i++) {
					Color color = new Color(getSprite().pixels[i]);
					color = new Color(color.getRed() / 2, color.getGreen() / 2, color.getBlue() / 2);
					disabledBitmap.pixels[i] = color.getRGB();
				}
			}
			screen.render(disabledBitmap, x, y);
		}	
		int x1 = (int) ((width / 2.5) - (name.length() * 2)) + x;
		GameFont.render(name, screen, x1, y + 6);
	}
	
	public void pressed() {
		if(isEnabled())
			setSprite(Sprites.getSprite(Sprites.BUTTON, 1, 0));
	}
	
	public void pressed(Bitmap sprite) {
		setSprite(sprite);
	}
	
	public void update() {
		
	}
	
}

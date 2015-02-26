package me.jacob.macdougall.gui;

import java.awt.Color;

import graphic.engine.screen.Bitmap;
import graphic.engine.screen.GameFont;
import graphic.engine.screen.Screen;

public class CheckBoxes extends GUI_Objects {
	
	private boolean isChecked = false;
	private Bitmap checked;
	private Bitmap disabled;
	
	public CheckBoxes(String name, int x, int y, int width, int height, boolean isChecked) {
		super(name, x, y, width, height);
		this.isChecked = isChecked;
	}
	
	public CheckBoxes(String name, int x, int y, int width, int height, boolean isChecked, Bitmap sprite) {
		super(name, x, y, width, height);
		this.isChecked= isChecked;
		setSprite(sprite);
	}
	
	@Override
	public void setSprite(Bitmap sprite) {
		this.sprite = sprite;
		checked = new Bitmap(width, height);
		for(int i = 0; i < checked.pixels.length; i++) {
			Color color = new Color(sprite.pixels[i]);
			color = new Color(color.getRed() / 2, color.getGreen() / 2, color.getBlue() / 2);
			checked.pixels[i] = color.getRGB();
		}
		disabled = new Bitmap(width, height);
		for(int i = 0; i < disabled.pixels.length; i++) {
			Color color = new Color(sprite.pixels[i]);
			color = new Color(color.getRed() / 4, color.getGreen() / 8, color.getBlue() / 4);
			disabled.pixels[i] = color.getRGB();
		}
	}
	
	public void updateSprites() {
		
	}
	
	public boolean isChecked() {
		return isChecked;
	}
	
	/**
	 * Toggles whether or not the check box is checked.
	 */
	public void Toggle() {
		isChecked = !isChecked; // Whatever isChecked is currently at, reverse it
	}
	
	public void render(Screen screen) {
		if(isChecked()) {
			screen.render(checked, x, y);
		} else if(!isEnabled()) {
			screen.render(disabled, x, y);
		} else {
			masterRender(screen);
		}
		int x1 = (int) ((width / 2.5) - (name.length() * 2)) + x;
		GameFont.render(name, screen, x1, y + 6);
	}
	
}

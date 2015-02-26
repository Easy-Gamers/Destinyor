package me.jacob.macdougall.gui;

import java.awt.Color;

import graphic.engine.screen.Bitmap;
import graphic.engine.screen.GameFont;
import graphic.engine.screen.Screen;
import graphic.engine.window.Resolution;

public class GUI_Objects {
	
	protected int x, y;
	protected int width, height;
	protected String desc;
	protected boolean isEnabled = true;
	protected String name;
	protected Bitmap sprite;
	
	protected boolean focused = false;
	
	
	/**
	 * Default GUI Object
	 * @param x
	 * @param y
	 * @param width
	 * @param height
	 */
	public GUI_Objects(String name, int x, int y, int width, int height) {
		this.name = name;
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		if(sprite == null) {
			sprite = new Bitmap(width, height);
			for(int i = 0; i < sprite.pixels.length; i++) {
				sprite.pixels[i] = Color.GREEN.getRGB();
			}
		}
	}
	
	/**
	 * Returns the x position relative to the screen
	 * @return
	 */
	public int getAbsoluteX() {
		float xtemp = (float) Resolution.width() / Screen.getWidth();
		float X = (float) (x * xtemp);
		//X = x ^(Resolution.width() / Options.ResX[Resolution.getScaleX()] * Options.ResY[Resolution.getScaleY()]);
		return (int) X;
	}
	
	/**
	 * Returns the y position relative to the screen
	 * @return
	 */
	public int getAbsoluteY() {
		float ytemp = (float) Resolution.height() / Screen.getHeight();
		float Y = (float) (y * ytemp);
		//X = x ^(Resolution.width() / Options.ResX[Resolution.getScaleX()] * Options.ResY[Resolution.getScaleY()]);
		return (int) Y;
	}
	
	/**
	 * Returns the width of the image being drawn on the screen
	 * @return width * (Resolution.width() / Screen.getWidth())
	 */
	public int getWidth() {
		float widthtemp = (float) Resolution.width() / Screen.getWidth();
		float Width = (float) (width * widthtemp);
		return (int) Width;
	}
	
	/**
	 * Returns the height of the image being drawn on the screen
	 * @return height * (Resolution.height() / Screen.getHeight())
	 */
	public int getHeight() {
		float heighttemp = (float) Resolution.height() / Screen.getHeight();
		float Height = (float) (height * heighttemp);
		return (int) Height;
	}
	
	/**
	 * Returns the actual image width
	 * @return 120 for default buttons
	 */
	public int getAbsoluteWidth() {
		return width;
	}
	
	/**
	 * Returns the actual image height
	 * @return 20 for default buttons
	 */
	public int getAbsoluteHeight() {
		return height;
	}
	
	/**
	 * Returns actual x value, relative to the frame
	 * @return
	 */
	public int getX() {
		return x;
	}
	
	/**
	 * Returns actual y value, relative to the frame
	 * @return
	 */
	public int getY() {
		return y;
	}
	
	/**
	 * Was the click on the button?
	 * @param x
	 * @param y
	 * @return
	 */
	public boolean inBox(int x, int y) {
		int x1 = getAbsoluteX();
		int w1 = getWidth();
		int y1 = getAbsoluteY();
		int h1 = getHeight();
		if(x >= x1 && x <= x1 + w1) {
			if(y >= y1 && y <= y1 + h1) {
				return true;
			}
		}
		return false;
	}
	
	/**
	 * Was the click on the button?
	 * @param x Click Position
	 * @param y Click Position
	 * @param x1 Release Position
	 * @param y1 Release Position
	 * @return
	 */
	public boolean inBox(int x, int y, int x1, int y1) {
		int x2 = getAbsoluteX();
		int w1 = getWidth();
		int y2 = getAbsoluteY();
		int h1 = getHeight();
		if(x >= x2 && x <= x2 + w1 && x1 > x2 && x1 <= x2 + w1) {
			if(y >= y2 && y <= y2 + h1 && y1 >= y2 && y1 <= y2 + h1) {
				return true;
			}
		}
		return false;
	}
	
	public String getDesc() {
		return desc;
	}
	
	public void setDesc(String desc) {
		this.desc = desc;
	}
	
	public void displayDesc(Screen screen) {
		GameFont.render(desc, screen, x + width, y);
	}
	
	public boolean isEnabled() {
		return isEnabled;
	}
	
	public void setEnabled(boolean isEnabled) {
		this.isEnabled = isEnabled;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public void setFocused(boolean focus) {
		this.focused = focus;
	}
	
	public boolean getFocused() {
		return focused;
	}
	
	public void setSprite(Bitmap sprite) {
		this.sprite = sprite;
	}
	
	public Bitmap getSprite() {
		return sprite;
	}
	
	public void masterRender(Screen screen) {
		if(sprite != null)
		screen.render(sprite, x, y);
	}
}

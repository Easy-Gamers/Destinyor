package me.jacob.macdougall.gui;

import graphic.engine.screen.GameFont;
import graphic.engine.screen.Screen;
import graphic.engine.window.Resolution;

public class GUI_Objects {
	
	protected int x, y;
	protected int width, height;
	protected String desc;
	protected boolean isEnabled = true;
	
	/**
	 * Default GUI Object
	 * @param x
	 * @param y
	 * @param width
	 * @param height
	 */
	public GUI_Objects(int x, int y, int width, int height) {
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
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
}

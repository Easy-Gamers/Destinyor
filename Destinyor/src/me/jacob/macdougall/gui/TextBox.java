package me.jacob.macdougall.gui;

import java.awt.event.KeyEvent;

import me.jacob.macdougall.input.Keys;

import input.engine.keyboard.Key;
import graphic.engine.screen.GameFont;
import graphic.engine.screen.Screen;
import graphic.engine.screen.Bitmap;

public class TextBox extends GUI_Objects {

	public Bitmap sprite;
	
	private Text text = new Text();
	
	public TextBox(String name, int x, int y, int width, int height, Bitmap sprite) {
		super(name, x, y, width, height);
		this.sprite = sprite;
	}
	
	public void render(Screen screen) {
		screen.render(sprite, x, y);
		GameFont.render(text.getText(), screen, x + 8, y + 6);
	}
	
	public void update() {
		if(focused) {
			for(Key key : Key.keys) {
				if(key.pressed) {
					switch(key.kevent) {
						case KeyEvent.VK_ENTER: focused = false; break;
						case KeyEvent.VK_BACK_SPACE: text.remove(); break;
						case KeyEvent.VK_DELETE: text.remove(); break;
						default: text.add(key.name.charAt(0)); break;
					}
					if(key.effect == Keys.LEFT) {
						text.moveLeft();
					}
					if(key.effect == Keys.RIGHT) {
						text.moveRight();
					}
				}
				
			}
		}
	}
	
	public String getText() {
		return text.getText();
	}
	
}

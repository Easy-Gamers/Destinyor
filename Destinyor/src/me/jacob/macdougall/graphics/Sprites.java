package me.jacob.macdougall.graphics;

import graphic.engine.screen.Bitmap;
import graphic.engine.screen.SpriteHandler;

public class Sprites {
	
	private static SpriteHandler buttons;
	private static SpriteHandler scrollbars;
	private static SpriteHandler spritesheet;
	private static SpriteHandler font;
	private static SpriteHandler mapSprites;
	
	public static final int SPRITE = 0, FONT = 1, MAP = 2, BUTTON = 3, SCROLL = 4;
	
	public static SpriteHandler getSprites(int sprite) {
		switch(sprite) {
			case FONT: return font;
			case MAP: return mapSprites;
			case BUTTON: return buttons;
			case SCROLL: return scrollbars;
			default: return spritesheet;
		}
	}
	
	public static void setSprite(int sprite, SpriteHandler sprites) {
		switch(sprite) {
			case FONT: font = sprites; break;
			case MAP: mapSprites = sprites; break;
			case BUTTON: buttons = sprites; break;
			case SCROLL: scrollbars = sprites; break;
			default: spritesheet = sprites; break;
		}
	}
	
	public static int getLength(int sprite) {
		return getSprites(sprite).getLength();
	}
	
	public static int getLength(int sprite, int i) {
		return getSprites(sprite).getLength(i);
	}
	
	public static Bitmap getSprite(int sprite, int i, int j) {
		return getSprites(sprite).getSprites(i, j);
	}
	
	public static Bitmap[][] getSpritesheet(int sprite) {
		return getSprites(sprite).getSpritesheet();
	}
	
}

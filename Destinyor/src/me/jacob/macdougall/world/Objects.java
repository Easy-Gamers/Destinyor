package me.jacob.macdougall.world;

import java.util.HashMap;
import java.util.Map;

import me.jacob.macdougall.Time;
import me.jacob.macdougall.graphics.Sprites;
import me.jacob.macdougall.player.Player;
import graphic.engine.screen.Bitmap;
import graphic.engine.screen.Screen;

public class Objects {
	
	/*
	 * Class that will handle none tile set objects
	 * Such as trees, tables, chairs ect
	 */
	
	public static Map<String, Objects> objects = new HashMap<>();
	
	boolean animated;
	public String name;
	public int x;
	public int y;
	// X and Y Position of first frame
	int x1, y1;
	// Start position
	int f1;
	// Animation length
	int f2;
	int[] frames;
	
	public Objects(String name, int x, int y, boolean animated, String frame) {
		this.name = name;
		setFrame(frame);
		
		
		this.x = x;
		this.y = y;
		this.animated = animated;
	}
	
	protected Objects(Objects object) {
		this.name = object.name;
		this.setFrame(object.frames);
		
		this.x = object.x;
		this.y = object.y;
		
		this.animated = object.animated;
	}
	
	public void setFrame(String frame) {
		frame = frame.trim();
		String[] frames = frame.split(",");
		this.frames = new int[frames.length];
		for(int i = 0; i < frames.length; i++) {
			this.frames[i] = Integer.parseInt(frames[i].trim());
		}
		x1 = this.frames[0];
		f1 = x1;
		y1 = this.frames[1];
		f2 = this.frames[this.frames.length - 1];
	}
	
	public void setFrame(int[] frames) {
		this.frames = frames;
		this.x1 = this.frames[0];
		f1 = x1;
		this.y1 = this.frames[1];
		this.f2 = this.frames[this.frames.length - 1];
	}
	
	public Bitmap sprite() {
		if(animated) {
			if(Time.getObjectTimer(30, false))
			x1++;
			if(x1 >= f1+f2) {
				x1 = f1;
			}
			return Sprites.getSprite(Sprites.SPRITE, x1, y1);
			//return Art.getSpritesheet()[x1][y1];
		} else {
			return Sprites.getSprite(Sprites.SPRITE, frames[0], frames[1]);
			//return Art.getSpritesheet()[frames[0]][frames[1]];
		}
	}
	
	public void render(Screen screen, int X, int Y) {
		screen.render(sprite(), X + x * Tile.SIZE, Y + y * Tile.SIZE);
	}
	
	public void update() {
		
	}
	
	public Objects newInstance() {
		Objects object = new Objects(this);
		
		return object;
	}
	
	public static Objects newInstance(String name) {
		Objects object = new Objects(objects.get(name));
		
		return object;
	}
	
	public static Objects newInstance(Objects object) {
		Objects o = new Objects(object);
		
		return o;
	}
	
	public static Objects newInstance(String name, int x, int y, boolean animated, String frame){
			if(objects.get(name) != null) {
				Objects object = newInstance(objects.get(name));
				object.x = x;
				object.y = y;
				object.animated = animated;
				object.setFrame(frame);
				return object;
			} else {
				Objects object = new Objects(name, x, y, animated, frame);
				objects.put(name, object);
				return newInstance(object);
			}
	}
	
	public boolean inRange() {
		return (Player.X >= x - 8 && Player.X <= x + 8 && Player.Y >= y - 6 && Player.Y <= y + 6);
	}
	
	
}

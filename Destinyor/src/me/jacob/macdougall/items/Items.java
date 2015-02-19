package me.jacob.macdougall.items;

import graphic.engine.screen.Bitmap;

import java.util.HashMap;
import java.util.Map;

import me.jacob.macdougall.graphics.Sprites;

public class Items {
	
	public static Map<String, Items> items = new HashMap<>();
	
	public String name = "";
	public int cost = 0;
	
	public Bitmap frame = Sprites.getSprite(Sprites.SPRITE, 0, 0);
	
	public boolean equippable = false;
	
	public Items() {
		
	}
	
	protected Items(Items item) {
		this.name = item.name;
		this.cost = item.cost;
		this.frame = item.frame;
		this.equippable = item.equippable;
	}
	
	public Items newInstance() {
		Items item = new Items(this);
		
		return item;
	}
	
	public static Items newInstance(String name) {
		Items item = new Items(items.get(name));
		
		return item;
	}
	
	public static Items newInstance(Items item) {
		Items i = new Items(item);
		
		return i;
	}
	
	public void getColor() {
		double pixel = 0;
		int[] pixels = frame.pixels;
		
		for(int i = 0; i < frame.pixels.length; i++) {
			pixel += pixels[i];
		}
		pixel = pixel / pixels.length; //Gets the average colour of the item
	}
}

package me.jacob.macdougall.graphics;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.IOException;

import graphic.engine.screen.Art;
import graphic.engine.screen.Bitmap;

public class Shadows {
	
	public static final int LEFT = 2, UP = 6, DOWN = 3, RIGHT = 7, RIGHT_UP_CORNER = 8, //LEFT_UP_CORNER1 = 4, LEFT_UP_CORNER2 = 5;
			LEFT_UP_CORNER = 4, LEFT_CORNER = 5;
	
	
	//public int[][] pixels;
//	public int[] pixels;
//	public int[] shadowPixels;
//	
//	public BufferedImage image;
//	public BufferedImage shadowImage;
//	
//	public Bitmap shadow;
	
	public BufferedImage image;
	
	public Shadows(Bitmap bitmap) {
		try {
			image = Art.convert(bitmap, 32, 32, this, Sprites.getSprites(Sprites.SPRITE));
			//image = Art.convertSpritesheet(bitmap, this);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		
	}
	
	public Bitmap getShadows(int direction) {
		Bitmap bitmaps = new Bitmap(32, 32);
		
		image.getRGB(0, 0, 32, 32, bitmaps.pixels, 0, 32);
		
		switch(direction) {
		case 0: break;
		case RIGHT_UP_CORNER: RIGHT_UP_CORNER(bitmaps); break;
		
		case UP: UP(bitmaps); break;
		
		case DOWN: DOWN(bitmaps); break;
		
		case LEFT: LEFT(bitmaps); break;
		
		case RIGHT: RIGHT(bitmaps); break;
		
		//case LEFT_UP_CORNER: DOWN(bitmaps); LEFT(bitmaps); break;
		//case LEFT_UP_CORNER: LEFT(bitmaps); DOWN(bitmaps); break;
		//case LEFT_UP_CORNER1: LEFT_UP_CORNER1(bitmaps); break;
		//case LEFT_UP_CORNER2: LEFT_UP_CORNER2(bitmaps); break;
		case LEFT_UP_CORNER: LEFT_UP_CORNER(bitmaps); break;
		
		case LEFT_CORNER: LEFT_CORNER(bitmaps); break;
		}
		image.flush();
		return bitmaps;
	}
	
	private Bitmap RIGHT(Bitmap bitmaps) {
		int i = 0;
		int j = 1023;
		while(j != 1023 - 8) {
			if(i >= 0 + j) {
				j--;
				i = 0;
			}
			
			Color colour = new Color(bitmaps.pixels[j - i], true);
			colour = new Color(colour.getRed() / 2, colour.getGreen() / 2, colour.getBlue() / 2);
			bitmaps.pixels[j - i] = colour.getRGB();
			
			i += 32;
		}
		
		return bitmaps;
	}
	
	private Bitmap LEFT(Bitmap bitmaps) {
		int i = 0;
		int j = 0;
		while(j != 7) {
			if(i >= 996 + j) {
				j++;
				i = 0;
			}
			
			Color colour = new Color(bitmaps.pixels[i + j], true);
			colour = new Color(colour.getRed() / 2, colour.getGreen() / 2, colour.getBlue() / 2);
			bitmaps.pixels[i + j] = colour.getRGB();
			
			i += 32;
		}
		return bitmaps;
	}
	
	private Bitmap RIGHT_UP_CORNER(Bitmap bitmaps) {
		for(int i = 0; i < 32; i++) {
			for(int j = 0; j < 32 * i; j+= 32) {
				Color colour = new Color(bitmaps.pixels[j + i], true);
				colour = new Color(colour.getRed() / 2, colour.getGreen() / 2, colour.getBlue() / 2);
				bitmaps.pixels[j + i] = colour.getRGB();
			}
		}
		return bitmaps;
	}
	
	private Bitmap UP(Bitmap bitmaps) {
		for(int i = 0; i < 32 * 8; i++) {
			Color colour = new Color(bitmaps.pixels[i], true);
			colour = new Color(colour.getRed() / 2, colour.getGreen() / 2, colour.getBlue() / 2);
			bitmaps.pixels[i] = colour.getRGB();
		}
		return bitmaps;
	}
	
	private Bitmap DOWN(Bitmap bitmaps) {
		for(int i = 0; i < 32 * 8; i++) {
			Color colour = new Color(bitmaps.pixels[i], true);
			colour = new Color(colour.getRed() / 2, colour.getGreen() / 2, colour.getBlue() / 2);
			bitmaps.pixels[i] = colour.getRGB();
		}
		return bitmaps;
	}
	
	@SuppressWarnings("unused")
	private Bitmap LEFT_UP_CORNER1(Bitmap bitmaps) {
		int oldI = 0;
		for(int i = 32; i < 32 * 32; i+=31) {
			for(int j = oldI; j < i; j++) {
			Color colour = new Color(bitmaps.pixels[j], true);
			colour = new Color(colour.getRed() / 2, colour.getGreen() / 2, colour.getBlue() / 2);
			bitmaps.pixels[j] = colour.getRGB();
			}
			oldI += 32;
		}
		
		return bitmaps;
	}
	
	private Bitmap LEFT_UP_CORNER(Bitmap bitmaps) {
		int oldI = 0;
		int[] pixels = new int[32 * 32];
		for(int i = 32; i < 32 * 32; i+=31) {
			for(int j = oldI; j < i + 7; j++) {
				if(pixels[j] != 1) {
					Color colour = new Color(bitmaps.pixels[j], true);
					colour = new Color(colour.getRed() / 2, colour.getGreen() / 2, colour.getBlue() / 2);
					bitmaps.pixels[j] = colour.getRGB();
				}
				pixels[j] = 1;
			}
			oldI += 32;
		}
		
		return bitmaps;
	}
	private Bitmap LEFT_CORNER(Bitmap bitmaps) {
		for(int i = 0; i < 7; i++) {
			for(int j = 0; j < 32 * 8; j+=32) {
				Color colour = new Color(bitmaps.pixels[i + j], true);
				colour = new Color(colour.getRed() / 2, colour.getGreen() / 2, colour.getBlue() / 2);
				bitmaps.pixels[i + j] = colour.getRGB();
			}
		}
		return bitmaps;
	}
	
}

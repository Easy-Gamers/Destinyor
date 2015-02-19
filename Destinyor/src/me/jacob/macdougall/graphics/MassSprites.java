package me.jacob.macdougall.graphics;

import me.jacob.macdougall.world.LevelMap;

import graphic.engine.screen.Bitmap;
import graphic.engine.screen.Screen;

/**
 * class that handles classes with more than one tile of sprite
 * @author Jacob
 *
 */
public class MassSprites {

	public Bitmap[][] bitmap;
	//public Point point;
	//public int[] frames = new int[4];
	public LevelMap map;
	
	protected int x;
	protected int y;
	
	protected int x1, y1, x2, y2;
	
	/**
	 * Class used to control multiple sprites
	 * @param x x position to begin draw at
	 * @param y y position to begin drawing at
	 * @param x1 first sprite x coordinate
	 * @param x2 final sprite x coordinate
	 * @param y1 first sprite y coordinate
	 * @param y2 final sprite y coordinate
	 * @param map level that it will be drawn on
	 */
	public MassSprites(int x, int y, int x1, int y1, int x2, int y2, LevelMap map) {
		//point = new Point(x, y);
		setXandY(x, y);
		
		this.x1 = x1;
		this.y1 = y1;
		this.x2 = x2;
		this.y2 = y2;

		bitmap = new Bitmap[(x2 - x1)][(y2 - y1)];

		for(int i = 0; i < (x2 - x1); i++) { // Gets the size of the x co-ord
			for(int j = 0; j < (y2 - y1); j++) { // Gets the size of the y co-ord
				bitmap[i][j] = Sprites.getSprite(Sprites.SPRITE, x1 + i, y1 + j);
			}
		}
		this.map = map;
	}

	public void render(Screen screen) {
		if(map != null) {
			for(int i = 0; i < x2 - x1; i++)
				for(int j = 0; j < y2 - y1; j++)
					screen.render(bitmap[i][j], map.MapX_Pos + (x + i) * LevelMap.SIZE, map.MapY_Pos + (y + j) * LevelMap.SIZE);
		} else { // Assume it's a boss
			for(int i = 0; i < x2 - x1; i++)
				for(int j = 0; j < y2 - y1; j++)
					screen.render(bitmap[i][j], 0 + (x + i) * LevelMap.SIZE, 0 + (y + j) * LevelMap.SIZE);
		}
	}

	public void setXandY(int x, int y) {
		this.x = x;
		this.y = y;
	}
	
	public int getX() {
		return x;
	}
	
	public int getY() {
		return y;
	}
}

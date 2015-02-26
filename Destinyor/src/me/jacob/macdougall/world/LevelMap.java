package me.jacob.macdougall.world;

import graphic.engine.screen.Screen;

import java.util.*;

import me.jacob.macdougall.Destinyor;
import me.jacob.macdougall.graphics.Shadows;
import me.jacob.macdougall.npcs.NPC;

public class LevelMap {
	
	public static Map<Integer, LevelMap> maps = new HashMap<>();
	
	public List<Objects> objects = new ArrayList<>();
	public List<NPC> npcs = new ArrayList<>();
	
	public static int levelX = 1; // For miniMap
    public static int levelY = 1; // For MiniMap
    public static int level = 1; // For Level
	public static final int FloorWidth = 512;
	public static final int FloorHeight = 512;
	public int MapX_Pos;
	public int MapY_Pos;
	
	public static int SIZE = Tile.SIZE;
	
	public int floor;
	
	public int[][] tiles;
	
	public int shadow = 0;
	
	public LevelMap() {
		tiles = new int[FloorWidth][FloorHeight];
	}
	
	public Tile getTileAt(int i, int j) {
		if(i < 0 || i >= tiles.length || j < 0 || j > tiles.length) return null;
		return Tile.tiles[tiles[i][j]];
	}
	
	public void render(Screen screen, int x, int y) {
		int X = (int) (Screen.getWidth()  / 2.0);
		int Y = (int) (Screen.getHeight() / 2.0);
		MapX_Pos = x + X;
		MapY_Pos = y + Y;
		
		for(int i = 0; i < tiles.length; i++) {
			for(int j = 0; j < tiles[i].length; j++) {
				if(Tile.tiles[tiles[i][j]] == null) {
					continue;
				} else {
					if(!Tile.tiles[tiles[i][j]].solid) {
							if(Tile.tiles[tiles[i - 1][j - 1]] != null && Tile.tiles[tiles[i - 1][j - 1]].solid) {
								shadow = Shadows.LEFT_CORNER;
								// Check the corners because nearly all shadow blocks will say yes, so you overwrite them after if the other conditions are true
							}
							if(Tile.tiles[tiles[i][j - 1]] != null && Tile.tiles[tiles[i][j - 1]].solid) {
								shadow = Shadows.DOWN;
							}
							if(Tile.tiles[tiles[i-1][j]] != null && Tile.tiles[tiles[i-1][j]].solid) {
								shadow = Shadows.LEFT;
							}
							if(Tile.tiles[tiles[i][j - 1]] != null && Tile.tiles[tiles[i][j - 1]].solid && Tile.tiles[tiles[i-1][j]] != null && Tile.tiles[tiles[i-1][j]].solid) {
								shadow = Shadows.LEFT_UP_CORNER;
							}
						}
					Tile.tiles[tiles[i][j]].render(screen, MapX_Pos + i * SIZE, MapY_Pos + j * SIZE, shadow);
					shadow = 0;
				}
			}
		}
	}
	
	public void putObjects(Objects object) {
		objects.add(object);
	}

	public void renderObjects(Screen screen) {
		for(Objects object : this.objects) {
			if(object.inRange()) {
				object.render(screen, MapX_Pos, MapY_Pos);
			}
		}
	}
	
	
	
}

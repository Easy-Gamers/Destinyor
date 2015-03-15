package me.jacob.macdougall.graphics;

import java.awt.Color;

import graphic.engine.screen.Bitmap;
import graphic.engine.screen.Screen;
import graphic.engine.window.Resolution;
import me.jacob.macdougall.world.*;

public class UI {

	private static Bitmap refresh = new Bitmap(0, 0);
	
	public static final int Map = 0, Fight = 1, Minimap = 2, Inventory = 3,
			Equipment = 4, Spellbook = 5, Player1 = 6, Player2 = 7,
			Player3 = 8, Player4 = 9;

	// 0 map, 1, fight, 2, minimap, 3 inv, 4 equip, 5 spellbook, 6 Kryo equip, 7 Karen equip, 8 Player 3 Equip,  8 Player 4 Equip
	public static int menu = 0;

	public static boolean biggerUI = true;

	private static final int BorderWidth = 16, BorderHeight = 12;
	private static final int GroundWidth = 16, GroundHeight = 12;

	public static void render(Screen screen) {
		Ground(screen, GroundWidth, GroundHeight);

		Border(screen, BorderWidth, BorderHeight);

		BorderEnemyText(screen, 4);
		
		BorderBattleInfo(screen, 9);
		CommandsBorderY(screen, 4);
	}
	
	public static void renderDefault(Screen screen) {
		Border(screen, BorderWidth, BorderHeight);
	}
	
	public static void renderSaves(Screen screen) {
		renderDefault(screen);
	}

	public static void renderInventory(Screen screen) {
		REFRESH(screen);
		BorderMenu(screen, BorderWidth, BorderHeight);
	}

	public static void TextBox(Screen screen) {
		for(int i = 0; i < 8; i++) {
			for(int j = 0; j < 2; j++) {
				screen.render(Sprites.getSprite(Sprites.SPRITE, 0, 0), (i + 4) * Tile.SIZE, (j + 9) * Tile.SIZE);
			}
		}
	}

	public static void renderEquipment(Screen screen) {
		REFRESH(screen);
		BorderMenu(screen, BorderWidth, BorderHeight);
	}

	private static void BorderMenu(Screen screen, int w, int h) {
		BorderX(screen, w, h);
		BorderY(screen, w, h);
		BorderTextBottom(screen, w);
		
	}
	
	private static void CommandsBorderY(Screen screen, int h) {
		for(int j = 0; j < h; j++) {
			screen.render(Sprites.getSprite(Sprites.SPRITE, 18, 3), 7 * Tile.SIZE, (j + 8) * Tile.SIZE);
		}
	}

	private static void BorderBattleInfo(Screen screen, int w) {
		for(int i = 0; i < w; i++) {
			screen.render(Sprites.getSprite(Sprites.SPRITE, 17, 3), (i + 7) * Tile.SIZE, 10 * Tile.SIZE);
		}
	}

	private static void Border(Screen screen, int w, int h) {
		BorderX(screen, w, h);
		BorderY(screen, w, h);
		BorderTextTop(screen, w, h);
	}

	private static void BorderTextBottom(Screen screen, int w) {
		for(int i = 0; i < w; i++) {
			screen.render(Sprites.getSprite(Sprites.SPRITE, 17, 3), (i + 0) * Tile.SIZE, 8 * Tile.SIZE);
		}
	}

	private static void BorderX(Screen screen, int w, int h) {
		for(int i = 0; i < w; i++) {
			for(int j = 0; j < h; j++) {
				screen.render(Sprites.getSprite(Sprites.SPRITE, 17, 3), (i + 0) * Tile.SIZE, (11 * Tile.SIZE) + 12);
				screen.render(Sprites.getSprite(Sprites.SPRITE, 16, 3), (i + 0) * Tile.SIZE, (-1 * Tile.SIZE) + 6);
			}
		}
	}

	private static void BorderY(Screen screen, int w, int h) {
		for(int i = 0; i < w; i++) {
			for(int j = 0; j < h; j++) {
				screen.render(Sprites.getSprite(Sprites.SPRITE, 18, 3), (0 * Tile.SIZE), ((j + 0) * Tile.SIZE));
				screen.render(Sprites.getSprite(Sprites.SPRITE, 19, 3), (15 * Tile.SIZE) - 3, (j + 0) * Tile.SIZE);
			}
		}
	}

	private static void BorderTextTop(Screen screen, int w, int h) {
		for(int i = 0; i < w; i++) {
			for(int j = 0; j < h; j++) {
				screen.render(Sprites.getSprite(Sprites.SPRITE, 17, 3), (i + 0) * Tile.SIZE, 8 * Tile.SIZE);
			}
		}
	}

	private static void BorderEnemyText(Screen screen, int h) {
			for(int j = 0; j < h; j++) {
				screen.render(Sprites.getSprite(Sprites.SPRITE, 18, 3), 4 * Tile.SIZE, (j + 8) * Tile.SIZE);
			}
	}

	private static void Ground(Screen screen, int w, int h) {
		for(int i = 0; i < w; i++) {
			for(int j = 0; j < h; j++) {
				screen.render(Sprites.getSprite(Sprites.SPRITE, 3, 0), (i + 0) * Tile.SIZE, (j + 0) * Tile.SIZE);
			}
		}
	}

	public static void REFRESH(Screen screen) {
		if(refresh.w != Resolution.width() || refresh.h != Resolution.height()) {
			refresh = new Bitmap(Resolution.width(), Resolution.height());
			for(int i = 0; i < refresh.pixels.length; i++) {
				refresh.pixels[i] = Color.BLACK.getRGB();
			}
		}
		screen.render(refresh, 0, 0);
	}

}
package me.jacob.macdougall.minimap;

import graphic.engine.screen.Art;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;

import me.jacob.macdougall.Destinyor;
import me.jacob.macdougall.graphics.UI;
import me.jacob.macdougall.npcs.NPC;
import me.jacob.macdougall.player.Camera;
import me.jacob.macdougall.player.Player;
import me.jacob.macdougall.world.LevelMap;

public class Minimap {
	
	private BufferedImage minimap;
	
	public int scale = 2;
	
	//public static int getScale = 4;
	
	public Minimap(Destinyor game) {
		minimap =  Art.map("/map.png", LevelMap.level - 1, 512, 512, game);
	}
	
//	public void render(Graphics g) {
//		//if(UI.menu == 2) {
//			g.drawImage(Art.getScaledInstance( minimap, 256, 128, RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON, true), 256, 128, null);
//			g.setColor(Color.RED);
//			g.drawRect(Player.X + 256, Player.Y + 128, 1, 1);
//			g.setColor(Color.BLUE);
//			for(NPC n : NPC.npcs.values()) {
//				n.renderMinimap(g);
//			}
//		//}
//	}
	
	public void render(Graphics g) {
		if(UI.menu == 2) {
		//scale = 1;
		g.drawImage(Art.getScaledInstance(minimap, minimap.getWidth() * scale, minimap.getHeight() * scale, RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_NEAREST_NEIGHBOR, true), Camera.mX, Camera.mY, null);
			g.setColor(Color.GREEN);
			g.drawRect(Player.X * scale, Player.Y * scale, scale, scale);
			g.fillRect(Player.X * scale, Player.Y * scale, scale, scale);
			g.setColor(Color.BLUE);
			for(NPC n : NPC.npcs) {
				n.renderMinimap(g, scale);
			}
//			for(RandomNPCs n : RandomNPCs.randomNpcs) {
//				n.renderMinimap(g, scale);
//			}
		}
	}
	
//	public void zoom(InputHandler input) {
//		if(UI.menu == 2) {
//			if(input.shift.down && Keys.Enter()) {
//				scale++;
//			}
//		}
//	}
}

package me.jacob.macdougall.player;

import me.jacob.macdougall.*;
import me.jacob.macdougall.battles.Battles;
import me.jacob.macdougall.graphics.UI;
import me.jacob.macdougall.input.Keys;
import me.jacob.macdougall.items.PlayerEquipment;
import me.jacob.macdougall.npcs.NPC;
import me.jacob.macdougall.world.*;

public class Move {

	// Handles Input *yes I know the name is a lie it was originally suppose to be only movement*
	
	LevelMap map;

	public static int steps = 0;
	public static int location = 0;

	private static final int DIR_UP = 0, DIR_DOWN = 1, DIR_LEFT = 2,
			DIR_RIGHT = 3;
	public static int dir = DIR_DOWN;

	public static int frame = 0;

	public static boolean canMove = true;

	public Move(LevelMap lvlmap) {
		map = lvlmap;
	}

	public static int GetXDir() {
		if(dir == DIR_LEFT)
			return -1;
		
		if(dir == DIR_RIGHT)
			return -1;
		
		return 0;
	}

	public static int GetYDir() {
		if(dir == DIR_UP)
			return -1;
		
		if(dir == DIR_DOWN)
			return 1;
		
		return 0;
	}

	public void Movement() {

		if(Battles.endBattle) {
			Battles.endBattle = false;
		}

		if(UI.menu == UI.Map || UI.menu == UI.Minimap)
			tick();

		if(UI.menu != UI.Fight) 
			if(Time.inventoryTimer(10)) {
				if(Keys.Inventory())
					if(UI.menu != UI.Inventory)
						UI.menu = UI.Inventory;
					else
						UI.menu = UI.Map;
				
				if(Keys.Equipment())
					if(UI.menu != UI.Equipment)
						UI.menu = UI.Equipment;
					else
						UI.menu = UI.Map;
			}
		if(UI.menu == UI.Equipment || UI.menu == UI.Inventory) {
			if(UI.menu == UI.Equipment) {
				if(Keys.MoveRight() && Time.getKeyTimer(10, false)) {
					PlayerEquipment.setCurrentPlayerRelative(1);
					if(PlayerEquipment.getCurrentPlayer() > 3) {
						PlayerEquipment.setCurrentPlayer(0);
					}
					if(PlayerEquipment.getCurrentPlayer() < 0) {
						PlayerEquipment.setCurrentPlayer(3);
					}
					Time.resetKeyTimer();
				}
				if(Keys.MoveLeft() && Time.getKeyTimer(10, false)) {
					PlayerEquipment.setCurrentPlayerRelative(-1);
					if(PlayerEquipment.getCurrentPlayer() < 0) {
						PlayerEquipment.setCurrentPlayer(3);
					}
					if(PlayerEquipment.getCurrentPlayer() > 3) {
						PlayerEquipment.setCurrentPlayer(0);
					}
					Time.resetKeyTimer();
				}
			}
		}

		Destinyor.ChangeScreenToUI();
		Destinyor.ChangeScreenToMap();

		if(UI.menu == UI.Map || UI.menu == UI.Minimap)
			minimap();
	}

	public void minimap() {
		if(Keys.Minimap())
			if(Time.mapTimer(10))
				if(UI.menu != UI.Minimap)
					UI.menu = UI.Minimap;
				else
					UI.menu = UI.Map;
	}

	public void tick() {
		if(Time.playerTimer(10)) {
			if(Keys.MoveUp()) {
				if(!canMove)
					return;
				dir = DIR_UP;
				move(DIR_UP);
			} else if(Keys.MoveDown()) {
				if(!canMove)
					return;
				dir = DIR_DOWN;
				move(DIR_DOWN);
			} else if(Keys.MoveLeft()) {
				if(!canMove)
					return;
				dir = DIR_LEFT;
				move(DIR_LEFT);
			} else if(Keys.MoveRight()) {
				if(!canMove)
					return;
				dir = DIR_RIGHT;
				move(DIR_RIGHT);
			} else if(frame != 0) { // Checks the frame to reset it. Resets the frame if it is an odd number, eg 1,3,5, and only if their is no key down
				float fPause = (frame / 2);
				int iPause = (frame / 2);
				if(fPause == iPause) {
					frame = frame + 1;
				}
			}

		}
	}

	public void move(int dir) {
		if(!canMove)
			return;

		if(!Time.moveTimer(20) || (!Time.moveTimer(10) && Keys.Shift()))
			return;

		if(dir == DIR_LEFT)
			if(canMove(dir))
				MoveLeft();

		if(dir == DIR_RIGHT)
			if(canMove(dir))
				MoveRight();

		if(dir == DIR_UP)
			if(canMove(dir))
				MoveUp();

		if(dir == DIR_DOWN)
			if(canMove(dir))
				MoveDown();
	}

	public int invertDir(int dir) {
		if(dir == DIR_UP)
			return DIR_DOWN;
		if(dir == DIR_DOWN)
			return DIR_UP;
		if(dir == DIR_LEFT)
			return DIR_RIGHT;
		if(dir == DIR_RIGHT)
			return DIR_LEFT;
		return 0;
	}

	private boolean canMove(int dir) {

		for(NPC n : NPC.npcs) {
			if(dir == DIR_LEFT)
				if(n.collision(Player.X - 1, Player.Y))
					return false;
			if(dir == DIR_RIGHT)
				if(n.collision(Player.X + 1, Player.Y))
					return false;
			if(dir == DIR_UP)
				if(n.collision(Player.X, Player.Y - 1))
					return false;
			if(dir == DIR_DOWN)
				if(n.collision(Player.X, Player.Y + 1))
					return false;
		}
		if(UI.menu == UI.Map || UI.menu == UI.Minimap) {
			if(dir == DIR_LEFT) {
				Tile t = map.getTileAt(Player.X - 1, Player.Y);
				if(t != null && !t.isSolid())
					return true;
				else
					return false;
			}
			if(dir == DIR_RIGHT) {
				Tile t = map.getTileAt(Player.X + 1, Player.Y);
				if(t != null && !t.isSolid())
					return true;
				else
					return false;
			}
			if(dir == DIR_UP) {
				Tile t = map.getTileAt(Player.X, Player.Y - 1);
				if(t != null && !t.isSolid())
					return true;
				else
					return false;
			}
			if(dir == DIR_DOWN) {
				Tile t = map.getTileAt(Player.X, Player.Y + 1);
				if(t != null && !t.isSolid())
					return true;
				else
					return false;
			}
		}
		return false;
	}

	public void MoveLeft() {
		steps += 1;
		Camera.cX -= 1 * 32;
		Player.X -= 1;
		frame += 1;
		Battles.enterCombat();
		Destinyor.refresh();
	}

	public void MoveRight() {
		steps += 1;
		Camera.cX += 1 * Tile.SIZE;
		Player.X += 1;
		frame += 1;
		Battles.enterCombat();
		Destinyor.refresh();
	}

	public void MoveUp() {
		steps += 1;
		Camera.cY -= 1 * 32;
		Player.Y -= 1;
		frame += 1;
		Battles.enterCombat();
		Destinyor.refresh();
	}

	public void MoveDown() {
		steps += 1;
		Camera.cY += 1 * 32;
		Player.Y += 1;
		frame += 1;
		Battles.enterCombat();
		Destinyor.refresh();
	}

}

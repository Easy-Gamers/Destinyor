package me.jacob.macdougall.enemies;

import java.util.ArrayList;
import java.util.List;

import graphic.engine.screen.Screen;
import me.jacob.macdougall.cutscenes.NPCs;
import me.jacob.macdougall.magic.Element;
import me.jacob.macdougall.magic.Spells;
import me.jacob.macdougall.npcs.NPC;
import me.jacob.macdougall.world.LevelMap;

public class Boss extends Enemies {
	
	public static List<Boss> bosses = new ArrayList<>();
	
	public NPC npc;

	public Element Resistance;
	public String Condition;

	public boolean Defeated;
	public boolean isNpc;
	
	/**
	 * 
	 * @param Name
	 * @param x1
	 * @param x2
	 * @param y1
	 * @param y2
	 * @param lvl
	 * @param exp
	 * @param hp
	 * @param str
	 * @param skl
	 * @param spd
	 * @param luk
	 * @param def
	 * @param gold
	 * @param resistance
	 * @param spells
	 * @param x
	 * @param y
	 * @param level
	 */
	public Boss(String name, int x1, int y1, int x2, int y2, int lvl, int exp, int hp, int str, int skl, int spd, int luk, int def, int wis, int gold, Element resistance, int x, int y, LevelMap level) {
		super(name, "", lvl, exp, hp, str, skl, spd, luk, def, wis, gold, x, y, x1, y1, x2, y2, level);
		this.Resistance = resistance;
		this.npc = NPCs.getNpc(name);
		isNpc = (npc != null);
	}
	
	public Boss getBoss(String name) {
		for(Boss boss : bosses) {
			if(boss != null && boss.getName().equals(name)) {
				return boss;
			}
		}
		return null;
	}

	public void renderAsNpc(Screen screen) {
		// All Npcs are in the npc file, as such any new boss that is a npc is also in the npc folder.
		if(isNpc && npc != null)
			npc.render(screen);
	}
	
	public void Speaking() {
		if(isNpc) {
			if(!npc.isSpeaking()) {
				npc.Speaking();
			} else {
				npc.stopSpeaking();
				// Battle
			}
		}
	}

}

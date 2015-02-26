package me.jacob.macdougall.quests;

import me.jacob.macdougall.cutscenes.Cutscene;
import me.jacob.macdougall.enemies.Boss;
import me.jacob.macdougall.items.Equipment;
import me.jacob.macdougall.npcs.NPC;
import me.jacob.macdougall.world.LevelMap;

public class BossQuest extends Quest {

	public int x, y;
	public int level;
	public LevelMap map;
	
	public BossQuest(Rewards[] rewards, NPC questNpc, Boss boss, Cutscene[] cutscene, String startDialouge, String endDialouge, boolean completed) {
		super(rewards, questNpc, cutscene, startDialouge, endDialouge, completed);
		// TODO Auto-generated constructor stub
	}

	
	@SuppressWarnings("unused")
	public static void testQuest() {
		Rewards reward;
		Equipment[] item = { Equipment.equipment.get(0) };
		reward = new Rewards(item, null, null);
		//BossQuest = new BossQuest(reward, NPC.getNpc("Jacob", 0), new Boss(), null, "segterger", "gerwager", false);
	}
	

}

package me.jacob.macdougall.quests;

import me.jacob.macdougall.cutscenes.Cutscene;
import me.jacob.macdougall.enemies.Boss;
import me.jacob.macdougall.npcs.NPC;

public class Story extends Quest {

	public Story(Rewards[] rewards, NPC questNpc, Boss boss, Cutscene[] cutscene, String startDialouge, String endDialouge, boolean completed) {
		super(rewards, questNpc, cutscene, startDialouge, endDialouge, completed);
		// TODO Auto-generated constructor stub
	}

}

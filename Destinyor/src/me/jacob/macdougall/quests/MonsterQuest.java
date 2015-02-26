package me.jacob.macdougall.quests;

import me.jacob.macdougall.cutscenes.Cutscene;
import me.jacob.macdougall.enemies.Dummy;
import me.jacob.macdougall.npcs.NPC;
import me.jacob.macdougall.npcs.body.Entities;

public class MonsterQuest extends Quest {
	// Monster Quest kill x amount of monsters
	
	public static final int ENEMY = 0, ENTITY = 1;
	
	public String[] enemies;
	public String[] entities;
	
	public int[] killed;
	
	
	public MonsterQuest(Rewards[] rewards, NPC questNpc, Cutscene[] cutscene, String startDialouge, String endDialouge, boolean completed) {
		super(rewards, questNpc, cutscene, startDialouge, endDialouge, completed);
	}
	
	public MonsterQuest(Rewards[] rewards, NPC questGiver, Cutscene[] cutscenes, String startDialouge, String endDialouge, boolean completed, Entities... entities) {
		super(rewards,questGiver, cutscenes, startDialouge, endDialouge, completed);

	}
	
	public MonsterQuest(Rewards[] rewards, NPC questGiver, Cutscene[] cutscenes, String startDialouge, String endDialouge, boolean completed, Dummy... enemies) {
		super(rewards,questGiver, cutscenes, startDialouge, endDialouge, completed);

	}
	
	public void update(Dummy[] enemies) {
		switch(getKillType()) {
			case ENEMY: enemyUpdate(enemies); break;
			case ENTITY: entityUpdate(enemies); break;
		}
	}
	
	public void enemyUpdate(Dummy[] enemies) {
		
	}
	
	public void entityUpdate(Dummy[] entities) {
		
	}
	
	public int getKillType() {
		return (enemies != null) ? ENEMY : ENTITY;
	}
	
}

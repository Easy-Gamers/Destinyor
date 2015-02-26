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
	private int[] amountToKill;
	
	
	public MonsterQuest(Rewards[] rewards, NPC questNpc, Cutscene[] cutscene, String startDialouge, String endDialouge, boolean completed) {
		super(rewards, questNpc, cutscene, startDialouge, endDialouge, completed);
	}
	
	public MonsterQuest(Rewards[] rewards, NPC questGiver, Cutscene[] cutscenes, String startDialouge, String endDialouge, boolean completed, int[] amountToKill, Entities... entities) {
		super(rewards,questGiver, cutscenes, startDialouge, endDialouge, completed);
		this.amountToKill = amountToKill;
		String[] entityNames = new String[entities.length];
		for(int i = 0; i < entities.length; i++) {
			entityNames[i] = entities[i].getName();
		}
		this.entities = entityNames;
	}
	
	public MonsterQuest(Rewards[] rewards, NPC questGiver, Cutscene[] cutscenes, String startDialouge, String endDialouge, boolean completed, int[] amountToKill, Dummy... enemies) {
		super(rewards, questGiver, cutscenes, startDialouge, endDialouge, completed);
		String[] enemyNames = new String[enemies.length];
		for(int i = 0; i < enemies.length; i++) {
			enemyNames[i] = enemies[i].getName();
		}
		this.enemies = enemyNames;
	}
	
	public void update(Dummy[] enemies) {
		switch(getKillType()) {
			case ENEMY: enemyUpdate(enemies); break;
			case ENTITY: entityUpdate(enemies); break;
		}
		boolean done = false;
		for(int i = 0; i < amountToKill.length; i++) {
			if(killed[i] >= amountToKill[i]) {
				done = true;
			} else {
				done = false;
				break;
			}
		}
		if(done) {
			this.setCompleted(true);
			return;
		}
	}
	
	public void enemyUpdate(Dummy[] enemies) {
		for(int i = 0; i < this.enemies.length; i++)
			for(Dummy dummy : enemies)
				if(this.enemies.equals(dummy.getName()) && getKillType() == ENEMY)
					killed[i]++;
	}
	
	public void entityUpdate(Dummy[] entities) {
		for(int i = 0; i < this.entities.length; i++)
			for(Dummy dummy : entities)
				if(this.entities.equals(dummy.getEntityType().getName()))
					killed[i]++;
	}
	
	public int getKillType() {
		return (enemies != null) ? ENEMY : ENTITY;
	}
	
}

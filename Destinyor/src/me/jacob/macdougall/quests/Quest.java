package me.jacob.macdougall.quests;

import graphic.engine.screen.Dialouge;
import graphic.engine.screen.Screen;

import java.util.HashMap;
import java.util.Map;

import me.jacob.macdougall.cutscenes.Cutscene;
import me.jacob.macdougall.npcs.*;

public class Quest {
	
	// Item fetching quest
	// Monster hunting quest
	// Boss killing quest
	// previous quest completion

	public static final String BOSS = "Boss", MONSTER = "Monster Hunting", BOUNTY = "Bounty Hunter", FETCH = "Fetch";
	
	public static Map<Integer, Quest> quests = new HashMap<>();

	public Rewards[] rewards;
	private NPC questGiver;
	public Cutscene[] cutscenes;
	private boolean isAccepted = false;
	private boolean completed = false;
	public boolean rewarded = false;
	protected QuestReader questReader;
	
	private String startDialouge;
	private String endDialouge;

	

	public Quest(Rewards[] rewards, NPC questNpc, Cutscene[] cutscene, String startDialouge, String endDialouge, boolean completed) {
		this.questGiver = questNpc;
		this.rewards = rewards;
		this.startDialouge = startDialouge;
		this.endDialouge =  endDialouge;
		this.completed = completed;
	}

	public Rewards[] getRewards() {
		if(completed) {
			rewarded = true;
			return rewards;
		}
		return null;
	}

	public NPC getQuestGiver() {
		return questGiver;
	}

	public void setQuestGiver(NPC npc) {
		this.questGiver = npc;
	}
	
	public boolean isAccepted() {
		return isAccepted;
	}
	
	public String getStartDialouge() {
		return startDialouge;
	}
	
	public String getEndDialouge() {
		return endDialouge;
	}
	
	public static void checkQuest(String questType) {
		
	}
	
	public boolean isCompleted() {
		return completed;
	}
	
	public void setCompleted(boolean complete) {
		this.completed = complete;
	}
	
	/**
	 * Mock example of the prompt dialouge
	 */
	public void prompt(Screen screen) {
		String[] dialouge = {
				"Are you sure you want to turn in this quest?"
		};
		Dialouge.setText(dialouge, 0);
		Dialouge.render(screen); // hand in the "/*+*/ /*items.length*/ /*+*/ /*item.name*/ + "s");
	}
}
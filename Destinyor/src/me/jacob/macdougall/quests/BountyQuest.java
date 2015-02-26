package me.jacob.macdougall.quests;

import me.jacob.macdougall.cutscenes.Cutscene;
import me.jacob.macdougall.npcs.NPC;

public class BountyQuest extends Quest {

	// Bounty quest makes a enemy spawn as a random encounter, the play has to go and find it and then kill it.
	
	public BountyQuest(Rewards[] rewards, NPC questNpc, Cutscene[] cutscene, String startDialouge, String endDialouge, boolean completed) {
		super(rewards, questNpc, cutscene, startDialouge, endDialouge, completed);
		// TODO Auto-generated constructor stub
	}

}

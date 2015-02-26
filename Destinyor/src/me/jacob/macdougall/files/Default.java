package me.jacob.macdougall.files;

import java.util.ArrayList;
import java.util.List;

import me.jacob.macdougall.magic.Element;

public class Default {

	private static final List<String[]> entities = new ArrayList<>();
	private static final List<String[]> spells = new ArrayList<>();
	private static final List<String[]> items = new ArrayList<>();
	private static final List<String[]> bosses = new ArrayList<>();
	
	private static final List<String[]> quests = new ArrayList<>();
	
	private static final String[] bossFormat = {
		"Name = ", "Frame = ", "Level = ",
		"Experience = ", "Health = ", "Strength = ",
		"Skill = ", "Speed = ", "Luck = ", "Defense = ",
		"Wisdom = ", "Gold = ", "Resistance = ", "X&Y = ", "Level = "
	};
	
	private static final String[] itemsFormat = {
		"Name = ", "Damage = ", "Price = ", "Element = ", "Limb = ", "Spell = "
	};
	
	public static void setEntities(String[] stats) {
		entities.add(stats);
	}
	
	public static String[][] getEntities() {
		String[][] stats = new String[entities.size()][];
		for(int i = 0; i < entities.size(); i++) {
			stats[i] = entities.get(i);
		}
		
		return stats;
	}
	
	public static String[] getEntitiesFormat() {
		return null;
	}
	
	public static void setSpell(String[] stats) {
		spells.add(stats);
	}
	
	public static List<String[]> getSpell() {
		return spells;
	}
	
	public static void setItems(String[] stats) {
		items.add(stats);
	}

	public static String[][] getItems() {
		String[][] stats = new String[items.size()][];
		for(int i = 0; i < items.size(); i++) {
			stats[i] = items.get(i);
		}
		
		return stats;
	}
	
	public static String[] getItemsFormat() {
		return itemsFormat;
	}
	
	public static void setBoss(String[] stats) {
		bosses.add(stats);
	}

	public static String[][] getBosses() {
		String[][] stats = new String[bosses.size()][];
		for(int i = 0; i < bosses.size(); i++) {
			stats[i] = bosses.get(i);
		}
		
		return stats;
	}
	
	public static String[] getBossFormat() {
		return bossFormat;
	}
	
	public static void setQuests(String[] stats) {
		quests.add(stats);
	}
	
	public static String[][] getQuests() {
		String[][] stats = new String[quests.size()][];
		for(int i = 0; i < quests.size(); i++) {
			stats[i] = quests.get(i);
		}
		return stats;
	}
	

	public static void setSpells() {

		String[] FireBall = { "Fire Ball", "HP", "20", "1" };

		setSpell(FireBall);

	}

	public static void SetItems() {
		// public Item(String name, int damage, int cost, Element element,
		// String type, Spells spelleffect)
		String[] Sword = { "Sword", "1", "1", Element.PHYSICAL, "Right Hand",
				"null" };

		String[] Sheild = { "Shield", "1", "1", Element.PHYSICAL, "Left Hand",
				"null", "" };

		String[] Pauldrens = {
				"Pauldrens", "1", "1", Element.PHYSICAL, "Shoulders", "null"
		};
		
		String[] Chestguard = { "Chest Plate", "1", "1", Element.PHYSICAL,
				"Upper Torso", "null" };

		String[] LegGuards = { "Leg Plate", "1", "1", Element.PHYSICAL,
				"Legs", "null" };

		String[] Boots = { "Boots", "1", "1", Element.PHYSICAL, "Feet",
				"null" };

		String[] Gloves = { "Gloves", "1", "1", Element.PHYSICAL, "Hands",
				"null" };

		String[][] stats = { Sword, Sheild, Pauldrens, Chestguard, LegGuards, Boots,
				Gloves };

		for (String[] stat : stats) {
			setItems(stat);
		}

	}
	
	public static void SetEntities() {
		final String[] Humanoid = {
				"Humanoid {",
				"\tHead, Neck, Upper Torso, Shoulders, Arms,",
				"\tLower Torso, Hands, Legs, Feet",
				"}"
		};
		
		final String[] Angelic = {
				"Angelic {",
				"\tHead, Neck, Upper Torso, Shoulders, Wings,",
				"\tArms, Lower Torso, Hands, Legs, Feet",
				"}"
		};
		
		final String[] Slime = {
				"Slime {",
				"\tHead",
				"}"
		};
		
		final String[][] stats = {
				Humanoid, Angelic, Slime
		};
		
		for(String[] stat : stats) {
			setEntities(stat);
		}
		
	}
	
	public static void setBosses() {
		final String[] pUndeadStats = { "Undead Priest", "12,9,12,9", "100", "10000",
				"100000", "7000", "2000", "3000", "1000", "7000", "10000",
				"100000", "Dark", "0, 0", "1" };
		
		setBoss(pUndeadStats);
	}
	
	public static void setQuests() {
		String[] fetchQuest = {
				"Name = getItems", "Quest Giver = Queen Jade", "Rewards = Gloves",
				"QuestType = FetchQuest", "Description = Please kill slimes and bring back 1 pounds of their slime.",
				"Ending Dialouge = This was a test quest, if you see this text, it worked. For better or for worse.", "Amount = 1"
		};
		
		String[] bossQuest = {
				"Name = testBossQuest", "Quest Giver = Sir stabofface", "Rewards = Gloves",
				"QuestType = BossQuest", "Description = Please kill Izak for me.", 
				"Ending dialouge = This was a test quest, if you see this text, it worked. For better of for worse."
		};
		
		String[] monsterQuest = {
				"Name = KillMonsters", "Quest Giver = Sir StabYourFace", "Rewards = Sword",
				"QuestType = MonsterQuest", "Description = Please kill 1 slime.",
				"Ending Dialouge = This was a test quest, if you see this, it worked. For better or for worse.", "Amount = 1"
		};
		
		
		
		setQuests(fetchQuest);
		setQuests(bossQuest);
		setQuests(monsterQuest);
	}

}

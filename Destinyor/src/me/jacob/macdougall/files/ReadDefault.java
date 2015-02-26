package me.jacob.macdougall.files;

import me.jacob.macdougall.player.Player;
import me.jacob.macdougall.enemies.Enemy;;

public class ReadDefault {
	
	private static String[] playerHeader = {
		"Players", "Level = ", "X & Y = ", ""
	};
	
	private static String[] enemyHeader = {
		"Enemies", ""
	};
	
	private static String[] npcHeader = {
		"NPCs", ""
	};
	
	private static String[] spellHeader = {
		"Spells", ""
	};
	
	private static String[] itemHeader = {
		"Items", ""
	};
	
	private static String[] playerInfo = {
		"Name = ", "Gender = ", "Level = ", "Experience = ",
		"Health = ", "Strength = ", "Skill = ", "Speed = ",
		"Luck = ", "Defense = ", "Wisdom = ", "Gold = ",
		"Resistances = ", "inParty = "
	};
	
	private static String[] enemyInfo = {
		"Name = ", "Frame = ", "Level = ", "Experience = ",
		"Health = ", "Strength = ", "Skill = ", "Speed = ",
		"Luck = ", "Defense = ", "Wisdom = ", "Gold = ",
		"Resistance = ", "Spells = ", "X&Y = "
	};
	
	private static String[] npcInfo = {
		"Name = ", "Frames = ", "X = ", "Y = ",
		"Dialouge Location = ", "Level = "
	};
	
	private static String[] spellInfo = {
		"", "", "", ""
	};
	
	private static String[] itemInfo = {
		"Name = ", "Damage = ", "Price = ", "Element = ",
		"Limb = ", "Spell = "
	};
	
	public static void addPlayers(String location) {
		String[][] players = Reader.readDefault(location, playerHeader, playerInfo);
		Player.Level = Integer.parseInt(players[0][1]);
		players[0][2] = players[0][2].trim();
		String[] pos = players[0][2].split(",");
		Player.X = Integer.parseInt(pos[0]);
		Player.Y = Integer.parseInt(pos[1]);
		for(int i = 1; i < players.length; i++) {
			Player.addPlayer(Player.readStats(players[i]));
			System.out.println(Player.players.get(i - 1).getName());
		}
	}
	
	public static void addEnemies(String location) {
		String[][] enemies = Reader.readDefault(location, enemyHeader, enemyInfo);
		for(int i = 1; i < enemies.length; i++) {
			Enemy enemy = Enemy.readStats(enemies[i]);
			Enemy.enemies.put(enemy.getName(), enemy);
			System.out.println(enemy.getName());
		}
	}
	
	public static void addNpcs(String location) {
		String[][] npcs = Reader.readDefault(location, npcHeader, npcInfo);
		for(int i = 1; i < npcs.length; i++) {
			
		}
		
	}
}

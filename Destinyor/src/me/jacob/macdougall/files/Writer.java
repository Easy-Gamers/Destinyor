package me.jacob.macdougall.files;

import graphic.engine.window.Resolution;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

import me.jacob.macdougall.GameVariables;
import me.jacob.macdougall.player.Player;
import me.jacob.macdougall.quests.Quest;
import me.jacob.macdougall.quests.Rewards;

public class Writer {

	//private static BufferedWriter bw;

	public static void writeSettingFile(String location) {
		BufferedWriter bw;
		String widthSetting = "Width = " + Resolution.width();
		String heightSetting = "Height = " + Resolution.height();
		String fullscreen = "Window Mode = " + Resolution.getWindowType();
		String fpsLimit = "FPS Limit = " + GameVariables.getFPSLimit();

		try {
			bw = new BufferedWriter(new FileWriter(location));

			bw.write(widthSetting);
			bw.newLine();
			bw.write(heightSetting);
			bw.newLine();
			bw.write(fullscreen);
			bw.newLine();
			bw.write(fpsLimit);

			bw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void writeCutscene(String location) {
		BufferedWriter bw;
		try {
			bw = new BufferedWriter(new FileWriter(location + Files.fileSplit + "default.txt"));

			bw.write("X = 7, Y = 7, Level = 1:");
			bw.newLine();
			bw.write("Jacob: left, left, left, down, down, jacob.txt, jacob.txt, up, up, right, right, right:");
			bw.newLine();
			bw.write("Izak: right, up, up, izak.txt, izak.txt, down, down, left:");

			bw.close();

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void writeNpcs(String location) {
		BufferedWriter bw;
		try {
			bw = new BufferedWriter(new FileWriter(location));

			String[] stats = { "Name = ", "Frames = ", "X = ", "Y = ", "Dialouge Location = ", "Level = " };

			String Format = "NPCs";

			bw.write(Format + " #Note for Modding: Copy and paste the npc characteristics than alter it, also always leave a space between npcs");

			for(String[] npcs : Default.getNpcs()) {
				int i = 0;
				bw.newLine();
				for(String npc : npcs) {
					bw.newLine();
					bw.write(stats[i]);
					bw.write(npc);
					if(npc.contains(".txt")) {
						writeDialouge(Files.DialougesFolder + Files.fileSplit + npc, npcs[0]);
					}
					i++;
				}
				i = 0;
			}
			bw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void writeDialouge(String location, String name) {
		BufferedWriter bw;
		try {
			bw = new BufferedWriter(new FileWriter(location));

			String[] stats = Default.getDialouges(name);

			for(String stat : stats) {
				bw.write(stat);
				bw.newLine();
			}
			bw.close();

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void writeDefaultCharacterFile(String location) {
		BufferedWriter bw;
		int players = 4;
		String player1Name = "Player Name = ";
		String player1Gender = "Player Gender = ";
		String player1Level = "Player Level = " + 1;
		String player1Exp = "Player Experience = " + 0;
		String player1Hp = "Player Health = " + 100;
		String player1Str = "Player Strength = " + 10;
		String player1Skl = "Player Skill = " + 10;
		String player1Spd = "Player Speed = " + 10;
		String player1Luk = "Player Luck = " + 10;
		String player1Def = "Player Defense = " + 10;
		String player1Wis = "Player Wisdom = " + 10;
		String player1Gold = "Player Gold = " + 0;
		String player1Resistance = "Player Resistances = ";

		String name;
		String gender;

		//String player1Spells = "Player Spells = ";
		String player1XY = "X & Y = " + Player.X + "," + Player.Y;
		try {

			bw = new BufferedWriter(new FileWriter(location));
			bw.write("Players");
			bw.newLine();
			bw.write("Level = 1");
			bw.newLine();
			bw.write(player1XY);
			for(int i = 0; i < players; i++) {
				switch (i) {
					case 0:
						name = "Kyro";
						gender = "Male";
						break;
					case 1:
						name = "Karen";
						gender = "Female";
						break;
					case 2:
						name = "Mark";
						gender = "Male";
						break;
					case 3:
						name = "Lesie";
						gender = "Female";
						break;

					default:
						name = "SEND HELP";
						gender = "HELP";
				}
				bw.newLine();
				bw.newLine();
				bw.write(player1Name + name);
				bw.newLine();
				bw.write(player1Gender + gender);
				bw.newLine();
				bw.write(player1Level);
				bw.newLine();
				bw.write(player1Exp);
				bw.newLine();
				bw.write(player1Hp);
				bw.newLine();
				bw.write(player1Str);
				bw.newLine();
				bw.write(player1Skl);
				bw.newLine();
				bw.write(player1Spd);
				bw.newLine();
				bw.write(player1Luk);
				bw.newLine();
				bw.write(player1Def);
				bw.newLine();
				bw.write(player1Wis);
				bw.newLine();
				bw.write(player1Gold);
				bw.newLine();
				bw.write(player1Resistance);
				//bw.newLine();
				//bw.write(player1Spells);
				bw.newLine();
				bw.write("inParty = true");

			}

			bw.close();

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void WriteEnemyFile(String location) {

		String[] Format = { "Enemies", "{", "}" };

		String[] stats = { "Name = ", "Frame = ", "Level = ", "Experience = ", "Health = ", "Strength = ", "Skill = ", "Speed = ", "Luck = ", "Defense = ", "Wisdom = ", "Gold = ", "Resistance = ", "X&Y = " };

		BufferedWriter bw;

		try {

			bw = new BufferedWriter(new FileWriter(location));

			bw.write(Format[0] + " #Note for Modding: Copy and paste the enemy characteristics than alter it, also always leave a space between enemies");

			for(String[] enemies : Default.getEnemies()) {
				int i = 0;
				bw.newLine();
				for(String enemy : enemies) {
					bw.newLine();
					bw.write(stats[i]);
					bw.write(enemy);
					i++;
				}
				i = 0;
			}

			bw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void WriteSpellFile(String location) {
		BufferedWriter bw;
		String[] Format = { "Spells", "{", "}" };

		try {
			bw = new BufferedWriter(new FileWriter(location));

			bw.write(Format[0]);
			bw.newLine();
			bw.newLine();

			for(String[] spells : Default.getSpell()) {
				for(String spell : spells) {
					bw.write(spell);
					bw.newLine();
				}
			}

			bw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void WriteItemFile(String location) {
		BufferedWriter bw;
		String[] stats = { "Name = ", "Damage = ", "Price = ", "Element = ", "Limb = ", "Spell = ", "IsArmour" };

		try {
			bw = new BufferedWriter(new FileWriter(location));
			bw.write("Items");
			bw.newLine();

			for(String[] items : Default.getItems()) {
				int i = 0;
				bw.newLine();
				for(String item : items) {
					bw.write(stats[i] + item);
					bw.newLine();
					i++;
				}
				i = 0;
			}

			bw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void WriteQuests(String location) {
		BufferedWriter bw;
		try {
			bw = new BufferedWriter(new FileWriter(location));
			
			for(int i = 0; i < Quest.quests.size(); i++) {
				Quest quest = Quest.quests.get(i);
					// Bosses
					bw.write(quest.getBosses().getName());
					
					// Npcs
					bw.write(quest.getQuestGiver().getName());
					bw.write(quest.getBosses().getName());
				for(Rewards reward : Quest.quests.get(i).rewards)
					bw.write(reward.name);
			}
			bw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static void WriteFetchQuests(String location, String questName) {
		BufferedWriter bw;
		try {
			bw = new BufferedWriter(new FileWriter(location + questName + Files.EXT));
			
			bw.write(questName);
			//bw.write()
			
			
			bw.close();
		} catch(IOException e) {
			e.printStackTrace();
		}
	}
	
	public static void WriteMainQuests(String location) {
		
	}
	
	public static void WriteBossQuests(String location) {
		
	}
	
	public static void WriteKillingMonstersQuests(String location) {
		
	}

	public static void WriteDefault(String location, String[][] Stats) {
		BufferedWriter bw;

		try {
			bw = new BufferedWriter(new FileWriter(location));

			for(int i = 0; i < Stats.length; i++) {
				for(int j = 0; j < Stats[i].length; j++) {
					bw.write(Stats[i][j]);
					bw.newLine();
				}
			}
			bw.close();

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void WriteDefault(String location, String name, String[][] STATS, String[] format) {
		BufferedWriter bw;

		try {
			bw = new BufferedWriter(new FileWriter(location));

			bw.write(name);
			bw.newLine();

			for(String[] stats : STATS) {
				for(int i = 0; i < stats.length; i++) {
					bw.newLine();
					if(format != null) {
						if(i < format.length) {
							bw.write(format[i]);
						}
					}
					bw.write(stats[i]);
				}
				bw.newLine();
			}

			bw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static void writeSaveFile(String location) {
		BufferedWriter bw;
		
		try {
			bw = new BufferedWriter(new FileWriter(location));
			
			bw.write("Level = " + Player.Level);
			bw.newLine();
			bw.write("X & Y = " + Player.X + ", " + Player.Y);
			bw.newLine();
			
			for(Player player : Player.players) {
				bw.newLine();
				player.writeStats(bw);
				bw.newLine();
			}
			
			// Do quest
			
			// Do items
			
			// Do cutscenes
			
			// Do Cities
			
			bw.close();
		} catch(IOException e) {
			e.printStackTrace();
		}
	}
	
	public static void writeBossFile(String location) {
		
	}
}

package me.jacob.macdougall.files;

import me.jacob.macdougall.ArrayHandler;
import me.jacob.macdougall.DebugWriter;
import me.jacob.macdougall.Destinyor;
import me.jacob.macdougall.GameVariables;
import me.jacob.macdougall.enemies.Boss;
import me.jacob.macdougall.items.Equipment;
import me.jacob.macdougall.magic.Element;
import me.jacob.macdougall.magic.Spells;
import me.jacob.macdougall.npcs.NPC;
import me.jacob.macdougall.npcs.body.Entities;
import me.jacob.macdougall.npcs.body.Limb;
import me.jacob.macdougall.world.LevelMap;

import graphic.engine.window.Resolution;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.File;
import java.io.InputStream;
import java.io.InputStreamReader;

public class Reader {

	public static void readConfig() {
		InputStream input = Destinyor.class.getResourceAsStream("/Config.destinyor");
		InputStreamReader inputReader = new InputStreamReader(input);
		try {
			BufferedReader br = new BufferedReader(inputReader);
			
			String readline = "";
			
			while(readline != null) {
				readline = br.readLine();
				if(readline != null) {
					if(readline.contains("Path")) {
						String path = readline.replace("Path = ", "");
						if(!path.equals("Destinyor")) {
							Files.DestinyorFolder = path;
							Files.setFiles();
						}
					}
				}
			}
			br.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static void ReadNpcs(String location) {
		BufferedReader br;
		String[] Stats = { "Name = ", "Frames = ", "X = ", "Y = ", "Dialouge Location = ", "Level = " };

		try {
			br = new BufferedReader(new FileReader(new File(location)));
			br.readLine();

			int x;
			int y;

			String Name;
			String Frame;
			String dialouge;
			int level;
			String endNpc = br.readLine();
			

			while (endNpc != null) {
				br.skip(Stats[0].length());
				Name = br.readLine();
				br.skip(Stats[1].length());
				Frame = br.readLine().trim();
				br.skip(Stats[2].length());
				x = Integer.parseInt(br.readLine().trim());
				br.skip(Stats[3].length());
				y = Integer.parseInt(br.readLine().trim());
				br.skip(Stats[4].length());
				dialouge = br.readLine();
				br.skip(Stats[5].length());
				level = Integer.parseInt(br.readLine());

				if(dialouge.contains(".txt")) {
					dialouge = ReadDialouge(Files.DestinyorFolder + Files.fileSplit + "Dialouges" + Files.fileSplit + dialouge);
				}

				endNpc = br.readLine();

				NPC.npcs.add(new NPC(Name, Frame, x, y, dialouge, true, LevelMap.maps.get(level)));

				DebugWriter.println("Menu: Reading: Npcs: " + Name);
			}

			br.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		br = null;
	}

	public static String ReadCutscenes(String location) {
		BufferedReader br;
		BufferedReader reader;

		String text = "";
		String[] temptext;
		String nullchecker;
		int lines = 0;
		int i = 0;

		try {
			br = new BufferedReader(new FileReader(new File(location)));
			reader = new BufferedReader(new FileReader(new File(location)));
			while (reader.readLine() != null) {
				lines++;
			}
			reader.close();

			temptext = new String[lines];

			while (i != lines) {
				nullchecker = br.readLine();
				if(nullchecker != null) {
					temptext[i] = nullchecker;
					i++;
				}
			}

			br.close();
			br = null;
			for(String tt : temptext) {
				text += tt;
			}

			return text;
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public static String ReadDialouge(String location) {
		File file = new File(location);

		if(!file.exists()) {
			String location1 = location;
			location = Files.fileSplit + "Dialouges";
			File dir = new File(location);
			dir.mkdirs();

			location = location1;
		}

		String dialouge = "";

		String endDialouge = "";

		try {
			BufferedReader br = new BufferedReader(new FileReader(location));

			while (endDialouge != null) {
				endDialouge = br.readLine();

				if(endDialouge != null) {
					dialouge += endDialouge;
				}
			}

			br.close();
			br = null;
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return dialouge;
	}

	public static void ReadSettings(String location) {
		BufferedReader br;
		String[] Length = { "Width = ", "Height = ", "Window Mode = ", "FPS Limit = " };

		try {

			br = new BufferedReader(new FileReader(location));
			br.mark("@Override".length());
			if(br.readLine().equals("@Override")) {
				Destinyor.Override = true;
				DebugWriter.println("Overriding");
			} else {
				br.reset();
			}
			br.mark("@Debug".length());

			if(br.readLine().equals("@Debug")) {
				Destinyor.Debug = true;
				DebugWriter.println("Debugging");
			} else {
				br.reset();
			}

			br.skip(Length[0].length());
			Resolution.setWidth(Integer.parseInt(br.readLine()));
			br.skip(Length[1].length());
			Resolution.setHeight(Integer.parseInt(br.readLine()));
			br.skip(Length[2].length());
			Resolution.setWindowType(br.readLine());
			br.skip(Length[3].length());
			GameVariables.setFPSLimit(Integer.parseInt(br.readLine()));

			br.close();

		} catch (IOException e) {
			e.printStackTrace();
		}
		br = null;
	}
	
	public static void readSpells(String location) {
		BufferedReader br;
		
		String name;
		String attribute;
		float damage;
		int targets;
		
		String nullChecker = "";
		
		try {
			br = new BufferedReader(new FileReader(location));

			br.readLine();
			br.readLine();
			
			while(nullChecker != null) {
				name = br.readLine();
				attribute = br.readLine();
				damage = Float.parseFloat(br.readLine());
				targets = Integer.parseInt(br.readLine());
				nullChecker = br.readLine();
				Spells.spells.put(name, new Spells(name, damage, targets, attribute));
			}

			//DebugWriter.println("Menu: Adding: " + name);

			//Spells.spells.put(name, new Spells(name, Element.get(type), damage, targets, cost, condition));

			br.close();

		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}

	/**
	 * Reads the item file, has a hidden variable called isArmour, so people can manually say something is armour if needed.
	 * @param location
	 */
	public static void ReadItems(String location) {
		BufferedReader br;

		String nullChecker = "";

		String[] Stats = { "Name = ", "Damage = ", "Price = ", "Element = ", "Limb = ", "Spell = " };

		String[] stats;

		boolean isArmour = false;

		try {
			br = new BufferedReader(new FileReader(location));

			nullChecker = br.readLine();
			br.readLine();

			while (nullChecker != null) {

				stats = new String[Stats.length];

				for(int i = 0; i < stats.length; i++) {
					br.skip(Stats[i].length());
					stats[i] = br.readLine();
				}

				nullChecker = br.readLine();

				if(nullChecker != null && !nullChecker.isEmpty()) {
					isArmour = true;
					nullChecker = br.readLine();
				}

				@SuppressWarnings("unused")
				Equipment equip = new Equipment(stats[0], Integer.parseInt(stats[1]), Integer.parseInt(stats[2]), Element.get(stats[3]), Limb.getLimb(stats[4]), Spells.get(stats[5]), isArmour);
				isArmour = false;

			}

			br.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		br = null;
	}
	
	public static String[][] readDefault(String location, String[] header, String[] args) {
				ArrayHandler ah = new ArrayHandler();
				BufferedReader br;
				try {
					if(location.startsWith("/")) {
						InputStream input = Destinyor.class.getResourceAsStream(location);
						InputStreamReader inputReader = new InputStreamReader(input);
						br = new BufferedReader(inputReader);
					} else {
						File file = new File(location);
						br = new BufferedReader(new FileReader(file));
					}
					
					
					
					if(header != null) {
						String readline;
						//String[] headerInfo = null;
						for(int i = 0; i < header.length; i++) {
							br.skip(header[i].length());
							readline = br.readLine();
							ah.add(readline);
						}
						ah.close();
					}
					
					String nullChecker = "";
					String readline = "";
					
					while(nullChecker != null) {
						for(int i = 0; i < args.length; i++) {
							br.skip(args[i].length());
							readline = br.readLine();
							ah.add(readline);	
						}
						ah.close();
						nullChecker = br.readLine();
					}
				br.close();
				} catch (IOException e) {
					e.printStackTrace();
				} catch (Exception e) {
					e.printStackTrace();
					System.err.println(e.getMessage());
					System.exit(1);
				}
				String[][] info = new String[ah.get().length][];
				String[] singleInfo;
				for(int i = 0; i < ah.get().length; i++) {
					singleInfo = new String[ah.get()[i].length];
					for(int j = 0; j < ah.get()[i].length; j++) {
						singleInfo[j] = (String) ah.get()[i][j];
					}
					info[i] = singleInfo;
				}
				return info;
			}
	
	

	public static void readEntities(String location) {
		BufferedReader br;
		
		String name;
		String limbs = "";

		try {
			br = new BufferedReader(new FileReader(location));

			String nullChecker = br.readLine();

			String stringChecker = br.readLine();

			while (nullChecker != null) {
				nullChecker = br.readLine();
				
				if(nullChecker != null)
					stringChecker = nullChecker.trim();
				else
					break;
				
				if(stringChecker != null && stringChecker.contains("{")) {
					name = stringChecker.replace("{", "").trim();
					
					while (!stringChecker.contains("}")) {
						limbs += br.readLine().trim();
						stringChecker = limbs;
					}
					
					limbs = limbs.replace("}", "").trim();
					Entities entity = new Entities(name, limbs);
					limbs = "";
					entity.put();
				}
			}

			br.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		br = null;
	}
	
	public static void readBosses(String location) {
		BufferedReader br;
		
		String nullChecker = "";

		String[] Stats = { "Name = ", "Frame = ", "Level = ", "Experience = ", "Health = ", "Strength = ", "Skill = ", "Speed = ", "Luck = ", "Defense = ", "Wisdom = ", "Gold = ", "Resistance = ", "X&Y = ", "Level = " };

		String[] stats;
		
		try {
			br = new BufferedReader(new FileReader(location));
			
			nullChecker = br.readLine();
			br.readLine();

				while (nullChecker != null) {

					stats = new String[Stats.length];

					for(int i = 0; i < Stats.length; i++) {
						br.skip(Stats[i].length());
						stats[i] = br.readLine();
					}
					
					stats[1] = stats[1].replace(", ", ",");
					String[] frame = stats[1].split(",");
					int[] frames = new int[frame.length];
					for(int i = 0; i < frames.length; i++)
						frames[i] = Integer.parseInt(frame[i]);
					
					int[] stat = new int[10];
					for(int i = 2; i < stat.length; i++) {
						stat[i - 2] = Integer.parseInt(stats[i]);
					}
					
					stats[13] = stats[13].replace(", ", ",");
					String[] xy = stats[13].split(",");
					
					int x = Integer.parseInt(xy[0]);
					int y = Integer.parseInt(xy[1]);
					
					Boss boss = new Boss(stats[0], 
							frames[0], frames[1], frames[2], frames[3], stat[0], stat[1], stat[2], stat[3], stat[4], stat[5], stat[6], stat[7], stat[8], stat[9],
							Element.get(stats[12]), x, y, LevelMap.maps.get(Integer.parseInt(stats[14])));
					nullChecker = br.readLine();
				}
			
			br.close();
		} catch(IOException e) {
			e.printStackTrace();
		}
	}
}

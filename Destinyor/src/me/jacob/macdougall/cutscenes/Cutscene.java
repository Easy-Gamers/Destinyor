package me.jacob.macdougall.cutscenes;

import me.jacob.macdougall.DebugWriter;
import me.jacob.macdougall.Time;
import me.jacob.macdougall.files.Files;
import me.jacob.macdougall.files.FileLoader;
import me.jacob.macdougall.files.Reader;
import me.jacob.macdougall.input.Keys;
import me.jacob.macdougall.npcs.NPC;
import me.jacob.macdougall.player.Move;
import me.jacob.macdougall.player.Player;
import me.jacob.macdougall.world.LevelMap;

import java.util.Map;
import java.util.HashMap;

public class Cutscene {

	//Cutscene will take up the most temporaly memory and will probably take the most time to load.

	public static Map<String, Cutscene> cutscenes = new HashMap<>();
	private final Map<Integer, Integer> directions = new HashMap<>();
	public Map<Integer, NPCs> npc = new HashMap<>();

	public static String getName;

	public String name;
	public String[] Holder;
	public String[] names;
	public String[] commands;
	public String[][] stuff;
	public String text;
	private String textHolder;
	public int[][] direction;
	public int[] dir;
	private String[] info;
	public int x, y;
	public int level;
	public int npcs = 0;
	public boolean finished = false;
	public static boolean playing = false;
	public boolean isQuest = false;
	public NPCs cNpc;
	public int cI = 0;
	private int cJ = 0;
	private int dx = 0;
	private int dy = 0;
	public int dd = 0;

	// Direction, location, founder
	private int dxf = 0;
	private int dyf = 0;
	private int ddf = 0;

	public Cutscene(String location) {

		name = location.replace((Files.CutsceneFolder + Files.fileSplit), "");
		textHolder = FileLoader.readCutscenes(location).replaceAll(" ", "");
		Holder = textHolder.split(":");
		
		int holder = 0;
		int namesH = 0;

		names = new String[(int) (Math.ceil(Holder.length / 2))];

		for(int i = 1; i < Holder.length; i += 2) {
			// 1, 3
			names[namesH] = Holder[i];
			namesH++;

		}

		stuff = new String[(Holder.length / 2)][];

		for(int i = 2; i < Holder.length; i += 2) {
			stuff[holder] = Holder[i].split(",");
			holder++;
		}

		info = Holder[0].split(",");

		for(String infos : info) {
			if(infos.contains("X")) {
				x = Integer.parseInt(String.valueOf(infos.charAt(2)));
			}
			if(infos.contains("Y")) {
				y = Integer.parseInt(String.valueOf(infos.charAt(2)));
			}
			if(infos.contains("Level")) {
				level = Integer.parseInt(String.valueOf(infos.charAt(6)));
			}
			if(infos.contains("isQuest")) {
				isQuest = true;
			}
		}

		int nX = 0, nY = 0;
		String nDialouge = "";

		for(int i = 0; i < stuff.length; i++) {

			//npcs.put(names[i], stuff[i]);
			dir = new int[stuff[i].length];
			for(int j = 0; j < stuff[i].length; j++) {

				if(stuff[i][j].contains("left")) {
					directions.put(j, 1);
					dir[j] = 0;
					dxf++;
				}

				if(stuff[i][j].contains("right")) {
					directions.put(j, -1);
					dir[j] = 0;
					dxf++;
				}

				if(stuff[i][j].contains("down")) {
					directions.put(j, 1);
					dir[j] = 1;
					dyf++;
				}

				if(stuff[i][j].contains("up")) {
					directions.put(j, -1);
					dir[j] = 1;
					dyf++;
				}

				if(stuff[i][j].contains(".txt")) {
					directions.put(j, 0);
					dir[j] = 2;
					ddf++;
					nDialouge = Reader.readDefault(Files.DialougesFolder + stuff[i][j]);
				}

				if(stuff[i][j].contains("x = ")) {
					nX = Integer.parseInt(String.valueOf(stuff[i][j].charAt(4)));
				}

				if(stuff[i][j].contains("y = ")) {
					nY = Integer.parseInt(String.valueOf(stuff[i][j].charAt(4)));
				}
				
				if(nX == 0)
					nX = NPC.getNpc(names[i]).getX();
				

				if(nY == 0)
					nY = NPC.getNpc(names[i]).getY();
			}

			direction = new int[3][];
			direction[0] = new int[dxf];
			direction[1] = new int[dyf];
			direction[2] = new int[ddf];

			dxf = 0;
			dyf = 0;
			ddf = 0;
			int d = 0;

			for(int j = 0; j < dir.length; j++) {
				switch (dir[j]) {
					case 0:
						direction[dir[j]][dxf] = directions.get(d);
						dxf++;
						break;
					case 1:
						direction[1][dyf] = directions.get(d);
						dyf++;
						break;
					case 2:
						direction[2][ddf] = directions.get(d);
						ddf++;
						break;
				}
				d++;
			}

			dxf = 0;
			dyf = 0;
			ddf = 0;
			NPCs Npc;
			NPC parent = NPC.getNpc(names[i]);
			Npc = new NPCs(names[i], parent.getStringFrame(), nX, nY, nDialouge);
			Npc.changeLevel(LevelMap.maps.get(level));
			npc.put(npc.size(), Npc);
			Npc.direction = direction;

			Npc.dir = dir;
			dir = null;
			direction = null;
			nX = 0;
			nY = 0;
			npcs++;
		}
		cutscenes.put(name, this);
		DebugWriter.println("Menu: Adding: Cutscene: " + name + " At: " + x + ", " + y);
	}

	public void startCutscene() {
		if(!isQuest) {
			if(Player.X == x && Player.Y == y && LevelMap.level == level && !finished) {

				if(!playing) {
					DebugWriter.println("Starting Cutscene: " + name + " At: " + x + ", " + y);
					playing = true;
					getName = this.name;
					Move.canMove = false;
					Time.resetCutsceneTimer();
				}
			}
		}
	}
	
	public void startQuestCutscene() {
		if(Player.X == x && Player.Y == y && LevelMap.level == level && !finished) {
			if(!playing) {
				DebugWriter.println("Starting Cutscene: " + name + " At: " + x + ", " + y);
				playing = true;
				getName = this.name;
				Move.canMove = false;
				Time.resetCutsceneTimer();
			}
		}
	}

	public void update() {
		if(cNpc != null) {
			if(cNpc.Moving && Time.cutsceneTimer(60)) {
				cNpc.Moving = false;
				cJ++;
			}
			if(!Time.cutsceneTimer(60) && cNpc.Moving) {
				return;
			}

			if(Keys.Enter() && cNpc.isSpeaking() && Time.getKeyTimer(10, false)) {
				cNpc.stopSpeaking();
				dd++;
				cJ++;
				Time.resetKeyTimer();
			}
			if(!Keys.Enter() && cNpc.isSpeaking()) {
				return;
			}
		}
		//       an if loop that is similar to a for loop, without the return

		if(this.cI < names.length) {
			
			cNpc = npc.get(cI);

			if(this.cJ < cNpc.dir.length) {
				
				switch(cNpc.dir[cJ]) {
					case 0: moveX(cNpc.direction[cNpc.dir[cJ]][dx]); break;
					case 1: moveY(cNpc.direction[cNpc.dir[cJ]][dy]); break;
					case 2: cNpc.Speaking(dd); break;
				}
				
				if(cNpc.Moving || cNpc.isSpeaking()) {
					return;
				}
				
			}
			dx = 0;
			dy = 0;
			dd = 0;
			cJ = 0;

			cI++;
			return;
		}
		finished = true;
	}

	public void moveX(int x) {
		cNpc.setXRelative(x);
		cNpc.Moving = true;
		dx++;
		Time.resetCutsceneTimer();
	}

	public void moveY(int y) {
		cNpc.setYRelative(y);
		cNpc.Moving = true;
		dy++;
		Time.resetCutsceneTimer();
	}

	public void stopCutscene() {
		finished = true;
		playing = false;
		delete();
		Move.canMove = true;
	}

	public void delete() {
		for(NPCs n : npc.values()) {
			n.delete();
		}
	}

}

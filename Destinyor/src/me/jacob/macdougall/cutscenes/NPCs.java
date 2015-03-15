package me.jacob.macdougall.cutscenes;

import me.jacob.macdougall.npcs.Keywords;
import me.jacob.macdougall.npcs.NPC;

import java.util.ArrayList;
import java.util.List;

public class NPCs extends NPC {
	// Temporay cloning class used for cutscenes, then gets deleted
	//public static Map<Integer, NPCs> cNpcs = new HashMap<>();

	public static List<NPCs> cNpcs = new ArrayList<>();

	public int[][] direction;
	public int[] dir;
	public boolean Moving = false;

	/**
	 * 
	 * @param name
	 * @param frame
	 * @param x
	 * @param y
	 * @param Dialouge
	 * @param id
	 * @param parentID
	 */
	public NPCs(String name, String frame, int x, int y, String Dialouge) {
		super(name, frame, x, y, Dialouge, false);
		//this.cutscene = true;
		cNpcs.add(this);
	}

	//	public NPCs(String name, String frame, int x, int y, String dialouge) {
	//		super(name, frame, x, y, dialouge, false);
	//		cNpcs.add(this);
	//	}

	public void delete() {
		NPCs.cNpcs.remove(this);
	}

	public void Speaking(int i) {
		//Destinyor.Refresh();
		this.setSpeaking(true);
		this.dL = i;
		dialouge[dL] = Keywords.setKeyWords(dialougePerm[dL]); // Keeps updating the speech incase the player changes characters.
	}
}

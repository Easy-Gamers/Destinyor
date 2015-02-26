package me.jacob.macdougall.files.saves;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import me.jacob.macdougall.GameVariables;
import me.jacob.macdougall.files.Files;
import me.jacob.macdougall.magic.Spells;
import me.jacob.macdougall.player.Player;

public class Save {
	
	public static List<Save> saves = new ArrayList<>();
	
	private File saveFile;
	/**
	 * Achievements file holds data like, quest completed, cutscenes watched, ect.
	 */
	private File achievements;
	private int id;
	
	public Save(String location, int id) {
		saveFile = new File(location + Files.fileSplit + "Characters.destinyor");
		achievements = new File(location + Files.fileSplit + "Achievements.destinyor");
		if(!achievements.exists() || !saveFile.exists()) {
			createSave();
		}
		this.id = id;
	}
	
	public int getID() {
		return id;
	}
	
	private void createSave() {
		if(saveFile.mkdir() && !saveFile.exists()) {
			writeSave();
		}
		if(achievements.mkdir() && !achievements.exists()) {
			writeAchievements();
		}
	}
	
	private void writeSave() {
		try {
			BufferedWriter bw = new BufferedWriter(new FileWriter(saveFile));
			
			for(Player player : Player.players) {
				bw.newLine();
				player.writeStats(bw);
				bw.newLine();
			}
			for(Player player : Player.players) {
				bw.newLine();
				bw.write(player.getName() + " spells = {");
				bw.newLine();
				if(player.spells.size() > 0) { 
					int s = 0;
					int i = 0;
					for(Spells spell : player.spells.values()) {
						
						if(s < player.spells.size() - 1)
							bw.write(spell.getName() + ", ");
						else
							bw.write(spell.getName());
						
						if(i > 4) {
							bw.newLine();
							i = 0;
						}
						
						s++;
						i++;
					}
				}
				bw.newLine();
				bw.write("}");
				bw.newLine();
			}
			
			bw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	private void writeAchievements() {
		try {
			BufferedWriter bw = new BufferedWriter(new FileWriter(achievements));
			
			bw.write("Difficultly = " + GameVariables.getDifficultly().toString());
			bw.write("Level = " + Player.Level);
			bw.write("X & Y = " + Player.X + "," + Player.Y);
			
			bw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void loadSave() {
		readSave();
		readAchievements();
	}
	
	private void readSave() {
		try {
			BufferedReader br = new BufferedReader(new FileReader(saveFile));
			
			br.close();
		} catch (IOException e) {
			
			e.printStackTrace();
		}
	}
	
	private void readAchievements() {
		try {
			BufferedReader br = new BufferedReader(new FileReader(achievements));
			
			br.close();
		} catch (IOException e) {
			
			e.printStackTrace();
		}
	}
	
}

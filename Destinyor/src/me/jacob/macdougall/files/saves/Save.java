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
import me.jacob.macdougall.files.ReadDefault;
import me.jacob.macdougall.files.Reader;
import me.jacob.macdougall.magic.Spells;
import me.jacob.macdougall.player.Player;

public class Save {
	
	public static List<Save> saves = new ArrayList<>();
	
	private File saveFile;
	/**
	 * Achievements file holds data like, quest completed, cutscenes watched, ect.
	 */
	private File achievements;
	
	private File spells;
	private File equipment;
	private File inventory;
	
	private int id;
	
	public Save(String location, int id) {
		File folder = new File(location);
		if(!folder.exists()) {
			folder.mkdirs();
		}
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
		if(!saveFile.exists()) {
			writeSave();
		}
		if(!achievements.exists()) {
			writeAchievements();
		}
	}
	
	private void writeSave() {
		try {
			BufferedWriter bw = new BufferedWriter(new FileWriter(saveFile));
			bw.write("Players");
			bw.newLine();
			bw.write("Level = " + Player.Level);
			bw.newLine();
			bw.write("X & Y = " + Player.X + "," + Player.Y);
			//bw.newLine();

			
			for(Player player : Player.players) {
				bw.newLine();
				bw.newLine();
				player.writeStats(bw);
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
			
			bw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void loadSave() {
		readSave();
		readAchievements();
	}
	
	private void writeSpells() {
		try {
			BufferedWriter bw = new BufferedWriter(new FileWriter(spells));
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
		} catch(IOException e) {
			e.printStackTrace();
		}
		
	}
	
	private void readSave() {
		ReadDefault.addPlayers(saveFile.getAbsolutePath());
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

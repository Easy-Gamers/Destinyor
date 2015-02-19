package me.jacob.macdougall.files.saves;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import me.jacob.macdougall.files.Files;
import me.jacob.macdougall.player.Player;

@SuppressWarnings("unused")
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
		if(!saveFile.exists()) {
			createSave();
		}
		achievements = new File(location + Files.fileSplit + "Achievements.destinyor");
		this.id = id;
	}
	
	public int getID() {
		return id;
	}
	
	public void createSave() {
		if(saveFile.mkdir()) {
			writeSave();
		}
		if(achievements.mkdir()) {
			writeAchievements();
		}
	}
	
	private void writeSave() {
		try {
			BufferedWriter bw = new BufferedWriter(new FileWriter(saveFile));
			
			bw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	private void writeAchievements() {
		try {
			BufferedWriter bw = new BufferedWriter(new FileWriter(achievements));
			
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

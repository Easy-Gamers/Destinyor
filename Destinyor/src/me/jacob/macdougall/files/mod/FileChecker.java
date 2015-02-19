package me.jacob.macdougall.files.mod;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import me.jacob.macdougall.files.Files;
import me.jacob.macdougall.files.FileLoader;

public class FileChecker {

	public static List<File> directories = new ArrayList<>();
	
	public static final String characters = "Characters.destinyor";
	public static final String enemies = "Enemies.destinyor";
	public static final String spells = "Spells.destinyor";
	public static final String npcs = "Npcs.destinyor";
	public static final String items = "Items.destinyor";

	public static final String spriteSheet = "Icon.png";
	public static final String character1Sheet = "Character1.png";
	public static final String character2Sheet = "Character2.png";
	public static final String character3Sheet = "Character3.png";
	public static final String character4Sheet = "Character4.png";

	public static String[] fileNames = { characters, enemies, spells, npcs, items };

	public static String[] pictureNames = { spriteSheet, character1Sheet, character2Sheet, character3Sheet, character4Sheet };

	public FileChecker() {
		File file = FileLoader.CreateFolderAndReturn(Files.ModFolder);
		if(file.isDirectory()) {
			for(File dir : file.listFiles()) {
				if(dir.isDirectory()) {
					directories.add(dir);
				}
			}
		}
	}
	
	public void setMod(String file) {
		for(File files : directories) {
			if(files.getName().contains(file)) {
				for(String fileName : fileNames)
					if(files.getPath().contains(fileName))
						Files.setFile(fileName, files.getPath());

				for(String pictureName : pictureNames)
					if(files.getPath().contains(pictureName))
						Files.setPictures(pictureName);
				}
			}
		}	

}

package me.jacob.macdougall.files.mod;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import me.jacob.macdougall.ArrayHandler;
import me.jacob.macdougall.files.Files;
import me.jacob.macdougall.files.FileLoader;

public class FileChecker {
	
	public List<File> directories = new ArrayList<>();
	public List<File> files = new ArrayList<>();
	
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
	
	public FileChecker(String location) {
		File file = FileLoader.CreateFolderAndReturn(location);
		if(file.isDirectory()) {
			for(File dir : file.listFiles()) {
				if(dir.isDirectory()) {
					directories.add(dir);
					for(File dirFiles : dir.listFiles()) {
						files.add(dirFiles);
					}
				}
			}
		}
	}
	
	public void setMod(String file) {
		for(File files : directories) {
			if(files.getName().contains(file)) {
				
				for(File modFiles : files.listFiles())
					for(String fileName : fileNames)
						if(modFiles.getName().contains(fileName))
							Files.setFile(fileName, modFiles.getPath());

				for(String pictureName : pictureNames)
					if(files.getPath().contains(pictureName))
						Files.setPictures(pictureName);
			}
		}
	}
 	
	
	public File getFile(int i) {
		return files.get(i);
	}
	
	public File getFile(String name) {
		for(File file : files) {
			if(file.getName().equals(name)) {
				return file;
			}
		}
		return null;
	}

}

package me.jacob.macdougall.files.mod;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import me.jacob.macdougall.ArrayHandler;
import me.jacob.macdougall.files.Files;
import me.jacob.macdougall.files.FileLoader;

public class FileChecker {

	private static List<File> directories = new ArrayList<>();
	public static List<Mod> mods = new ArrayList<>();
	
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
					mods.add(new Mod(dir.getName(), dir.listFiles()));
					System.out.println(dir.getName());
				}
			}
		}
	}
	
	public void setMod(String file) {
		for(File files : directories) {
			System.out.println(file);
			System.out.println(files.getName());
			if(files.getName().contains(file)) {
				for(File modFiles : files.listFiles()) {
					for(String fileName : fileNames) {
						System.out.println(modFiles.getName());
						if(modFiles.getName().contains(fileName))
							Files.setFile(fileName, modFiles.getPath());
					}
				}

				for(String pictureName : pictureNames)
					if(files.getPath().contains(pictureName))
						Files.setPictures(pictureName);
			}
		}
	}
	
	private static Mod[] checkCompatibleMods(Mod checkMod) {
		List<Mod> cMods = new ArrayList<>();
		boolean compatible = true;
		for(Mod mod : mods) {
			if(mod.getName() != checkMod.getName()) {
				for(File file : checkMod.getFiles()) {
					if(!mod.checkCompatibility(file)) {
						compatible = false;
						break;
					}
					
				}
			}
			if(compatible)
				cMods.add(mod);
			else
				compatible = true;
		}
		
		Object[] array = ArrayHandler.convertSingle(cMods);
		Mod[] mods = new Mod[array.length];
		
		for(int i = 0; i < array.length; i++) {
			mods[i] = (Mod) array[i];
		}
		
		return mods;
	}
	
//	public static Mod[] convertToMods(List<Mod> mods) {
//		Mod[] modArray = new Mod[mods.size()];
//		for(int i = 0; i < mods.size(); i++) {
//			modArray[i] = mods.get(i);
//		}
//		return modArray;
//	}
 	
	public static Mod[] getMods(Mod checkMod) {
		if(checkMod != null) {
			return checkCompatibleMods(checkMod);
		} else {
			
			Object[] array = ArrayHandler.convertSingle(mods);
			Mod[] modArray = new Mod[array.length];
			
			for(int i = 0; i < array.length; i++) {
				modArray[i] = (Mod) array[i];
			}
			
			return modArray;
			//return convertToMods(mods);
		}
	}
	
	public static Mod getMod(int i) {
		return mods.get(i);
	}
	
	public static Mod getMod(String name) {
		for(Mod mod : mods) {
			if(mod.getName().equals(name)) {
				return mod;
			}
		}
		return null;
	}

}

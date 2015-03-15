package me.jacob.macdougall.files.mod;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import me.jacob.macdougall.ArrayHandler;

public class Mod {
	
	public static List<Mod> mods = new ArrayList<>();
	
	private File[] files;
	
	private String name;
	
	public Mod(String name, File... files) {
		this.name = name;
		this.files = files;
	}
	
	/**
	 * Checks to see if two mods have the same file
	 * @param fileName File.getName() of the current file being checked
	 * @return Returns false if it's not compatible, otherwise returns true
	 */
	public boolean checkCompatibility(String fileName) {
		for(File file : files) {
			if(file.getName().equals(fileName)) {
				return false;
			}
		}
		return true;
	}
	
	/**
	 * Checks to see if two mods have the same file
	 * @param fileName File.getName() of the current file being checked
	 * @return Returns false if it's not compatible, otherwise returns true
	 */
	public boolean checkCompatibility(File file) {
		for(File mainFile : files) {
			if(mainFile.getName().equals(file.getName())) {
				return false;
			}
		}
		return true;
	}
	
	public String getName() {
		return name;
	}
	
	public File[] getFiles() {
		return files;
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

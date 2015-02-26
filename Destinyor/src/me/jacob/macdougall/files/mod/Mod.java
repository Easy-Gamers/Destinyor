package me.jacob.macdougall.files.mod;

import java.io.File;

public class Mod {
	
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
	
}

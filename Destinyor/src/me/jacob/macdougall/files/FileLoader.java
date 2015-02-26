package me.jacob.macdougall.files;

import java.io.*;

import me.jacob.macdougall.ArrayHandler;

public class FileLoader {

	// Allows the coder to only do Fileloader.CreateFiles, ReadFiles, WriteFiles in one method

	public static void CreateFolder(String location) {
		File file = new File(location);
		if(!file.exists()) {
			file.mkdir();
		}
	}
	
	public static boolean checkIfFolderIsEmpty(String location) {
		File file = new File(location);
		
		if(file.isDirectory()) {
			if(file.list().length == 0) {
				return true;
			}
			if(file.list().length > 0) {
				return false;
			}
		}
		return false;
	}
	
	public static String[] getFilesAndFolders(String location, String files) {
		if(!checkIfFolderIsEmpty(location)) {
			File file = new File(location);
			if(file.isDirectory()) {
				return fileChecker(file.list(), files);
			}
		}
		return null;
	}
	
	public static String[] fileChecker(String[] files, String check) {
		ArrayHandler ah = new ArrayHandler();
		//String[] list = null;
		for(String file : files) {
			if(accept(file, check)) {
				ah.add(file);
			}
		}
		
		ah.close();
		
		String[] arrayOfFiles = new String[ah.get()[0].length];
		for(int i = 0; i < files.length; i++) {
			arrayOfFiles[i] = (String) ah.get()[0][i];
		}
		
		return arrayOfFiles;
	}
	
	@SuppressWarnings("unused")
	private static String[] infiniteArray(String[] array, String object) {
		if(array != null) {
			String[] temp = new String[array.length + 1];
			
			for(int i = 0; i < array.length; i++) {
				temp[i] = array[i];
			}
			
			temp[array.length] = object;
			
			array = temp;
		
		} else {
			String[] temp = {object};
			array = temp;
		}
		
		return array;
	}
	
	public static boolean accept(String file, String check) {
		return file.contains(check);
	}

	/**
	 * Creates the folder if it needs to be created and returns the folder, if the folder already exists it just returns the folder.
	 * @param location
	 * @return
	 */
	public static File CreateFolderAndReturn(String location) {
		File file = new File(location);
		if(!file.exists()) {
			file.mkdir();
		}
		return file;
	}

	public static void CreateFile(String location) {
		File file = new File(location);
		if(!file.exists()) {
			String path;

			path = location;
			location = Files.DestinyorFolder;
			File dir = new File(location);
			dir.mkdirs();

			location = path;
			WriteToFiles(location);
		}
	}
	
	public static void CreateDefaultFile(String location, String name, String[][] STATS, String[] format) {
		File file = new File(location);
		if(!file.exists()) {
			
			file = CreateFolderAndReturn(Files.DestinyorFolder);
			
			Writer.WriteDefault(location, name, STATS, format);
		}
	}

	public static void ReadFromFiles(String location) {
		if(location.equals(Files.Settings)) {
			Reader.ReadSettings(location);
		}

		if(location.equals(Files.Spells)) {
			Reader.readSpells(location);
		}

//		if(location.equals(Files.Npcs)) {
//			Reader.ReadNpcs(location);
//		}

		if(location.equals(Files.Items)) {
			Reader.ReadItems(location);
		}
	}

	public static void WriteToFiles(String location) {

		if(location.equals(Files.Settings)) {
			Writer.writeSettingFile(location);
		}
//		if(location.equals(Files.Characters)) {
//			Writer.writeDefaultCharacterFile(location);
//		}

		if(location.equals(Files.Spells)) {
			Writer.WriteSpellFile(location);
		}

//		if(location.equals(Files.Npcs)) {
//			Writer.writeNpcs(location);
//		}

	}

	public static String readCutscenes(String location) {
		return Reader.ReadCutscenes(location);
	}

	public static String readDialouges(String location) {
		return Reader.ReadDialouge(location);
	}
}
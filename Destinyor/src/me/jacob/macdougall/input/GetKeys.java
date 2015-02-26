package me.jacob.macdougall.input;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import me.jacob.macdougall.Destinyor;
import me.jacob.macdougall.files.Files;

public class GetKeys {
	
	public static void getKeys() {
		File file;
		file = new File(Files.DestinyorFolder + Files.fileSplit + "Keys.txt");
		if(file.exists()) {
			readKeys(file);
		} else {
			file = new File(Destinyor.class.getResource("/Keys.txt").getFile());
			readKeys(file);
		}
	}
	
	
	
	public static void readKeys(File file) {
		try {
			BufferedReader br = new BufferedReader(new FileReader(file));
			
			String nullChecker = "";
			String[] key;
			while(nullChecker != null) {
				nullChecker = br.readLine();
				if(nullChecker != null) {
					nullChecker = nullChecker.trim();
					key = nullChecker.split("=");
					KeyChanger.setKeys(key[0].trim(), key[1].trim());
				}
			}
			
			br.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
}

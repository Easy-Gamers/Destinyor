package me.jacob.macdougall.files.saves;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class SaveData {
	
	private File characters;
	
	
	public SaveData(String location) {
		characters = new File(location);
		if(!characters.exists()) {
			characters.mkdirs();
		}
	}
	
	public void readCharacter() {
		String nullChecker = "";
		
		try {
			BufferedReader br = new BufferedReader(new FileReader(characters));
			br.readLine();
			br.readLine();
			
			do {
				
			} while(nullChecker != null);
			
			br.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void writeCharacters() {
		
	}
	
	public String readLine(BufferedReader br, String skip) throws IOException {
		br.skip(skip.length());
		return br.readLine();
	}
	
}

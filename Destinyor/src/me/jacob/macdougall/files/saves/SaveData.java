package me.jacob.macdougall.files.saves;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import me.jacob.macdougall.files.Files;
import me.jacob.macdougall.files.ReadDefault;
import me.jacob.macdougall.player.Player;
import me.jacob.macdougall.world.LevelMap;

public class SaveData {
	
	private String characters;
	
	
	public SaveData(String location) {
		File file = new File(location);
		if(!file.exists()) {
			file.mkdirs();
		}
		characters = location + "Characters" + Files.EXT;
	}
	
	public void readCharacter() {
		ReadDefault.addPlayers(characters);
	}
	
	public void writeCharacters() {
		try {
			BufferedWriter bw = new BufferedWriter(new FileWriter(new File(characters)));
			
			bw.write("Players");
			bw.newLine();
			bw.write("Level = " + LevelMap.level);
			bw.newLine();
			bw.write("X & Y = " + Player.X + "," + Player.Y);
			
			
			for(Player player : Player.players) {
				bw.newLine();
				bw.newLine();
				player.writeStats(bw);
			}
			
			bw.close();
		} catch (IOException e) {
			
		}
	}
	
	public String readLine(BufferedReader br, String skip) throws IOException {
		br.skip(skip.length());
		return br.readLine();
	}
	
}

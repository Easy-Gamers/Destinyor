package me.jacob.macdougall.files;

import me.jacob.macdougall.Destinyor;
import me.jacob.macdougall.magic.Element;

public class SetGame {
	
	
	private static String input;
	private static String[] splitter;
	
	public static void setSpells() {
		//String name, Element element, int damage,  int targets, int cost, String Condition
		String[] stats = {
				"Name = ", "Element = ", "Damage = ", "Targets = ", "Condition = "
		};
		
		
		input = file.engine.reader.Reader.Read(Destinyor.DestinyorSpells);
		splitter = input.split("");
		
		for(int i = 1; i < splitter.length; i++) {
			
		}
		
		
	}
	
}

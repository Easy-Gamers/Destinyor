package me.jacob.macdougall.files;

import java.util.Locale;

import me.jacob.macdougall.GameVariables;

public class Files {
	
	// Contains every single file to be used by the game
	
	public static final String home = System.getProperty("user.home");
	public static final String fileSplit = System.getProperty("file.separator");
	
	public static final Locale LOCALE = Locale.getDefault();
    public static String DestinyorFolder = home + fileSplit + "Documents" + fileSplit + GameVariables.getActualTitle();
    public static String DestinyorHome = DestinyorFolder + fileSplit;
    public static final String EXT = ".destinyor";
    
    public static String DialougesFolder = DestinyorHome + "Dialouges";
    public static String CutsceneFolder = DestinyorHome + "Cutscenes";
    public static String ModFolder = DestinyorHome + "Mod";
    public static String SaveFolder = DestinyorHome + "Saves";
    public static String FileFolder = DestinyorHome + "Game Files";
    public static String QuestFolder = DestinyorHome + "Quests";
    
    public static String Characters = DestinyorHome + "Characters" + EXT;
    public static String Enemies = DestinyorHome + "Enemies" + EXT;
    public static String Settings = DestinyorHome + "Settings" + ".ini";
    public static String Spells = DestinyorHome + "Spells" + EXT;
    public static String Npcs = DestinyorHome + "Npcs" + EXT;
    public static String Items = DestinyorHome + "Items" + EXT;
    public static String Entities = DestinyorHome + "Entities" + EXT;
    public static String Bosses = DestinyorHome + "Bosses" + EXT;
    //public static String Quests = QuestFolder + fileSplit;
    
    public static String SpriteSheet = "/icon0.png";
    public static String DestinyorCharacter1Sheet = "/Character1.png";
    public static String DestinyorCharacter2Sheet = "/Character2.png";
    public static String DestinyorCharacter3Sheet = "/Character3.png";
    public static String DestinyorCharacter4Sheet = "/Character4.png";
    public static String DestinyorEnemiesSheet = "/Enemies.png";
    public static String DestinyorMap = "/map.png";
    public static String DestinyorFont = "/8font.png";
    
    public static void setFiles() {
    	DestinyorHome = DestinyorFolder + fileSplit;
    	DialougesFolder = DestinyorHome + "Dialouges";
    	CutsceneFolder = DestinyorHome + "Cutscenes";
    	ModFolder = DestinyorHome + "Mod";
    	SaveFolder = DestinyorHome + "Saves";
    	QuestFolder = DestinyorHome + "Quests";
    	Characters = DestinyorHome + "Characters" + EXT;
    	Enemies = DestinyorHome + "Enemies" + EXT;
    	Settings = DestinyorHome + "Settings" + ".ini";
    	Spells = DestinyorHome + "Spells" + EXT;
    	Npcs = DestinyorHome + "Npcs" + EXT;
    	Items = DestinyorHome + "Items" + EXT;
    	Entities = DestinyorHome + "Entities" + EXT;
    	Bosses = DestinyorHome + "Bosses" + EXT;
    }
    
    
    public static void setFile(String file, String url) {
    	if(Characters.contains(file) || file.contains("Characters.destinyor"))
    		Characters = url;
    	
    	else
    		
    	if(Enemies.contains(file))
        	Enemies = url;
    	
    	else
    		
    	if(Spells.contains(file))
    		Spells = url;
    	
    	else
    		
    	if(Npcs.contains(file))
    		Npcs = url;
    	
    	else
    		
    	if(Items.contains(file))
    		Items = url;
    	
    	else
    		
    	if(Entities.contains(file))
    		Entities = url;
    		
    }
    
    public static void setPictures(String file) {
    	if(SpriteSheet.contains(file)) {
    		SpriteSheet = fileSplit + file;
    		SpriteSheet = ModFolder + SpriteSheet;
    	}
    	if(DestinyorCharacter1Sheet.contains(file)) {
    		DestinyorCharacter1Sheet = fileSplit + file;
    		DestinyorCharacter1Sheet = ModFolder + DestinyorCharacter1Sheet;
    	}
    	if(DestinyorCharacter2Sheet.contains(file)) {
    		DestinyorCharacter2Sheet = fileSplit + file;
    		DestinyorCharacter2Sheet = ModFolder + DestinyorCharacter2Sheet;
    	}
    	if(DestinyorCharacter3Sheet.contains(file)) {
    		DestinyorCharacter3Sheet = fileSplit + file;
    		DestinyorCharacter3Sheet = ModFolder + DestinyorCharacter3Sheet;
    	}
    	if(DestinyorCharacter4Sheet.contains(file)) {
    		DestinyorCharacter4Sheet = fileSplit + file;
    		DestinyorCharacter4Sheet = ModFolder + DestinyorCharacter4Sheet;
    	}
    	if(DestinyorEnemiesSheet.contains(file)) {
    		DestinyorEnemiesSheet = fileSplit + file;
    		DestinyorEnemiesSheet = ModFolder + DestinyorEnemiesSheet;
    	}
    	if(DestinyorMap.contains(file)) {
    		DestinyorMap = fileSplit + file;
    		DestinyorMap = ModFolder + DestinyorMap;
    	}
    	if(DestinyorFont.contains(file)) {
    		DestinyorFont = fileSplit + file;
    		DestinyorFont = ModFolder + DestinyorFont;
    	}
    }
	
	
}

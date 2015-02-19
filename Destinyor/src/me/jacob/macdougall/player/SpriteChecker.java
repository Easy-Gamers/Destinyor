package me.jacob.macdougall.player;

import graphic.engine.screen.Bitmap;
import graphic.engine.screen.SpriteHandler;

import java.io.File;

import me.jacob.macdougall.Destinyor;
import me.jacob.macdougall.files.Files;

public class SpriteChecker {
    
	//private static Sprites player = new Sprites()
	private static Bitmap[][] defaultSprite = new SpriteHandler(Files.DestinyorCharacter1Sheet, 32, 32, Destinyor.class).getSpritesheet();
	//private static Bitmap[][] moddedSprite = new Sprites(Files.DestinyorCharacter1Sheet, 32, 32).getSpritesheet();
	
	public static final boolean exists = new File(Files.DestinyorCharacter1Sheet).exists();
	public static final Bitmap[][] sprite = (exists) ? new SpriteHandler(Files.DestinyorCharacter1Sheet, 32, 32).getSpritesheet() : defaultSprite;
	
	public static final Bitmap[][] frame1 = 	
	{
		{ sprite[0][3],  sprite[1][3], sprite[2][3], sprite[3][3]}, // Up
		{ sprite[0][0],  sprite[1][0], sprite[2][0], sprite[3][0] }, // Down
		{ sprite[0][1],  sprite[1][1], sprite[2][1], sprite[3][1] }, // Left
		{ sprite[0][2],  sprite[1][2], sprite[2][2], sprite[3][2] } // Right
	};
        
	public static final Bitmap[][] frames = frame1;
	
	
	
}

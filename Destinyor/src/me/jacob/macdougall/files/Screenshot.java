package me.jacob.macdougall.files;

import java.io.File;
import java.io.IOException;

import graphic.engine.screen.Screen;

import javax.imageio.ImageIO;

public class Screenshot {
	
	private int id = 0;
	
	private String path;
	
	public String getPath() {
		return path;
	}
	
	public Screenshot(Screen screen) {
		File image = new File("Screenshot" + id + ".png");
		try {
			ImageIO.write(screen.image, "PNG", image);
		} catch (IOException e) {
			e.printStackTrace();
		}
		id++;
	}
	
	public Screenshot(Screen screen, String FilePath) {
		File image = new File(FilePath + Files.fileSplit + "Icon.png");
		path = FilePath + Files.fileSplit + "Icon.png";
		try {
			ImageIO.write(screen.image, "PNG", image);
		} catch(IOException e) {
			e.printStackTrace();
		}
	}
	
	
}

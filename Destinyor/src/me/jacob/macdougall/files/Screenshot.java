package me.jacob.macdougall.files;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import graphic.engine.screen.Screen;

import javax.imageio.ImageIO;

import me.jacob.macdougall.Destinyor;

public class Screenshot {
	
	private int id = 0;
	
	private String path;
	
	private BufferedImage image;
	
	public String getPath() {
		return path;
	}
	
	public Screenshot(Screen screen) {
		File image = new File("Screenshot" + id + ".png");
		try {
			ImageIO.write(screen.image, "PNG", image);
			this.image = ImageIO.read(image);
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
			this.image = ImageIO.read(image);
		} catch(IOException e) {
			e.printStackTrace();
		}
	}
	
	public Screenshot(String FilePath) {
		File file = new File(FilePath);
		if(!file.exists())
			file.mkdirs();
		File image = new File(FilePath + Files.fileSplit + "Icon.png");
		
		path = FilePath + Files.fileSplit + "Icon.png";
		try {
			ImageIO.write(Destinyor.getScreen().image, "PNG", image);
			this.image = ImageIO.read(image);
		} catch(IOException e) {
			e.printStackTrace();
		}
	}
	
	public BufferedImage getImage() {
		return image;
	}
	
	
}

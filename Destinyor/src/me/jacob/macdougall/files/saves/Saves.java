package me.jacob.macdougall.files.saves;

import graphic.engine.screen.Art;
import graphic.engine.screen.Bitmap;
import graphic.engine.screen.GameFont;
import graphic.engine.screen.Screen;
import graphic.engine.window.Resolution;

import input.engine.mouse.Mouse;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

import me.jacob.macdougall.DebugWriter;
import me.jacob.macdougall.Destinyor;
import me.jacob.macdougall.files.FileLoader;
import me.jacob.macdougall.files.Files;
import me.jacob.macdougall.files.Screenshot;
import me.jacob.macdougall.graphics.Sprites;
import me.jacob.macdougall.graphics.UI;
import me.jacob.macdougall.gui.Buttons;
import me.jacob.macdougall.gui.GUI_Objects;
import me.jacob.macdougall.gui.MenuHandler;
import me.jacob.macdougall.gui.Scroll;
import me.jacob.macdougall.gui.Scroller;
import me.jacob.macdougall.gui.TextBox;
import me.jacob.macdougall.world.Tile;

public class Saves {
	
	public static List<Saves> saves = new ArrayList<>();
	
	public static Scroller s;
	private int id = 0;
	private String name;
	private Bitmap image;
	private String filePath;
	private GUI_Objects saveslot;
	private static TextBox namer = new TextBox("Namer", Resolution.width() / 4 - 60, Resolution.height() / 4 - 20, 120, 20, Sprites.getSprite(Sprites.BUTTON, 0, 0));
	private static Buttons createSave = new Buttons("Create Save", 330, 300, 120, 20);
	
	public Saves(String name, int id, Bitmap image) {
		this.name = name;
		this.image = image;
		this.id = id;
		filePath = "Save" + getID();
		saveslot = new GUI_Objects("Saveslot", 0, id * (4 * Tile.SIZE), 512 - 17, 96 - 8);
		Bitmap sprite = saveslot.getSprite();
		for(int i = 0; i < sprite.pixels.length; i++) {
			sprite.pixels[i] = Color.RED.getRGB();
		}
		saveslot.setSprite(sprite);
	}
	
	public static void save(Destinyor game) {
		MenuHandler.menu = MenuHandler.NONE;
		game.renderSave();
		String path = Files.SaveFolder + Files.fileSplit + "Save" + getID(saves.size());
		System.out.println("Creating save: " + path);
		Screenshot screenshot = new Screenshot(path);
		DebugWriter.println(screenshot.getPath());
		Save save = new Save(path, saves.size());
		MenuHandler.menu = MenuHandler.getLastMenuState();
		saves.clear();
		loadSaves();
		Destinyor.refresh();
	}
	
	public String getID() {
		if(id < 10) {
			return "00" + id;
		}
		if(id < 100) {
			return "0" + id;
		}
		return Integer.toString(id);
	}
	
	public static String getID(int id) {
		if(id < 10) {
			return "00" + id;
		}
		if(id < 100) {
			return "0" + id;
		}
		return Integer.toString(id);
	}
	
	public String getFilePath() {
		return filePath;
	}
	
	public void render(Screen screen, int num) {
		if(image != null)
			screen.render(image, 7, (96 * num) + 6);
		
		GameFont.render(name, screen, 196, (96 * num) + 32);
		
		GameFont.render("Save: " + filePath, screen, 196, (96 * num) + 64);
	}
	
	
	
	public static void renderSaveScreen(Screen screen) {
		UI.renderSaves(screen);
		
		if(s != null) {
			s.render(screen);
			s.getAllCurrent();
			for(int i = 0; i < s.getAllCurrent().length; i++) {
				get(s.getLocation() + i).render(screen, i);
			}
			
		}
		
		if(namer.getFocused())
			namer.render(screen);
		createSave.render(screen);
	}
	
	public void update(Mouse mouse) {
			
	}
	
	public static void update(Mouse mouse, Destinyor game) {
		if(MenuHandler.menu == MenuHandler.SAVES) {
			if(s != null)
				s.update(mouse);
			if(createSave.inBox(mouse.getPressed(0), mouse.getPressed(1), mouse.getReleased(0), mouse.getReleased(1))) {
				namer.setFocused(true);
				mouse.reset();
			}
			if(namer.getFocused()) {
				namer.update();
				if(!namer.getFocused()) {
					save(game);
				}
			}
		}
		
	}
	
	public static void add(Saves save) {
		saves.add(save);
	}
	
	public static Saves get(int i) {
		if(!saves.isEmpty()) {
			if(i >= saves.size() || saves.get(i) == null) {
				return null;
			} else {
				return saves.get(i);
			}
		}
		return null;
	}
	
	public static void loadSaves() {
		System.out.println("Checking saves");
			String[] saveFiles = FileLoader.getFilesAndFolders(Files.SaveFolder, "Save");
			if(saveFiles != null && saveFiles.length > 0) {
				GUI_Objects[] objects = new GUI_Objects[saveFiles.length];
				for(int i = 0; i < saveFiles.length; i++) {
					System.out.println("Found save: " + saveFiles[i]);
					add(new Saves(saveFiles[i], i, Art.getAndConvert(Files.SaveFolder + Files.fileSplit + saveFiles[i] + Files.fileSplit + "Icon.png", 128, 96, false)));
					objects[i] = get(i).saveslot;
					System.out.println("Added save: " + saveFiles[i]);
				}
				
				s = new Scroller("Saves", 8, 8, 512 - 17, 96 - 8, 3, Scroll.Y, objects);
				s.setSpace(Tile.SIZE / 4);
				System.out.println("Saves found");
			} else {
				System.out.println("No saves found");
			}
	}
	
	
}

package me.jacob.macdougall.launcher.gui;

import graphic.engine.screen.Screen;
import graphic.engine.window.Resolution;

import input.engine.mouse.Mouse;

import java.util.HashMap;
import java.util.Map;

import me.jacob.macdougall.Destinyor;
import me.jacob.macdougall.files.Files;
import me.jacob.macdougall.files.Writer;
import me.jacob.macdougall.files.mod.FileChecker;
import me.jacob.macdougall.gui.Buttons;
import me.jacob.macdougall.gui.DropDowns;
import me.jacob.macdougall.gui.GUI;
import me.jacob.macdougall.launcher.Launcher;

public class MenuHandler {
	
	public static final int NONE = -1, MAIN = 0, MODS = 1, OPTIONS = 2;
	
	public Map<Integer, GUI> menus = new HashMap<>();
	
	private static final String[][] buttonNames = {
		{ "Main", "Mods", "Options", "Start", "Exit" },
		{ "Main", "Mods", "Options" },
		{ "Main", "Mods", "Options", "Accept", "Cancel" }
	};
	
	private static final String[][] dropDownNames = {
		{ "Resolution", "Window" }
	};
	
	public static int menu = MAIN;
	
	
	private void createMainMenu() {
		Buttons[] buttons;
		int i = 0;
		
		buttons = new Buttons[buttonNames[MAIN].length];
				
		for(i = 0; i < buttons.length - 2; i++)
			buttons[i] = new Buttons(buttonNames[MAIN][i], 64 + (130 * i), 386 / 8);
		for(i = buttons.length - 2; i < buttons.length; i++)
			buttons[i] = new Buttons(buttonNames[MAIN][i], 100 + (200 * (i - (buttons.length - 2))), 300);
				
		buttons[0].setEnabled(false);
				
		GUI gui = new GUI("Main Launcher", MAIN);
		gui.add(buttons);
		menus.put(MAIN, gui);
	}
	
	private void createModsMenu() {
		Buttons[] buttons;
		int i = 0;
		
		buttons = new Buttons[buttonNames[MODS].length + FileChecker.directories.size()];
		for(i = 0; i < buttonNames[MODS].length; i++) {
			buttons[i] = new Buttons(buttonNames[MODS][i], 64 + (130 * i), 386 / 8);
		}
		buttons[1].setEnabled(false);
		int x = 64;
		int y = 80;
		int yy = 0;
		for(int j = 0; i < buttons.length; j++) {
			buttons[i] = new Buttons(FileChecker.directories.get(j).getName(), x, y + (25 * yy));
			yy++;
			if(yy > 9) {
				x = 64 + 260;
				yy = 0;
			}
			i++;
		}
		GUI gui = new GUI("Mod Menu", MODS);
		gui.add(buttons);
		menus.put(MODS, gui);
	}
	
	private void createOptionsMenu() {
		Buttons[] buttons;
		DropDowns[] dropDowns;
		int i = 0;
		
		buttons = new Buttons[buttonNames[OPTIONS].length];
		
		for(i = 0; i < buttons.length - 2; i++) {
			buttons[i] = new Buttons(buttonNames[OPTIONS][i], 64 + (130 * i), 386 / 8);
		}
		
		for(i = buttons.length - 2; i < buttons.length; i++) {
			buttons[i] = new Buttons(buttonNames[OPTIONS][i], 64 + (258 * (i - (buttons.length - 2))), 266);
		}
		
		buttons[2].setEnabled(false);
		
		GUI gui = new GUI("Options Menu", OPTIONS);
		gui.add(buttons);
		
		buttons = new Buttons[Resolution.Length()];
		
		for(i = 0; i < Resolution.Length(); i++) {
			buttons[i] = new Buttons(Resolution.getResolution(i), 0, 0);
		}
		
		DropDowns res = new DropDowns(dropDownNames[0][0], 64, 153, 120, 20, 3, Resolution.getResolutionInt(), buttons);
		
		buttons = new Buttons[3];
		
		Buttons window = new Buttons("Windowed" , 0, 0);
		Buttons border = new Buttons("Borderless" , 0, 0);
		Buttons full = new Buttons("FullScreen" , 0, 0);
		
		buttons[0] = window;
		buttons[1] = border;
		buttons[2] = full;
		
		DropDowns win = new DropDowns(dropDownNames[0][1], 322, 153, 120, 20, 3, 0, buttons);
		
		dropDowns = new DropDowns[2];
		dropDowns[0] = res;
		dropDowns[1] = win;
		
		gui.add(dropDowns);
		
		menus.put(OPTIONS, gui);
	}
	
	public MenuHandler() {
		createMainMenu();
		createModsMenu();
		createOptionsMenu();
	}
	
	public void update(Destinyor game, Mouse mouse) {
		if(menu != NONE) {
			menus.get(menu).update();
			GUI gui = menus.get(menu);
			gui.update(mouse);
			if(gui.focused != null) {
				switch(gui.focused.getName()) {
					
					case "Main":
						menu = MAIN;
						System.out.println("main");
						gui.reset();
						break;
						
					case "Mods": 
						menu = MODS;
						System.out.println("mod");
						gui.reset();
						break;
					
					case "Options": 
						menu = OPTIONS;
						System.out.println("option");
						gui.reset(); 
						break;
						
					case "Start": 
						menu = NONE;
						mouse.resetMouseWheel();
						gui.reset();
						Launcher.launch();
						break;
					
					
						
					case "Exit": 
						gui.reset(); 
						System.exit(0); 
						break; // Unreachable code.
						
					case "Accept":
						if(!Resolution.getResolution(Resolution.getResolutionInt(gui.dropDowns.get("Resolution").getCurrent().getName())).equals((Resolution.width() + " * " + Resolution.height()))) {
							int width = Resolution.getWidth(Resolution.getResolutionInt(gui.dropDowns.get("Resolution").getCurrent().getName()));
							int height = Resolution.getHeight(Resolution.getResolutionInt(gui.dropDowns.get("Resolution").getCurrent().getName()));
							Resolution.setWindowType(gui.dropDowns.get("Window").getCurrent().getName());
							acceptRes(game, width, height);
						}
						menu = MAIN;
						gui.reset();
						Destinyor.refresh();
						break;
					
					case "Cancel":
						gui.reset();
						break;
					
					case "Resolution": break;
					case "Window": break;
				}
			}
		}
	}
	
	public void render(Screen screen) {
		if(menu != NONE) {
			menus.get(menu).render(screen);
		}
	}
	
	public void acceptRes(Destinyor game, int width, int height) {
		Resolution.setWidth(width);
		Resolution.setHeight(height);
		Writer.writeSettingFile(Files.Settings);
		Resolution.setWidth(800);
		Resolution.setHeight(600);
	}
}

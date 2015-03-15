package me.jacob.macdougall.gui;

import graphic.engine.screen.Screen;
import graphic.engine.window.Resolution;

import input.engine.mouse.Mouse;

import java.util.HashMap;
import java.util.Map;

import me.jacob.macdougall.Destinyor;
import me.jacob.macdougall.Time;
import me.jacob.macdougall.graphics.Sprites;
import me.jacob.macdougall.input.Keys;
import me.jacob.macdougall.player.Move;

public class MenuHandler {
	
	public static final int NONE = -1, MAIN = 0, MENU = 1, OPTIONS = 2,
			BATTLES = 3, SAVES = 4;
	
	public Map<Integer, GUI> menus = new HashMap<>();
	
	private static final String[][] buttonNames = {
		{ "New Game", "Resume Game", "Load Game", "Options", "Exit" },
		{ "Resume", "Options", "Save", "Load", "Exit" },
		{ "Accept", "Cancel" }
	};
	
	private static final String[][] dropDownNames = {
		{ "Resolution", "Window" }
	};
	
	public static int menu = MAIN;
	
	public static boolean mainMenu = true;
	
	public static int getLastMenuState() {
		if(mainMenu)
			return MAIN;
		else
			return MENU;
	}
	
	public MenuHandler() {
		Buttons[] buttons;
		DropDowns[] dropDowns;
		//TextBox[] textboxes;
		
		// Main Menu
		buttons = new Buttons[buttonNames[MAIN].length];
		
		for(int i = 0; i < buttons.length; i++) {
			buttons[i] = new Buttons(buttonNames[MAIN][i], 192, (112) + i * 30);
		}
		
		GUI gui = new GUI("Main Menu", MAIN);
		gui.add(buttons);
		menus.put(MAIN, gui);
		
		
		// Pause Menu
		buttons = new Buttons[buttonNames[MENU].length];
		
		for(int i = 0; i < buttons.length; i++) {
			buttons[i] = new Buttons(buttonNames[MENU][i], 192, (112) + i * 20);
		}
		
		gui = new GUI("Pause Menu", MENU);
		gui.add(buttons);
		menus.put(MENU, gui);
		
		// Options Menu
		buttons = new Buttons[buttonNames[OPTIONS].length];
		
		for(int i = 0; i < buttons.length; i++) {
			buttons[i] = new Buttons(buttonNames[OPTIONS][i], 120  * (i + 1), 32 + 120);
		}
		
		gui = new GUI("Options Menu", OPTIONS);
		gui.add(buttons);
		
		buttons = new Buttons[Resolution.Length()];
		
		for(int i = 0; i < Resolution.Length(); i++) {
			buttons[i] = new Buttons(Resolution.getResolution(i), 130, 0);
		}
		
		DropDowns res = new DropDowns(dropDownNames[0][0], 130, 32, 120, 20, 3, Resolution.getResolutionInt(), buttons);
		
		buttons = new Buttons[3];
		
		Buttons window = new Buttons("Window" , 382, 32 - 21);
		Buttons border = new Buttons("Borderless" , 382, 32);
		Buttons full = new Buttons("FullScreen" , 382, 32 + 21);
		
		buttons[0] = window;
		buttons[1] = border;
		buttons[2] = full;
		
		DropDowns win = new DropDowns(dropDownNames[0][1], 382, 32, 120, 20, 3, 0, buttons);
		
		dropDowns = new DropDowns[2];
		dropDowns[0] = res;
		dropDowns[1] = win;
		
		gui.add(dropDowns);
		
		gui.add(new TextBox("FPSLimiter", 100, 300, 120, 20, Sprites.getSprite(Sprites.BUTTON, 0, 0)));
		
		menus.put(OPTIONS, gui);
		
		buttons = new Buttons[1];
		buttons[0] = new Buttons("Cancel", 100, 300);
		
		gui = new GUI("Saves", SAVES);
		
		gui.add(buttons);
		
		menus.put(SAVES, gui);
	}
	
	public void update(Destinyor game, Mouse mouse) {
		if(menu != NONE) {
		menus.get(menu).update();
		
		GUI gui = menus.get(menu);
		gui.update(mouse);
		if(gui.focused != null) {
		switch(gui.focused.getName()) {
			case "New Game": 
				menu = NONE;
				mainMenu = false;
				mouse.resetMouseWheel();
				movement(); 
				mouse.resetPressedPos();
				mouse.resetReleasedPos();
				gui.reset();
				Destinyor.refresh();
				break;
				
			case "Resume Game": break;
			case "Load Game": break;
			
			case "Options": 
				menu = OPTIONS;
				gui.reset();
				Destinyor.refresh(); 
				break;
				
			case "Exit": 
				gui.reset(); 
				System.exit(0); 
				break; // Unreachable code.
			
			case "Resume": 
				menu = NONE; 
				movement(); 
				gui.reset(); 
				Destinyor.refresh(); 
				break;
			
			case "Save": 
				menu = SAVES;
				mouse.reset();
				gui.reset();
				Destinyor.refresh();
				break;
			case "Load": break;
			case "Accept":
				if(!Resolution.getResolution(Resolution.getResolutionInt(gui.dropDowns.get("Resolution").getCurrent().name)).equals((Resolution.width() + " * " + Resolution.height()))) {
					int width = Resolution.getWidth(Resolution.getResolutionInt(gui.dropDowns.get("Resolution").getCurrent().name));
					int height = Resolution.getHeight(Resolution.getResolutionInt(gui.dropDowns.get("Resolution").getCurrent().name));
					acceptRes(game, width, height);
				}
				menu = MAIN;
				gui.reset();
				Destinyor.refresh();
				break;
			
			case "Cancel":
				menu = getLastMenuState();
				gui.reset(); 
				Destinyor.refresh();
				break;
			
			case "Resolution": break;
			case "Window": break;
			case "FPSLimiter": break;
		}
		//gui.focused = null;
		}
		} else {
			if(Keys.Escape() && Time.getKeyTimer(10, false)) {
				menu = MENU;
				Destinyor.refresh();
				Time.resetKeyTimer();
			}
		}
	}
	
	public void render(Screen screen) {
		if(menu != NONE) {
			menus.get(menu).render(screen);
		}
	}
	
	public static void movement() {
		Move.canMove = !(menu > NONE);
	}
	
//	public void accept(Destinyor game, int width, int height) {
//		
//		
//		
//		Resolution.setWindowType(Window);
//		if(!Window.equalsIgnoreCase(DefaultWindow)) {
//			Destinyor.setSettings();
//			mustRestart = true;
//		}
//	}
	
	public void acceptRes(Destinyor game, int width, int height) {
		Resolution.setWidth(width);
		Resolution.setHeight(height);
		game.setSize(width, height);
		game.setLocation(game.getLocation());
		Destinyor.frame.setSize(width, height);
		Destinyor.frame.setLocation(Destinyor.frame.getLocation());
	}
}

package me.jacob.macdougall.launcher.gui;

import graphic.engine.screen.Screen;
import graphic.engine.window.Resolution;

import input.engine.mouse.Mouse;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import me.jacob.macdougall.ArrayHandler;
import me.jacob.macdougall.Destinyor;
import me.jacob.macdougall.files.Files;
import me.jacob.macdougall.files.Writer;
import me.jacob.macdougall.files.mod.FileChecker;
import me.jacob.macdougall.files.mod.Mod;
import me.jacob.macdougall.graphics.Sprites;
import me.jacob.macdougall.gui.Buttons;
import me.jacob.macdougall.gui.CheckBoxes;
import me.jacob.macdougall.gui.DropDowns;
import me.jacob.macdougall.gui.GUI;
import me.jacob.macdougall.launcher.Launcher;

// I made this it's own class instead of merging it with the already existing MenuHandler because
// the launcher is seperate from the game
// and I don't want to load in stuff that hasn't been create yet in the game data
public class MenuHandler {
	
	public static final int NONE = -1, MAIN = 0, MODS = 1, OPTIONS = 2;
	
	public Map<Integer, GUI> menus = new HashMap<>();
	public List<Mod> mods = new ArrayList<>();
	
	private int actualWidth;
	private int actualHeight;
	
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
		CheckBoxes[] checkboxes;
		int i = 0;
		
		buttons = new Buttons[buttonNames[MODS].length];
		for(i = 0; i < buttonNames[MODS].length; i++) {
			buttons[i] = new Buttons(buttonNames[MODS][i], 64 + (130 * i), 386 / 8);
		}
		buttons[1].setEnabled(false);
		
		checkboxes = new CheckBoxes[Mod.getMods(null).length];
		int x = 64;
		int y = 80;
		int yy = 0;
		for(i = 0; i < checkboxes.length; i++) {
			checkboxes[i] = new CheckBoxes(Mod.getMod(i).getName(), x, y + (25 * yy), 120, 20, false, Sprites.getSprite(Sprites.BUTTON, 0, 0));
			yy++;
			if(yy > 9) {
				x = 64 + 260;
				yy = 0;
			}
			if(yy > 9 * 2) {
				break;
			}
		}
		GUI gui = new GUI("Mod Menu", MODS);
		gui.add(buttons);
		gui.add(checkboxes);
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
		actualWidth = Resolution.width();
		actualHeight = Resolution.height();
		
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
						gui.reset();
						break;
						
					case "Mods": 
						menu = MODS;
						gui.reset();
						break;
					
					case "Options": 
						menu = OPTIONS;
						gui.reset(); 
						break;
						
					case "Start": 
						menu = NONE;
						mouse.resetMouseWheel();
						gui.reset();
						Launcher.launch(getMods());
						break;
					
					case "Exit": 
						gui.reset(); 
						System.exit(0); 
						break; // Unreachable code.
						
					case "Accept":
						if(!Resolution.getResolution(Resolution.getResolutionInt(gui.dropDowns.get("Resolution").getCurrent().getName())).equals((actualWidth + " * " + actualHeight))) {
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
					default: // Assume it's a mod
						modHandler(gui);
						mouse.reset();
						gui.reset();
						break; 
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
	
	public void modHandler(GUI gui) {
		if(!resetting(gui)) {
			if(getFocused(gui).isChecked()) {
			boolean[] enabled = new boolean[gui.checkBoxes.size()];
			Mod[] mods = Mod.getMods(Mod.getMod(gui.focused.getName()));
			
			int j = 0;
			for(int i = 0; i < mods.length; i++) {
				j = 0;
				for(CheckBoxes checkbox : gui.checkBoxes.values()) {
					if(!checkbox.getName().equals(mods[i].getName())) {
						if(!enabled[j])
							enabled[j] = false;
					} else {
						if(checkbox.isEnabled())
							enabled[j] = true;
						else
							enabled[j] = false;
					}
					j++;
				}
			}
			
			int i = 0;
			for(CheckBoxes checkbox : gui.checkBoxes.values()) {
				checkbox.setEnabled(enabled[i]);
				i++;
			}
			addMods(Mod.getMod(gui.focused.getName()));
			} else {
				removeMods(Mod.getMod(getFocused(gui).getName()));
			}
		}
	}
	
	public boolean resetting(GUI gui) {
		CheckBoxes focused = getFocused(gui);
		if(!focused.isChecked()) {
			boolean anychecked = false;
			for(CheckBoxes checkbox : gui.checkBoxes.values()) {
				if(checkbox.isChecked()) {
					anychecked = true;
				}
			}
			if(!anychecked) {
				for(CheckBoxes checkbox : gui.checkBoxes.values()) {
					checkbox.setEnabled(true);
					removeMods(Mod.getMod(checkbox.getName()));
				}
				return true;
			} else {
				for(CheckBoxes checkbox : gui.checkBoxes.values()) {
					if(checkbox.isChecked()) {
						System.out.println("Checkbox " + checkbox.getName() + " is checked.");
						boolean[] enabled = new boolean[gui.checkBoxes.size()];
						Mod[] mods = Mod.getMods(Mod.getMod(checkbox.getName()));
						int j = 0;
						for(int i = 0; i < mods.length; i++) {
							j = 0;
							for(CheckBoxes checkboxes : gui.checkBoxes.values()) {
								if(!checkboxes.getName().equals(mods[i].getName())) {
									if(!enabled[j])
										enabled[j] = false;
								} else {
									enabled[j] = true;
								}
								j++;
							}
						}
						
						int i = 0;
						for(CheckBoxes checkboxes : gui.checkBoxes.values()) {
							checkboxes.setEnabled(enabled[i]);
							i++;
						}
					}
				}
			}
		}
		return false;
	}
	
	public CheckBoxes getFocused(GUI gui) {
		for(CheckBoxes checkbox : gui.checkBoxes.values()) {
			if(gui.focused.getName().equals(checkbox.getName())) {
				return checkbox;
			}
		}
		return null;
	}
	
	public Mod[] getMods() {
		Object[] array = ArrayHandler.convertSingle(mods);
		Mod[] modArray = new Mod[array.length];
		
		for(int i = 0; i < array.length; i++) {
			modArray[i] = (Mod) array[i];
		}
		
		for(int i = 0; i < modArray.length; i++) {
			System.out.println("Loading: " + modArray[i].getName());
		}
		return modArray;
	}
	
	public void addMods(Mod mod) {
		mods.add(mod);
		System.out.println("Adding Mod: " + mod.getName());
		for(Mod cMod : mods) {
			System.out.println("Currently Loaded Mods: " + cMod.getName());
		}
	}
	
	public void removeMods(Mod mod) {
		if(mods.contains(mod)) {
			mods.remove(mod);
			System.out.println("Removing Mod: " + mod.getName());
			if(mods.size() > 0) {
				for(Mod cMod : mods) {
					System.out.println("Currently Loaded Mods: " + cMod.getName());
				}
			} else {
				System.out.println("No Mods Currently Loaded.");
			}
		}
	}
}

package me.jacob.macdougall.gui;

import graphic.engine.screen.Art;
import graphic.engine.screen.GameFont;
import graphic.engine.screen.Screen;
import graphic.engine.window.Resolution;

import input.engine.keyboard.InputHandler;
import input.engine.mouse.Mouse;
import me.jacob.macdougall.DebugWriter;
import me.jacob.macdougall.Destinyor;
import me.jacob.macdougall.GameVariables;
import me.jacob.macdougall.Time;
import me.jacob.macdougall.audio.Sound;
import me.jacob.macdougall.files.Saves;
import me.jacob.macdougall.input.Keys;
import me.jacob.macdougall.player.Move;

import java.util.ArrayList;
import java.util.List;

public class Menus {

	public static final int NONE = -1, MAIN = 0, MENU = 1, OPTIONS = 2,
			BATTLES = 3, SAVES = 4;

	public List<Buttons[]> buttons = new ArrayList<>();

	public String[][] names = { { "New Game", "Resume Game", "Load Game", "Options", "Exit" }, { "Resume", "Options", "Save", "Load", "Exit" },
	//{ "Resolution" }
	};

	public String resolution = Resolution.width() + " * " + Resolution.height();

	public List<Buttons> button = new ArrayList<>();
	public List<TextBox> textboxes = new ArrayList<>();

	// -1 no menu currently open, 0 main, 1 esc, 2 options, 3 battles
	public static int menu = 0;
	
	public static boolean mainMenu = true;

	public static int getLastMenuState() {
		if(mainMenu)
			return MAIN;
		else
			return MENU;
	}

	public InputHandler input;
	public Destinyor game;
	public ResolutionButton rButton = new ResolutionButton(this);
	public boolean isOn = true;
	public int buttonSelected = 0;

	//private int[] pressed = new int[2];

	public Menus(Destinyor game, InputHandler input) {
		this.game = game;
		this.input = input;

		Buttons[][] menus = new Buttons[names.length][];

		for(int i = 0; i < names.length; i++) {
			menus[i] = new Buttons[names[i].length];
			for(int j = 0; j < names[i].length; j++) {
				button.add(new Buttons(names[i][j], 192, (112) + j * 30));
				menus[i][j] = button.get(button.size() - 1);
			}
			buttons.add(menus[i]);
		}

		button.add(new Buttons("Resolution", 10, 32));

		button.add(new Buttons(rButton.resolution, 10 + 120, 32));

		button.add(new Buttons("Accept", 32, 32 + 120));

		button.add(new Buttons("Display Mode: ", 512 - 250, 32));

		button.add(new Buttons(Resolution.Fullscreen, 512 - 130, 32));

		Buttons[] buttons3 = { button.get(button.size() - 5), button.get(button.size() - 4), button.get(button.size() - 3), button.get(button.size() - 2), button.get(button.size() - 1) };

		buttons.add(buttons3);
		
		textboxes.add(new TextBox(100, 100, 120, 20, Art.getButtons()[0][0]));
	}

	public void checkMenu() {
		if(Keys.Escape() && !Cutscene.playing) {
			menu = 1;
			Menus.movement();
		}
	}

	public void render(Screen screen) {

		if(menu > NONE && menu < buttons.size()) {
			int i = 0;
			for(Buttons b : buttons.get(menu)) {
				if(b != null) {
					b.render(screen);
					if(buttonSelected == i)
						screen.render(Art.getFont()[35][1], b.x - 10, b.y + b.getHeight() / 8);
				}
				i++;
			}
		}

		switch (menu) {
			case NONE:
				break;
			case MAIN:
				break;
			case MENU:
				break;
			case OPTIONS:
				rButton.render(screen);
				textboxes.get(0).render(screen);
				break;
			case BATTLES:
				break;
			case SAVES:
				Saves.renderSaveScreen(screen);
				break;
		}
		if(rButton.mustRestart) {
			GameFont.render("You must restart the game in order for these changes to take affect", screen, 512 / 2 - 120, 368 / 2 - 20);
		}

	}

	public void mouseChecker(Mouse mouse, int width, int height) {
		if(menu == NONE) {
			return;
		}

		//pressed = mouse.getPressed();

		if(mouse.getPressed() != null) {
			switch(menu) {
				case MAIN: 
					mainMenu(mouse); 
					break;
				case MENU:
					pauseMenu(mouse);
					break;
				case OPTIONS:
					optionsMenu(mouse);
					break;
			}
			if(menu == MAIN) {
				mainMenu(mouse);
			}
			if(menu == MENU) {
				
			}
			if(menu == OPTIONS) {
				
			}
		}
	}
	
	public void mainMenu(Mouse mouse) {
		for(int i = 0; i < button.size(); i++) {
			if(button.get(i).inBox(mouse.getPressed(Mouse.X), mouse.getPressed(Mouse.Y))) {
				switch (i) {
					case 0:
						isOn = false;
						mainMenu = false;
						menu = -1;
						mouse.resetMouseWheel();
						movement();
						DebugWriter.RemoveDebug("Menu: ");
						break;

					case 1:
						// Load from the biggest numbered save
						break;

					case 2:
						// Open load screen
						break;
					
					case 3:
						menu = OPTIONS;
						break;
						
					case 4:
						for(Sound sounds : Sound.sounds.values()) {
							sounds.stopSound();
							sounds.close();
						}
						System.exit(0);
						break;
				}
			}
		}
	}
	
	public void pauseMenu(Mouse mouse) {
		for(int i = 0; i < button.size(); i++) {
			if(button.get(i).inBox(mouse.getPressed(Mouse.X), mouse.getPressed(Mouse.Y))) {
				switch (i) {
					case 0:
						isOn = false;
						mainMenu = false;
						menu = -1;
						mouse.resetMouseWheel();
						movement();
						DebugWriter.RemoveDebug("Menu: ");
						break;

					case 1:
						menu = OPTIONS;
						break;

					case 2:
						menu = SAVES;
						break;
						
					case 3:
						break;
						
					case 4:
						for(Sound sounds : Sound.sounds.values()) {
							sounds.stopSound();
							sounds.close();
						}
						System.exit(0);
						break;
				}
			}
		}
	}
	
	public void optionsMenu(Mouse mouse) {
		textboxes.get(0).update(mouse);
		if(textboxes.get(0).focused)
			return;
		if(textboxes.get(0).input != "") {
			boolean numbers = true;
			for(char numCheck : textboxes.get(0).input.toCharArray()) {
				if(!Character.isDigit(numCheck))
				{
					numbers = false;
					break;
				}
			}
			if(numbers) {
				GameVariables.setFPSLimit(Integer.parseInt(textboxes.get(0).input));
			}
			
		}
		rButton.setRes(mouse.getPressed(Mouse.X), mouse.getPressed(Mouse.Y));
		rButton.setWindow(mouse.getPressed(Mouse.X), mouse.getPressed(Mouse.Y));
		rButton.setSelected(mouse);
		if(buttons.get(menu)[1].inBox(mouse.getPressed(Mouse.X), mouse.getPressed(Mouse.Y)) && Time.getKeyTimer(10, false)) {
			rButton.rClick();
			Time.resetKeyTimer();
			//Destinyor.Refresh();
		} else if(buttons.get(menu)[2].inBox(mouse.getPressed(Mouse.X), mouse.getPressed(Mouse.Y))) {
			rButton.accept(game);
		} else if(buttons.get(menu)[4].inBox(mouse.getPressed(Mouse.X), mouse.getPressed(Mouse.Y)) && Time.getKeyTimer(10, false)) {
			rButton.wClick();
			Time.resetKeyTimer();
		}
	}

	public void inputChecker() {
		if(Time.getKeyTimer(10, false) && menu != NONE) {
			if(Keys.MoveUp()) {
				buttonSelected -= 1;
				Time.resetKeyTimer();
			}
			if(Keys.MoveDown()) {
				buttonSelected += 1;
				Time.resetKeyTimer();
			}
			if(Keys.Enter()) {
				click();
				Time.resetKeyTimer();
			}
		}
		if(menu != NONE) {
			if(menu < buttons.size() && buttons.get(menu) != null) {
				if(buttonSelected > buttons.get(menu).length - 1) {
					buttonSelected = 0;
				}
				if(buttonSelected < 0) {
					buttonSelected = buttons.get(menu).length - 1;
				}
			}
		}
	}

	public void click() {
		switch (buttonSelected) {
			case 0:
				isOn = false;
				mainMenu = false;
				menu = NONE;
				DebugWriter.RemoveDebug("Menu: ");
				break;
			case 1: break;
			case 2: break;
			case 3:
				menu = OPTIONS;
				break;
			case 4: break;
			case 5:
				for(Sound sounds : Sound.sounds.values()) {
					sounds.stopSound();
					sounds.close();
				}
				System.exit(0);
				break;
		}
		buttonSelected = 0;
	}

	public static void movement() {
		Move.canMove = !(menu > NONE);
	}
}

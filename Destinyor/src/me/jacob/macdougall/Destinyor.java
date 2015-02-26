// http://www.youtube.com/watch?v=psWSGP7qrN4&list=PLE2vW4kTRjCDqxFSI0QqzigRwpdS-gMH6&index=1

// 3 year project, been working on it for around 2 years.
// Made by me.jacob.macdougall@gmail.com

/* To Do:
 * Fix Destinyor game code to make it look pretty and easier to find and change methods and variables // this.setMinPriority(); // HAHA GOOD JOKE! // NEVER GOING TO HAPPEN
 * Implement Music fully // sorta done
 * Implement SoundEffects // working on it.
 * Implement Spells fully 
 * Fix menu for all resolution // TWO YEARS LATER AND IT IS DONE!
 * Get more fps // this.setMinPriority();
 * Add random npc save file // this.setMinPriority();
 * add reader for random npc save file
 * finish implementing items Done?
 * Implement bigger monsters
 * Implement Bosses Working
 * Add non moving npcs for shopkeepers and other essinstial npcs //NOT HAVING IT
 * Add Shopkeepers
 * Make economy 
 * Add Quests and rewards // DONE-ish
 * Add txt moddability to quests and rewards
 * Add UI Adjustments as needed // DONE-ISH
 * Add Battle UI to give more feedback // DONE-ISH
 * Fix bugs // GOOD JOKE
 * Add random text to the npcs
 * Make a timer class // DONE
 * Maybe add semi random duengeons
 * Create element txt file //Why? Because Modding
 * Create effect txt file // ?
 * Change all the reader files to a simple for(int i = 0; i < x lines of code; i++) { br.skip(Stats[i].length); statHolder[i] = br.readLine(); }
 * add txt mod file for limbs // DONE
 * add txt file for keys // DONE
 * add moddable keys aka remapable WORKING ON IT SHEESH // It's 3 months since you've had the ability too! // DONE
 * 
 * it should be attack stat +plus weapon attack - armour and defense right
 * Armour effects psyical damage as well as magic, weapons do not, but can have a wis+ stat.
 * Ninizak: we should be able to target different limbs mayb
 Ninizak: ala VATS
 well we'll try somethings and if we cant get it to work well then we'll scrap it

 JacobIT: Yo izak,
 JacobIT: should keys be double bindable?
 JacobIT: aka space is jump and sprint?
 Ninizak: ye why not
 JacobIT: ... Because people hate that.
 Ninizak: i hate being told no
 JacobIT: I didn't say no.
 Ninizak: just make it appear red if its doubled
 Ninizak: so then they can decide ye or ne
 JacobIT: I told you why not.
 * XML uses around 65,736 bytes of data, might be better to find a way around that or declare it null at some point
 * init uses  8,896 bytes for the strings, then npcs at 3,904
 * NPCs take up the most amount of memory at 12,992 bytes
 * Databuffer takes the most amount of memory at 10,223,600 bytes which is 10 Megabytes
 * The set methods in Art take up 434,864 bytes of data
 * 
 * Heavy armour will have an effect on tp generation
 * JacobIT: Izak, should the player be aloud to distbrute stat points or will it be automatic?
Ninizak: the games about fate n shit so
Ninizak: what if we
Ninizak: have like
Ninizak: an option of like
Ninizak: offense defense and whatever
Ninizak: and youd pick one
Ninizak: and that would sort of influence your levels
Ninizak: so if you had it set to offense itd boost attacking stats primarilly
JacobIT: Okay.

 */

package me.jacob.macdougall;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.BufferStrategy;
import java.util.Random;

import me.jacob.macdougall.audio.Playlist;
import me.jacob.macdougall.audio.Sound;
import me.jacob.macdougall.battles.*;
import me.jacob.macdougall.cutscenes.*;
import me.jacob.macdougall.files.*;
import me.jacob.macdougall.files.mod.FileChecker;
import me.jacob.macdougall.files.mod.Mod;
import me.jacob.macdougall.files.saves.Saves;
import me.jacob.macdougall.graphics.Sprites;
import me.jacob.macdougall.graphics.UI;
import me.jacob.macdougall.gui.MenuHandler;
import me.jacob.macdougall.input.GetKeys;
import me.jacob.macdougall.input.Keys;
import me.jacob.macdougall.items.Equipment;
import me.jacob.macdougall.items.PlayerInventory;
import me.jacob.macdougall.items.PlayerEquipment;
import me.jacob.macdougall.launcher.Launcher;
import me.jacob.macdougall.magic.*;
import me.jacob.macdougall.minimap.Minimap;
import me.jacob.macdougall.npcs.*;
import me.jacob.macdougall.player.*;
import me.jacob.macdougall.threads.Thread_Controller;
import me.jacob.macdougall.world.*;
import graphic.engine.screen.GameFont;
import graphic.engine.screen.Screen;
import graphic.engine.screen.SpriteHandler;

import java.awt.Font;
import javax.swing.JFrame;

import input.engine.keyboard.InputHandler;
import input.engine.mouse.Mouse;
import graphic.engine.screen.Dialouge;
import graphic.engine.window.Resolution;

public class Destinyor extends Canvas implements Runnable {

	private static final long serialVersionUID = 1L;

	public static boolean create = true; // Create the files
	public static boolean write = false; // Write over the files
	public static boolean read = false; // Read the files

	public static boolean Override = false;
	public static boolean Debug = false;

	private static boolean refresh = false;
	
	public static String song = "";

	private static LevelMap map;

	private static Thread thread;
	public static JFrame frame;
	
	// Input Objects
	private static InputHandler input;
	private static Mouse mouse;
	private static Move move;
	private static Battles battle;

	// Graphic Objects
	private static Minimap minimap;
	private static Debug debug;
	private static Screen screen;

	// Entities Objects

	// Other Objects
	private static MenuHandler menu;

	private Cutscene cutscene;
	private Cities city1;
	private PlayerInventory pInv;

	private static final Clock clock = new Clock();

	private boolean running = false;

	private Destinyor() {
		
	}

	private void init() {
		screen = new Screen(1024 / 2, 768 / 2);
		Sound music2 = new Sound(Destinyor.class.getResource("/Orc March.wav").getPath().replace("%20", " "), true);

		@SuppressWarnings("unused")
		Playlist playlist = new Playlist(1, music2);

		GameVariables.setDifficultly(GameVariables.Difficultly.NORMAL);
		map = LevelMap.maps.get(1);
		input = new InputHandler(this);
		GetKeys.getKeys();
		battle = new Battles();
		menu = new MenuHandler();
		Thread_Controller.init(screen, battle);
		Thread_Controller.startAudio();

		if(Debug) {
			debug = new Debug();
		}

		
		minimap = new Minimap(this);

		city1 = new Cities("Default", 12, 12, 100, 2, 5, 16, 19, 1, 2);

		move = new Move(map);

		Thread_Controller.setNpc(move);

		// Start making random npcs
		Random random = new Random();

		int x = random.nextInt(LevelMap.FloorWidth);
		int y = random.nextInt(LevelMap.FloorHeight);

		for(int i = 0; i < 2; i++) {
			for(int j = 0; j < RandomNPCs.names[i].length; j++) {
				RandomNPCs.RandomNpcs(j, i, x, y, map);
				x = random.nextInt(LevelMap.FloorWidth);
				y = random.nextInt(LevelMap.FloorHeight);
			}
		}

		Player.putItInTheBag(Equipment.get("Sword").newInstance());
		Player.putItInTheBag(Equipment.get("Shield").newInstance());
		Player.putItInTheBag(Equipment.get("Chest Plate").newInstance());
		Player.putItInTheBag(Equipment.get("Leg Plate").newInstance());
		Player.putItInTheBag(Equipment.get("Boots").newInstance());
		Player.putItInTheBag(Equipment.get("Gloves").newInstance());

		for(int i = 0; i < Player.inventory.size(); i++) {
			if(Player.inventory.get(i).equippable)
				Player.getMainCharacter().Equip((Equipment) Player.inventory.get(i));
			System.out.println(Player.inventory.get(i).name);
		}

		pInv = new PlayerInventory();

		Thread_Controller.startNPC();

		cutscene = new Cutscene(Files.CutsceneFolder + Files.fileSplit + "default.txt");
	}

	private void RenderSpellBook(Player player) {
		UI.renderInventory(screen);

		if(!player.spells.isEmpty() || player.spells.size() >= 1) {
			for(int i = 1; i < player.spells.size(); i++) {
				Spells spell = player.spells.get(i);
				GameFont.render("Name: " + spell.getName(), screen, 12, 288);
				GameFont.render("Attack: " + spell.getDamage(), screen, 12, 288 + 32);
			}
		}
	}

	private void RenderUI() {
		Player.Attackable = false; // Makes sure the Player can't get into another battle
		UI.render(screen); // Renders UI
		battle.render(Player.getActualPlayers(), screen, AIBattle.enemies); // Renders enemies and Players
	}

	private void renderMenu() {
		//menus.render(screen);
		menu.render(screen);
	}

	public void renderSave() {
		map.render(screen, -Camera.cX, -Camera.cY);
		map.renderObjects(screen);
		for(NPC n : NPC.npcs)
			if(n.inRange())
				n.render(screen);
		
		Player.getMainCharacter().render(screen);
	}
	
	private void RenderMaps() {
		Player.Attackable = true; // Makes Sure Player Can be Attacked By Enemy
		map.render(screen, -Camera.cX, -Camera.cY);
		map.renderObjects(screen);
		if(UI.menu == 0 || UI.menu == 2) {
			if(!Cutscene.playing) {
				npcLoop();
			} else {
				if(Thread_Controller.cNpc != null) {
					if(Thread_Controller.cNpc.isSpeaking()) {
						UI.TextBox(screen);
						Thread_Controller.cNpc.speak();
					}
				}
				for(NPCs n : cutscene.npc.values())
					n.render(screen);
			}
			Time.getObjectTimer(30, true);
			city1.render(screen);
		}
		Player.getMainCharacter().render(screen);
	}
	
	private static void npcLoop() {
		for(NPC n : NPC.npcs)
			
			if(n.inRange()) {
				n.render(screen);
				if(n.isSpeaking())
					n.speak();
			}
	}

	// Starts Game
	private void start() {
		running = true;
		thread = new Thread(this, "MainThread");
		thread.setPriority(Thread.MAX_PRIORITY);
		thread.start();
	}

	// Stops Game
	public void stop() {
		running = false;
		try {
			thread.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		System.exit(0);
	}

	private void render() {
		// Get Current Canvas
		BufferStrategy strategy = this.getBufferStrategy();
		if(strategy == null) {
			// Create Strategy
			createBufferStrategy(3);
			requestFocus();
			return;
		}
		
		// Draw Things with Screen
		// if is faster than switch in this case
		//if(Menus.menu == Menus.NONE) { // If menu is not on render normal
		if(MenuHandler.menu == MenuHandler.NONE) {
			switch(UI.menu) {
				case UI.Map: RenderMaps(); break;
				case UI.Minimap: RenderMaps(); break;
				case UI.Fight: RenderUI(); break;
				case UI.Inventory: pInv.renderInventory(screen); break;
				case UI.Equipment: PlayerEquipment.renderEquipment(screen); break;
				case UI.Spellbook: RenderSpellBook(Player.getMainCharacter()); break;
			}
			if(Cutscene.playing)
				RenderMaps();
			if(!Dialouge.isEmpty())
				UI.TextBox(screen);
			Dialouge.render(screen);
		} else {
			renderMenu();
		}
		
		// Draw Screen onto Frame
		Graphics g = strategy.getDrawGraphics();

		g.drawImage(screen.image, 0, 0, Resolution.width(), Resolution.height(), null);

		if(UI.menu == UI.Fight) {
			battle.renderTime(Player.getActualPlayers(), g);
		}

		g.setColor(Color.GREEN);
		g.setFont(new Font("Arial", Font.BOLD, 16));
		minimap.render(g);

		g.setColor(Color.GREEN);
		g.setFont(new Font("Arial", Font.BOLD, 16));
		g.drawString("Fps: " + String.valueOf(GameVariables.getFps()) + ", " + "Ups: " + String.valueOf(GameVariables.getUps()), 1, 16);
		g.drawString(song, this.getWidth() - (song.length() * 9), 16);
		g.drawString(mouse.getMousePos(0) + ", " + mouse.getMousePos(1), mouse.getMousePos(0), mouse.getMousePos(1));
		if(Debug) {
			g.drawString("Player X Pos = " + Player.X + ", Player Y Pos = " + Player.Y, 1, 33); // Displays Player x and y position
			g.drawString("Steps: " + Move.steps, 1, 65);
			g.drawString("Steps until next battle: " + (Battles.randomBattles - Battles.move), 1, 65 + 23);
			debug.render();
		}

		clock.render(g);

		// Finalize & Dispose
		g.dispose();
		strategy.show();
		
		if(refresh) {
			UI.REFRESH(screen);
			refresh = false;
		}
	}

	// Updates Game Logic
	private void update() {
		Time.tick();
		clock.tick();
		
		Saves.update(mouse, screen, this);
		
		input.tick();

		move.Movement();

		

		city1.goToTown(this);
		pInv.updateInventory(mouse);
		PlayerEquipment.updateEquipment();
		
		menu.update(this, mouse);
		cutscene.startCutscene();

		if(UI.menu == UI.Fight) {
			if(Battles.enemiesCreated) {
				PlayerBattle.update();
				if(AIBattle.enemies != null) {
					battle.assignTarget(mouse, AIBattle.enemies);
					battle.bInput.clicker(mouse, AIBattle.enemies);
				}
			}
		}
	}

	// Thread
	@Override
	// @SuppressWarnings("SleepWhileInLoop")
	public synchronized void run() {
		int fps = 0, update = 0;
		long fps_Timer = System.currentTimeMillis();
		double nsPerUpdate = 1000000000 / 60;
		double fsPerUpdate = 1000000000 / GameVariables.getFPSLimit();
		// Last update in nanoseconds
		double then = System.nanoTime();
		double fThen = System.currentTimeMillis();
		double unprocessed = 0;
		double fUnprocessed = 0;
		while (running) {
			double now = System.nanoTime();
			unprocessed += (now - then) / nsPerUpdate;
			then = now;

			// Update queue
			while (unprocessed >= 1) {
				// Update
				update++;
				if(Thread_Controller.doneLoading) // Don't try and access any variables until after the game has loaded. EVER
					update();
				unprocessed--;
			}

			if(GameVariables.isFpsLimit()) {
				if(fsPerUpdate != 1000000000 / GameVariables.getFPSLimit()) { // Incase the user changes the fps limit mid way through the game
					fsPerUpdate = 1000000000 / GameVariables.getFPSLimit();
				}
				double fNow = System.nanoTime();
				fUnprocessed += (fNow - fThen) / (fsPerUpdate);
				fThen = fNow;
				if(fUnprocessed >= 1) {
					fps++;
					if(Thread_Controller.doneLoading)
						render();
					fUnprocessed--;
				}

			} else {
				fps++;
				if(Thread_Controller.doneLoading)
					render();
			}

			// FPS Timer
			if(System.currentTimeMillis() - fps_Timer > 1000) {
				System.out.printf("\n Main_Thread: %d fps, %d updates", fps, update);
				GameVariables.setFps(fps);
				GameVariables.setUps(update);
				fUnprocessed = 0;
				fps = 0;
				update = 0;
				fps_Timer += 1000;

				try {
					if(GameVariables.isFpsLimit())
						Thread.sleep(1000 / GameVariables.getFPSLimit());
					else
						Thread.sleep(100);
				} catch (InterruptedException e) {
					System.err.println(e.getMessage());
				}
			}
		}
	}

	public static void main(String[] args) {
		Reader.readConfig();
		FileLoader.CreateFolder(Files.DestinyorFolder);
		FileLoader.CreateFile(Files.Settings);
		FileLoader.ReadFromFiles(Files.Settings);
		FileChecker fc = new FileChecker();
		
		Destinyor game = new Destinyor();
		
		Launcher.setLauncher(new Launcher(game));
		
		while(Launcher.isRunning()) { // The while loops needs to do something in order for it to end
			Destinyor.wait(1);
		}
		if(Launcher.getMods() != null && Launcher.getMods().length >= 0) {
			for(Mod mod : Launcher.getMods()) {
				fc.setMod(mod.getName());
				System.out.println("Setting Mod: " + mod.getName());
			}
		} else {
			System.out.println("No mods loaded");
		}
		Launcher.clear();
		
		
		FileLoader.ReadFromFiles(Files.Settings);
		FileLoader.CreateFolder(Files.SaveFolder);
		
		
		
		// http://www.innerbody.com/anatomy/integumentary // for limbs

		Dimension Res = new Dimension(Resolution.width(), Resolution.height());
		
		Sprites.setSprite(Sprites.SPRITE, new SpriteHandler(Files.SpriteSheet, 32, 32, game));
		
		Sprites.setSprite(Sprites.MAP, new SpriteHandler(Files.DestinyorMap, 512, 512, game));
		
		//Sprites.setSprite(Sprites.SCROLL, new SpriteHandler("/Scrollbar.png", 10, 20, game));
		
		
		
		Saves.loadSaves();
		Element.setElements();
		DynamicsLoader.init();
		
		Destinyor.map = LevelMap.maps.get(1);

		FileLoader.CreateFolder(Files.DialougesFolder);
		FileLoader.CreateFolder(Files.CutsceneFolder);
		Writer.writeCutscene(Files.CutsceneFolder);

		Default.SetEntities();
		Default.setEnemies();
		Default.setSpells();
		Default.setNpcs();
		Default.SetItems();
		Default.setBosses();

		Writer.WriteDefault(Files.Bosses, "Bosses", Default.getBosses(), Default.getBossFormat());
		Reader.readBosses(Files.Bosses);

		Thread_Controller.startLoading();

		Destinyor.waitForLoading();

		Reader.readEntities(Files.Entities);
		FileLoader.ReadFromFiles(Files.Items);

		game.setSize(Res);

		if(game.getSize().getWidth() != Res.getWidth() || game.getSize().getHeight() != Res.getHeight())
			game.setSize(Resolution.width(), Resolution.height());

		// Frame
		JFrame frame = new JFrame(GameVariables.getTitle());
		frame.setResizable(false);

		switch (Resolution.getWindowType()) {
			case Resolution.FULLSCREEN:
				frame.setUndecorated(true);
				frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
				break;
			case Resolution.B_WINDOW:
				frame.setUndecorated(true);
				break;
		}

		frame.add(game);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.pack();
		frame.setLocationRelativeTo(null);
		frame.setAlwaysOnTop(true);
		frame.setVisible(true);
		frame.requestFocus();
		frame.requestFocusInWindow();
		Destinyor.mouse = new Mouse(game);

		if(Res != game.getSize() || Res != frame.getSize()) {
			if(frame.getExtendedState() != JFrame.MAXIMIZED_BOTH) {
				game.setSize(Res);
				frame.setSize(Res);
				frame.setLocationRelativeTo(null);
			} else {
				Resolution.setWidth(frame.getWidth());
				Resolution.setHeight(frame.getHeight());
			}
		}
		
		// Starts Game
		game.init();
		Destinyor.frame = frame;
		game.start();

		if(Debug) {
			Player.Attackable = false;
		}
	}
	
	// Okay I figured it out it means: 
	/*
	 * This method is for testing purposes only, the real way to change
	 * the screen is in the move class.
	 * That's what happens when you do a 6 hour coding spree.
	 */
	// Testing only, in move class/file // ENGLISH?
	public static void ChangeScreenToUI() {
		if(Keys.PageDown()) {
			UI.menu = UI.Fight;
			Destinyor.refresh();
		}
	}

	// Testing only, in move class/file // ENGLISH?
	public static void ChangeScreenToMap() {
		if(Keys.Home()) {
			UI.menu = UI.Map;
			Destinyor.refresh();
		}
	}

	private static void waitForLoading() {
		do {
			if(Thread_Controller.doneLoading) {
				Thread_Controller.pauseLoading();
			}
		} while (!Thread_Controller.doneLoading);
	}

	public static void setSettings() {
		int amount = 4;
		
		if(Override)
			amount++;
		
		if(Debug)
			amount++;
		
		String[] Stats = new String[amount];
		int i = 0;
		if(Override) {
			Stats[i] = "@Override";
			i++;
		}

		if(Debug) {
			Stats[i] = "@Debug";
			i++;
		}
		Stats[i] = "Width = " + Resolution.width();
		i++;
		Stats[i] = "Height = " + Resolution.height();
		i++;
		Stats[i] = "Window Mode = " + Resolution.getWindowType();
		i++;
		Stats[i] = "FPS Limit = " + GameVariables.getFPSLimit();
		file.engine.writer.Writer.WriteDefault(Files.Settings, Stats);
	}

	public void changeLevel(int level) {
		map = LevelMap.maps.get(level);
		minimap = new Minimap(this);
		refresh();
	}

	public static void wait(int n) {
		long t0, t1;
		t0 = System.currentTimeMillis();
		do {
			t1 = System.currentTimeMillis();
		} while (t1 - t0 < n);
	}

	public static LevelMap getCurrentLevel() {
		return map;
	}
	
	public static void refresh() {
		refresh = true;
	}

}

package me.jacob.macdougall.launcher;

import input.engine.keyboard.InputHandler;
import input.engine.mouse.Mouse;

import java.awt.Canvas;
import java.awt.Graphics;
import java.awt.image.BufferStrategy;

import javax.swing.JFrame;

import me.jacob.macdougall.Destinyor;
import me.jacob.macdougall.GameVariables;
import me.jacob.macdougall.Time;
import me.jacob.macdougall.files.Files;
import me.jacob.macdougall.files.mod.Mod;
import me.jacob.macdougall.graphics.Sprites;
import me.jacob.macdougall.graphics.UI;
import me.jacob.macdougall.gui.Buttons;
import me.jacob.macdougall.gui.Scroll;
import me.jacob.macdougall.gui.Scroller;
import me.jacob.macdougall.launcher.gui.MenuHandler;
import graphic.engine.screen.Screen;
import graphic.engine.screen.SpriteHandler;
import graphic.engine.window.Resolution;

public class Launcher extends Canvas implements Runnable {

	private static final long serialVersionUID = 4727156896020447859L;

	private static Mod[] mods;
	
	private static Launcher launcher;
	private static JFrame frame;
	
	private static boolean running = false;
	
	private Thread thread;
	private Screen screen;
	
	private MenuHandler menus;
	private Destinyor game;
	private Mouse mouse;
	private InputHandler input;
	
	public Launcher(Destinyor game) {
		this.game = game;
		screen = new Screen(512, 384);
		
		Sprites.setSprite(Sprites.FONT, new SpriteHandler(Files.DestinyorFont, 8, 8, game));
		Sprites.setSprite(Sprites.BUTTON, new SpriteHandler(Files.Buttons, 120, 20, game));
		graphic.engine.screen.Font.setFont(Sprites.getSprites(Sprites.FONT));
		
		menus = new MenuHandler();
		setSize(800, 600);
		Resolution.setWidth(800);
		Resolution.setHeight(600);
	}
	
	public static void inializeWindow(Launcher launcher) {
		frame = new JFrame("Destinyor");
		frame.setResizable(false);
		frame.setSize(800, 600);
		frame.add(launcher);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.pack();
		frame.setLocationRelativeTo(null);
		frame.setAlwaysOnTop(true);
		frame.setVisible(true);
		frame.requestFocus();
		frame.requestFocusInWindow();
		launcher.mouse = new Mouse(launcher);
		launcher.input = new InputHandler(launcher);
	}
	
	private void start() {
		running = true;
		thread = new Thread(this, "LauncherThread");
		thread.setPriority(Thread.MAX_PRIORITY);
		thread.start();
	}

	// Stops Game
	public void stop() {
		running = false;
		frame.setVisible(false);
		try {
			thread.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void run() {
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
					render();
					fUnprocessed--;
				}

			} else {
				fps++;
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
						Thread.sleep(1000 / GameVariables.getFPSLimit()); // 1000m = 1s so (1000 / 60) = 1/10th of a second-ish
					else
						Thread.sleep(100);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	public void update() {
		input.tick();
		menus.update(game, mouse);
		Time.tick();
	}
	
	public void render() {
		BufferStrategy strategy = this.getBufferStrategy();
		if(strategy == null) {
			// Create Strategy
			createBufferStrategy(3);
			requestFocus();
			return;
		}
		
		UI.REFRESH(screen);
		menus.render(screen);
		Graphics g = strategy.getDrawGraphics();
		g.drawImage(screen.image, 0, 0, 800, 600, null);
		
		g.dispose();
		strategy.show();
	}
	
	public static void launch(Mod[] mods) {
		Launcher.mods = mods;
		launcher.stop();
		Time.resetCutsceneTimer();
		Time.resetKeyTimer();
		Time.resetObjectTimer();
		Time.entityTimer(0);
		Time.frameTimer(0);
		Time.getTime(0);
		Time.inventoryTimer(0);
		Time.mapTimer(0);
		Time.moveTimer(0);
		Time.playersTimer(0);
		Time.playerTimer(0);
	}
	
	public static void setLauncher(Launcher launcher) {
		if(Launcher.launcher == null) { // Only one instance
			inializeWindow(launcher);
			launcher.start();
			Launcher.launcher = launcher;
		}
	}
	
	public static boolean isRunning() {
		return running;
	}
	
	public static void clear() {
		frame.dispose();
		frame = null;
		launcher = null;
	}
	
	public static Mod[] getMods() {
		return Launcher.mods;
	}
	
}

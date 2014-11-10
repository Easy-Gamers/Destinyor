package me.jacob.macdougall.threads;

import graphic.engine.screen.Screen;
import me.jacob.macdougall.Destinyor;
import me.jacob.macdougall.battles.Battles;
import me.jacob.macdougall.cutscenes.*;
import me.jacob.macdougall.enemies.Enemy;
import me.jacob.macdougall.npcs.NPC;
import me.jacob.macdougall.player.Move;

// Main_Thread in Destinyor handles rendering and player updates
// Loading_Thread in Loading_Thread handles loading
// Npc_Thread in NPC_Thread handles npcs and ai

/**
 * Main Thread found int Destinyor handles the rendering and player updates.
 * Loading Thread handles all the loading, the Destinyor thread will render the loading screens.
 * NPC thread handles the enemy's and npcs because it requires quite a bit of processing it was moved to it's own seperate thread
 * 
 * @author Jacob
 * 
 */
public abstract class Thread_Controller {
	public static Destinyor game;
	public static Cutscene cutscene;
	public static NPCs cNpc;
	public static NPC npc;
	protected static Enemy[] enemies;
	protected static Move move;
	protected static Battles battle;
	protected static Screen screen;
	protected static NPC_Thread nThread;
	protected static Loading_Thread lThread;
	protected static Cutscene_Thread cThread;
	protected static Audio_Thread aThread;
	protected Thread NPCThread;
	protected Thread LoadingThread;
	protected Thread CutsceneThread;
	protected Thread AudioThread;

	protected boolean NPCs = false;
	protected boolean Loading = false;
	protected boolean cutscenes = false;
	protected boolean audio = false;

	public static boolean doneLoading = false;

	protected int Updates = 0;
	protected int Frames = 0;

	public static void init(Screen screen, Battles battles) {
		Thread_Controller.screen = screen;
		nThread = new NPC_Thread();
		battle = battles;
	}

	public static void setNpc(Move move) {
		Thread_Controller.move = move;
	}

	public static Enemy[] getEnemies() {
		return enemies;
	}

	public static void startNPC() {
		nThread.start();
	}

	public static void startCutscene() {
		cThread = new Cutscene_Thread();
		cThread.start();
	}

	public static void startLoading() {
		lThread = new Loading_Thread();
		lThread.start();
	}

	public static void pauseLoading() {
		lThread.pause();
	}

	public static void stopLoading() {
		lThread.stop();
	}

	public static void pauseCutscene() {
		cThread.pause();
	}

	public static void resumeCutscene() {
		cThread.resume();
	}

	public static void renderCutscene() {
		cThread.render();
	}

	public static void startAudio() {
		aThread = new Audio_Thread();
		aThread.start();
	}

	public abstract void start();

	public abstract void stop();

	public abstract void pause();

	public abstract void resume();

	protected abstract void update();

	protected abstract void render();

}

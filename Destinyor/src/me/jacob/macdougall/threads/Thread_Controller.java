package me.jacob.macdougall.threads;

import graphic.engine.screen.Screen;
import me.jacob.macdougall.battles.Battles;
import me.jacob.macdougall.cutscenes.*;
import me.jacob.macdougall.enemies.Enemy;
import me.jacob.macdougall.npcs.NPC;
import me.jacob.macdougall.player.Move;

// Main_Thread in Destinyor handles rendering and player updates
// Loading_Thread in Loading_Thread handles loading
// Npc_Thread in NPC_Thread handles npcs and ai

/**
 * Main Thread found in Destinyor handles the rendering and player updates.
 * Loading Thread handles all the loading, the Destinyor thread will render the loading screens.
 * NPC thread handles the enemy's and npcs because it requires quite a bit of processing it was moved to it's own seperate thread
 * Cutscene thread handles the updating of cutscenes, however the rendering is still done in the main thread
 * @author Jacob
 * 
 */
public class Thread_Controller {
	public static Cutscene cutscene;
	public static NPCs cNpc;
	public static NPC npc;
	protected static Enemy[] enemies;
	protected static Move move;
	protected static Battles battle;
	protected static Screen screen;
	protected static NPC_Thread nThread;
	protected static Loading_Thread lThread;
	protected static Audio_Thread aThread;
	

	protected Thread CutsceneThread;

	public static boolean doneLoading = false;

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

	public static void startAudio() {
		aThread = new Audio_Thread();
		aThread.start();
	}
	
	public static void stopAudio() {
		aThread.stop();
	}

}

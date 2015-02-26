package me.jacob.macdougall.threads;

import me.jacob.macdougall.battles.AIBattle;
import me.jacob.macdougall.battles.Battles;
import me.jacob.macdougall.cutscenes.Cutscene;
import me.jacob.macdougall.cutscenes.NPCs;
import me.jacob.macdougall.graphics.UI;
import me.jacob.macdougall.npcs.NPC;

public class NPC_Thread implements Runnable {

	protected Thread NPCThread;
	
	private boolean running = false;
	
	
	public void start() {
		running = true;
		NPCThread = new Thread(this, "NpcHandler");
		NPCThread.setPriority(Thread.NORM_PRIORITY);
		NPCThread.start();
	}


	public void resume() {
		running = true;
	}


	public void pause() {
		running = false;
	}


	public void stop() {
		running = false;
		try {
			NPCThread.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}


	protected void update() {
		if(UI.menu == 0 || UI.menu == 2) {

			if(!Cutscene.playing) {
				for(NPC n : NPC.npcs) {
					n.tick();
					if(n.inRange()) {
						n.Speaking();
						if(n.isSpeaking()) {
							Thread_Controller.npc = n;
						}
					}
				}
			} else {
				if(Thread_Controller.cutscene != Cutscene.cutscenes.get(Cutscene.getName))
					Thread_Controller.cutscene = Cutscene.cutscenes.get(Cutscene.getName);
				Thread_Controller.cutscene.update();
				for(NPCs n : Thread_Controller.cutscene.npc.values()) {
					n.tick();
					if(n.isSpeaking()) {
						Thread_Controller.cNpc = n;
					}
				}
				if(Thread_Controller.cutscene.finished) {
					Thread_Controller.cutscene.stopCutscene();
				}
			}

		}

		if(UI.menu == UI.Fight) {
			if(!Battles.enemiesCreated) {

				AIBattle.enemies = Battles.SetEnemies();
				Battles.enemiesCreated = true;
			}
			AIBattle.update();
			for(int i = 0; i < AIBattle.enemies.length; i++) {
				AIBattle.enemies[i].tick();
			}
		}
	}

	@Override
	public synchronized void run() {
		long fps_Timer = System.currentTimeMillis();
		double unsPerUpdate = 1000000000 / 60;
		// Last update in nanoseconds
		double uthen = System.nanoTime();
		double unprocessed = 0;
		while (running) {
			double unow = System.nanoTime();
			unprocessed += (unow - uthen) / unsPerUpdate;
			uthen = unow;
			// Update queue
			while (unprocessed >= 1) {
				update();
				unprocessed--;
			}

			// FPS Timer
			if(System.currentTimeMillis() - fps_Timer > 1000) {
				fps_Timer += 1000;

				try {
					Thread.sleep(5);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
	}

}

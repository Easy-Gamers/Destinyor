package me.jacob.macdougall.threads;

import java.io.IOException;

import javax.sound.sampled.LineUnavailableException;

import me.jacob.macdougall.audio.Playlist;
//import me.jacob.macdougall.audio.Sound;
import me.jacob.macdougall.world.LevelMap;

public class Audio_Thread implements Runnable {

	protected Thread AudioThread;
	private boolean running = false;
	
	@Override
	public synchronized void run() {
		//int fps = 0, update = 0;
        long fps_Timer = System.currentTimeMillis();
        double unsPerUpdate = 1000000000 / 60;
        double uthen = System.nanoTime();
        double unprocessed = 0;
        while(running) {
        	double unow = System.nanoTime();
        	unprocessed += (unow - uthen) / unsPerUpdate;
        	uthen = unow;
        	
        	// Update queue
        	while(unprocessed >= 1){
                update();
                unprocessed--;
        	}
        	
        	
        	
        	// FPS Timer
        	if(System.currentTimeMillis() - fps_Timer > 1000){
                fps_Timer += 1000;
                
                try {
    				Thread.sleep(5);
    			} catch (InterruptedException e) {
    				e.printStackTrace();
    			}
        	}
        }
	}

	public void start() {
		running = true;
		AudioThread = new Thread(this, "AudioHandler");
        AudioThread.setPriority(Thread.MIN_PRIORITY);
		AudioThread.start();
	}

	public void stop() {
		running = false;
		try {
			AudioThread.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	protected void update() {
		
		try {
			if(!Playlist.checkSongs()) {
				for(Playlist playlist : Playlist.playlists)
				if(playlist.levelID == LevelMap.level) {
					playlist.Play();
					playlist.isPlaying = true;
				}
			}
		} catch (LineUnavailableException | IOException e) {
			e.printStackTrace();
		}
	}


}

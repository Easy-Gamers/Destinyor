package me.jacob.macdougall.threads;

import java.io.IOException;

import javax.sound.sampled.LineUnavailableException;

import me.jacob.macdougall.audio.Playlist;
//import me.jacob.macdougall.audio.Sound;
import me.jacob.macdougall.world.LevelMap;

public class Audio_Thread extends Thread_Controller implements Runnable {

	@Override
	public synchronized void run() {
		//int fps = 0, update = 0;
        long fps_Timer = System.currentTimeMillis();
        double unsPerUpdate = 1000000000 / 60;
        double uthen = System.nanoTime();
        double unprocessed = 0;
        while(audio) {
        	double unow = System.nanoTime();
        	unprocessed += (unow - uthen) / unsPerUpdate;
        	uthen = unow;
        	
        	// Update queue
        	while(unprocessed >= 1){
        		// Update
                //update++;
                update();
                unprocessed--;
        	}
        	
        	
        	
        	// FPS Timer
        	if(System.currentTimeMillis() - fps_Timer > 1000){
        		//System.out.printf("\n Audio_Thread: %d fps, %d updates", fps, update);
                //fps = 0;
                //update = 0;
                fps_Timer += 1000;
                
                try {
    				Thread.sleep(5);
    			} catch (InterruptedException e) {
    				e.printStackTrace();
    			}
        	}
        }
	}

	@Override
	public void start() {
		audio = true;
		this.AudioThread = new Thread(this, "AudioHandler");
        this.AudioThread.setPriority(Thread.MIN_PRIORITY);
		this.AudioThread.start();
	}

	@Override
	public void stop() {
		
	}

	@Override
	public void pause() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void resume() {
		// TODO Auto-generated method stub
		
	}

	@Override
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

	@Override
	protected void render() {
		
	}

}

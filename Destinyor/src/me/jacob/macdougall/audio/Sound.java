package me.jacob.macdougall.audio;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;
import javax.sound.sampled.LineEvent;
import javax.sound.sampled.LineListener;
import javax.sound.sampled.Line;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

import me.jacob.macdougall.DebugWriter;
import me.jacob.macdougall.threads.Thread_Controller;

public class Sound implements LineListener {

	public static Map<String, Sound> sounds = new HashMap<>();
	public static Map<Integer, String> names = new HashMap<>();
	public static Random rand = new Random();
	public static int nextSong;

	public boolean isPlaying = false;
	//public AudioInputStream ais; // Permanet AudioInputStream object
	public AudioInputStream audioInputStream;
	public Clip clip;
	public Line line;
	public File soundFile;
	public FloatControl volume;

	public String name;

	public boolean loopable = false;
	public float audioLevel = -80f; // -80 == mute, 0 == normal, 6 == max
	//public float audioLevel = -20;

	public void playSound() {
		DebugWriter.println("Playing: " + soundFile.getName());
		clip.start();
		isPlaying = true;
	}

	public void open() throws LineUnavailableException, IOException {
		try {
			audioInputStream = AudioSystem.getAudioInputStream(soundFile);
		} catch (UnsupportedAudioFileException e) {
			e.printStackTrace();
		}

		if(clip != null && !clip.isOpen()) {
			clip.open(audioInputStream);
			try {
				volume = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
				volume.setValue(audioLevel);
			} catch(IllegalArgumentException e) { // If on linux use something else instead
				DebugWriter.println("Sound: IllegalArgumentException has been thrown");
				Thread_Controller.stopAudio();
			}
        }
	}

	public void stopSound() {
		if(clip != null) {
			// System.out.println("Stopping: " + name);
			clip.stop();
			close();
			this.isPlaying = false;
		}
	}

	public void close() {
		if(clip != null && clip.isOpen()) {
			// System.out.println("Closing: " + name);
			clip.close();
			try {
				audioInputStream.reset();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	public void loop() throws LineUnavailableException, IOException {
		if(this.loopable && this.clip.isOpen()) {
			if(!this.clip.isActive()) {
				clip.loop(1);
			}
		}
	}

	@Override
	public void update(LineEvent event) {
		LineEvent.Type type = event.getType();
		if(type == LineEvent.Type.OPEN) {
			try {
				open();
			} catch (LineUnavailableException | IOException e) {
				e.printStackTrace();
			}
		}
		if(type == LineEvent.Type.CLOSE) {
			close();
		}
		if(type == LineEvent.Type.START) {
			playSound();
		}
		if(type == LineEvent.Type.STOP) {
			stopSound();
		}
	}

	public Sound(String location, boolean loopable) {
		this.loopable = loopable;
		this.name = location;
		names.put(names.size(), name);
		try {
			soundFile = new File(location);

			DebugWriter.println("Menu: Adding: " + soundFile.getName());

			Line.Info linfo = new Line.Info(Clip.class);
			line = AudioSystem.getLine(linfo);
			clip = (Clip) line;
			clip.addLineListener(this);
			audioInputStream = AudioSystem.getAudioInputStream(soundFile);
		} catch (Exception ex) {
			DebugWriter.println("Error with playing sound: " + name);
			ex.printStackTrace();
		}
		sounds.put(location, this);
	}

	public static boolean checkSongs() {
		for(Sound sound : sounds.values()) {
			if(sound.isPlaying && sound.loopable) { // Checks to make sure the
													// song is a song and not a
													// sound effect
				return true;
			}
		}
		return false;
	}

	public boolean playing() {
		if(clip != null && clip.isOpen()) {
			if(clip.getMicrosecondPosition() >= clip.getMicrosecondPosition()) {
				this.isPlaying = false;
				return true;
			}
		}
		return false;
	}

	public String getName() {
		return soundFile.getName();
	}

}

package me.jacob.macdougall.audio;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.sound.sampled.LineUnavailableException;

import me.jacob.macdougall.Destinyor;
import me.jacob.macdougall.world.LevelMap;

public class Playlist {

	public static List<Playlist> playlists = new ArrayList<>();
	
	public List<Sound> songs = new ArrayList<>();
	
	public int levelID = 0;
	

	public static Random rand = new Random();
	public static int nextSong;

	public static Sound song;

	public boolean isPlaying = false;

	public Playlist(int levelID, Sound... sound) {
		this.levelID = levelID;
		for(Sound song : sound) {
			songs.add(song);
		}
		playlists.add(this);
	}

	@SuppressWarnings("unused")
	public void Play() throws LineUnavailableException, IOException {
		if(levelID == LevelMap.level) {
			for(Sound song : songs) {
				if(checkSongs())
					return;
			}
			nextSong();
		}
	}

	public void nextSong() throws LineUnavailableException, IOException {
		int oldSong = nextSong;
		nextSong = rand.nextInt(this.songs.size());
		if(oldSong == nextSong && this.songs.size() >= 2) {
			while (oldSong == nextSong) {
				nextSong = rand.nextInt(this.songs.size());
			}
		}
		song = songs.get(nextSong);
		Destinyor.song = song.getName();
		song.open();
		song.playSound();
	}

	public static boolean checkSongs() {
		if(Sound.checkSongs()) {
			return true;
		}

		for(Playlist playlist : playlists) {
			for(Sound sounds : playlist.songs) {
				if(sounds.isPlaying) { // Checks to make sure the song is a song
										// and not a sound effect
					return true;
				}
			}
		}
		return false;
	}

}

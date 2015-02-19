package me.jacob.macdougall;

import graphic.engine.window.Resolution;

import java.awt.Graphics;

public class Clock {

	private int seconds = 0;
	private int minutes = 0;
	private int hours = 0;
	private int days = 0;
	private int weeks = 0;
	
	/**
	 * 
	 * @return a String containing time in week:day:hour:minute:second format
	 */
	public String getTime() {
		String time = weeks + " : " + days + " : " + hours + " : " + minutes + " : " + seconds;
		return time;
	}
	
	public void render(Graphics g) {
		g.drawString(getTime(), 1, Resolution.height() - 30);
	}
	
	// Tick tock goes the clock
	public void tick() {
		if(Time.getTime(60)) {
			seconds++;
		}
		if(seconds == 60) {
			seconds = 0;
			minutes++;
		}
		if(minutes == 60) {
			minutes = 0;
			hours++;
		}
		if(hours == 24) {
			hours = 0;
			days++;
		}
		if(days == 7) {
			days = 0;
			weeks++;
		}
	}
	
	/**
	 * For use when reading time from a save file.
	 * @param time
	 */
	public void setTime(String time) {
		String[] timeArray = time.split(":");
		int[] timeNumber = new int[timeArray.length];
		for(int i = 0; i < timeArray.length; i++) {
			timeNumber[i] = Integer.parseInt(timeArray[i]);
		}
		
		weeks = timeNumber[0];
		days = timeNumber[1];
		hours = timeNumber[2];
		minutes = timeNumber[3];
		seconds = timeNumber[4];
	}
        
}
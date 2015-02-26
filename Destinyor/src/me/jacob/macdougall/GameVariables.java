package me.jacob.macdougall;

public class GameVariables {

	private static final String title = "Destinyor";
	private static final int[] build = {
		0, 0, 3, 7
	};
	
	private static int FPSLimit = 60;

	private static int fps = 0;
	private static int ups = 0;

	private static Difficultly d;
	private static Render render;

	public static boolean isFpsLimit() {
		return (FPSLimit > 1);
	}

	public static int getFPSLimit() {
		return FPSLimit;
	}

	public static void setFPSLimit(int fps) {
		FPSLimit = fps;
	}

	public static enum Difficultly {
		EASY("Easy"), NORMAL("Normal"), HARD("Hard");
		
		private String difficultly;
		
		Difficultly(String difficultly) {
			this.difficultly = difficultly;
		}
		
		public String toString() {
			return difficultly;
		}
	}

	public static enum Render {
		MENU, OPTIONS, PAUSE, LEVEL, MAP, INVENTORY, EQUIPMENT, SPELLBOOK, CHARACTERS
	}

	public static Difficultly getDifficultly() {
		return d;
	}

	public static void setDifficultly(Difficultly diff) {
		d = diff;
	}

	/**
	 * 
	 * @param diff
	 * 0 = easy, 1 = normal, 2 = hard
	 */
	public static void setDifficultly(int diff) {
		switch (diff) {
			case 0:
				d = Difficultly.EASY;
				break;
			case 1:
				d = Difficultly.NORMAL;
				break;
			case 2:
				d = Difficultly.HARD;
				break;
			default:
				d = Difficultly.NORMAL;
				break;
		}
	}

	public Render getRender() {
		return render;
	}

	public static void setRender(Render renderType) {
		render = renderType;
	}
	
	public static String getBuild() {
		String buildName = "";
		for(int i = 0; i < build.length; i++) {
			if(build[i] != 0) {
				switch(i) {
					case 0: buildName = "Release Version: " + getActualBuild(); break;
					case 1: buildName = "Beta Version: " + build[i] + "." + build[i+1] + "." + build[i+2]; break;
					case 2: buildName = "Alpha Version: " + build[i] + "." + build[i+1]; break;
					case 3: buildName = "PreAlpha Version: " + build[i]; break;
					default: buildName = "Unknown Version: " + build[i]; break;
				}
				break;
			}
		}
		return buildName;
	}
	
	public static String getTitle() {
		return (title + " " + getBuild());
	}
	
	public static String getActualTitle() {
		return title;
	}
	
	public static String getActualBuild() {
		String buildName = "";
		
		for(int i = 0; i < build.length; i++) {
			buildName += build[i] + ".";
		}
		
		return buildName;
	}
	
	public static void setFps(int fps) {
		GameVariables.fps = fps;
	}
	
	public static void setUps(int ups) {
		GameVariables.ups = ups;
	}
	
	public static void setFPSAndUPS(int fps, int ups) {
		setFps(fps);
		setUps(ups);
	}
	
	public static int getFps() {
		return GameVariables.fps;
	}
	
	public static int getUps() {
		return GameVariables.ups;
	}
}

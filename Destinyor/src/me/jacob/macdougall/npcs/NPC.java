package me.jacob.macdougall.npcs;

import java.awt.Graphics;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import graphic.engine.screen.Bitmap;
import graphic.engine.screen.Dialouge;
import graphic.engine.screen.Screen;
import me.jacob.macdougall.Destinyor;
import me.jacob.macdougall.Time;
import me.jacob.macdougall.files.Files;
import me.jacob.macdougall.files.Reader;
import me.jacob.macdougall.graphics.Sprites;
import me.jacob.macdougall.input.Keys;
import me.jacob.macdougall.player.Move;
import me.jacob.macdougall.player.Player;
import me.jacob.macdougall.world.LevelMap;
import me.jacob.macdougall.world.Tile;

public class NPC {

	public static List<NPC> npcs = new ArrayList<>();

	public static NPC getNpc(String name) {
		NPC npc = null;
		for(NPC n : npcs) {
			if(n.getName().equals(name) && n.getLevel() == Destinyor.getCurrentLevel()) {
				npc = n;
				break;
			}
		}
		return npc;
	}

	private static final Random random = new Random();

	private final int X, Y;

	private final String name;
	protected final String[] dialougePerm;

	protected String stringFrame;
	protected String[] dialouge;

	private final boolean moving; // If the npc is allowed to move, and is not a static npc, aka shopkeeper

	private long t = System.currentTimeMillis();
	private long t1 = System.currentTimeMillis();

	private int x, y;
	private int frame;
	private int ran = random.nextInt(300);
	private int time = 0;
	public int dL = -1;

	protected boolean canMove = true;
	private boolean speaking = false;

	private LevelMap map;
	private Bitmap[] frames;

	public boolean NPCCollision(int x, int y) {
		for(NPC n : npcs) {
			if(n.getX() == x && n.getY() == y) {
				return true;
			}
		}
		return false;
	}

	public NPC(String name, String frame, int x, int y, String dialouge, boolean moving) {
		this.name = name;
		this.x = x;
		this.y = y;
		X = x;
		Y = y;
		this.dialouge = dialouge.split("END");
		this.moving = moving;

		for(int i = 0; i < this.dialouge.length; i++) {
			this.dialouge[i] = name + ":" + this.dialouge[i];
		}

		this.dialougePerm = this.dialouge;
		stringFrame = frame;
		if(frame != null) {
			// Change all this code to add more frames (useful for later)
			// For every frame you add, add one example with code
			int[] framez = new int[2];
			// int[] framez = new int[3];

			framez[0] = Integer.parseInt(frame.split(",")[0].trim()); // X coordinate on spritesheet
			framez[1] = Integer.parseInt(frame.split(",")[1].trim()); // Y coordinate on spritesheet
			// Not Applicable for changing to add more frames

			this.frames = new Bitmap[2];
			this.frames[0] = Sprites.getSprite(Sprites.SPRITE, framez[0], framez[1]); // Get First Frame
			this.frames[1] = Sprites.getSprite(Sprites.SPRITE, framez[0] + 1, framez[1]); // Move over one to get next frame
			//this.frames[2] = Sprites.getSprite(Sprites.SPRITE, framez[0], framez[1] + 1);; // Move down one to get next frame
			
//			this.frames[0] = Art.getSpritesheet()[framez[0]][framez[1]]; // Get First Frame
//			this.frames[1] = Art.getSpritesheet()[framez[0] + 1][framez[1]]; // Move over one to get next frame
			// this.frames[2] = Art.spritesheet[framez[0]][framez[1] + 1]; // Move down one to get next frame
		}
	}

	public NPC(String name, String frame, int x, int y, String dialouge, boolean moving, LevelMap map) {
		this.name = name;
		this.x = x;
		this.y = y;
		X = x;
		Y = y;
		this.dialouge = dialouge.split("END");
		this.moving = moving;

		for(int i = 0; i < this.dialouge.length; i++) {
			this.dialouge[i] = name + ":" + this.dialouge[i];
		}

		this.dialougePerm = this.dialouge;
		stringFrame = frame;
		if(frame != null) {
			// Change all this code to add more frames (useful for later)
			// For every frame you add, add one example with code
			int[] framez = new int[2];
			// int[] framez = new int[3];

			framez[0] = Integer.parseInt(frame.split(",")[0].trim()); // X coordinate on spritesheet
			framez[1] = Integer.parseInt(frame.split(",")[1].trim()); // Y coordinate on spritesheet
			// Not Applicable for changing to add more frames

			this.frames = new Bitmap[2];
			
			this.frames[0] = Sprites.getSprite(Sprites.SPRITE, framez[0], framez[1]); // Get First Frame
			this.frames[1] = Sprites.getSprite(Sprites.SPRITE, framez[0] + 1, framez[1]); // Move over one to get next frame
			//this.frames[2] = Sprites.getSprite(Sprites.SPRITE, framez[0], framez[1] + 1);; // Move down one to get next frame
			
//			this.frames[0] = Art.getSpritesheet()[framez[0]][framez[1]]; // Get First Frame
//			this.frames[1] = Art.getSpritesheet()[framez[0] + 1][framez[1]]; // Move over one to get next frame
			// this.frames[2] = Art.spritesheet[framez[0]][framez[1] + 1]; // Move down one to get next frame
		}
		this.map = map;
	}
	
	public void addToMap() {
		map.npcs.add(this);
	}

	public void tick() {
		if(canMove()) {
			time++;
			if(time % (ran + 60) == 0) {
				ran = random.nextInt(300);
				int XorY = random.nextInt(2);

				if(x >= X + 2 && !collision(Player.X + 1, Player.Y)) {
					if(CanMove(x + 1, y))
						setXRelative(1);
					canMove = false;
				} else if(x <= X - 2 && !collision(Player.X - 1, Player.Y)) {
					if(CanMove(x - 1, y))
						setXRelative(-1);
					canMove = false;
				}

				if(y >= Y + 2 && !collision(Player.X, Player.Y + 1)) {
					if(CanMove(x, y + 1))
						setYRelative(1);
					canMove = false;
				} else if(y <= Y - 2 && !collision(Player.X, Player.Y - 1)) {
					if(CanMove(x, y - 1))
						setYRelative(-1);
					canMove = false;
				}

				int move = random.nextInt(3) - 1;

				switch (XorY) {
					case 0:
						if(x < X + 2 && x > X - 2 && canMove && !collision(Player.X - move, Player.Y)) {
							if(CanMove(x + move, y)) {
								setXRelative(move); //X += move;
							}
						}
						break;
					case 1:
						if(y < Y + 2 && y > Y - 2 && canMove && !collision(Player.X, Player.Y - move)) {
							move = random.nextInt(3) - 1;
							if(CanMove(x, y + move)) {
								setYRelative(move);
							}
						}
						break;
				}
			}
		}

		if(System.currentTimeMillis() - t > 400 && inRange()) {
			frame++;
			t = System.currentTimeMillis();
		}
		
		if(frame >= frames.length)
			frame = 0;
	}

	public boolean inRange() {
		return (Player.X >= X - 8 && Player.X <= X + 8 && Player.Y >= Y - 6 && Player.Y <= Y + 6);
	}

	public void Speaking() {
		if(System.currentTimeMillis() >= t1 + 100) {
			t1 = System.currentTimeMillis();
			if(Keys.Enter() && this.X == Player.X + Move.GetXDir() && this.Y == Player.Y + Move.GetYDir() && Time.getKeyTimer(10, false)) {
				//Destinyor.Refresh();
				this.speaking = true;
				this.dL++;
				this.canMove = false;
				if(dL == dialouge.length) {
					this.dL = 0;
					this.stopSpeaking();
					canMove = true;
				}
				dialouge[dL] = Keywords.setKeyWords(dialougePerm[dL]); // Keeps updating the speech incase the player changes characters.
				Time.resetKeyTimer();
			}
		}

		if(!collision(Player.X - 1, Player.Y)) {
			if(!collision(Player.X + 1, Player.Y)) {
				if(!collision(Player.X, Player.Y - 1)) {
					if(!collision(Player.X, Player.Y + 1)) {
						speaking = false;
						canMove = true;
					}
				}
			}
		}
	}

	public void stopSpeaking() {
		this.speaking = false;
		Dialouge.setText(null, 0);
	}

	public boolean CanMove(int x, int y) {
		if(!moving) {
			Tile t = map.getTileAt(x, y);
			if(t == null)
				return false;
			if(t.isSolid())
				return false;
			if(NPCCollision(x, y))
				return false;
			else
				return true;
		} else {
			return false;
		}
	}

	private Bitmap getFrames(int i) {
		return frames[i];
	}

	public void render(Screen screen) {
		screen.render(getFrames(frame), map.MapX_Pos + x * 32, map.MapY_Pos + y * 32);
	}

	public void renderMinimap(Graphics g, int scale) {
		g.drawRect(this.getX() * scale, this.getY() * scale, scale, scale);
		g.fillRect(this.getX() * scale, this.getY() * scale, scale, scale);
	}

	public boolean canMove() {
		return (canMove && moving);
	}

	public boolean collision(int x, int y) {
		return (this.x == x && this.y == y);
	}

	public void setX(int x) {
		this.x = x;
	}

	public void setY(int y) {
		this.y = y;
	}

	public void setXRelative(int i) {
		x += i;
	}

	public void setYRelative(int j) {
		y += j;
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	public String getName() {
		return name;
	}

	public boolean isSpeaking() {
		return speaking;
	}

	public void setSpeaking(boolean speak) {
		speaking = speak;
	}

	public LevelMap getLevel() {
		return map;
	}

	public void changeLevel(LevelMap level) {
		this.map = level;
	}

	public void add(NPC npc) {
		npcs.add(npc);
	}

	public String getStringFrame() {
		return stringFrame;
	}

	public void speak() {
		Dialouge.setText(dialouge, dL);
	}
	
	public static NPC readStats(String[] args) {
		int i = 0;
		String name = args[i++];
		String frames = args[i++];
		int x = Integer.parseInt(args[i++]);
		int y = Integer.parseInt(args[i++]);
		String dialouge = args[i++];
		int lvl = Integer.parseInt(args[i++]);
		
		if(dialouge.contains(".txt")) {
			String location = Files.DialougesFolder + Files.fileSplit + dialouge;
			dialouge = Reader.ReadDialouge(location);
		}
		
		return new NPC(name, frames, x, y, dialouge, false, LevelMap.maps.get(lvl));
	}

}

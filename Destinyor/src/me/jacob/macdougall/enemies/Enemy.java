package me.jacob.macdougall.enemies;

import graphic.engine.screen.Bitmap;
import graphic.engine.screen.Screen;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import me.jacob.macdougall.graphics.Sprites;
import me.jacob.macdougall.items.Items;
import me.jacob.macdougall.magic.Element;
import me.jacob.macdougall.magic.Spells;

public class Enemy extends Enemies {

	public Element Resistance;

	public static Map<String, Enemy> enemies = new HashMap<>();
	public static Map<Integer, String> names = new HashMap<>();

	public static boolean attackable = false;
	public static boolean battle = false;

	public int X = 0, Y = 0, X1 = 0, Y1 = 0;

	protected boolean removed = false;

	private Items[] item;

	public int damage = 0;

	public int enemy;

	/**
	 * 
	 * @param name
	 * @param frame
	 * @param lvl
	 * @param exp
	 * @param hp
	 * @param str
	 * @param skl
	 * @param spd
	 * @param luk
	 * @param def
	 * @param wis
	 * @param gold
	 * @param Resistance
	 * @param spells
	 * @param xPos
	 * @param xSpawn
	 * @param yPos
	 * @param ySpawn
	 */
	public Enemy(String name, String frame, int lvl, int exp, int hp, int str, int skl, int spd, int luk, int def, int wis, int gold, Element Resistance, int xPos, int xSpawn, int yPos, int ySpawn) {
		super(name, null, lvl, exp, hp, str, skl, spd, luk, def, wis, gold, 0, 0, 0, 0, 0, 0, null);
		names.put(names.size(), name);
		//this.spells.put(0, Spells.Attack);
//		if(spells != null)
//			for(int s = 0; s < spells.length; s++)
//				this.spells.put(s + 1, spells[s]);

		this.X = xPos;
		this.Y = yPos;
		this.X1 = xSpawn;
		this.Y1 = ySpawn;

		if(frame != null) {
			if(!frame.equals("")) {
				// Change all this code to add more frames (useful for later)
				// For every frame you add, add one example with code
				int[] framez = new int[2];
				//				int[] framez = new int[3];

				framez[0] = Integer.parseInt(frame.split(",")[0]); // X coordinate on spritesheet
				framez[1] = Integer.parseInt(frame.split(",")[1]); // Y coordinate on spritesheet
				// Not Applicable for changing to add more frames

				this.frames = new Bitmap[2];
				this.frames[0] = Sprites.getSprite(Sprites.SPRITE, framez[0], framez[1]); // Get First Frame
				this.frames[1] = Sprites.getSprite(Sprites.SPRITE, framez[0] + 1, framez[1]); // Move over one to get next frame
				// this.frames[2] = Sprites.getSprite(Sprites.SPRITE, framez[0], framez[1] + 1); // Move down one to get next frame
				
				//this.frames[0] = Art.getSpritesheet()[framez[0]][framez[1]]; // Get First Frame
				//this.frames[1] = Art.getSpritesheet()[framez[0] + 1][framez[1]]; // Move over one to get next frame
				//this.frames[2] = Art.spritesheet[framez[0]][framez[1] + 1]; // Move down one to get next frame
			}
		}
	}

	/**
	 * http://stackoverflow.com/questions/5802118/why-we-use-clone-method-in-java
	 * <br>
	 * Clone constructor
	 * @param enemy
	 */
	protected Enemy(Enemy enemy) {
		super(enemy);
		this.X = enemy.X;
		this.Y = enemy.Y;
		this.X1 = enemy.X1;
		this.Y1 = enemy.Y1;
		this.setFrames(frames);
	}

	public static Enemy[] getEntities() {
		Enemy[] entity = new Enemy[enemies.size()];
		for(Enemy e : enemies.values()) {
			for(int i = 0; i < enemies.size(); i++) {
				entity[i] = e;
			}
		}
		return entity;
	}

	public static Enemy newInstance(Enemy enemy) {
		Enemy e = new Enemy(enemy);

		return e;
	}

	public static Enemy newInstance(String name) {
		Enemy enemy = new Enemy(enemies.get(name));

		return enemy;
	}

	//	public static Enemy newInstance(String key){
	//		try {
	//			return (Enemy) enemies.get(key).clone();
	//		} catch (CloneNotSupportedException e) {
	//			System.err.println("Unable to clone " + key);
	//			return null;
	//		}
	//	}
	
	@Override
	public void render(Screen screen) {
		screen.render(frames[fr], x, y);
	}

	public Items death() {
		Items items = null;
		if(item != null) {
			Random random = new Random();
			int rand = random.nextInt(item.length);
			items = item[rand];
		}
		return items;
	}

	public static Enemy getRandomEntity() {

		int rand = random.nextInt(getEntities().length);
		return Enemy.newInstance(names.get(rand));
	}

	public int getEnemyNumber() {
		return enemy;
	}

	public void setEnemy(int enemyNumber) {
		enemy = enemyNumber;
	}
}

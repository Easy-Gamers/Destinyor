package me.jacob.macdougall.enemies;

import graphic.engine.screen.Bitmap;
import graphic.engine.screen.GameFont;
import graphic.engine.screen.Screen;
import me.jacob.macdougall.items.Equipment;
import me.jacob.macdougall.magic.Spells;
import me.jacob.macdougall.world.LevelMap;

public class Enemies extends Dummy {
	
	protected static int frameInterval = 250, fr = 0;
	
	protected long frameTimer = System.currentTimeMillis();
	
	protected Bitmap[] frames;
	
	public boolean attacking = false;
	
	public Enemies(String name, String gender, int lvl, int exp, int hp, int str, int skl, int spd, int luk, int def, int wis, int gold, int x, int y, int spriteXStart, int spriteYStart, int spriteXEnd, int spriteYEnd, LevelMap map) {
		super(name, gender, lvl, exp, hp, str, skl, spd, luk, def, wis, gold, x, y, spriteXStart, spriteYStart, spriteXEnd, spriteYEnd, map);
		// TODO Auto-generated constructor stub
	}
	
	public Enemies(String name, String gender, int lvl, int exp, int hp, int str, int skl, int spd, int luk, int def, int wis, int gold, int x, int y, LevelMap map) {
		super(name, gender, lvl, exp, hp, str, skl, spd, luk, def, wis, gold, x, y, 0, 0, 0, 0, map);
	}
	
	protected Enemies(Enemies enemy) {
		super(enemy);
		this.frames = enemy.frames;
		this.frameTimer = enemy.frameTimer;
		this.attacking = enemy.attacking;
	}
	
	protected static Enemies newInstance(Enemies enemy) {
		return new Enemies(enemy);
	}
	
	protected Enemies newInstance() {
		return new Enemies(this);
	}
	
	public void tick() {
		if(System.currentTimeMillis() - frameTimer > 2 * frameInterval) {
			frameTimer = System.currentTimeMillis();
		}
		if(System.currentTimeMillis() - frameTimer > frameInterval && frames != null) {
			if(fr >= frames.length - 1) {
				fr = 0;
			} else {
				fr++;
			}

			frameTimer += frameInterval;
		}
	}
	
	public void setFrames(Bitmap[] frames) {
		this.frames = frames;
	}
	
	public void renderHP(Screen screen) {
			GameFont.render(String.valueOf(this.getStat(Enemies.HEALTH_POINTS)), screen, 8, y + 15);
	}

}

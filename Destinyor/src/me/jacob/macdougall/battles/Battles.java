package me.jacob.macdougall.battles;

import input.engine.mouse.Mouse;

import java.awt.Graphics;
import java.util.Random;

import me.jacob.macdougall.Destinyor;
import me.jacob.macdougall.enemies.Enemies;
import me.jacob.macdougall.enemies.Enemy;
import me.jacob.macdougall.graphics.UI;
import me.jacob.macdougall.magic.Spells;
import me.jacob.macdougall.player.Move;
import me.jacob.macdougall.player.Player;
import graphic.engine.screen.Screen;

public class Battles {

	public static boolean endBattle = false;

	public BattleInput bInput;
	private final BattleRender bRender;
	
	public static boolean enemiesCreated = false;

	private final static Random randomGen = new Random();
	public static int randomBattles = randomGen.nextInt(90) + 10;
	private static int creatures = 0;
	
	public static int move = 10;
	
	protected static Spells spell = null;

	public Battles() {
		bInput = new BattleInput(PlayerBattle.point);
		bRender = new BattleRender(PlayerBattle.point);
	}

	public void renderTime(Player[] players, Graphics g) {
		bRender.renderTime(g, players);
	}

	public void render(Player[] players, Screen screen, Enemies[] entities) {
		bRender.render(players, screen, entities, bInput);
	}

	public void assignTarget(Mouse mouse, Enemies[] enemies) {
		bInput.assingTarget(mouse, enemies);
	}

	public static Enemy[] SetEnemies() {
		creatures = randomGen.nextInt(5);
		Enemy[] e = new Enemy[creatures + 1];

		int i = 0;
		int x = 32;
		int y = 16;
		int j;

		Enemy enemy;
		boolean X;
		boolean X1;
		boolean Y;
		boolean Y1;
		while (e[creatures] == null) {
			enemy = Enemy.getRandomEntity();
			X = enemy.X <= Player.X;
			X1 = enemy.X1 >= Player.X;
			Y = enemy.Y <= Player.Y;
			Y1 = enemy.Y1 >= Player.Y;
			if(X && X1 && Y && Y1) {

				e[i] = enemy;
				e[i].setXandY(x, y);
				e[i].setEnemy(i);
				i++;

				if(y == 16 + (16 * 4)) {
					j = 16;
				} else {
					j = 32;
				}

				if(y == 16 + (16 * 3) || y == 16 + (16 * 2)) {
					x += 32;
					y = j;
				} else {
					y += 52;
				}
			}
		}
		return e;
	}

	public static void enterCombat() {

		//int random1 = randomGen.nextInt(100);

		if(Destinyor.Override)
			Player.Attackable = false;

		if(Player.Attackable)
			attack();
		
		if(Move.steps > 100)
			Move.steps = 0;

	}
	
	private static void attack() {
		if(move >= randomBattles && Move.steps >= 10) {
			randomBattles = randomGen.nextInt(90) + 10;
			UI.menu = UI.Fight;
			Move.steps = 0;
			Battles.enemiesCreated = false;
			move = 10; // Player must move 10 paces before a random battle will engage
		}
		move++;
	}

	

//	public void calculateDamage(Enemies[] enemies, Move move) {
//		if(spell != null && spell.inUse) {
//			spellHandler(spell.player, enemies);
//		}
//	}
//
//	public static void spellHandler(Player player, Enemies[] enemies) {
//
//		Spell: {
//			if(player.getSpells(Pointer.spell) != null) {
//				for(Enemies enemy : enemies) {
//					if(player.getTarget() != null && player.getTarget().alive()) {
//						if(player.getSpells(Pointer.spell).effect == Spells.Requires_Combo)
//							spell = player.getSpells(Pointer.spell);
//						player.getSpells(Pointer.spell).attack(player, enemy);
//						player.resetTurnTimer();
//						Pointer.spell = 1;
//						BattleRender.DrawSpells = false;
//						break Spell;
//					}
//				}
//			}
//		}
//	}
}

package me.jacob.macdougall.battles;

import java.awt.Color;
import java.awt.Graphics;

import me.jacob.macdougall.Destinyor;
import me.jacob.macdougall.enemies.Enemies;
import me.jacob.macdougall.magic.Spells;
import me.jacob.macdougall.player.Player;
import graphic.engine.screen.GameFont;
import graphic.engine.screen.Screen;

public class BattleRender {

	public static boolean DrawSpells = false;
	
	private final Pointer point;

	public BattleRender(Pointer point) {
		this.point = point;
	}

	

	public void render(Player[] players, Screen screen, Enemies[] entities, BattleInput battles) {
		int count = 0;
		int X = 10;
		int Y = 256;

		point.render(screen);
		// Player
		for(int p = 0; p < players.length; p++) {
			players[p].renderUI(screen, p * 56, p * 12);

			if(players[p].canAttack()) {
				if(!Pointer.ep)
					Pointer.point = true;
				if(!DrawSpells) {
					battles.commands.get(0).render(screen);
					battles.commands.get(1).render(screen);
					battles.commands.get(2).render(screen);
					battles.commands.get(3).render(screen);
					if(Destinyor.Debug)
					for(int i = 0; i < battles.commands.size(); i++)
						battles.commands.get(i).render(screen);
				}

				

				if(DrawSpells && players[p].spells != null) {
					int s = 0;
					for(Spells spell : players[p].spells.values()) {
						GameFont.render(spell.getName(), screen, 155, 275 + (20 * s));
						s++;
						if(s > 5)
							s = 0; // If there are more spells than 5 only display 5 and scroll for the rest
					}
				}
			}

			if(Destinyor.Debug)
				GameFont.render(Integer.toString(players[p].getStat(Player.HEALTH_POINTS)), screen, 32 * 15, (32) + (p * 56));
		}

		if(PlayerBattle.getAttackingPlayer() != null)
			PlayerBattle.getAttackingText(screen);

//		if(Battles.spell != null && Battles.spell.inUse && Battles.spell.effect == Spells.Requires_Combo) {
//			switch (Battles.spell.getCombo()) {
//				case Combo_Spell.LEFT:
//					GameFont.render("Left", screen, 256, 768 / 2);
//					break;
//				case Combo_Spell.RIGHT:
//					GameFont.render("Right", screen, 256, 768 / 2);
//					break;
//				case Combo_Spell.UP:
//					GameFont.render("Up", screen, 256, 768 / 2);
//					break;
//				case Combo_Spell.DOWN:
//					GameFont.render("Down", screen, 256, 768 / 2);
//					break;
//			}
//		}
		
		AIBattle.renderDamage(screen);
		
		// Render Enemies
		if(entities != null)
			for(Enemies enemy : entities)
				if(enemy != null)
					if(enemy.alive()) {
						count++;
						enemy.render(screen);
						
						if(Destinyor.Debug)
							enemy.renderHP(screen);
						
						if(count >= 6) {
							Y = 256 + 52 - 10;
							X += 64 + 32;
							count = 0;
						}
						
						GameFont.render(enemy.getName(), screen, X, (count * 16) + Y);
						
						if(PlayerBattle.getDamage() > 0) {
							
							if(enemy == PlayerBattle.getRecentlyAttackedEnemy())
								GameFont.render(PlayerBattle.getDamage() + "", screen, enemy.getX(), enemy.getY() - 8);
						}
					}

	}

	public void renderTime(Graphics g, Player[] players) {
		g.setColor(Color.BLACK);
		for(int p = 0; p < players.length; p++) {
			players[p].renderTime(g, p);
		}
	}

}

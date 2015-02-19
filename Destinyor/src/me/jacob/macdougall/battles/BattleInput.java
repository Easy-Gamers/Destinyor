package me.jacob.macdougall.battles;

import java.util.HashMap;
import java.util.Map;

import input.engine.mouse.Mouse;
import me.jacob.macdougall.Time;
import me.jacob.macdougall.enemies.Enemies;
import me.jacob.macdougall.gui.Commands;
import me.jacob.macdougall.input.Keys;
import me.jacob.macdougall.player.Player;

public class BattleInput {

	public Pointer point;

	public static boolean targetSelected = false;

	public Map<Integer, Commands> commands = new HashMap<>();

	public BattleInput(Pointer point) {
		this.point = point;

		commands.put(0, new Commands("Attack", 155, 275, (8 * "Attack".length()), 8));
		commands.put(1, new Commands("Spells", 155, 295, (8 * "Spells".length()), 8));
		commands.put(2, new Commands("Items", 155, 315, (8 * "Items".length()), 8));
		commands.put(3, new Commands("Flee", 155, 335, (8 * "Flee".length()), 8));
		
		commands.put(4, new Commands("Enemy1", 32, 16, 32, 32));
		commands.put(5, new Commands("Enemy2", 32, 68, 32, 32));
		commands.put(6, new Commands("Enemy3", 32, 120, 32, 32));
		commands.put(7, new Commands("Enemy4", 32, 172, 32, 32));
		commands.put(8, new Commands("Enemy5", 32, 224, 32, 32));
	}

	public void clicker(Mouse mouse, Enemies[] enemies) {
		for(int i = 0; i < commands.size(); i++) {
			if(commands.get(i).inBox(mouse.getPressed(Mouse.X), mouse.getPressed(Mouse.Y))) {
				switch (i) {
					case 0:
						Player player = Player.getPlayerAttack();
						if(player != null && player.getTarget() != null && Time.getKeyTimer(10, false)) {
							int damage = player.attack(player.getTarget(), PlayerBattle.pAttack(player, player.getTarget()));
							PlayerBattle.setAttacking(player, player.getTarget(), damage);
							Time.resetKeyTimer();
						}
						break;
					case 1:
						break;
				}
			}
		}
	}

	public void assingTarget(Mouse mouse, Enemies[] enemies) {

		if(enemies != null) {
			Player player = Player.getNextAttackPlayer();

			if(player.getTarget() != null && !player.getTarget().alive()) {
				for(Enemies enemy : enemies) {
					if(player.getTarget() == null) {
						player.setTarget(enemy);
						break;
					} else {
						if(enemy.alive())
							player.setTarget(enemy);
					}
				}
			}

			if(enemies.length > 0) {
				if(enemies[0].alive()) {
					if(Keys.Enemy1() || commands.get(4).inBox(mouse.getPressed(Mouse.X), mouse.getPressed(Mouse.Y)) || point.getEnemy(enemies) == 0) {
						targetSelected = true;
						Player.getNextAttackPlayer().setTarget(enemies[0]);
						System.out.println("Targeting 1");
						Pointer.ep = false;
					}
				}
			}

			if(enemies.length > 1) {
				if(enemies[1].alive()) {
					if(Keys.Enemy2() || commands.get(5).inBox(mouse.getPressed(Mouse.X), mouse.getPressed(Mouse.Y)) || point.getEnemy(enemies) == 1) {
						targetSelected = true;
						Player.getNextAttackPlayer().setTarget(enemies[1]);
						System.out.println("Targeting 2");
						Pointer.ep = false;
					}
				}
			}

			if(enemies.length > 2) {
				if(enemies[2].alive()) {
					if(Keys.Enemy3() || commands.get(6).inBox(mouse.getPressed(Mouse.X), mouse.getPressed(Mouse.Y)) || point.getEnemy(enemies) == 2) {
						targetSelected = true;
						Player.getNextAttackPlayer().setTarget(enemies[2]);
						System.out.println("Targeting 3");
						Pointer.ep = false;
					}
				}
			}

			if(enemies.length > 3) {
				if(enemies[3].alive()) {
					if(Keys.Enemy4() || commands.get(7).inBox(mouse.getPressed(Mouse.X), mouse.getPressed(Mouse.Y)) || point.getEnemy(enemies) == 3) {
						targetSelected = true;
						Player.getNextAttackPlayer().setTarget(enemies[3]);
						System.out.println("Targeting 4");
						Pointer.ep = false;
					}
				}
			}

			if(enemies.length > 4) {
				if(enemies[4].alive()) {
					if(Keys.Enemy5() || commands.get(8).inBox(mouse.getPressed(Mouse.X), mouse.getPressed(Mouse.Y)) || point.getEnemy(enemies) == 4) {
						targetSelected = true;
						Player.getNextAttackPlayer().setTarget(enemies[4]);
						System.out.println("Targeting 5");
						Pointer.ep = false;
					}
				}
			}
		}
	}

}

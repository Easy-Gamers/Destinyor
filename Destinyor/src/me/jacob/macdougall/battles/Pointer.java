package me.jacob.macdougall.battles;

import graphic.engine.screen.Screen;
import me.jacob.macdougall.Time;
import me.jacob.macdougall.enemies.Dummy;
import me.jacob.macdougall.enemies.Enemies;
import me.jacob.macdougall.graphics.Sprites;
import me.jacob.macdougall.input.Keys;
import me.jacob.macdougall.player.Player;

public class Pointer {

	public static int spell = 0;
	
	public static enum Menu {
		MAIN, SPELLS, ITEMS, ENEMIES
	}
	
	public static enum Command {
		ATTACK(0), SPELLS(1) , ITEMS(2), FLEE(3);
		
		private int id;
		
		private Command(int id) {
			this.id = id;
		}
		
		public int getID() {
			return id;
		}
		
		public int getX() {
			return 140;
		}
		
		public int getY() {
			return (id * 20) + 275;
		}
		
		public Command getNext() {
			switch(id) {
				case 0: return SPELLS;
				case 1: return ITEMS;
				case 2: return FLEE;
				default: return ATTACK;
			}
		}
		
		public Command getPrevious() {
			switch(id) {
				case 1: return ATTACK;
				case 2: return SPELLS;
				case 3: return ITEMS;
				default: return FLEE;
			}
		}
	}
	
	public Menu menu = Menu.MAIN;
	public Command command = Command.ATTACK;
	public int enemy = 0;
	
	public Dummy[] targets;

	public static int PointerX = 140;
	public static int PointerY = 275;

	public static int ePointerX = 20;
	public static int ePointerY = 38;

	public static boolean ep = false;
	public static boolean point = true;
	public int eT = 0; // Enemy Target
	public int pT = 0; // Player Target
	
	private boolean e = true, p = false;

	public Pointer() {
	}

	public static void reset() {
		PointerX = 140;
		PointerY = 275;
		ePointerX = 20;
		ePointerY = 38;
	}

	public void render(Screen screen) {
		if(ep)			
			screen.render(Sprites.getSprite(Sprites.FONT, 35, 1), ePointerX, ePointerY);
		if(point)
			screen.render(Sprites.getSprite(Sprites.FONT, 35, 1), PointerX, PointerY);
	}

	public void point(Player player, Enemies[] enemies) {
		if(Time.getKeyTimer(10, false))
		switch (menu) {
			case MAIN:
				main(player, enemies);
				break;

			case SPELLS:
				spells(player, enemies);
				break;
				
			case ITEMS: break;
			case ENEMIES: break;
		}
	}
	
	private void main(Player player, Enemies[] enemies) {
		if(!ep && point) {
			if(Keys.MoveUp()) {
				command = command.getPrevious();
				PointerX = command.getX();
				PointerY = command.getY();
				Time.resetKeyTimer();
			}
			if(Keys.MoveDown()) {
				command = command.getNext();
				PointerX = command.getX();
				PointerY = command.getY();
				Time.resetKeyTimer();
			}
			if(Keys.MoveRight()) {
				if(command == Command.SPELLS) {
					BattleRender.DrawSpells = true;
					menu = Menu.SPELLS;
					PointerY = 275;
					Time.resetKeyTimer();
				}
			}
			commands(player, enemies);
		}
	}
	
	private void spells(Player player, Enemies[] enemies) {
		if(Time.getKeyTimer(10, false)) {
			
			if(Keys.MoveUp()) {
				if(spell >= 1 && spell <= player.spells.size()) {
					spell--;
				} else {
					spell = 0;
				}
				Time.resetKeyTimer();
			}
			
			if(Keys.MoveDown()) {
				if(spell >= 1 && spell <= player.spells.size()) {
					spell++;
				} else {
					spell = 0;
				}
				Time.resetKeyTimer();
			}
			
			if(Keys.MoveLeft()) {
				BattleRender.DrawSpells = false;
				menu = Menu.MAIN;
				command = Command.SPELLS;
				PointerX = command.getX();
				PointerY = command.getY();
				Time.resetKeyTimer();
			}
			
			if(Keys.MoveRight()) {
				//Battles.spellHandler(player, enemies);
				if(player.getTarget() != null) {
					player.useSpell(spell, player.getTarget());
					menu = Menu.MAIN;
					spell = 0;
					command = Command.ATTACK;
					BattleRender.DrawSpells = false;
				}
			}
			
		}
	}
	
	private void items(Player player, Enemies[] enemies) {
		
	}
	
	private void enemies(Player player, Enemies[] enemies, int noOfTargets) {
		int amountOfTargets = Player.getActualPlayers().length + enemies.length;
		if(e) {
			if(Keys.MoveUp()) {
				if(eT > 0) {
					eT--;
					ePointerY -= 26;
				} else {
					eT = enemies.length - 1;
					ePointerY = 16 + (52 * eT);
				}
			}
			if(Keys.MoveDown()) {
				if(eT < enemies.length) {
					eT++;
					ePointerY += 26;
				} else {
					eT = 0;
					ePointerY = 16;
				}
			}
			if(Keys.MoveLeft() || Keys.MoveRight()) {
				p = true;
				e = false;
			}
			if(Keys.Enter()) {
				p = false;
				e = true;
				for(int i = 0; i < targets.length; i++) {
					if(targets[i] == null) {
						targets[i] = (Dummy) enemies[eT];
					}
				}
				
			}
		} else {
			if(Keys.MoveUp()) {
				if(pT > 0) {
					pT--;
					ePointerY -= 26;
				} else {
					pT = Player.getActualPlayers().length - 1;
					ePointerY = 16 + (52 * pT);
				}
			}
			if(Keys.MoveDown()) {
				if(pT < Player.getActualPlayers().length) {
					pT++;
					ePointerY += 26;
				} else {
					pT = 0;
					ePointerY = 16;
				}
			}
			if(Keys.MoveLeft() || Keys.MoveRight()) {
				p = false;
				e = true;
			}
			if(Keys.Enter()) {
				p = false;
				e = true;
				for(int i = 0; i < Player.getActualPlayers().length; i++) {
					if(targets[i] == null) {
						targets[i] = (Dummy) Player.getActualPlayer(pT);
					}
				}
				
			}
		}
	}
	

	private void commands(Player player, Enemies[] enemies) {
		switch (command) {
			case ATTACK:
				if((Keys.Enter() || Keys.MoveRight()) && Time.getKeyTimer(10, false)) {
					if(checkEnemy(enemies)) {
						int damage = player.attack(player.getTarget(), PlayerBattle.pAttack(player, player.getTarget()));
						PlayerBattle.setAttacking(player, player.getTarget(), damage);
						Time.resetKeyTimer();
					} else {
						pointEnemy();
					}
				}
				break;
			case SPELLS:
				if(Keys.Enter() && Time.getKeyTimer(10, false)) {
					menu = Menu.SPELLS;
					BattleRender.DrawSpells = true;
					Time.resetKeyTimer();
				}
				break;
			case ITEMS: break;
			case FLEE: break;
		}
	}

	public void pointEnemy() {
		ep = true;
		p = false;
	}

	public int getEnemy(Enemies[] enemies) {
		if(ep && !BattleInput.targetSelected) {
			if(Time.getKeyTimer(10, false)) {
				if(Keys.MoveDown()) {
					enemy--;
					Time.resetKeyTimer();
				}
				if(Keys.MoveUp())
					enemy++;
				Time.resetKeyTimer();
			}
			if(enemy > enemies.length)
				enemy = 0;
			if(enemy < 0)
				enemy = enemies.length;
			ePointerY = (52 - 8) * (enemy + 1);
			if(Keys.Enter() && Time.getKeyTimer(10, false)) {
				ep = false;
				reset();
				return enemy;

			} else {
				return -1;
			}
		} else {
			return -1;
		}
	}
	
	private void setTargets(Player player, Enemies[] enemies) {
		if(menu == Menu.MAIN && command == Command.ATTACK) {
			if(player.getTarget() == null) {
				menu = Menu.ENEMIES;
			}
			return;
		}
		if(menu == Menu.SPELLS) {
			if(targets == null) {
				targets = new Dummy[player.getSpells(spell).getTargets()];
			}
		}
		
		switch(menu) {
			case MAIN: break;
			case SPELLS: break;
			case ITEMS: break;
			case ENEMIES: break;
		}
	}

	public boolean checkEnemy(Enemies[] enemies) {
		return (Player.getPlayerAttack().getTarget() != null);
	}
}

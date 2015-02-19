package me.jacob.macdougall.player;

import me.jacob.macdougall.Time;
import me.jacob.macdougall.enemies.Dummy;
import me.jacob.macdougall.enemies.Enemies;
import me.jacob.macdougall.graphics.GraphicsGetter;
import me.jacob.macdougall.items.Equipment;
import me.jacob.macdougall.items.Items;
import me.jacob.macdougall.magic.Element;
import me.jacob.macdougall.magic.Spells;
import me.jacob.macdougall.quests.Quest;
import graphic.engine.screen.Bitmap;
import graphic.engine.screen.GameFont;
import graphic.engine.screen.Screen;

import java.awt.Graphics;
import java.io.BufferedWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Player extends Dummy {

	// Static means that all instances of the class will have the same value. Only values relating to movement should be static as only one character moves.
	public static List<Player> players = new ArrayList<>();
	
	// The inventory should also be static as all players have access to it, same with gold.
	public static Map<Integer, Items> inventory = new HashMap<>();
	public static List<Quest> questLog = new ArrayList<>();
	
	public Quest quest;

	private int threat = 100;
	
	public Element[] Resistance;

	public static boolean Attackable = false;

	// Map X and Y and Level
	public static int X = 6, Y = 7;
	public static int Level = 1;
	public int statPoints = 0;
	
	public String statSpread = "MANUAL";
	public boolean inParty = false;

	// Direction constants
	public static final int Direction_Up = 0, Direction_Down = 1,
			Direction_Left = 2, Direction_Right = 3;
	// Direction Controller
	public static int dir = Direction_Down;

	private Enemies target = null;

	public static final Bitmap[][] frames = SpriteChecker.frames;


	public static int AmountOFPlayers() {
		return players.size();
	}

	public Player(String name, String gender, int lvl, int exp, int hp, int str, int skl, int spd, int luk, int def, int wis, int gold, boolean inParty) {
		super(name, gender, lvl, exp, hp, str, skl, spd, luk, def, wis, gold, X, Y, 0, 0, 0, 0, null);

		this.inParty = inParty;
	}
	
	/**
	 * 
	 * @return an array containing up to 4 players who are in the party. Will return an array that is 4 big.<br>
	 * if there are not 4 players in the party, than it will return an array with null values.
	 */
	private static Player[] getPlayers() {
		int p = 0;
		for(Player player : Player.players) {
			if(player.inParty) {
				p++;
			}
			if(p > 4) {
				p = 4;
			}
		}
		Player[] players = new Player[4];
		int i = 0;
		for(Player player : Player.players) {
			if(player.inParty && i <= 4) {
				players[i] = player;
				i++;
			}
		}
		return players;
	}
	
	/**
	 * 
	 * @return all players that is in the party
	 */
	public static Player[] getActualPlayers() {
		Player[] player = Player.getPlayers();
		Player[] players;
		int size = 0;
		for(int i = 0; i < player.length; i++) {
			if(player[i] == null) {
				size++;
			}
		}

		players = new Player[player.length - size];
		for(int i = 0; i < players.length; i++) {
			players[i] = player[i];
		}
		return players;
	}
	
	public static Player getActualPlayer(int player) {
		return getActualPlayers()[player];
	}
	
	/**
	 * 
	 * @return the main character
	 */
	public static Player getMainCharacter() {
		return getActualPlayer(0);
	}

	public static void addPlayer(Player player) {
		players.add(player);
	}

	public Spells getSpells(int i) {
		return spells.get(i);
	}

	public void levelUp() {
		if(this.getStat(LEVEL) >= Math.pow(50, getStat(LEVEL))) {
			this.setStatRelative(LEVEL, 1);
			statPoints += 10;
		}
		//            if(Exp >= 50 * Lvl) {
		//                Lvl += 1;
		//                Str += 10;
		//                Def += 10;
		//                Hp = get("stat.hp") + 50;
		//                put("stat.hp", Hp);
		//                DebugWriter.println("Leveling Up: " + Name);
		//            }
	}
	
	@Override
	public void render(Screen screen) {
		int x = 0, y = 0;
		x = (int) (Screen.getWidth() / 2);
		y = (int) (Screen.getHeight() / 2);

		if(frames[Move.dir].length <= Move.frame) {
			Move.frame = 0;
		}

		screen.render(frames[Move.dir][Move.frame], x, y);
	}

	public void renderUI(Screen screen, int co, int no) {
		// co = "character offset", no = "name offset";
		if(Move.frame >= frames[Direction_Down].length) {
			Move.frame = 0;
		}
		screen.render(frames[Direction_Down][Move.frame], 448, 24 + co);
		if(Time.frameTimer(10)) {
			Move.frame++;
		}
		GameFont.render(getName(), screen, (420), ((32 * 9) - 18) + no);

	}

	public void renderTime(Graphics g, int no) {
		int x = GraphicsGetter.getAbsoluteX(472);
		int y = GraphicsGetter.getAbsoluteY((271) + (no * 12));
		int width = GraphicsGetter.getWidth(turnTimer / 20);
		int width2 = GraphicsGetter.getWidth(turnTimer / 20);
		int height = GraphicsGetter.getHeight(6);
		g.drawRect(x, y, width, height);
		g.fillRect(x, y, width2, height);
	}
	
	

	public void useSpell(int spell, Dummy... targets) {
		for(int i = 0; i < targets.length; i++) {
			if(!miss(targets[i])) {
				spells.get(spell).attack(this, target);
			}
		}
	}

	public Equipment getEquipment(int key) {
		if(this.equipped.get(key) == null) {
			return null;
		} else {
			return this.equipped.get(key);
		}
	}

	public void Equip(Equipment equip) {
		equipped.put(equipped.size(), equip);
		equip.equipped = true;
	}

	public static void putItInTheBag(Items item) {
		inventory.put(inventory.size(), item);
	}

	/**
	 * Returns a player with max TA
	 * @return
	 */
	public static Player getPlayerAttack() {
		Player[] players = Player.getActualPlayers();
		Player player;
		// Make sure it's in chronological order
		for(int i = 0; i < players.length; i++) {
			if(players[i].turnTimer >= players[i].limit) {
				player = players[i];
				return player;
			}
		}
		return null;
	}

	/**
	 * 
	 * @return the first player who can attack or who will be the first to attack
	 */
	public static Player getNextAttackPlayer() {
		if(getPlayerAttack() != null) {
			return getPlayerAttack();
		} else {
			Player player = null;

			if(getActualPlayers() != null) {
				player = getMainCharacter();
			}

			for(int i = 0; i < getActualPlayers().length; i++) {
				if(player.getTurnTimer() > getActualPlayers()[i].getTurnTimer()) {
					player = getActualPlayers()[i];
					i = 0;
				}
			}
			return player;
		}
	}

	public int getThreat() {
		return (int) (threat + getStat(Player.STRENGTH) + getStat(Player.SKILL) + getStat(Player.WISDOM) - (getStat(Player.HEALTH_POINTS) * 0.10f));
	}

	public void setThreat(int threat) {
		this.threat = threat;
	}

	public void setTarget(Enemies enemy) {
		target = enemy;
	}

	public Enemies getTarget() {
		return target;
	}
	
	public void putInParty(boolean inParty) {
		this.inParty = inParty;
	}
	
	public boolean isInParty() {
		return inParty;
	}
	
	public void writeStats(BufferedWriter bw) throws IOException {
		bw.write("Player Name = " + getName());
		bw.newLine();
		bw.write("Player Gender = " + getGender());
		bw.newLine();
		bw.write("Player Level = " + getStat(Player.LEVEL));
		bw.newLine();
		bw.write("Player Experience = " + getStat(Player.EXPERIENCE));
		bw.newLine();
		bw.write("Player Health = " + getMaxStat(Player.HEALTH_POINTS));
		bw.newLine();
		bw.write("Player Strength = " + getMaxStat(Player.STRENGTH));
		bw.newLine();
		bw.write("Player Skill = " + getMaxStat(Player.SKILL));
		bw.newLine();
		bw.write("Player Speed = " + getMaxStat(Player.SPEED));
		bw.newLine();
		bw.write("Player Luck = " + getMaxStat(Player.LUCK));
		bw.newLine();
		bw.write("Player Defence = " + getMaxStat(Player.DEFENCE));
		bw.newLine();
		bw.write("Player Wisdom = " + getMaxStat(Player.WISDOM));
		bw.newLine();
		bw.write("Player Gold = " + getStat(Player.GOLD));
		bw.newLine();
		bw.write("Player Resistances = ");
		bw.newLine();
		bw.write("inParty = " + isInParty());
	}

}

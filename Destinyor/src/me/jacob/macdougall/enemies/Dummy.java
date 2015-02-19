package me.jacob.macdougall.enemies;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import me.jacob.macdougall.GameVariables;
import me.jacob.macdougall.graphics.MassSprites;
import me.jacob.macdougall.items.Equipment;
import me.jacob.macdougall.magic.Spells;
import me.jacob.macdougall.npcs.body.Entities;
import me.jacob.macdougall.npcs.body.Limb;
import me.jacob.macdougall.world.LevelMap;

public class Dummy extends MassSprites {

	protected static final Random random = new Random();
	
	public static final int LEVEL = 0, EXPERIENCE = 1, HEALTH_POINTS = 2,
			STRENGTH = 3, SKILL = 4, SPEED = 5, LUCK = 6, DEFENCE = 7,
			WISDOM = 8, GOLD = -1, ACCURACY = 9;

	public Map<Integer, Equipment> equipped = new HashMap<>(); // Needs to be in exact order
	public Map<Integer, Spells> spells = new HashMap<>(); // Needs to be in exact order

	protected LevelMap level;
	
	protected int chanceToMiss = 0;
	
	protected String name;
	protected String gender;

	protected int lvl;
	protected int exp;
	protected int hp;
	protected int str;
	protected int skl;
	protected int spd;
	protected int luk;
	protected int def;
	protected int wis;
	protected int gold;
	protected int acc;

	protected int mapLvl;
	protected int maxExp;
	protected int maxHp;
	protected int maxStr;
	protected int maxSkl;
	protected int maxSpd;
	protected int maxLuk;
	protected int maxDef;
	protected int maxWis;

	protected int limit = 500;
	protected int turnTimer = 0;

	private boolean paused = true;

	private Entities entity;

	/**
	 * 
	 * @param name
	 * @param gender
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
	 * @param x
	 * @param y
	 * @param spells
	 * @param equipment
	 * @param spriteXStart
	 * @param spriteYStart
	 * @param spriteXEnd
	 * @param spriteYEnd
	 * @param map
	 */
	public Dummy(String name, String gender, int lvl, int exp, int hp, int str, int skl, int spd, int luk, int def, int wis, int gold, int x, int y, int spriteXStart, int spriteYStart, int spriteXEnd, int spriteYEnd, LevelMap map) {
		super(x, y, spriteXStart, spriteYStart, spriteXEnd, spriteYEnd, map);
		this.name = name;
		this.gender = gender;
		this.lvl = lvl;
		this.exp = exp;
		this.hp = hp;
		this.maxHp = hp;
		this.str = str;
		this.skl = skl;
		this.spd = spd;
		this.luk = luk;
		this.def = def;
		this.wis = wis;
		this.gold = gold;
		this.x = x;
		this.y = y;
//		if(spells != null && spells.length > 0) {
//			for(int i = 0; i < spells.length; i++) {
//				this.spells.put(i, spells[i]);
//			}
//		}
//		if(equipment != null && equipment.length > 0) {
//			for(int i = 0; i < equipment.length; i++) {
//				this.equipped.put(i, equipment[i]);
//			}
//		}
	}

	/**
	 * http://stackoverflow.com/questions/5802118/why-we-use-clone-method-in-java
	 * <br>
	 * Clone constructor
	 * @param dummy
	 */
	protected Dummy(Dummy dummy) {
		super(dummy.getX(), dummy.getY(), 0, 0, 0, 0, null);
		this.name = dummy.name;
		this.gender = dummy.gender;
		this.lvl = dummy.lvl;
		this.exp = dummy.exp;
		this.hp = dummy.hp;
		this.str = dummy.str;
		this.skl = dummy.skl;
		this.spd = dummy.spd;
		this.luk = dummy.luk;
		this.def = dummy.def;
		this.wis = dummy.wis;
		this.gold = dummy.gold;
		this.x = dummy.x;
		this.y = dummy.y;
		this.spells = dummy.spells;
		this.equipped = dummy.equipped;
		this.level = dummy.level;
		this.entity = dummy.entity;
		this.limit = dummy.limit;
	}

	/**
	 * Returns the specified stat
	 * @param stat stat to return
	 * @return
	 */
	public int getStat(int stat) {
		switch (stat) {
			case LEVEL:
				return lvl;
			case EXPERIENCE:
				return exp;
			case HEALTH_POINTS:
				return hp;
			case STRENGTH:
				return str;
			case SKILL:
				return skl;
			case SPEED:
				return spd;
			case LUCK:
				return luk;
			case DEFENCE:
				return def;
			case WISDOM:
				return wis;
			default:
				return gold;
		}
	}
	
	public int getMaxStat(int stat) {
		switch (stat) {
			case STRENGTH:
				return maxStr;
			case SKILL:
				return maxSkl;
			case SPEED:
				return maxSpd;
			case LUCK:
				return maxLuk;
			case DEFENCE:
				return maxDef;
			case WISDOM:
				return maxWis;
			default:
				return maxHp;
		}
	}

	/**
	 * Changes the value of the stat to the amount given
	 * @param stat stat to change
	 * @param amount amount to add or remove
	 */
	public void setStat(int stat, int amount) {
		switch (stat) {
			case LEVEL:
				lvl = amount;
				break;
			case EXPERIENCE:
				exp = amount;
				break;
			case HEALTH_POINTS:
				hp = amount;
				break;
			case STRENGTH:
				str = amount;
				break;
			case SKILL:
				skl = amount;
				break;
			case SPEED:
				spd = amount;
				break;
			case LUCK:
				luk = amount;
				break;
			case DEFENCE:
				def = amount;
				break;
			case WISDOM:
				wis = amount;
				break;
			default:
				gold = amount;
				break;
		}
	}

	public void cureAliments() {
		hp = maxHp;
		str = maxStr;
		skl = maxSkl;
		spd = maxSpd;
		luk = maxLuk;
		def = maxDef;
		wis = maxWis;
	}

	/**
	 * Applies the amount to the attribute, so at level 1
	 * setStatRelative(LEVEL, 1)
	 * sets lvl to 2;
	 * @param stat stat to change
	 * @param amount amount to add or remove
	 */
	public void setStatRelative(int stat, int amount) {
		switch (stat) {
			case LEVEL:
				lvl += amount;
				break;
			case EXPERIENCE:
				exp += amount;
				break;
			case HEALTH_POINTS:
				hp += amount;
				break;
			case STRENGTH:
				str += amount;
				break;
			case SKILL:
				skl += amount;
				break;
			case SPEED:
				spd += amount;
				break;
			case LUCK:
				luk += amount;
				break;
			case DEFENCE:
				def += amount;
				break;
			case WISDOM:
				wis += amount;
				break;
			default:
				gold += amount;
				break;
		}
	}

	/**
	 * Returns the hp of the dummy modified by the difficutly level
	 * @return
	 */
	public int getModifiedHp() {
		int HP = this.hp;
		if(GameVariables.getDifficultly() == GameVariables.Difficultly.EASY) {
			if(isNpc()) {
				HP = (int) (HP * 0.50f);
			} else {
				HP = HP * 2;
			}
		}
		return HP;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

	/**
	 * 
	 * @return the gender if it is a player, otherwise returns an empty string
	 */
	public String getGender() {
		return gender;
	}

	public void setLevel(LevelMap level) {
		this.level = level;
	}

	public void setLevel(int level) {
		this.level = LevelMap.maps.get(level);
	}

	public boolean alive() {
		return (getStat(HEALTH_POINTS) > 0);
	}

	public boolean hasEquipment() {
		return !this.equipped.isEmpty();
	}

	public Equipment[] armour() {
		Equipment[] e = new Equipment[equipped.size()];
		int i = 0;
		for(Equipment equip : equipped.values()) {
			if(equip.isArmour()) {
				e[i] = equip;
				i++;
			}
		}
		return e;
	}

	/**
	 * Returns an array containing all armours, no nulls or weapons.
	 * @return Returns any equipment that is equipped, that is a armour. (Defined by Equipment.isArmour())
	 */
	public Equipment[] returnArmour() {
		Equipment[] equip = this.armour();
		Equipment[] e = new Equipment[equip.length];
		int j = 0;
		if(equip != null && equip.length > 0 && equip[0] != null) {
			for(int i = 0; i < equip.length; i++) {
				if(equip[i] != null) {
					e[i] = equip[i];
					j++;
				}
			}
			equip = new Equipment[j];
			j = 0;
			for(Equipment equipment : e) {
				if(equipment != null) {
					equip[j] = equipment;
					j++;
				}
			}
		}
		return equip;
	}

	/**
	 * Gets all equipment, creates null spaces where there a weapon doesn't exist, however there still is equipment
	 * @return
	 */
	public Equipment[] weapon() {
		Equipment[] e = new Equipment[equipped.size()]; // Only two hands
		int i = 0;
		for(Equipment equip : equipped.values()) {
			if(equip.isWeapon()) {
				e[i] = equip;
				i++;
			}
		}
		return e;
	}

	/**
	 * Returns an array containing all weapons, no nulls or armours.
	 * @return Returns any equipment that is equipped, that is a weapon. (Defined by Equipment.isWeapon())
	 */
	public Equipment[] returnWeapons() {
		Equipment[] equip = this.weapon();
		Equipment[] e = new Equipment[equip.length];
		int j = 0;
		if(equip != null && equip[0] != null) {
			for(int i = 0; i < equip.length; i++) {
				if(equip[i] != null) {
					e[i] = equip[i];
					j++;
				}
			}
			equip = new Equipment[j];
			j = 0;
			for(Equipment equipment : e) {
				if(equipment != null) {
					equip[j] = equipment;
					j++;
				}
			}
		}
		return equip;
	}

	/**
	 * Uses advance return
	 * @return if(ta >= limit) return true, else return false
	 */
	public boolean canAttack() {
		return (turnTimer >= limit && hp > 0);
	}

	/**
	 * Continues the turn timer
	 */
	public void play() {
		if(!paused)
			turnTimer++;
		paused = false;
	}

	/**
	 * Pauses the turn timer
	 */
	public void pause() {
		paused = true;
	}

	public int getTurnTimer() {
		return turnTimer;
	}

	public int getLimit() {
		return limit;
	}

	public void resetTurnTimer() {
		turnTimer = 0;
	}

	public Entities getEntityType() {
		return entity;
	}

	public Limb[] getLimbs() {
		return (Limb[]) entity.limbs.toArray();
	}

	protected static Dummy newInstance(Dummy dummy) {
		Dummy d = new Dummy(dummy);
		return d;
	}

	protected Dummy newInstance() {
		Dummy d = new Dummy(this);
		return d;
	}

	public boolean isNpc() {
		return (gender == null || gender.equals(""));
	}
	
	public int attack(Dummy dummy, int maxDamage) {
		int damage = 0;
		if(!miss(dummy)) {
			damage = random.nextInt(maxDamage);
			if(damage < 1) {
				damage = 1;
			}
			dummy.setStatRelative(Dummy.HEALTH_POINTS, -damage);
		}
		resetTurnTimer();
		return damage;
	}
	
	public boolean miss(Dummy dummy) {
		int rand = 0;
		rand = (int) (getStat(Dummy.LUCK) + (getStat(Dummy.LUCK) * skillcheck(dummy)) * 1);
		// If you can't hit the target you get a 50/50 chance to hit
		if(rand <= 1) {
			rand = 2;
		}
		chanceToMiss = random.nextInt(rand);
		if(chanceToMiss <= 0) {
			return true;
		} else {
			return false;
		}
	}
	
	/**
	 * calculates the amount of bonus damage.
	 * It is based on the enemies skill level and the current attacking players skill level.
	 * The damage is multiplied by enemy.skl % player.skl
	 * @param enemies
	 * @return
	 */
	public float skillcheck(Dummy dummy) {
		int crit = 0;
		if(this.getStat(Dummy.SKILL) > dummy.getStat(Dummy.SKILL)) {
			crit = dummy.getStat(Dummy.SKILL) % this.getStat(Dummy.SKILL);
		}
		return crit;
	}
	
	public static int convertToAttribute(String attr) {
		if(attr.toLowerCase().contains("lvl") || attr.toLowerCase().contains("level")) {
			return LEVEL;
		}
		if(attr.toLowerCase().contains("exp")) {
			return EXPERIENCE;
		}
		if(attr.toLowerCase().contains("str")) {
			return STRENGTH;
		}
		if(attr.toLowerCase().contains("skl") || attr.toLowerCase().contains("skill")) {
			return SKILL;
		}
		if(attr.toLowerCase().contains("spd") || attr.toLowerCase().contains("speed")) {
			return SPEED;
		}
		if(attr.toLowerCase().contains("luk") || attr.toLowerCase().contains("luck")) {
			return LUCK;
		}
		if(attr.toLowerCase().contains("def")) {
			return DEFENCE;
		}
		if(attr.toLowerCase().contains("wis")) {
			return WISDOM;
		}
		if(attr.toLowerCase().contains("gold")) {
			return GOLD;
		}
		
		return HEALTH_POINTS;
	}
	
	public void putSpell(Spells spell) {
		spells.put(spells.size(), spell);
	}
	
	public void putSpell(String spell) {
		spells.put(spells.size(), Spells.get(spell));
	}
	
	
}

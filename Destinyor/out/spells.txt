package me.jacob.macdougall.magic;

import java.util.HashMap;
import java.util.Map;

import me.jacob.macdougall.enemies.Enemies;
import me.jacob.macdougall.player.Player;

public class Spells {
	
	/*
	 * Makes spells have their own folder with each file being an entities spell list, like dialouges
	 */
	
	public static Map<String, Spells> spells = new HashMap<>();
        public static Map<Integer, String> names = new HashMap<>();
        
        public static Spells Attack = new Spells("Attack", Element.get(Element.PHYSICAL), 0, 1, 0, "0");
	
	public Element element;
	public String name;
	public int damage;
	public int targets;
	public int cost;
    public boolean targeted = false;
    public boolean UseAbleOutsideCombat = false;
    public int effect = Normal;
    public boolean inUse = false;

	public int[] condition;
	public Effect effects;
	
	public Player player;
	public Enemies enemy;
	
	//public int targets = 0;
	
	//																																										// Heals, Statboosts
	public static final int Normal = 0, Requires_Sword = 1, Requires_Shield = 2, Requires_Combo = 3, Requires_Two_Hands = 4, UseAbleOutside = 5, AoE = 6, Ignore_Defense = 7, Helpful = 8, Relative = 9;
	
	// 0 normal spell, 1 requires sword, 2 requires sheild, 3 requires combo, 4 reuqires two handed, 
	int conditions = 0;
	
	
	public Spells(String name, Element element, int damage,  int targets, int cost, String Condition) {
		this.name = name;
		this.element = element;
		this.damage = damage;
		this.targets = targets;
		this.cost = cost;
		if(damage > 0)
			effects = Effect.getEffect("Damage", "HP", damage, "Damage the target by " + damage + " points");
		else if(damage < 0)
			effects = Effect.getEffect("Healing", "HP", -damage, "Heals the target by " + -damage + " points");
		else
			effects = null; // for relative values
		
		if(Condition.contains(",")) {
		String[] temp = Condition.split(",");
		condition = new int[temp.length];
		for(int i = 0; i < temp.length; i++) {
			this.condition[i] = Integer.parseInt(temp[i]);
		}
		} else {
			condition = new int[1];
			condition[0] = Integer.parseInt(Condition);
		}
		
                put(name, this);
                names.put(spells.size() + 1, name);
	}
	
	public Spells(String name, Element element, int damage, int targets, int cost) {
		
	}
	
	protected Spells(Spells spell) {
		
	}
	
	public static void put(String name, Spells spells) {
		Spells.spells.put(name, spells);
	}
	
	public static Spells get(String name) {
		return spells.get(name);
	}
	
	public Spells newInstance() {
		Spells spell = new Spells(this);
		
		return spell;
	}
	
	public static Spells newInstance(String name) {
		Spells spell = new Spells(spells.get(name));
		
		return spell;
	}
	
	public static Spells newInstance(Spells spell) {
		Spells s = new Spells(spell);
		
		return s;
	}
	
	public int getCombo() {
		return 0;
	}
        
        public void attack(Player player, Enemies enemy2) {
//            if(entity.Resistance.getElement() != null) {
//                if(entity.Resistance.getElement().equals(this.element.getElement())) {
//                    entity.HP = -((this.damage * player.Wis) / 2);
//                } else {
//                    if(entity.Resistance.getWeakness().equals(this.element.getElement()))
//                        entity.HP = -((this.damage * player.Wis) * 2);
//                    else
//                        entity.HP = -(this.damage * player.Wis);
//                }
//            } else {
        	this.player = player;
        	this.enemy = enemy2;
        	enemy2.setStatRelative(Enemies.HEALTH_POINTS, -(this.damage * player.getStat(Player.WISDOM)));
            //player.mana = -this.cost;
           // }
        }
        
        public int getDamage(int relative) {
        	if(damage == 0)
        	return (int) Math.floor(relative * this.effects.appliedAttribute);
        	else
        	return (int) this.effects.appliedAttribute;
        }
	
}
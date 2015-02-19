package me.jacob.macdougall.magic;

import java.util.HashMap;
import java.util.Map;

import me.jacob.macdougall.enemies.Dummy;

public class Spells {
	// Rename when you delete Spells.java
	
	public static Map<String, Spells> spells = new HashMap<String, Spells>();
	
	private float damage;
	private String name;
	private int targets;
	private int attribute;
	
	public Spells(String name, float damage, int targets, String attribute) {
		this.damage = damage;
		this.name = name;
		this.targets = targets;
		this.attribute = Dummy.convertToAttribute(attribute);
	}
	
	public boolean isPercentage() {
		return ((damage >= 1 || damage <= -1) && damage != 0);
	}
	
	public float getDamage() {
		if(isPercentage()) {
			return 1 + damage;
		} else {
			return damage;
		}
	}
	
	public String getName() {
		return name;
	}
	
	public void attack(Dummy caster, Dummy target) {
		float actualDamage = 0;
		if(damage > 1 || damage < -1) {
			actualDamage =  -damage;
		}
		if(actualDamage != 0) {
			target.setStatRelative(attribute, (int) actualDamage);
		} else {
			if(damage == 0) {
				target.setStat(attribute, 0);
			} else {
				target.setStatRelative(attribute, (int) (target.getStat(attribute) * damage));
			}
		}
	}
	
	public int getTargets() {
		return targets;
	}
	
	public static Spells get(String name) {
		return spells.get(name);
	}
}

package me.jacob.macdougall.items;

import graphic.engine.screen.GameFont;
import graphic.engine.screen.Screen;

import java.awt.Color;
import java.awt.Transparency;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import me.jacob.macdougall.magic.*;
import me.jacob.macdougall.npcs.body.Limb;

public class Equipment extends Items {

	// Resell Value = (Original Price - ((years of use * 100) + (missing part cost / 2))) / 2 

	public static Map<String, Equipment> equipment = new HashMap<>();
	public static Map<Integer, String> names = new HashMap<>();

	public int damage = 0;
	public int minDamage = 0;
	public int maxDamage = 0;
	public Element element;
	public Spells spellEffect;
	public int attribute = 0;
	public String attributeType = "";
	public boolean equipped = false;
	public boolean isArmour = true;

	public Limb limb; // What limb the item attaches to.

	/**
	 * 
	 * @param name
	 * @param damage
	 * @param cost
	 * @param element
	 * @param limb
	 * @param spelleffect
         * @param  isArmour
	 */
	public Equipment(String name, int damage, int cost, Element element, Limb limb, Spells spelleffect, boolean isArmour) {
		this.name = name;
		this.damage = damage;
		this.cost = cost;
		this.element = element;
		this.limb = limb;
		if(spelleffect != null) {
//			spelleffect = Spells.newInstance(spelleffect.name);
//			spelleffect.cost = 0;
		}
		this.spellEffect = spelleffect;
		this.equippable = true;
		this.isArmour = isArmour;
		names.put(equipment.size() - 1, name);
		put(name, this);
	}

	protected Equipment(Equipment equip) {
		this.name = equip.name;
		this.damage = equip.damage;
		this.cost = equip.cost;
		this.element = equip.element;
		this.limb = equip.limb;
		this.spellEffect = equip.spellEffect;
		this.equippable = equip.equippable;
		this.isArmour = equip.isArmour;
	}
        
    @Override
	public Equipment newInstance() {
		Equipment equip = new Equipment(this);

		return equip;
	}

	public static Equipment newInstance(String name) {
		Equipment equip = new Equipment(equipment.get(name));

		return equip;
	}

	public static Equipment newInstance(Equipment equip) {
		Equipment e = new Equipment(equip);

		return e;
	}

	public void render(Screen screen, int x, int y) {
		if(!name.equals("null")) {
			GameFont.render(name, screen, x, y);
			GameFont.render(damage + ", " + cost, screen, (name.length() * 8) + x, y);
			if(limb != null) {
				GameFont.render(", " + limb.getName(), screen, (name.length() * 8) + x + 28, y);
			}
		}
	}
	
//	public void render(Screen screen, int number) {
//		//screen.render(sprite, x, y);
//	}

	public static Equipment get(String key) {
		return equipment.get(key);
	}

	public static void put(String key, Equipment equip) {
		equipment.put(key, equip);
		items.put(key, equip);
	}

	/**
	 * 
	 * @return
	 */
	public boolean isWeapon() {

		boolean isLimbAvaible = (limb != null);
		boolean isLimb = false;

		if(isLimbAvaible)
			isLimb = (limb == Limb.getLimb("Left Hand") || limb == Limb.getLimb("Right Hand") || limb == Limb.getLimb("Hands"));

		return (isLimb && !isArmour);
	}

	public boolean isArmour() {

		boolean isLimbAvaible = (limb != null);
		boolean isLimb = false;

		if(isLimbAvaible)
			isLimb = (limb == Limb.getLimb("Left Hand") || limb == Limb.getLimb("Right Hand") || limb == Limb.getLimb("Hands"));

		return (!isLimb || isArmour);
	}

	public void changeArmourColor(int area) {
		if(this.equipped) {
			for(int i = 0; i < frame.pixels.length; i++) {
				Color colour = new Color(frame.pixels[i], true);
				if(colour.getTransparency() != Transparency.TRANSLUCENT) {
					// Do something with the colour
				}
			}
			// grab pixel data and put it on the player in the correct area
			// Head, Body, Arms, Leg, ect
		}
	}

	public int getDamage() {
		Random random = new Random();
		int d = random.nextInt(maxDamage - minDamage) + minDamage;
		return d;
	}

}

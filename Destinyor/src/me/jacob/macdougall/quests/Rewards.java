package me.jacob.macdougall.quests;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import me.jacob.macdougall.items.Equipment;
import me.jacob.macdougall.items.Items;
import me.jacob.macdougall.magic.Effect;
import me.jacob.macdougall.player.Player;

public class Rewards {

	public static Map<String, Rewards> rewards;
	public String name;

	public List<Object> reward = new ArrayList<>();

	public Map<String, Rewards> itemReward;
	public Map<String, Rewards> effectReward;
	public Map<String, Rewards> playerReward;

	public Equipment[] item;
	public Effect[] effect;
	public Player[] player;

	/**
	 * 
	 * @param item
	 * @param effect
	 * @param player
	 */
	public Rewards(Equipment[] item, Effect[] effect, Player[] player) {
		this.item = item;
		this.effect = effect;
		this.player = player;
	}

	/**
	 * 
	 * @param loot
	 */
	public Rewards(String loot) {
		String reward = loot;
		reward = reward.replace(", ", ",");
		String[] rewards = reward.split(",");

		for(String r : rewards) {
			if(r.contains("gold")) {
				
				break;
			}

			for(Items i : Items.items.values()) {
				if(i.name.equals(r)) {
					this.reward.add(i);
					break;
				}
			}

			for(Player p : Player.players) {
				if(p.getName().equals(r)) {
					this.reward.add(p);
					break;
				}
			}
		}
	}

	public Object getReward() {
		Object[] rewards = new Object[itemReward.size() + effectReward.size() + playerReward.size()];
		for(int i = 0; i < rewards.length; i++) {
			if(item != null)
				if(item[i] != null)
					rewards[i] = item[i];

			if(effect != null)
				if(effect[i] != null)
					rewards[i] = effect[i];

			if(player != null)
				if(player[i] != null)
					rewards[i] = player[i];
		}
		return rewards;
	}

}

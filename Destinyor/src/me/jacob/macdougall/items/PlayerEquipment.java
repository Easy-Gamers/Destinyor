package me.jacob.macdougall.items;

import graphic.engine.screen.GameFont;
import graphic.engine.screen.Screen;
import me.jacob.macdougall.Time;
import me.jacob.macdougall.graphics.UI;
import me.jacob.macdougall.input.Keys;
import me.jacob.macdougall.player.Player;

public class PlayerEquipment {

	public PlayerEquipment() {

	}

	int currentEquipment = 0;
	int p = 6;

	Player player = (p-6 >= 0 && p-6 <= 4) ? Player.getActualPlayers()[p - 6] : Player.getActualPlayers()[0];

	public void renderEquipment(Screen screen) {
		p = UI.menu;
		player = (p-6 >= 0 && p-6 <= 4) ? Player.getActualPlayers()[p - 6] : Player.getActualPlayers()[0];
		int px = 30;
		int py = 0;
		int x = 6;
		int y = 0;

		UI.renderInventory(screen);

		if(player != null) {

			//Equipment item = (Equipment) Player.inventory.get(currentEquipment);
			if(player.hasEquipment() && player.getEquipment(currentEquipment) != null) {
				Equipment item = player.getEquipment(currentEquipment);

				GameFont.render("Name: " + item.name, screen, 12, 288);
				GameFont.render("Equipped: " + item.equipped, screen, 12, 288 + 16);
				GameFont.render("Attack: " + item.damage, screen, 12, 288 + 32);
				GameFont.render("Price: " + item.cost, screen, 12, 288 + 48);
				GameFont.render("Element: " + item.element.getElement(), screen, 12, 288 + 64);
				GameFont.render("Equipped to: " + item.limb.getName(), screen, 12, 288 - 16);
			}

			if(UI.menu >= 6 && UI.menu <= 9 && UI.menu < Player.getActualPlayers().length + 6) {
				for(int i = 0; i < Equipment.items.size(); i++) {
					GameFont.render(player.getName(), screen, px, py);
					if(player.equipped.size() > 0 && player.getEquipment(i) != null) {
						player.getEquipment(i).render(screen, x, y + 12 * (i + 1));
						//if(!player.Equipment(i).name.equals("null")) {
						//y += 0;
						//}

					}
				}
				px += 144 - 10;
				x += ((768 / 2) / 4) + 50;
			}
		}
	}

	public void updateEquipment() {
		if(UI.menu >= 6 && UI.menu <= 9 && UI.menu < Player.getActualPlayers().length + 6) {
			p = UI.menu;
			player = (p-6 >= 0 && p-6 <= 4) ? Player.getActualPlayers()[p - 6] : Player.getActualPlayers()[0];
			
			if(player != null) {
				if(Time.getKeyTimer(10, false)) {
					if(Keys.MoveUp()) {
						currentEquipment--;
						Time.resetKeyTimer();
					}
					if(Keys.MoveDown()) {
						currentEquipment++;
						Time.resetKeyTimer();
					}
				}

				if(currentEquipment >= player.equipped.size()) {
					currentEquipment = 0;
				}

				if(currentEquipment < 0) {
					currentEquipment = player.equipped.size() - 1;
				}
			}
		}
	}

}

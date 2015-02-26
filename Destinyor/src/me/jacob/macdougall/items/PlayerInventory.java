package me.jacob.macdougall.items;

import input.engine.mouse.Mouse;
import graphic.engine.screen.GameFont;
import graphic.engine.screen.Screen;

import me.jacob.macdougall.Time;
import me.jacob.macdougall.graphics.UI;
import me.jacob.macdougall.gui.Commands;
import me.jacob.macdougall.gui.DropDowns;
import me.jacob.macdougall.input.Keys;
import me.jacob.macdougall.player.Player;

public class PlayerInventory {

	/**
	 * Class that handles player inventory
	 */
	public PlayerInventory() {

	}
	
	public static DropDowns dropdown;
	
	public static void update(Mouse mouse) {
		if(!Player.inventory.isEmpty()) {
			if(dropdown == null || dropdown.getLength() != Player.inventory.size()) {
				Commands[] commands = new Commands[Player.inventory.size()];
				int maxWidth = 0;
				for(int i = 0; i < Player.inventory.size(); i++) {
					Items item = Player.inventory.get(i);
					commands[i] = new Commands(item.name, 12, 8);
					if(commands[i].getAbsoluteWidth() > maxWidth) {
						maxWidth = commands[i].getAbsoluteWidth();
					}
				}
				dropdown = new DropDowns("Inventory", 12, 16, maxWidth, 8, Player.inventory.size(), 0, commands);
				dropdown.alwaysFocus();
			}
		}
		dropdown.update(mouse);
	}

	int currentItem = 0;

	public void renderInventory(Screen screen) {
		UI.renderInventory(screen);
		if(!Player.inventory.isEmpty()) {
			dropdown.render(screen);
			Items item = Player.inventory.get(dropdown.getCurrentLocation());
			GameFont.render("Name: " + item.name, screen, 12, 288);
			GameFont.render("Price: " + item.cost, screen, 12, 288 + 48);
		}
	}

	public void updateInventory(Mouse mouse) {
		if(UI.menu == UI.Inventory)
		update(mouse);
		
	}

}

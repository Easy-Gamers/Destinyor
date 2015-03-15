package me.jacob.macdougall.items;

import input.engine.mouse.Mouse;
import graphic.engine.screen.Bitmap;
import graphic.engine.screen.GameFont;
import graphic.engine.screen.Screen;

import me.jacob.macdougall.Time;
import me.jacob.macdougall.graphics.UI;
import me.jacob.macdougall.gui.Commands;
import me.jacob.macdougall.gui.DropDowns;
import me.jacob.macdougall.gui.GUI_Objects;
import me.jacob.macdougall.gui.Scroll;
import me.jacob.macdougall.gui.Scroller;
import me.jacob.macdougall.input.Keys;
import me.jacob.macdougall.player.Player;

public class PlayerInventory {

	/**
	 * Class that handles player inventory
	 */
	public PlayerInventory() {
		s = new Scroller("Inventory", 8, 8, 512, 368, 12, Scroll.Y);
		s.setSpace(8 * 2);
		//public Scroller(String name, int x, int y, int width, int height, int aps, int direction, GUI_Objects... objects) {
	}
	
	private static Scroller s;
	
	public static void update(Mouse mouse) {
		boolean exce = false;
		try {
			s.update(mouse);
		} catch(NullPointerException e) {
			exce = true;
		}
		if(!Player.inventory.isEmpty()) {
			boolean check = false;
			if(!exce) {
				if(Player.inventory.size() == s.getLength()) {
					for(int i = 0; i < Player.inventory.size(); i++) {
						if(Player.inventory.get(i).name != s.get(i).getName()) {
							check = true;
						}
					}
				} else {
					check = true;
				}
			} else {
				check = true;
			}
			if(check) {
				s.clear();
				for(Items item : Player.inventory.values()) {
					GUI_Objects object = new GUI_Objects(item.name, 0, 0, item.name.length() * 7, 8);
					System.out.println("Adding: " + object.getName());
					s.add(object);
				}
			}
		}
	}

	int currentItem = 0;

	public void renderInventory(Screen screen) {
		UI.renderInventory(screen);
		if(!Player.inventory.isEmpty()) {
			try {
				//s.render(screen);
				for(int i = 0; i < s.getAllCurrent().length; i++) {
					GameFont.render(s.getAllCurrent()[i].getName(), screen, 8, 8 * (i + 1));
				}
				Items item = Player.inventory.get(s.getLocation());
				GameFont.render("Name: " + item.name, screen, 12, 288);
				GameFont.render("Price: " + item.cost, screen, 12, 288 + 48);
			} catch(NullPointerException e) {
				
			}
		}
	}

	public void updateInventory(Mouse mouse) {
		if(UI.menu == UI.Inventory)
			update(mouse);
		
	}

}

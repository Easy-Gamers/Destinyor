package me.jacob.macdougall.input;

import input.engine.keyboard.Key;

public class Keys {
	
	// First class I used CONSTANTS for instead of constants
	
	public static final String LEFT = "Left";
	public static final String RIGHT = "Right";
	public static final String UP = "Up";
	public static final String DOWN = "Down";
	public static final String ESCAPE = "Escape";
	//public static final String ATTACK = "Attack";
	public static final String HOME = "Home";
	public static final String PAGE_UP = "Page Up";
	public static final String PAGE_DOWN = "Page Down";
	public static final String ENTER = "Enter";
	public static final String SHIFT = "Shift";
	
	public static final String ONE = "1";
	public static final String TWO = "2";
	public static final String THREE = "3";
	public static final String FOUR = "4";
	public static final String FIVE = "5";
	
	public static final String INVENTORY = "Inventory";
	public static final String EQUIPMENT = "Equipment";
	public static final String MINIMAP = "Minimap";
	
	public static boolean MoveLeft() {
		for(Key key : Key.keys) {
			if(key.getEffect().equals(LEFT)) {
				if(key.down) return true;
			}
		}
		return false;
	}
	
	public static boolean MoveRight() {
		for(Key key : Key.keys) {
			if(key.getEffect().equals(RIGHT)) {
				if(key.down) return true;
			}
		}
		return false;
	}
	
	public static boolean MoveUp() {
		for(Key key : Key.keys) {
			if(key.getEffect().equals(UP)) {
				if(key.down) return true;
			}
		}
		return false;
	}
	
	public static boolean MoveDown() {
		for(Key key : Key.keys) {
			if(key.getEffect().equals(DOWN)) {
				if(key.down) return true;
			}
		}
		return false;
	}
	
	public static boolean Escape() {
		for(Key key : Key.keys) {
			if(key.getEffect().equals(ESCAPE)) {
				if(key.down) return true;
			}
		}
		return false;
	}
	
//	public static boolean Attack() {
//		for(Key key : Key.keys) {
//			if(key.getEffect().equals(ATTACK)) {
//				if(key.down) return true;
//			}
//		}
//		return false;
//	}
	
	public static boolean Home() {
		for(Key key : Key.keys) {
			if(key.getEffect().equals(HOME)) {
				if(key.down) return true;
			}
		}
		return false;
	}
	
	public static boolean PageUp() {
		for(Key key : Key.keys) {
			if(key.getEffect().equals(PAGE_UP)) {
				if(key.down) return true;
			}
		}
		return false;
	}
	
	public static boolean PageDown() {
		for(Key key : Key.keys) {
			if(key.getEffect().equals(PAGE_DOWN)) {
				if(key.down) return true;
			}
		}
		return false;
	}
	
	public static boolean Enter() {
		for(Key key : Key.keys) {
			if(key.getEffect().equals(ENTER)) {
				if(key.down) return true;
			}
		}
		return false;
	}
	
	public static boolean Enemy1() {
		for(Key key : Key.keys) {
			if(key.getEffect().equals(ONE)) {
				if(key.down) return true;
			}
		}
		return false;
	}
	
	public static boolean Enemy2() {
		for(Key key : Key.keys) {
			if(key.getEffect().equals(TWO)) {
				if(key.down) return true;
			}
		}
		return false;
	}
	
	public static boolean Enemy3() {
		for(Key key : Key.keys) {
			if(key.getEffect().equals(THREE)) {
				if(key.down) return true;
			}
		}
		return false;
	}
	
	public static boolean Enemy4() {
		for(Key key : Key.keys) {
			if(key.getEffect().equals(FOUR)) {
				if(key.down) return true;
			}
		}
		return false;
	}
	
	public static boolean Enemy5() {
		for(Key key : Key.keys) {
			if(key.getEffect().equals(FIVE)) {
				if(key.down) return true;
			}
		}
		return false;
	}
	
	public static boolean Inventory() {
		for(Key key : Key.keys) {
			if(key.getEffect().equals(INVENTORY)) {
				if(key.down) return true;
			}
		}
		return false;
	}
	
	public static boolean Equipment() {
		for(Key key : Key.keys) {
			if(key.getEffect().equals(EQUIPMENT)) {
				if(key.down) return true;
			}
		}
		return false;
	}
	
	public static boolean Minimap() {
		for(Key key : Key.keys) {
			if(key.getEffect().equals(MINIMAP)) {
				if(key.down) return true;
			}
		}
		return false;
	}
	
	public static boolean Shift() {
		for(Key key : Key.keys) {
			if(key.getEffect().equals(SHIFT)) {
				if(key.down) return true;
			}
		}
		return false;
	}
	
}

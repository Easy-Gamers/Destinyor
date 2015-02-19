package me.jacob.macdougall.input;

import input.engine.keyboard.Key;

public class KeyChanger {
	
	public static void setKey(Key key, String effect) {
		key.setEffect(effect);
	}
	
	public static void setKeys(String key, String effect) {
		Key.keys.get(Key.getKey(key).kevent).setEffect(effect);
	}
	
	public static void setKeys(int keyID, String effect) {
		Key.keys.get(keyID).setEffect(effect);
	}
	
}

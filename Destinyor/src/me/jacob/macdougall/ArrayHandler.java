package me.jacob.macdougall;

import java.util.List;

import me.jacob.macdougall.files.mod.Mod;

public class ArrayHandler {
	
	// A class that is equivalent (for my purposes) to List, however it stays in order, mostly used for the defaultReader, due to taking less memory in that instance, also acts as a que
	
	private Object[][] array;
	
	private int x;
	
	public ArrayHandler() {
		x = 0;
	}
	
	public void add(Object object) {
		if(array != null) {
				if(array.length <= x) {
					Object[][] temp = new Object[array.length + 1][];
					for(int i = 0; i < array.length; i++) {
						temp[i] = array[i];
					}
					array = temp;
				}
				if(array[x] != null) {
					Object[] temp = new Object[array[x].length + 1];
					
					for(int i = 0; i < array[x].length; i++) {
						temp[i] = array[x][i];
					}
					
					temp[array[x].length] = object;
					array[x] = temp;
				} else {
					Object[] temp = { object };
					array[x] = temp;
				}
		} else {
			Object[][] temp = {
					{ object }
			};
			array = temp;
		}
	}
	
	public void remove(int x, int y) {
		if(array != null) {
			if(array.length >= x && array[x].length >= y) {
				Object[][] temp = new Object[array.length - 1][];
				for(int i = 0; i < x; i++) {
					for(int j = 0; j < y; j++) {
						temp[i][j] = array[i][j];
					}
				}
				for(int i = x + 1; i < temp.length; i++) {
					for(int j = y + 1; j < temp[i].length; j++) {
						temp[i - 1][j - 1] = array[i][j];
					}
				}
			}
		}
	}
	
	public void remove(Object object) {
		if(array != null) {
			Object[][] temp = new Object[array.length - 1][];
			int i = 0;
			int j = 0;
			for(Object[] objectArray : array) {
				for(Object objects : objectArray) {
					if(objects != object) {
						temp[i][j] = objects;
						j++;
					}
				}
				j = 0;
				i++;
			}
		}
	}
	
	public void close(Object object) {
		add(object);
		x++;
	}
	
	public void close() {
		x++;
	}
	
	public void clear() {
		array = null;
	}
	
	public Object[][] get() {
		return array;
	}
	
	public Object[] get(int i) {
		return array[i];
	}
	
	public Object get(int i, int j) {
		return array[i][j];
	}
	
	public int getLength() {
		return array.length;
	}
	
	public int getLength(int i) {
		return array[i].length;
	}
	
	public static Object[] convertSingle(List<?> list) {
		Object[] array = new Mod[list.size()];
		for(int i = 0; i < list.size(); i++) {
			array[i] = list.get(i);
		}
		return array;
	}
	
	public static Object[][] convertMulti(List<Object[]> list) {
		
		return null;
	}
	
}

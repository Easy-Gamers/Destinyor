package me.jacob.macdougall;

import java.util.List;

import me.jacob.macdougall.files.mod.Mod;

public class ArrayHandler {
	
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
	
	public void close(Object object) {
		add(object);
		x++;
	}
	
	public void close() {
		x++;
	}
	
	public Object[][] get() {
		return array;
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

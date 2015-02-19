package me.jacob.macdougall;

import java.util.ArrayList;
import java.util.List;

public class DebugWriter {

	public static List<String> strings = new ArrayList<>();

	public static void println(String args) {
		strings.add(args);
	}

	public static void removeln(String args) {
		strings.remove(args);
	}

	public static String getln(int key) {
		return strings.get(key);
	}
	
	public static void RemoveDebug(String remove) {
		for(int i = 0; i < DebugWriter.strings.size(); i++) {
			if(DebugWriter.getln(i).contains(remove)) {
				DebugWriter.removeln(DebugWriter.getln(i));
				i--;
				Debug.refresh();
			}
		}
	}

}

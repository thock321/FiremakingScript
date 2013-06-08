package org.scripts.firemaking.util;

public class Util {
	
	public static String capitalize(String string) {
		char firstLetter = string.toUpperCase().charAt(0);
		return firstLetter + string.substring(1);
	}

}

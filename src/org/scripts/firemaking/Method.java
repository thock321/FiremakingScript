package org.scripts.firemaking;

public enum Method {
	
	BONFIRES;
	
	public static Method getMethod(String method) {
		for (Method m : Method.values()) {
			if (m.toString().equalsIgnoreCase(method))
				return m;
		}
		return null;
	}

}

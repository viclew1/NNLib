package fr.lewon.utils;

import com.rits.cloning.Cloner;

public enum CloneUtil {
	
	INSTANCE;
	
	private Cloner cloner;
	
	private CloneUtil() {
		cloner = new Cloner();
	}
	
	public <T> T deepCopy(T object) {
		return cloner.deepClone(object);
	}
	
}

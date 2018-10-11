package fr.lewon.utils;

import java.util.List;
import java.util.Random;

public enum ArrayUtils {
	
	INSTANCE;
	
	private final Random r = new Random();

	public <T> void shuffleArray(T[] array) {
		for (int i=array.length-1 ; i>= 1 ; i--) {
			int j = r.nextInt(i+1);
			T temp = array[j];
			array[j] = array[i];
			array[i] = temp;
		}
	}
	
	public <T> void shuffleList(List<T> list) {
		for (int i=list.size()-1 ; i>= 1 ; i--) {
			int j = r.nextInt(i+1);
			T temp = list.get(j);
			list.set(j, list.get(i));
			list.set(i, temp);
		}
	}
}

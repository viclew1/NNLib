package fr.lewon.utils;

import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

public enum ListsUtil {
	
	INSTANCE;
	
	private final Random r = new Random();
	
	public <E> void shuffleList(List<E> list) {
		for (int i=list.size()-1 ; i>= 1 ; i--) {
			int j = r.nextInt(i+1);
			E temp = list.get(j);
			list.set(j, list.get(i));
			list.set(i, temp);
		}
	}
	
	public <E> List<E> flattenLists(List<List<E>> lists) {
		return lists.stream()
				.flatMap(List::stream)
				.collect(Collectors.toList());
	}
}

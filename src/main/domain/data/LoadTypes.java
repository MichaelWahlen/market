package main.domain.data;

import java.util.Map;

public interface LoadTypes<T extends StaticData> {
	
	public void loadTypes(String string, Map<Integer, T> types);
	
}

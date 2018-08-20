package main.domain.data;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import main.utilities.ParserCSV;
import main.utilities.StringUtilities;


public class GenericFactory<T extends StaticData> {
	
	private Map<Integer, T> types = new HashMap<Integer, T>();	
	private List<String> columnNames;		
	
	public GenericFactory(Class<T> classRef) {	
		List<String> initialStrings = ParserCSV.parseToStrings(new File("src/resources/"+classRef.getSimpleName()+".csv"));
		columnNames = StringUtilities.decomposeValueSeperatedString(initialStrings.get(0), '|');
		initialStrings.remove(0);		
		try {
			T trya = classRef.newInstance();
			for(String string:initialStrings) {
				@SuppressWarnings("unchecked")
				T type = (T) trya.get(string);
				types.put(type.getCode(), type);
			}
		} catch (Exception e) {			
			e.printStackTrace();
		} 		
	}
	   
	public T getType(int switchKey){		  	   
		return types.get(switchKey);
	}
	
	public List<Integer> getKeys(){
		List<Integer> returnList = new ArrayList<Integer>();
		returnList.addAll(types.keySet());
		return returnList;
	}
	
	
	public List<String> getColumns() {
		return columnNames; 
	}
	
	public Object[][] getObjectRepresentation(){	
		Object[][] typeToObjects = new Object[types.size()][columnNames.size()];		
		int j = 0;
		for(T product:types.values()) {
			List<String> representation = new ArrayList<String>();
			representation = product.getDetailsInOrder();
			for(int i=0;i<representation.size();i++) {
				typeToObjects[j][i] = representation.get(i);
			}
			j++;
		}
		return typeToObjects;
	}
	
}

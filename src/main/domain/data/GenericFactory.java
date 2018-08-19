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
	private static List<String> columnNames;	

	public GenericFactory(LoadTypes<T> type, String fileLocation) {	
		List<String> initialStrings = ParserCSV.parseToStrings(new File(fileLocation));
		columnNames = StringUtilities.decomposeValueSeperatedString(initialStrings.get(0), '|');
		initialStrings.remove(0);
		for(String string:initialStrings) {
			type.loadTypes(string, types);
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

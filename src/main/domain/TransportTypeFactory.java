package main.domain;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import main.utilities.ParserCSV;
import main.utilities.StringUtilities;

public class TransportTypeFactory {
	
	private static TransportTypeFactory instance = null;
	private Map<String, TransportType> types = new HashMap<String, TransportType>();
		   
	private TransportTypeFactory() {	
		loadTypes();
	}
	
	public static TransportTypeFactory getInstance() {
		if(instance == null) {
			instance = new TransportTypeFactory();
		}
		return instance;
	}
	   
	public TransportType getType(String key){		  	   
		return types.get(key);
	}
	
	public List<String> getKeys(){
		List<String> returnList = new ArrayList<String>();
		returnList.addAll(types.keySet());
		return returnList;
	}
	
	private void loadTypes() {
		List<String> manufacturerstring = ParserCSV.parseToStrings(new File("src/resources/TransportType.csv"));
		for(String string:manufacturerstring) {			
			List<String> moreString = StringUtilities.decomposeValueSeperatedString(string, '|');
			TransportType manufacturer = new TransportType();			
			manufacturer.setName(moreString.get(0));					
			this.types.put(manufacturer.getName(), manufacturer);
		}
	}
	
	public static List<String> getColumns() {
		return Arrays.asList("name"); 
	}
	
	public Object[][] getObjectRepresentation(){	
		Object[][] objects = new Object[types.size()][1];
		int i = 0;
		for(TransportType type:types.values()) {
			objects[i][0] = type.getName();
			i++;			
		}
		return objects;
	}


}

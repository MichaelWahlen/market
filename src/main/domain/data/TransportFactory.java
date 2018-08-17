package main.domain.data;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import main.utilities.ParserCSV;
import main.utilities.StringUtilities;

public class TransportFactory {
	
	private static TransportFactory instance = null;
	private Map<String, Transport> types = new HashMap<String, Transport>();
		   
	private TransportFactory() {	
		loadTypes();
	}
	
	public static TransportFactory getInstance() {
		if(instance == null) {
			instance = new TransportFactory();
		}
		return instance;
	}
	   
	public Transport getType(String key){		  	   
		return types.get(key);
	}
	
	public List<String> getKeys(){
		List<String> returnList = new ArrayList<String>();
		returnList.addAll(types.keySet());
		return returnList;
	}
	
	private void loadTypes() {
		List<String> manufacturerstring = ParserCSV.parseToStrings(new File("src/resources/Transport.csv"));
		for(String string:manufacturerstring) {			
			List<String> moreString = StringUtilities.decomposeValueSeperatedString(string, '|');
			Transport manufacturer = new Transport();
			manufacturer.setCode(Integer.parseInt(moreString.get(0)));
			manufacturer.setName(moreString.get(1));					
			this.types.put(manufacturer.getName(), manufacturer);
		}
	}
	
	public static List<String> getColumns() {
		return Arrays.asList("code","name"); 
	}
	
	public Object[][] getObjectRepresentation(){	
		Object[][] objects = new Object[types.size()][2];
		int i = 0;
		for(Transport type:types.values()) {
			objects[i][0] = type.getCode();
			objects[i][1] = type.getName();
			i++;			
		}
		return objects;
	}


}

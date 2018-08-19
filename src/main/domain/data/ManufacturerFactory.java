package main.domain.data;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import main.utilities.ParserCSV;
import main.utilities.StringUtilities;

public class ManufacturerFactory {
	
	private static ManufacturerFactory instance = null;
	private Map<String, Manufacturer> types = new HashMap<String, Manufacturer>();
	private static List<String> columnNames;
		   
	private ManufacturerFactory(ResourceFactory resourceFactory) {	
		loadManufacturers(resourceFactory);
	}
	
	public static ManufacturerFactory getInstance() {
		if(instance == null) {
			instance = new ManufacturerFactory(ResourceFactory.getInstance());
		}
		return instance;
	}
	   
	public Manufacturer getResource(String key){		  	   
		return types.get(key);
	}
	
	public List<String> getManufacturerKeys(){
		List<String> returnList = new ArrayList<String>();
		returnList.addAll(types.keySet());
		return returnList;
	}
	
	private void loadManufacturers(ResourceFactory resourceFactory) {
		List<String> initialStrings = ParserCSV.parseToStrings(new File("src/resources/Manufacturer.csv"));
		columnNames = StringUtilities.decomposeValueSeperatedString(initialStrings.get(0), '|');
		initialStrings.remove(0);
		for(String string:initialStrings) {			
			List<String> moreString = StringUtilities.decomposeValueSeperatedString(string, '|');
			Manufacturer manufacturer = new Manufacturer(resourceFactory.getResourceKeys());
			manufacturer.setCode(Integer.parseInt(moreString.get(0)));
			manufacturer.setName(moreString.get(1));
			List<String> evenMoreString = StringUtilities.decomposeValueSeperatedString(moreString.get(2), ';');
			for(String stringie:evenMoreString) {
				manufacturer.addManufacturedResource(resourceFactory.getType(stringie));
			}			
			this.types.put(manufacturer.getName(), manufacturer);
		}
	}
	
	public static List<String> getColumns() {
		return columnNames; 
	}
	
	public Object[][] getObjectRepresentation(){	
		Object[][] productsToObjects = new Object[types.size()][2];
		int i = 0;
		for(Manufacturer product:types.values()) {
			productsToObjects[i][0] = product.getCode();
			productsToObjects[i][1] = product.getName();
			i++;			
		}
		return productsToObjects;
	}
}

package main.domain;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import main.utilities.ParserCSV;
import main.utilities.StringUtilities;

public class ManufacturerFactory {
	
	private static ManufacturerFactory instance = null;
	private Map<String, Manufacturer> manufacturers = new HashMap<String, Manufacturer>();
		   
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
		return manufacturers.get(key);
	}
	
	public List<String> getManufacturerKeys(){
		List<String> returnList = new ArrayList<String>();
		returnList.addAll(manufacturers.keySet());
		return returnList;
	}
	
	private void loadManufacturers(ResourceFactory resourceFactory) {
		List<String> manufacturerstring = ParserCSV.parseToStrings(new File("src/resources/Manufacturer.csv"));
		for(String string:manufacturerstring) {			
			List<String> moreString = StringUtilities.decomposeValueSeperatedString(string, '|');
			Manufacturer manufacturer = new Manufacturer(resourceFactory.getResourceKeys());			
			manufacturer.setName(moreString.get(0));
			List<String> evenMoreString = StringUtilities.decomposeValueSeperatedString(moreString.get(1), ';');
			for(String stringie:evenMoreString) {
				manufacturer.addManufacturedResource(resourceFactory.getResource(stringie));
			}			
			this.manufacturers.put(manufacturer.getName(), manufacturer);
		}
	}
	
	public static List<String> getFieldNames() {
		return Arrays.asList("name"); 
	}
	
	public Object[][] getManufacturersToObjects(){	
		Object[][] productsToObjects = new Object[manufacturers.size()][1];
		int i = 0;
		for(Manufacturer product:manufacturers.values()) {
			productsToObjects[i][0] = product.getName();
			i++;			
		}
		return productsToObjects;
	}
}

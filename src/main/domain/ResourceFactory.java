package main.domain;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import main.utilities.ParserCSV;
import main.utilities.StringUtilities;

public class ResourceFactory {
	
	private static ResourceFactory instance = null;
	private Map<String, GenerateableResource> resources = new HashMap<String, GenerateableResource>();
		   
	private ResourceFactory() {	
		loadProduct();
	}
	
	public static ResourceFactory getInstance() {
		if(instance == null) {
			instance = new ResourceFactory();
		}
		return instance;
	}
	   
	public GenerateableResource getResource(String key){		  	   
		return resources.get(key);
	}
	
	public List<String> getResourceKeys(){
		List<String> returnList = new ArrayList<String>();
		returnList.addAll(resources.keySet());
		return returnList;
	}
	
	private void loadProduct() {
		List<String> products = ParserCSV.parseToStrings(new File("src/resources/Resource.csv"));
		for(String string:products) {			
			List<String> moreString = StringUtilities.decomposeValueSeperatedString(string, '|');
			Resource resource = new Resource(moreString.get(0),moreString.get(2),Integer.parseInt(moreString.get(1)),Boolean.parseBoolean(moreString.get(3)));			
			if(!moreString.get(4).equals("")) {
			List<String> evenMoreString = StringUtilities.decomposeValueSeperatedString(moreString.get(4), ';');
				for(String mahString:evenMoreString) {
					List<String> tooManyStrings = StringUtilities.decomposeValueSeperatedString(mahString, ':');
					resource.addDemand(tooManyStrings.get(0), Integer.parseInt(tooManyStrings.get(1)));
				}
			}
			this.resources.put(resource.getName(), resource);
		}
	}
	
	public static List<String> getFieldNames() {
		return Arrays.asList("name","defaultGenerationRate","shortDescription","isCompound","demand","demandAmount"); 
	}
	
	public Object[][] getResourcesToObjects(){	
		Object[][] productsToObjects = new Object[resources.size()*3][6];
		int i = 0;
		for(GenerateableResource product:resources.values()) {
			productsToObjects[i][0] = product.getName();
			productsToObjects[i][1] = product.getDefaultGenerationRate();
			productsToObjects[i][2] = product.getShortDescription();
			productsToObjects[i][3] = product.isCompoundResource();	
			if(product.isCompoundResource()) {
				for(Entry<String, Integer> entry:product.getRequiredResources().entrySet()) {
					productsToObjects[i][0] = product.getName();
					productsToObjects[i][1] = product.getDefaultGenerationRate();
					productsToObjects[i][2] = product.getShortDescription();
					productsToObjects[i][3] = product.isCompoundResource();	
					productsToObjects[i][4] = entry.getKey();
					productsToObjects[i][5] = entry.getValue();
					i++;
				}
			} else {
				i++;
			}
		}
		return productsToObjects;
	}
}

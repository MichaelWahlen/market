package main.domain.data;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import main.domain.GenerateableResource;
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
	   
	public GenerateableResource getType(String key){		  	   
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
			Resource resource = new Resource(moreString.get(1),moreString.get(3),Integer.parseInt(moreString.get(2)),Boolean.parseBoolean(moreString.get(4)));
			resource.setCode(Integer.parseInt(moreString.get(0)));
			if(!moreString.get(5).equals("")) {
			List<String> evenMoreString = StringUtilities.decomposeValueSeperatedString(moreString.get(5), ';');
				for(String mahString:evenMoreString) {
					List<String> tooManyStrings = StringUtilities.decomposeValueSeperatedString(mahString, ':');
					resource.addDemand(tooManyStrings.get(0), Integer.parseInt(tooManyStrings.get(1)));
				}
			}
			this.resources.put(resource.getName(), resource);
		}
	}
	
	public static List<String> getColumns() {
		return Arrays.asList("code","name","defaultGenerationRate","shortDescription","isCompound","demand","demandAmount"); 
	}
	
	public Object[][] getObjectRepresentation(){	
		Object[][] productsToObjects = new Object[resources.size()*3][7];
		int i = 0;
		for(GenerateableResource product:resources.values()) {
			productsToObjects[i][0] = product.getCode();
			productsToObjects[i][1] = product.getName();
			productsToObjects[i][2] = product.getDefaultGenerationRate();
			productsToObjects[i][3] = product.getShortDescription();
			productsToObjects[i][4] = product.isCompoundResource();	
			if(product.isCompoundResource()) {
				for(Entry<String, Integer> entry:product.getRequiredResources().entrySet()) {
					productsToObjects[i][0] = product.getCode();
					productsToObjects[i][1] = product.getName();
					productsToObjects[i][2] = product.getDefaultGenerationRate();
					productsToObjects[i][3] = product.getShortDescription();
					productsToObjects[i][4] = product.isCompoundResource();	
					productsToObjects[i][5] = entry.getKey();
					productsToObjects[i][6] = entry.getValue();
					i++;
				}
			} else {
				i++;
			}
		}
		return productsToObjects;
	}
}

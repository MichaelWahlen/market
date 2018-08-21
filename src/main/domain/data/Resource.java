package main.domain.data;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import main.domain.GenerateableResource;
import main.utilities.StringUtilities;

public class Resource implements GenerateableResource, StaticData{
	
	private int defaultGenerationRate;
	private String name;
	private String shortDescription;
	private Map<Integer, Integer> requiredResources = new HashMap<Integer, Integer>();
	private boolean isCompoundResource;
	private int code;	
	
	public Resource() {
		
	}	
	
	@Override
	public String getName() {
		return name;
	}

	@Override
	public int getDefaultGenerationRate() {
		return defaultGenerationRate;
	}
	
	@Override
	public String getShortDescription() {
		return shortDescription;
	}	
	
	public void addDemand(Integer key, int amount) {
		requiredResources.put(key,amount);		
	}
	
	@Override
	public Map<Integer, Integer> getRequiredResources() {		
		return requiredResources;
	}
	
	@Override
	public boolean isCompoundResource() {
		return isCompoundResource;
	}

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public void setDefaultGenerationRate(int defaultGenerationRate) {
		this.defaultGenerationRate = defaultGenerationRate;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setShortDescription(String shortDescription) {
		this.shortDescription = shortDescription;
	}

	public void setRequiredResources(Map<Integer, Integer> requiredResources) {
		this.requiredResources = requiredResources;
	}

	public void setCompoundResource(boolean isCompoundResource) {
		this.isCompoundResource = isCompoundResource;
	}

	@Override
	public List<String> getDetailsInOrder() {
		List<String> returns = new ArrayList<String>();
		returns.add(""+code);
		returns.add(name);
		returns.add(""+defaultGenerationRate);
		returns.add(shortDescription);
		returns.add(""+isCompoundResource);
		returns.add("PlaceHolder");
		return returns;
	}

	@Override
	public StaticData get(String string) {
		List<String> moreString = StringUtilities.decomposeValueSeperatedString(string, '|');
		Resource resource = new Resource();
		resource.setCode(Integer.parseInt(moreString.get(0)));
		resource.setName(moreString.get(1));
		resource.setDefaultGenerationRate(Integer.parseInt(moreString.get(2)));		
		resource.setShortDescription(moreString.get(3));
		resource.setCompoundResource(Boolean.parseBoolean(moreString.get(4)));		
		if(!moreString.get(5).equals("")) {
		List<String> evenMoreString = StringUtilities.decomposeValueSeperatedString(moreString.get(5), ';');
			for(String mahString:evenMoreString) {
				List<String> tooManyStrings = StringUtilities.decomposeValueSeperatedString(mahString, ':');
				resource.addDemand(Integer.parseInt(tooManyStrings.get(0)), Integer.parseInt(tooManyStrings.get(1)));
			}
		}
		return resource;
	}

	


}

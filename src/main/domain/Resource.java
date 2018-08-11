package main.domain;

import java.util.HashMap;
import java.util.Map;

public class Resource implements GenerateableResource{
	
	private final int defaultGenerationRate;
	private final String name;
	private final String shortDescription;
	private Map<String, Integer> requiredResources = new HashMap<String, Integer>();
	private final boolean isCompoundResource;
	
	
	public Resource(String name, String shortDescription, int defaultGenerationRate,boolean isCompoundResource) {
		this.defaultGenerationRate = defaultGenerationRate;
		this.name = name;
		this.shortDescription = shortDescription;
		this.isCompoundResource = isCompoundResource;
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
	
	public void addDemand(String key, int amount) {
		requiredResources.put(key,amount);		
	}
	
	@Override
	public Map<String, Integer> getRequiredResources() {		
		return requiredResources;
	}
	
	@Override
	public boolean isCompoundResource() {
		return isCompoundResource;
	}
	


}

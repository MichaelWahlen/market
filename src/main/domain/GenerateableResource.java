package main.domain;

import java.util.Map;

public interface GenerateableResource {
	public String getName();	
	public int getDefaultGenerationRate();	
	public String getShortDescription();	
	public Map<Integer, Integer> getRequiredResources();
	public boolean isCompoundResource();
	public int getCode();	
}

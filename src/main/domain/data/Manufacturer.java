package main.domain.data;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import main.domain.GenerateableResource;
import main.domain.Producer;
import main.domain.StockPile;

public class Manufacturer implements Producer {

	private StockPile localStockPile;
	private List<GenerateableResource> manufacturedResources = new ArrayList<GenerateableResource>();
	private String name;
	private int code;
	
	public Manufacturer(Map<String, Integer> startStockPile) {
		localStockPile = new StockPile(new ArrayList<String>(startStockPile.keySet()));
		for(Entry<String, Integer> entry: startStockPile.entrySet()) {
			localStockPile.stockImport(entry.getKey(), entry.getValue());
		}
	}
	
	public Manufacturer(List<String> startStockPile) {
		localStockPile = new StockPile(startStockPile);		
	}
	
	public void addManufacturedResource(GenerateableResource resource) {
		manufacturedResources.add(resource);
	}
	
	public void fireProductionRun() {
		for(GenerateableResource resource:manufacturedResources) {		
			if(localStockPile.takeFromStock(resource.getRequiredResources())) {
				localStockPile.stockImport(resource.getName(), resource.getDefaultGenerationRate());
			}
		}
	}
	
	public Integer exportResource(String key, Integer amount) {		
		return localStockPile.stockExport(key, amount);		
	}
	
	public void importResource(String key, Integer amount) {
		localStockPile.stockImport(key, amount);
	}
	
	public List<String> stockNames(){
		return localStockPile.getFieldNames();
	}
	
	public Object[][] stockToObjects(){
		return localStockPile.stockToObjects();
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}
}

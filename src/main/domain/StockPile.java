package main.domain;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

public class StockPile {
	
	private Map<String, Integer> stock = new HashMap<String, Integer>();

	public StockPile(List<String> productKeys) {
		for(String productKey:productKeys) {
			stock.put(productKey, 0);
		}
	}	
	
	public Map<String, Integer> getStock() {
		return stock;
	}

	public synchronized void addToStock(String key, int amount) {
		stock.put(key, stock.get(key)+amount);
	}
	
	public synchronized boolean takeFromStock(Map<String, Integer> requiredResources) {		
		Boolean isAllAccountedFor = true;
		for(Entry<String, Integer> entry: requiredResources.entrySet()) {
			Integer availableStock = stock.get(entry.getKey());
			Integer requiredAmount = entry.getValue();
			if(availableStock<requiredAmount) {
				isAllAccountedFor = false;
				break;
			}
		}
		if(isAllAccountedFor) {
			for(Entry<String, Integer> entry: requiredResources.entrySet()) {
				stock.put(entry.getKey(), stock.get(entry.getKey())-entry.getValue());
			}
		}		
		return isAllAccountedFor;		
	}
	
	public Object[][] stockToObjects(){		
		int i = 0;
		Object[][] returnValue = new Object[stock.size()][2];
		for(Entry<String, Integer> entry: stock.entrySet()) {
			returnValue[i][0] = entry.getKey();
			returnValue[i][1] = entry.getValue();
			i++;
		}
		return returnValue;
	}
	
	public List<String> getFieldNames(){
		return Arrays.asList("name","stock"); 
	}
	
}

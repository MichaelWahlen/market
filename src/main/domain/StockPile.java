package main.domain;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

public class StockPile {
	
	private Map<Integer, Integer> stock = new HashMap<Integer, Integer>();

	public StockPile(List<Integer> startStockPile) {
		for(Integer productKey:startStockPile) {
			stock.put(productKey, 0);
		}
	}	
	
	public Map<Integer, Integer> getStock() {
		return stock;
	}

	public synchronized void stockImport(Integer key, int amount) {
		stock.put(key, stock.get(key)+amount);
	}
	
	public synchronized boolean takeFromStock(Map<Integer, Integer> requiredResources) {		
		Boolean isAllAccountedFor = true;
		for(Entry<Integer, Integer> entry: requiredResources.entrySet()) {
			Integer availableStock = stock.get(entry.getKey());
			Integer requiredAmount = entry.getValue();
			if(availableStock<requiredAmount) {
				isAllAccountedFor = false;
				break;
			}
		}
		if(isAllAccountedFor) {
			for(Entry<Integer, Integer> entry: requiredResources.entrySet()) {
				stock.put(entry.getKey(), stock.get(entry.getKey())-entry.getValue());
			}
		}		
		return isAllAccountedFor;		
	}
	
	public Object[][] stockToObjects(){		
		int i = 0;
		Object[][] returnValue = new Object[stock.size()][2];
		for(Entry<Integer, Integer> entry: stock.entrySet()) {
			returnValue[i][0] = entry.getKey();
			returnValue[i][1] = entry.getValue();
			i++;
		}
		return returnValue;
	}
	
	public List<String> getFieldNames(){
		return Arrays.asList("name","stock"); 
	}

	public Integer stockExport(Integer key, Integer amount) {
		Integer availableStock = stock.get(key);
		Integer returnValue = 0;
		if(availableStock>=amount) {
			stock.put(key,availableStock-amount);
			returnValue =  amount;
		} else {
			stock.put(key,0);
			returnValue = availableStock;
		}
		return returnValue;
	}
	
}

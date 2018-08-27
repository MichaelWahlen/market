package main.domain;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class Network {

	private Map<Integer,List<Node>> networks = new HashMap<Integer, List<Node>>();
	private int lastAssignedKey = 0;
	
	public Network(int defaultNetwork) {
		networks.put(defaultNetwork, new ArrayList<Node>());		
	}

	public int getLargestNetwork(Set<Integer> keys) {
		int maxSize = 0;
		int largestKey = 0;
		int currentSize = 0;
		for(Integer integer: keys) {			
				currentSize = networks.get(integer).size();
				if(currentSize>maxSize) {
					maxSize = currentSize;
					largestKey = integer;
				}			
		}
		return largestKey;		
	}

	public void changeNetwork(int pickedNetTopNetWork, Set<Integer> topSet, List<Node> nodes) {
		List<Node> selectedNetwork = networks.get(pickedNetTopNetWork);
		for(Integer integer: topSet) {
			if(integer!=0 && integer!=pickedNetTopNetWork) {
				List<Node> selection = networks.get(integer);
				for(Node node:selection) {					
					node.setNetworkKey(pickedNetTopNetWork);					
					selectedNetwork.add(node);								
				}
				networks.remove(integer);
			} 
		}	
	}

	public List<Node> get(int pickedNetTopNetWork) {		
		return networks.get(pickedNetTopNetWork);
	}

	public List<Node> createNewNetwork(List<Node> nodes, int transportTypeKey) {
		lastAssignedKey++;
		List<Node> topNode = new ArrayList<Node>();
		for(Node node: nodes) {			
			node.setNetworkKey(lastAssignedKey);			
			topNode.add(node);					
		}		
		networks.put(lastAssignedKey, topNode);	
		return topNode;
	}
	
	
	
}

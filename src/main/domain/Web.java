package main.domain;

import java.util.ArrayList;
import java.util.HashMap;

import java.util.List;
import java.util.Map;

import main.domain.data.TransportFactory;


public class Web {
	
	private Node[][] nodes;
	private Map<Integer,List<Node>> topLevelNetworks = new HashMap<Integer, List<Node>>();
	private Map<Integer,List<Node>> detailNetworks = new HashMap<Integer, List<Node>>();
	private int lastAssignedKey = 0;
	private TransportFactory transportTypeFactory = TransportFactory.getInstance();
	
	
	public Web(Node[][] nodes) {
		this.nodes = nodes;
		for (int y = 0; y<nodes.length;y++){
        	for (int x = 0; x < nodes[0].length;x++){  
        		Node selectedNode = nodes[y][x];
        		int assignedTop = selectedNode.getTopNetworkKey();
        		int assignedDetail = selectedNode.getDetailNetworkKey();
        		if(assignedTop>lastAssignedKey) {
        			lastAssignedKey = assignedTop;
        		}
        		if(assignedDetail>lastAssignedKey) {
        			lastAssignedKey = assignedDetail;
        		}
        		List<Node> topLevelNodes =topLevelNetworks.get(assignedTop);
        		if(topLevelNodes == null) {
        			topLevelNodes = new ArrayList<Node>();        			
        		}
        		topLevelNodes.add(selectedNode);
        		List<Node> detailLevelNodes =detailNetworks.get(assignedDetail);
        		if(detailLevelNodes == null) {
        			detailLevelNodes = new ArrayList<Node>();        			
        		}
        		detailLevelNodes.add(selectedNode);        		
        	}
		}
	}
	
	public Node[][] getNodes() {
		return nodes;
	}

	public void setNodes(Node[][] nodes) {
		this.nodes = nodes;
	}

	public void setTransportType(int x, int y, String string) {
		Node selectedNode = nodes[x][y];
		int maX = nodes.length - 1;
		int maY = nodes[0].length - 1;
		Map<Integer,Integer> topSet = new HashMap<Integer,Integer>();		
		Map<Integer,Integer> detailSet = new HashMap<Integer,Integer>();
		if(selectedNode.getTransportType().getName().equals(string)) {
			
		} else if(selectedNode.getTransportType().getName().equals("Nothing")) {
			
			Node south = nodes[Math.min(maX, x+1)][y];
			Node east = nodes[x][Math.min(maY, y+1)];
			Node north = nodes[Math.max(0, x-1)][y]; 
			Node west = nodes[x][Math.max(0, y-1)];			
			
			if(north.getTransportType().getName().equals(string)&&!north.getTransportType().getName().equals("Nothing")) {
				topSet.put(north.getTopNetworkKey(),north.getTopNetworkKey());
				detailSet.put(north.getTopNetworkKey(),north.getTopNetworkKey());
			}
			if(east.getTransportType().getName().equals(string)&&!east.getTransportType().getName().equals("Nothing")) {
				topSet.put(east.getTopNetworkKey(),east.getTopNetworkKey());
				detailSet.put(east.getTopNetworkKey(),east.getTopNetworkKey());
			}
			if(south.getTransportType().getName().equals(string)&&!south.getTransportType().getName().equals("Nothing")) {
				topSet.put(south.getTopNetworkKey(),south.getTopNetworkKey());
				detailSet.put(south.getTopNetworkKey(),south.getTopNetworkKey());
			}
			if(west.getTransportType().getName().equals(string)&&!west.getTransportType().getName().equals("Nothing")) {
				topSet.put(west.getTopNetworkKey(),west.getTopNetworkKey());
				detailSet.put(west.getTopNetworkKey(),west.getTopNetworkKey());
			}
			
			if(topSet.size()==0) {				
				lastAssignedKey++;
				selectedNode.setDetailNetworkKey(lastAssignedKey);
				selectedNode.setTopNetworkKey(lastAssignedKey);
				List<Node> detailNode = new ArrayList<Node>();
				List<Node> topNode = new ArrayList<Node>();
				detailNode.add(selectedNode);
				topNode.add(selectedNode);
				detailNetworks.put(lastAssignedKey, detailNode);
				topLevelNetworks.put(lastAssignedKey,topNode);
			} else if(topSet.size()==1){
				int chosen = 0;
				for(Integer integ:topSet.keySet()) {
					chosen = integ;
				}
				selectedNode.setDetailNetworkKey(chosen);
				selectedNode.setTopNetworkKey(chosen);
				List<Node> detailNode = detailNetworks.get(chosen);
				List<Node> topNode = topLevelNetworks.get(chosen);
				detailNode.add(selectedNode);
				topNode.add(selectedNode);				
			} else if(topSet.size()>1) {
				int chosen = 0;
				int maxOfNodes = 0;
				int currentNrOfNodes = 0;
				for(Integer integ:topSet.keySet()) {
					currentNrOfNodes = topLevelNetworks.get(integ).size();
					if(currentNrOfNodes>maxOfNodes) {
						maxOfNodes=currentNrOfNodes;
						chosen = integ;
					}					
				}
				selectedNode.setDetailNetworkKey(chosen);
				selectedNode.setTopNetworkKey(chosen);
				List<Node> detailNode = detailNetworks.get(chosen);
				List<Node> topNode = topLevelNetworks.get(chosen);
				detailNode.add(selectedNode);
				topNode.add(selectedNode);			
				List<Node> chosenListTop = topLevelNetworks.get(chosen);
				List<Node> chosenListDetail = detailNetworks.get(chosen);
				for(Integer twelf:topSet.keySet()) {
					if(twelf!=chosen) {
						List<Node> tops = topLevelNetworks.get(twelf);						
						for(Node node:tops) {
							node.setTopNetworkKey(chosen);
							node.setDetailNetworkKey(chosen);
							chosenListTop.add(node);
							chosenListDetail.add(node);
						}
						topLevelNetworks.remove(twelf);
						detailNetworks.remove(twelf);
					}
				}				
			}			
			
			selectedNode.setTransportType(transportTypeFactory.getType(string));
		}
	}

	
	
}

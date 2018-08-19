package main.domain;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import main.domain.data.FactoryHolder;
import main.domain.data.GenericFactory;
import main.domain.data.Transport;



public class Web {
	
	private Node[][] nodes;
	private Map<Integer,List<Node>> topLevelNetworks = new HashMap<Integer, List<Node>>();
	private Map<Integer,List<Node>> detailNetworks = new HashMap<Integer, List<Node>>();
	private int lastAssignedKey = 0;
	private GenericFactory<Transport> transportTypeFactory = FactoryHolder.getInstance().getTransportInstance();
	private int maxX;
	private int maxY;
	
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
        		List<Node> topLevelNodes = topLevelNetworks.get(assignedTop);
        		if(topLevelNodes == null) {
        			topLevelNodes = new ArrayList<Node>();        			
        		}
        		topLevelNodes.add(selectedNode);
        		List<Node> detailLevelNodes = detailNetworks.get(assignedDetail);
        		if(detailLevelNodes == null) {
        			detailLevelNodes = new ArrayList<Node>();        			
        		}
        		detailLevelNodes.add(selectedNode);        		
        	}
		}
		maxX = nodes.length - 1;
		maxY = nodes[0].length - 1;
	}

	private void conditionallyAddNode(Set<Integer> detailSet, Set<Integer> topSet, int currentX, int currentY, int string) {
		Node abc = this.nodes[currentX][currentY];
		if(abc.getTransportType().getCode()==string) {
			detailSet.add(abc.getDetailNetworkKey());
			topSet.add(abc.getTopNetworkKey());
		}		
	}
	
	public void setTransportType(List<Node> nodes, int transportTypeKey) {		
		Set<Integer> detailSet = new HashSet<Integer>();
		Set<Integer> topSet = new HashSet<Integer>();
		for(Node node:nodes) {
			int currentX = node.getX();
			int currentY = node.getY();
			Node abc = this.nodes[currentX][currentY];
			detailSet.add(abc.getDetailNetworkKey());
			topSet.add(abc.getTopNetworkKey());
			if(currentX<maxX) {
				conditionallyAddNode(detailSet,topSet,currentX+1,currentY,transportTypeKey);				
			}
			if(currentX>0) {
				conditionallyAddNode(detailSet,topSet,currentX-1,currentY,transportTypeKey);	
			}
			if(currentY<maxY) {
				conditionallyAddNode(detailSet,topSet,currentX,currentY+1,transportTypeKey);	
			}
			if(currentY>0) {
				conditionallyAddNode(detailSet,topSet,currentX,currentY-1,transportTypeKey);	
			}
		}
		//----------------------------		
		if(detailSet.size()==1 && detailSet.contains(0)) {
			addBasic(nodes,transportTypeKey);
			return;
		}	
		
		
		int pickedNetTopNetWork = getLargestKey(topSet,topLevelNetworks);	
		int pickedDetailNetWork = getLargestKey(detailSet,detailNetworks);
		//--------------------------------		
		setNetwork(pickedNetTopNetWork,topSet,topLevelNetworks,true,nodes);		
		setNetwork(pickedDetailNetWork,detailSet,detailNetworks,false,nodes);	
		//--------------------------------
				
		List<Node> selection = detailNetworks.get(pickedDetailNetWork);
		List<Node> selection2 = topLevelNetworks.get(pickedNetTopNetWork);
		for(Node node: nodes) {		
			node.setTransportType(transportTypeFactory.getType(transportTypeKey));
			node.setDetailNetworkKey(pickedDetailNetWork);
			node.setTopNetworkKey(pickedNetTopNetWork);
			selection.add(node);
			selection2.add(node);
		}	
		
	}

	private void addBasic(List<Node> nodes, int transportTypeKey) {
		lastAssignedKey++;
		List<Node> detailNode = new ArrayList<Node>();
		List<Node> topNode = new ArrayList<Node>();
		for(Node node: nodes) {
			node.setDetailNetworkKey(lastAssignedKey);
			node.setTopNetworkKey(lastAssignedKey);
			detailNode.add(node);
			topNode.add(node);
			node.setTransportType(transportTypeFactory.getType(transportTypeKey));
		}			
		detailNetworks.put(lastAssignedKey, detailNode);
		topLevelNetworks.put(lastAssignedKey,topNode);		
	}

	private void setNetwork(int pickedNetwork, Set<Integer> keys, Map<Integer, List<Node>> networks, boolean isTop, List<Node> nodes) {		
		List<Node> selectedNetwork = networks.get(pickedNetwork);
		for(Integer integer: keys) {
			if(integer!=0 && integer!=pickedNetwork) {
				List<Node> selection = networks.get(integer);
				for(Node node:selection) {					
					if(isTop) {
						node.setTopNetworkKey(pickedNetwork);
					} else {
						node.setDetailNetworkKey(pickedNetwork);
					}
					selectedNetwork.add(node);								
				}
				networks.remove(integer);
			} 
		}		
	}

	private int getLargestKey(Set<Integer> keys, Map<Integer, List<Node>> networks) {
		int maxSize = 0;
		int largestKey = 0;
		for(Integer integer: keys) {
			if(integer!=0) {
				int currentSize = networks.get(integer).size();
				if(currentSize>maxSize) {
					maxSize = currentSize;
					largestKey = integer;
				}
			}
		}
		return largestKey;		
	}


	
	
}

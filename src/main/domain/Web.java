package main.domain;

import java.awt.Point;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import main.domain.data.Transport;
import main.domain.map.LocalMap;
import main.domain.map.Manhattan;
import main.domain.map.NodeCompass;
import main.domain.map.Router;
import main.gui.TableRepresentation;

public class Web {
	
	private Node[][] nodes;
	private Router router;
	private Map<Integer,List<Node>> topLevelNetworks = new HashMap<Integer, List<Node>>();
	private Map<Integer,List<Node>> detailNetworks = new HashMap<Integer, List<Node>>();	
	private int lastAssignedKey = 0;
	private int maxX;
	private int maxY;
	private LocalMap map = new LocalMap();	
	
	// doesnt work with different types of transport yet. Needs to be fixed. Will currently merge networks without switches purely based on the fact that they are connected. But RR and R should not create a single network.
	
	public Web(int rows, int columns) {		
		nodes = map.createNodeMap(rows, columns);
		router = new Router(new NodeCompass(nodes, new Manhattan()));
		maxX = rows - 1;
		maxY = columns - 1;
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
		
	}

	private void conditionallyAddNode(Set<Integer> detailSet, Set<Integer> topSet, int currentX, int currentY, int transportKey) {
		Node abc = this.nodes[currentX][currentY];
		Map<Integer,Transport> transports = abc.getTransportType();
		boolean hasTransportType = false;
		for(Transport transport: transports.values()) {
			if(transport.getCode() == transportKey) {
				hasTransportType = true;
				break;
			}
		}
		if(hasTransportType) {
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
			map.setTransportType(node.getX(), node.getY(), transportTypeKey);
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
			map.setTransportType(node.getX(), node.getY(), transportTypeKey);			
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

	public Node[][] getNodes() {		
		return nodes;
	}

	public StockPile getStockPile() {		
		return map.getStockPile()	;
	}

	public void switchTile(int fromX, int fromY, int string) {
		map.switchTile(fromX, fromY, string);		
	}

	public void setSwitch(int x, int y, int switchKey) {
		map.setSwitch(x, y, switchKey);		
	}

	public Map<String, TableRepresentation> getAllTableReps() {
		return map.getAllTableReps();
	}

	public void buildTransportLine(int fromX, int fromY, int string, int toX, int toY) {
		if(!nodes[toX][toY].isFull() && !nodes[fromX][fromY].isFull()) {
			List<Point> points = router.getRoute(fromX,fromY,toX,toY,0);	
			if(points!=null) {
				List<Node> nodes  = new ArrayList<Node>();				
				for(Point point:points) {
					nodes.add(this.nodes[point.x][point.y]);
				}
				setTransportType(nodes, string);
			}			
		}		
	}




	
	
}

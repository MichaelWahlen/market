package main.domain;

import java.awt.Point;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import main.domain.map.Compass;
import main.domain.map.LocalMap;
import main.domain.map.Manhattan;
import main.domain.map.NodeCompass;
import main.domain.map.Router;
import main.gui.TableRepresentation;

public class Web {
	
	private Node[][] nodes;
	private Router router;
	private Map<Integer,List<Node>> topLevelNetworks = new HashMap<Integer, List<Node>>();
	private int lastAssignedKey = 0;
	private LocalMap map = new LocalMap();
	private Compass compass;
	
	// doesnt work with different types of transport yet. Needs to be fixed. Will currently merge networks without switches purely based on the fact that they are connected. But RR and R should not create a single network.
	// also doesnt work with single cells being next to a different network
	
	public Web(int rows, int columns) {		
		nodes = map.createNodeMap(rows, columns);
		compass = new NodeCompass(nodes, new Manhattan());
		router = new Router(compass);		
	}

	private void addNetwork(Set<Integer> detailSet, Set<Integer> topSet, int x, int y) {			
		Node currentNode = nodes[x][y];
		topSet.add(currentNode.getTopNetworkKey());	
	}	
	
	private boolean containsTransport(int x, int y, int transportTypeKey) {
		return map.tileContainsTransport(x,y,transportTypeKey);
	}
	
	public void setTransportType(List<Node> nodes, int transportTypeKey) {		
		Set<Integer> detailSet = new HashSet<Integer>();
		Set<Integer> topSet = new HashSet<Integer>();
		for(Node node:nodes) {
			int currentX = node.getX();
			int currentY = node.getY();
			detailSet.add(map.getNetworkKey(currentX, currentY));
			topSet.add(map.getNetworkKey(currentX, currentY));
			if(currentX<compass.getXBoundary()) {
				if(containsTransport(currentX+1,currentY,transportTypeKey))	{
					addNetwork(detailSet,topSet,currentX+1,currentY);
				}
			}
			if(currentX>0) {
				if(containsTransport(currentX-1,currentY,transportTypeKey))	{
					addNetwork(detailSet,topSet,currentX-1,currentY);	
				}
			}
			if(currentY<compass.getYBoundary()) {
				if(containsTransport(currentX,currentY+1,transportTypeKey))	{
					addNetwork(detailSet,topSet,currentX,currentY+1);
				}
			}			
			if(currentY>0) {
				if(containsTransport(currentX,currentY-1,transportTypeKey))	{
					addNetwork(detailSet,topSet,currentX,currentY-1);
				}
			}
		}

		if(detailSet.size()==1 && detailSet.contains(0)) {
			addBasic(nodes,transportTypeKey);
			return;
		}		
		int pickedNetTopNetWork = getLargestKey(topSet,topLevelNetworks);	
		
		setNetwork(pickedNetTopNetWork,topSet,topLevelNetworks,true,nodes);		
		
		List<Node> selection2 = topLevelNetworks.get(pickedNetTopNetWork);
		for(Node node: nodes) {		
			map.setTransportType(node.getX(), node.getY(), transportTypeKey);		
			node.setNetworkKey(pickedNetTopNetWork);			
			selection2.add(node);
		}		
	}


	private void addBasic(List<Node> nodes, int transportTypeKey) {
		lastAssignedKey++;
		List<Node> detailNode = new ArrayList<Node>();
		List<Node> topNode = new ArrayList<Node>();
		for(Node node: nodes) {			
			node.setNetworkKey(lastAssignedKey);
			detailNode.add(node);
			topNode.add(node);
			map.setTransportType(node.getX(), node.getY(), transportTypeKey);			
		}			
		
		topLevelNetworks.put(lastAssignedKey,topNode);		
	}

	private void setNetwork(int pickedNetwork, Set<Integer> keys, Map<Integer, List<Node>> networks, boolean isTop, List<Node> nodes) {		
		List<Node> selectedNetwork = networks.get(pickedNetwork);
		for(Integer integer: keys) {
			if(integer!=0 && integer!=pickedNetwork) {
				List<Node> selection = networks.get(integer);
				for(Node node:selection) {					
					if(isTop) {
						node.setNetworkKey(pickedNetwork);
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

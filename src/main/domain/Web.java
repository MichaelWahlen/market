package main.domain;

import java.awt.Point;
import java.util.ArrayList;
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
	//private int lastAssignedKey = 0;
	private LocalMap map = new LocalMap();
	private Compass compass;
	private Network network;
	
	// doesnt work with different types of transport yet. Needs to be fixed. Will currently merge networks without switches purely based on the fact that they are connected. But RR and R should not create a single network.
	// also doesnt work with single cells being next to a different network
	
	public Web(int rows, int columns) {		
		nodes = map.createNodeMap(rows, columns);
		compass = new NodeCompass(nodes, new Manhattan());
		router = new Router(compass);	
		network = new Network(0,map);
	}
	
	private int getNetworkKey(int x, int y) {
		return map.getNetworkKey(x, y);
	}

	private void addNode(Set<Integer> topSet, int x, int y, int transportTypeKey) {			
		if(containsTransport(x,y,transportTypeKey)) {			
			topSet.add(getNetworkKey(x, y));	
		}
	}	
	
	private boolean containsTransport(int x, int y, int transportTypeKey) {
		return map.tileContainsTransport(x,y,transportTypeKey);
	}
	
	private void setTransportType(List<Node> nodes, int transportTypeKey) {		
		Set<Integer> topSet = new HashSet<Integer>();
		for(Node node:nodes) {
			int currentX = node.getX();
			int currentY = node.getY();			
			topSet.add(getNetworkKey(currentX, currentY));
			if(currentX<compass.getXBoundary()) {				
				addNode(topSet,currentX+1,currentY,transportTypeKey);
			}
			if(currentX>0) {				
				addNode(topSet,currentX-1,currentY,transportTypeKey);				
			}
			if(currentY<compass.getYBoundary()) {				
				addNode(topSet,currentX,currentY+1,transportTypeKey);				
			}			
			if(currentY>0) {				
				addNode(topSet,currentX,currentY-1,transportTypeKey);				
			}
		}
		if(topSet.size()==1 && topSet.contains(0)) {
			network.createNewNetwork(nodes, transportTypeKey);
			return;
		}		
		int pickedNetTopNetWork = network.getLargestNetwork(topSet);		
		network.changeNetwork(pickedNetTopNetWork,topSet,nodes);
		List<Node> selection2 = network.get(pickedNetTopNetWork);
		for(Node node: nodes) {		
			map.setTransportType(node.getX(), node.getY(), transportTypeKey);		
			node.setNetworkKey(pickedNetTopNetWork);			
			selection2.add(node);
		}		
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

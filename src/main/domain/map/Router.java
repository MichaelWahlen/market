package main.domain.map;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Set;

import main.domain.Node;

public class Router {
	
	private Node[][] nodes;
	private int maxX;
	private int maxY;
	private int targetX;
	private int targetY;
	private Set<Node> nodesToBeReset;
	private PriorityQueue<Node> openQueue;
	
	public Router(Node[][] nodes) {
		this.nodes = nodes;
		maxX = nodes.length - 1;
		maxY = nodes[0].length - 1;		
	}
	
	public List<Node> getRoute(int fromX, int fromY, int toX, int toY, int detailNetwork){
		
		if(fromX==toX&&fromY==toY) {
			List<Node> nodes = new ArrayList<Node>();
			nodes.add(this.nodes[fromX][fromY]);
			return nodes;			
		}		
		
		nodesToBeReset = new HashSet<Node>();
		List<Node> returnList = null;
		targetX = toX;
		targetY = toY;
		NodeComperator myComp = new NodeComperator();
		openQueue = new PriorityQueue<>(1,myComp);
		Node initialNode = nodes[fromX][fromY];
		initialNode.setTravelledDistance(0);		
		nodesToBeReset.add(initialNode);
		Node nextNode = initialNode;
		boolean targetNotFound = true;		
		while(targetNotFound) {		
			List<Node> neighbours = getNeighbours(nextNode.getX(),nextNode.getY());			
			for(Node node: neighbours) {			
				openQueue.add(node);
				if(node.getX()==toX&&node.getY()==toY) {
					targetNotFound = false;
					returnList = generateRoute(node.getX(),node.getY());
				}
				nodesToBeReset.add(node);
			}
			if(openQueue.isEmpty()) {
				break;
			}
			nextNode = openQueue.remove();			
		}
		resetNodes(nodesToBeReset);	
		return returnList;
	}
	
	private List<Node> generateRoute(int x, int y) {
		List<Node> returnList = new ArrayList<Node>();
		addNode(returnList,x,y);
		Collections.reverse(returnList);
		return returnList;
	}

	private void addNode(List<Node> returnList, int x, int y) {
		returnList.add(nodes[x][y]);
		Node parent = nodes[x][y].getParentNode();
		if(parent!=null) {
			addNode(returnList,parent.getX(),parent.getY());
		}		
	}

	private void resetNodes(Set<Node> nodesToBeReset) {
		for(Node node:nodesToBeReset) {
			node.setTravelledDistance(Integer.MAX_VALUE);
			node.setHeuristicDistance(Integer.MAX_VALUE);
			node.setParentNode(null);
		}		
	}

	private List<Node> getNeighbours(int fromX, int fromY) {
		List<Node> tempHolder = new ArrayList<Node>();
		int travelledDistance = nodes[fromX][fromY].getTravelledDistance();
		if(fromX>0) {			
			tempHolder.add(getOpenNode(fromX-1,fromY,travelledDistance));
		};
		if(fromY>0) {
			tempHolder.add(getOpenNode(fromX,fromY-1,travelledDistance));
		};
		if(fromX<maxX) {
			tempHolder.add(getOpenNode(fromX+1,fromY,travelledDistance));
		};
		if(fromY<maxY) {
			tempHolder.add(getOpenNode(fromX,fromY+1,travelledDistance));
		};	
		List<Node> realReturn = new ArrayList<Node>();
		for(Node node: tempHolder) {
			if (node!=null) {
				node.setParentNode(nodes[fromX][fromY]);
				node.setHeuristicDistance(getHeuristic(node.getX(),node.getY()));
				realReturn.add(node);
			}
		}		
		return realReturn;
	}
	
	private Node getOpenNode(int toX, int toY, int travelledDistance) {		
		Node nodeToAdd = nodes[toX][toY];
		int travelWeightCurrentNode = nodeToAdd.getWeight();
		if(nodeToAdd.isPassable() && nodeToAdd.getTransportType().getCode()==99&&nodeToAdd.getTravelledDistance()>travelledDistance + travelWeightCurrentNode) {
			nodeToAdd.setTravelledDistance(travelledDistance + travelWeightCurrentNode);
			return nodeToAdd;
		}
		return null;
	}
	
	private int getHeuristic(int toX, int toY) {
		return Math.abs(toX-targetX)+2*Math.abs(toY-targetY);
	}
	
	
}

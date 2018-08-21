package main.domain.map;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Set;
import main.domain.Node;

/**
 * Class used to get shortest route from point A to point B
 */
public class Router {
	
	private Node[][] nodes;
	private int maxX;
	private int maxY;
	private NodeComperator nodeComperator = new NodeComperator();
	private Heuristic manhattan = new Manhattan();
	
	/**
	 * @param nodes node array of arrays, nodes need to have a lot of information to allow route planning. Relative weight of the element, travelled distance, projected distances, etc, etc, this needs to be changed :(
	 */
	
	public Router(Node[][] nodes) {
		this.nodes = nodes;
		maxX = nodes.length - 1;
		maxY = nodes[0].length - 1;		
	}
	
	public List<Node> getRoute(int fromX, int fromY, int toX, int toY, int detailNetwork){		
		List<Node>  returnList = null;
		Node currentNode = nodes[fromX][fromY];	
		Set<Node> nodesToBeReset = new HashSet<Node>();
		PriorityQueue<Node> openQueue = new PriorityQueue<>(1,nodeComperator);
		manhattan.setTarget(toX, toY);	
		if(fromX==toX && fromY==toY) {
			List<Node> nodes = new ArrayList<Node>();
			nodes.add(currentNode);
			return nodes;			
		}				
		currentNode.setTravelledDistance(0);			
		nodesToBeReset.add(currentNode);
		boolean targetFound = false;
		while(!targetFound) {		
			List<Node> neighbours = getNeighbours(currentNode);	
			nodesToBeReset.addAll(neighbours);
			for(Node node: neighbours) {								
				if(node.getX()==toX&&node.getY()==toY) {					
					targetFound= true;
					returnList = generateRoute(node);					
				}
				openQueue.add(node);				
			}
			if(openQueue.isEmpty()) {
				break;
			}
			currentNode = openQueue.remove();			
		}
		resetNodes(nodesToBeReset);	
		return returnList;
	}
	
	private List<Node> generateRoute(Node node) {
		List<Node> returnList = new ArrayList<Node>();
		addNode(returnList,node);
		Collections.reverse(returnList);
		return returnList;
	}

	private void addNode(List<Node> returnList,Node node) {
		returnList.add(node);
		Node parent = node.getParentNode();
		if(parent!=null) {
			addNode(returnList,parent);
		}		
	}

	private void resetNodes(Set<Node> nodesToBeReset) {
		for(Node node:nodesToBeReset) {
			node.setTravelledDistance(Integer.MAX_VALUE);
			node.setHeuristicDistance(Integer.MAX_VALUE);
			node.setParentNode(null);
		}	
		nodesToBeReset.clear();
	}

	private List<Node> getNeighbours(Node originalNode) {
		List<Node> neightbourNodes = new ArrayList<Node>();
		int x = originalNode.getX();
		int y = originalNode.getY();		
		if(x>0) {			
			addOpenNode(neightbourNodes,nodes[x-1][y],originalNode);				
		};
		if(y>0) {			
			addOpenNode(neightbourNodes,nodes[x][y-1],originalNode);			
		};
		if(x<maxX) {			
			addOpenNode(neightbourNodes,nodes[x+1][y],originalNode);			
		};
		if(y<maxY) {			
			addOpenNode(neightbourNodes,nodes[x][y+1],originalNode);			
		};			
		return neightbourNodes;
	}
	
	private void addOpenNode(List<Node> list,Node node, Node parent) {		
		int travelDistanceToThisNode = node.getWeight() + parent.getTravelledDistance();
		if(node!=null && node.isPassable() && node.getTransportType().getCode()==99 && node.getTravelledDistance()>travelDistanceToThisNode) {
			node.setTravelledDistance(travelDistanceToThisNode);
			node.setHeuristicDistance(manhattan.getHeuristicValue(node.getX(), node.getY()));
			node.setParentNode(parent);
			list.add(node);			
		}		
	}
	

	
	
}

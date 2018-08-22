package main.domain.map;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
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
	private Heuristic manhattan = new Manhattan();
	private InnerNode[][] travelMap;	
	
	private class InnerNode {
		
		private int x;
		private int y;
		private int travelledDistance;
		private int estimatedDistanceToTarget;
		private InnerNode parentNode = null;
		
		public InnerNode() {
			estimatedDistanceToTarget = Integer.MAX_VALUE;
			travelledDistance = Integer.MAX_VALUE;
		}
		
		public void setEstimatedDistanceToTarget(int estimatedDistanceToTarget) {
			this.estimatedDistanceToTarget = estimatedDistanceToTarget;
		}
		public int getX() {
			return x;
		}
		public void setX(int x) {
			this.x = x;
		}
		public int getY() {
			return y;
		}
		public void setY(int y) {
			this.y = y;
		}
		public int getTravelledDistance() {
			return travelledDistance;
		}
		public void setTravelledDistance(int travelledDistance) {
			this.travelledDistance = travelledDistance;
		}
		
		public int getTotalDistance() {
			return travelledDistance+estimatedDistanceToTarget;
		}

		public void setParentNode(InnerNode currentInnerNode) {
			this.parentNode = currentInnerNode;
		}

		public InnerNode getParentNode() {
			return parentNode;
		}
		
	}
	
	private	class InnerComperator implements Comparator<InnerNode>{		
		@Override
	    public int compare(InnerNode o1, InnerNode o2) {			
			return Integer.compare(o1.getTotalDistance(), o2.getTotalDistance());
	    }
	}

	
	/**
	 * Create a inner class,give it heuristic distance/travelled distance etc etc, move all creation thereof here... new compartor etc also neccesary
	 * @param nodes node array of arrays, nodes need to have a lot of information to allow route planning. Relative weight of the element, travelled distance, projected distances, etc, etc, this needs to be changed :(
	 */
	
	public Router(Node[][] nodes) {
		this.nodes = nodes;
		maxX = nodes.length - 1;
		maxY = nodes[0].length - 1;	
		travelMap = new InnerNode[maxX+1][maxY+1];
		for(int i = 0; i <= maxX;i++) {
			for(int j = 0 ; j <= maxY;j++) {
				InnerNode innerNode = new InnerNode();
				innerNode.setX(i);
				innerNode.setY(j);
				travelMap[i][j] = innerNode;				
			}
		}		
	}
	
	public List<InnerNode> getInnerRoute(int fromX, int fromY, int toX, int toY, int detailNetwork){		
		List<InnerNode>  returnList = null;
		InnerNode currentInnerNode = travelMap[fromX][fromY];
		Set<InnerNode> innerNodesToReset = new HashSet<InnerNode>();
		PriorityQueue<InnerNode> openInnerQueue = new PriorityQueue<>(1,new InnerComperator());
		manhattan.setTarget(toX, toY);	
		if(fromX==toX && fromY==toY) {
			List<InnerNode> innerNodes = new ArrayList<InnerNode>();
			innerNodes.add(currentInnerNode);
			return innerNodes;			
		}				
		currentInnerNode.setTravelledDistance(0);			
		innerNodesToReset.add(currentInnerNode);
		boolean targetFound = false;
		while(!targetFound) {		
			List<InnerNode> neighbours = getNeighbours(currentInnerNode);	
			innerNodesToReset.addAll(neighbours);
			for(InnerNode node: neighbours) {								
				if(node.getX()==toX&&node.getY()==toY) {					
					targetFound= true;
					returnList = generateRoute(node);					
				}
				openInnerQueue.add(node);				
			}
			if(openInnerQueue.isEmpty()) {
				break;
			}
			currentInnerNode = openInnerQueue.remove();			
		}
		resetInnerNodes(innerNodesToReset);	
		return returnList;
	}
	
	
	private void resetInnerNodes(Set<InnerNode> innerNodesToReset) {
		for(InnerNode innerNode:innerNodesToReset) {
			innerNode.setTravelledDistance(Integer.MAX_VALUE);
			innerNode.setEstimatedDistanceToTarget(Integer.MAX_VALUE);
			innerNode.setParentNode(null);
		}	
		innerNodesToReset.clear();		
	}

	private List<InnerNode> generateRoute(InnerNode innerNode) {		
		List<InnerNode> returnList = new ArrayList<InnerNode>();
		addInnerNode(returnList,innerNode);
		Collections.reverse(returnList);
		return returnList;
	}

	private void addInnerNode(List<InnerNode> returnList, InnerNode innerNode) {
		returnList.add(innerNode);
		InnerNode parent = innerNode.getParentNode();
		if(parent!=null) {
			addInnerNode(returnList,parent);
		}		
	}

	private List<InnerNode> getNeighbours(InnerNode currentInnerNode) {
		List<InnerNode> neightbourNodes = new ArrayList<InnerNode>();
		int x = currentInnerNode.getX();
		int y = currentInnerNode.getY();		
		if(x>0) {			
			addInnerNode(neightbourNodes,nodes[x-1][y],currentInnerNode,travelMap[x-1][y]);				
		};
		if(y>0) {			
			addInnerNode(neightbourNodes,nodes[x][y-1],currentInnerNode,travelMap[x][y-1]);			
		};
		if(x<maxX) {			
			addInnerNode(neightbourNodes,nodes[x+1][y],currentInnerNode,travelMap[x+1][y]);			
		};
		if(y<maxY) {			
			addInnerNode(neightbourNodes,nodes[x][y+1],currentInnerNode,travelMap[x][y+1]);			
		};			
		return neightbourNodes;
	}	
	
	private void addInnerNode(List<InnerNode> currentNeighbours, Node potentialNeighbourNode, InnerNode currentInnerNode, InnerNode potentialInnerNeighbour) {
		int travelDistanceToThisNode = potentialNeighbourNode.getWeight() + currentInnerNode.getTravelledDistance();
		if(potentialNeighbourNode.isPassable() && potentialInnerNeighbour.getTravelledDistance()>travelDistanceToThisNode) {
			potentialInnerNeighbour.setTravelledDistance(travelDistanceToThisNode);
			potentialInnerNeighbour.setEstimatedDistanceToTarget(manhattan.getHeuristicValue(potentialNeighbourNode.getX(), potentialNeighbourNode.getY()));
			potentialInnerNeighbour.setParentNode(currentInnerNode);
			currentNeighbours.add(potentialInnerNeighbour);			
		}		
	}

	//------------ Old
	public List<Node> getRoute(int fromX, int fromY, int toX, int toY, int detailNetwork){		
		List<InnerNode> test  = getInnerRoute(fromX,fromY,toX,toY,detailNetwork);
		List<Node> holderTest = new ArrayList<Node>();
		if(test!=null) {
			for(InnerNode innerNode:test) {
				holderTest.add(nodes[innerNode.getX()][innerNode.getY()]);
			}
		}		
		return holderTest;
	}
	


	
	
}

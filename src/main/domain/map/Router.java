package main.domain.map;

import java.awt.Point;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Set;

/**
 * Class used to get shortest route from point A to point B
 */
public class Router {
	
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

	private InnerNode[][] travelMap;	
	private Compass compass;
	
	/**
	 * @param compass a compass that gives information about the object that needs to be travelled. Minimal information is required: x bound, y bound, whether a move is possible and the cost of said move. This is all pushed to the compass.
	 */
	
	public Router(Compass compass) {		
		this.compass = compass;		
		this.travelMap = getTravelMap(compass.getXBoundary(),compass.getYBoundary());
	}	
	
	public List<Point> getRoute(int fromX, int fromY, int toX, int toY, int topNetWork){		
		List<InnerNode> test  = getInnerRoute(fromX,fromY,toX,toY,topNetWork);
		List<Point> holderTest = new ArrayList<Point>();
		if(test!=null) {
			for(InnerNode innerNode:test) {
				holderTest.add(new Point(innerNode.getX(),innerNode.getY()));
			}
		}		
		return holderTest;
	}	
	
	private InnerNode[][] getTravelMap(int x, int y){
		InnerNode[][] returnTravel = new InnerNode[x+1][y+1];
		for(int i = 0; i <= x;i++) {
			for(int j = 0 ; j <= y;j++) {
				InnerNode innerNode = new InnerNode();
				innerNode.setX(i);
				innerNode.setY(j);
				returnTravel[i][j] = innerNode;				
			}
		}
		return returnTravel;
	}
	
	private List<InnerNode> getInnerRoute(int fromX, int fromY, int toX, int toY, int detailNetwork){		
		List<InnerNode>  returnList = null;
		InnerNode currentInnerNode = getInnerNode(fromX, fromY);
		Set<InnerNode> innerNodesToReset = new HashSet<InnerNode>();
		PriorityQueue<InnerNode> openInnerQueue = new PriorityQueue<>(1,new InnerComperator());
		setDestination(toX, toY);	
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
			for(InnerNode innerNode: neighbours) {								
				if(innerNode.getX()==toX&&innerNode.getY()==toY) {					
					targetFound= true;
					returnList = generateRoute(innerNode);					
				}
				openInnerQueue.add(innerNode);				
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
			addInnerNode(neightbourNodes,x-1,y,currentInnerNode);				
		};
		if(y>0) {			
			addInnerNode(neightbourNodes,x,y-1,currentInnerNode);			
		};
		if(x<getXBoundary()) {			
			addInnerNode(neightbourNodes,x+1,y,currentInnerNode);			
		};
		if(y<getYBoundary()) {			
			addInnerNode(neightbourNodes,x,y+1,currentInnerNode);			
		};			
		return neightbourNodes;
	}
	
	private void addInnerNode(List<InnerNode> neighbours, int x, int y, InnerNode startNode) {
		int travelDistanceToThisNode = getTraverseWeight(x,y) + startNode.getTravelledDistance();
		InnerNode neighbour = getInnerNode(x,y);
		if(isMovePossible(x, y) && neighbour.getTravelledDistance()>travelDistanceToThisNode) {
			neighbour.setTravelledDistance(travelDistanceToThisNode);
			neighbour.setEstimatedDistanceToTarget(getHeuristicEstimation(x, y));
			neighbour.setParentNode(startNode);
			neighbours.add(neighbour);			
		}		
	}
	
	private int getHeuristicEstimation(int x, int y) {		
		return compass.getHeuristicEstimation(x, y);
	}

	private boolean isMovePossible(int x, int y) {
		return compass.isMovePossible(x, y);
	}

	private int getTraverseWeight(int x,int y) {
		return compass.getTraverseWeight(x, y);
	}
	
	private void setDestination(int toX, int toY) {
		compass.setDestination(toX, toY);		
	}
	
	private int getYBoundary() {		
		return compass.getYBoundary();
	}

	private int getXBoundary() {		
		return compass.getXBoundary();
	}
	
	private InnerNode getInnerNode(int x, int y) {
		return travelMap[x][y];
	}


	
	
}

package main.domain.map;

import main.domain.Node;

public class NodeCompass implements Compass {
	
	private Node[][] nodes;
	private Heuristic heuristic;
	private int allowedNetwork = 0;
	private boolean requiresNetwork = false;
	
	public NodeCompass(Node[][] nodes, Heuristic heuristic) {
		this.nodes = nodes;
		this.heuristic = heuristic;
	}
	
	@Override
	public void setDestination(int destinationX, int destinationY) {
		heuristic.setDestination(destinationX, destinationY);
	}

	@Override
	public boolean isMovePossible(int x, int y) {		
		boolean isEmpty = !nodes[x][y].isFull();
		boolean isWithinNetwork = (!requiresNetwork||nodes[x][y].getTopNetworkKey()==allowedNetwork);		
		return (isEmpty && isWithinNetwork);
	}

	@Override
	public int getTraverseWeight(int x, int y) {		
		return nodes[x][y].getWeight();
	}

	@Override
	public int getXBoundary() {		
		return nodes.length-1;
	}

	@Override
	public int getYBoundary() {		
		return nodes[0].length -1;
	}

	@Override
	public int getHeuristicEstimation(int targetX, int targetY) {
		return heuristic.getHeuristicValue(targetX, targetY);
	}

	@Override
	public void setAllowedNetwork(int networkKey) {
		this.allowedNetwork = networkKey;		
	}

	@Override
	public void requiresNetwork(boolean requiresNetwork) {		
		this.requiresNetwork = requiresNetwork;
	}
}

package main.domain.map;

public interface Compass {
	public boolean isMovePossible(int x, int y);
	public int getTraverseWeight(int x, int y);
	public int getXBoundary();
	public int getYBoundary();
	public int getHeuristicEstimation(int targetX, int targetY);
	void setDestination(int destinationX, int destinationY);
	public void setAllowedNetwork(int networkKey);
	public void requiresNetwork(boolean requiresNetwork);
}

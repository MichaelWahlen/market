package main.domain.map;

public class Manhattan implements Heuristic {
	
	private int targetX;
	private int targetY;
	
	@Override
	public void setDestination(int x, int y) {
		targetX = x;
		targetY = y;
	}

	@Override
	public int getHeuristicValue(int x, int y) {		
		return Math.abs(x-targetX)+2*Math.abs(y-targetY);
	}
	
}

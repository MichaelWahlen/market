package main.domain.map;

public interface Heuristic {
	public void setTarget(int x, int y);
	public int getHeuristicValue(int x, int y);
}

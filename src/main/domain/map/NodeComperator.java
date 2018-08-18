package main.domain.map;

import java.util.Comparator;

import main.domain.Node;

public 	class NodeComperator implements Comparator<Node>{
	
	@Override
    public int compare(Node o1, Node o2) {			
		return Integer.compare(o1.getTotalDistanceToGoal(), o2.getTotalDistanceToGoal());
    }
}

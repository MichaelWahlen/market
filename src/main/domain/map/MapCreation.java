package main.domain.map;

import java.util.Random;

import main.domain.Node;
import main.domain.data.ResourceFactory;
import main.domain.data.TileFactory;
import main.domain.data.TransportFactory;

public class MapCreation {	

	public static Node[][] createSurface(int rows, int columns){
		TileFactory tileFactory = TileFactory.getInstance();
		ResourceFactory productFactory = ResourceFactory.getInstance();
		TransportFactory transportFactory = TransportFactory.getInstance();
		Node[][] nodes = new Node[rows][columns];
		Random rand = new Random();
		for(int i = 0;i<nodes.length;i++) {
			for(int j = 0 ; j < nodes[0].length;j++) {
				int pick = rand.nextInt(10);
				Node node = new Node();	
				node.setX(i);
				node.setY(j);
				node.setTravelledDistance(Integer.MAX_VALUE);
				node.setHeuristicDistance(Integer.MAX_VALUE);
				if(pick<=3) {
					node.setAboveGroundResource(productFactory.getType("Cow"));
					node.setTile(tileFactory.getType("Grass"));					
				} else if(pick<=5)	{
					node.setAboveGroundResource(productFactory.getType("Corn"));
					node.setTile(tileFactory.getType("Sand"));				
				} else if(pick<=7)	{
					node.setAboveGroundResource(productFactory.getType("Wood"));
					node.setTile(tileFactory.getType("Forest"));					
				} else if(pick==8) {
					node.setAboveGroundResource(productFactory.getType("Corn"));
					node.setTile(tileFactory.getType("Stone"));					
				} else if(pick==9) {
					node.setAboveGroundResource(productFactory.getType("Corn"));
					node.setTile(tileFactory.getType("RoadBlock"));					
				}				
				node.setTransportType(transportFactory.getType(99));
				nodes[i][j] = node ;				
			}
		}
		return nodes;
	}
	
}

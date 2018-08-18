package main.domain.map;

import java.util.Random;

import main.domain.Node;
import main.domain.data.ResourceFactory;
import main.domain.data.TileFactory;

public class MapCreation {	

	public static Node[][] createSurface(int rows, int columns){
		TileFactory tileFactory = TileFactory.getInstance();
		ResourceFactory productFactory = ResourceFactory.getInstance();
		Node[][] nodes = new Node[rows][columns];
		Random rand = new Random();
		for(int i = 0;i<nodes.length;i++) {
			for(int j = 0 ; j < nodes[0].length;j++) {
				int pick = rand.nextInt(10);
				Node tile = new Node();	
				tile.setX(i);
				tile.setY(j);
				tile.setTravelledDistance(Integer.MAX_VALUE);
				tile.setHeuristicDistance(Integer.MAX_VALUE);
				if(pick<=3) {
					tile.setAboveGroundResource(productFactory.getType("Cow"));
					tile.setTile(tileFactory.getType("Grass"));
				} else if(pick<=5)	{
					tile.setAboveGroundResource(productFactory.getType("Corn"));
					tile.setTile(tileFactory.getType("Sand"));
				} else if(pick<=7)	{
					tile.setAboveGroundResource(productFactory.getType("Wood"));
					tile.setTile(tileFactory.getType("Forest"));
				} else if(pick==8) {
					tile.setAboveGroundResource(productFactory.getType("Corn"));
					tile.setTile(tileFactory.getType("Stone"));
				} else if(pick==9) {
					tile.setAboveGroundResource(productFactory.getType("Corn"));
					tile.setTile(tileFactory.getType("RoadBlock"));
				}				
				nodes[i][j] = tile ;				
			}
		}
		return nodes;
	}
	
}

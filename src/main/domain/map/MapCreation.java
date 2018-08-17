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
				int pick = rand.nextInt(3);
				Node tile = new Node();				
				switch(pick) {
				case 1:
					tile.setAboveGroundResource(productFactory.getType("Cow"));
					tile.setTile(tileFactory.getType("Grass"));
					break;
				case 2:
					tile.setAboveGroundResource(productFactory.getType("Corn"));
					tile.setTile(tileFactory.getType("Sand"));
					break;
				default:	
					tile.setAboveGroundResource(productFactory.getType("Wood"));
					tile.setTile(tileFactory.getType("Forest"));
				}				
				nodes[i][j] = tile ;				
			}
		}
		return nodes;
	}
	
}

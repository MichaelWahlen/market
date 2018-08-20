package main.domain.map;

import java.util.Random;

import main.domain.Node;
import main.domain.data.FactoryHolder;
import main.domain.data.GenericFactory;
import main.domain.data.Resource;
import main.domain.data.Tile;
import main.domain.data.Transport;


public class MapCreation {	

	public static Node[][] createSurface(int rows, int columns){
		GenericFactory<Tile> tileFactory = FactoryHolder.getGenericFactory(Tile.class);
		GenericFactory<Resource> productFactory = FactoryHolder.getGenericFactory(Resource.class);
		GenericFactory<Transport> transportFactory = FactoryHolder.getGenericFactory(Transport.class);
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
					node.setAboveGroundResource(productFactory.getType(3));
					node.setTile(tileFactory.getType(2));					
				} else if(pick<=5)	{
					node.setAboveGroundResource(productFactory.getType(1));
					node.setTile(tileFactory.getType(1));				
				} else if(pick<=7)	{
					node.setAboveGroundResource(productFactory.getType(2));
					node.setTile(tileFactory.getType(3));					
				} else if(pick==8) {
					node.setAboveGroundResource(productFactory.getType(1));
					node.setTile(tileFactory.getType(4));					
				} else if(pick==9) {
					node.setAboveGroundResource(productFactory.getType(1));
					node.setTile(tileFactory.getType(6));					
				}				
				node.setTransportType(transportFactory.getType(99));
				nodes[i][j] = node ;				
			}
		}
		return nodes;
	}
	
}

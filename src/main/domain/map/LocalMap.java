package main.domain.map;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import main.domain.Node;
import main.domain.StockPile;
import main.domain.data.FactoryHolder;
import main.domain.data.GenericFactory;
import main.domain.data.Manufacturer;
import main.domain.data.Resource;
import main.domain.data.Switch;
import main.domain.data.Tile;
import main.domain.data.Transport;
import main.gui.TableRepresentation;


public class LocalMap {	
	
	private Node[][] nodes;
	
	private GenericFactory<Transport> transportTypeFactory = FactoryHolder.getGenericFactory(Transport.class);
	private GenericFactory<Tile> tileFactory = FactoryHolder.getGenericFactory(Tile.class);
	private GenericFactory<Switch> switchFactory = FactoryHolder.getGenericFactory(Switch.class);
	private GenericFactory<Resource> resourceFactory = FactoryHolder.getGenericFactory(Resource.class);
	private GenericFactory<Manufacturer> manufacturerFactory = FactoryHolder.getGenericFactory(Manufacturer.class);
	
	public Node[][] createNodeMap(int rows, int columns){
		Node[][] nodes = new Node[rows][columns];
		Random rand = new Random();
		for(int i = 0;i<nodes.length;i++) {
			for(int j = 0 ; j < nodes[0].length;j++) {
				int pick = rand.nextInt(10);
				Node node = new Node();	
				node.setX(i);
				node.setY(j);
//				node.setTravelledDistance(Integer.MAX_VALUE);
//				node.setHeuristicDistance(Integer.MAX_VALUE);
				if(pick<=3) {
					node.setAboveGroundResource(resourceFactory.getType(3));
					node.setTile(tileFactory.getType(2));					
				} else if(pick<=5)	{
					node.setAboveGroundResource(resourceFactory.getType(1));
					node.setTile(tileFactory.getType(1));				
				} else if(pick<=7)	{
					node.setAboveGroundResource(resourceFactory.getType(2));
					node.setTile(tileFactory.getType(3));					
				} else if(pick==8) {
					node.setAboveGroundResource(resourceFactory.getType(1));
					node.setTile(tileFactory.getType(4));					
				} else if(pick==9) {
					node.setAboveGroundResource(resourceFactory.getType(1));
					node.setTile(tileFactory.getType(6));					
				}				
				node.setTransportType(transportTypeFactory.getType(99));
				nodes[i][j] = node ;				
			}
		}
		this.nodes = nodes;
		return nodes;
	}
	
	public void setTransportType(int x, int y, int transportType) {
		nodes[x][y].setTransportType(transportTypeFactory.getType(transportType));
	}
	
	public Map<String, TableRepresentation> getAllTableReps(){
		Map<String, TableRepresentation> returnResult = new HashMap<String, TableRepresentation>();
		returnResult.put("Tile", tileFactory.getTableRepresentation());
		returnResult.put("Switch", switchFactory.getTableRepresentation());
		returnResult.put("Resource", resourceFactory.getTableRepresentation());
		returnResult.put("Transport", transportTypeFactory.getTableRepresentation());
		returnResult.put("Manufacturer", manufacturerFactory.getTableRepresentation());
		return returnResult;
		
	}

	public StockPile getStockPile() {		
		return new StockPile(resourceFactory.getKeys());
	}

	public void switchTile(int fromX, int fromY, int string) {
		nodes[fromX][fromY].setAboveGroundResource(resourceFactory.getType(string));		
	}

	public void setSwitch(int x, int y, int switchKey) {
		if(nodes[x][y].isVacant()) {
			nodes[x][y].setSwitch(switchFactory.getType(switchKey));
		}
	}
	
}

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
import main.domain.data.Terrain;
import main.domain.data.Transport;
import main.gui.TableRepresentation;


public class LocalMap {	
	
	private Node[][] nodes;
	
	private GenericFactory<Transport> transportTypeFactory = FactoryHolder.getGenericFactory(Transport.class);
	private GenericFactory<Terrain> terrainFactory = FactoryHolder.getGenericFactory(Terrain.class);
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
				if(pick<=3) {
					node.setAboveGroundResource(resourceFactory.getType(3));
					node.setTerrain(terrainFactory.getType(2));					
				} else if(pick<=5)	{
					node.setAboveGroundResource(resourceFactory.getType(1));
					node.setTerrain(terrainFactory.getType(1));				
				} else if(pick<=7)	{
					node.setAboveGroundResource(resourceFactory.getType(2));
					node.setTerrain(terrainFactory.getType(3));					
				} else if(pick==8) {
					node.setAboveGroundResource(resourceFactory.getType(1));
					node.setTerrain(terrainFactory.getType(4));					
				} else if(pick==9) {
					node.setAboveGroundResource(resourceFactory.getType(1));
					node.setTerrain(terrainFactory.getType(5));
					node.setFull(true);
				}				
				node.addTransport(transportTypeFactory.getType(99));
				nodes[i][j] = node ;				
			}
		}
		this.nodes = nodes;
		return nodes;
	}
	
	public void setTransportType(int x, int y, int transportType) {
		nodes[x][y].addTransport(transportTypeFactory.getType(transportType));
	}
	
	public Map<String, TableRepresentation> getAllTableReps(){
		Map<String, TableRepresentation> returnResult = new HashMap<String, TableRepresentation>();
		returnResult.put("Tile", terrainFactory.getTableRepresentation());
		returnResult.put("Switch", switchFactory.getTableRepresentation());
		returnResult.put("Resource", resourceFactory.getTableRepresentation());
		returnResult.put("Transport", transportTypeFactory.getTableRepresentation());
		returnResult.put("Manufacturer", manufacturerFactory.getTableRepresentation());
		return returnResult;		
	}
	
	public boolean tileContainsTransport(int x, int y, int transportTypeKey) {
		boolean containsTransportType = false;		
		for(Transport transport: nodes[x][y].getTransportType().values()) {
			if(transport.getCode() == transportTypeKey) {
				containsTransportType = true;
				break;
			}
		}
		return containsTransportType;
	}
	
	public StockPile getStockPile() {		
		return new StockPile(resourceFactory.getKeys());
	}

	public void switchTile(int fromX, int fromY, int key) {
		nodes[fromX][fromY].setAboveGroundResource(resourceFactory.getType(key));		
	}

	public void setSwitch(int x, int y, int key) {
		if(!nodes[x][y].isFull()) {
			nodes[x][y].setSwitch(switchFactory.getType(key));
		}
	}
	
	public int getNetworkKey(int x, int y) {
		return nodes[x][y].getTopNetworkKey();
	}
	
}

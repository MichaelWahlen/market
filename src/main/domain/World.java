package main.domain;
import java.util.List;

import main.domain.data.FactoryHolder;
import main.domain.data.GenericFactory;
import main.domain.data.Manufacturer;
import main.domain.data.Resource;
import main.domain.data.Switch;
import main.domain.data.Tile;
import main.domain.data.Transport;

import main.domain.map.MapCreation;
import main.domain.map.Router;


public class World {
	
	private Web web;
	private Node[][] nodes;
	private StockPile stockPile;
	
	private GenericFactory<Transport> transportTypeFactory = null;
	private GenericFactory<Tile> tileFactory = null;
	private GenericFactory<Switch> switchFactory = null;
	private GenericFactory<Resource> resourceFactory = null;
	private GenericFactory<Manufacturer> manufacturerFactory = null;
	private Router router;
	
	public World() {		
		transportTypeFactory = FactoryHolder.getGenericFactory(Transport.class);
		switchFactory = FactoryHolder.getGenericFactory(Switch.class);
		tileFactory = FactoryHolder.getGenericFactory(Tile.class);
		resourceFactory = FactoryHolder.getGenericFactory(Resource.class);
		manufacturerFactory = FactoryHolder.getGenericFactory(Manufacturer.class);
		createTile();
		createHub();
		router = new Router(nodes);		
	}	
	
	private void createHub() {
		stockPile = new StockPile(resourceFactory.getKeys());		
	}

	public void switchTile(int fromX, int fromY, int string, int toX, int toY) {		
			nodes[fromX][fromY].setAboveGroundResource(resourceFactory.getType(string));			
	}
	
	public void buildTransportLine(int fromX, int fromY, int string, int toX, int toY) {
		if(nodes[toX][toY].isPassable() && nodes[fromX][fromY].isPassable()) {
			List<Node> nodes  = router.getRoute(fromX,fromY,toX,toY,0);			
			if(nodes!=null) {
				web.setTransportType(nodes, string);
			}
		}
	}
	
	private void createTile() {		
		nodes = MapCreation.createSurface(10, 13);
		web = new Web(nodes);		
	}
	
	public void simulateTick() {
		for(int i = 0; i<nodes.length;i++){
			for(int j =0;j<nodes[0].length;j++) {
				GenerateableResource tileResource = nodes[i][j].getAboveGroundResource();
				if(!tileResource.isCompoundResource()) {
					stockPile.stockImport(tileResource.getCode(), tileResource.getDefaultGenerationRate());
				} else {
					if(stockPile.takeFromStock(tileResource.getRequiredResources())) {
						stockPile.stockImport(tileResource.getCode(), tileResource.getDefaultGenerationRate());
					}
				}				
			}
		}			
	}
	
	public Node[][] getNodes() {
		return nodes;
	}
	
	public List<String> getResourceColumns() {
		return resourceFactory.getColumns();
	}


	public Object[][] getResources() {		
		return resourceFactory.getObjectRepresentation();
	}

	public List<String> getStockColumns() {		
		return stockPile.getFieldNames(); 
	}

	public Object[][] getStocks() {
		return stockPile.stockToObjects();
	}
	
	public List<String> getManufactoryColumns() {
		return manufacturerFactory.getColumns();
	}
	
	public Object[][] getManufacturers() {		
		return manufacturerFactory.getObjectRepresentation();
	}

	public List<String> getTransportColumns() {
		return transportTypeFactory.getColumns();
	}
	
	public Object[][] getTransportTypes() {		
		return transportTypeFactory.getObjectRepresentation();
	}

	public List<String> getTileColumns() {
		return tileFactory.getColumns();
	}
	
	public Object[][] getTiles() {		
		return tileFactory.getObjectRepresentation();
	}

	public List<String> getSwitchColumns() {
		return switchFactory.getColumns();
	}
	
	public Object[][] getSwitches() {		
		return switchFactory.getObjectRepresentation();
	}

	public void addSwitch(int x, int y, int switchKey) {
		Node selectedNode = nodes[x][y];	
		if(selectedNode.isVacant()) {
			selectedNode.setSwitch(switchFactory.getType(switchKey));
		}		
	}
	

	
}

package main.domain;
import java.util.List;

import main.domain.data.FactoryHolder;
import main.domain.data.GenericFactory;
import main.domain.data.ManufacturerFactory;
import main.domain.data.ResourceFactory;
import main.domain.data.Switch;
import main.domain.data.TileFactory;
import main.domain.data.Transport;

import main.domain.map.MapCreation;
import main.domain.map.Router;


public class World {
	
	private Web web;
	private Node[][] nodes;
	private StockPile stockPile;
	private ResourceFactory resourceFactory = ResourceFactory.getInstance();
	private ManufacturerFactory manufacturerFactory = ManufacturerFactory.getInstance();
	private GenericFactory<Transport> transportTypeFactory = null;
	private TileFactory tileFactory = TileFactory.getInstance();
	private GenericFactory<Switch> switchFactory = null;
	private FactoryHolder fact = FactoryHolder.getInstance();
	private Router router;
	
	public World() {			
		createTile();
		createHub();
		router = new Router(nodes);		
		transportTypeFactory = fact.getTransportInstance();
		switchFactory = fact.getSwitchFactory();
	}	
	
	private void createHub() {
		stockPile = new StockPile(resourceFactory.getResourceKeys());		
	}

	public void switchTile(int fromX, int fromY, String string, int toX, int toY) {
		if(resourceFactory.getType(string)!=null) {
			nodes[fromX][fromY].setAboveGroundResource(resourceFactory.getType(string));
		} else if(nodes[toX][toY].isPassable() && nodes[fromX][fromY].isPassable()) {
			List<Node> nodes  = router.getRoute(fromX,fromY,toX,toY,string,0);			
			if(nodes!=null) {
				web.setTransportType(nodes, Integer.parseInt(string));
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
					stockPile.stockImport(tileResource.getName(), tileResource.getDefaultGenerationRate());
				} else {
					if(stockPile.takeFromStock(tileResource.getRequiredResources())) {
						stockPile.stockImport(tileResource.getName(), tileResource.getDefaultGenerationRate());
					}
				}				
			}
		}			
	}
	
	public Node[][] getNodes() {
		return nodes;
	}
	
	public List<String> getResourceColumns() {
		return ResourceFactory.getColumns();
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
		return ManufacturerFactory.getColumns();
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
		return TileFactory.getColumns();
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

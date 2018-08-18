package main.domain;
import java.util.List;

import main.domain.data.ManufacturerFactory;
import main.domain.data.ResourceFactory;
import main.domain.data.TileFactory;
import main.domain.data.TransportFactory;
import main.domain.map.MapCreation;
import main.domain.map.Router;


public class World {
	
	private Web web;
	private Node[][] nodes;
	private StockPile stockPile;
	private ResourceFactory resourceFactory = ResourceFactory.getInstance();
	private ManufacturerFactory manufacturerFactory = ManufacturerFactory.getInstance();
	private TransportFactory transportTypeFactory = TransportFactory.getInstance();
	private TileFactory tileFactory = TileFactory.getInstance();
	private Router router;
	
	public World() {			
		createTile();
		createHub();
		router = new Router(nodes);
	}	
	
	private void createHub() {
		stockPile = new StockPile(resourceFactory.getResourceKeys());		
	}

	public void switchTile(int fromX, int fromY, String string, int toX, int toY) {
		if(resourceFactory.getType(string)!=null) {
			nodes[fromX][fromY].setAboveGroundResource(resourceFactory.getType(string));
		} else if(nodes[toX][toY].isPassable() && nodes[fromX][fromY].isPassable()) {
			for(Node node:router.getRoute(fromX,fromY,toX,toY,true)) {
				web.setTransportType(node.getX(),node.getY(),string);			
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
		return web.getNodes();
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
		return TransportFactory.getColumns();
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
	

	
}

package main.domain;
import java.util.List;
import main.map.MapCreation;


public class World {
	
	private Web web;
	private Node[][] nodes;
	private StockPile stockPile;
	private ResourceFactory resourceFactory = ResourceFactory.getInstance();
	private ManufacturerFactory manufacturerFactory = ManufacturerFactory.getInstance();
	private TransportTypeFactory transportTypeFactory = TransportTypeFactory.getInstance();
	
	public World() {			
		createTile();
		createHub();
	}	
	
	private void createHub() {
		stockPile = new StockPile(resourceFactory.getResourceKeys());		
	}

	public void switchTile(int x, int y, String string) {
		if(resourceFactory.getType(string)!=null) {
			nodes[x][y].setAboveGroundResource(resourceFactory.getType(string));
		} else {
			web.setTransportType(x,y,string);			
		}
	}
	
	private void createTile() {
		MapCreation mp = new MapCreation(10,12);
		web = new Web(mp.getNodes());		
		nodes = web.getNodes();
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
	
	public List<String> getResourceFields() {
		return ResourceFactory.getColumns();
	}


	public Object[][] getResourceContents() {		
		return resourceFactory.getObjectRepresentation();
	}

	public List<String> getStockNames() {		
		return stockPile.getFieldNames(); 
	}

	public Object[][] getStock() {
		return stockPile.stockToObjects();
	}

	public Object[][] getManufacturer() {		
		return manufacturerFactory.getObjectRepresentation();
	}

	public List<String> getManufacturerColumnNames() {
		return ManufacturerFactory.getColumns();
	}
	
	public Object[][] getTransportTypes() {		
		return transportTypeFactory.getObjectRepresentation();
	}

	public List<String> getTransportColumns() {
		return TransportTypeFactory.getColumns();
	}
	
	public Node[][] getNodes() {
		return web.getNodes();
	}
	
}

package main.domain;
import java.util.List;
import main.map.MapCreation;
import main.map.Tile;

public class World {
	
	private Tile[][] tiles;
	private StockPile stockPile;
	private ResourceFactory resourceFactory = ResourceFactory.getInstance();
	private ManufacturerFactory manufacturerFactory = ManufacturerFactory.getInstance();
	
	public World() {			
		createTile();
		createHub();
	}	
	
	private void createHub() {
		stockPile = new StockPile(resourceFactory.getResourceKeys());		
	}

	public void switchTile(int x, int y, String string) {
		tiles[x][y].setAboveGroundResource(resourceFactory.getResource(string));
	}
	
	private void createTile() {
		MapCreation mp = new MapCreation(10,12);
		tiles = mp.getTileLevel();
	}
	
	public void simulateTick() {
		for(int i = 0; i<tiles.length;i++){
			for(int j =0;j<tiles[0].length;j++) {
				GenerateableResource tileResource = tiles[i][j].getAboveGroundResource();
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
		return ResourceFactory.getFieldNames();
	}


	public Object[][] getResourceContents() {		
		return resourceFactory.getResourcesToObjects();
	}
	

	public Tile[][] getTiles(){
		return tiles;
	}

	public List<String> getStockNames() {		
		return stockPile.getFieldNames(); 
	}

	public Object[][] getStock() {
		return stockPile.stockToObjects();
	}

	public Object[][] getManufacturer() {		
		return manufacturerFactory.getManufacturersToObjects();
	}

	public List<String> getManufacturerColumnNames() {
		return ManufacturerFactory.getFieldNames();
	}
	
}

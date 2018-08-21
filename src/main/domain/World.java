package main.domain;
import java.util.Map;

import main.gui.TableRepresentation;


public class World {
	
	private Web web;
	private Node[][] nodes;
	private StockPile stockPile;
	
	public World() {
		web = new Web(10, 13);
		nodes = web.getNodes();
		stockPile = web.getStockPile();	
	}

	public void switchTile(int fromX, int fromY, int string, int toX, int toY) {		
		web.switchTile(fromX, fromY, string);					
	}
	
	public void buildTransportLine(int fromX, int fromY, int string, int toX, int toY) {
		web.buildTransportLine(fromX, fromY, string, toX, toY);
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
		return web.getNodes();
	}

	public TableRepresentation getStockTable() {
		TableRepresentation tableRep = new TableRepresentation();
		tableRep.setColumnNames(stockPile.getFieldNames());
		tableRep.setObjectRepresentation(stockPile.stockToObjects());
		return tableRep;
	}
	
	public void addSwitch(int x, int y, int switchKey) {				
			web.setSwitch(x,y,switchKey);			
	}
	
	public Map<String, TableRepresentation> getAllTableReps(){
		return web.getAllTableReps();
	}
	
}

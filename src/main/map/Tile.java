package main.map;

import main.domain.*;

public class Tile {
	
	private TileType tileType = null;
	private GenerateableResource aboveGroundResource = null;
	
	public Tile(TileType tileType){		
		this.tileType = tileType;	
	}
	
	public TileType getType(){
		return tileType;
	}
	
	public String getResource(){
		return tileType.getResourceName();
	}
	
	public int getTransType(){
		return tileType.getTransverseableGroup();
	}


	public GenerateableResource getAboveGroundResource() {
		return aboveGroundResource;
	}


	public void setAboveGroundResource(GenerateableResource aboveGroundResource) {
		this.aboveGroundResource = aboveGroundResource;
	}
	
	
	
}

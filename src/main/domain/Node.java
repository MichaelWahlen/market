package main.domain;

import main.domain.data.Tile;
import main.domain.data.Transport;

public class Node {
	
	private int detailNetworkKey = 0;
	private int topNetworkKey = 0;
	private GenerateableResource aboveGroundResource;
	private Transport transportType =new Transport();
	private Tile tile;
	
	public int getDetailNetworkKey() {
		return detailNetworkKey;
	}
	
	public void setDetailNetworkKey(int detailNetworkKey) {
		this.detailNetworkKey = detailNetworkKey;
	}
	
	public int getTopNetworkKey() {
		return topNetworkKey;
	}
	
	public void setTopNetworkKey(int topNetworkKey) {
		this.topNetworkKey = topNetworkKey;
	}
	
	public GenerateableResource getAboveGroundResource() {
		return aboveGroundResource;
	}
	
	public void setAboveGroundResource(GenerateableResource generateableResource) {
		this.aboveGroundResource = generateableResource;
	}

	public Transport getTransportType() {
		return transportType;
	}

	public void setTransportType(Transport transportType) {
		this.transportType = transportType;
	}

	public Tile getTile() {
		return tile;
	}

	public void setTile(Tile tile) {
		this.tile = tile;
	}
	
	public int getTileCode() {
		return tile.getCode();
	}
	
}

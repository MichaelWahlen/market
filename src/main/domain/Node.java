package main.domain;

import java.util.ArrayList;
import java.util.List;

import main.domain.data.Switch;
import main.domain.data.Tile;
import main.domain.data.Transport;

public class Node {
	
	private int detailNetworkKey = 0;
	private int topNetworkKey = 0;
	private GenerateableResource aboveGroundResource;
	private Transport containedTransports;
	private Tile tile;
	private int x;
	private int y;
	private Switch localSwitch;
	
	public List<String> getStatus(){
		List<String> overviewList = new ArrayList<String>();
		overviewList.add("Detail network key: "+detailNetworkKey);
		overviewList.add("Top network key: "+topNetworkKey);
		overviewList.add("Above ground resource: "+aboveGroundResource.getName());
		overviewList.add("Transport type: "+containedTransports.getName());
		overviewList.add("Tile name: "+tile.getName());
		overviewList.add("Tile weight: "+ tile.getWeight());
		overviewList.add("X coord: "+x);
		overviewList.add("Y coord: "+y);
		if(localSwitch!=null) {
			overviewList.add("Switch: "+localSwitch.getName());
		}
		return overviewList;
	}
	
	public boolean isVacant() {
		if(isPassable() && containedTransports.getCode()==99 && localSwitch == null) {
			return true;
		}
		return false;
	}
	
	public void setSwitch(Switch localSwitch) {
		this.localSwitch = localSwitch;
	}
	
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
		return containedTransports;
	}

	public void setTransportType(Transport transportType) {
		this.containedTransports = transportType;
	}

	public void setTile(Tile tile) {
		this.tile = tile;
	}
	
	public int getTileCode() {
		return tile.getCode();
	}
	
	public int getWeight() {
		return tile.getWeight();
	}
	
	public boolean isPassable() {
		return tile.isPassable();
	}

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

	public Tile getTile() {
		return tile;
	}
	
}

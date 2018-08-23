package main.domain;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import main.domain.data.Switch;
import main.domain.data.Terrain;
import main.domain.data.Transport;

public class Node {
	
	private int detailNetworkKey = 0;
	private int topNetworkKey = 0;
	private GenerateableResource aboveGroundResource;
	private Map<Integer, Transport> containedTransports = new HashMap<Integer, Transport>();
	private Terrain terrain;
	private int x;
	private int y;
	private Switch localSwitch;
	private int Slope = 0;
	private boolean isFull = false;
	
	public List<String> getStatus(){
		List<String> overviewList = new ArrayList<String>();
		overviewList.add("Detail network key: "+detailNetworkKey);
		overviewList.add("Top network key: "+topNetworkKey);
		overviewList.add("Above ground resource: "+aboveGroundResource.getName());
		String transportNames = "";
		for(Transport transport:containedTransports.values()) {
			transportNames = transportNames + " "+transport.getName();
		}
		overviewList.add("Transport type: "+transportNames);
		overviewList.add("Tile name: "+terrain.getName());
		overviewList.add("Tile weight: "+ terrain.getWeight());
		overviewList.add("X coord: "+x);
		overviewList.add("Y coord: "+y);
		if(localSwitch!=null) {
			overviewList.add("Switch: "+localSwitch.getName());
		}
		return overviewList;
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

	public Map<Integer,Transport> getTransportType() {
		return containedTransports;
	}

	public void addTransport(Transport transportType) {
		containedTransports.put(transportType.getCode(),transportType);
	}

	public void setTerrain(Terrain tile) {
		this.terrain = tile;
	}
	
	public int getTileCode() {
		return terrain.getCode();
	}
	
	public int getWeight() {
		return terrain.getWeight();
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

	public Terrain getTile() {
		return terrain;
	}

	public int getSlope() {
		return Slope;
	}

	public void setSlope(int slope) {
		Slope = slope;
	}

	public boolean isFull() {
		return isFull;
	}

	public void setFull(boolean isFull) {
		this.isFull = isFull;
	}
	
}

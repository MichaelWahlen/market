package main.domain;

import java.util.ArrayList;
import java.util.List;

import main.domain.data.Tile;
import main.domain.data.Transport;

public class Node {
	
	private int detailNetworkKey = 0;
	private int topNetworkKey = 0;
	private GenerateableResource aboveGroundResource;
	private Transport transportType;
	private Tile tile;
	private Node parentNode;	
	private int x;
	private int y;
	private int travelledDistance = 0;
	private int heuristicDistance = 0;	
	
	public List<String> getStatus(){
		List<String> overviewList = new ArrayList<String>();
		overviewList.add("Detail network key: "+detailNetworkKey);
		overviewList.add("Top network key: "+topNetworkKey);
		overviewList.add("Above ground resource: "+aboveGroundResource.getName());
		overviewList.add("Transport type: "+transportType.getName());
		overviewList.add("Tile name: "+tile.getName());
		overviewList.add("Tile weight: "+ tile.getWeight());
		overviewList.add("X coord: "+x);
		overviewList.add("Y coord: "+y);
		return overviewList;
	}
	
	
	public int getTotalDistanceToGoal() {
		return travelledDistance+heuristicDistance;
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
		return transportType;
	}

	public void setTransportType(Transport transportType) {
		this.transportType = transportType;
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

	public Node getParentNode() {
		return parentNode;
	}

	public void setParentNode(Node parentNode) {
		this.parentNode = parentNode;
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

	public int getTravelledDistance() {
		return travelledDistance;
	}

	public void setTravelledDistance(int travelledDistance) {
		this.travelledDistance = travelledDistance;
	}

	public Tile getTile() {
		return tile;
	}

	public int getHeuristicDistance() {
		return heuristicDistance;
	}

	public void setHeuristicDistance(int heuristicDistance) {
		this.heuristicDistance = heuristicDistance;
	}
	

	
}

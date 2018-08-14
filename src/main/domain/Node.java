package main.domain;

public class Node {
	
	private int detailNetworkKey = 0;
	private int topNetworkKey = 0;
	private GenerateableResource aboveGroundResource;
	private TransportType transportType =new TransportType();
	
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

	public TransportType getTransportType() {
		return transportType;
	}

	public void setTransportType(TransportType transportType) {
		this.transportType = transportType;
	}
	
}

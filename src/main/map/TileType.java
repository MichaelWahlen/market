package main.map;

public enum TileType {FOREST(1,4,20,"Wood"),DIRT(1,4,0,"None"),WATER(2,9,0,"Water"),STONE(0,9,10,"Stone"),ROAD(1,2,0,"None"), GOLD(0,9,5,"Gold"), CITY(1,3,0,"None")
	,GRASS(1,4,0,"None"),	ENTRYDOWN(1,5,0,"None"), ENTRYUP(1,5,0,"None"), RAILROAD(3,1,0,"None"),DOOR(1,1,0,"None"),WALL(0,9,0,"None");
	
	
	// transverseable group are the following groups : none (0), passableLand (1), passableWater (2), passableTrain (3)
	private int transverseableGroup;
	private int weight;
	private int potentialResourceAmount;
	private String resourceName;
		
	private TileType(int transverseableGroup, int weight, int potentialAvailableAmount, String resourceName){
		this.transverseableGroup = transverseableGroup;
		this.weight = weight;
		this.potentialResourceAmount = potentialAvailableAmount;
		this.resourceName = resourceName;
	}
	
	public int getPotentialAmount(){
		return potentialResourceAmount;
	} 
	
	public int getWeight(){
		return weight;
	}
	
	public String getResourceName(){
		return resourceName;
	}
	
	public int getTransverseableGroup(){
		return transverseableGroup;
	}

}

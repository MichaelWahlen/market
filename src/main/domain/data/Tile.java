package main.domain.data;

public class Tile {
	private int code;
	private String name;
	private int weight;
	private boolean passable;
	
	public int getCode() {
		return code;
	}
	public void setCode(int code) {
		this.code = code;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	public int getWeight() {
		return weight;
	}
	public void setWeight(int weight) {
		this.weight = weight;
	}
	public boolean isPassable() {
		return passable;
	}
	public void setPassable(boolean passable) {
		this.passable = passable;
	}
	
	
}
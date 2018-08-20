package main.domain.data;

import java.util.ArrayList;
import java.util.List;

import main.utilities.StringUtilities;

public class Tile implements StaticData{
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
	@Override
	public List<String> getDetailsInOrder() {
		List<String> returns = new ArrayList<String>();
		returns.add(""+code);
		returns.add(name);
		returns.add(""+weight);
		returns.add(""+passable);
		return returns;
	}
	@Override
	public StaticData get(String string) {
		List<String> moreString = StringUtilities.decomposeValueSeperatedString(string, '|');
		Tile tile = new Tile();
		tile.setCode(Integer.parseInt(moreString.get(0)));
		tile.setName(moreString.get(1));
		tile.setWeight(Integer.parseInt(moreString.get(2)));
		tile.setPassable(Boolean.parseBoolean(moreString.get(3)));
		return tile;
	}
	
	
}

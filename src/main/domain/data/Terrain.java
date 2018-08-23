package main.domain.data;

import java.util.ArrayList;
import java.util.List;

import main.utilities.StringUtilities;

public class Terrain implements StaticData{
	private int code;
	private String name;
	private int traverseCost;
	
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
		return traverseCost;
	}
	public void setWeight(int weight) {
		this.traverseCost = weight;
	}

	@Override
	public List<String> getDetailsInOrder() {
		List<String> returns = new ArrayList<String>();
		returns.add(""+code);
		returns.add(name);
		returns.add(""+traverseCost);	
		return returns;
	}
	@Override
	public StaticData get(String string) {
		List<String> moreString = StringUtilities.decomposeValueSeperatedString(string, '|');
		Terrain tile = new Terrain();
		tile.setCode(Integer.parseInt(moreString.get(0)));
		tile.setName(moreString.get(1));
		tile.setWeight(Integer.parseInt(moreString.get(2)));	
		return tile;
	}
	
	
}

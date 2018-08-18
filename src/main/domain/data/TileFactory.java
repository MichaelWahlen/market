package main.domain.data;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import main.utilities.ParserCSV;
import main.utilities.StringUtilities;

public class TileFactory {
	
	private static TileFactory instance = null;
	private Map<String, Tile> types = new HashMap<String, Tile>();
		   
	private TileFactory() {	
		loadTypes();
	}
	
	public static TileFactory getInstance() {
		if(instance == null) {
			instance = new TileFactory();
		}
		return instance;
	}
	   
	public Tile getType(String key){		  	   
		return types.get(key);
	}
	
	public List<String> getKeys(){
		List<String> returnList = new ArrayList<String>();
		returnList.addAll(types.keySet());
		return returnList;
	}
	
	private void loadTypes() {
		List<String> initialStrings = ParserCSV.parseToStrings(new File("src/resources/Tile.csv"));
		for(String string:initialStrings) {			
			List<String> moreString = StringUtilities.decomposeValueSeperatedString(string, '|');
			Tile tile = new Tile();
			tile.setCode(Integer.parseInt(moreString.get(0)));
			tile.setName(moreString.get(1));
			tile.setWeight(Integer.parseInt(moreString.get(2)));
			tile.setPassable(Boolean.parseBoolean(moreString.get(3)));
			this.types.put(tile.getName(), tile);
		}
	}
	
	public static List<String> getColumns() {
		return Arrays.asList("code","name","weight","isPassable"); 
	}
	
	public Object[][] getObjectRepresentation(){	
		Object[][] typeToObjects = new Object[types.size()][4];
		int i = 0;
		for(Tile product:types.values()) {
			typeToObjects[i][0] = product.getCode();
			typeToObjects[i][1] = product.getName();
			typeToObjects[i][2] = product.getWeight();
			typeToObjects[i][3] = product.isPassable();
			i++;			
		}
		return typeToObjects;
	}
}

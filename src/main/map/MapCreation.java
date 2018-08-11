package main.map;

import java.util.Random;
import main.domain.ResourceFactory;

public class MapCreation {
	
	//private TileFactory tileFactory = TileFactory.getInstance();
	private ResourceFactory productFactory = ResourceFactory.getInstance();
	private Tile[][] tileLevel;
	private int rows, columns;
	
	public MapCreation(int rows, int columns){
		this.rows = rows;
		this.columns = columns;		
		createSurface();	
	}
	
	public Tile[][] getTileLevel(){
		return tileLevel;
	}
	
	private void createSurface(){
		tileLevel = new Tile[rows][columns];
		Random rand = new Random();
		for(int i = 0;i<tileLevel.length;i++) {
			for(int j = 0 ; j < tileLevel[0].length;j++) {
				int pick = rand.nextInt(3);
				if(pick==1) {
					Tile tile = new Tile(TileType.DIRT);
					tile.setAboveGroundResource(productFactory.getResource("Cow"));
					tileLevel[i][j] = tile ;
				} else
				if(pick==0) {
					Tile tile = new Tile(TileType.FOREST);
					tile.setAboveGroundResource(productFactory.getResource("Corn"));
					tileLevel[i][j] = tile ;
				} else {
					Tile tile = new Tile(TileType.DIRT);
					tile.setAboveGroundResource(productFactory.getResource("Wood"));
					tileLevel[i][j] = tile ;
				}
			}
		}
	}
}

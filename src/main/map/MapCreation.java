package main.map;

import java.util.Random;

import main.domain.Node;
import main.domain.ResourceFactory;

public class MapCreation {
	
	//private TileFactory tileFactory = TileFactory.getInstance();
	private ResourceFactory productFactory = ResourceFactory.getInstance();
	private Node[][] tileLevel;
	private int rows, columns;
	
	public MapCreation(int rows, int columns){
		this.rows = rows;
		this.columns = columns;		
		createSurface();	
	}
	
	public Node[][] getTileLevel(){
		return tileLevel;
	}
	
	private void createSurface(){
		tileLevel = new Node[rows][columns];
		Random rand = new Random();
		for(int i = 0;i<tileLevel.length;i++) {
			for(int j = 0 ; j < tileLevel[0].length;j++) {
				int pick = rand.nextInt(3);
				if(pick==1) {
					Node tile = new Node();
					tile.setAboveGroundResource(productFactory.getType("Cow"));
					tileLevel[i][j] = tile ;
				} else
				if(pick==0) {
					Node tile = new Node();
					tile.setAboveGroundResource(productFactory.getType("Corn"));
					tileLevel[i][j] = tile ;
				} else {
					Node tile = new Node();
					tile.setAboveGroundResource(productFactory.getType("Wood"));
					tileLevel[i][j] = tile ;
				}
			}
		}
	}

	public Node[][] getNodes() {
		
		return tileLevel;
	}
}

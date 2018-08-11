package main.map;

public class TileFactory {
	private static TileFactory instance = null;
	   
	   private TileFactory() {		   
	   }
	
	   public static TileFactory getInstance() {
		      if(instance == null) {
		         instance = new TileFactory();
		      }
		      return instance;
		}
	   
	   public Tile getTile(TileType tileType){
		   Tile square = new Tile(tileType);		   
		   return square;
	   }
}

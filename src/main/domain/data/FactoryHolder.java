package main.domain.data;

public class FactoryHolder {
	
	private static FactoryHolder instance = null;
	private GenericFactory<Switch> switchFactory = null;
	private GenericFactory<Transport> transportFactory = null;
	private GenericFactory<Tile> tileFactory = null;
	private GenericFactory<Resource> resourceFactory = null;
	private GenericFactory<Manufacturer> manufacturerFactory = null;
		
	private FactoryHolder() {		
	}
	
	public static FactoryHolder getInstance() {
		if(instance == null) {
			instance = new FactoryHolder();
			instance.initFactories();
		}
		return instance;
	}
	
	@SuppressWarnings("unchecked")
	public <T extends StaticData> GenericFactory<T> getGenericFactory(Class<T> classref){		
		if(classref.getSimpleName().equals("Manufacturer")) {
			return (GenericFactory<T>) manufacturerFactory;
		}
		if(classref.getSimpleName().equals("Transport")) {				
			return (GenericFactory<T>) transportFactory;
		}
		if(classref.getSimpleName().equals("Switch")) {
			return (GenericFactory<T>) switchFactory;
		}
		if(classref.getSimpleName().equals("Tile")) {
			return (GenericFactory<T>) tileFactory;
		}
		if(classref.getSimpleName().equals("Resource")) {
			return (GenericFactory<T>) resourceFactory;
		}
		return null;		
	}
	
	private void initFactories() {			
			transportFactory = new GenericFactory<Transport>(Transport.class);			
			switchFactory = new GenericFactory<Switch>(Switch.class);			
			tileFactory = new GenericFactory<Tile>(Tile.class);			
			resourceFactory = new GenericFactory<Resource>(Resource.class);			
			manufacturerFactory = new GenericFactory<Manufacturer>(Manufacturer.class);		
	}

	
	
}

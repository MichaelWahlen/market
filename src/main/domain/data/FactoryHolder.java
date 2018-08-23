package main.domain.data;

/**
 * Functions as a wrapper around the different factories that are in turn used to get static data elements
 */
public class FactoryHolder {	

	private static final GenericFactory<Transport> transportFactory = new GenericFactory<Transport>(Transport.class);
	private static final GenericFactory<Switch> switchFactory = new GenericFactory<Switch>(Switch.class);;	
	private static final GenericFactory<Terrain> terrainFactory = new GenericFactory<Terrain>(Terrain.class);	
	private static final GenericFactory<Resource> resourceFactory = new GenericFactory<Resource>(Resource.class);
	private static final GenericFactory<Manufacturer> manufacturerFactory = new GenericFactory<Manufacturer>(Manufacturer.class);
	
	@SuppressWarnings("unchecked")
	public static <T extends StaticData> GenericFactory<T> getGenericFactory(Class<T> classref){		
		if(classref.getSimpleName().equals("Manufacturer")) {
			return (GenericFactory<T>) manufacturerFactory;
		}
		if(classref.getSimpleName().equals("Transport")) {				
			return (GenericFactory<T>) transportFactory;
		}
		if(classref.getSimpleName().equals("Switch")) {
			return (GenericFactory<T>) switchFactory;
		}
		if(classref.getSimpleName().equals("Terrain")) {
			return (GenericFactory<T>) terrainFactory;
		}
		if(classref.getSimpleName().equals("Resource")) {
			return (GenericFactory<T>) resourceFactory;
		}
		return null;		
	}
	
}

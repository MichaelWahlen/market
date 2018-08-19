package main.domain.data;

public class FactoryHolder {
	
	private static FactoryHolder instance = null;
	private GenericFactory<Switch> switchFactory = null;
	private GenericFactory<Transport> transportFactory = null;
	
	private FactoryHolder() {		
	}
	
	public static FactoryHolder getInstance() {
		if(instance == null) {
			instance = new FactoryHolder();
			instance.initFactories();
		}
		return instance;
	}
	
	public GenericFactory<Transport>  getTransportInstance() {
		return transportFactory;
	}
	
	public GenericFactory<Switch> getSwitchFactory(){
		return switchFactory;
	}
	
	private void initFactories() {		
		if (transportFactory==null) {
			String fileLocation = "src/resources/Transport.csv";
			transportFactory = new GenericFactory<Transport>(new TransportLoad(),fileLocation);
		}
		if (switchFactory==null) {
			String fileLocation = "src/resources/Switch.csv";
			switchFactory = new GenericFactory<Switch>(new SwitchLoad(),fileLocation);
		}
	}
	
}

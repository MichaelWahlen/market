package main.domain.data;

import java.util.ArrayList;
import java.util.List;
import main.domain.GenerateableResource;
import main.domain.Producer;
import main.domain.StockPile;
import main.utilities.StringUtilities;

public class Manufacturer implements Producer, StaticData {

	private StockPile localStockPile;
	private List<GenerateableResource> manufacturedResources = new ArrayList<GenerateableResource>();
	private String name;
	private int code;
	
	public Manufacturer() {
		
	}

	public void addManufacturedResource(GenerateableResource resource) {
		manufacturedResources.add(resource);
	}
	
	public void fireProductionRun() {
		for(GenerateableResource resource:manufacturedResources) {		
			if(localStockPile.takeFromStock(resource.getRequiredResources())) {
				localStockPile.stockImport(resource.getCode(), resource.getDefaultGenerationRate());
			}
		}
	}
	
	public void resetStockpile() {
		localStockPile = new StockPile(FactoryHolder.getGenericFactory(Resource.class).getKeys());
	}
	
	public Integer exportResource(Integer key, Integer amount) {		
		return localStockPile.stockExport(key, amount);		
	}
	
	public void importResource(Integer key, Integer amount) {
		localStockPile.stockImport(key, amount);
	}
	
	public List<String> stockNames(){
		return localStockPile.getFieldNames();
	}
	
	public Object[][] stockToObjects(){
		return localStockPile.stockToObjects();
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}
	
	public StockPile getLocalStockPile() {
		return localStockPile;
	}

	public void setLocalStockPile(StockPile localStockPile) {
		this.localStockPile = localStockPile;
	}

	public List<GenerateableResource> getManufacturedResources() {
		return manufacturedResources;
	}

	public void setManufacturedResources(List<GenerateableResource> manufacturedResources) {
		this.manufacturedResources = manufacturedResources;
	}

	@Override
	public List<String> getDetailsInOrder() {
		List<String> returns = new ArrayList<String>();
		returns.add(""+code);
		returns.add(name);
		return returns;
	}
	
	@Override
	public StaticData get(String string) {
		List<String> moreString = StringUtilities.decomposeValueSeperatedString(string, '|');
		GenericFactory<Resource> resourceFactory = FactoryHolder.getGenericFactory(Resource.class);
		Manufacturer manufacturer = new Manufacturer();
		manufacturer.setCode(Integer.parseInt(moreString.get(0)));
		manufacturer.setName(moreString.get(1));
		manufacturer.resetStockpile();
		List<String> evenMoreString = StringUtilities.decomposeValueSeperatedString(moreString.get(2), ';');
		for(String stringie:evenMoreString) {
			manufacturer.addManufacturedResource(resourceFactory.getType(Integer.parseInt(stringie)));
		}			
		return manufacturer;
	}
}

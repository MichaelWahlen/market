package main.gui;
import java.util.*;

import main.controller.ExecutionLoop;
import main.domain.Node;
import main.domain.World;
import main.utilities.StringUtilities;

public class Controller implements Observer {
	
	private List<Listener> listeners = new ArrayList<Listener>();
	private ExecutionLoop engine = new ExecutionLoop(this);	
	private World world = new World();
	
	public Controller() {		
	}
	
	public void startEngine() {			
		new Thread(engine).start();				
	}	

	public void addListener(Listener listener) {
		listeners.add(listener);
	}

	public synchronized void runSimulationStep() {
		world.simulateTick();		
		updateListeners("Simulate");
	}

	public synchronized void updateListeners(String message) {
		for(Listener listener:listeners) {
			listener.update(message,this);
		}		
	}
	
	public Object[][] getResources(){
		return world.getResourceContents(); 
	}
	
	public List<String> getResourceColumnNames(){
		return world.getResourceFields();
	}
	
	public void pauze() {
		engine.pauze();
	}
	
	public Node[][] getTiles(){
		return world.getNodes();
	}

	@Override
	public void notify(String update) {
		if(update.toLowerCase().equals("pauze")) {
			pauze();
		} else {
			List<String> input = StringUtilities.decomposeValueSeperatedString(update, '|');
			if(input.get(0).equals("Clicked")) {
				world.switchTile(Integer.parseInt(input.get(1)), Integer.parseInt(input.get(2)),input.get(3));
				updateListeners("Click");
			}
			
		}
	}

	public Object[][] getStock() {
		return world.getStock();
	}

	public List<String> getStockNames() {		
		return world.getStockNames();
	}

	public Object[][] getManufacturer() {
		return world.getManufacturer();
	}

	public List<String> getManufacturerColumnNames() {		
		return world.getManufacturerColumnNames();
	}

	public Object[][] getTransportTypes() {
		return world.getTransportTypes();
	}

	public List<String> getTransportColumns() {		
		return world.getTransportColumns();
	}
}

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
		List<String> update = new ArrayList<String>();
		update.add("Simulate");
		updateListeners(update);
	}

	public synchronized void updateListeners(List<String> input) {
		for(Listener listener:listeners) {
			listener.update(input,this);
		}		
	}
	
	public void pauze() {
		engine.pauze();
	}
	
	public Node[][] getNodes(){
		return world.getNodes();
	}

	@Override
	public void notify(String update) {
		if(update.toLowerCase().equals("pauze")) {
			pauze();
		} else {
			List<String> input = StringUtilities.decomposeValueSeperatedString(update, '|');
			if(input.get(0).equals("Clicked")&&!input.get(3).equals("Information")) {				
				world.switchTile(Integer.parseInt(input.get(1)), Integer.parseInt(input.get(2)),input.get(3),Integer.parseInt(input.get(4)), Integer.parseInt(input.get(5)));
				updateListeners(input);
			}
			else if(input.get(0).equals("Switch")) {
				System.out.println("Switch");
				world.addSwitch(Integer.parseInt(input.get(1)), Integer.parseInt(input.get(2)),Integer.parseInt(input.get(3)));
			}
			else if(input.get(3).equals("Information")){				
				updateListeners(input);
			}
			
		}
	}
	
	public List<String> getResourceColumns(){
		return world.getResourceColumns();
	}
	
	public Object[][] getResources(){
		return world.getResources(); 
	}

	public List<String> getStockColumns() {		
		return world.getStockColumns();
	}
	
	public Object[][] getStocks() {
		return world.getStocks();
	}

	public List<String> getManufactorerColumns() {		
		return world.getManufactoryColumns();
	}
	
	public Object[][] getManufacturer() {
		return world.getManufacturers();
	}
	
	public List<String> getTransportColumns() {		
		return world.getTransportColumns();
	}
	
	public Object[][] getTransportTypes() {
		return world.getTransportTypes();
	}
	
	public List<String> getTileColumns() {		
		return world.getTileColumns();
	}
	
	public Object[][] getTiles() {
		return world.getTiles();
	}
	
	public List<String> getSwitchColumns() {		
		return world.getSwitchColumns();
	}
	
	public Object[][] getSwitches() {
		return world.getSwitches();
	}


}

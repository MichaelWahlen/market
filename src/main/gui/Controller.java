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

			}
			else if(input.get(0).equals("Switch")) {				
				world.addSwitch(Integer.parseInt(input.get(1)), Integer.parseInt(input.get(2)),Integer.parseInt(input.get(3)));
			}
			else if(input.get(0).equals("Transport")) {
				world.buildTransportLine(Integer.parseInt(input.get(1)), Integer.parseInt(input.get(2)),Integer.parseInt(input.get(3)),Integer.parseInt(input.get(4)),Integer.parseInt(input.get(5)));
				String[] args = new String[1];
				args[0] = "Transport";
				updateListeners(args);
			}
			else if(input.get(3).equals("Information")){				
				updateListeners(input);
			}			
		}
	}
	
	private void updateListeners(String[] args) {
		for(Listener listener:listeners) {
			listener.update(args,this);
		}		
	}
	
	public TableRepresentation getStockTable() {
		return world.getStockTable();
	}
	
	public Map<String, TableRepresentation> getAllTableReps(){
		return world.getAllTableReps();
	}
}

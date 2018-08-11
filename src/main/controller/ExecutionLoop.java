package main.controller;

import main.gui.Controller;

public class ExecutionLoop implements Runnable{
	
	private Controller controller;
	private boolean pauze = false;
	
	public ExecutionLoop(Controller controller) {
		this.controller = controller;
	}	
		
	@Override
	public void run() {
		long lapsedSimulationTime = 0;		
		long currentTime = System.currentTimeMillis();
		long simulationCycle = 250;		
		long newTime = 0;			
		while (true) {
			newTime = System.currentTimeMillis();
			lapsedSimulationTime = lapsedSimulationTime + newTime - currentTime;			
			currentTime = newTime;			
			while(lapsedSimulationTime > simulationCycle) {
				if(lapsedSimulationTime > 2 * simulationCycle ) {
					System.out.println("Simulation cannot catch up");
				}
				lapsedSimulationTime = lapsedSimulationTime - simulationCycle;
				controller.runSimulationStep();				
			}
			while(pauze) {				
				currentTime = System.currentTimeMillis();
			}
		}		
	}

	public void pauze() {
		pauze = !pauze;		
	}	
	 
}

package main.application;
import main.gui.Controller;
import main.gui.TestFrame;

public class Start {
 
	public static void main(String[] args) {
		Controller controller = new Controller();
		controller.startEngine();
		TestFrame testFrame = new TestFrame(controller);
		testFrame.registerObserver(controller);
		testFrame.setVisible(true);			
		controller.addListener(testFrame);		
		System.out.println("now we work");
	}

}

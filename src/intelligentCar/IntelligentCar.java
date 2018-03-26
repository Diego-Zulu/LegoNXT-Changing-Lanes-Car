package intelligentCar;

import java.io.IOException;

import sensorHandling.ProximityCheckingHandler;
import btHandling.BTConnectionHandler;
import btHandling.BTMessagesHandler;
import lejos.nxt.Button;
import motorHandling.PropulsionHandler;
 
public class IntelligentCar
{
	boolean isRunning = true;
	BTConnectionHandler bt;
	PropulsionHandler propulsion;
	ProximityCheckingHandler proximity;
	BTMessagesHandler messageHandler;
	
	final int speedModifier = 10;
	
	public IntelligentCar() {
		
		bt = BTConnectionHandler.getInstance();
		proximity = ProximityCheckingHandler.getInstance();
		propulsion = PropulsionHandler.getInstance();
		messageHandler = new BTMessagesHandler();
	}
	
	public void run() throws InterruptedException {
		
		LCDHandler.displayMessage("Starting BT", "Please Wait ...");
		bt.startConnection();
		LCDHandler.displayUntilButtonPressed("BT connected", "Start?");
		
		while (isRunning) {
			
			stepsToRepeat();
		}
		
		bt.closeConnection();
	}
	
	private void stepsToRepeat() {
		
		String btMessage = "";
		try {
			
		btMessage = bt.receiveMessage();
		messageHandler.actOnBTMessage(btMessage);
		
		} catch (IOException ex) {
			
			LCDHandler.notifyException(ex);
		}
		proximity.checkForNewObstructions(propulsion.getMaxSpeed());
		propulsion.accelerateAcoordingToDistance(proximity.getAproximateFrontFreeDistance());
		checkForBrickButtonsPress();
	}
	
	private void checkForBrickButtonsPress() {
		checkForManualSpeedUpOrDown();
		checkForManualStop();
		checkForManualProgramEnd();
	}
	
	private void checkForManualSpeedUpOrDown() {
		
		int actualSpeed = propulsion.getMaxSpeed();
		if (Button.LEFT.isDown()) {
			actualSpeed -= speedModifier;
			LCDHandler.displayMessage("SPEED DOWN", actualSpeed + "");
		} else if (Button.RIGHT.isDown()) {
			actualSpeed += speedModifier;
			LCDHandler.displayMessage("SPEED UP", actualSpeed + "");
		}
	}
	
	private void checkForManualStop() {
		
		if (Button.ENTER.isDown()) {
			LCDHandler.displayMessage("STOPPED", "Press any button ...");
			try {
				Thread.sleep(100);
			} catch (InterruptedException ex) {
				LCDHandler.notifyException(ex);
			}
			Button.waitForAnyPress();
		}
	}
	
	private void checkForManualProgramEnd() {
		if (Button.ESCAPE.isDown()) {
			LCDHandler.displayMessage("BYE BYE!", "Ending ...");
			isRunning = false;
		}
	}
}
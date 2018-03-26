package btHandling;

import intelligentCar.LCDHandler;
import motorHandling.DirectionHandler;
import sensorHandling.ProximityCheckingHandler;

public class BTMessagesHandler {

	final static int actionMessageLength = 5;
	final static int totalMessageLength = 8;
	final static String turnToMessage = "TRNTO";
	final static String turnByMessage = "TRNBY";
	
	MessageMemory messageMemory;
	DirectionHandler direction;
	ProximityCheckingHandler proximity;
	
	public BTMessagesHandler() {
		messageMemory = new MessageMemory();
		direction = DirectionHandler.getInstance();
		proximity = ProximityCheckingHandler.getInstance();
	}

	public void actOnBTMessage(String message) {
		
		if (message.length() == totalMessageLength) {
			String action = message.substring(0, actionMessageLength);
			int value = Integer.parseInt(message.substring(actionMessageLength));
			boolean couldExecute = true;
			
			if (action.equals(turnToMessage)) {
				
				couldExecute = tryTurnToMessage(value);
			} else if (action.equals(turnByMessage)) {
				couldExecute = tryTurnByMessage(value);
			} else {
				if (messageMemory.hasSavedMessage()) {
					actOnBTMessage(messageMemory.getMessage());
				}
			}
			
			if (!couldExecute) {
				messageMemory.saveMessage(message);
			} else {
				messageMemory.clearSavedMessage();
			}
		} else {
			LCDHandler.displayMessage("UNK BT Message", message);
		}
	}
	
	private boolean tryTurnToMessage(int value) {
		
		if (proximity.canTurn(value)) {
			direction.rotateDirectionTo(value, true);
			return true;
		} else {
			return false;
		}
	}
	
	private boolean tryTurnByMessage(int value) {
		
		if (proximity.canTurn(value)) {
			direction.rotateDirectionBy(value, true);
			return true;
		} else {
			return false;
		}
	}
}

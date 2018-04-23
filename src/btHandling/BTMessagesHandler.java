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
		
		boolean valid = validMessage(message);
		if (valid) {
			carryOutNecessarySteps(message);
		} else {
			LCDHandler.displayMessage("UNK BT Message", message);
			tryActOnSavedMessage();
		}
	}
	
	private boolean validMessage(String message) {

		return message != null && message.length() == totalMessageLength && actionIsKnown(message) && valueisNumeric(message);
	}
	
	private boolean actionIsKnown(String message) {
		String action = extractActionFromBTMessage(message);
		return action.equals(turnToMessage) || action.equals(turnByMessage);
	}
	
	private String extractActionFromBTMessage(String btMessage) {
		return btMessage.substring(0, actionMessageLength);
	}
	
	private boolean valueisNumeric(String message)
	{

		String value = extractValueFromBTMessage(message);
		for (int i=0; i<value.length(); i++) {
			
			char c = value.charAt(i);
			if (c != '-' && (c < '0' || c > '9')) {
				return false;
			}
		}
		return true;
	}
	
	private String extractValueFromBTMessage(String btMessage) {
		
		return btMessage.substring(actionMessageLength).trim();
	}
	
	private void tryActOnSavedMessage() {
		
		if (messageMemory.hasSavedMessage()) {
			carryOutNecessarySteps(messageMemory.getMessage());
		}
	}
	
	private void carryOutNecessarySteps(String message) {
		
		boolean couldExecute = false;
		String action = extractActionFromBTMessage(message);
		int value = Integer.parseInt(extractValueFromBTMessage(message));
		
		if (action.equals(turnToMessage)) {
			couldExecute = tryTurnToMessage(value);
		} else if (action.equals(turnByMessage)) {
			couldExecute = tryTurnByMessage(value);
		}
		
		if (!couldExecute) {
			LCDHandler.displayMessage("SAVED BT Message", message);
			messageMemory.saveMessage(message);
		} else {
			LCDHandler.displayMessage("EXEC BT Message", message);
			messageMemory.clearSavedMessage();
		}
	}
	
	private boolean tryTurnToMessage(int value) {
		
		if (proximity.canTurn(value)) {
			direction.rotateDirectionTo(value, true);
			return true;
		}
		return direction.couldCorrectDirection(value);
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

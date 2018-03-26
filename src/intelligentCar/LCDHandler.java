package intelligentCar;

import lejos.nxt.Button;
import lejos.nxt.LCD;

public class LCDHandler {
	
	public static void displayMessage(String title, String message) {
		LCD.clear();
		LCD.drawString(title, 0, 0);
		LCD.drawString(message, 0, 1);
	}

	public static void notifyException(Exception ex) {
		displayMessage("Exception", ex.toString());
	}
	
	public static void displayUntilButtonPressed(String title, String message) {
		displayMessage(title, message);
		
		Button.waitForAnyPress();
	}
	
	public static void displayAsDebug(String message, int lcdRow) {
	
		LCD.drawString(message, 0, lcdRow);
	}
}

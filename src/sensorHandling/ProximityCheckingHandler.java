package sensorHandling;
import intelligentCar.LCDHandler;
import lejos.nxt.SensorPort;
import lejos.nxt.UltrasonicSensor;


public class ProximityCheckingHandler {

	UltrasonicSensor sonicLeft;
	UltrasonicSensor sonicRight;
	ObstructionMemory obstructions;
	
	int lastKnownLeftFreeDistance;
	int lastKnownRightFreeDistance;
	
	private final int minFreeTurnDistance = 50;
	
	private static ProximityCheckingHandler instance;
	
	private ProximityCheckingHandler() {
		sonicLeft = new UltrasonicSensor(SensorPort.S3);
		sonicRight = new UltrasonicSensor(SensorPort.S4);
		
		obstructions = new ObstructionMemory();
		
		sonicLeft.continuous();
		sonicRight.continuous();
		
		lastKnownRightFreeDistance = 255;
		lastKnownLeftFreeDistance = 255;
	}
	
	public static ProximityCheckingHandler getInstance() {
		if (instance == null) {
			instance = new ProximityCheckingHandler();
		}
		return instance;
	}
	
	public int getRightFreeDistance() {
		return sonicRight.getDistance();
	}
	
	public int getLeftFreeDistance() {
		return sonicLeft.getDistance();
	}
	
	public boolean canTurnRight() {
		
		return sonicRight.getDistance() >= minFreeTurnDistance && obstructions.noRightObstruction();
	}
	
	public boolean canTurnLeft() {
		
		return sonicLeft.getDistance() >= minFreeTurnDistance && obstructions.noLeftObstruction();
	}
	
	public int getAproximateFrontFreeDistance() {
		
		int rightAprox = (lastKnownRightFreeDistance + sonicRight.getDistance()) / 2;
		int leftAprox = (lastKnownLeftFreeDistance + sonicLeft.getDistance()) / 2;
		
		return (leftAprox + rightAprox) / 2;
	}
	
	public void checkForNewObstructions(int currentSpeed) {
		
		int leftDistance = sonicLeft.getDistance();
		int rightDistance = sonicRight.getDistance();
		
		//TODO Remove debug lines
				LCDHandler.displayAsDebug("IRLLef " + leftDistance + "  ", 4);
				LCDHandler.displayAsDebug("IRLRig " + rightDistance + "  ", 5);
		
		obstructions.checkIfObstructionsMayBeForgotten(currentSpeed);
		
		if (leftDistance < minFreeTurnDistance) {
			obstructions.addLeftObstruction(leftDistance);
		}
		
		if (rightDistance < minFreeTurnDistance) {
			obstructions.addRightObstruction(rightDistance);
		}
		
		lastKnownLeftFreeDistance = leftDistance;
		lastKnownRightFreeDistance = rightDistance;
	}

	public boolean canTurn(int value) {
		
		if (value < 0) {
			return canTurnLeft();
		} else {
			return canTurnRight();
		}
	}
}

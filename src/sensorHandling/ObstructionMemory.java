package sensorHandling;

import intelligentCar.LCDHandler;

public class ObstructionMemory {
	
	int rightObstacleAproxDistance;
	int leftObstacleAproxDistance;
	
	final float speedIntoDistanceReducer = 0.5f;
	final int distanceWhenNoObstacleInView = 0;
	
	public ObstructionMemory() {
		clearMemory();
	}

	public boolean noRightObstruction() {
		return distanceWhenNoObstacleInView >= rightObstacleAproxDistance;
	}

	public boolean noLeftObstruction() {
		return distanceWhenNoObstacleInView >= leftObstacleAproxDistance;
	}

	public void addRightObstruction(int rightDistance) {
		rightObstacleAproxDistance = rightDistance;
		
	}

	public void addLeftObstruction(int leftDistance) {
		leftObstacleAproxDistance = leftDistance;
		
	}

	public void checkIfObstructionsMayBeForgotten(int currentSpeed) {
		
		if (rightObstacleAproxDistance > 0) {
			rightObstacleAproxDistance -= currentSpeed * speedIntoDistanceReducer;
		}
		
		if (leftObstacleAproxDistance > 0) {
			leftObstacleAproxDistance -= currentSpeed * speedIntoDistanceReducer;
		}
		 
		//TODO Remove debug lines
		LCDHandler.displayAsDebug(leftObstacleAproxDistance + "  ", 6);
		LCDHandler.displayAsDebug(rightObstacleAproxDistance + "  ", 7);
	}
	
	public void clearMemory() {
		rightObstacleAproxDistance = distanceWhenNoObstacleInView;
		leftObstacleAproxDistance = distanceWhenNoObstacleInView;
	}
}

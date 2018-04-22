package sensorHandling;

import intelligentCar.LCDHandler;

public class ObstructionMemory {
	
	int rightObstacleAproxDistance;
	int leftObstacleAproxDistance;
	
	final float speedIntoDistanceReducer = 0.05f;
	final int distanceWhenNoObstacleInView = -1;
	
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
		rightObstacleAproxDistance = Math.max(rightDistance, rightObstacleAproxDistance);
		
	}

	public void addLeftObstruction(int leftDistance) {
		leftObstacleAproxDistance = Math.max(leftDistance, leftObstacleAproxDistance);;
		
	}

	public void checkIfObstructionsMayBeForgotten(int currentSpeed) {
		
		if (rightObstacleAproxDistance > distanceWhenNoObstacleInView) {
			rightObstacleAproxDistance -= currentSpeed * speedIntoDistanceReducer;
		}
		
		if (leftObstacleAproxDistance > distanceWhenNoObstacleInView) {
			leftObstacleAproxDistance -= currentSpeed * speedIntoDistanceReducer;
		}
		 
		//TODO Remove debug lines
		LCDHandler.displayAsDebug("MEMLef " + leftObstacleAproxDistance + "  ", 6);
		LCDHandler.displayAsDebug("MEMRig " + rightObstacleAproxDistance + "  ", 7);
	}
	
	public void clearMemory() {
		rightObstacleAproxDistance = distanceWhenNoObstacleInView;
		leftObstacleAproxDistance = distanceWhenNoObstacleInView;
	}
}

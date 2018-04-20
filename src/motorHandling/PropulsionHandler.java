package motorHandling;
import lejos.nxt.Motor;
import lejos.nxt.NXTRegulatedMotor;


public class PropulsionHandler {
	
	final int minSpeed = 360;
	final int maxSpeed = 720;
	final int rotationDirection = -1;
	final int frontMethodRotationDirection = 1;
	final int minDistanceToMaintain = 10;
	final int maxDistanceToAccountFor = 30;
	
	NXTRegulatedMotor leftMotor; 	//B Motor (backwards to go)
	NXTRegulatedMotor rightMotor;	//C Motor (backwards to go)
	
	private static PropulsionHandler instance;
	
	public static PropulsionHandler getInstance() {
		
		if (instance == null) {
			instance = new PropulsionHandler();
		}
		return instance;
	}
	
	public void setSpeed(int newSpeed) {
		newSpeed = Math.min(Math.max(minSpeed, newSpeed), Math.min(newSpeed, maxSpeed));
		leftMotor.setSpeed(newSpeed);
		rightMotor.setSpeed(newSpeed);
	}
	
	public void setMinSpeed(int newMaxSpeed) {
		leftMotor.setSpeed(newMaxSpeed);
		rightMotor.setSpeed(newMaxSpeed);
	}
	
	public int getSpeed() {
		return leftMotor.getSpeed();
	}
	
	private void assignMotors() {
		leftMotor = Motor.B;
		rightMotor = Motor.C;
	}
	
	private PropulsionHandler(int maxSpeed) {
		
		assignMotors();
		setSpeed(maxSpeed);
	}
	
	private PropulsionHandler() {
		
		assignMotors();
	}

	public void accelerateAcoordingToDistance(int distance) {
		
		if (minDistanceToMaintain >= distance) {
			stopMotors();
			return;
		} else { 
			int speed = maxSpeed;
			accelerateNormally();
			if (distance <= maxDistanceToAccountFor) {
				int percentage = (distance - minDistanceToMaintain) / (maxDistanceToAccountFor - minDistanceToMaintain);
				speed = minSpeed + ((maxSpeed - minSpeed) * percentage);
			}
			setSpeed(speed);
		}
		
	}
	
	public void accelerateNormally() {
		
		if (frontMethodRotationDirection == rotationDirection) {
			leftMotor.forward();
			rightMotor.forward();
		} else {
			leftMotor.backward();
			rightMotor.backward();
		}
	}
	
	public void stopMotors() {
		leftMotor.stop();
		rightMotor.stop();
	}
}

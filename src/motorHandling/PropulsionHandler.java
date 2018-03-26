package motorHandling;
import lejos.nxt.Motor;
import lejos.nxt.NXTRegulatedMotor;


public class PropulsionHandler {
	
	int normalRotation;
	final int defaultRotationAmount = 20;
	final int rotationDirection = -1;
	final int minDistanceToMaintain = 5;
	final int maxDistanceToAccountFor = 15;
	
	NXTRegulatedMotor leftMotor; 	//B Motor (backwards to go)
	NXTRegulatedMotor rightMotor;	//C Motor (backwards to go)
	
	private static PropulsionHandler instance;
	
	public void configureMovement(int maxSpeed, int normalRot) {
		
		setMaxSpeed(maxSpeed);
		setRotationAmount(normalRot);
	}
	
	public static PropulsionHandler getInstance() {
		
		if (instance == null) {
			instance = new PropulsionHandler();
		}
		return instance;
	}
	
	public void setMaxSpeed(int newMaxSpeed) {
		leftMotor.setSpeed(newMaxSpeed);
		rightMotor.setSpeed(newMaxSpeed);
	}
	
	public void setRotationAmount(int newNormalRot) {
		normalRotation = newNormalRot;
	}
	
	public int getMaxSpeed() {
		return leftMotor.getSpeed();
	}
	
	public int getRotationAmount() {
		return normalRotation;
	}
	
	private void assignMotors() {
		leftMotor = Motor.B;
		rightMotor = Motor.C;
	}
	
	private PropulsionHandler(int maxSpeed, int normalRot) {
		
		assignMotors();
		configureMovement(maxSpeed, normalRot);
	}
	
	private PropulsionHandler() {
		
		assignMotors();
		setRotationAmount(defaultRotationAmount);
	}

	public void accelerateAcoordingToDistance(int distance) {
		
		int rot = normalRotation;
		
		if (minDistanceToMaintain >= distance) {
			stopMotors();
			return;
		} else if (distance <= maxDistanceToAccountFor) {
			int percentage = distance / maxDistanceToAccountFor;
			rot = rot * percentage;
		}
		
		leftMotor.rotate(rot, true);
		rightMotor.rotate(rot, true);
	}
	
	public void accelerateNormally() {
		
		int rot = getNormalRotationInCorrectDirection();
		
		leftMotor.rotate(rot, true);
		rightMotor.rotate(rot, true);
	}
	
	public void stopMotors() {
		leftMotor.stop();
		rightMotor.stop();
	}
	
	private int getNormalRotationInCorrectDirection() {
		return normalRotation * rotationDirection;
	}
}

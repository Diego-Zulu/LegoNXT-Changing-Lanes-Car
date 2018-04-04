package motorHandling;
import lejos.nxt.Motor;
import lejos.nxt.NXTRegulatedMotor;


public class DirectionHandler {

	int normalRotation;
	NXTRegulatedMotor directionMotor; 	//A Motor (negative is left)
	private static DirectionHandler instance;
	
	final int maxAngle = 90; //Breaks direction if turned further
	final int maxRouteCorrectionAngle = 10;
	
	public void setMaxSpeed(int newMaxSpeed) {
		directionMotor.setSpeed(newMaxSpeed);
	}
	
	public void setRotationAmount(int newNormalRot) {
		normalRotation = newNormalRot;
	}
	
	public void configureMovement(int maxSpeed, int normalRot) {
		
		setMaxSpeed(maxSpeed);
		setRotationAmount(normalRot);
	}
	
	public int getDirectionPos() {
		return directionMotor.getPosition();
	}
	
	public boolean canCorrectDirection(int angle) {
		
		if (angle + getDirectionPos() <= maxRouteCorrectionAngle 
				&& angle + getDirectionPos() >= -maxRouteCorrectionAngle) {
			
			directionMotor.rotate(angle, true);
			return true;
		} 
		return false;
	}
	
	public static DirectionHandler getInstance() {
		if (instance == null) {
			instance = new DirectionHandler();
		}
		return instance;
	}
	
	private void assignMotor() {
		directionMotor = Motor.A;
		directionMotor.rotateTo(0);
	}
	
	private DirectionHandler(int maxSpeed, int normalRot) {
		
		assignMotor();
		configureMovement(maxSpeed, normalRot);
	}
	
	private DirectionHandler() {
		
		assignMotor();
	}
	
	public void rotateDirectionTo(int angle, boolean immediateReturn) {
		
		directionMotor.rotateTo(limitAngleToPreventBreaking(angle), immediateReturn);
	}
	
	private int limitAngleToPreventBreaking(int angle) {
		
		if (Math.abs(angle) > maxAngle) {
			
			if (angle < 0) {
				angle = -maxAngle;
			} else {
				angle = maxAngle;
			}
		}
		
		return angle;
	}
	
	public void rotateDirectionBy(int angle, boolean immediateReturn) {
		
		int limited = limitAngleToRotateBy(angle, directionMotor.getPosition());
		
		directionMotor.rotate(limited, immediateReturn);
	}
	
	private int limitAngleToRotateBy(int rotateBy, int actualPos) {
		
		if (Math.abs(rotateBy + actualPos) > maxAngle) {
			
			if (rotateBy < 0) {
				rotateBy = -maxAngle - rotateBy;
			} else {
				rotateBy = maxAngle - rotateBy;
			}
		}
		
		return rotateBy;
	}
	
	
	public void stopDirection() {
		directionMotor.stop();
	}
}

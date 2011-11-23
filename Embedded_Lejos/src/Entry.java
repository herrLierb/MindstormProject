import java.util.Random;

import lejos.nxt.Button;
import lejos.nxt.Motor;
import lejos.nxt.NXTRegulatedMotor;

public class Entry {

	private final NXTRegulatedMotor steeringMotor;
	private final NXTRegulatedMotor accelMotor;
	private int maxSteeringAngle = 55;

	public Entry() {
		accelMotor = Motor.A;
		steeringMotor = Motor.B;
//		int steeringAngle = (new Random()).nextInt(2 * maxSteeringAngle)
//				- maxSteeringAngle;

		int steeringAngle = 15;
		steeringMotor.rotate(steeringAngle, false);
		System.out.println("Steered " + steeringAngle + " deg");
		float maxSpeed = accelMotor.getMaxSpeed();
		System.out.println("MaxSpeed " + maxSpeed);
//		accelMotor.setSpeed(maxSpeed);
//		accelMotor.forward();
	}

	public static void main(String[] args) {
		System.out.println("Hello.");
		Button.waitForPress();

		new Entry();

		
		System.out.println("Baam.");

		Button.waitForPress();

	}

}

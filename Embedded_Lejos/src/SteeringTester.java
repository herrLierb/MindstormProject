import lejos.nxt.Button;
import lejos.nxt.Motor;
import lejos.nxt.NXTRegulatedMotor;

public class SteeringTester {

	private final NXTRegulatedMotor steeringMotor;

	public SteeringTester() {

		steeringMotor = Motor.B;
		
		steeringMotor.setSpeed(10f);
		steeringMotor.rotateTo(55);
		
		Button.waitForPress();
		steeringMotor.setSpeed(20f);
		steeringMotor.rotateTo(-55);
		
		Button.waitForPress();
		steeringMotor.setSpeed(30f);
		steeringMotor.rotateTo(0);
		
		System.out.println("deg: " + steeringMotor.getPosition());

	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		System.out.println("Hello.");
		Button.waitForPress();

		new SteeringTester();

		System.out.println("Baam.");

		Button.waitForPress();

	}
}

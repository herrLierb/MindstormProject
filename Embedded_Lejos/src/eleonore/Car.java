package eleonore;
import lejos.nxt.Motor;
import lejos.nxt.NXTRegulatedMotor;


public abstract class Car {

	protected NXTRegulatedMotor steering = Motor.B;
	protected NXTRegulatedMotor engine = Motor.A;
	
	protected int MAX_STEERING_DEG = 55;
	protected float STEERING_SPEED = 100;
	
	public Car() {
		steering.setSpeed(STEERING_SPEED);
		engine.setSpeed(engine.getMaxSpeed());
	}
	
	
	protected void steer(int degs) {
		steering.rotateTo((Math.abs(degs) > MAX_STEERING_DEG ? MAX_STEERING_DEG * (int)Math.signum(degs): degs), false);
	}
	
	protected void go() {
		engine.backward();
	}
	
}

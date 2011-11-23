package gregor;

import lejos.nxt.ColorSensor;
import lejos.nxt.Motor;
import lejos.nxt.NXTRegulatedMotor;
import lejos.nxt.SensorPort;

public class GregorBase {

	protected NXTRegulatedMotor left = Motor.C;
	protected NXTRegulatedMotor right = Motor.A;
	protected ColorSensor colorSensor = new ColorSensor(SensorPort.S4);
	
	private float speed;
	
	public GregorBase(float speed) {
		this.speed = speed;
	}
	
	public GregorBase() {
		speed = Math.min(left.getMaxSpeed(), right.getMaxSpeed());
	}
	
	private void forward() {
		left.backward();
		right.backward();
	}
	
	private void fullSpeed() {
		setSpeed(Math.min(left.getMaxSpeed(), right.getMaxSpeed()));
	}

	protected void straight() {
		fullSpeed();
		forward();
	}
	
	protected void straight(float speed) {
		setSpeed(speed);
		forward();
	}
	
	protected void setSpeed(float speed) {
		this.speed = speed;
		left.setSpeed(speed);
		right.setSpeed(speed);
	}

	protected void turnRight(float intensity) {
		left.setSpeed(speed-(intensity*speed));
		right.setSpeed(speed);
		forward();
	}

	protected void turnLeft(float intensity) {
		right.setSpeed(speed-(intensity*speed));
		left.setSpeed(speed);
		forward();
	}
	
	protected void turnLeftOnPlace() {
		fullSpeed();
		right.forward();
		left.backward();
	}
	
	protected void turnRightOnPlace() {
		fullSpeed();
		right.backward();
		left.forward();
	}

	protected void stop() {
		left.stop();
		right.stop();
	}
	
	public int readLightValue() {
		return colorSensor.getRawLightValue();
	}

	protected void sleep(int millis) {
		try {
			Thread.sleep(millis);
		} catch (InterruptedException e) {
		}
	}
}

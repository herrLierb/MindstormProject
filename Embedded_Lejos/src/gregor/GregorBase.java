package gregor;

import lejos.nxt.ColorSensor;
import lejos.nxt.Motor;
import lejos.nxt.NXTRegulatedMotor;
import lejos.nxt.SensorPort;
import lejos.robotics.navigation.DifferentialPilot;
import lejos.util.PIDController;

public abstract class GregorBase {

	private final static double WHEEL_DIAMETER = 43.2;
	private final static double TRACK_WIDTH = 179.1;
	protected final static ColorSensor colorSensor = new ColorSensor(SensorPort.S4);

	protected int black;
	protected int white;
	protected int targetSensorValue;
	private double speed;

	private final DifferentialPilot pilot;

	private final PIDController pid;


	public GregorBase(int black, int white, float kp, float ki, float kd) {
		this.black = black;
		this.white = white;
		this.targetSensorValue = calcTarget(black, white);
		
		this.pilot = new DifferentialPilot(WHEEL_DIAMETER, TRACK_WIDTH,
				Motor.A, Motor.C, true);
		
		this.pid = new PIDController(targetSensorValue); //try to get to optimal grayscale
		this.pid.setPIDParam(PIDController.PID_KP, kp);
		this.pid.setPIDParam(PIDController.PID_KI, ki);
		this.pid.setPIDParam(PIDController.PID_KD, kd);
		//set deadband
		this.pid.setPIDParam(PIDController.PID_DEADBAND, 20.0f);
		
		//according to steer
		this.pid.setPIDParam(PIDController.PID_LIMITHIGH, 200);
		this.pid.setPIDParam(PIDController.PID_LIMITLOW, -200);
		
		speed = pilot.getMaxTravelSpeed();
	}

	private void forward() {
		pilot.forward();
	}

	private void fullSpeed() {
		setSpeed(pilot.getMaxTravelSpeed());
	}

	protected void straight() {
		fullSpeed();
		forward();
	}

	protected void straight(double speed) {
		setSpeed(speed);
		forward();
	}

	protected void setSpeed(double speed) {
		pilot.setTravelSpeed(speed);
	}

	/**
	 * Starts the robot moving forward along a curved path. This method is
	 * similar to the {@link DifferentialPilot#arcForward(double radius)} method
	 * except it uses the turnRate parameter do determine the curvature of the
	 * path and therefore has the ability to drive straight. This makes it
	 * useful for line following applications.
	 * <p>
	 * The turnRate specifies the sharpness of the turn. Use values between -200
	 * and +200. <br/>
	 * A negative value means that center of the turn is on the left. If the
	 * robot is traveling toward the top of the page the arc looks like this: ).
	 * A positive value means that center of the turn is on the right so the arc
	 * liiks this: (. . In this class, this parameter determines the ratio of
	 * inner wheel speed to outer wheel speed as a percent.<br/>
	 * <i>Formula:</i> <code>ratio = 100 - abs(turnRate)</code>. <br/>
	 * When the ratio is negative, the outer and inner wheels rotate in opposite
	 * directions. Examples of how the formula works:
	 * <ul>
	 * <li>
	 * <code>steer(0)</code> -> inner and outer wheels turn at the same speed,
	 * travel straight
	 * <li>
	 * <code>steer(25)</code> -> the inner wheel turns at 75% of the speed of
	 * the outer wheel, turn left
	 * <li>
	 * <code>steer(100)</code> -> the inner wheel stops and the outer wheel is
	 * at 100 percent, turn left
	 * <li>
	 * <code>steer(200)</code> -> the inner wheel turns at the same speed as the
	 * outer wheel - a zero radius turn.
	 * </ul>
	 * 
	 * @param turnRate
	 *            Range: [-200; +200] If positive, the left side of the robot is on the inside of
	 *            the turn. If negative, the left side is on the outside.
	 * 
	 */
	protected void steer(double turnRate) {
		pilot.steer(turnRate);
	}

	protected void stop() {
		pilot.stop();
	}

	public static int readLightValue() {
		return colorSensor.getRawLightValue();
	}

	public static void sleep(int millis) {
		try {
			Thread.sleep(millis);
		} catch (InterruptedException e) {
		}
	}
	
	public int doPID(int currentValue) {
		return pid.doPID(currentValue);
	}
	

	public int getBlack() {
		return black;
	}

	public void setBlack(int black) {
		this.black = black;
	}

	public int getWhite() {
		return white;
	}

	public void setWhite(int white) {
		this.white = white;
	}

	public abstract int calcTarget(int black, int white);
}

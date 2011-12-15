package alfred;

import lejos.nxt.ColorSensor;
import lejos.nxt.ColorSensor.Color;
import lejos.nxt.Motor;
import lejos.nxt.SensorPort;
import lejos.robotics.navigation.DifferentialPilot;

public class Alfred {

	// Set parameters here: --------------------------------------------

	private double Kc = 0.33;
	private double dt = 0.004198;
	private double Kp = 0.33; // 0.6 * Kc; // 0.32; //0.3, 0.21, 0.29; // 0.35
	private double Ki = 0.65 * Kp; // 2 * Kp * dt; // 0.65 * Kp; // 0.0; // 0.5
	private double Kd = 4.5 * Kp; // Kp * 1/(8 * dt); // 4.5 * Kp; //

	private double speedFactor = 0.55; // 0.5
	private double optimumShift = 0.7;

	private double maxIntegral = 1.0;
	private double minIntegral = -1.0;

	// -----------------------------------------------------------------


	private double errorIntegral = 0.0;
	private double errorDifferential = 0.0;

	private final double WHEEL_DIAMETER = 43.2;
	private final double TRACK_WIDTH = 179.1;
	private final DifferentialPilot myPilot;

	private ColorSensor colorSensor1 = new ColorSensor(SensorPort.S4, Color.RED);

	// high value corresponds to white, low value corresponds to black
	private int highReadInValue = 0;
	private int lowReadInValue = 0;

	public Alfred() {
		myPilot = new DifferentialPilot(this.WHEEL_DIAMETER, this.TRACK_WIDTH,
				Motor.A, Motor.C, true);
		myPilot.setTravelSpeed(myPilot.getMaxTravelSpeed() * speedFactor);

		this.colorSensor1.setFloodlight(true);
	}

	public void run() {
		double currentError, lastError = 0.0;
		double u;
		
		long timeStamp = System.currentTimeMillis();
		int count = 0;
		
		while (true) {
			currentError = this.getCurrentPercentageError();
			if (errorIntegral > 0) {
				this.errorIntegral = Math.min(this.maxIntegral,
						this.errorIntegral + currentError);
			} else {
				this.errorIntegral = Math.max(this.minIntegral,
						this.errorIntegral + currentError);
			}

			this.errorDifferential = (currentError - lastError); // dt;

			u = (Kp * currentError) + (Ki * this.errorIntegral)
					+ (Kd * this.errorDifferential);
			if (u < -1.0) {
				u = -1.0;
				// System.out.println("----> too low");
			} else if (u > 1.0) {
				// System.out.println("----> too high");
				u = 1.0;
			}
			
			if(u > -0.05 && u < 0.05) {
				u = 0;
			}
			
			// System.out.println(u);

			myPilot.steer((int) (u * 200.0));

			lastError = currentError;
			
			if(count < 10001) {
				count ++;
			}
				
			if(count == 10000) {
				System.out.println(System.currentTimeMillis() - timeStamp);
			} 
			
		}
	}

	// if the error is lower than 0, it's too white, if it's bigger than 0, it's
	// too black.
	public double getCurrentPercentageError() {
		double returnValue = 0.0;
		int currentValue = this.readSensorValue();
		int optimum = getOptimumSensorValue(currentValue);
		if (currentValue > optimum) {
			returnValue = (double) (currentValue - optimum)
					/ (double) (this.highReadInValue - optimum);
		} else if (currentValue < optimum) {
			returnValue = -((double) (optimum - currentValue) / (double) (optimum - this.lowReadInValue));
		}
		return returnValue;
	}

	// Alfred is optimizing the optimumSensorValue while driving.
	public int getOptimumSensorValue(int currentValue) {
		this.highReadInValue = Math.max(this.highReadInValue, currentValue);
		this.lowReadInValue = Math.min(this.lowReadInValue, currentValue);
		double diff = this.highReadInValue - this.lowReadInValue;
		return (int) (this.lowReadInValue + optimumShift * diff);
	}

	public void setHighReadInValue(int value) {
		this.highReadInValue = value;
	}

	public void setLowReadInValue(int value) {
		this.lowReadInValue = value;
	}

	public int readSensorValue() {
		return this.colorSensor1.getRawLightValue();
	}
}

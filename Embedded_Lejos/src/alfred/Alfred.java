package alfred;

import java.util.Queue;

import lejos.nxt.ColorSensor;
import lejos.nxt.ColorSensor.Color;
import lejos.nxt.Motor;
import lejos.nxt.SensorPort;
import lejos.robotics.navigation.DifferentialPilot;

public class Alfred {
	
	// Set parameters here: --------------------------------------------
	
	private double Kp = 0.21; // 0.29;
	private double Ki = 0.6 * Kp; // 0.0;
	private double Kd = 4.5 * Kp; //
	
	private int speedDecrease = 0;
	
	private double maxIntegral = 1.0;
	private double minIntegral = -1.0;
	
	// -----------------------------------------------------------------
	
	// U has to be between -1.0 and 1.0
	private double U = 0;
	
	private double currentError = 0.0;
	private double errorIntegral = 0.0;
	private double lastError = 0.0;
	private double errorDifferential = 0.0;
	
	private final double WHEEL_DIAMETER = 43.2;
	private final double TRACK_WIDTH = 179.1;
	private final DifferentialPilot myPilot;
	
	private ColorSensor colorSensor1 = new ColorSensor(SensorPort.S4, Color.RED);
	private int sensorValue;
	
	// high value corresponds to white, low value corresponds to black
	private int highReadInValue = 0;
	private int lowReadInValue = 0;
	
	public Alfred() {
		myPilot = new DifferentialPilot(this.WHEEL_DIAMETER, this.TRACK_WIDTH, Motor.A, Motor.C, true);
		myPilot.setTravelSpeed(myPilot.getMaxTravelSpeed() - speedDecrease);
		
		this.colorSensor1.setFloodlight(true);
	}
	
	public void run() {
		while(true) {
			this.currentError = this.getCurrentPercentageError();
			if(errorIntegral > 0) {
				this.errorIntegral = Math.min(this.maxIntegral, this.errorIntegral + this.currentError);
			} else {
				this.errorIntegral = Math.max(this.minIntegral, this.errorIntegral + this.currentError);
			}
			
			this.errorDifferential = this.currentError - this.lastError;
		
			this.U = (Kp * this.currentError) + (Ki * this.errorIntegral) + (Kd * this.errorDifferential);
			if(U < -1.0) {
				U = -1.0;
			} else if(U > 1.0) {
				U = 1.0;
			}
			
			myPilot.steer((int)(U * 200.0));
			
			// System.out.println(this.getOptimumSensorValue() + " - " + U);
			
			this.lastError = this.currentError;  
		}
	}
	
	// if the error is lower than 0, it's too white, if it's bigger than 0, it's too black.
	public double getCurrentPercentageError() {
		double returnValue = 0.0;
		if(this.readSensorValue() > this.getOptimumSensorValue()) {
			returnValue = (double)(this.readSensorValue() - this.getOptimumSensorValue()) / (double)(this.highReadInValue - this.getOptimumSensorValue());
		} else if(this.readSensorValue() < this.getOptimumSensorValue()) {
			returnValue = -((double)(this.getOptimumSensorValue() - this.readSensorValue()) / (double)(this.getOptimumSensorValue() - this.lowReadInValue));
		}
		return returnValue;
	}
	
	// Alfred is optimizing the optimumSensorValue while driving.
	public int getOptimumSensorValue() {
		this.highReadInValue = Math.max(this.highReadInValue, this.readSensorValue());
		this.lowReadInValue = Math.min(this.lowReadInValue, this.readSensorValue());
		return (int) Math.round( ( (double)(this.highReadInValue) + (double)(this.lowReadInValue) ) / 2.0 );
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

package gregor;

import lejos.robotics.subsumption.Behavior;

/**
 * {@link LineFollowBehavior} is the main behavior guiding
 * {@link GregorBase} while following the line.
 * @author Christian Breil
 *
 */
public class LineFollowBehavior implements Behavior{

	private static float SPEED = 500;
	
	// TODO: SensorDaten per Benutzereingabe abfragen!
	private static int sensorOptimum = 260;
	private static int sensorMax = 520;
	
	private static float integral = 0f; 
	
	private static float kp = 0.7f;
			
	private GregorBase puppet;
	private boolean suppressed = false;

	public LineFollowBehavior(GregorBase puppet) {
		this.puppet = puppet;
		this.puppet.setSpeed(SPEED);
	}

	@Override
	public void action() {
		suppressed = false;
		while(!suppressed){
			int lightValue = puppet.readLightValue();
			System.out.println("light value: " + lightValue);
			if (lightValue > sensorOptimum) {
				float intensity = ((float)(lightValue - sensorOptimum)/(float)(sensorMax - sensorOptimum))*kp;
				if(intensity > 1) intensity = 1;
				// integral:
				integral += (float)(lightValue-sensorOptimum);
				puppet.turnLeft(intensity);
				//System.out.println("Left: " + intensity);
			} else if (lightValue < sensorOptimum){
				float intensity = ((float)(sensorOptimum - lightValue)/(float)(sensorOptimum))*kp;
				if(intensity > 1) intensity = 1;
				// integral
				integral += (float)(lightValue-sensorOptimum);
				puppet.turnRight(intensity);
				//System.out.println("Right: " + intensity);
			} else {
				puppet.straight(SPEED);
			}
		}
	}

	@Override
	public void suppress() {
		suppressed = true;
	}

	@Override
	public boolean takeControl() {
		return true;
	}

}

package gregor;

import lejos.robotics.subsumption.Behavior;

/**
 * {@link LineFollowBehavior} is the main behavior guiding {@link GregorBase}
 * while following the line.
 * 
 */
public class LineFollowBehavior implements Behavior {

	private static final float SPEED = 150;

	private GregorBase puppet;
	private boolean suppressed = false;

	public LineFollowBehavior(GregorBase puppet) {
		this.puppet = puppet;
		this.puppet.setSpeed(SPEED);
	}

	@Override
	public void action() {
		suppressed = false;
		int controlValue;
		while (!suppressed) {
			controlValue = puppet.doPID(GregorBase.readLightValue());
			System.out.println(controlValue);
			puppet.steer(controlValue);
			
//			 int lightValue = puppet.readLightValue();
//			 if (lightValue > sensorOptimum) {
//			 float intensity = ((float)(lightValue -
//			 sensorOptimum)/(float)(puppet.black - sensorOptimum))*kp;
//			 if(intensity > 1) intensity = 1;
//			 // integral:
//			 integral += (float)(lightValue-sensorOptimum);
//			 puppet.turnLeft(intensity);
//			 } else if (lightValue < sensorOptimum){
//			 float intensity = ((float)(sensorOptimum -
//			 lightValue)/(float)(sensorOptimum))*kp;
//			 if(intensity > 1) intensity = 1;
//			 // integral
//			 integral += (float)(lightValue-sensorOptimum);
//			 puppet.turnRight(intensity);
//			 } else {
//			 puppet.straight(SPEED);
//			 }
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

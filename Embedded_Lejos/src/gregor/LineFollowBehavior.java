package gregor;

import lejos.nxt.Sound;
import lejos.robotics.subsumption.Behavior;

/**
 * {@link LineFollowBehavior} is the main behavior guiding {@link GregorBase}
 * while following the line.
 * 
 */
public class LineFollowBehavior implements Behavior {
	
	long start;
	
	private GregorBase puppet;
	private boolean suppressed = false;

	public LineFollowBehavior(GregorBase puppet) {
		this.puppet = puppet;
	}
	
	@Override
	public void action() {
		suppressed = false;
		int controlValue;
		float pValue = 0.1f;
		float iValue = 0.00281282f;
		float dValue = 3.91954847f;
		int iLimit = -10;
		int testSpeed = 0;
		
		
		for(int i=0; i<9; i++) {
			pValue = pValue + 0.05f;
			// iLimit = iLimit + 10;
			// testSpeed = testSpeed + 25;
			
			puppet.setParameters(0.5f, 0.0f, 0.0f);
			// puppet.setIParameters(iLimit, -1*iLimit);
			// puppet.setSpeed(testSpeed);
			
			start = System.currentTimeMillis();
			suppressed = false;
			Sound.playTone(440, 200);
			
			int error = 0;
			
			while (!suppressed) {
				if(System.currentTimeMillis() - start < 10000) {
					
//					error = ((puppet.getBlack() + puppet.getWhite()) / 2) - puppet.readLightValue();
					error = 325 - puppet.readLightValue();
					
//					controlValue = puppet.doPID(GregorBase.readLightValue());
					controlValue = (int)(0.4f * error);
					
//					controlValue = Math.round(((float)controlValue / 900.0f)*200);
					
					System.out.println(controlValue);
					puppet.steer(controlValue);
				} else {
					suppressed = true;
				}
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

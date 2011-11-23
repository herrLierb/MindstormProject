package gregor;

import lejos.nxt.Button;
import lejos.robotics.subsumption.Arbitrator;
import lejos.robotics.subsumption.Behavior;

public class Gregor extends GregorBase {
	
	public Gregor() {
		super();

		LineSearchBehavior lineSearchBehavior = new LineSearchBehavior();
		LineFollowBehavior lineFollowBehavior = new LineFollowBehavior(this);
		Arbitrator arbitrator = new Arbitrator(new Behavior[]{lineFollowBehavior, lineSearchBehavior}, false);
		arbitrator.start();
		
		/*straight();
		sleep(2000);
		turnRight();
		sleep(2000);
		straight();
		sleep(2000);
		turnLeft();
		sleep(2000);
		turnRightOnPlace();
		sleep(2000);
		turnLeftOnPlace();
		sleep(2000);
		stop();*/
	}
	
	
	public static void main(String[] args) {
		Button.waitForPress(500);
		
		new Gregor();
		System.out.println("Feddisch");
	}

}

package gregor;

import lejos.nxt.Button;
import lejos.nxt.Sound;
import lejos.robotics.subsumption.Arbitrator;
import lejos.robotics.subsumption.Behavior;

public class Gregor extends GregorBase {
	
	private Arbitrator arbitrator;
	private static final float KP = 0.0f;
	private static final float KI = 0.0f;
	private static final float KD = 0.0f;
	
	public Gregor(int black, int white) {
		super(black, white, KP, KI, KD);
	}
	
	public void init() {
		LineSearchBehavior lineSearchBehavior = new LineSearchBehavior();
		LineFollowBehavior lineFollowBehavior = new LineFollowBehavior(this);
		this.arbitrator = new Arbitrator(new Behavior[]{lineFollowBehavior, lineSearchBehavior}, false);
	}
	
	public void start() {
		arbitrator.start();
	}
	
	@Override
	public final int calcTarget(int black, int white) {
		return (int)((black + white) * 0.5f);
	}
	

	public static void main(String[] args) {
		int white = 0, black = 0;
		int grey = 0;
		
		//calibrate
//		System.out.println("Please press the orange Button and set me on a WHITE surface until bling!");
//		Button.waitForPress();
//		Gregor.sleep(2500);
//		
//		//sensorLesen
//		white = Gregor.readLightValue();
//		//bling
//		Sound.playTone(440, 200);
//		
//		System.out.println("Please press the orange Button and set me on a BLACK surface until bling!");
//		Button.waitForPress();
//		Gregor.sleep(2500);
//		black = Gregor.readLightValue();
//		
//		Sound.playTone(440, 200);
		
//		System.out.println("Please press the orange Button and set me on a GREY surface until bling!");
//		Button.waitForPress();
//		Gregor.sleep(2500);
//		grey = Gregor.readLightValue();
		
		Gregor greg = new Gregor(black, white);
		//init
		greg.init();
		
		//bling
		Sound.playTone(440, 200);
		
		
		System.out.println("Calibrated to:\nBlack: " + greg.black + "\nWhite: " + greg.white+ "\nGrey: " + grey);
		System.out.println("Press the Button to start the race.");
		Button.waitForPress();
		Gregor.sleep(1000);
		greg.start();
	}

}

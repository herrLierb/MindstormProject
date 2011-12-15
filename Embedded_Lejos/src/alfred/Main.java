package alfred;

import lejos.nxt.Button;
import lejos.nxt.Sound;

public class Main {

	public static void main(String[] args) throws InterruptedException {
		
		Alfred myAlfred = new Alfred();
		
		// Start here:
		// System.out.println("PRESS A BUTTON");
		// Button.waitForPress();
		
		// Read in the WHITE value:
		System.out.println("Place on WHITE");
		myAlfred.setHighReadInValue(myAlfred.readSensorValue());
		Sound.playTone(440, 200);
		
		// Read in the BLACK value:
		System.out.println("Place on BLACK");
		// Thread.sleep(3000);
		myAlfred.setLowReadInValue(myAlfred.readSensorValue());
		Sound.playTone(440, 200);
		
		System.out.println("OPTIMUM: " + myAlfred.getOptimumSensorValue());
		
		// Thread.sleep(4000);
		myAlfred.run();
	}

}

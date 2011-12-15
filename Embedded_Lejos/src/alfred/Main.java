package alfred;

import lejos.nxt.Button;
import lejos.nxt.Sound;

public class Main {

	public static void main(String[] args) throws InterruptedException {
		
		Alfred myAlfred = new Alfred();
		
		// Start here:
		// System.out.println("PRESS A BUTTON");
		
		// Read in the WHITE value:
		System.out.println("Place on WHITE");
		// Button.waitForPress();
		myAlfred.setHighReadInValue(myAlfred.readSensorValue());
		Sound.playTone(440, 200);
		
		// Read in the BLACK value:
		System.out.println("Place on BLACK");
		// Button.waitForPress();
		myAlfred.setLowReadInValue(myAlfred.readSensorValue());
		Sound.playTone(440, 200);
		
		System.out.println("OPTIMUM: " + myAlfred.getOptimumSensorValue(myAlfred.readSensorValue()));
		
		// Button.waitForPress();
		myAlfred.run();
	}

}

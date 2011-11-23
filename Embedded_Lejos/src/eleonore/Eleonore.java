package eleonore;

import lejos.nxt.Button;

public class Eleonore extends Car {
	
	
	public Eleonore() {
		steer(-MAX_STEERING_DEG);
		go();
		for(int i = 0; i<5; i++) {
//			sleep(500);
			steer(-MAX_STEERING_DEG);
			sleep(5200);
//			steer(0);
//			sleep(500);
			steer(MAX_STEERING_DEG);
			sleep(5200);
		}
		
		steer(0);
		System.out.println("wrrrrm!");
		Button.waitForPress();
	}
	

	private void sleep(int millis) {
		try {
			Thread.sleep(millis);
		} catch (InterruptedException e) {
		}
	}


	public static void main(String[] args) {
		Button.waitForPress(500);
		new Eleonore();

	}

}

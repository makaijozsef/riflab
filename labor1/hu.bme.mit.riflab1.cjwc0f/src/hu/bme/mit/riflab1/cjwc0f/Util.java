package hu.bme.mit.riflab1.cjwc0f;

import java.util.Random;

public class Util {
	
	public static Random rand;
	static {
		rand = new Random();
		rand.setSeed(1234);
	}

}

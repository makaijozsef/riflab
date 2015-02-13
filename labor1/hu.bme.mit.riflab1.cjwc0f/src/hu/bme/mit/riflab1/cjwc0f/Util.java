package hu.bme.mit.riflab1.cjwc0f;

import java.util.Random;

public class Util {

	public static final int SLEEP_TIME = 200;

	public static Random rand;
	static {
		rand = new Random();
		rand.setSeed(1234);
	}

}

package hu.bme.mit.riflab1.cjwc0f;

import java.util.Random;

public class Util {

	// Settings for the business logic

	public static final int MAX_ROOM = 2000;
	public static final int SOCIAL_LIMIT = 50000;
	public static final int HIGHEST_EARNING = 200000;
	public static final int DEFAULT_ROOM = -1;

	// UI and program parameters

	public static final int SLEEP_TIME = /* 20 */0;

	public static final int DEFAULT_WIDTH = 350;
	public static final int DEFAULT_HEIGHT = 250;

	public static Random rand;
	static {
		rand = new Random();
		rand.setSeed(1234);
	}

}

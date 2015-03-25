package hu.bme.mit.cjwc0f.labor4.workflow;

import java.util.Random;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class Util {

	// Settings for the business logic

	public static final int MAX_ROOM = 2000;
	public static final int SOCIAL_LIMIT = 50000;
	public static final int HIGHEST_EARNING = 200000;
	public static final int DEFAULT_ROOM = -1;

	// UI and program parameters

	public static final int SLEEP_TIME = 200;

	public static final int DEFAULT_WIDTH = 350;
	public static final int DEFAULT_HEIGHT = 250;

	public static Random rand;
	static {
		rand = new Random();
		rand.setSeed(1234);
	}
	
	public static byte[] serialize(Object obj) throws IOException {
        ByteArrayOutputStream b = new ByteArrayOutputStream();
        ObjectOutputStream o = new ObjectOutputStream(b);
        o.writeObject(obj);
        return b.toByteArray();
    }

    public static Object deserialize(byte[] bytes) throws IOException, ClassNotFoundException {
        ByteArrayInputStream b = new ByteArrayInputStream(bytes);
        ObjectInputStream o = new ObjectInputStream(b);
        return o.readObject();
    }

}

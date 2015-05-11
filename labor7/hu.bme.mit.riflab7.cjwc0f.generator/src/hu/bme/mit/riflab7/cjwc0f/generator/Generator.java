package hu.bme.mit.riflab7.cjwc0f.generator;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Random;

public class Generator {

	public final static int DATA_COUNT = 1000;

	public static void main(String[] args) throws FileNotFoundException {
		PrintWriter pw = new PrintWriter(new File("E:\\gauss_" + DATA_COUNT + ".csv"));
		Random r = new Random();
		for (int i = 0; i < DATA_COUNT; i++) {
			for (int j = 0; j < 6; j++) {
				//*
				double d = 4 + r.nextGaussian();
				if (d < 0) {
					d = d * (-1);
				}
				if (i >= (DATA_COUNT / 2) && j == 4) {
					pw.write("0;");
				} else {
					pw.write(d + ";");
				}
				
				/*/
				 double value = 4 - Math.sqrt(3) + 2 * Math.sqrt(3) * r.nextDouble();
				 if (value < 0) {
				 value = value * (-1);
				 }
				 if (i >= (DATA_COUNT / 2) && j == 4) {
				 pw.write("0;");
				 } else {
				 pw.write(value + ";");
				 }
				//*/
			}
			pw.write("\n");
		}
		pw.close();
	}

}

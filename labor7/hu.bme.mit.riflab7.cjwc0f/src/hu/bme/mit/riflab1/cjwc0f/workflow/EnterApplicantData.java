package hu.bme.mit.riflab1.cjwc0f.workflow;

import static hu.bme.mit.riflab1.cjwc0f.Util.rand;
import hu.bme.mit.riflab1.cjwc0f.Util;
import hu.bme.mit.riflab1.cjwc0f.data.ApplicationData;

public class EnterApplicantData {

	public static ApplicationData generate() {

		ApplicationData applicantData = new ApplicationData();

		double average1 = getRandomAverage();
		applicantData.setAverage1(average1);

		double average2 = getRandomAverage();
		applicantData.setAverage2(average2);

		int earnings = rand.nextInt(Util.HIGHEST_EARNING);
		applicantData.setEarnings(earnings);

		applicantData.setCommunityPoints(rand.nextInt(100));

		return applicantData;
	}

	private static double getRandomAverage() {
		return Math.random() * 4 + 1;
	}

}

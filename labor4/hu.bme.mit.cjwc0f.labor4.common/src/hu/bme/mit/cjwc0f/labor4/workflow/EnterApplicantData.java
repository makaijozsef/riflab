package hu.bme.mit.cjwc0f.labor4.workflow;

import static hu.bme.mit.cjwc0f.labor4.workflow.Util.rand;

import java.sql.Timestamp;

import hu.bme.mit.cjwc0f.labor4.data.ApplicationData;

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
		
		applicantData.setTimestamp(new Timestamp(System.currentTimeMillis()).toString());

		return applicantData;
	}

	private static double getRandomAverage() {
		return Math.random() * 4 + 1;
	}

}

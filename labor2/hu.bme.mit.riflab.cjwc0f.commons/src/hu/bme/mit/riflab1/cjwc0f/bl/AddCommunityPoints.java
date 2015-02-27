package hu.bme.mit.riflab1.cjwc0f.bl;

import hu.bme.mit.riflab1.cjwc0f.data.ApplicationData;

public class AddCommunityPoints {

	public static ApplicationData calculate(ApplicationData applicantData) {

		double avgIncrement = (double)applicantData.getCommunityPoints() / 50;
		double currAvg = applicantData.getAverage();

		applicantData.setAverage(currAvg + avgIncrement);

		return applicantData;
	}
}

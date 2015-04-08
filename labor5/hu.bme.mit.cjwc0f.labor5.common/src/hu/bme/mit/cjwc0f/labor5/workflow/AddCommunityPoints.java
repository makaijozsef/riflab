package hu.bme.mit.cjwc0f.labor5.workflow;

import hu.bme.mit.cjwc0f.labor5.data.ApplicationData;

public class AddCommunityPoints {

	public static ApplicationData calculate(ApplicationData applicantData) {

		double avgIncrement = (double)applicantData.getCommunityPoints() / 50;
		double currAvg = applicantData.getAverage();

		applicantData.setAverage(currAvg + avgIncrement);

		return applicantData;
	}
}

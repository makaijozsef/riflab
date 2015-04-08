package hu.bme.mit.cjwc0f.labor5.workflow;

import hu.bme.mit.cjwc0f.labor5.data.ApplicationData;

public class DetermineAverage {

	public static ApplicationData calculate(ApplicationData applicantData) {
		double average = (applicantData.getAverage1() + applicantData.getAverage2())/2;
		applicantData.setAverage(average);
		return applicantData;
	}
	
}

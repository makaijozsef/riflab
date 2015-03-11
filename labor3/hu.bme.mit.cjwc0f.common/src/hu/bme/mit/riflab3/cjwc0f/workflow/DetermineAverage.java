package hu.bme.mit.riflab3.cjwc0f.workflow;

import hu.bme.mit.riflab3.cjwc0f.data.ApplicationData;

public class DetermineAverage {

	public static ApplicationData calculate(ApplicationData applicantData) {
		double average = (applicantData.getAverage1() + applicantData.getAverage2())/2;
		applicantData.setAverage(average);
		return applicantData;
	}
	
}

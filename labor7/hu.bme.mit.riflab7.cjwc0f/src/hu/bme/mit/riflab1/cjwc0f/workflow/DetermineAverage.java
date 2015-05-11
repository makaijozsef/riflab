package hu.bme.mit.riflab1.cjwc0f.workflow;

import hu.bme.mit.riflab1.cjwc0f.Generate;
import hu.bme.mit.riflab1.cjwc0f.data.ApplicationData;

public class DetermineAverage {

	public static ApplicationData calculate(ApplicationData applicantData) {
		double average = (applicantData.getAverage1() + applicantData.getAverage2())/2;
		applicantData.setAverage(average);
		applicantData.setAverageTime(Generate.generateGaussian());
		return applicantData;
	}
	
}

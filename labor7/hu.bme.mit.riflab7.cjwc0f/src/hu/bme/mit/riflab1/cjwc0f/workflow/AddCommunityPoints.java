package hu.bme.mit.riflab1.cjwc0f.workflow;

import hu.bme.mit.riflab1.cjwc0f.Generate;
import hu.bme.mit.riflab1.cjwc0f.data.ApplicationData;

public class AddCommunityPoints {

	public static ApplicationData calculate(ApplicationData applicantData) {

		double avgIncrement = (double)applicantData.getCommunityPoints() / 50;
		double currAvg = applicantData.getAverage();

		applicantData.setAverage(currAvg + avgIncrement);
		
		applicantData.setComminutyTime(Generate.generateGaussian());
		
		
		return applicantData;
	}
}

package hu.bme.mit.riflab.cjwc0f.impl;

import hu.bme.mit.riflab.cjwc0f.interf.IDetermineAverageService;
import hu.bme.mit.riflab1.cjwc0f.bl.DetermineAverage;
import hu.bme.mit.riflab1.cjwc0f.data.ApplicationData;

public class DetermineAverageService implements IDetermineAverageService {

	@Override
	public ApplicationData calculate(ApplicationData applicantData) {
		ApplicationData calculatedData = DetermineAverage.calculate(applicantData);
		return calculatedData;
	}

}

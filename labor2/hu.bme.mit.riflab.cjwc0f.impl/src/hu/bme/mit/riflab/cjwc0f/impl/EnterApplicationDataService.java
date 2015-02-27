package hu.bme.mit.riflab.cjwc0f.impl;

import hu.bme.mit.riflab.cjwc0f.interf.IEnterApplicationDataService;
import hu.bme.mit.riflab1.cjwc0f.bl.EnterApplicantData;
import hu.bme.mit.riflab1.cjwc0f.data.ApplicationData;

public class EnterApplicationDataService implements
		IEnterApplicationDataService {

	@Override
	public ApplicationData generate() {
		ApplicationData applicationData = EnterApplicantData.generate();
		return applicationData;
	}

}

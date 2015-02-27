package hu.bme.mit.riflab.cjwc0f.impl;

import hu.bme.mit.riflab.cjwc0f.interf.ISocialInspectionService;
import hu.bme.mit.riflab1.cjwc0f.bl.SocialInspection;
import hu.bme.mit.riflab1.cjwc0f.data.ApplicationData;
import hu.bme.mit.riflab1.cjwc0f.data.SocialResult;

public class SocialInspectionService implements ISocialInspectionService {

	@Override
	public SocialResult createResult(ApplicationData applicationData) {
		SocialResult socialResult = SocialInspection.createResult(applicationData);
		return socialResult;
	}

}

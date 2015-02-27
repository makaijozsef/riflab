package hu.bme.mit.riflab.cjwc0f.impl;

import hu.bme.mit.riflab.cjwc0f.interf.IAddCommunityPointsService;
import hu.bme.mit.riflab1.cjwc0f.bl.AddCommunityPoints;
import hu.bme.mit.riflab1.cjwc0f.data.ApplicationData;

public class AddCommunityPointsService implements IAddCommunityPointsService {

	@Override
	public ApplicationData calculate(ApplicationData applicantData) {
		ApplicationData dataWithCommunitypoints = AddCommunityPoints.calculate(applicantData);
		return dataWithCommunitypoints;
	}

}

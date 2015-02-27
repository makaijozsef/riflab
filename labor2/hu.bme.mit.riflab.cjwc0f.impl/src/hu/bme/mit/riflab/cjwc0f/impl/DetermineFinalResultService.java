package hu.bme.mit.riflab.cjwc0f.impl;

import hu.bme.mit.riflab.cjwc0f.interf.IDetermineFinalResultService;
import hu.bme.mit.riflab1.cjwc0f.bl.DetermineFinalResult;
import hu.bme.mit.riflab1.cjwc0f.data.ApplicationData;
import hu.bme.mit.riflab1.cjwc0f.data.SocialResult;

public class DetermineFinalResultService implements
		IDetermineFinalResultService {

	@Override
	public ApplicationData decide(ApplicationData applicantData,
			SocialResult socialResult) {
		ApplicationData decision = DetermineFinalResult.decide(applicantData, socialResult);
		return decision;
	}

}

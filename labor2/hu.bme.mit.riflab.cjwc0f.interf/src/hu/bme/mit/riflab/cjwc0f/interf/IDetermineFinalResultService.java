package hu.bme.mit.riflab.cjwc0f.interf;

import hu.bme.mit.riflab1.cjwc0f.data.ApplicationData;
import hu.bme.mit.riflab1.cjwc0f.data.SocialResult;

public interface IDetermineFinalResultService {

	public ApplicationData decide(ApplicationData applicantData, SocialResult socialResult);
	
}

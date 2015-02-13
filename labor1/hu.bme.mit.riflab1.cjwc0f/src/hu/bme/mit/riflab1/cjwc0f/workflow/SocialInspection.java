package hu.bme.mit.riflab1.cjwc0f.workflow;

import static hu.bme.mit.riflab1.cjwc0f.Util.rand;
import hu.bme.mit.riflab1.cjwc0f.data.ApplicationData;
import hu.bme.mit.riflab1.cjwc0f.data.SocialResult;

public class SocialInspection {

	public static SocialResult createResult(ApplicationData applicationData) {

		SocialResult socialResult = new SocialResult();

		if (applicationData.getEarnings() < 50000) {
			socialResult.setAdmitted(true);
			socialResult.setRoomNumber(rand.nextInt(2000));
		} else {
			socialResult.setAdmitted(false);
			socialResult.setRoomNumber(0);
		}

		socialResult.setApplicantData(applicationData);
		
		return socialResult;
	}

}

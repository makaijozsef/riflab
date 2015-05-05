package hu.bme.mit.riflab1.cjwc0f.workflow;

import static hu.bme.mit.riflab1.cjwc0f.Util.rand;
import hu.bme.mit.riflab1.cjwc0f.Util;
import hu.bme.mit.riflab1.cjwc0f.data.ApplicationData;
import hu.bme.mit.riflab1.cjwc0f.data.SocialResult;

public class SocialInspection {

	public static SocialResult createResult(ApplicationData applicationData) {

		SocialResult socialResult = new SocialResult();

		if (applicationData.getEarnings() < Util.SOCIAL_LIMIT) {
			socialResult.setAdmitted(true);
			socialResult.setRoomNumber(rand.nextInt(Util.MAX_ROOM));
		} else {
			socialResult.setAdmitted(false);
			socialResult.setRoomNumber(0);
		}

		socialResult.setApplicantData(applicationData);

		return socialResult;
	}

}

package hu.bme.mit.cjwc0f.labor5.workflow;

import static hu.bme.mit.cjwc0f.labor5.workflow.Util.rand;
import hu.bme.mit.cjwc0f.labor5.data.ApplicationData;
import hu.bme.mit.cjwc0f.labor5.data.SocialResult;

public class SocialInspection {

	public static SocialResult createResult(ApplicationData applicationData) {

		SocialResult socialResult = new SocialResult();

		if (applicationData.getEarnings() < Util.SOCIAL_LIMIT) {
			socialResult.setAdmitted(true);
			socialResult.setRoomNumber(rand.nextInt(Util.MAX_ROOM));
		} else {
			socialResult.setAdmitted(false);
			socialResult.setRoomNumber(-1);
		}

		socialResult.setApplicantData(applicationData);

		return socialResult;
	}

}

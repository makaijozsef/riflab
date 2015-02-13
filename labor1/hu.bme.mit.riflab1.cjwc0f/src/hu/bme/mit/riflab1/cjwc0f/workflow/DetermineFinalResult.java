package hu.bme.mit.riflab1.cjwc0f.workflow;

import hu.bme.mit.riflab1.cjwc0f.Util;
import hu.bme.mit.riflab1.cjwc0f.data.ApplicationData;
import hu.bme.mit.riflab1.cjwc0f.data.SocialResult;

public class DetermineFinalResult {

	public static ApplicationData decide(ApplicationData applicantData, SocialResult socialResult) {
		if (applicantData.getResult() != null) {
			// If admitted based on the study results, set the final results
			applicantData.setAdmitted(true);
			applicantData.setRoomNumber(applicantData.getResult().getRoomNumber());
		} else {
			if (applicantData.getSocialResult() != null && applicantData.getSocialResult().isAdmitted()) {
				// If admitted based only on the social results
				applicantData.setAdmitted(true);
				applicantData.setRoomNumber(applicantData.getSocialResult().getRoomNumber());
			} else {
				// Not admitted
				applicantData.setAdmitted(false);
				applicantData.setRoomNumber(Util.DEFAULT_ROOM);
			}
		}
		return applicantData;
	}

}

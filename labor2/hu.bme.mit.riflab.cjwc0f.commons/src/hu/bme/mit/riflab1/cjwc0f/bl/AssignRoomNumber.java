package hu.bme.mit.riflab1.cjwc0f.bl;

import static hu.bme.mit.riflab1.cjwc0f.bl.Util.rand;
import hu.bme.mit.riflab1.cjwc0f.data.ApplicationData;
import hu.bme.mit.riflab1.cjwc0f.data.StudyResult;

public class AssignRoomNumber {

	public static ApplicationData assignRoom(ApplicationData appicantData) {
		StudyResult result = new StudyResult();
		result.setRoomNumber(rand.nextInt(2000));
		result.setAdmitted(true);
		appicantData.setResult(result);
		return appicantData;
	}
}

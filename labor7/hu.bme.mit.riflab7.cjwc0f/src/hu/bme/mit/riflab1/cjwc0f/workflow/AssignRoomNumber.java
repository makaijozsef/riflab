package hu.bme.mit.riflab1.cjwc0f.workflow;

import hu.bme.mit.riflab1.cjwc0f.data.ApplicationData;
import hu.bme.mit.riflab1.cjwc0f.data.StudyResult;

import static hu.bme.mit.riflab1.cjwc0f.Util.rand;

public class AssignRoomNumber {
	
	public static ApplicationData assignRoom(ApplicationData appicantData){
		StudyResult result = new StudyResult();
		result.setRoomNumber(rand.nextInt(2000));
		appicantData.setResult(result);
		return appicantData;
	}
}

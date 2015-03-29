package hu.bme.mit.cjwc0f.labor4.workflow;

import static hu.bme.mit.cjwc0f.labor4.workflow.Util.rand;
import hu.bme.mit.cjwc0f.labor4.data.ApplicationData;
import hu.bme.mit.cjwc0f.labor4.data.StudyResult;

public class AssignRoomNumber {
	
	public static ApplicationData assignRoom(ApplicationData appicantData){
		StudyResult result = new StudyResult();
		result.setRoomNumber(rand.nextInt(2000));
		appicantData.setResult(result);
		return appicantData;
	}
}

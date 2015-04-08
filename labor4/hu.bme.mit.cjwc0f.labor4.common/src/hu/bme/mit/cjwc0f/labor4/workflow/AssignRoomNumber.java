package hu.bme.mit.cjwc0f.labor4.workflow;

import hu.bme.mit.cjwc0f.labor4.data.ApplicationData;
import hu.bme.mit.cjwc0f.labor4.data.StudyResult;
import static hu.bme.mit.cjwc0f.labor4.workflow.Util.rand;

public class AssignRoomNumber {
	
	public static ApplicationData assignRoom(ApplicationData appicantData){
		StudyResult result = new StudyResult();
		result.setRoomNumber(rand.nextInt(2000));
		appicantData.setResult(result);
		return appicantData;
	}
}

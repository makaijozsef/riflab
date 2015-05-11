package hu.bme.mit.riflab1.cjwc0f.workflow;

import static hu.bme.mit.riflab1.cjwc0f.Util.rand;
import hu.bme.mit.riflab1.cjwc0f.Generate;
import hu.bme.mit.riflab1.cjwc0f.data.ApplicationData;
import hu.bme.mit.riflab1.cjwc0f.data.StudyResult;

public class AssignRoomNumber {
	
	public static ApplicationData assignRoom(ApplicationData applicantData){
		StudyResult result = new StudyResult();
		result.setRoomNumber(rand.nextInt(2000));
		applicantData.setResult(result);
		applicantData.setRoomTime(Generate.generateGaussian());
		return applicantData;
	}
}

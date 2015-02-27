package hu.bme.mit.riflab.cjwc0f.impl;

import hu.bme.mit.riflab.cjwc0f.interf.IAssignRoomNumberService;
import hu.bme.mit.riflab1.cjwc0f.bl.AssignRoomNumber;
import hu.bme.mit.riflab1.cjwc0f.data.ApplicationData;

public class AssignRoomNumberService implements IAssignRoomNumberService {

	@Override
	public ApplicationData assignRoom(ApplicationData appicantData) {
		ApplicationData roomAssignedData = AssignRoomNumber.assignRoom(appicantData);
		return roomAssignedData;
	}

}

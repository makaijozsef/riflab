package hu.bme.mit.cjwc0f.labor5.data;

import java.io.Serializable;

public class StudyResult implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -8572217563986323780L;

	private int roomNumber;

	private boolean isAdmitted;

	public int getRoomNumber() {
		return roomNumber;
	}

	public void setRoomNumber(int roomNumber) {
		this.roomNumber = roomNumber;
	}

	public boolean isAdmitted() {
		return isAdmitted;
	}

	public void setAdmitted(boolean isAdmitted) {
		this.isAdmitted = isAdmitted;
	}
}

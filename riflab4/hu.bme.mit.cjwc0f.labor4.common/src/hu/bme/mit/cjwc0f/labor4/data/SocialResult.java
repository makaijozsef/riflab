package hu.bme.mit.cjwc0f.labor4.data;

import java.io.Serializable;

public class SocialResult implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 6138427649139774847L;
	private int roomNumber;
	private boolean isAdmitted;
	private ApplicationData applicantData;

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

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		
		builder.append("timestamp: ");
		builder.append(applicantData.getTimestamp());
		builder.append('\n');

		builder.append("roomNumber: ");
		builder.append(roomNumber);
		builder.append('\n');

		builder.append("isAdmitted: ");
		builder.append(isAdmitted);
		builder.append('\n');

		return builder.toString();
	}

	public ApplicationData getApplicantData() {
		return applicantData;
	}

	public void setApplicantData(ApplicationData applicantData) {
		this.applicantData = applicantData;
		applicantData.setSocialResult(this);
	}

}

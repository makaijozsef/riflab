package hu.bme.mit.riflab1.cjwc0f.data;

public class SocialResult {

	private int roomNumber;
	private boolean isAdmitted;
	private ApplicationData applicantData;
	private boolean automated;

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

	public void setAutomated(boolean automated) {
		this.automated = automated;
	}

	public boolean isAutomated() {
		return automated;
	}

}

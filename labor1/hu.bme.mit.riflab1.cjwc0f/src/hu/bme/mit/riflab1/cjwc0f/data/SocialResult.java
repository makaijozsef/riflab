package hu.bme.mit.riflab1.cjwc0f.data;

public class SocialResult {

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
			
		builder.append("<html>roomNumber: ");
		builder.append(roomNumber);
		builder.append("<br>");
		
		builder.append("isAdmitted: ");
		builder.append(isAdmitted);
		builder.append("</html>");
		
		
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

package hu.bme.mit.riflab1.cjwc0f.data;

public class ApplicationData {

	private int communityPoints;
	private double average1;
	private double average2;
	private int earnings;
	private double average;
	private int roomNumber;
	private boolean isAdmitted;

	private StudyResult result;
	private SocialResult socialResult;

	public int getCommunityPoints() {
		return communityPoints;
	}

	public void setCommunityPoints(int communityPoints) {
		this.communityPoints = communityPoints;
	}

	public double getAverage1() {
		return average1;
	}

	public void setAverage1(double average1) {
		this.average1 = average1;
	}

	public double getAverage2() {
		return average2;
	}

	public void setAverage2(double average2) {
		this.average2 = average2;
	}

	public int getEarnings() {
		return earnings;
	}

	public void setEarnings(int earnings) {
		this.earnings = earnings;
	}

	public double getAverage() {
		return average;
	}

	public void setAverage(double average) {
		this.average = average;
	}

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

	public StudyResult getResult() {
		return result;
	}

	public void setResult(StudyResult result) {
		this.result = result;
	}

	public SocialResult getSocialResult() {
		return socialResult;
	}

	public void setSocialResult(SocialResult socialResult) {
		this.socialResult = socialResult;
	}
	
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("<html>community points: ");
		builder.append(communityPoints);
		builder.append("<br>");
		
		builder.append("average1: ");
		builder.append(average1);
		builder.append("<br>");
		
		builder.append("average2: ");
		builder.append(average2);
		builder.append("<br>");
		
		builder.append("earnings: ");
		builder.append(earnings);
		builder.append("<br>");
		
		builder.append("average: ");
		builder.append(average);
		builder.append("<br>");

		builder.append("roomNumber: ");
		builder.append(roomNumber);
		builder.append("<br>");
		
		builder.append("isAdmitted: ");
		builder.append(isAdmitted);
		builder.append("</html>");
		
		
		return builder.toString();
	}

}

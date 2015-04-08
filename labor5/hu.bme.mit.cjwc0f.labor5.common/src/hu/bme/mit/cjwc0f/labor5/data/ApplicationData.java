package hu.bme.mit.cjwc0f.labor5.data;

import java.io.Serializable;

public class ApplicationData implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 6126130682741185424L;
	private int communityPoints;
	private double average1;
	private double average2;
	private int earnings;
	private Double average;
	private Integer roomNumber;
	private Boolean isAdmitted;
	private String timestamp;

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
	
	public String getTimestamp() {
		return timestamp;
	}
	
	public void setTimestamp(String timestamp) {
		this.timestamp = timestamp;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		
		builder.append("timestamp: ");
		builder.append(timestamp);
		builder.append('\n');
		
		builder.append("community points: ");
		builder.append(communityPoints);
		builder.append('\n');

		builder.append("average1: ");
		builder.append(average1);
		builder.append('\n');

		builder.append("average2: ");
		builder.append(average2);
		builder.append('\n');

		builder.append("earnings: ");
		builder.append(earnings);
		builder.append('\n');

		builder.append("average: ");
		builder.append(average);
		builder.append('\n');

		builder.append("roomNumber: ");
		builder.append(roomNumber);
		builder.append('\n');

		builder.append("isAdmitted: ");
		builder.append(isAdmitted);
		builder.append('\n');

		return builder.toString();
	}

}

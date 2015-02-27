package hu.bme.mit.riflab1.cjwc0f.data;

public class StudyResult {
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
}

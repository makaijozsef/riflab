package hu.bme.mit.cjwc0f.events;

public abstract class HostelApplicationAbstractEvent {
	private String timestamp;

	public HostelApplicationAbstractEvent(String timestamp) {
		this.setTimestamp(timestamp);
	}

	public String getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(String timestamp) {
		this.timestamp = timestamp;
	}
}

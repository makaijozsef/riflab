package hu.bme.mit.cjwc0f.events;

public class ArrivedToTask extends HostelApplicationAbstractEvent {

	private String taskName;

	public ArrivedToTask(String timestamp, String taskName) {
		super(timestamp);
		this.taskName = taskName;
	}

	public String getTaskName() {
		return taskName;
	}

	public void setTaskName(String taskName) {
		this.taskName = taskName;
	}

}

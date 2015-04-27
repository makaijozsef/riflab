package hu.bme.mit.cjwc0f.events;

public class WaitingForTask extends HostelApplicationAbstractEvent {

	private int instancesWaiting;
	private String taskName;
	
	public WaitingForTask(int instancesWaiting, String taskName) {
		super(null);
		this.instancesWaiting = instancesWaiting;
		this.taskName = taskName;
	}
	
	public int getInstancesWaiting() {
		return instancesWaiting;
	}
	
	public void setInstancesWaiting(int instancesWaiting) {
		this.instancesWaiting = instancesWaiting;
	}
	
	public String getTaskName() {
		return taskName;
	}
	
	public void setTaskName(String taskName) {
		this.taskName = taskName;
	}

}

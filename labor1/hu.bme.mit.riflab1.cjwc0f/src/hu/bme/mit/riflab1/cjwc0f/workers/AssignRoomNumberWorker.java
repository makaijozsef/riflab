package hu.bme.mit.riflab1.cjwc0f.workers;

import hu.bme.mit.riflab1.cjwc0f.data.ApplicationData;
import hu.bme.mit.riflab1.cjwc0f.workflow.AddCommunityPoints;

import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.atomic.AtomicBoolean;

import javax.swing.JLabel;
import javax.swing.SwingWorker;

public class AssignRoomNumberWorker extends
		SwingWorker<Object, ApplicationData> {

	AtomicBoolean clicked = new AtomicBoolean(false);

	private BlockingQueue<ApplicationData> inputQueue;
	private BlockingQueue<ApplicationData> outputQueue;

	private JLabel label;

	public AssignRoomNumberWorker(BlockingQueue<ApplicationData> inputQueue,
			BlockingQueue<ApplicationData> outputQueue) {
		this.inputQueue = inputQueue;
		this.outputQueue = outputQueue;
	}

	public void clicked() {
		if (!inputQueue.isEmpty())
			clicked.set(true);
	}

	@Override
	protected Object doInBackground() throws Exception {

		while (true) {
			if (clicked.get()) {
				ApplicationData applicantData = createAndPublishData(clicked);
				outputQueue.put(applicantData);
			}
		}
	}

	private ApplicationData createAndPublishData(AtomicBoolean clicked)
			throws InterruptedException {
		clicked.set(false);
		ApplicationData applicantData = AddCommunityPoints.calculate(inputQueue
				.take());
		publish(applicantData);
		return applicantData;
	}

	@Override
	protected void process(List<ApplicationData> chunks) {
		super.process(chunks);
		for (ApplicationData applicantData : chunks) {
			label.setText(applicantData.toString());
		}
	}

	public void addLabel(JLabel label) {
		this.label = label;
	}

}

package hu.bme.mit.riflab1.cjwc0f.workers;

import hu.bme.mit.riflab1.cjwc0f.data.ApplicationData;
import hu.bme.mit.riflab1.cjwc0f.workflow.AddCommunityPoints;

import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.atomic.AtomicBoolean;

import javax.swing.JLabel;
import javax.swing.SwingWorker;

public class AddCommunityPointsWorker extends
		SwingWorker<Object, ApplicationData> {

	AtomicBoolean clicked = new AtomicBoolean(false);
	AtomicBoolean clickedTrue = new AtomicBoolean(false);
	AtomicBoolean clickedFalse = new AtomicBoolean(false);

	private BlockingQueue<ApplicationData> inputQueue;
	private BlockingQueue<ApplicationData> outputQueueTrue;
	private BlockingQueue<ApplicationData> outputQueueFalse;

	private JLabel label;

	public AddCommunityPointsWorker(BlockingQueue<ApplicationData> inputQueue,
			BlockingQueue<ApplicationData> outputQueueTrue,
			BlockingQueue<ApplicationData> outputQueueFalse) {
		this.inputQueue = inputQueue;
		this.outputQueueTrue = outputQueueTrue;
		this.outputQueueFalse = outputQueueFalse;
	}

	public void clicked() {
		if (!inputQueue.isEmpty())
			clicked.set(true);
	}

	public void clickedTrue() {
		if (!inputQueue.isEmpty())
			clickedTrue.set(true);
	}

	public void clickedFalse() {
		if (!inputQueue.isEmpty())
			clickedFalse.set(true);
	}

	@Override
	protected Object doInBackground() throws Exception {

		while (true) {
			if (clickedTrue.get()) {
				ApplicationData applicantData = createAndPublishData(clickedTrue);
				outputQueueTrue.put(applicantData);
			}
			if (clickedFalse.get()) {
				ApplicationData applicantData = createAndPublishData(clickedFalse);
				outputQueueFalse.put(applicantData);
			}
			if (clicked.get()) {
				ApplicationData applicantData = createAndPublishData(clicked);
				if (applicantData.getAverage() >= 3) {
					outputQueueTrue.put(applicantData);
				} else {
					outputQueueFalse.put(applicantData);
				}
			}
		}
	}

	private ApplicationData createAndPublishData(AtomicBoolean clicked) throws InterruptedException {
		clicked.set(false);
		ApplicationData applicantData = AddCommunityPoints.calculate(inputQueue.take());
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

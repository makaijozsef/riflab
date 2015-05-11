package hu.bme.mit.riflab1.cjwc0f.workers;

import hu.bme.mit.riflab1.cjwc0f.Util;
import hu.bme.mit.riflab1.cjwc0f.data.ApplicationData;
import hu.bme.mit.riflab1.cjwc0f.workflow.AssignRoomNumber;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.atomic.AtomicBoolean;

public class AssignRoomNumberWorker extends AbstractWorker {

	private DetermineFinalResultWorker finalResultWorker;

	public AssignRoomNumberWorker(BlockingQueue<ApplicationData> inputQueue,
			BlockingQueue<ApplicationData> outputQueue, DetermineFinalResultWorker finalResultWorker) {
		super(inputQueue, outputQueue);
		this.finalResultWorker = finalResultWorker;
	}

	@Override
	protected Object doInBackground() throws Exception {
		while (true) {
			Thread.sleep(Util.SLEEP_TIME);
			if (clicked.get()) {
				ApplicationData applicantData = createAndPublishData(clicked);
				outputQueue.put(applicantData);
			}
		}
	}

	private ApplicationData createAndPublishData(AtomicBoolean clicked) throws InterruptedException {
		ApplicationData inputData = inputQueue.take();
		clicked.set(inputData.isAutomated());
		ApplicationData applicantData = AssignRoomNumber.assignRoom(inputData);
		applicantData.setAutomated(inputData.isAutomated());
		publish(applicantData);
		if (inputQueue.isEmpty()) {
			clicked.set(false);
			if (applicantData.isAutomated()) {
				finalResultWorker.clicked();
			}
		}
		return applicantData;
	}

}

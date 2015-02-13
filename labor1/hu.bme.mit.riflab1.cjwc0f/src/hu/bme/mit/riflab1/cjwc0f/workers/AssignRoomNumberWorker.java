package hu.bme.mit.riflab1.cjwc0f.workers;

import hu.bme.mit.riflab1.cjwc0f.Util;
import hu.bme.mit.riflab1.cjwc0f.data.ApplicationData;
import hu.bme.mit.riflab1.cjwc0f.workflow.AssignRoomNumber;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.atomic.AtomicBoolean;

public class AssignRoomNumberWorker extends AbstractWorker {

	public AssignRoomNumberWorker(BlockingQueue<ApplicationData> inputQueue, BlockingQueue<ApplicationData> outputQueue) {
		super(inputQueue, outputQueue);
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
		clicked.set(false);
		ApplicationData applicantData = AssignRoomNumber.assignRoom(inputQueue.take());
		publish(applicantData);
		return applicantData;
	}

}

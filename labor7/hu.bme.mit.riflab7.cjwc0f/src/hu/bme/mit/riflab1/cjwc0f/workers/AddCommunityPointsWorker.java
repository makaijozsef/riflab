package hu.bme.mit.riflab1.cjwc0f.workers;

import hu.bme.mit.riflab1.cjwc0f.Util;
import hu.bme.mit.riflab1.cjwc0f.data.ApplicationData;
import hu.bme.mit.riflab1.cjwc0f.workflow.AddCommunityPoints;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.atomic.AtomicBoolean;

public class AddCommunityPointsWorker extends AbstractWorker {

	AtomicBoolean clickedTrue = new AtomicBoolean(false);
	AtomicBoolean clickedFalse = new AtomicBoolean(false);

	private BlockingQueue<ApplicationData> outputQueueTrue;
	private BlockingQueue<ApplicationData> outputQueueFalse;
	private DetermineFinalResultWorker finalResultWorker;
	private AssignRoomNumberWorker assignRoomWorker;

	public AddCommunityPointsWorker(BlockingQueue<ApplicationData> inputQueue,
			BlockingQueue<ApplicationData> outputQueueTrue, BlockingQueue<ApplicationData> outputQueueFalse,
			AssignRoomNumberWorker assignRoomWorker, DetermineFinalResultWorker finalResultWorker) {
		super(inputQueue, outputQueueFalse);
		this.inputQueue = inputQueue;
		this.outputQueueTrue = outputQueueTrue;
		this.outputQueueFalse = outputQueueFalse;
		this.assignRoomWorker = assignRoomWorker;
		this.finalResultWorker = finalResultWorker;
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
			Thread.sleep(Util.SLEEP_TIME);
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
				if (inputQueue.isEmpty() && applicantData.isAutomated()) {
					assignRoomWorker.clicked();
				}
			}
		}
	}

	private ApplicationData createAndPublishData(AtomicBoolean clicked) throws InterruptedException {
		ApplicationData inputData = inputQueue.take();
		clicked.set(inputData.isAutomated());
		ApplicationData applicantData = AddCommunityPoints.calculate(inputData);
		applicantData.setAutomated(inputData.isAutomated());
		publish(applicantData);
		if (inputQueue.isEmpty()) {
			clicked.set(false);
		}
		return applicantData;
	}

}

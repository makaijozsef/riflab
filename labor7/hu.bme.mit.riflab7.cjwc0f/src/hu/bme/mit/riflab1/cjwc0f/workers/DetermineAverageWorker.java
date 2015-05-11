package hu.bme.mit.riflab1.cjwc0f.workers;

import hu.bme.mit.riflab1.cjwc0f.Util;
import hu.bme.mit.riflab1.cjwc0f.data.ApplicationData;
import hu.bme.mit.riflab1.cjwc0f.workflow.DetermineAverage;

import java.util.concurrent.BlockingQueue;

public class DetermineAverageWorker extends AbstractWorker {

	private AddCommunityPointsWorker addCommunityPointsWorker;

	public DetermineAverageWorker(BlockingQueue<ApplicationData> inputQueue,
			BlockingQueue<ApplicationData> outputQueue, AddCommunityPointsWorker addCommunityPointsWorker) {
		super(inputQueue, outputQueue);
		this.addCommunityPointsWorker = addCommunityPointsWorker;
	}

	@Override
	protected Object doInBackground() throws Exception {

		while (true) {
			Thread.sleep(Util.SLEEP_TIME);
			if (clicked.get()) {
				clicked.set(false);
				ApplicationData inputData = null;
				do {
					if (inputQueue.isEmpty()) {
						break;
					}
					inputData = inputQueue.take();
					ApplicationData applicantData = DetermineAverage.calculate(inputData);
					applicantData.setAutomated(inputData.isAutomated());
					publish(applicantData);
					outputQueue.put(applicantData);
				} while (inputData.isAutomated());
				if (inputData.isAutomated()) {
					addCommunityPointsWorker.clicked();
				}
			}
		}
	}
}

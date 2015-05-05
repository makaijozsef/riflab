package hu.bme.mit.riflab1.cjwc0f.workers;

import hu.bme.mit.riflab1.cjwc0f.Util;
import hu.bme.mit.riflab1.cjwc0f.data.ApplicationData;
import hu.bme.mit.riflab1.cjwc0f.workflow.DetermineAverage;

import java.util.concurrent.BlockingQueue;

public class DetermineAverageWorker extends AbstractWorker {

	public DetermineAverageWorker(BlockingQueue<ApplicationData> inputQueue, BlockingQueue<ApplicationData> outputQueue) {
		super(inputQueue, outputQueue);
	}

	@Override
	protected Object doInBackground() throws Exception {

		while (true) {
			Thread.sleep(Util.SLEEP_TIME);
			if (clicked.get()) {
				clicked.set(false);
				ApplicationData applicantData = DetermineAverage.calculate(inputQueue.take());
				publish(applicantData);
				outputQueue.put(applicantData);
			}
		}
	}

}

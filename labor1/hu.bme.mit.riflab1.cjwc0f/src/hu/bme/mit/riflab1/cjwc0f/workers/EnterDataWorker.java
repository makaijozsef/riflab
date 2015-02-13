package hu.bme.mit.riflab1.cjwc0f.workers;

import hu.bme.mit.riflab1.cjwc0f.data.ApplicationData;
import hu.bme.mit.riflab1.cjwc0f.workflow.EnterApplicantData;

import java.util.concurrent.BlockingQueue;

public class EnterDataWorker extends AbstractWorker {

	public EnterDataWorker(BlockingQueue<ApplicationData> inputQueue, BlockingQueue<ApplicationData> outputQueue) {
		super(inputQueue, outputQueue);
	}

	@Override
	protected Object doInBackground() throws Exception {

		while (true) {
			if (clicked.get()) {
				clicked.set(false);
				ApplicationData generatedData = EnterApplicantData.generate();
				publish(generatedData);
				inputQueue.put(generatedData);
				outputQueue.put(generatedData);
			}
		}
	}

}

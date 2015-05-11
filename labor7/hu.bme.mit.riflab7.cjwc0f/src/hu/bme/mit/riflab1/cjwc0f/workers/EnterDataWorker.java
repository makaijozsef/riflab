package hu.bme.mit.riflab1.cjwc0f.workers;

import hu.bme.mit.riflab1.cjwc0f.Util;
import hu.bme.mit.riflab1.cjwc0f.data.ApplicationData;
import hu.bme.mit.riflab1.cjwc0f.workflow.EnterApplicantData;

import java.util.concurrent.BlockingQueue;

public class EnterDataWorker extends AbstractWorker {

	private SocialInspectionWorker socialInspectionWorker;
	private DetermineAverageWorker determineAverageWorker;

	public EnterDataWorker(BlockingQueue<ApplicationData> inputQueue, BlockingQueue<ApplicationData> outputQueue,
			SocialInspectionWorker socialInspectionWorker, DetermineAverageWorker determineAverageWorker) {
		super(inputQueue, outputQueue);
		this.socialInspectionWorker = socialInspectionWorker;
		this.determineAverageWorker = determineAverageWorker;

	}

	@Override
	protected Object doInBackground() throws Exception {

		while (true) {
			Thread.sleep(Util.SLEEP_TIME);
			if (clicked.get()) {
				clicked.set(false);
				ApplicationData generatedData = EnterApplicantData.generate();
				publish(generatedData);
				inputQueue.put(generatedData);
				outputQueue.put(generatedData);
			}
			if (clickedAutomatic.get()) {
				clickedAutomatic.set(false);
				for (int i = 0; i < 1000; i++) {
					ApplicationData generatedData = EnterApplicantData.generate();
					generatedData.setAutomated(true);
					publish(generatedData);
					inputQueue.put(generatedData);

					outputQueue.put(generatedData);
				}
				socialInspectionWorker.clicked();
				determineAverageWorker.clicked();
			}
		}
	}

}

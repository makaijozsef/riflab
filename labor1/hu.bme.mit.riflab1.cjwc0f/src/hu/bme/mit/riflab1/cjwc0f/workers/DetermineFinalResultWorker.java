package hu.bme.mit.riflab1.cjwc0f.workers;

import hu.bme.mit.riflab1.cjwc0f.data.ApplicationData;
import hu.bme.mit.riflab1.cjwc0f.data.SocialResult;
import hu.bme.mit.riflab1.cjwc0f.workflow.DetermineFinalResult;

import java.util.Iterator;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DetermineFinalResultWorker extends AbstractWorker {

	private BlockingQueue<ApplicationData> inputQueueStudy;
	private BlockingQueue<SocialResult> inputQueueSocial;

	private ApplicationData lastData;

	public DetermineFinalResultWorker(BlockingQueue<ApplicationData> inputQueueStudy,
			BlockingQueue<SocialResult> inputQueueSocial, BlockingQueue<ApplicationData> outputQueue) {
		super(inputQueueStudy, outputQueue);
		this.inputQueueStudy = inputQueueStudy;
		this.inputQueueSocial = inputQueueSocial;
		this.outputQueue = outputQueue;
	}

	@Override
	public void clicked() {
		if (!inputQueueSocial.isEmpty() && !inputQueueStudy.isEmpty()) {

			ApplicationData targetApplicantData = inputQueueSocial.peek().getApplicantData();

			Iterator<ApplicationData> iterator = inputQueueStudy.iterator();
			while (iterator.hasNext()) {
				ApplicationData applicationData = (ApplicationData) iterator.next();

				if (applicationData.equals(targetApplicantData)) {
					lastData = applicationData;
					iterator.remove();
					try {
						inputQueueSocial.take();
					} catch (InterruptedException e) {
						Logger.getGlobal().log(Level.SEVERE,
								"Error occured while reading the input queue of social results");
					}
					clicked.set(true);
					break;

				}

			}

		}

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

	private ApplicationData createAndPublishData(AtomicBoolean clicked) throws InterruptedException {
		clicked.set(false);
		ApplicationData applicantData = DetermineFinalResult.decide(lastData, lastData.getSocialResult());
		publish(applicantData);
		return applicantData;
	}

}

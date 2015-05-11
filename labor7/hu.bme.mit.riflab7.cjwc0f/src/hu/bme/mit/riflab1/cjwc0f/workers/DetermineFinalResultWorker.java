package hu.bme.mit.riflab1.cjwc0f.workers;

import hu.bme.mit.riflab1.cjwc0f.Generate;
import hu.bme.mit.riflab1.cjwc0f.Util;
import hu.bme.mit.riflab1.cjwc0f.data.ApplicationData;
import hu.bme.mit.riflab1.cjwc0f.data.SocialResult;
import hu.bme.mit.riflab1.cjwc0f.workflow.DetermineFinalResult;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Queue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.atomic.AtomicBoolean;

public class DetermineFinalResultWorker extends AbstractWorker {

	private Queue<ApplicationData> inputQueueStudy;
	private Queue<SocialResult> inputQueueSocial;

	private ApplicationData lastData;

	public DetermineFinalResultWorker(BlockingQueue<ApplicationData> inputQueueStudy,
			BlockingQueue<SocialResult> inputQueueSocial, BlockingQueue<ApplicationData> outputQueue) {
		super(inputQueueStudy, outputQueue);
		this.inputQueue = inputQueueStudy;
		this.inputQueueStudy = inputQueueStudy;
		this.inputQueueSocial = inputQueueSocial;
		this.outputQueue = outputQueue;
	}

	PrintWriter pw = null;

	@Override
	public void clicked() {
		if (!inputQueueSocial.isEmpty() && !inputQueueStudy.isEmpty()) {

			int iterCount = 1;

			if (inputQueueSocial.peek().isAutomated() == true) {
				iterCount = Util.DATA_COUNT;
				try {
					// Wait for the queues to fill up
					Thread.sleep(100);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}

			try {
				pw = new PrintWriter(new File("E:\\gauss_" + Util.DATA_COUNT + ".csv"));
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			for (int i = 0; i < iterCount; i++) {
				
				lastData = inputQueueStudy.poll();
				lastData.setFinalTime(Generate.generateGaussian());

				StringBuilder builder = new StringBuilder();

				builder.append(lastData.getEnterTime()).append(";").append(lastData.getSocialTime()).append(";")
						.append(lastData.getAverageTime()).append(";").append(lastData.getComminutyTime()).append(";")
						.append(lastData.getRoomTime()).append(";").append(lastData.getFinalTime()).append('\n');

				System.out.print(builder.toString());
				
				pw.write(builder.toString());

			}
			clicked.set(true);
			pw.close();

		}

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
		ApplicationData applicantData = DetermineFinalResult.decide(lastData, lastData.getSocialResult());
		publish(applicantData);
		return applicantData;
	}

}

package hu.bme.mit.riflab1.cjwc0f.workers;

import hu.bme.mit.riflab1.cjwc0f.Util;
import hu.bme.mit.riflab1.cjwc0f.data.ApplicationData;
import hu.bme.mit.riflab1.cjwc0f.data.SocialResult;
import hu.bme.mit.riflab1.cjwc0f.workflow.SocialInspection;

import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.atomic.AtomicBoolean;

import javax.swing.JTextArea;
import javax.swing.SwingWorker;

public class SocialInspectionWorker extends SwingWorker<Object, SocialResult> {

	private AtomicBoolean clicked = new AtomicBoolean(false);

	private BlockingQueue<ApplicationData> inputQueue;
	private BlockingQueue<SocialResult> outputQueue;

	private JTextArea textArea;

	private DetermineFinalResultWorker finalResultWorker;

	public SocialInspectionWorker(BlockingQueue<ApplicationData> inputQueue, BlockingQueue<SocialResult> outputQueue,
			DetermineFinalResultWorker finalResultWorker) {
		this.inputQueue = inputQueue;
		this.outputQueue = outputQueue;
		this.finalResultWorker = finalResultWorker;
	}

	public void clicked() {
		if (!inputQueue.isEmpty())
			clicked.set(true);
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
					SocialResult socialResult = SocialInspection.createResult(inputData);
					socialResult.setAutomated(inputData.isAutomated());
					publish(socialResult);
					outputQueue.put(socialResult);
				} while (inputData.isAutomated());
				// if (inputData.isAutomated()) {
				// finalResultWorker.clicked();
				// }
			}
		}
	}

	@Override
	protected void process(List<SocialResult> chunks) {
		super.process(chunks);
		for (SocialResult socialResult : chunks) {
			textArea.setText(socialResult.toString());
		}
	}

	public void addTextArea(JTextArea textArea) {
		this.textArea = textArea;
	}

}

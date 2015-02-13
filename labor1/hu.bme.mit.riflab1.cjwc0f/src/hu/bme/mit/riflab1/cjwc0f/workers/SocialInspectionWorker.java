package hu.bme.mit.riflab1.cjwc0f.workers;

import hu.bme.mit.riflab1.cjwc0f.data.ApplicationData;
import hu.bme.mit.riflab1.cjwc0f.data.SocialResult;
import hu.bme.mit.riflab1.cjwc0f.workflow.SocialInspection;

import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.atomic.AtomicBoolean;

import javax.swing.JLabel;
import javax.swing.SwingWorker;

public class SocialInspectionWorker extends SwingWorker<Object, SocialResult> {

	AtomicBoolean clicked = new AtomicBoolean(false);

	private BlockingQueue<ApplicationData> inputQueue;

	private JLabel label;

	private BlockingQueue<SocialResult> outputQueue;

	public SocialInspectionWorker(BlockingQueue<ApplicationData> inputQueue,
			BlockingQueue<SocialResult> outputQueue) {
		this.inputQueue = inputQueue;
		this.outputQueue = outputQueue;
	}

	public void clicked() {
		if(!inputQueue.isEmpty())
			clicked.set(true);
	}

	@Override
	protected Object doInBackground() throws Exception {

		while (true) {
			if (clicked.get()) {
				clicked.set(false);
				SocialResult socialResult = SocialInspection
						.createResult(inputQueue.take());
				publish(socialResult);
				outputQueue.put(socialResult);
			}
		}
	}

	@Override
	protected void process(List<SocialResult> chunks) {
		super.process(chunks);
		for (SocialResult socialResult : chunks) {
			label.setText(socialResult.toString());
		}
	}

	public void addLabel(JLabel label) {
		this.label = label;
	}

}

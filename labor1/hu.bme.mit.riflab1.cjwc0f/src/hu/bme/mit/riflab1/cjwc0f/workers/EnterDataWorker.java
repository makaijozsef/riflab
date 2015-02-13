package hu.bme.mit.riflab1.cjwc0f.workers;

import hu.bme.mit.riflab1.cjwc0f.data.ApplicationData;
import hu.bme.mit.riflab1.cjwc0f.workflow.EnterApplicantData;

import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.atomic.AtomicBoolean;

import javax.swing.JLabel;
import javax.swing.SwingWorker;

public class EnterDataWorker extends SwingWorker<Object, ApplicationData> {

	AtomicBoolean clicked = new AtomicBoolean(false);

	private BlockingQueue<ApplicationData> inputQueue;
	private BlockingQueue<ApplicationData> outputQueue;

	private JLabel label;

	public EnterDataWorker(BlockingQueue<ApplicationData> inputQueue, BlockingQueue<ApplicationData> outputQueue) {
		this.inputQueue = inputQueue;
		this.outputQueue = outputQueue;
	}

	public void clicked() {
		clicked.set(true);
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

	@Override
	protected void process(List<ApplicationData> chunks) {
		super.process(chunks);
		for (ApplicationData applicationData : chunks) {
			label.setText(applicationData.toString());
		}
	}

	public void addLabel(JLabel label) {
		this.label = label;
	}

}

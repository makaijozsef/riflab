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

	private BlockingQueue<ApplicationData> queue1;
	private BlockingQueue<ApplicationData> queue2;

	private JLabel label;

	public EnterDataWorker(BlockingQueue<ApplicationData> queue1, BlockingQueue<ApplicationData> queue2) {
		this.queue1 = queue1;
		this.queue2 = queue2;
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
				queue1.put(generatedData);
				queue2.put(generatedData);
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

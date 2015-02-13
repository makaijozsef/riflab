package hu.bme.mit.riflab1.cjwc0f.workers;

import hu.bme.mit.riflab1.cjwc0f.data.ApplicationData;
import hu.bme.mit.riflab1.cjwc0f.workflow.EnterApplicantData;

import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.atomic.AtomicBoolean;

import javax.swing.JTextArea;
import javax.swing.SwingWorker;

public abstract class AbstractWorker extends SwingWorker<Object, ApplicationData> {

	protected AtomicBoolean clicked = new AtomicBoolean(false);

	protected BlockingQueue<ApplicationData> inputQueue;
	protected BlockingQueue<ApplicationData> outputQueue;

	protected JTextArea text;

	public AbstractWorker(BlockingQueue<ApplicationData> inputQueue, BlockingQueue<ApplicationData> outputQueue) {
		this.inputQueue = inputQueue;
		this.outputQueue = outputQueue;
	}

	public void clicked() {
		clicked.set(true);
	}

	@Override
	protected void process(List<ApplicationData> chunks) {
		super.process(chunks);
		for (ApplicationData applicationData : chunks) {
			text.setText(applicationData.toString());
		}
	}

	public void addTextArea(JTextArea textArea) {
		this.text = textArea;
	}

}

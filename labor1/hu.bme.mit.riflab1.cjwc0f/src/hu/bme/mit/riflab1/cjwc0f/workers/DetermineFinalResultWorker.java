package hu.bme.mit.riflab1.cjwc0f.workers;

import hu.bme.mit.riflab1.cjwc0f.data.ApplicationData;
import hu.bme.mit.riflab1.cjwc0f.data.SocialResult;
import hu.bme.mit.riflab1.cjwc0f.workflow.DetermineFinalResult;

import java.util.Iterator;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.atomic.AtomicBoolean;

import javax.swing.JLabel;
import javax.swing.SwingWorker;

public class DetermineFinalResultWorker extends
		SwingWorker<Object, ApplicationData> {

	AtomicBoolean clicked = new AtomicBoolean(false);

	private BlockingQueue<ApplicationData> outputQueue;

	private JLabel label;

	private BlockingQueue<ApplicationData> inputQueueStudy;
	private BlockingQueue<SocialResult> inputQueueSocial;
	
	
	private ApplicationData lastData;
	
	
	public DetermineFinalResultWorker(BlockingQueue<ApplicationData> inputQueueStudy,
			BlockingQueue<SocialResult> inputQueueSocial,
			BlockingQueue<ApplicationData> outputQueue) {
		this.inputQueueStudy = inputQueueStudy;
		this.inputQueueSocial = inputQueueSocial;
		this.outputQueue = outputQueue;
	}

	public void clicked() throws InterruptedException {
		if (!inputQueueSocial.isEmpty() && !inputQueueStudy.isEmpty()){
			
			ApplicationData targetApplicantData = inputQueueSocial.peek().getApplicantData();
			
			Iterator<ApplicationData> iterator = inputQueueStudy.iterator();
			while (iterator.hasNext()) {
				ApplicationData applicationData = (ApplicationData) iterator
						.next();
				
				if(applicationData.equals(targetApplicantData)){
					lastData = applicationData;
					iterator.remove();
					inputQueueSocial.take();
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

	@Override
	protected void process(List<ApplicationData> chunks) {
		super.process(chunks);
		for (ApplicationData applicantData : chunks) {
			label.setText(applicantData.toString());
		}
	}

	public void addLabel(JLabel label) {
		this.label = label;
	}

}

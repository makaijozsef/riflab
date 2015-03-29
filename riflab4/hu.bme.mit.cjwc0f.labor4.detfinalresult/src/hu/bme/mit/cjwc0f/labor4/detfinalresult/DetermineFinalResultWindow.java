package hu.bme.mit.cjwc0f.labor4.detfinalresult;

import hu.bme.mit.cjwc0f.labor4.data.ApplicationData;
import hu.bme.mit.cjwc0f.labor4.data.SocialResult;
import hu.bme.mit.cjwc0f.labor4.gui.AbstractWindow;
import hu.bme.mit.cjwc0f.labor4.workflow.DetermineFinalResult;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.lang.management.ManagementFactory;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.jms.JMSException;
import javax.jms.ObjectMessage;
import javax.jms.Queue;
import javax.jms.QueueReceiver;
import javax.jms.QueueSession;
import javax.naming.NamingException;
import javax.swing.JButton;
import javax.swing.SwingWorker;

@SuppressWarnings("serial")
public class DetermineFinalResultWindow extends AbstractWindow {
	
	private final class SwingWorkerStudyExtension extends SwingWorker<Void, Void> {

		@Override
		protected Void doInBackground() throws Exception {
			button.setEnabled(false);
			if(receivedStudyMessage == null){
				receivedStudyMessage = (ObjectMessage) roomReceiver.receiveNoWait();
			}
			while (receivedStudyMessage == null) {
				Thread.sleep(200);
				receivedStudyMessage = (ObjectMessage) roomReceiver.receiveNoWait();
			}
			
			ApplicationData applicationData = (ApplicationData) receivedStudyMessage.getObject();
			
			SocialResult matchingSR = null;
			for (SocialResult sr : socialResults) {
				if(applicationData.getTimestamp().equals(sr.getApplicantData().getTimestamp())){
					matchingSR = sr;
					break;
				}
			}
			if(matchingSR != null){
				socialResults.remove(matchingSR);
				ApplicationData finalResult = DetermineFinalResult.decide(applicationData, matchingSR);
				
				textArea.setText(finalResult.toString());
			}
			else {
				applicationDatas.add(applicationData);
			}
			receivedStudyMessage = (ObjectMessage) roomReceiver.receiveNoWait();
			while (receivedStudyMessage == null) {
				Thread.sleep(200);
				receivedStudyMessage = (ObjectMessage) roomReceiver.receiveNoWait();
			}
			button.setEnabled(true);
			return null;
			
		}
	}
	private final class SwingWorkerSocialExtension extends SwingWorker<Void, Void> {
		
		@Override
		protected Void doInBackground() throws Exception {
			socialButton.setEnabled(false);
			if(receivedSocialMessage == null){
				receivedSocialMessage = (ObjectMessage) socialReceiver.receiveNoWait();
			}
			while (receivedSocialMessage == null) {
				Thread.sleep(200);
				receivedSocialMessage = (ObjectMessage) socialReceiver.receiveNoWait();
			}
			
			SocialResult socialResult = (SocialResult) receivedSocialMessage.getObject();
			
			ApplicationData matchingAD = null;
			for (ApplicationData ad : applicationDatas) {
				if(ad.getTimestamp().equals(socialResult.getApplicantData().getTimestamp())){
					matchingAD = ad;
					break;
				}
			}
			if(matchingAD != null){
				applicationDatas.remove(matchingAD);
				ApplicationData finalResult = DetermineFinalResult.decide(matchingAD, socialResult);
				
				textArea.setText(finalResult.toString());
			}
			else {
				socialResults.add(socialResult);
			}
					
					
			receivedSocialMessage = (ObjectMessage) socialReceiver.receiveNoWait();
			while (receivedSocialMessage == null) {
				Thread.sleep(200);
				receivedSocialMessage = (ObjectMessage) socialReceiver.receiveNoWait();
			}
			socialButton.setEnabled(true);
			return null;
			
		}
		
	}
	
	private List<ApplicationData> applicationDatas = new ArrayList<ApplicationData>();
	private List<SocialResult> socialResults = new ArrayList<SocialResult>();
	private Queue inputRoomQueue;
	private QueueReceiver roomReceiver;
	private Queue inputSocialQueue;
	private QueueReceiver socialReceiver;
	
	private ObjectMessage receivedStudyMessage;
	private ObjectMessage receivedSocialMessage;

	
	
	private boolean firstClickStudy = true;
	private boolean firstClickSocial = true;
	private JButton socialButton;
	
	public DetermineFinalResultWindow() {
		
		super("Determine final result", 1400, 300);
		
		final String jvmName = ManagementFactory.getRuntimeMXBean().getName();
		this.setTitle("Determine final result - " + jvmName);

		createQueueSession();
		try {
			inputRoomQueue = (Queue) initialContext.lookup("queue/finalResultQueue");
			roomReceiver = ((QueueSession)session).createReceiver(inputRoomQueue);
			inputSocialQueue = (Queue) initialContext.lookup("queue/socialInspectionQueue");
			socialReceiver = ((QueueSession)session).createReceiver(inputSocialQueue);
		} catch (NamingException e) {
			Logger.getLogger(Logger.GLOBAL_LOGGER_NAME).log(Level.SEVERE, "Problem occured with the JNDI: " + e.getMessage());
		} catch (JMSException e1) {
			Logger.getLogger(Logger.GLOBAL_LOGGER_NAME).log(Level.SEVERE, "Error with creating receiver/sender: " + e1.getMessage());
		}
		

	    button.setText("Consume from study part");
		button.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				
				SwingWorker<Void, Void> messageReader = new SwingWorkerStudyExtension();
				if (firstClickStudy) {
					// Only for init
					firstClickStudy = false;
					messageReader.execute();
					if(receivedStudyMessage == null){
						textArea.setText("The first input is not ready yet.");
					}
				} else {
					messageReader.execute();
				}
				
				
			}

		});
		
		this.getContentPane().add(button, BorderLayout.WEST);
		
		socialButton = new JButton();
		socialButton.setText("Consume from social part");
		
		socialButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				SwingWorker<Void, Void> messageReader = new SwingWorkerSocialExtension();
				if (firstClickSocial) {
					// Only for init
					firstClickSocial = false;
					messageReader.execute();
					if (receivedStudyMessage == null) {
						textArea.setText("The first input is not ready yet.");
					}
				} else {
					messageReader.execute();
				}

			}

		});

		this.getContentPane().add(socialButton, BorderLayout.EAST);

	}

}

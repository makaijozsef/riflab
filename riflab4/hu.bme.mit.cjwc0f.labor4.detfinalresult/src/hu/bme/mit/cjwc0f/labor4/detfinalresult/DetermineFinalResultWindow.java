package hu.bme.mit.cjwc0f.labor4.detfinalresult;

import hu.bme.mit.cjwc0f.labor4.data.ApplicationData;
import hu.bme.mit.cjwc0f.labor4.data.SocialResult;
import hu.bme.mit.cjwc0f.labor4.gui.AbstractWindow;
import hu.bme.mit.cjwc0f.labor4.workflow.DetermineAverage;
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
import javax.jms.QueueSender;
import javax.jms.QueueSession;
import javax.naming.NamingException;
import javax.swing.JButton;

@SuppressWarnings("serial")
public class DetermineFinalResultWindow extends AbstractWindow {
	
	private List<ApplicationData> applicationDatas = new ArrayList<ApplicationData>();
	private List<SocialResult> socialResults = new ArrayList<SocialResult>();
	private Queue inputRoomQueue;
	private QueueReceiver roomReceiver;
	private Queue inputSocialQueue;
	private QueueReceiver socialReceiver;
	
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
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JMSException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		

	    button.setText("Consume from study part");
		button.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					ObjectMessage receive = ((ObjectMessage)roomReceiver.receive());
					ApplicationData applicationData = (ApplicationData) receive.getObject();
					
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
					
				} catch (JMSException e1) {
					Logger.global.log(Level.SEVERE, "Could not publish message: " + e1.getMessage());
				}
			}

		});
		
		this.getContentPane().add(button, BorderLayout.WEST);
		
		JButton socialButton = new JButton();
		socialButton.setText("Consume from social part");
		
		socialButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					ObjectMessage receive = ((ObjectMessage)socialReceiver.receive());
					SocialResult socialResult = (SocialResult) receive.getObject();
					
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
					
				} catch (JMSException e1) {
					Logger.global.log(Level.SEVERE, "Could not publish message: " + e1.getMessage());
				}
			}

		});
		
		this.getContentPane().add(socialButton, BorderLayout.EAST);
		
		
	}

}

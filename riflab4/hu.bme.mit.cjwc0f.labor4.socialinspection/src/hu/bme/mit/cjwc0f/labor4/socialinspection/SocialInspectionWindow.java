package hu.bme.mit.cjwc0f.labor4.socialinspection;

import hu.bme.mit.cjwc0f.labor4.data.ApplicationData;
import hu.bme.mit.cjwc0f.labor4.data.SocialResult;
import hu.bme.mit.cjwc0f.labor4.gui.AbstractWindow;
import hu.bme.mit.cjwc0f.labor4.workflow.SocialInspection;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.lang.management.ManagementFactory;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.jms.JMSException;
import javax.jms.ObjectMessage;
import javax.jms.Queue;
import javax.jms.QueueReceiver;
import javax.jms.QueueSender;
import javax.jms.QueueSession;
import javax.naming.NamingException;

@SuppressWarnings("serial")
public class SocialInspectionWindow extends AbstractWindow {
	
	private Queue inputQueue;
	private QueueSender sender;
	private Queue outputQueue;
	private QueueReceiver receiver;
	
	public SocialInspectionWindow() {
		super("Social inspection", 700, 500);
		
		final String jvmName = ManagementFactory.getRuntimeMXBean().getName();
		this.setTitle("Social inspection - " + jvmName);
		
		createQueueSession();
		try {
			inputQueue = (Queue) initialContext.lookup("queue/enterDataQueueSocial");
			receiver = ((QueueSession)session).createReceiver(inputQueue);
			outputQueue = (Queue) initialContext.lookup("queue/socialInspectionQueue");
			sender = ((QueueSession)session).createSender(outputQueue);
		} catch (NamingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JMSException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		
		button.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				
				try {
					ObjectMessage receive = ((ObjectMessage)receiver.receive());
					ApplicationData applicantData = (ApplicationData) receive.getObject();
					SocialResult socialResult = SocialInspection.createResult(applicantData);
		        	ObjectMessage message = session.createObjectMessage(socialResult);
					sender.send(message);
					textArea.setText(socialResult.toString());
				} catch (JMSException e1) {
					Logger.global.log(Level.SEVERE, "Could not publish message: " + e1.getMessage());
				}
			}
			
		});

	}

}

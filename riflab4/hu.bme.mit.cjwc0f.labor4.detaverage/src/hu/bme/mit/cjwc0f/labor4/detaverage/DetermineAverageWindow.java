package hu.bme.mit.cjwc0f.labor4.detaverage;

import hu.bme.mit.cjwc0f.labor4.data.ApplicationData;
import hu.bme.mit.cjwc0f.labor4.data.SocialResult;
import hu.bme.mit.cjwc0f.labor4.gui.AbstractWindow;
import hu.bme.mit.cjwc0f.labor4.workflow.DetermineAverage;
import hu.bme.mit.cjwc0f.labor4.workflow.SocialInspection;

import java.awt.BorderLayout;
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
import javax.swing.JButton;

@SuppressWarnings("serial")
public class DetermineAverageWindow extends AbstractWindow {

	private Queue inputQueue;
	private QueueReceiver receiver;
	private Queue outputQueue;
	private QueueSender sender;

	public DetermineAverageWindow() {
		super("Determine average", 350, 100);
		
		final String jvmName = ManagementFactory.getRuntimeMXBean().getName();
		this.setTitle("Determine average - " + jvmName);

		
		createQueueSession();
		try {
			inputQueue = (Queue) initialContext.lookup("queue/enterDataQueueAverage");
			receiver = ((QueueSession)session).createReceiver(inputQueue);
			outputQueue = (Queue) initialContext.lookup("queue/determineAverageQueue");
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
					ApplicationData resultData = DetermineAverage.calculate(applicantData);
		        	ObjectMessage message = session.createObjectMessage(resultData);
					sender.send(message);
					textArea.setText(resultData.toString());
				} catch (JMSException e1) {
					Logger.global.log(Level.SEVERE, "Could not publish message: " + e1.getMessage());
				}
			}
			
		});
		
	}

}

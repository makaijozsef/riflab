package hu.bme.mit.cjwc0f.labor4.assignroomnumber;

import hu.bme.mit.cjwc0f.labor4.data.ApplicationData;
import hu.bme.mit.cjwc0f.labor4.gui.AbstractWindow;
import hu.bme.mit.cjwc0f.labor4.workflow.AssignRoomNumber;
import hu.bme.mit.cjwc0f.labor4.workflow.DetermineAverage;

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
public class AssignRoomNumberWindow extends AbstractWindow {

	private Queue inputQueue;
	private QueueReceiver receiver;
	private Queue outputQueue;
	private QueueSender sender;

	public AssignRoomNumberWindow() {
		super("Assign room number", 1050, 0);

		final String jvmName = ManagementFactory.getRuntimeMXBean().getName();
		this.setTitle("Assign room number - " + jvmName);
		
		createQueueSession();
		try {
			inputQueue = (Queue) initialContext.lookup("queue/roomAssignmentQueue");
			receiver = ((QueueSession)session).createReceiver(inputQueue);
			outputQueue = (Queue) initialContext.lookup("queue/finalResultQueue");
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
					ApplicationData resultData = AssignRoomNumber.assignRoom(applicantData);
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
